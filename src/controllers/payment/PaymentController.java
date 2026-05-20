package controllers.payment;

import javax.swing.*;

import views.payment.PaymentView;
import views.payment.PaymentWindow;

public class PaymentController {

    private PaymentWindow paymentWindow;
    private PaymentView paymentView;

    public PaymentController(PaymentWindow paymentWindow) {

        this.paymentWindow = paymentWindow;
        this.paymentView = paymentWindow.getPaymentView();

        initListeners();
    }

    private void initListeners() {

        paymentView.getBtnPay().addActionListener(e -> processPayment());
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