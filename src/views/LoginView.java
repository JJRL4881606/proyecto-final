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
import javax.swing.JCheckBox;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.border.EmptyBorder;
import java.net.URI;

import components.RoundButton;
import components.RoundedPanel;
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
	RoundButton btnLogin;
	RoundButton btnRegistration;
	
	public LoginView() {
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
	    chkShowPassword.addActionListener(e -> {
	        if (chkShowPassword.isSelected()) {
	            txtPassword.setEchoChar((char) 0);
	        } else {
	            txtPassword.setEchoChar('•');
	        }
	    });
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
                    Desktop.getDesktop().browse(new URI("https://www.google.com"));
                    //Abre google mientras hacemos laparte de recuperar contrasena 
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

        btnLogin = new RoundButton("INICIAR SESIÓN",
    	    new ImageIcon(getClass().getResource("/img/button-login-icon.png")));
		btnLogin.setBackground(new Color(255, 249, 179));
		btnLogin.setForeground(Color.BLACK);
		btnLogin.setToolTipText("Haz click aquí");
		btnLogin.setFont(AppFont.big());

		btnRegistration = new RoundButton("CREAR CUENTA",
			new ImageIcon(getClass().getResource("/img/button-registration-icon.png")));
		btnRegistration.setBackground(new Color(255, 249, 179));
		btnRegistration.setForeground(Color.BLACK);
		btnRegistration.setToolTipText("Haz click aquí");
		btnRegistration.setFont(AppFont.big());
		
		btnLogin.setIcon(FormUtils.loadIcon("/img/button-login-icon.png", 30));
		btnRegistration.setIcon(FormUtils.loadIcon("/img/button-registration-icon.png", 30));
		
		panelButtons.add(btnLogin);	
		panelButtons.add(btnRegistration);
		
		return panelButtons;
	}
	
	
	
	private void assignListeners() {

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
	
	public RoundButton getBtnLogin() {
	    return btnLogin;
	}	

	public RoundButton getBtnRegistration() {
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

	public void showWrongError() {
	    lblWrongError.setVisible(true);
	}

	public void clearErrors() {
	    lblEmailError.setText("");
	    lblPasswordError.setText("");
	    lblWrongError.setVisible(false);

	    txtEmail.setBorder(FormUtils.normalBorder);
	    txtPassword.setBorder(FormUtils.normalBorder);
	}
	
	private void resetField(JLabel label, JComponent field) {
	    label.setText("");
	    field.setBorder(FormUtils.normalBorder);
	}
	
	public void clearLblEmailError() {
	    resetField(lblEmailError, txtEmail);
	}
	
	public void clearLblPasswordError() {
	    resetField(lblPasswordError, txtPassword);
	}
}
