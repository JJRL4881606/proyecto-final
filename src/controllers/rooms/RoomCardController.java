package controllers.rooms;

import java.util.List;

import components.RoomCard;
import components.SearchBar;
import views.main.MainView;
import javax.swing.*;

import components.RoomCard;
import models.RoomType;
import views.auth.LoginView;
import views.home.HomeView;
import views.main.MainWindow;
import views.payment.PaymentWindow;

import java.awt.Window;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class RoomCardController {

    private RoomCard roomCard;
    private MainView mainView;

    public RoomCardController(RoomCard roomCard, MainView mainView) {
        this.roomCard = roomCard;
        this.mainView = mainView;

        initListeners();
    }

    private void initListeners() {

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
        roomCard.getBtnReserve().addActionListener(e -> {

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
        });
    }
}
    private RoomType room;

    public RoomCardController(RoomCard roomCard, RoomType room) {
        this.roomCard = roomCard;
        this.room = room;
        initController();
    }

    private void initController() {
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
                PaymentWindow paymentWindow = new PaymentWindow();
                paymentWindow.setVisible(true);
            }
        });

        // Acción para ver detalles
        roomCard.getBtnDetails().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.out.println("Falta por hacer @JOSUE");
            }
        });
    }
}
