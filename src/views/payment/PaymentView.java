package views.payment;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.event.KeyEvent;
import java.util.Calendar;
import java.util.Date;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JSeparator;
import javax.swing.JSpinner;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.SpinnerDateModel;
import javax.swing.ImageIcon;

import javax.swing.border.EmptyBorder;

import components.RoundedImageOverlayPanel;
import components.RoundedPanel;
import components.UnderlineMenu;
import models.RoomType;
import models.User;
import utils.AppFont;
import utils.ButtonFactory;
import utils.FormUtils;
import utils.UIColors;

@SuppressWarnings("serial")
public class PaymentView extends JPanel {

    // Campos datos personales
    private JTextField txtFirstName;
    private JTextField txtLastName;
    private JTextField txtEmail;
    private JTextField txtPhone;
    private JSpinner spGuests;
    private JLabel lblGuestsError;

    // Error labels
    private JLabel lblFirstNameError;
    private JLabel lblLastNameError;
    private JLabel lblEmailError;
    private JLabel lblPhoneError;
    private JLabel lblCheckInError;
    private JLabel lblCheckOutError;
    private JLabel lblNights;
    private JTextArea txtaFeatures;

    // Campos de pago
    private JComboBox<String> cmbPaymentMethod;

    // Checkboxes
    private JCheckBox chkTerms;
    private JCheckBox chkPolicies;
    
    private JSpinner spCheckIn;
    private JSpinner spCheckOut;

    // Botón
    private JButton btnPay;
    private JMenuItem btnHome;

    // Resumen
    private JLabel lblRoom;
    private JLabel lblCheckIn;
    private JLabel lblCheckOut;
    private JLabel lblTotal;

    private final RoomType room;
    private final User user;
    
    private JLabel lblLogo;
    
    //labels de error
	private JLabel lblPaymentMethodError;

    public PaymentView(RoomType room,User user) {
		this.room = room;
		this.user = user;
        initComponents();
    }

    private void initComponents() {

    	int fieldWidth = 500;

        setLayout(new BorderLayout(20, 20));
        setBackground(Color.WHITE);

        setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));
        
        // HEADER
        this.add(headerSection(), BorderLayout.NORTH);

        // ================= LEFT PANEL =================
        RoundedPanel leftPanel = new RoundedPanel(50);
        leftPanel.setLayout(new BoxLayout(leftPanel,BoxLayout.Y_AXIS));
        leftPanel.setBorder(BorderFactory.createEmptyBorder(30,30,30,30));

        // ===== DATOS PERSONALES =====
        JLabel lblPersonal =new JLabel("Datos Personales");
        lblPersonal.setFont(AppFont.big());
        lblPersonal.setBorder(BorderFactory.createEmptyBorder(0,0,10,0));
        lblPersonal.setAlignmentX(CENTER_ALIGNMENT);
        lblPersonal.setHorizontalAlignment(JLabel.CENTER);
        
        JPanel personalPanel = new JPanel(new GridLayout(2, 2, 10, 10));
        personalPanel.setBorder(BorderFactory.createEmptyBorder(15,15,15,15));
        
        txtFirstName = FormUtils.createTextField();
        lblFirstNameError = FormUtils.createErrorLabel();
        txtFirstName.setText(user.getName());
        txtFirstName.setEditable(false);
        personalPanel.add(FormUtils.createField("Nombre", txtFirstName, lblFirstNameError, "No editable: Nombre del usuario", fieldWidth));

        txtLastName = FormUtils.createTextField();
        lblLastNameError = FormUtils.createErrorLabel();
        txtLastName.setText(user.getSurname());
        txtLastName.setEditable(false);
        personalPanel.add(FormUtils.createField("Apellidos", txtLastName, lblLastNameError, "No editable: Apellidos del usuario", fieldWidth));

        txtEmail = FormUtils.createTextField();
        lblEmailError = FormUtils.createErrorLabel();
        txtEmail.setText(user.getEmail());
        txtEmail.setEditable(false);
        personalPanel.add(FormUtils.createField("Correo", txtEmail, lblEmailError, "No editable: Correo del usuario", fieldWidth));

        txtPhone = FormUtils.createTextField();
        lblPhoneError = FormUtils.createErrorLabel();
        txtPhone.setText(user.getPhone());
        txtPhone.setEditable(false);
        personalPanel.add(FormUtils.createField("Teléfono", txtPhone, lblPhoneError, "No editable: Teléfono del usuario", fieldWidth));
        
        // ===== DATOS DE LA RESERVA =====
        JLabel lblDates =new JLabel("Datos de la reservación");
        lblDates.setFont(AppFont.big());
        lblDates.setBorder(BorderFactory.createEmptyBorder(0,0,10,0));
        lblDates.setAlignmentX(CENTER_ALIGNMENT);
        lblDates.setHorizontalAlignment(JLabel.CENTER);
        
        JPanel datesPanel = new JPanel(new GridLayout(2, 2, 10, 10));
        datesPanel.setBorder(BorderFactory.createEmptyBorder(15,15,15,15));

        // FECHA ACTUAL
        Calendar calendar = Calendar.getInstance();

        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);

        Date today = calendar.getTime();

        // FECHA ENTRADA
        spCheckIn = FormUtils.createDateField();
        JSpinner.DateEditor editorIn = (JSpinner.DateEditor) spCheckIn.getEditor();
        editorIn.getTextField().setEditable(false);
        SpinnerDateModel checkInModel = (SpinnerDateModel) spCheckIn.getModel();
        checkInModel.setStart(today);
        spCheckIn.setValue(today);
        lblCheckInError = FormUtils.createErrorLabel();
        datesPanel.add(FormUtils.createField("Fecha de entrada", spCheckIn, lblCheckInError, "", fieldWidth));

        // FECHA SALIDA
        calendar.add(Calendar.DAY_OF_MONTH, 1);
        Date tomorrow = calendar.getTime();
        
        spCheckOut = FormUtils.createDateField();
        JSpinner.DateEditor editorOut = (JSpinner.DateEditor) spCheckOut.getEditor();
        editorOut.getTextField().setEditable(false);
        SpinnerDateModel checkOutModel = (SpinnerDateModel) spCheckOut.getModel();
        checkOutModel.setStart(tomorrow);
        spCheckOut.setValue(tomorrow);
        lblCheckOutError = FormUtils.createErrorLabel();
        datesPanel.add(FormUtils.createField("Fecha de salida", spCheckOut, lblCheckOutError, "", fieldWidth));
        
        // ===== HUÉSPEDES =====
        int maxGuests = room.getCapacity();
        
		spGuests = FormUtils.createNumberField(maxGuests);
		spGuests.setPreferredSize(new Dimension(fieldWidth, 40));
	    lblGuestsError = FormUtils.createErrorLabel();
	    datesPanel.add(FormUtils.createField( "Cantidad de huéspedes", spGuests, lblGuestsError, "", fieldWidth));
        
        // ===== SECCIÓN DE PAGO =====
        JLabel lblPayment = new JLabel("Pago de la reservación");
        lblPayment.setFont(AppFont.big());
        lblPayment.setBorder(BorderFactory.createEmptyBorder(0, 0, 10, 0));
        lblPayment.setAlignmentX(CENTER_ALIGNMENT);
        lblPayment.setHorizontalAlignment(JLabel.CENTER);

        JPanel paymentPanel = new JPanel(new GridLayout(1, 1, 10, 10));
        paymentPanel.setBorder(BorderFactory.createEmptyBorder(15,15,15,15));

        String[] paymentMethods = {"Selecciona un método de pago", "Tarjeta de crédito", "Tarjeta de débito", "Efectivo", "Transferencia", "PayPal"};
        cmbPaymentMethod = FormUtils.createCombo(paymentMethods);
    	lblPaymentMethodError = FormUtils.createErrorLabel();
    	paymentPanel.add(FormUtils.createField("Método de pago:", cmbPaymentMethod, lblPaymentMethodError, "", 350));
        
    	// CHECKBOXES
    	chkTerms = new JCheckBox("Acepto términos y condiciones");
    	chkPolicies = new JCheckBox("Acepto políticas de cancelación");

    	chkTerms.setOpaque(false);
    	chkPolicies.setOpaque(false);

    	JPanel checksPanel = new JPanel();
    	checksPanel.setOpaque(false);
    	checksPanel.setLayout(new BoxLayout(checksPanel, BoxLayout.Y_AXIS));

    	chkTerms.setAlignmentX(Component.CENTER_ALIGNMENT);
    	chkPolicies.setAlignmentX(Component.CENTER_ALIGNMENT);

    	checksPanel.add(chkTerms);
    	checksPanel.add(Box.createVerticalStrut(8));
    	checksPanel.add(chkPolicies);
    	
        // BOTÓN
        btnPay = ButtonFactory.createBlueButton(
            "CONFIRMAR PAGO",
            "/assets/img/btn-icons/button-save-white-icon.png",
            "Haz click para confirmar el pago"
        );
        btnPay.setAlignmentX(Component.CENTER_ALIGNMENT);
        btnPay.setPreferredSize(new Dimension(150,55));


        // ALIGNMENTS
        personalPanel.setAlignmentX(Component.CENTER_ALIGNMENT);
        datesPanel.setAlignmentX(Component.CENTER_ALIGNMENT);
        paymentPanel.setAlignmentX(Component.CENTER_ALIGNMENT);

        chkTerms.setAlignmentX(Component.CENTER_ALIGNMENT);
        chkPolicies.setAlignmentX(Component.CENTER_ALIGNMENT);
        btnPay.setAlignmentX(Component.CENTER_ALIGNMENT);

        // AGREGAR A LEFT PANEL
        leftPanel.add(lblPersonal);
        leftPanel.add(personalPanel);
        leftPanel.add(new JSeparator());
        leftPanel.add(Box.createVerticalStrut(30));
        leftPanel.add(lblDates);
        leftPanel.add(datesPanel);
        leftPanel.add(new JSeparator());
        leftPanel.add(Box.createVerticalStrut(30));
        leftPanel.add(lblPayment);
        leftPanel.add(paymentPanel);
        leftPanel.add(new JSeparator());
        leftPanel.add(Box.createVerticalStrut(30));
        leftPanel.add(checksPanel);
        leftPanel.add(Box.createVerticalStrut(20));
        leftPanel.add(btnPay);
        
        // ================= RIGHT PANEL =================

        RoundedPanel rightPanel = new RoundedPanel(35);
        rightPanel.setLayout(new BoxLayout(rightPanel,BoxLayout.Y_AXIS));
        rightPanel.setBackground(Color.WHITE);
        rightPanel.setBorder(
            BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(220,220,220), 1, true),
                BorderFactory.createEmptyBorder(25,25,25,25)
            )
        );

        rightPanel.setPreferredSize(new Dimension(320, 0));

        // ================= IMAGE =================

        RoundedImageOverlayPanel bg = new RoundedImageOverlayPanel(
    		room.getImagePath(),
            30,
            new Color(0,0,0,0)
        );

        bg.setPreferredSize(new Dimension(270, 230));
        bg.setMaximumSize(new Dimension(Integer.MAX_VALUE, 230));
        bg.setAlignmentX(Component.CENTER_ALIGNMENT);

        // ================= HOTEL =================

        JLabel lblHotel = new JLabel("ATLANTIS THE PALM");
        lblHotel.setFont(AppFont.subtitle());

        JLabel lblAddress = new JLabel("DUBAI");
        lblAddress.setForeground(new Color(120,120,120));
        lblAddress.setFont(AppFont.small());

        // ================= PRICE =================

        JLabel lblPrice = new JLabel("$ " + room.getPrice() + " por noche");
        lblPrice.setFont(AppFont.subtitle());

        // ================= DATA =================

        lblRoom = new JLabel("Habitación: " + room.getName());
        lblCheckIn = new JLabel("Entrada:");
        lblCheckOut = new JLabel("Salida:");
        
        JLabel lblCapacity = new JLabel("Capacidad: " + room.getCapacity() + " huespedes");
        JLabel lblBedType = new JLabel("Tipo de cama: " + room.getBedType());
        lblNights = new JLabel("Estancia: 0 noche/s");
        
        StringBuilder featuresText = new StringBuilder("Incluye: ");

        if (room.getAmenities() != null) {

            for (int i = 0; i < room.getAmenities().size(); i++) {

                featuresText.append(
                		room.getAmenities()
                        .get(i)
                        .getName()
                );

                if (i < room.getAmenities().size() - 1) {
                    featuresText.append(", ");
                }
            }
        }
        
        featuresText.append(" + servicios del hotel en general");

        txtaFeatures = new JTextArea(featuresText.toString());
        txtaFeatures.setLineWrap(true);
        txtaFeatures.setWrapStyleWord(true);

        // aplicar fuente
        lblRoom.setFont(AppFont.big());
        lblCheckIn.setFont(AppFont.big());
        lblCheckOut.setFont(AppFont.big());
        lblBedType.setFont(AppFont.big());
        lblCapacity.setFont(AppFont.big());
        lblNights.setFont(AppFont.big());

        // ================= TOTAL =================

        lblTotal = new JLabel("Total: $0");
        lblTotal.setFont(AppFont.subtitle());

        // ================= SEPARATORS =================

        JSeparator topSeparator = new JSeparator();
        JSeparator bottomSeparator = new JSeparator();
        
        lblHotel.setAlignmentX(Component.LEFT_ALIGNMENT);
        lblAddress.setAlignmentX(Component.LEFT_ALIGNMENT);
        lblPrice.setAlignmentX(Component.LEFT_ALIGNMENT);
        topSeparator.setAlignmentX(Component.LEFT_ALIGNMENT);
        bg.setAlignmentX(Component.LEFT_ALIGNMENT);
        txtaFeatures.setAlignmentX(Component.LEFT_ALIGNMENT);
        lblRoom.setAlignmentX(Component.LEFT_ALIGNMENT);
        lblCheckIn.setAlignmentX(Component.LEFT_ALIGNMENT);
        lblCheckOut.setAlignmentX(Component.LEFT_ALIGNMENT);
        lblBedType.setAlignmentX(Component.LEFT_ALIGNMENT);
        lblCapacity.setAlignmentX(Component.LEFT_ALIGNMENT);
        lblNights.setAlignmentX(Component.LEFT_ALIGNMENT);
        lblTotal.setAlignmentX(Component.LEFT_ALIGNMENT);

        // ================= AGREGAR COMPONENTES =================

        // HOTEL INFO
        rightPanel.add(lblHotel);
        rightPanel.add(Box.createVerticalStrut(5));

        rightPanel.add(lblAddress);
        rightPanel.add(Box.createVerticalStrut(20));

        rightPanel.add(lblPrice);
        rightPanel.add(Box.createVerticalStrut(20));

        rightPanel.add(topSeparator);
        rightPanel.add(Box.createVerticalStrut(25));

        // IMAGEN
        rightPanel.add(bg);
        rightPanel.add(Box.createVerticalStrut(25));
        
        rightPanel.add(txtaFeatures);
        rightPanel.add(Box.createVerticalStrut(25));

        rightPanel.add(bottomSeparator);
        rightPanel.add(Box.createVerticalStrut(25));

        // DETALLES
        rightPanel.add(lblRoom);
        rightPanel.add(Box.createVerticalStrut(12));

        rightPanel.add(lblCheckIn);
        rightPanel.add(Box.createVerticalStrut(12));

        rightPanel.add(lblCheckOut);
        rightPanel.add(Box.createVerticalStrut(12));
        
        rightPanel.add(lblBedType);
        rightPanel.add(Box.createVerticalStrut(12));

        rightPanel.add(lblCapacity);
        rightPanel.add(Box.createVerticalStrut(12));

        rightPanel.add(lblNights);
        rightPanel.add(Box.createVerticalStrut(25));
        // TOTAL
        rightPanel.add(new JSeparator());
        rightPanel.add(Box.createVerticalStrut(20));

        rightPanel.add(lblTotal);

        // ================= AGREGAR PANELES =================

        add(leftPanel, BorderLayout.CENTER);
        add(rightPanel, BorderLayout.EAST);
    }
    
    // ================= HEADER =================

    public JPanel headerSection() {
        JPanel superiorPanel = new JPanel(new GridLayout(1, 3));
        
        superiorPanel.setBackground(UIColors.HEADER);
        superiorPanel.setBorder(new EmptyBorder(30, 30, 35, 30));
        superiorPanel.add(headerLeftSection());
        superiorPanel.add(headerCenterSection());
        superiorPanel.add(headerRightSection());

        return superiorPanel;
    }
    
    public JPanel headerCenterSection() {
        JPanel panel = createTransparentPanel();
        panel.setLayout(new FlowLayout(FlowLayout.CENTER));

        ImageIcon icon = new ImageIcon(getClass().getResource("/assets/img/logos/hotel-logo.png"));
    	Image img = icon.getImage().getScaledInstance(250, 80, Image.SCALE_SMOOTH);

    	lblLogo = new JLabel(new ImageIcon(img));
    	lblLogo.setCursor(new Cursor(Cursor.HAND_CURSOR));
    	
    	panel.add(lblLogo);
    	
        return panel;
    }

    public JPanel headerRightSection(){
        JPanel panel = createTransparentPanel();
    	panel.setLayout(new GridBagLayout());
        
    	JLabel lblReserve = new JLabel("RESERVAR Y PAGAR");
    	lblReserve.setFont(AppFont.big());
    	lblReserve.setForeground(Color.WHITE);
    	lblReserve.setAlignmentX(CENTER_ALIGNMENT);
    	panel.add(lblReserve);

        return panel;
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
        mb.setBorder(BorderFactory.createEmptyBorder(5, 10, 5, 10));
        mb.setOpaque(true);
        mb.setBackground(UIColors.HEADER);

        JMenu home = new UnderlineMenu("Inicio");
        home.setMnemonic(KeyEvent.VK_S);
        mb.add(home);

        btnHome = new JMenuItem("Regresar a inicio");
        btnHome.setMnemonic(KeyEvent.VK_I);
        home.add(btnHome);

        return mb;
    }

    // ===== GETTERS =====
    public JMenuItem getBtnHome() {
        return btnHome;
    }
    
    public int getGuests(){
        return (Integer) spGuests.getValue();
    }

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

    public JComboBox<String> getCmbPaymentMethod() {
        return cmbPaymentMethod;
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
    
    public JSpinner getSpCheckIn() {
        return spCheckIn;
    }
    
    public JSpinner getSpCheckOut() {
        return spCheckOut;
    }
    
    public JLabel getLblCheckIn() {
        return lblCheckIn;
    }
    
    public JLabel getLblCheckOut() {
        return lblCheckOut;
    }
    
    public JLabel getLblNights() {
        return lblNights;
    }
    
    public JLabel getLblTotal() {
        return lblTotal;
    }
    
    public RoomType getRoom() {
    	return room;
    }
	
	public JLabel getLblLogo() {
	    return lblLogo;
	}
}