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

// Controla la lógica del formulario de reservaciones del panel admin
// Maneja validaciones, cálculo automático del total, restricciones de fechas
// y el guardado de la reservación y su pago en la bd
public class ReservationFormController {

    private ReservationFormDialog view;
    private PaymentRepository paymentRepo = new PaymentRepository();
    
    // Evita que el botón guardar se procese dos veces si el usuario hace doble clic
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
        
        //Los listeners de pago, términos y políticas solo se agregan si los componentes existen (cuando haces una nueva reservacion)

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
        
        // Cada vez que cambian las fechas, se revalidan y se recalcula el total
        view.getSpCheckIn().addChangeListener(e -> {
            validateDates();
            calculateTotal();
        });

        view.getSpCheckOut().addChangeListener(e -> {
            validateDates();
            calculateTotal();
        });

        // Al cambiar la habitación, revalidar, actualizar límite de huéspedes y recalcular total
        view.getComboRoom().addActionListener(e -> {
            validateRoom();
            updateGuestLimit();
            validateGuests();
            calculateTotal();
        });
        
        view.getComboUser().addActionListener(e -> validateUser());
    }

    //Valida el formulario completo y guarda la reservación
    // Si es una reservación nueva con estado CONFIRMED, verifica primero que la
    // habitación esté disponible en esas fechas. Al editar, excluye la propia
    // reservación de la verificación de disponibilidad
    // El pago solo se guarda cuando es una reservación nueva,
    // porque al editar el pago ya existe y no se vuelve a crear
    private void handleSave() {

    	// saving previene el doble guardado por doble clic.
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

            // Solo verificar disponibilidad si el estado es CONFIRMED
            if(status.equals(ReservationStatus.CONFIRMED)) {

            	boolean available;

            	// Crear reservación. checa la disponibilidad normalmente
            	if(view.getReservation() == null) {
            	    available = repo.isRoomAvailableByDates(
            	        view.getRoomId(),
            	        checkIn,
            	        checkOut
            	    );
            	}
            	
                // Si se está editando una reservación, ignora esa misma reservación al buscar conflictos de fechas
            	else {
            	    available = repo.isRoomAvailableByDates(
            	        view.getRoomId(),
            	        checkIn,
            	        checkOut,
            	        view.getReservation().getReservationId()
            	    );
            	}
            	
                if(!available){
                    JOptionPane.showMessageDialog(null, "Habitación no disponible en esas fechas");
                    return;
                }
            }

            // Si es nueva, crear el objeto
            // si es edición, usar el existente y actualizarlo
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

            // Si el id es 0 es nueva, si ya tiene id es una edición
            if(reservation.getReservationId() == 0){
                reservationId = repo.saveAndReturnId(reservation);
                reservation.setReservationId(reservationId);
            } else {
                repo.update(reservation);
                reservationId = reservation.getReservationId();
            }

            // El pago solo se crea cuando es una reservación nueva
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
    
    // Configura los spinners de fecha para que checkin no sea anterior a hoy
    // y checkout sea siempre al menos un día después del checkin
    // Si es una reservación nueva, fuerza los valores iniciales a hoy y mañana
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

        // Solo forzar valores iniciales si es una reservación nueva
        if(view.getReservation()==null){

            view.getSpCheckIn().setValue(today);
            view.getSpCheckOut().setValue(tomorrow);

            // Se actualiza el editor también para que el texto muestre el valor correcto
            ((JSpinner.DefaultEditor) view.getSpCheckOut().getEditor()).getTextField().setValue(tomorrow);
    	}
	}
    
    // Actualiza el límite max de huéspedes del spinner según la capacidad de la habitación seleccionada
    // Si el valor actual supera el nuevo máximo, lo baja automáticamente antes de recrear el modelo del spinner
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

    // Corre todas las validaciones y devuelve false si cualquiera falla
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

    // Valida que los huéspedes sean al menos 1 y no superen la capacidad de la habitación
    // Si no hay habitación seleccionada todavía, solo valida el mínimo
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
    
    // Si el combo no existe es porque estamos editando, en ese caso siempre retorna true
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
    
    // Si el checkbox no existe es porque estamos editando, en ese caso siempre retorna true
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


    // Valida y corrige las fechas en tiempo real mientras el usuario las cambia.
    // Si checkin queda antes de hoy, se sube a hoy automáticamente.
    // Si checkout queda en la misma fecha o antes que checkin, se mueve al día siguiente.
    private boolean validateDates(){

        Date today = DateUtils.normalize(new Date());

        Date checkIn = DateUtils.normalize(
            (Date)view.getSpCheckIn().getValue()
        );

        Date checkOut = DateUtils.normalize(
            (Date)view.getSpCheckOut().getValue()
        );

        // No permitir checkin en el pasado
        if(checkIn.before(today)){
            view.getSpCheckIn().setValue(today);
            checkIn = today;
        }

        Date minCheckOut = DateUtils.addDays(
            checkIn,
            1
        );

        // Actualizar el mínimo permitido del spinner de checkout
        SpinnerDateModel modelOut = (SpinnerDateModel)view.getSpCheckOut().getModel();

        modelOut.setStart(minCheckOut);

        // Si checkout quedó inválido, corregirlo
        if(!checkOut.after(checkIn)){
            view.getSpCheckOut().setValue(minCheckOut);
            checkOut = minCheckOut;
        }

        view.clearCheckOutError();

        return true;
    }
    
    //Calcula el total multiplicando el precio por noche de la habitación por la cantidad de noches
    // Si no hay habitación seleccionada, deja el campo vacío
    // Usa mínimo 1 noche para evitar un total de 0 pesos por fechas iguales
    private void calculateTotal(){

    	//Verificar si hay habitación seleccionada
        if(view.getComboRoom().getSelectedIndex() == 0){
            view.getTxtTotal().setText("");
            return;
        }

        //Obtiene el id de la habitación seleccionada
        int roomId = view.getRoomId();

        //repos para consultar informacon
        RoomRepository roomRepo = new RoomRepository();
        RoomTypeRepository typeRepo = new RoomTypeRepository();

        //busca habitacion en la bd
        Room room = roomRepo.findById(roomId);

        //verificar que exista
        if(room == null){
            return;
        }

        //Obtener el tipo de habitación
        RoomType type = typeRepo.getById(room.getTypeId());

        //obtiene fechas de los spiners
        Date checkIn = (Date)view.getSpCheckIn().getValue();
        Date checkOut = (Date)view.getSpCheckOut().getValue();

        // Calcular días entre las dos fechas
        long nights = ChronoUnit.DAYS.between(
            checkIn.toInstant().atZone(ZoneId.systemDefault()).toLocalDate(), //convierte date en un instant, agrega zona horaira, extrae solo dia mes año 
            checkOut.toInstant().atZone(ZoneId.systemDefault()).toLocalDate()
        );

        //usa el valor mas grande entre dia y nights, para garantizar al menos una noche
        nights = Math.max(1,nights);

        double total = type.getPrice() * nights;

        view.getTxtTotal().setText(String.format("%.2f", total));
    }
}