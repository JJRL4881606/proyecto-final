package controllers.payment;

import java.awt.Point;
import java.awt.Window;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.Calendar;
import java.util.Date;

import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

import models.User;
import views.auth.LoginWindow;
import views.main.MainView;
import views.main.MainWindow;
import views.payment.PaymentView;
import views.payment.PaymentWindow;

public class PaymentController {

    private PaymentWindow paymentWindow;
    private PaymentView paymentView;
    private long estancia;
    private User user;

    public PaymentController(PaymentWindow paymentWindow, PaymentView paymentView) {
	    this.paymentWindow = paymentWindow;
	    this.paymentView = paymentView;
	    this.user = user;

        initListeners();
        setupDateValidation();
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
			
		paymentView.getBtnHome().addActionListener(e -> {
			new MainWindow(user);
            
            Window window = SwingUtilities.getWindowAncestor(paymentView);
            if (window != null) {
                window.dispose();
            }
		});	
		
		paymentView.getSpCheckIn().addChangeListener(e -> updateLabelCheckIn() );
		paymentView.getSpCheckOut().addChangeListener(e -> updateLabelCheckOut() );
	}
	
	
	protected void updateLabelCheckIn() {
		paymentView.getLblCheckIn().setText("Entrada: " + new java.text.SimpleDateFormat("dd/MM/yyyy").format(paymentView.getSpCheckIn().getValue()));
		estancia = ( ((Date) paymentView.getSpCheckOut().getValue()).getTime() 
	            - ((Date) paymentView.getSpCheckIn().getValue()).getTime() )
	            / (1000 * 60 * 60 * 24);
		paymentView.getLblNights().setText("Estancia: " + estancia + " noche/s");
		paymentView.getLblTotal().setText("$ " + (estancia * paymentView.getRoom().getPrice()));
	}
	
	protected void updateLabelCheckOut() {
		paymentView.getLblCheckOut().setText("Salida: " + new java.text.SimpleDateFormat("dd/MM/yyyy").format(paymentView.getSpCheckOut().getValue()));
		estancia = ( ((Date) paymentView.getSpCheckOut().getValue()).getTime() 
	            - ((Date) paymentView.getSpCheckIn().getValue()).getTime() )
	            / (1000 * 60 * 60 * 24);
		paymentView.getLblNights().setText("Estancia: " + estancia + " noche/s");
		paymentView.getLblTotal().setText("Total: $ " + (estancia * paymentView.getRoom().getPrice()));
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
    
    private void setupDateValidation() {

        JSpinner spCheckIn = paymentView.getSpCheckIn();
        JSpinner spCheckOut = paymentView.getSpCheckOut();

        // ================= CHECK IN =================
        spCheckIn.addChangeListener(e -> {

            Date checkInDate =
                    (Date) spCheckIn.getValue();

            Calendar cal = Calendar.getInstance();
            cal.setTime(checkInDate);

            // mínimo checkout = +1 día
            cal.add(Calendar.DAY_OF_MONTH, 1);

            Date minCheckOut = cal.getTime();

            SpinnerDateModel checkOutModel =
                    (SpinnerDateModel)
                            spCheckOut.getModel();

            // actualizar mínimo permitido
            checkOutModel.setStart(minCheckOut);

            // si checkout quedó inválido
            Date currentCheckOut =
                    (Date) spCheckOut.getValue();

            if (currentCheckOut.before(minCheckOut)) {
                spCheckOut.setValue(minCheckOut);
            }
        });


        // ================= CHECK OUT =================
        spCheckOut.addChangeListener(e -> {

            Date checkInDate =
                    (Date) spCheckIn.getValue();

            Calendar cal = Calendar.getInstance();
            cal.setTime(checkInDate);

            // mínimo permitido
            cal.add(Calendar.DAY_OF_MONTH, 1);

            Date minCheckOut = cal.getTime();

            Date selectedCheckOut =
                    (Date) spCheckOut.getValue();

            // si el usuario intenta una fecha inválida
            if (selectedCheckOut.before(minCheckOut)) {
                spCheckOut.setValue(minCheckOut);
            }
        });
    }
}