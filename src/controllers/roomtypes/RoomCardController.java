package controllers.roomtypes;

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
    private RoomType roomType;
    private User user;

    public RoomCardController(RoomCard roomCard, MainView mainView, RoomType roomType, User user) {
        this.roomCard = roomCard;
        this.mainView = mainView;
        this.roomType = roomType;
        this.user = user;

        initController();
    }

    private void initController() {
    	// detalles
        roomCard.getBtnDetails().addActionListener(e -> {
            mainView.getRoomDetailsPanel().setRoomType(roomCard.getRoomType());
            mainView.showView(MainView.ROOM_DETAILS);
        });
    	
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
                PaymentWindow paymentWindow = new PaymentWindow(roomType,user);
                paymentWindow.setVisible(true);
            }
        });
    }
}
