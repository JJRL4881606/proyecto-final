package controllers.roomtypes;

import views.payment.PaymentWindow;
import views.roomtypes.RoomDetailsView;

import java.awt.Window;

import javax.swing.SwingUtilities;

import utils.Session;

// Controla la pantalla de detalle de habitacion
//solo sirve manejar el botón de reservar

public class RoomDetailsController {

    private RoomDetailsView view;

    public RoomDetailsController(RoomDetailsView view){
        this.view = view;
        initListeners();
    }

    private void initListeners(){

    	view.getBtnReserve().addActionListener(e -> {

            // No hacer nada si todavia no se cargó un roomtype
    	    if(view.getRoomType() == null){
    	        return;
    	    }

            // Cerrar ventana actual
            Window window = SwingUtilities.getWindowAncestor(view);
            if (window != null) {
                window.dispose();
            }

            // Abrir nueva ventana de pago
    	    PaymentWindow paymentWindow = new PaymentWindow(view.getRoomType(), Session.getCurrentUser());
    	    paymentWindow.setVisible(true);
    	});
    }
}