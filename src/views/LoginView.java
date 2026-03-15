package views;

import java.awt.GridBagLayout;
import java.awt.Image;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

import java.awt.Color;
import java.awt.Component;
import java.awt.Cursor;
import java.awt.Desktop;
import java.awt.Dimension;
import java.awt.GridBagConstraints;

import javax.imageio.ImageIO;
import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.border.EmptyBorder;

import java.net.URI;

import components.RoundButton;
import components.RoundedPanel;
import utils.AppFont;
import utils.FormUtils;

@SuppressWarnings("serial")
public class LoginView extends JPanel
{	
	LoginWindow window;
	JTextField txtEmail;
	JPasswordField txtPassword;
	JLabel lblEmailError;
	JLabel lblPasswordError;
	JLabel lblWrongError;
	
	public LoginView(LoginWindow window) {
		this.window = window;
		this.setBackground(new Color(100,149,237)); 
	    setLayout(new GridBagLayout());
	    initializeComponents();
        setVisible(true);
        assignListeners();
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

	    txtEmail = FormUtils.createTextField();
	    lblEmailError = FormUtils.createErrorLabel();
	    
	    mainPanel.add(FormUtils.createField("Correo electrónico", txtEmail, lblEmailError, "Ingrese su correo electrónico"));
	    
	    // PASSWORD

	    txtPassword = FormUtils.createPasswordField();
	    lblPasswordError = FormUtils.createErrorLabel();
	    
	    mainPanel.add(FormUtils.createField("Contraseña", txtPassword, lblPasswordError, "Ingrese su contraseña"));

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
        lblForgotPassword.setCursor(new Cursor(Cursor.HAND_CURSOR));
        lblForgotPassword.addMouseListener(new MouseAdapter() {

            public void mouseClicked(MouseEvent e) {
                try {
                    Desktop.getDesktop().browse(new URI("https://www.google.com"));
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            }

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
        JPanel panelButtons = new JPanel();
        panelButtons.setBorder(new EmptyBorder(5, 20, 10, 20));
        panelButtons.setOpaque(false);

		JButton btnLogin = new RoundButton("INICIAR SESIÓN", new ImageIcon("src/img/login-icon.png"));
		btnLogin.setBackground(new Color(255, 249, 179));
		btnLogin.setForeground(Color.BLACK);
		btnLogin.setToolTipText("Haz click aquí");
		btnLogin.setFont(AppFont.big());

		JButton btnRegistration = new RoundButton("CREAR CUENTA", new ImageIcon("src/img/registration-icon.png"));
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
		
		panelButtons.add(btnLogin);	
		panelButtons.add(btnRegistration);
		
		btnLogin.addActionListener(e -> handleLogin());
		btnRegistration.addActionListener(e-> handleRegistration());

		return panelButtons;
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
	
	// QUITAR LABELS DE ERROR
	
	private void resetErrorLabels() {
	    lblEmailError.setText("");
	    txtEmail.setBorder(FormUtils.normalBorder);

	    lblPasswordError.setText("");
	    txtPassword.setBorder(FormUtils.normalBorder);

	    lblWrongError.setVisible(false);
	}
	
	//VALIDAR CAMPOS
	
	private boolean validateEmail() {
	    String email = txtEmail.getText().trim();

	    if (email.isEmpty()) {
	        lblEmailError.setText("El correo es obligatorio");
	        txtEmail.setBorder(FormUtils.redBorder);
	        return false;
	    }

	    String emailRegex = "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$";

	    if (!email.matches(emailRegex)) {
	        lblEmailError.setText("Formato de correo inválido");
	        txtEmail.setBorder(FormUtils.redBorder);
	        return false;
	    }

	    lblEmailError.setText("");
	    txtEmail.setBorder(FormUtils.normalBorder);
	    return true;
	}
	
	private boolean validatePassword() {

	    if (new String(txtPassword.getPassword()).trim().isEmpty()) {
	        lblPasswordError.setText("La contraseña es obligatoria");
	        txtPassword.setBorder(FormUtils.redBorder);
	        return false;
	    }

	    lblPasswordError.setText("");
	    txtPassword.setBorder(FormUtils.normalBorder);
	    return true;
	}
	
	private boolean validateLogin() {

	    resetErrorLabels();

	    boolean valid = true;

	    if (!validateEmail()) valid = false;
	    if (!validatePassword()) valid = false;

	    return valid;
	}
	
	private void assignListeners() {

	    txtEmail.getDocument().addDocumentListener(new javax.swing.event.DocumentListener() {

	        public void insertUpdate(javax.swing.event.DocumentEvent e) {
	            validateEmail();
	        }

	        public void removeUpdate(javax.swing.event.DocumentEvent e) {
	            validateEmail();
	        }

	        public void changedUpdate(javax.swing.event.DocumentEvent e) {
	            validateEmail();
	        }
	    });

	    txtPassword.getDocument().addDocumentListener(new javax.swing.event.DocumentListener() {

	        public void insertUpdate(javax.swing.event.DocumentEvent e) {
	            validatePassword();
	        }

	        public void removeUpdate(javax.swing.event.DocumentEvent e) {
	            validatePassword();
	        }

	        public void changedUpdate(javax.swing.event.DocumentEvent e) {
	            validatePassword();
	        }
	    });
	}
}
