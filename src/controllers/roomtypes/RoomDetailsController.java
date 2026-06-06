package controllers.roomtypes;

import views.payment.PaymentWindow;
import views.roomtypes.RoomDetailsView;

import java.awt.Window;

import javax.swing.SwingUtilities;

import utils.Session;

public class RoomDetailsController {

    private RoomDetailsView view;

    public RoomDetailsController(RoomDetailsView view){

        this.view = view;

        initListeners();
    }

    private void initListeners(){

    	view.getBtnReserve().addActionListener(e -> {

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