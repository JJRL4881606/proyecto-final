package views.payment;

import javax.swing.*;

import utils.ButtonFactory;
import utils.FormUtils;

import java.awt.*;

public class PaymentView extends JPanel {

    // Campos datos personales
    private JTextField txtFirstName;
    private JTextField txtLastName;
    private JTextField txtEmail;
    private JTextField txtPhone;
    
    //Error Labels
    private JLabel lblFirstNameError;
    private JLabel lblLastNameError;
    private JLabel lblEmailError;
    private JLabel lblPhoneError;
    
    private JLabel lblCardNumberError;
    private JLabel lblExpirationDateError;
    private JLabel lblCVVError;

    // Campos tarjeta
    private JTextField txtCardNumber;
    private JTextField txtExpirationDate;
    private JTextField txtCVV;

    // Checkboxes
    private JCheckBox chkTerms;
    private JCheckBox chkPolicies;

    // Botón
    private JButton btnPay;

    // Resumen
    private JLabel lblRoom;
    private JLabel lblCheckIn;
    private JLabel lblCheckOut;
    private JLabel lblTotal;
    
    private int fieldWidth = 200;

    public PaymentView() {
        initComponents();
    }

    private void initComponents() {

        setLayout(new BorderLayout(15, 15));
        setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        // ===== PANEL IZQUIERDO =====
        JPanel leftPanel = new JPanel();
        leftPanel.setLayout(new BoxLayout(leftPanel, BoxLayout.Y_AXIS));

        // Datos personales
        JPanel personalPanel = new JPanel(new GridLayout(4, 2, 10, 10));
        personalPanel.setBorder(
                BorderFactory.createTitledBorder("Datos Personales")
        );

        txtFirstName = FormUtils.createTextField();
	    lblFirstNameError = FormUtils.createErrorLabel();
	    this.add(FormUtils.createField("Nombres", txtFirstName, lblFirstNameError, "Ingrese sus nombres", fieldWidth));
	    
        txtLastName = FormUtils.createTextField();
	    lblLastNameError = FormUtils.createErrorLabel();
	    this.add(FormUtils.createField("Nombres", txtLastName, lblLastNameError, "Ingrese sus apellidos", fieldWidth));
	    
        txtEmail = FormUtils.createTextField();
	    lblEmailError = FormUtils.createErrorLabel();
	    this.add(FormUtils.createField("Nombres", txtEmail, lblEmailError, "Ingrese su correo", fieldWidth));
	    
        txtPhone = FormUtils.createTextField();
	    lblPhoneError = FormUtils.createErrorLabel();
	    this.add(FormUtils.createField("Nombres", txtPhone, lblPhoneError, "Ingrese su telefono", fieldWidth));

        personalPanel.add(txtFirstName);
        personalPanel.add(txtLastName);
        personalPanel.add(txtEmail);
        personalPanel.add(txtPhone);

        // Datos tarjeta
        JPanel cardPanel = new JPanel(new GridLayout(3, 2, 10, 10));
        cardPanel.setBorder(
                BorderFactory.createTitledBorder("Datos de Tarjeta")
        );
        
        txtCardNumber = FormUtils.createTextField();
	    lblCardNumberError = FormUtils.createErrorLabel();
	    this.add(FormUtils.createField("Nombres", txtCardNumber, lblCardNumberError, "Ingrese su número de tarjeta", fieldWidth));
	    
	    txtExpirationDate = FormUtils.createTextField();
	    lblExpirationDateError = FormUtils.createErrorLabel();
	    this.add(FormUtils.createField("Nombres", txtExpirationDate, lblExpirationDateError, "Ingrese la fecha vencimiento", fieldWidth));
	    
	    txtCVV = FormUtils.createTextField();
	    lblCVVError = FormUtils.createErrorLabel();
	    this.add(FormUtils.createField("Nombres", txtCVV, lblCVVError, "Ingrese el CVV", fieldWidth));

	    cardPanel.add(txtCardNumber);
	    cardPanel.add(txtExpirationDate);
	    cardPanel.add(txtCVV);

        // Checkboxes
        chkTerms = new JCheckBox("Acepto términos y condiciones");
        chkPolicies = new JCheckBox("Acepto políticas de cancelación");

        // Botón
        btnPay = ButtonFactory.createBigButton(
	            "CONFIRMAR PAGO",
	            "/assets/img/btn-icons/button-save-icon.png",
	            "Haz click para confirmar el pago"
	    );
        btnPay.setBackground(new Color(112, 238, 156));

        leftPanel.add(personalPanel);
        leftPanel.add(Box.createVerticalStrut(15));
        leftPanel.add(cardPanel);
        leftPanel.add(Box.createVerticalStrut(15));
        leftPanel.add(chkTerms);
        leftPanel.add(chkPolicies);
        leftPanel.add(Box.createVerticalStrut(15));
        leftPanel.add(btnPay);

        // ===== PANEL DERECHO =====
        JPanel rightPanel = new JPanel();
        rightPanel.setLayout(new BoxLayout(rightPanel, BoxLayout.Y_AXIS));
        rightPanel.setBorder(
                BorderFactory.createTitledBorder("Resumen de Reserva")
        );
        rightPanel.setPreferredSize(new Dimension(250, 0));

        lblRoom = new JLabel("Habitación: Junior Suite");
        lblCheckIn = new JLabel("Entrada: 04/06/2026");
        lblCheckOut = new JLabel("Salida: 05/06/2026");
        lblTotal = new JLabel("Total: $532.79 USD");

        rightPanel.add(lblRoom);
        rightPanel.add(Box.createVerticalStrut(10));
        rightPanel.add(lblCheckIn);
        rightPanel.add(Box.createVerticalStrut(10));
        rightPanel.add(lblCheckOut);
        rightPanel.add(Box.createVerticalStrut(20));
        rightPanel.add(lblTotal);

        // Agregar paneles
        add(leftPanel, BorderLayout.CENTER);
        add(rightPanel, BorderLayout.EAST);
    }

    // ===== GETTERS =====

    public JTextField getTxtFirstName() {
        return txtFirstName;
    }

    public JTextField getTxtLastName() {
        return txtLastName;
    }

    public JTextField getTxtEmail() {
        return txtEmail;
    }

    public JTextField getTxtPhone() {
        return txtPhone;
    }

    public JTextField getTxtCardNumber() {
        return txtCardNumber;
    }

    public JTextField getTxtExpirationDate() {
        return txtExpirationDate;
    }

    public JTextField getTxtCVV() {
        return txtCVV;
    }

    public JCheckBox getChkTerms() {
        return chkTerms;
    }

    public JCheckBox getChkPolicies() {
        return chkPolicies;
    }

    public JButton getBtnPay() {
        return btnPay;
    }
}