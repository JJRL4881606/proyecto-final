package views.auth;

import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.Insets;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.Color;
import java.awt.Component;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JCheckBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.border.EmptyBorder;
import components.RoundedButton;
import components.RoundedPanel;
import utils.AppFont;
import utils.ButtonFactory;
import utils.FormUtils;
import utils.UIColors;

@SuppressWarnings("serial")
public class LoginView extends JPanel
{	
	private LoginWindow window;
	
	private JTextField txtEmail;
	private JPasswordField txtPassword;
	private JCheckBox chkShowPassword;
	private JLabel lblForgotPassword;
	
	private JLabel lblEmailError;
	private JLabel lblPasswordError;
	private JLabel lblWrongError;
	
	private RoundedButton btnLogin;
	private RoundedButton btnRegistration;
	
	private int fieldWidth = 300;
	
	public LoginView() {
		this.setBackground(UIColors.BACKGROUND);
		setLayout(new GridBagLayout());
	    initializeComponents();
        setVisible(true);
   	}

	private void initializeComponents() 
	{
	    JPanel card = new RoundedPanel(50);
	    card.setLayout(new BoxLayout(card, BoxLayout.Y_AXIS));
	    card.setBackground(UIColors.CARD);
	    card.setBorder(BorderFactory.createEmptyBorder(25, 35, 25, 35));
	    card.setMaximumSize(new Dimension(500, Integer.MAX_VALUE));
	    card.setAlignmentX(CENTER_ALIGNMENT);
	    card.putClientProperty("FlatLaf.style", "arc:20");
	    
	    card.add(createTitle());
	    card.add(Box.createRigidArea(new Dimension(0, 10)));
	    card.add(createForm());
        card.add(Box.createRigidArea(new Dimension(0, 20)));
        card.add(createButtons());
	    card.add(Box.createRigidArea(new Dimension(0, 10)));

	    GridBagConstraints gbc = new GridBagConstraints();
	    gbc.gridx = 0;
	    gbc.gridy = 0;
	    gbc.anchor = GridBagConstraints.CENTER;
	    gbc.insets = new Insets(40, 40, 40, 40);
	    add(card, gbc);
	}
	
	private JPanel createTitle() {
	    JPanel panel = new JPanel();
	    panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
	    panel.setOpaque(false);
	    
        ImageIcon icon = new ImageIcon(getClass().getResource("/assets/img/logos/hotel-logo-blue.png"));
        Image img = icon.getImage().getScaledInstance(300, 96, Image.SCALE_SMOOTH);
        JLabel logo = new JLabel(new ImageIcon(img));
		logo.setAlignmentX(CENTER_ALIGNMENT);
		
		JLabel lblSubtitle = new JLabel("Ingrese sus datos para iniciar sesión");
		lblSubtitle.setBorder(new EmptyBorder(10, 20, 10, 20)); 
		lblSubtitle.setFont(AppFont.subtitle());
		lblSubtitle.setForeground(UIColors.TITLE);
		lblSubtitle.setAlignmentX(CENTER_ALIGNMENT); 

	    panel.add(logo);
	    panel.add(Box.createVerticalStrut(8));
	    panel.add(lblSubtitle);

	    return panel;
	}
		
	private JPanel createForm() {

	    JPanel mainPanel = new JPanel();
	    mainPanel.setBackground(UIColors.CARD);
	    mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));
	    mainPanel.setAlignmentX(Component.CENTER_ALIGNMENT);
	    mainPanel.setBorder(new EmptyBorder(10, 20, 10, 20));

	    // EMAIL
	    txtEmail = FormUtils.createTextField();
	    lblEmailError = FormUtils.createErrorLabel();
	    mainPanel.add(FormUtils.createField("Correo electrónico", txtEmail, lblEmailError, "Ingrese su correo electrónico", fieldWidth));
	    
	    // PASSWORD
	    txtPassword = FormUtils.createPasswordField();
	    lblPasswordError = FormUtils.createErrorLabel();
	    mainPanel.add(FormUtils.createField("Contraseña", txtPassword, lblPasswordError, "Ingrese su contraseña", fieldWidth));

	    // MOSTRAR CONTRASEÑA
	    chkShowPassword = FormUtils.createCheckBox();
	    mainPanel.add(chkShowPassword);
        add(Box.createRigidArea(new Dimension(5, 5)));
        
        // OLVIDASTE CONTRASEÑA
        lblForgotPassword = new JLabel("<html><u>¿Olvidaste tu contraseña?</u></html>");
        lblForgotPassword.setBorder(BorderFactory.createEmptyBorder(8,10,8,10));
        lblForgotPassword.setForeground(new Color(0,0,0));
        lblForgotPassword.setCursor(new Cursor(Cursor.HAND_CURSOR));
        lblForgotPassword.setFont(AppFont.small());
        lblForgotPassword.setAlignmentX(CENTER_ALIGNMENT);
        lblForgotPassword.setHorizontalAlignment(SwingConstants.CENTER);
        
        lblForgotPassword.addMouseListener(new MouseAdapter() {
            public void mouseEntered(MouseEvent e) {
                lblForgotPassword.setForeground(Color.RED);
            }

            public void mouseExited(MouseEvent e) {
                lblForgotPassword.setForeground(Color.BLACK);
            }
        });
        
        mainPanel.add(lblForgotPassword);
        
        // ERROR LABEL
        lblWrongError = new JLabel("Usuario o contraseña incorrectos");
        lblWrongError.setVisible(false);
        lblWrongError.setForeground(Color.RED);
        lblWrongError.setFont(AppFont.small());
        lblWrongError.setAlignmentX(CENTER_ALIGNMENT);

        mainPanel.add(lblWrongError);

	    return mainPanel;
	}
	    
    // CREAR LOS BOTONES
	public JPanel createButtons() {
		JPanel panelButtons = new JPanel(new GridLayout(2, 1, 0, 10));
		panelButtons.setOpaque(false);
		panelButtons.setBorder(new EmptyBorder(5, 20, 10, 20));
		
        Dimension btn = new Dimension(340,100);
        panelButtons.setPreferredSize(btn);
        panelButtons.setMaximumSize(btn);

	    btnLogin = ButtonFactory.createGoldButton(
	            "INICIAR SESIÓN",
	            "/assets/img/btn-icons/button-login-icon.png",
	            "Haz click para iniciar sesión"
	    );
	    btnRegistration = ButtonFactory.createGoldButton(
	            "CREAR CUENTA",
	            "/assets/img/btn-icons/button-registration-icon.png",
	            "Haz click para registrarte"
	    );
		
		panelButtons.add(btnLogin);	
		panelButtons.add(btnRegistration);
		
		return panelButtons;
	}
			
	public RoundedButton getBtnLogin() {
	    return btnLogin;
	}	

	public RoundedButton getBtnRegistration() {
	    return btnRegistration;
	}
	
	public String getEmail() {
	    return txtEmail.getText().trim();
	}

	public String getPassword() {
	    return new String(txtPassword.getPassword()).trim();
	}

	public JTextField getTxtEmail() {
	    return txtEmail;
	}

	public JPasswordField getTxtPassword() {
	    return txtPassword;
	}
	
	public void setEmailError(String msg) {
	    lblEmailError.setText(msg);
	    txtEmail.setBorder(FormUtils.redBorder);
	}

	public void setPasswordError(String msg) {
	    lblPasswordError.setText(msg);
	    txtPassword.setBorder(FormUtils.redBorder);
	}

	public void setWrongError() {
	    lblWrongError.setVisible(true);
	    txtEmail.setBorder(FormUtils.redBorder);
	    txtPassword.setBorder(FormUtils.redBorder);
	}

	public void clearErrors() {
	    FormUtils.clearError(lblEmailError, txtEmail);
	    FormUtils.clearError(lblPasswordError, txtPassword);
	    lblWrongError.setVisible(false);
	}
	
	public void clearEmailError(){
	    FormUtils.clearError(lblEmailError, txtEmail);
	}

	public void clearPasswordError(){
		FormUtils.clearError(lblPasswordError, txtPassword);
	}
	
	public void clearWrongError() {
	    lblWrongError.setVisible(false);
	}
	
	public JLabel getLblEmailError() {
	    return lblEmailError;
	}	
	
	public JLabel getLblPasswordError() {
	    return lblPasswordError;
	}
	
	public JCheckBox getChkShowPassword() {
	    return chkShowPassword;
	}
	
	public JLabel getLblForgotPassword() {
	    return lblForgotPassword;
	}

	public LoginWindow getWindow() {
		return window;
	}

	public void setWindow(LoginWindow window) {
		this.window = window;
	}
}