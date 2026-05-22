package views.payment;

import javax.swing.*;
import javax.swing.border.EmptyBorder;

import components.RoundedImageOverlayPanel;
import components.RoundedPanel;
import components.UnderlineMenu;
import utils.AppFont;
import utils.ButtonFactory;
import utils.FormUtils;
import utils.UIColors;

import java.awt.*;

import java.awt.*;
import javax.swing.*;

public class PaymentView extends JPanel {

    // Campos datos personales
    private JTextField txtFirstName;
    private JTextField txtLastName;
    private JTextField txtEmail;
    private JTextField txtPhone;

    // Error labels
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

    private int fieldWidth = 500;

    public PaymentView() {
        initComponents();
    }

    private void initComponents() {

        setLayout(new BorderLayout(20, 20));
        setBackground(Color.WHITE);

        setBorder(
                BorderFactory.createEmptyBorder(
                        15, 15, 15, 15)
        );
        
        // HEADER
        this.add(headerSection(), BorderLayout.NORTH);

        // ================= LEFT PANEL =================

        RoundedPanel leftPanel = new RoundedPanel(50);
        leftPanel.setLayout(
                new BoxLayout(leftPanel,
                        BoxLayout.Y_AXIS));
        
        leftPanel.setBorder(
                BorderFactory.createEmptyBorder(
                    30,30,30,30
                ));

        // ===== DATOS PERSONALES =====
        JLabel lblPersonal =
                new JLabel("Datos Personales");

        lblPersonal.setFont(AppFont.big());
        lblPersonal.setBorder(
                BorderFactory.createEmptyBorder(
                        0,5,10,0));
        
        JPanel personalPanel =
                new JPanel(new GridLayout(4, 1, 10, 10));

        personalPanel.setBorder(
        	        BorderFactory.createEmptyBorder(
        	            15,15,15,15
        	        )
        	    );
        txtFirstName = FormUtils.createTextField();
        lblFirstNameError = FormUtils.createErrorLabel();

        personalPanel.add(
                FormUtils.createField(
                        "Nombres",
                        txtFirstName,
                        lblFirstNameError,
                        "Ingrese sus nombres",
                        fieldWidth));

        txtLastName = FormUtils.createTextField();
        lblLastNameError = FormUtils.createErrorLabel();

        personalPanel.add(
                FormUtils.createField(
                        "Apellidos",
                        txtLastName,
                        lblLastNameError,
                        "Ingrese sus apellidos",
                        fieldWidth));

        txtEmail = FormUtils.createTextField();
        lblEmailError = FormUtils.createErrorLabel();

        personalPanel.add(
                FormUtils.createField(
                        "Correo",
                        txtEmail,
                        lblEmailError,
                        "Ingrese su correo",
                        fieldWidth));

        txtPhone = FormUtils.createTextField();
        lblPhoneError = FormUtils.createErrorLabel();

        personalPanel.add(
                FormUtils.createField(
                        "Teléfono",
                        txtPhone,
                        lblPhoneError,
                        "Ingrese su teléfono",
                        fieldWidth));

        // ===== DATOS TARJETA =====
        JLabel lblTarjeta =
                new JLabel("Datos de la tarjeta");

        lblTarjeta.setFont(AppFont.big());
        lblTarjeta.setBorder(
                BorderFactory.createEmptyBorder(
                        0,5,10,0));
        
        JPanel cardPanel =
                new JPanel(new GridLayout(3, 1, 10, 10));

        BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(
                    new Color(220,220,220),
                    1,
                    true // rounded
                ),
                BorderFactory.createEmptyBorder(
                    15,15,15,15
                )
            );

        txtCardNumber = FormUtils.createTextField();
        lblCardNumberError =
                FormUtils.createErrorLabel();

        cardPanel.add(
                FormUtils.createField(
                        "Número de tarjeta",
                        txtCardNumber,
                        lblCardNumberError,
                        "Ingrese su número de tarjeta",
                        fieldWidth));

        txtExpirationDate =
                FormUtils.createTextField();

        lblExpirationDateError =
                FormUtils.createErrorLabel();

        cardPanel.add(
                FormUtils.createField(
                        "Fecha de vencimiento",
                        txtExpirationDate,
                        lblExpirationDateError,
                        "MM/YY",
                        fieldWidth));

        txtCVV = FormUtils.createTextField();
        lblCVVError =
                FormUtils.createErrorLabel();

        cardPanel.add(
                FormUtils.createField(
                        "CVV",
                        txtCVV,
                        lblCVVError,
                        "Ingrese el CVV",
                        fieldWidth));

        // CHECKBOXES

        chkTerms = new JCheckBox(
                "Acepto términos y condiciones");

        chkPolicies = new JCheckBox(
                "Acepto políticas de cancelación");

        // BOTÓN

        btnPay =
                ButtonFactory.createBigButton(
                        "CONFIRMAR PAGO",
                        "/assets/img/btn-icons/button-save-icon.png",
                        "Haz click para confirmar el pago");

        btnPay.setBackground(
                new Color(112, 238, 156));

        // ALIGNMENTS
        personalPanel.setAlignmentX(LEFT_ALIGNMENT);
        cardPanel.setAlignmentX(LEFT_ALIGNMENT);
        chkTerms.setAlignmentX(LEFT_ALIGNMENT);
        chkPolicies.setAlignmentX(LEFT_ALIGNMENT);
        btnPay.setAlignmentX(LEFT_ALIGNMENT);

        // AGREGAR A LEFT PANEL
        leftPanel.add(personalPanel);
        leftPanel.add(Box.createVerticalStrut(20));
        leftPanel.add(cardPanel);
        leftPanel.add(Box.createVerticalStrut(20));
        leftPanel.add(chkTerms);
        leftPanel.add(Box.createVerticalStrut(8));
        leftPanel.add(chkPolicies);
        leftPanel.add(Box.createVerticalStrut(20));
        leftPanel.add(btnPay);

     // ================= RIGHT PANEL =================

        RoundedPanel rightPanel = new RoundedPanel(35);
        rightPanel.setLayout(new BoxLayout(
                rightPanel,
                BoxLayout.Y_AXIS
        ));

        rightPanel.setBackground(Color.WHITE);

        rightPanel.setBorder(
                BorderFactory.createCompoundBorder(
                        BorderFactory.createLineBorder(
                                new Color(220,220,220),
                                1,
                                true
                        ),
                        BorderFactory.createEmptyBorder(
                                25,25,25,25
                        )
                )
        );

        rightPanel.setPreferredSize(
                new Dimension(320, 0)
        );

        // ================= IMAGE =================

        RoundedImageOverlayPanel bg =
                new RoundedImageOverlayPanel(
                        "/assets/img/about/about1.png",
                        30,
                        new Color(0,0,0,140)
                );

        // MUY IMPORTANTE
        bg.setPreferredSize(
                new Dimension(270, 230)
        );

        bg.setMaximumSize(
                new Dimension(Integer.MAX_VALUE, 230)
        );

        bg.setAlignmentX(Component.CENTER_ALIGNMENT);

        // ================= HOTEL =================

        JLabel lblHotel =
                new JLabel("ATLANTIS THE PALM");

        lblHotel.setFont(new Font(
                "SansSerif",
                Font.BOLD,
                20
        ));

        JLabel lblAddress =
                new JLabel("DUBAI");

        lblAddress.setForeground(
                new Color(120,120,120)
        );

        lblAddress.setFont(new Font(
                "SansSerif",
                Font.PLAIN,
                13
        ));

        // ================= PRICE =================

        JLabel lblPrice =
                new JLabel("$6,780.28 MXN");

        lblPrice.setFont(new Font(
                "SansSerif",
                Font.BOLD,
                22
        ));

        // ================= DATA =================

        Font infoFont =
                new Font("SansSerif",
                        Font.BOLD,
                        16);

        lblRoom =
                new JLabel(
                        "Habitación: Junior Suite"
                );

        lblCheckIn =
                new JLabel(
                        "Entrada: 04/06/2026"
                );

        lblCheckOut =
                new JLabel(
                        "Salida: 05/06/2026"
                );

        JLabel lblGuests =
                new JLabel("Huéspedes: 2");

        JLabel lblNights =
                new JLabel("Estancia: 1 noche");

        // aplicar fuente
        lblRoom.setFont(infoFont);
        lblCheckIn.setFont(infoFont);
        lblCheckOut.setFont(infoFont);
        lblGuests.setFont(infoFont);
        lblNights.setFont(infoFont);

        // ================= TOTAL =================

        lblTotal =
                new JLabel(
                        "Total: $532.79 USD"
                );

        lblTotal.setFont(new Font(
                "SansSerif",
                Font.BOLD,
                22
        ));

        // ================= SEPARATORS =================

        JSeparator topSeparator =
                new JSeparator();

        JSeparator bottomSeparator =
                new JSeparator();

        // ================= ADD COMPONENTS =================

        // HOTEL INFO
        rightPanel.add(lblHotel);
        rightPanel.add(Box.createVerticalStrut(5));

        rightPanel.add(lblAddress);
        rightPanel.add(Box.createVerticalStrut(20));

        rightPanel.add(lblPrice);
        rightPanel.add(Box.createVerticalStrut(20));

        rightPanel.add(topSeparator);
        rightPanel.add(Box.createVerticalStrut(25));

        // IMAGE
        rightPanel.add(bg);
        rightPanel.add(Box.createVerticalStrut(25));

        rightPanel.add(bottomSeparator);
        rightPanel.add(Box.createVerticalStrut(25));

        // DETAILS
        rightPanel.add(lblRoom);
        rightPanel.add(Box.createVerticalStrut(12));

        rightPanel.add(lblCheckIn);
        rightPanel.add(Box.createVerticalStrut(12));

        rightPanel.add(lblCheckOut);
        rightPanel.add(Box.createVerticalStrut(12));

        rightPanel.add(lblGuests);
        rightPanel.add(Box.createVerticalStrut(12));

        rightPanel.add(lblNights);
        rightPanel.add(Box.createVerticalStrut(25));

        // TOTAL
        rightPanel.add(new JSeparator());
        rightPanel.add(Box.createVerticalStrut(20));

        rightPanel.add(lblTotal);

        // ================= ADD PANELS =================

        add(leftPanel, BorderLayout.CENTER);
        add(rightPanel, BorderLayout.EAST);
    }
    
    // ================= HEADER =================

    public JPanel headerSection() {

        JPanel superiorPanel = new JPanel(new GridLayout(1, 3));

        superiorPanel.setBackground(UIColors.HEADER);
        superiorPanel.setBorder(
                new EmptyBorder(30, 30, 35, 30));

        superiorPanel.add(headerLeftSection());
        superiorPanel.add(headerCenterSection());
        superiorPanel.add(headerRightSection());

        return superiorPanel;
    }
    
    public JPanel headerCenterSection() {

        JPanel panel = createTransparentPanel();
        panel.setLayout(new FlowLayout(FlowLayout.CENTER));

        ImageIcon icon = new ImageIcon(
                getClass().getResource(
                        "/assets/img/logos/hotel-logo.png"));

        Image img = icon.getImage().getScaledInstance(
                250, 80, Image.SCALE_SMOOTH);

        JLabel logo = new JLabel(new ImageIcon(img));

        panel.add(logo);

        return panel;
    }

    public JPanel headerRightSection() {
        return createTransparentPanel();
    }

    public JPanel headerLeftSection() {

        JPanel panel = createTransparentPanel();
        panel.setLayout(new GridBagLayout());

        JMenuBar menu = createMenu();
        panel.add(menu);

        return panel;
    }
    
    private JPanel createTransparentPanel() {

        JPanel panel = new JPanel();
        panel.setOpaque(false);

        return panel;
    }
    
    public JMenuBar createMenu() {

        JMenuBar mb = new JMenuBar();
        mb.setFont(AppFont.big());
        mb.setForeground(Color.WHITE);
        mb.setBorder(
                BorderFactory.createEmptyBorder(
                        5, 10, 5, 10));

        mb.setOpaque(true);
        mb.setBackground(UIColors.HEADER);

        // USUARIO
        JMenu usuario = new UnderlineMenu("Usuario");
        usuario.setMnemonic('U');
        mb.add(usuario);

        usuario.addSeparator();

        // SISTEMA
        JMenu sistema = new UnderlineMenu("Sistema");
        sistema.setMnemonic('S');
        mb.add(sistema);

        JMenuItem btnExit = new JMenuItem("Salir");
        btnExit.setMnemonic('I');
        sistema.add(btnExit);

        return mb;
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