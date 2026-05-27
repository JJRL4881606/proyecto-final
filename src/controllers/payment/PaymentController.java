package controllers.payment;

import java.awt.Point;
import java.awt.Window;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.*;

import views.auth.LoginWindow;
import views.main.MainView;
import views.main.MainWindow;
import views.payment.PaymentView;
import views.payment.PaymentWindow;

public class PaymentController {

    private PaymentWindow paymentWindow;
    private PaymentView paymentView;

    public PaymentController(PaymentWindow paymentWindow, PaymentView paymentView) {
	    this.paymentWindow = paymentWindow;
	    this.paymentView = paymentView;

        initListeners();
    }
    
	public void initListeners( ) {
		paymentView.getBtnPay().addActionListener(e -> processPayment());
		
		//paymentView.getLogOut().addActionListener(e -> handleClose());
		
		paymentWindow.addWindowListener(new WindowAdapter() {
		    @Override
		    public void windowClosing(WindowEvent e) {
		        handleClose();
		    }
		    
		    public void windowOpened(WindowEvent e) {
		        resetScroll();
		    }
		});
			
		paymentWindow.revalidate();
		paymentWindow.repaint();

		resetScroll();
	}
	
	
    private void handleClose() {
        //Session.logout();

		new LoginWindow();
        Window window = SwingUtilities.getWindowAncestor(paymentView);
        if (window != null) window.dispose();
    }
	
	private void resetScroll() {
	    SwingUtilities.invokeLater(() -> {
	    		paymentWindow.getScroll().getViewport().setViewPosition(new Point(0, 0));
	    });
	}

    private void processPayment() {

        String name = paymentView.getTxtFirstName().getText().trim();
        String lastName = paymentView.getTxtLastName().getText().trim();
        String email = paymentView.getTxtEmail().getText().trim();
        String phone = paymentView.getTxtPhone().getText().trim();

        String cardNumber = paymentView.getTxtCardNumber().getText().trim();
        String expiration = paymentView.getTxtExpirationDate().getText().trim();
        String cvv = paymentView.getTxtCVV().getText().trim();

        boolean termsAccepted =
                paymentView.getChkTerms().isSelected();

        boolean policiesAccepted =
                paymentView.getChkPolicies().isSelected();

        // Validación básica
        if (name.isEmpty() ||
                lastName.isEmpty() ||
                email.isEmpty() ||
                phone.isEmpty() ||
                cardNumber.isEmpty() ||
                expiration.isEmpty() ||
                cvv.isEmpty()) {

            JOptionPane.showMessageDialog(
                    paymentWindow,
                    "Completa todos los campos",
                    "Error",
                    JOptionPane.ERROR_MESSAGE
            );
            return;
        }

        if (!termsAccepted || !policiesAccepted) {

            JOptionPane.showMessageDialog(
                    paymentWindow,
                    "Debes aceptar los términos y políticas",
                    "Advertencia",
                    JOptionPane.WARNING_MESSAGE
            );
            return;
        }

        JOptionPane.showMessageDialog(
                paymentWindow,
                "Pago realizado correctamente",
                "Éxito",
                JOptionPane.INFORMATION_MESSAGE
        );
    }
}