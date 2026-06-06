package controllers.reservations;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.temporal.ChronoUnit;
import java.util.Calendar;
import java.util.Date;
import javax.swing.JOptionPane;
import javax.swing.JSpinner;
import javax.swing.SpinnerDateModel;
import javax.swing.SpinnerNumberModel;

import models.Payment;
import models.Reservation;
import models.ReservationStatus;
import models.Room;
import models.RoomType;
import repository.PaymentRepository;
import repository.ReservationRepository;
import repository.RoomRepository;
import repository.RoomTypeRepository;
import utils.DateUtils;
import utils.FormUtils;
import views.reservations.ReservationFormDialog;

public class ReservationFormController {

    private ReservationFormDialog view;
    private PaymentRepository paymentRepo = new PaymentRepository();
    
    private boolean saving = false;

    public ReservationFormController(ReservationFormDialog view) {
        this.view = view;

        initDateRestrictions();
        initListeners();
    }

    private void initListeners() {
        view.getBtnSave().addActionListener(e -> handleSave());
        view.getBtnCancel().addActionListener(e -> handleCancel());

        view.getSpGuests().addChangeListener(e -> validateGuests());
        view.getComboStatus().addActionListener(e -> validateStatus());
        
        if(view.getComboPaymentMethod() != null) {
            view.getComboPaymentMethod().addActionListener(e -> validatePaymentMethod());
        }
        
        if(view.getChkTerms() != null) {
	        view.getChkTerms().addActionListener(e -> {
	        	if(view.getChkTerms().isSelected()) {
	        		view.clearTermsError(); 
	        	}
	        });
        }
        
        if(view.getChkPolicies() != null) {
	        view.getChkPolicies().addActionListener(e -> {
	        	if(view.getChkPolicies().isSelected()) {
	        		view.clearPoliciesError(); 
	        	}
	        });
        }
        
        // fechas
        view.getSpCheckIn().addChangeListener(e -> {
            validateDates();
            calculateTotal();
        });

        view.getSpCheckOut().addChangeListener(e -> {
            validateDates();
            calculateTotal();
        });

        // habitación
        view.getComboRoom().addActionListener(e -> {
            validateRoom();
            updateGuestLimit();
            validateGuests();
            calculateTotal();
        });
        
        view.getComboUser().addActionListener(e -> validateUser());

        FormUtils.addFocusEffect(view.getTxtTotal(), view.getLblTotalError());
    }

    private void handleSave() {

        if (saving) return;
        saving = true;

        try {

            if(!validateForm()) return;

            ReservationRepository repo = new ReservationRepository();

            LocalDate checkIn = ((Date)view.getSpCheckIn().getValue())
                .toInstant().atZone(ZoneId.systemDefault()).toLocalDate();

            LocalDate checkOut = ((Date)view.getSpCheckOut().getValue())
                .toInstant().atZone(ZoneId.systemDefault()).toLocalDate();

            String status = view.getStatus();

            if(status.equals(ReservationStatus.CONFIRMED)) {

                boolean available = (view.getReservation() == null)
                    ? repo.isRoomAvailableByDates(view.getRoomId(), checkIn, checkOut)
                    : repo.isRoomAvailableByDates(view.getRoomId(), checkIn, checkOut, view.getReservation().getReservationId());

                if(!available){
                    JOptionPane.showMessageDialog(null, "Habitación no disponible en esas fechas");
                    return;
                }
            }

            Reservation reservation = (view.getReservation() == null)
                ? new Reservation(
                    0,
                    view.getUserId(),
                    view.getRoomId(),
                    checkIn,
                    checkOut,
                    view.getGuests(),
                    view.getStatus(),
                    view.getTotal(),
                    LocalDateTime.now()
                )
                : view.getReservation();

            reservation.setUserId(view.getUserId());
            reservation.setRoomId(view.getRoomId());
            reservation.setCheckInDate(checkIn);
            reservation.setCheckOutDate(checkOut);
            reservation.setGuests(view.getGuests());
            reservation.setStatus(view.getStatus());
            reservation.setTotal(view.getTotal());

            int reservationId;

            if(reservation.getReservationId() == 0){
                reservationId = repo.saveAndReturnId(reservation);
                reservation.setReservationId(reservationId);
            } else {
                repo.update(reservation);
                reservationId = reservation.getReservationId();
            }

            // SOLO 1 PAYMENT
            if(view.getReservation() == null){
                Payment payment = new Payment(
                    reservationId,
                    view.getTotal(),
                    view.getComboPaymentMethod().getSelectedItem().toString(),
                    LocalDate.now()
                );

                paymentRepo.save(payment);
            }

            view.setReservation(reservation);
            view.setSaved(true);
            view.dispose();

        } finally {
            saving = false;
        }
    }
    
    private void initDateRestrictions(){

        FormUtils.onlyDateNumbers(view.getSpCheckIn());
        FormUtils.onlyDateNumbers(view.getSpCheckOut());

        Date today = DateUtils.normalize(new Date());
        Date tomorrow = DateUtils.addDays(today,1);

        SpinnerDateModel checkInModel =
            new SpinnerDateModel(
                today,
                today,
                null,
                Calendar.DAY_OF_MONTH
            );

        SpinnerDateModel checkOutModel =
            new SpinnerDateModel(
                tomorrow,
                tomorrow,
                null,
                Calendar.DAY_OF_MONTH
            );

        view.getSpCheckIn().setModel(checkInModel);
        view.getSpCheckOut().setModel(checkOutModel);

        // solo si es nueva reservación
        if(view.getReservation()==null){

            view.getSpCheckIn().setValue(today);

            view.getSpCheckOut().setValue(tomorrow);

            ((JSpinner.DefaultEditor)
                view.getSpCheckOut().getEditor())
                .getTextField()
                .setValue(tomorrow);
    	}
	}
    
    private void updateGuestLimit() {
        if (view.getComboRoom().getSelectedIndex() == 0) return;

        Room room = new RoomRepository().findById(view.getRoomId());
        if (room == null) return;

        RoomType type = new RoomTypeRepository().getById(room.getTypeId());
        int maxGuests = type.getCapacity();
        int current = (int) view.getSpGuests().getValue();

        // Ajusta el valor actual si supera el máximo antes de recrear el modelo
        if (current > maxGuests) {
            view.getSpGuests().setValue(maxGuests);
        }

        // Configura el spinner con el nuevo límite superior
        view.getSpGuests().setModel(new SpinnerNumberModel(
            Math.min(current, maxGuests), 1, maxGuests, 1
        ));
    }
    
    private void handleCancel() {
        int option = view.confirmCancel();
        if (option == JOptionPane.YES_OPTION) {
            view.dispose();
        }
    }

    private boolean validateForm() {
        view.clearErrors();
        boolean valid = true;
        
        if (!validateUser()) valid = false;
        if (!validateRoom()) valid = false;
        if (!validateGuests()) valid = false;
        if (!validateStatus()) valid = false;
        if (!validateTotal()) valid = false;
        if (!validateDates()) valid = false;
        if (!validateTerms()) valid = false;
        if (!validatePolicies()) valid = false;
        if (!validatePaymentMethod()) valid = false;
        return valid;
    }
    
    private boolean validateUser(){
        if(view.getComboUser().getSelectedIndex() == 0){
            view.setUserError("Seleccione un usuario");
            return false;
        }

        view.clearUserError();
        return true;
    }

    private boolean validateRoom(){
        if(view.getComboRoom().getSelectedIndex() == 0){
            view.setRoomError("Seleccione una habitación");
            return false;
        }

        view.clearRoomError();
        return true;
    }

    private boolean validateGuests() {
        int guests = (int) view.getSpGuests().getValue();

        if (guests <= 0) {
            view.setGuestsError("Mínimo 1 huésped");
            return false;
        }

        // Si no hay habitación seleccionada, no valida el máximo
        if (view.getComboRoom().getSelectedIndex() == 0) {
            view.clearGuestsError();
            return true;
        }

        Room room = new RoomRepository().findById(view.getRoomId());
        RoomType type = new RoomTypeRepository().getById(room.getTypeId());

        if (guests > type.getCapacity()) {
            view.setGuestsError("Máximo " + type.getCapacity() + " huéspedes");
            return false;
        }

        view.clearGuestsError();
        return true;
    }

    private boolean validateStatus(){
        if(view.getComboStatus().getSelectedIndex() == 0){
            view.setStatusError("Seleccione un estado");
            return false;
        }

        view.clearStatusError();
        return true;
    }
    
    private boolean validatePaymentMethod() {
    	
	    if(view.getComboPaymentMethod() == null) {
	        return true; // estamos editando
	    }

        if (view.getComboPaymentMethod().getSelectedIndex() == 0) {
            view.setPaymentMethodError("Seleccione un método de pago");
            return false;
        }

        view.clearPaymentMethodError();
        return true;
    }
    
    private boolean validateTerms() {
    	
	    if(view.getChkTerms() == null) {
	        return true; // estamos editando
	    }

        if (!view.getChkTerms().isSelected()) {
            view.setTermsError("Debe aceptar los términos y condiciones");
            return false;
        }

        view.clearTermsError();
        return true;
    } 
    
    private boolean validatePolicies() {
    	
	    if(view.getChkPolicies() == null) {
	        return true; // estamos editando
	    }

        if (!view.getChkPolicies().isSelected()) {
            view.setPoliciesError("Debe aceptar las políticas de reservación");
            return false;
        }

        view.clearPoliciesError();
        
        return true;
    }
    
    private boolean validateTotal(){
        return true;
    }

    private boolean validateDates(){

        Date today = DateUtils.normalize(new Date());

        Date checkIn = DateUtils.normalize(
            (Date)view.getSpCheckIn().getValue()
        );

        Date checkOut = DateUtils.normalize(
            (Date)view.getSpCheckOut().getValue()
        );

        if(checkIn.before(today)){
            view.getSpCheckIn().setValue(today);
            checkIn = today;
        }

        Date minCheckOut = DateUtils.addDays(
            checkIn,
            1
        );

        SpinnerDateModel modelOut = (SpinnerDateModel)view.getSpCheckOut().getModel();

        modelOut.setStart(minCheckOut);

        if(!checkOut.after(checkIn)){
            view.getSpCheckOut().setValue(minCheckOut);
            checkOut=minCheckOut;
        }

        view.clearCheckOutError();

        return true;
    }
    
    private void calculateTotal(){

        if(view.getComboRoom().getSelectedIndex() == 0){
            view.getTxtTotal().setText("");
            return;
        }

        int roomId = view.getRoomId();

        RoomRepository roomRepo = new RoomRepository();
        RoomTypeRepository typeRepo = new RoomTypeRepository();

        Room room = roomRepo.findById(roomId);

        if(room == null){
            return;
        }

        RoomType type = typeRepo.getById(room.getTypeId());

        Date checkIn = (Date)view.getSpCheckIn().getValue();

        Date checkOut = (Date)view.getSpCheckOut().getValue();

        long nights = ChronoUnit.DAYS.between(
            checkIn.toInstant()
                .atZone(ZoneId.systemDefault())
                .toLocalDate(),

            checkOut.toInstant()
                .atZone(ZoneId.systemDefault())
                .toLocalDate()
        );

        nights = Math.max(1,nights);

        double total = type.getPrice() * nights;

        view.getTxtTotal().setText(String.format("%.2f", total));
    }
}