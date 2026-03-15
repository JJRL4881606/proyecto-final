package views;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Insets;
import java.awt.Window;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

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
import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.JTextField;
import javax.swing.SpinnerDateModel;
import javax.swing.SwingUtilities;
import javax.swing.border.EmptyBorder;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

import components.RoundButton;
import components.RoundedPanel;
import utils.AppFont;
import utils.FormUtils;
import utils.UIColors;


@SuppressWarnings("serial")
public class RegistrationView extends JPanel
{	
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
	
	
	    
    public RegistrationView() 
    {
		this.setBackground(UIColors.BACKGROUND); 
	    setLayout(new GridBagLayout());
	    initializeComponents();
        setVisible(true);
		assignListeners();
    }
    
    public void initializeComponents() 
    {
	    JPanel card = new RoundedPanel(50);
	    card.setLayout(new BoxLayout(card, BoxLayout.Y_AXIS));
	    card.setBackground(UIColors.CARD);
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
	    gbc.insets = new Insets(40, 40, 40, 40);
	    add(card, gbc);
    }
    
    public JPanel createForm() {
    	
    	//PANEL PRINCIPAL
    	
        JPanel mainPanel = new JPanel();
        mainPanel.setBackground(UIColors.CARD);
        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));
        mainPanel.setAlignmentX(Component.CENTER_ALIGNMENT);
        mainPanel.setBorder(new EmptyBorder(10, 20, 10, 20));

        //NOMBRE
        
        txtName = FormUtils.createTextField();
        lblNameError = FormUtils.createErrorLabel();
        mainPanel.add(FormUtils.createField("Nombre(s)", txtName, lblNameError, "Ingrese su(s) nombre(s)"));
        
        //APELLIDOS
        
        txtSurname = FormUtils.createTextField();
        lblSurnameError = FormUtils.createErrorLabel();
        mainPanel.add(FormUtils.createField("Apellidos", txtSurname, lblSurnameError, "Ingrese su(s) apellido(s)"));
        
        //EMAIL
        
	    txtEmail = FormUtils.createTextField();
        lblEmailError = FormUtils.createErrorLabel();
        mainPanel.add(FormUtils.createField("Correo electrónico", txtEmail, lblEmailError, "Ingrese su email"));
        
        //TELÉFONO
        
	    txtPhone = FormUtils.createTextField();
        lblPhoneError = FormUtils.createErrorLabel();
        mainPanel.add(FormUtils.createField("Número de teléfono", txtPhone, lblPhoneError, "Ingrese su número de teléfono"));
        
        //FECHA NACIMIENTO
        
        spBirthDate = new JSpinner(new SpinnerDateModel());
        spBirthDate.setBorder(BorderFactory.createEmptyBorder(6,0,6,0));
        spBirthDate.setMaximumSize(new Dimension(Integer.MAX_VALUE, 35));
        JSpinner.DateEditor editor = new JSpinner.DateEditor(spBirthDate, "dd/MM/yyyy");
        spBirthDate.setEditor(editor);
        lblBirthDateError = FormUtils.createErrorLabel();
        mainPanel.add(FormUtils.createField("Fecha de nacimiento", spBirthDate, lblBirthDateError, ""));
        
        //PAÍS
        
        String[] countryList = {"Seleccione el país", "Afganistán", "Albania", "Alemania", "Andorra", "Angola", "Antigua y Barbuda", "Arabia Saudita", "Argelia", "Argentina", "Armenia", "Australia", "Austria", "Azerbaiyán", "Bahamas", "Bangladés", "Baréin", "Bélgica", "Belice", "Benín", "Bielorrusia", "Birmania", "Bolivia", "Bosnia y Herzegovina", "Botsuana", "Brasil", "Brunéi", "Bulgaria", "Burkina Faso", "Burundi", "Bután", "Cabo Verde", "Camboya", "Camerún", "Canadá", "Catar", "Chad", "Chile", "China", "Chipre", "Colombia", "Comoras", "Corea del Norte", "Corea del Sur", "Costa de Marfil", "Costa Rica", "Croacia", "Cuba", "Dinamarca", "Dominica", "Ecuador", "Egipto", "El Salvador", "Emiratos Árabes Unidos", "Eritrea", "Eslovaquia", "Eslovenia", "España", "Estados Unidos", "Estonia", "Esuatini", "Etiopía", "Filipinas", "Finlandia", "Fiyi", "Francia", "Gabón", "Gambia", "Georgia", "Ghana", "Granada", "Grecia", "Guatemala", "Guinea", "Guinea-Bisáu", "Guinea Ecuatorial", "Guyana", "Haití", "Honduras", "Hungría", "India", "Indonesia", "Irak", "Irán", "Irlanda", "Islandia", "Islas Marshall", "Islas Salomón", "Israel", "Italia", "Jamaica", "Japón", "Jordania", "Kazajistán", "Kenia", "Kirguistán", "Kiribati", "Kuwait", "Laos", "Lesoto", "Letonia", "Líbano", "Liberia", "Libia", "Liechtenstein", "Lituania", "Luxemburgo", "Madagascar", "Malasia", "Malaui", "Maldivas", "Malí", "Malta", "Marruecos", "Mauricio", "Mauritania", "México", "Micronesia", "Moldavia", "Mónaco", "Mongolia", "Montenegro", "Mozambique", "Namibia", "Nauru", "Nepal", "Nicaragua", "Níger", "Nigeria", "Noruega", "Nueva Zelanda", "Omán", "Países Bajos", "Pakistán", "Palaos", "Palestina", "Panamá", "Papúa Nueva Guinea", "Paraguay", "Perú", "Polonia", "Portugal", "Reino Unido", "República Centroafricana", "República Checa", "República del Congo", "República Democrática del Congo", "República Dominicana", "Ruanda", "Rumania", "Rusia", "Samoa", "San Cristóbal y Nieves", "San Marino", "San Vicente y las Granadinas", "Santa Lucía", "Santo Tomé y Príncipe", "Senegal", "Serbia", "Seychelles", "Sierra Leona", "Singapur", "Siria", "Somalia", "Sri Lanka", "Sudáfrica", "Sudán", "Sudán del Sur", "Suecia", "Suiza", "Surinam", "Tailandia", "Taiwán", "Tanzania", "Tayikistán", "Timor Oriental", "Togo", "Tonga", "Trinidad y Tobago", "Túnez", "Turkmenistán", "Turquía", "Tuvalu", "Ucrania", "Uganda", "Uruguay", "Uzbekistán", "Vanuatu", "Vaticano", "Venezuela", "Vietnam", "Yemen", "Yibuti", "Zambia", "Zimbabue"};
    	comboCountry = new JComboBox<>(countryList);
    	comboCountry.setBorder(BorderFactory.createEmptyBorder(6,0,6,0));
    	comboCountry.setMaximumSize(new Dimension(Integer.MAX_VALUE, 35));
    	comboCountry.setFont(AppFont.normal());
    	lblCountryError = FormUtils.createErrorLabel();
    	mainPanel.add(FormUtils.createField("País", comboCountry, lblCountryError, ""));
        
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
        lblGenderError = FormUtils.createErrorLabel();
        mainPanel.add(FormUtils.createField("Género", genderPanel, lblGenderError, ""));
        
        //CONTRASEÑA
        
        txtPassword = FormUtils.createPasswordField();
        lblPasswordError = FormUtils.createErrorLabel();
        mainPanel.add(FormUtils.createField("Contraseña", txtPassword, lblPasswordError, "Cree una contraseña"));

        //ACEPTAR TÉRMINOS
        
        chkTerms = new JCheckBox("Acepto los términos y condiciones");
        chkTerms.setAlignmentX(Component.CENTER_ALIGNMENT);
        lblTermsError = FormUtils.createErrorLabel();
        mainPanel.add(FormUtils.createField(null, chkTerms, lblTermsError, ""));
        
        //REGRESAR EL PANEL
        return mainPanel;
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

        RoundButton btnRegistration = new RoundButton("CREAR CUENTA", new ImageIcon("src/img/login-icon.png"));
        btnRegistration.setBackground(UIColors.BUTTON);
		btnRegistration.setFont(AppFont.big());
		btnRegistration.setFocusPainted(false);
        btnRegistration.addActionListener(e -> validateRegistration());
		        
        RoundButton btnReturn = new RoundButton("REGRESAR", new ImageIcon("src/img/back-icon.png"));
        btnReturn.setBackground(UIColors.BUTTON);
        btnReturn.setFont(AppFont.big());
        btnReturn.addActionListener(e -> {

            int option = JOptionPane.showConfirmDialog(this, "¿Seguro que deseas regresar? Se perderán todos los datos");

            if (option == JOptionPane.YES_OPTION) {
                new LoginWindow();
                Window window = 
                    SwingUtilities.getWindowAncestor(this);

                if (window != null) {
                    window.dispose();
                }
            }
        });
		
		btnRegistration.setIcon(FormUtils.loadIcon("/img/login-icon.png", 30));
		
        panel.add(btnRegistration);
		panel.add(btnReturn);

        return panel;
	}	
	
	private void resetField(JLabel label, JComponent field) {
	    label.setText("");
	    field.setBorder(FormUtils.normalBorder);
	}
	
	private void resetErrorLabels() {		
		resetField(lblNameError, txtName);
		resetField(lblSurnameError, txtSurname);
		resetField(lblPasswordError, txtPassword);
		resetField(lblEmailError, txtEmail);
		resetField(lblPhoneError, txtPhone);
		resetField(lblCountryError, comboCountry);
		resetField(lblBirthDateError, spBirthDate);
		lblGenderError.setText("");
		lblTermsError.setText("");
	}
	
	private void setError(JLabel label, JComponent field, String message) {
	    label.setText(message);
	    field.setBorder(FormUtils.redBorder);
	}

	private void clearError(JLabel label, JComponent field) {
	    label.setText("");
	    field.setBorder(FormUtils.normalBorder);
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
	        Window window = 
	            SwingUtilities.getWindowAncestor(this);

	        if (window != null) {
	            window.dispose();
	        }
	    }
	}
	
	private void assignListeners() {
		comboCountry.addActionListener(e -> {
			validateCountry();
		});
		
		chkTerms.addActionListener(e -> validateTerms());
		
		rbtnMale.addActionListener(e -> validateGender());
		rbtnFemale.addActionListener(e -> validateGender());
				
		txtName.getDocument().addDocumentListener(new DocumentListener() {
			
			@Override
			public void removeUpdate(DocumentEvent e) {
				validateName();
			}
			
			@Override
			public void insertUpdate(DocumentEvent e) {
				validateName();
			}
			
			@Override
			public void changedUpdate(DocumentEvent e) {
				validateName();
			}
		});		
		
		txtSurname.getDocument().addDocumentListener(new DocumentListener() {
			
			@Override
			public void removeUpdate(DocumentEvent e) {
				validateSurname();
			}
			
			@Override
			public void insertUpdate(DocumentEvent e) {
				validateSurname();
			}
			
			@Override
			public void changedUpdate(DocumentEvent e) {
				validateSurname();
			}
		});		
		
		txtPassword.getDocument().addDocumentListener(new DocumentListener() {
			
			@Override
			public void removeUpdate(DocumentEvent e) {
				validatePassword();
			}
			
			@Override
			public void insertUpdate(DocumentEvent e) {
				validatePassword();
			}
			
			@Override
			public void changedUpdate(DocumentEvent e) {
				validatePassword();
			}
		});	
		
		txtEmail.getDocument().addDocumentListener(new DocumentListener() {
			
			@Override
			public void removeUpdate(DocumentEvent e) {
				validateEmail();
			}
			
			@Override
			public void insertUpdate(DocumentEvent e) {
				validateEmail();
			}
			
			@Override
			public void changedUpdate(DocumentEvent e) {
				validateEmail();
			}
		});		
		
		txtPhone.getDocument().addDocumentListener(new DocumentListener() {
			
			@Override
			public void removeUpdate(DocumentEvent e) {
				validatePhone();
			}
			
			@Override
			public void insertUpdate(DocumentEvent e) {
				validatePhone();
			}
			
			@Override
			public void changedUpdate(DocumentEvent e) {
				validatePhone();
			}
		});	
		
		// KEYLISTENER NOMBRE

		txtName.addKeyListener(new KeyAdapter() {
		    @Override
		    public void keyTyped(KeyEvent e) {
		        char c = e.getKeyChar();

		        if (!Character.isLetter(c) && c != ' ') {
		            e.consume();
		        }
		    }
		});

		// KEYLISTENER APELLIDOS

		txtSurname.addKeyListener(new KeyAdapter() {
		    @Override
		    public void keyTyped(KeyEvent e) {
		        char c = e.getKeyChar();

		        if (!Character.isLetter(c) && c != ' ') {
		            e.consume();
		        }
		    }
		});
		
		// KEYLISTENER TELÉFONO (solo números)

		txtPhone.addKeyListener(new KeyAdapter() {
		    @Override
		    public void keyTyped(KeyEvent e) {
		        char c = e.getKeyChar();

		        if (!Character.isDigit(c)) {
		            e.consume();
		        }
		    }
		});
		
		// KEYLISTENERS (Name, surname, email, phone, password)

		addFocusEffect(txtName);
		addFocusEffect(txtSurname);
		addFocusEffect(txtEmail);
		addFocusEffect(txtPhone);
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
	
	private boolean validateName() {
	    String name = txtName.getText().trim();

	    if (name.isEmpty()) {
	        setError(lblNameError, txtName, "El nombre es obligatorio");
	        return false;
	    }
	    
	    if (!name.matches("[a-zA-ZáéíóúÁÉÍÓÚñÑ ]+")) {
	        setError(lblNameError, txtName, "Solo se permiten letras");
	        return false;
	    }
	    
	    clearError(lblNameError, txtName);
	    return true;
	}
	
	private boolean validateSurname() {
	    String surname = txtSurname.getText().trim();

	    if (surname.isEmpty()) {			
	        setError(lblSurnameError, txtSurname, "Los apellidos son obligatorios");
	        return false;
	    }

	    if (!surname.matches("[a-zA-ZáéíóúÁÉÍÓÚñÑ ]+")) {
	        setError(lblSurnameError, txtSurname, "Solo se permiten letras");
	        return false;
	    }
	    
	    clearError(lblSurnameError, txtSurname);
	    return true;
	}
	
	private boolean validatePassword() {
	    if (new String(txtPassword.getPassword()).trim().isEmpty()) {
	        setError(lblPasswordError, txtPassword, "La contraseña es obligatoria");
	        return false;
	    }
	    
	    clearError(lblPasswordError, txtPassword);
	    return true;
	}
	
	private boolean validateEmail() {
	    String email = txtEmail.getText().trim();

	    if (email.isEmpty()) {
	        setError(lblEmailError, txtEmail, "El correo es obligatorio");
	        return false;
	    }

	    String emailRegex = "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$";

	    if (!email.matches(emailRegex)) {			
	        setError(lblEmailError, txtEmail, "Formato de correo inválido");
	        return false;
	    }
	    
	    clearError(lblEmailError, txtEmail);
	    return true;
	}
	
	private boolean validatePhone() {
	    String phone = txtPhone.getText().trim();

	    if (phone.isEmpty()) {
	        setError(lblPhoneError, txtPhone, "El teléfono es obligatorio");
	        return false;
	    }

	    if (!phone.matches("\\d+")) {
	        setError(lblPhoneError, txtPhone, "Solo se permiten números");
	        return false;
	    }

	    if (!phone.matches("\\d{10,}")) {	    	
	        setError(lblPhoneError, txtPhone, "Debe tener al menos 10 números");
	        return false;
	    }
	    
	    clearError(lblPhoneError, txtPhone);	    
	    return true;
	}
	
	private boolean validateBirthDate() {
	    if (spBirthDate.getValue() == null) {
	        setError(lblBirthDateError, spBirthDate, "La fecha de nacimiento es obligatoria");
	        return false;
	    }
	    	
	    clearError(lblBirthDateError, spBirthDate);	    
	    return true;
	}
	
	private boolean validateCountry() {
		if (comboCountry.getSelectedIndex() == 0) {
			setError(lblCountryError, comboCountry, "Debe seleccionar un país");
			return false;
		}

	    clearError(lblCountryError, comboCountry);	    
		return true;
	}
		
	private boolean validateGender() {
	    if (!rbtnMale.isSelected() && !rbtnFemale.isSelected()) {
	        lblGenderError.setText("Seleccione un género");
	        return false;
	    }
	    
	    lblGenderError.setText("");

	    return true;
	}
	
	private boolean validateTerms() {
	    if (!chkTerms.isSelected()) {
	        lblTermsError.setText("Debe aceptar los términos y condiciones");
	        return false;
	    }
	    
		lblTermsError.setText("");

	    return true;
	}
	
}