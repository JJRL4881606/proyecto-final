package controllers.rooms;

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
