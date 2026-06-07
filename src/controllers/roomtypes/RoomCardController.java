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
    	
        // Mostrar la info completa del tipo de habitacion
        roomCard.getBtnDetails().addActionListener(e -> {

            // mandar el RoomType seleccionado al panel de detalles
            mainView.getRoomDetailsPanel().setRoomType(
                roomCard.getRoomType()
            );

            // mostrar la vista de detalles
            mainView.showView(MainView.ROOM_DETAILS);
        });
        
        // mandar al proceso de reservación de esta habitación
        roomCard.getBtnReserve().addActionListener(
            new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {

                    // Obtener la ventana que contiene esta tarjeta
                    Window window = SwingUtilities.getWindowAncestor(roomCard);

                    // cerrar la ventana actual antes de abrir paymnent
                    if(window != null){
                        window.dispose();
                    }

                    // Crear la ventana de pago utilizando el tipo de
                    // habitación seleccionado y el usuario actual
                    PaymentWindow paymentWindow =
                        new PaymentWindow(
                            roomType,
                            user
                        );

                    // Mostrar la ventana de pago
                    paymentWindow.setVisible(true);
                }
            }
        );
    }
}
