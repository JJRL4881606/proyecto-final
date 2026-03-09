package views;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;

import javax.swing.BoxLayout;
import javax.swing.ButtonGroup;
import javax.swing.ImageIcon;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JRadioButton;
import javax.swing.JSpinner;

import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Image;
import javax.imageio.ImageIO;
import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.JTextField;
import javax.swing.SpinnerDateModel;
import javax.swing.border.Border;
import javax.swing.border.EmptyBorder;

import components.RoundButton;
import components.RoundedPanel;
import utils.AppFont;

@SuppressWarnings("serial")
public class RegistrationView extends JPanel
{
	int ventanaCentroW = 400;
	
	RegistrationView registerWindow;

	JTextField txtName;
	JTextField txtSurname;
	JPasswordField txtPassword;
	JTextField txtEmail;
	JTextField txtPhone;
	JSpinner spBirthDate;
	JComboBox<String> comboCountry;
	JRadioButton rbtnMale;
	JRadioButton rbtnFemale;
	ButtonGroup genderGroup;
	JCheckBox chkTerms;
	
	JLabel lblNameError;
	JLabel lblSurnameError;
	JLabel lblPasswordError;
	JLabel lblEmailError;
	JLabel lblPhoneError;
	JLabel lblCountryError;
	JLabel lblBirthDateError;
	JLabel lblGenderError;
	JLabel lblTermsError;
	
    Border redBorder = BorderFactory.createCompoundBorder(
        BorderFactory.createLineBorder(Color.RED, 2),
        BorderFactory.createEmptyBorder(8, 10, 8, 10)
    );
    
    Border normalBorder = BorderFactory.createEmptyBorder(8, 10, 8, 10);
    
    public RegistrationView() 
    {
		this.setBackground(new Color(100,149,237)); 
	    setLayout(new GridBagLayout());
	    initializeComponents();
        setVisible(true);
    }
    
    public void initializeComponents() 
    {
	    JPanel card = new RoundedPanel(50);
	    card.setLayout(new BoxLayout(card, BoxLayout.Y_AXIS));
	    card.setBackground(new Color(151, 210, 251));
	    card.setBorder(BorderFactory.createEmptyBorder(25, 35, 25, 35));
	    card.setMaximumSize(new Dimension(700, Integer.MAX_VALUE));
	    card.setAlignmentX(CENTER_ALIGNMENT);
	    card.putClientProperty("FlatLaf.style", "arc:20");
	    
	    //AGREGAR LAS COSAS A LA CARD
	    card.add(createTitle());
	    card.add(Box.createRigidArea(new Dimension(0, 10)));
	    card.add(createForm());
	    card.add(Box.createRigidArea(new Dimension(0, 10)));
	    card.add(createButton());
	    card.add(Box.createRigidArea(new Dimension(0, 10)));

	    GridBagConstraints gbc = new GridBagConstraints();
	    gbc.gridx = 0;
	    gbc.gridy = 0;
	    gbc.anchor = GridBagConstraints.CENTER;
	    gbc.insets = new java.awt.Insets(40, 40, 40, 40);
	    add(card, gbc);
    }
    
    public JPanel createForm() {
    	
    	//PANEL PRINCIPAL
    	
        JPanel panelPrincipal = new JPanel();
        panelPrincipal.setBackground(new Color(151, 210, 251));
        panelPrincipal.setLayout(new BoxLayout(panelPrincipal, BoxLayout.Y_AXIS));
        panelPrincipal.setAlignmentX(Component.CENTER_ALIGNMENT);
        panelPrincipal.setBorder(new EmptyBorder(0, 40, 10, 40));

        //NOMBRE
        
        txtName = new JTextField();
        txtName.setBorder(BorderFactory.createEmptyBorder(8,10,8,10));
        txtName.setFont(AppFont.normal());
        txtName.setMaximumSize(new Dimension(Integer.MAX_VALUE, 35));
        lblNameError = createErrorLabel();
        panelPrincipal.add(createField("Nombre(s)", txtName, lblNameError));
        
        //APELLIDOS
        
        txtSurname = new JTextField();
        txtSurname.setBorder(BorderFactory.createEmptyBorder(8,10,8,10));
        txtSurname.setFont(AppFont.normal());
        txtSurname.setMaximumSize(new Dimension(Integer.MAX_VALUE, 35));
        lblSurnameError = createErrorLabel();
        panelPrincipal.add(createField("Apellidos", txtSurname, lblSurnameError));
        
        //CONTRASEÑA
        
        txtPassword = new JPasswordField();
        txtPassword.setBorder(BorderFactory.createEmptyBorder(8,10,8,10));
        txtPassword.setFont(AppFont.normal());
        txtPassword.setMaximumSize(new Dimension(Integer.MAX_VALUE, 35));
        lblPasswordError = createErrorLabel();
        panelPrincipal.add(createField("Contraseña", txtPassword, lblPasswordError));

        //EMAIL
        
        txtEmail = new JTextField();
        txtEmail.setBorder(BorderFactory.createEmptyBorder(8,10,8,10));
        txtEmail.setFont(AppFont.normal());
        txtEmail.setMaximumSize(new Dimension(Integer.MAX_VALUE, 35));
        lblEmailError = createErrorLabel();
        panelPrincipal.add(createField("Correo electrónico", txtEmail, lblEmailError));
        
        //TELÉFONO
        
        txtPhone = new JTextField();
        txtPhone.setBorder(BorderFactory.createEmptyBorder(8,10,8,10));
        txtPhone.setFont(AppFont.normal());
        txtPhone.setMaximumSize(new Dimension(Integer.MAX_VALUE, 35));
        lblPhoneError = createErrorLabel();
        panelPrincipal.add(createField("Número de teléfono", txtPhone, lblPhoneError));
        
        //FECHA NACIMIENTO
        
        spBirthDate = new JSpinner(new SpinnerDateModel());
        spBirthDate.setBorder(BorderFactory.createEmptyBorder(6,0,6,0));
        spBirthDate.setMaximumSize(new Dimension(Integer.MAX_VALUE, 35));
        JSpinner.DateEditor editor = new JSpinner.DateEditor(spBirthDate, "dd/MM/yyyy");
        spBirthDate.setEditor(editor);
        lblBirthDateError = createErrorLabel();
        panelPrincipal.add(createField("Fecha de nacimiento", spBirthDate, lblBirthDateError));
        
        //PAÍS
        
        String[] countryList = {"Seleccione el país", "Afganistán", "Albania", "Alemania", "Andorra", "Angola", "Antigua y Barbuda", "Arabia Saudita", "Argelia", "Argentina", "Armenia", "Australia", "Austria", "Azerbaiyán", "Bahamas", "Bangladés", "Baréin", "Bélgica", "Belice", "Benín", "Bielorrusia", "Birmania", "Bolivia", "Bosnia y Herzegovina", "Botsuana", "Brasil", "Brunéi", "Bulgaria", "Burkina Faso", "Burundi", "Bután", "Cabo Verde", "Camboya", "Camerún", "Canadá", "Catar", "Chad", "Chile", "China", "Chipre", "Colombia", "Comoras", "Corea del Norte", "Corea del Sur", "Costa de Marfil", "Costa Rica", "Croacia", "Cuba", "Dinamarca", "Dominica", "Ecuador", "Egipto", "El Salvador", "Emiratos Árabes Unidos", "Eritrea", "Eslovaquia", "Eslovenia", "España", "Estados Unidos", "Estonia", "Esuatini", "Etiopía", "Filipinas", "Finlandia", "Fiyi", "Francia", "Gabón", "Gambia", "Georgia", "Ghana", "Granada", "Grecia", "Guatemala", "Guinea", "Guinea-Bisáu", "Guinea Ecuatorial", "Guyana", "Haití", "Honduras", "Hungría", "India", "Indonesia", "Irak", "Irán", "Irlanda", "Islandia", "Islas Marshall", "Islas Salomón", "Israel", "Italia", "Jamaica", "Japón", "Jordania", "Kazajistán", "Kenia", "Kirguistán", "Kiribati", "Kuwait", "Laos", "Lesoto", "Letonia", "Líbano", "Liberia", "Libia", "Liechtenstein", "Lituania", "Luxemburgo", "Madagascar", "Malasia", "Malaui", "Maldivas", "Malí", "Malta", "Marruecos", "Mauricio", "Mauritania", "México", "Micronesia", "Moldavia", "Mónaco", "Mongolia", "Montenegro", "Mozambique", "Namibia", "Nauru", "Nepal", "Nicaragua", "Níger", "Nigeria", "Noruega", "Nueva Zelanda", "Omán", "Países Bajos", "Pakistán", "Palaos", "Palestina", "Panamá", "Papúa Nueva Guinea", "Paraguay", "Perú", "Polonia", "Portugal", "Reino Unido", "República Centroafricana", "República Checa", "República del Congo", "República Democrática del Congo", "República Dominicana", "Ruanda", "Rumania", "Rusia", "Samoa", "San Cristóbal y Nieves", "San Marino", "San Vicente y las Granadinas", "Santa Lucía", "Santo Tomé y Príncipe", "Senegal", "Serbia", "Seychelles", "Sierra Leona", "Singapur", "Siria", "Somalia", "Sri Lanka", "Sudáfrica", "Sudán", "Sudán del Sur", "Suecia", "Suiza", "Surinam", "Tailandia", "Taiwán", "Tanzania", "Tayikistán", "Timor Oriental", "Togo", "Tonga", "Trinidad y Tobago", "Túnez", "Turkmenistán", "Turquía", "Tuvalu", "Ucrania", "Uganda", "Uruguay", "Uzbekistán", "Vanuatu", "Vaticano", "Venezuela", "Vietnam", "Yemen", "Yibuti", "Zambia", "Zimbabue"};
    	comboCountry = new JComboBox<>(countryList);
    	comboCountry.setBorder(BorderFactory.createEmptyBorder(6,0,6,0));
    	comboCountry.setMaximumSize(new Dimension(Integer.MAX_VALUE, 35));
    	comboCountry.setFont(AppFont.normal());
    	lblCountryError = createErrorLabel();
    	panelPrincipal.add(createField("País", comboCountry, lblCountryError));
        
    	//GÉNERO

        JPanel genderPanel = new JPanel();
        genderPanel.setLayout(new GridLayout(0, 1));
        genderPanel.setOpaque(false);
        genderPanel.setAlignmentX(Component.CENTER_ALIGNMENT);
        genderPanel.setMaximumSize(new Dimension(Integer.MAX_VALUE, 70));
        rbtnMale = new JRadioButton("Hombre");
        rbtnFemale = new JRadioButton("Mujer");
        genderGroup = new ButtonGroup();
        genderGroup.add(rbtnMale);
        genderGroup.add(rbtnFemale);
        genderPanel.add(rbtnMale);
        genderPanel.add(rbtnFemale);
        lblGenderError = createErrorLabel();
        panelPrincipal.add(createField("Género", genderPanel, lblGenderError));

        //ACEPTAR TÉRMINOS
        
        chkTerms = new JCheckBox("Acepto los términos y condiciones");
        chkTerms.setAlignmentX(Component.CENTER_ALIGNMENT);
        lblTermsError = createErrorLabel();
        panelPrincipal.add(createField(null, chkTerms, lblTermsError));
        
        //REGRESAR EL PANEL
        return panelPrincipal;
    }
    
    //CREAR EL TÍTULO
    
	private JPanel createTitle() {
	    JPanel panel = new JPanel();
	    panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
	    panel.setOpaque(false);
	    
		JLabel lblTitle = new JLabel("HOTEL MJ - REGISTRO");
		lblTitle.setBorder(new EmptyBorder(30, 20, 20, 20)); 
		lblTitle.setFont(AppFont.title());
		lblTitle.setAlignmentX(CENTER_ALIGNMENT);
		panel.add(lblTitle);
		
		JLabel lblSubtitle = new JLabel("Ingrese los datos para registrarse");
		lblSubtitle.setBorder(new EmptyBorder(10, 20, 30, 20)); 
		lblSubtitle.setFont(AppFont.subtitle());
		lblSubtitle.setAlignmentX(CENTER_ALIGNMENT); 

	    panel.add(lblTitle);
	    panel.add(Box.createVerticalStrut(8));
	    panel.add(lblSubtitle);

        return panel;
	}
	
	//CREAR EL BOTÓN
	
	private JPanel createButton() {
		JPanel panel = new JPanel(new GridLayout(2, 1, 0, 10));
		panel.setOpaque(false);
	    panel.setBorder(new EmptyBorder(5, 20, 10, 20));

        RoundButton botonCrearCuenta = new RoundButton("CREAR CUENTA", new ImageIcon("src/img/login-icon.png"));
		botonCrearCuenta.setBackground(new Color(255, 249, 179));
		botonCrearCuenta.setFont(AppFont.big());
        botonCrearCuenta.setFocusPainted(false);
        botonCrearCuenta.addActionListener(e -> validateRegistration());
		        
        RoundButton btnReturn = new RoundButton("REGRESAR", new ImageIcon("src/img/login-icon.png"));
        btnReturn.setBackground(new Color(255, 249, 179));
        btnReturn.setFont(AppFont.big());
        //btnReturn.setFocusPainted(false);
        btnReturn.addActionListener(e -> {

            int option = JOptionPane.showConfirmDialog(this, "¿Seguro que deseas regresar? Se perderán todos los datos");

            if (option == JOptionPane.YES_OPTION) {
                new LoginWindow();
                java.awt.Window window = 
                    javax.swing.SwingUtilities.getWindowAncestor(this);

                if (window != null) {
                    window.dispose();
                }
            }
        });
		
		try
		{
			Image icono = ImageIO.read(getClass().getResource("/img/login-icon.png"));
			icono = icono.getScaledInstance(30,30, Image.SCALE_SMOOTH);
			botonCrearCuenta.setIcon(new ImageIcon(icono));
		}
		catch(Exception ex) 
		{
			System.out.println("No está la imagen del ícono");
		}
		
        panel.add(botonCrearCuenta);
		panel.add(btnReturn);

        return panel;
	}	
	
	private JLabel createErrorLabel() {
	    JLabel label = new JLabel();
	    label.setForeground(new Color(220, 38, 38));
	    label.setFont(AppFont.small());
	    label.setAlignmentX(Component.CENTER_ALIGNMENT);
	    return label;
	}
	
	private JPanel createField(String labelText, JComponent field, JLabel errorLabel) {
		
	    JPanel panel = new JPanel();
	    panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
	    panel.setOpaque(false);
	    panel.setAlignmentX(Component.CENTER_ALIGNMENT);

	    JLabel label = new JLabel(labelText);
	    label.setFont(AppFont.normal());
	    label.setAlignmentX(Component.CENTER_ALIGNMENT);

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
	
	private void resetErrorLabels() {		
		lblNameError.setText("");
		txtName.setBorder(normalBorder);
		lblSurnameError.setText("");
		txtSurname.setBorder(normalBorder);
		lblPasswordError.setText("");
		txtPassword.setBorder(normalBorder);
		lblEmailError.setText("");
		txtEmail.setBorder(normalBorder);
		lblPhoneError.setText("");
		txtPhone.setBorder(normalBorder);
		lblCountryError.setText("");
		comboCountry.setBorder(normalBorder);
		lblBirthDateError.setText("");
		spBirthDate.setBorder(normalBorder);
		lblGenderError.setText("");
		lblTermsError.setText("");
	}
	
	//VALIDACIONES DE LOS CAMPOS
	
	private void validateRegistration() {

	    resetErrorLabels();

	    boolean valid = true;

	    if (!validateName()) valid = false;
	    if (!validateSurname()) valid = false;
	    if (!validatePassword()) valid = false;
	    if (!validateEmail()) valid = false;
	    if (!validatePhone()) valid = false;
	    if (!validateBirthDate()) valid = false;
	    if (!validateCountry()) valid = false;
	    if (!validateGender()) valid = false;
	    if (!validateTerms()) valid = false;

	    if (valid) {
	        JOptionPane.showMessageDialog(this, "Registro exitoso");
	        new MainPageWindow();
	        java.awt.Window window = 
	            javax.swing.SwingUtilities.getWindowAncestor(this);

	        if (window != null) {
	            window.dispose();
	        }
	    }
	}

	private boolean validateName() {
	    String name = txtName.getText().trim();

	    if (name.isEmpty()) {
	        lblNameError.setText("El nombre es obligatorio");
			txtName.setBorder(redBorder);
	        return false;
	    }

	    if (!name.matches("[a-zA-ZáéíóúÁÉÍÓÚñÑ ]+")) {
	        lblNameError.setText("Solo se permiten letras");
			txtName.setBorder(redBorder);
	        return false;
	    }

	    return true;
	}
	
	private boolean validateSurname() {
	    String surname = txtSurname.getText().trim();

	    if (surname.isEmpty()) {
	        lblSurnameError.setText("Los apellidos son obligatorios");
			txtSurname.setBorder(redBorder);
	        return false;
	    }

	    if (!surname.matches("[a-zA-ZáéíóúÁÉÍÓÚñÑ ]+")) {
			txtSurname.setBorder(redBorder);
	        lblSurnameError.setText("Solo se permiten letras");
	        return false;
	    }

	    return true;
	}
	
	private boolean validatePassword() {
	    if (new String(txtPassword.getPassword()).trim().isEmpty()) {
	        lblPasswordError.setText("La contraseña es obligatoria");
			txtPassword.setBorder(redBorder);
	        return false;
	    }
	    return true;
	}
	
	private boolean validateEmail() {
	    String email = txtEmail.getText().trim();

	    if (email.isEmpty()) {
	        lblEmailError.setText("El correo es obligatorio");
			txtEmail.setBorder(redBorder);
	        return false;
	    }

	    String emailRegex = "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$";

	    if (!email.matches(emailRegex)) {
	        lblEmailError.setText("Formato de correo inválido");
			txtEmail.setBorder(redBorder);
	        return false;
	    }

	    return true;
	}
	
	private boolean validatePhone() {
	    String phone = txtPhone.getText().trim();

	    if (phone.isEmpty()) {
	        lblPhoneError.setText("El teléfono es obligatorio");
			txtPhone.setBorder(redBorder);
	        return false;
	    }

	    if (!phone.matches("\\d+")) {
	        lblPhoneError.setText("Solo se permiten números");
			txtPhone.setBorder(redBorder);
	        return false;
	    }

	    if (!phone.matches("\\d{10,}")) {
	        lblPhoneError.setText("Debe tener al menos 10 números");
			txtPhone.setBorder(redBorder);
	        return false;
	    }
	    
	    return true;
	}
	
	private boolean validateBirthDate() {
	    if (spBirthDate.getValue() == null) {
	        lblBirthDateError.setText("La fecha de nacimiento es obligatoria");
	        return false;
	    }
	    return true;
	}
	
	private boolean validateCountry() {
	    if (comboCountry.getSelectedIndex() == 0) {
	        lblCountryError.setText("Seleccione un país");
	        comboCountry.setBorder(redBorder);
	        return false;
	    }
	    return true;
	}
	
	private boolean validateGender() {
	    if (!rbtnMale.isSelected() && !rbtnFemale.isSelected()) {
	        lblGenderError.setText("Seleccione un género");
	        return false;
	    }
	    return true;
	}
	
	private boolean validateTerms() {
	    if (!chkTerms.isSelected()) {
	        lblTermsError.setText("Debe aceptar los términos y condiciones");
	        return false;
	    }
	    return true;
	}

}