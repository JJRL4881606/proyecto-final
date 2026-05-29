package controllers.rooms;

import components.RoomCard;
import views.main.MainView;
import javax.swing.*;

import models.RoomType;
import models.User;
import views.payment.PaymentWindow;

import java.awt.Window;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class RoomCardController {

    private RoomCard roomCard;
    private MainView mainView;
    private RoomType room;
    private User user;

    public RoomCardController(RoomCard roomCard, MainView mainView, RoomType room, User user) {
        this.roomCard = roomCard;
        this.mainView = mainView;
        this.room = room;
        this.user = user;
        initController();
    }

    private void initController() {
    	// detalles
        roomCard.getBtnDetails().addActionListener(e -> {
            mainView.roomDetailsPanel.setRoom(
                roomCard.getRoom()
            );

            mainView.showView(
                MainView.ROOM_DETAILS
            );
        });

        // reservar
        /*roomCard.getBtnReserve().addActionListener(e -> {

            SearchBar bookingSearch =
                mainView.bookingSearchPanel.getSearchBar();

            bookingSearch.setGuests(
                roomCard.getRoom().getCapacity()
            );

            mainView.bookingSearchPanel.setRooms(
                List.of(roomCard.getRoom())
            );

            mainView.showView(
                MainView.BOOKING_SEARCH
            );
        });*/
    	
        // Acción para reservar
        roomCard.getBtnReserve().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Cerrar ventana actual
                Window window = SwingUtilities.getWindowAncestor(roomCard);
                if (window != null) {
                    window.dispose();
                }

                // Abrir nueva ventana de pago
                PaymentWindow paymentWindow = new PaymentWindow(room,user);
                paymentWindow.setVisible(true);
            }
        });
    }
}
