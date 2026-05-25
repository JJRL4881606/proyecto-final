package controllers.booking;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.temporal.ChronoUnit;
import java.util.Date;
import javax.swing.JOptionPane;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import models.Reservation;
import models.ReservationStatus;
import models.Room;
import models.RoomStatus;
import models.RoomType;
import repository.RoomRepository;
import repository.RoomTypeRepository;
import utils.FormUtils;
import utils.Validator;
import views.booking.ReservationFormDialog;

public class ReservationFormController {

    private ReservationFormDialog view;

    public ReservationFormController(ReservationFormDialog view) {
        this.view = view;
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
            calculateTotal();
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

    private boolean validateDates() {
        Date checkIn = (Date) view.getSpCheckIn().getValue();
        Date checkOut = (Date) view.getSpCheckOut().getValue();

        if(!checkOut.after(checkIn)){
            view.setCheckOutError(
                "Debe ser al menos 1 noche"
            );
            return false;
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