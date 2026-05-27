package controllers.rooms;

import javax.swing.*;

import components.RoomCard;
import models.RoomType;
import models.User;
import views.auth.LoginView;
import views.home.HomeView;
import views.main.MainWindow;
import views.payment.PaymentWindow;

import java.awt.Window;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class RoomCardController {

    private RoomCard roomCard;
    private RoomType room;
    private User user;

    public RoomCardController(RoomCard roomCard, RoomType room, User user) {
        this.roomCard = roomCard;
        this.room = room;
        this.user = user;
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
                PaymentWindow paymentWindow = new PaymentWindow(room,user);
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
