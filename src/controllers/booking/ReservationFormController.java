package controllers.booking;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.temporal.ChronoUnit;
import java.util.Calendar;
import java.util.Date;
import javax.swing.JOptionPane;
import javax.swing.JSpinner;
import javax.swing.SpinnerDateModel;
import models.Reservation;
import models.ReservationStatus;
import models.Room;
import models.RoomStatus;
import models.RoomType;
import repository.ReservationRepository;
import repository.RoomRepository;
import repository.RoomTypeRepository;
import utils.DateUtils;
import utils.FormUtils;
import views.booking.ReservationFormDialog;

public class ReservationFormController {

    private ReservationFormDialog view;

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
        
        view.getComboUser().addActionListener(e -> {
            validateUser();
        });

        FormUtils.addFocusEffect(
            view.getTxtTotal(),
            view.getLblTotalError()
        );
    }

    private void handleSave() {
        if (!validateForm()) {
            return;
        }
        
        ReservationRepository reservationRepo = new ReservationRepository();

    	int reservationId = 0;

    	if(view.getReservation()!=null){
    	    reservationId = view.getReservation().getReservationId();
    	}

    	boolean available = reservationRepo.isRoomAvailable(

    	        view.getRoomId(),

    	        ((Date)view.getSpCheckIn().getValue())
    	            .toInstant()
    	            .atZone(ZoneId.systemDefault())
    	            .toLocalDate(),

    	        ((Date)view.getSpCheckOut().getValue())
    	            .toInstant()
    	            .atZone(ZoneId.systemDefault())
    	            .toLocalDate(),

    	        reservationId
    	);

    	if(!available){
    	    JOptionPane.showMessageDialog(
    	        null,
    	        "La habitación ya está ocupada en esas fechas"
    	    );

    	    return;
    	}
        	
        Reservation reservation = view.getReservation();
        Date checkIn = (Date) view.getSpCheckIn().getValue();
        Date checkOut = (Date) view.getSpCheckOut().getValue();

        if (reservation == null) {
            reservation = new Reservation(
                0,
                view.getUserId(),
                view.getRoomId(),
                checkIn.toInstant().atZone(ZoneId.systemDefault()).toLocalDate(),
                checkOut.toInstant().atZone(ZoneId.systemDefault()).toLocalDate(),
                view.getGuests(),
                view.getStatus(),
                view.getTotal(),
                LocalDateTime.now()
            );
        } else {
            reservation.setUserId(view.getUserId());
            reservation.setRoomId(view.getRoomId());
            reservation.setCheckInDate(checkIn.toInstant().atZone(ZoneId.systemDefault()).toLocalDate());
            reservation.setCheckOutDate(checkOut.toInstant().atZone(ZoneId.systemDefault()).toLocalDate());
            reservation.setGuests(view.getGuests());
            reservation.setStatus(view.getStatus());
            reservation.setTotal(view.getTotal());
        }

        view.setSaved(true);
        view.setReservation(reservation);
        
        //Poner la habitación como ocupada, para que no se pueda reserva 
        RoomRepository roomRepo = new RoomRepository();

        	Room room = roomRepo.findById(reservation.getRoomId());

        	if(room != null){
        		if(!reservation.getStatus().equals(ReservationStatus.CANCELED)){
        	        room.setStatus(RoomStatus.OCCUPIED);
        	    }else{
        	        room.setStatus(RoomStatus.AVAILABLE);
        	    }
        	    
        	    try {
					roomRepo.update(room);
				} catch (IOException e) {
					e.printStackTrace();
				}
        	}
        	
        view.dispose();
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
        view.getSpGuests().setModel(new javax.swing.SpinnerNumberModel(
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
        boolean valid = true;
        if (!validateUser()) valid = false;
        if( !validateRoom()) valid = false;
        if (!validateGuests()) valid = false;
        if (!validateStatus()) valid = false;
        if (!validateTotal()) valid = false;
        if (!validateDates()) valid = false;
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
    
    private boolean validateTotal(){
        return !view.getTxtTotal().getText().isBlank();
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