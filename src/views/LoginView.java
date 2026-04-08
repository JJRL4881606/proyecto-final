package views;

import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.Color;
import java.awt.Component;
import java.awt.Cursor;
import java.awt.Desktop;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

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
import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.border.EmptyBorder;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

import java.net.URI;

import components.RoundButton;
import components.RoundedPanel;
import exceptions.InvalidPasswordException;
import exceptions.InvalidUserException;
import utils.AppFont;
import utils.FormUtils;
import utils.UIColors;

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
		this.setBackground(UIColors.BACKGROUND);
		setLayout(new GridBagLayout());
	    initializeComponents();
        setVisible(true);
        assignListeners();
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
        lblForgotPassword.setCursor(new Cursor(Cursor.HAND_CURSOR));
        lblForgotPassword.setFont(AppFont.small());
        lblForgotPassword.setAlignmentX(CENTER_ALIGNMENT);
        lblForgotPassword.setHorizontalAlignment(SwingConstants.CENTER);
        lblForgotPassword.addMouseListener(new MouseAdapter() {

            public void mouseClicked(MouseEvent e) {
                try {
                    Desktop.getDesktop().browse(new URI("https://www.google.com")); //Abre google mientras hacemos laparte de recuperar contrasena 
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
		
		btnLogin.setIcon(FormUtils.loadIcon("/img/login-icon.png", 30));
		btnRegistration.setIcon(FormUtils.loadIcon("/img/registration-icon.png", 30));
		
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
	
	private void setError(JLabel label, JComponent field, String message) {
	    label.setText(message);
	    field.setBorder(FormUtils.redBorder);
	}

	private void clearError(JLabel label, JComponent field) {
	    label.setText("");
	    field.setBorder(FormUtils.normalBorder);
	}
	
	//VALIDAR CAMPOS
	
	private boolean validateEmail() throws InvalidUserException {
	    String email = txtEmail.getText().trim();

	    if (email.isEmpty()) {
	        throw new InvalidUserException("El correo es obligatorio");
	    }

	    String emailRegex = "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$";

	    if (!email.matches(emailRegex)) {
	        throw new InvalidUserException("Formato de correo inválido");
	    }

	    String correoAceptado = "correo@gmail.com";

	    if (!email.equals(correoAceptado)) {
	        throw new InvalidUserException("El correo no coincide");
	    }

	    return true;
	}
	
	private boolean validatePassword() throws InvalidPasswordException {
	    String pass = String.valueOf(txtPassword.getPassword());

	    if (pass.trim().isEmpty()) {
	        throw new InvalidPasswordException("La contraseña es obligatoria");
	    }

	    String passAceptada = "1234";

	    if (!pass.equals(passAceptada)) {
	        throw new InvalidPasswordException("La contraseña no coincide");
	    }

	    return true;
	}
	
	private boolean validateLogin() {

	    resetErrorLabels();

	    boolean valid = true;

	    try {
			if (!validateEmail()) {
				valid = false;
			}
		} catch (InvalidUserException e) {
			setError(lblEmailError, txtEmail, e.getMessage());
			valid = false;
			e.printStackTrace();
		}
	    try {
			if (!validatePassword()) valid = false;
		} catch (InvalidPasswordException e) {
			setError(lblPasswordError, txtPassword, e.getMessage());
			valid = false;
			e.printStackTrace();
		}

	    return valid;
	}
	
	private void assignListeners() {

	    txtEmail.getDocument().addDocumentListener(new DocumentListener() {

	        public void insertUpdate(DocumentEvent e) {
				try {
					validateEmail();
				} catch (InvalidUserException e1) {
			    	lblEmailError.setText("El correo no coincide");
					e1.printStackTrace();
				}
	        }

	        public void removeUpdate(DocumentEvent e) {
				try {
					validateEmail();
				} catch (InvalidUserException e1) {
			    	lblEmailError.setText("El correo no coincide");
					e1.printStackTrace();
				}	        }

	        public void changedUpdate(DocumentEvent e) {
				try {
					validateEmail();
				} catch (InvalidUserException e1) {
			    	lblEmailError.setText("El correo no coincide");
					e1.printStackTrace();
				}	        }
	    });
	    
	    // KEYLISTENER EMAIL (no permitir espacios)

	    txtEmail.addKeyListener(new KeyAdapter() {
	        @Override
	        public void keyTyped(KeyEvent e) {
	            char c = e.getKeyChar();

	            if (Character.isWhitespace(c)) {
	                e.consume();
	            }
	        }
	    });

	    txtPassword.getDocument().addDocumentListener(new DocumentListener() {

	        public void insertUpdate(DocumentEvent e) {
	            try {
					validatePassword();
				} catch (InvalidPasswordException e1) {
			    	lblPasswordError.setText("La contraseña no coincide");
					e1.printStackTrace();
				}
	            //validatePassword();
	        }

	        public void removeUpdate(DocumentEvent e) {
	            try {
					validatePassword();
				} catch (InvalidPasswordException e1) {
			    	lblPasswordError.setText("La contraseña no coincide");
					e1.printStackTrace();
				}
	        }

	        public void changedUpdate(DocumentEvent e) {
	            try {
					validatePassword();
				} catch (InvalidPasswordException e1) {
			    	lblPasswordError.setText("La contraseña no coincide");
					e1.printStackTrace();
				}
	        }
	    });
	    
	    //FOCUSLISTENER EMAIL Y PASSWORD
	    addFocusEffect(txtEmail);
	    addFocusEffect(txtPassword);
	}
	
	//AGREGAR FOCO EN EL CAMPO
	
	private void addFocusEffect(JComponent field) {

	    field.addFocusListener(new FocusAdapter() {

	        @Override
	        public void focusGained(FocusEvent e) {
	            field.setBorder(BorderFactory.createCompoundBorder(
	                BorderFactory.createLineBorder(new Color(30,144,255), 2),
	                BorderFactory.createEmptyBorder(8,10,8,10)
	            ));
	        }

	        @Override
	        public void focusLost(FocusEvent e) {
	            field.setBorder(FormUtils.normalBorder);
	        }
	    });
	}
	
}
