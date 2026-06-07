package controllers.account;

import java.awt.Color;
import java.awt.Dimension;
import java.time.format.DateTimeFormatter;
import java.util.List;

import javax.swing.Box;
import javax.swing.JOptionPane;

import components.ReservationCard;
import models.Reservation;
import models.ReservationStatus;
import models.Room;
import models.User;
import repository.ReservationRepository;
import repository.RoomRepository;
import utils.Session;
import views.account.MyReservationsView;

public class MyReservationsController {

    private MyReservationsView view;
    private ReservationRepository repository;
    
    private static final DateTimeFormatter DATE_FORMAT = DateTimeFormatter.ofPattern("dd/MM/yyyy");
    private static final DateTimeFormatter DATE_TIME_FORMAT = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");

    public MyReservationsController(MyReservationsView view) {
        this.view = view;
        repository = new ReservationRepository();

        loadReservations();
    }

    //cargar y mostrar todas las reservaciones del usuario actual
    public void loadReservations() {

        User user = Session.getCurrentUser(); //usuario actual

        if(user == null){
            return;
        }

        List<Reservation> reservations = repository.getReservationsByUser(user.getId());

        //limpiar las tarjetas actuales antes de volver a cargarlas
        view.getCardsContainer().removeAll();

        RoomRepository roomRepository = new RoomRepository();

        for(Reservation reservation : reservations){

            //obtener la habitación asociada a la reservación
            Room room = roomRepository.findById(reservation.getRoomId());

            //crear la tarjeta que mostrará la info
            ReservationCard card = new ReservationCard();

            //mostrar la información principal de la reservación
            card.getLblRoom().setText(
                "Habitación #" + room.getRoomNumber()
            );

            card.getLblCheckIn().setText(
                "Check-in: " + reservation.getCheckInDate().format(DATE_FORMAT)
            );

            card.getLblCheckOut().setText(
                "Check-out: " + reservation.getCheckOutDate().format(DATE_FORMAT)
            );

            card.getLblCreatedAt().setText(
                "Reservada el: " + reservation.getCreatedAt().format(DATE_TIME_FORMAT)
            );

            card.getLblGuests().setText(
                reservation.getGuests() + " huésped(es)"
            );

	    	String status = reservation.getStatus();

    	    card.getLblStatus().setText(status);

    	    //mostrar nombre y color según el estado de la reservación
    	    switch(status){
	    	    case ReservationStatus.CONFIRMED:
	    	        card.getLblStatus().setText("Confirmada");
	    	        card.getLblStatus().setForeground(new Color(46,125,50));
	    	        break;
	
	    	    case ReservationStatus.CANCELED:
	    	        card.getLblStatus().setText("Cancelada");
	    	        card.getLblStatus().setForeground(new Color(198,40,40));
	    	        break;
	
	    	    case ReservationStatus.COMPLETED:
	    	        card.getLblStatus().setText("Completada");
	    	        card.getLblStatus().setForeground(new Color(33,150,243));
	    	        break;
    	    }    	    
    	    
            //solo las reservaciones confirmadas pueden cancelarse
    	    boolean canCancel = status.equals(ReservationStatus.CONFIRMED);
    	    card.getBtnCancel().setVisible(canCancel);
    	    
    	    if(canCancel){

    	    	//cancelar la reservación y actualizar la vista
    	    	
    	        card.getBtnCancel().addActionListener(e->{

    	            int option = JOptionPane.showConfirmDialog(
    	                null,
    	                """
    	                ¿Deseas cancelar esta reservación?

    	                Esta acción no se puede deshacer.

    	                Dependiendo de las políticas del hotel pueden aplicar restricciones.
    	                """,
    	                "Cancelar reservación",
    	                JOptionPane.YES_NO_OPTION,
    	                JOptionPane.WARNING_MESSAGE
    	            );

    	            if(option == JOptionPane.YES_OPTION){

    	                repository.cancelReservation(
    	                    reservation.getReservationId()
    	                );

    	                loadReservations();
    	            }
    	        });
    	    }
    	    
    	    //mostrar el monto total pagado por la reservación
    	    card.getLblTotal().setText(
    	        "Total: $" + String.format("%,.2f", reservation.getTotal())
    	    );
    	    
    	    card.setPreferredSize(new Dimension(1300, 160));
    	    card.setMinimumSize(new Dimension(1300, 160));
    	    card.setMaximumSize(new Dimension(1300, 160));

    	    view.getCardsContainer().add(card);
    	    view.getCardsContainer().add(Box.createVerticalStrut(15));
    	}        	
        
        //actualizar interfaz después de reconstruir la lista
    	view.getCardsContainer().revalidate();
    	view.getCardsContainer().repaint();
        
    	//mostrar mensaje cuando no existan reservaciones
        view.getLblNoReservations().setVisible(reservations.isEmpty());
    }
}