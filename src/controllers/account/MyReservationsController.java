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

    public void loadReservations() {

        User user = Session.getCurrentUser();

        if(user == null){
            return;
        }

        List<Reservation> reservations = repository.getReservationsByUser(user.getId());

        view.getCardsContainer().removeAll();

        RoomRepository roomRepository = new RoomRepository();

    	for(Reservation reservation : reservations){

    	    Room room = roomRepository.findById(reservation.getRoomId());

    	    ReservationCard card = new ReservationCard();

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
    	    
    	    boolean canCancel = status.equals(ReservationStatus.CONFIRMED);
    	    card.getBtnCancel().setVisible(canCancel);
    	    
    	    if(canCancel){

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
    	    
    	    card.getLblTotal().setText(
	    	    "Total: $" + String.format("%,.2f", reservation.getTotal())
	    	);
    	    
    	    card.setPreferredSize(new Dimension(1300, 160));
    	    card.setMinimumSize(new Dimension(1300, 160));
    	    card.setMaximumSize(new Dimension(1300, 160));

    	    view.getCardsContainer().add(card);
    	    view.getCardsContainer().add(Box.createVerticalStrut(15));
    	}        	
    	view.getCardsContainer().revalidate();
    	view.getCardsContainer().repaint();
        
        view.getLblNoReservations().setVisible(reservations.isEmpty());
    }
}