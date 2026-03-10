package views;

import java.awt.GridBagLayout;
import java.awt.Image;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.GridBagConstraints;

import javax.imageio.ImageIO;
import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.border.Border;
import javax.swing.border.EmptyBorder;
import components.RoundButton;
import components.RoundedPanel;
import utils.AppFont;

@SuppressWarnings("serial")
public class LoginView extends JPanel
{
	int ventanaCentroW = 400;
	
	LoginWindow window;
	JTextField txtEmail;
	JPasswordField txtPassword;
	JLabel lblEmailError;
	JLabel lblPasswordError;
	JLabel lblWrongError;
	
    Border redBorder = BorderFactory.createCompoundBorder(
        BorderFactory.createLineBorder(Color.RED, 2),
        BorderFactory.createEmptyBorder(8, 10, 8, 10)
    );
    
    Border normalBorder = BorderFactory.createEmptyBorder(8, 10, 8, 10);
	
	public LoginView(LoginWindow window) {
		this.window = window;
		this.setBackground(new Color(100,149,237)); 
	    setLayout(new GridBagLayout());
	    initializeComponents();
        setVisible(true);
   	}

	private void initializeComponents() 
	{
	    JPanel card = new RoundedPanel(50);
	    card.setLayout(new BoxLayout(card, BoxLayout.Y_AXIS));
	    card.setBackground(new Color(151, 210, 251));
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
	    gbc.insets = new java.awt.Insets(40, 40, 40, 40);
	    add(card, gbc);
	}
	
	private JPanel createTitle() {
	    JPanel panel = new JPanel();
	    panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
	    panel.setOpaque(false);
	    
		JLabel lblTitle = new JLabel("HOTEL MJ - LOGIN");
		lblTitle.setBorder(new EmptyBorder(30, 20, 20, 20)); 
		lblTitle.setFont(AppFont.title());
		lblTitle.setAlignmentX(CENTER_ALIGNMENT);
		
		JLabel lblSubtitle = new JLabel("Ingrese sus datos para iniciar sesión");
		lblSubtitle.setBorder(new EmptyBorder(10, 20, 30, 20)); 
		lblSubtitle.setFont(AppFont.subtitle());
		lblSubtitle.setAlignmentX(CENTER_ALIGNMENT); 

	    panel.add(lblTitle);
	    panel.add(Box.createVerticalStrut(8));
	    panel.add(lblSubtitle);

	    return panel;
	}
		
	private JPanel createForm() {

	    JPanel mainPanel = new JPanel();
	    mainPanel.setBackground(new Color(151, 210, 251));
	    mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));
	    mainPanel.setAlignmentX(Component.CENTER_ALIGNMENT);
	    mainPanel.setBorder(new EmptyBorder(10, 20, 10, 20));

	    // EMAIL

	    txtEmail = new JTextField();
	    txtEmail.setBorder(BorderFactory.createEmptyBorder(8,10,8,10));
	    txtEmail.setFont(AppFont.normal());
	    txtEmail.setMaximumSize(new Dimension(Integer.MAX_VALUE, 35));

	    lblEmailError = createErrorLabel();

	    mainPanel.add(createField("Correo electrónico", txtEmail, lblEmailError, "Ingrese su correo electrónico"));

	    // PASSWORD

	    txtPassword = new JPasswordField();
	    txtPassword.setBorder(BorderFactory.createEmptyBorder(8,10,8,10));
	    txtPassword.setFont(AppFont.normal());
	    txtPassword.setMaximumSize(new Dimension(Integer.MAX_VALUE, 35));

	    lblPasswordError = createErrorLabel();

	    mainPanel.add(createField("Contraseña", txtPassword, lblPasswordError, "Ingrese su contraseña"));

	    // MOSTRAR CONTRASEÑA

	    JCheckBox chkShowPassword = new JCheckBox("Mostrar contraseña");
	    chkShowPassword.setOpaque(false);
	    chkShowPassword.setFont(AppFont.small());
	    chkShowPassword.setAlignmentX(Component.CENTER_ALIGNMENT);

	    mainPanel.add(chkShowPassword);
        
        // OLVIDASTE CONTRASEÑA
        
        JLabel lblForgotPassword = new JLabel("<html><u>¿Olvidaste tu contraseña?</u></html>");
        lblForgotPassword.setBorder(BorderFactory.createEmptyBorder(8,10,8,10));
        lblForgotPassword.setForeground(new Color(0,0,0));
        lblForgotPassword.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        lblForgotPassword.setFont(AppFont.small());
        lblForgotPassword.setAlignmentX(CENTER_ALIGNMENT);
        lblForgotPassword.setHorizontalAlignment(SwingConstants.CENTER);

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
    
    // CREAR LABELS DE ERROR
    
	private JLabel createErrorLabel() {
	    JLabel label = new JLabel();
	    label.setForeground(new Color(220, 38, 38));
	    label.setFont(AppFont.small());
	    label.setAlignmentX(Component.CENTER_ALIGNMENT);
	    return label;
	}
	    
    // CREAR LOS BOTONES
    
	public JPanel createButtons() {
        JPanel fila = new JPanel();
        fila.setBorder(new EmptyBorder(5, 20, 10, 20));
        fila.setOpaque(false);

		JButton btnLogin = new RoundButton("INICIAR SESIÓN", new ImageIcon("src/img/login-icon.png"));
		btnLogin.setBackground(new Color(255, 249, 179));
		btnLogin.setForeground(Color.BLACK);
		btnLogin.setToolTipText("Haz click aquí");
		btnLogin.setFont(AppFont.big());

		JButton btnRegistration = new RoundButton("CREAR CUENTA", new ImageIcon("src/img/login-icon.png"));
		btnRegistration.setBackground(new Color(255, 249, 179));
		btnRegistration.setForeground(Color.BLACK);
		btnRegistration.setToolTipText("Haz click aquí");
		btnRegistration.setFont(AppFont.big());
		
		try
		{
			Image icono = ImageIO.read(getClass().getResource("/img/login-icon.png"));
			icono = icono.getScaledInstance(30,30, Image.SCALE_SMOOTH);
			btnLogin.setIcon(new ImageIcon(icono));
		}
		catch(Exception ex) 
		{
			System.out.println("No está la imagen del ícono");
		}
		
		fila.add(btnLogin);	
		fila.add(btnRegistration);
		
		btnLogin.addActionListener(e -> handleLogin());
		btnRegistration.addActionListener(e-> handleRegistration());

		return fila;
	}
	
	// MOSTRAR MENSAJE DE INICIO SESIÓN
	
	private void handleLogin() {
		
		if(validateLogin()) {
			JOptionPane.showMessageDialog(
				this,
 				"Se inició la sesión", 
 				"Sesión iniciada", 
 				JOptionPane.INFORMATION_MESSAGE
 			);
			
			new MainPageWindow();
			window.dispose();
		}
	}
	
	private void handleRegistration() {
		new RegistrationWindow();
		window.dispose();
	}

	//MOSTRAR LABELS DE ERROR
	
	private void showEmailError(String mensaje) {
		lblEmailError.setText(mensaje);
		lblEmailError.setVisible(true);
		txtEmail.setBorder(redBorder);
	}	
	
	private void showPasswordError(String mensaje) {
		lblPasswordError.setText(mensaje);
		lblPasswordError.setVisible(true);
		txtPassword.setBorder(redBorder);
	}
	
	// QUITAR LABELS DE ERROR
	
	private void resetErrorLabels() {
		lblEmailError.setVisible(false);
	    txtEmail.setBorder(normalBorder);
	    lblPasswordError.setVisible(false);
	    txtPassword.setBorder(normalBorder);

	}
	
	//VALIDAR QUE LOS CAMPOS NO ESTÉN VACÍOS
	
	private boolean validateLogin() {

		resetErrorLabels();
	    boolean valid = true;

	    String email = txtEmail.getText().trim();
	    String password = String.valueOf(txtPassword.getPassword()).trim();

	    // VALIDAR CORREO VACÍO
	    if (email.isEmpty()) {
	        showEmailError("El correo es obligatorio");
	        valid = false;
	    }
	    // VALIDAR FORMATO CORREO
	    else if (!email.matches("^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$")) {
	    	showEmailError("Formato de correo inválido");
	        valid = false;
	    }

	    // VALIDAR CONTRASEÑA VACÍA
	    if (password.isEmpty()) {
	        showPasswordError("La contraseña es obligatoria");
	        valid = false;
	    }

	    return valid;
	}
	
	// CREAR CAMPOS
	
	private JPanel createField(String labelText, JComponent field, JLabel errorLabel, String placeholder) {

	    JPanel panel = new JPanel();
	    panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
	    panel.setOpaque(false);
	    panel.setAlignmentX(Component.CENTER_ALIGNMENT);

	    JLabel label = new JLabel(labelText);
	    label.setFont(AppFont.normal());
	    label.setAlignmentX(Component.CENTER_ALIGNMENT);

	    if (field instanceof JTextField) {
	        ((JTextField) field).putClientProperty("JTextField.placeholderText", placeholder);
	    }

	    errorLabel.setFont(AppFont.small());
	    errorLabel.setForeground(Color.RED);
	    errorLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

	    panel.add(label);
	    panel.add(field);
	    panel.add(Box.createRigidArea(new Dimension(0, 5)));
	    panel.add(errorLabel);
	    panel.add(Box.createRigidArea(new Dimension(0, 15)));

	    return panel;
	}
	
}
