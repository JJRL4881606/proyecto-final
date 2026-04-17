package views;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Insets;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.Date;

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
import javax.swing.border.EmptyBorder;

import components.RoundButton;
import components.RoundedPanel;
import utils.AppFont;
import utils.FormUtils;
import utils.UIColors;

@SuppressWarnings("serial")
public class RegistrationView extends JPanel
{	
	JLabel lblNameError;
	JLabel lblSurnameError;
	JLabel lblPasswordError;
	JLabel lblEmailError;
	JLabel lblPhoneError;
	JLabel lblCountryError;
	JLabel lblBirthDateError;
	JLabel lblGenderError;
	JLabel lblTermsError;	
	
	private JTextField txtName;
	private JTextField txtSurname;
	private JTextField txtEmail;
	private JTextField txtPhone;
	private JSpinner spBirthDate;
	private ButtonGroup genderGroup;
	private JPasswordField txtPassword;
	private JComboBox<String> comboCountry;
	private JCheckBox chkTerms;
	private JRadioButton rbtnMale;
	private JRadioButton rbtnFemale;
	private RoundButton btnRegistration;
	private RoundButton btnReturn;
	    
    public RegistrationView(RegistrationWindow window) 
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

        btnRegistration = new RoundButton("CREAR CUENTA", new ImageIcon("src/img/button-login-icon.png"));
        btnRegistration.setBackground(UIColors.BUTTON);
		btnRegistration.setFont(AppFont.big());
		btnRegistration.setFocusPainted(false);
        		        
        btnReturn = new RoundButton("REGRESAR", new ImageIcon("src/img/button-back-icon.png"));
        btnReturn.setBackground(UIColors.BUTTON);
        btnReturn.setFont(AppFont.big());
        btnReturn.setFocusPainted(false);
				
        panel.add(btnRegistration);
		panel.add(btnReturn);

        return panel;
	}	
	
	private void resetField(JLabel label, JComponent field) {
	    label.setText("");
	    field.setBorder(FormUtils.normalBorder);
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
	
	//LABELS ERROR
	public void clearErrors() {
		clearLblNameError();
		clearLblSurnameError();
		clearLblEmailError();
		clearLblPasswordError();
		clearLblPhoneError();
		clearLblCountryError();
		clearLblBirthDateError();
		clearLblGenderError();
		clearLblTermsError();
	}

	public void clearLblNameError() {
	    resetField(lblNameError, txtName);
	}
	
	public void clearLblSurnameError() {
	    resetField(lblSurnameError, txtSurname);
	}
	
	public void clearLblEmailError() {
	    resetField(lblEmailError, txtEmail);
	}
	
	public void clearLblPasswordError() {
	    resetField(lblPasswordError, txtPassword);
	}
	
	public void clearLblPhoneError() {
	    resetField(lblPhoneError, txtPhone);
	}
	
	public void clearLblCountryError() {
	    resetField(lblCountryError, comboCountry);
	}
	
	public void clearLblBirthDateError() {
	    resetField(lblBirthDateError, spBirthDate);
	}
	
	public void clearLblGenderError() {
	    lblGenderError.setText("");
	}
	
	public void clearLblTermsError() {
	    lblTermsError.setText("");
	}
	
	//GETTERS
	public String getName() {
	    return txtName.getText().trim();
	}

	public String getSurname() {
	    return txtSurname.getText().trim();
	}

	public String getEmail() {
	    return txtEmail.getText().trim();
	}

	public String getPhone() {
	    return txtPhone.getText().trim();
	}

	public String getPassword() {
	    return new String(txtPassword.getPassword()).trim();
	}

	public Date getBirthDate() {
	    return (Date) spBirthDate.getValue();
	}
	
	public char getGender() {
	    if (rbtnMale.isSelected()) return 'M';
	    if (rbtnFemale.isSelected()) return 'F';
	    return ' ';
	}
	
	public String getCountry() {
		return String.valueOf(comboCountry.getSelectedItem());
	}

	public int getCountryIndex() {
	    return comboCountry.getSelectedIndex();
	}

	public boolean isMaleSelected() {
	    return rbtnMale.isSelected();
	}

	public boolean isFemaleSelected() {
	    return rbtnFemale.isSelected();
	}

	public boolean isTermsAccepted() {
	    return chkTerms.isSelected();
	}
	
	//SETTERS ERROR
	
	public void setNameError(String msg) {
	    lblNameError.setText(msg);
	    txtName.setBorder(FormUtils.redBorder);
	}

	public void setSurnameError(String msg) {
	    lblSurnameError.setText(msg);
	    txtSurname.setBorder(FormUtils.redBorder);
	}

	public void setEmailError(String msg) {
	    lblEmailError.setText(msg);
	    txtEmail.setBorder(FormUtils.redBorder);
	}

	public void setPasswordError(String msg) {
	    lblPasswordError.setText(msg);
	    txtPassword.setBorder(FormUtils.redBorder);
	}

	public void setPhoneError(String msg) {
	    lblPhoneError.setText(msg);
	    txtPhone.setBorder(FormUtils.redBorder);
	}

	public void setCountryError(String msg) {
	    lblCountryError.setText(msg);
	    comboCountry.setBorder(FormUtils.redBorder);
	}

	public void setBirthDateError(String msg) {
	    lblBirthDateError.setText(msg);
	    spBirthDate.setBorder(FormUtils.redBorder);
	}

	public void setGenderError(String msg) {
	    lblGenderError.setText(msg);
	}

	public void setTermsError(String msg) {
	    lblTermsError.setText(msg);
	    chkTerms.setBorder(FormUtils.redBorder);
	}
	
	//ASSIGN LISTENERS
	
	private void assignListeners() {

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

	    // KEYLISTENER TELÉFONO
	    txtPhone.addKeyListener(new KeyAdapter() {
	        @Override
	        public void keyTyped(KeyEvent e) {
	            char c = e.getKeyChar();
	            if (!Character.isDigit(c)) {
	                e.consume();
	            }
	        }
	    });

	    // EFECTOS VISUALES
	    addFocusEffect(txtName);
	    addFocusEffect(txtSurname);
	    addFocusEffect(txtEmail);
	    addFocusEffect(txtPhone);
	    addFocusEffect(txtPassword);
	}
	
	// TEXTFIELDS
	public JTextField getTxtName() {
	    return txtName;
	}

	public JTextField getTxtSurname() {
	    return txtSurname;
	}

	public JTextField getTxtEmail() {
	    return txtEmail;
	}

	public JTextField getTxtPhone() {
	    return txtPhone;
	}

	// PASSWORD
	public JPasswordField getTxtPassword() {
	    return txtPassword;
	}

	// COMBOBOX
	public JComboBox<String> getComboCountry() {
	    return comboCountry;
	}
	
	//JSPINNER
	public JSpinner getSpBirthDate() {
		return spBirthDate;
	}

	// CHECKBOX
	public JCheckBox getChkTerms() {
	    return chkTerms;
	}

	// RADIO BUTTONS
	public JRadioButton getRbtnMale() {
	    return rbtnMale;
	}

	public JRadioButton getRbtnFemale() {
	    return rbtnFemale;
	}

	// BOTONES
	public RoundButton getBtnRegistration() {
	    return btnRegistration;
	}
	
	public RoundButton getBtnReturn() {
	    return btnReturn;
	}
	
	public int confirmReturn() {
	    return JOptionPane.showConfirmDialog(
	        this,
	        "¿Seguro que deseas regresar? Se perderán todos los datos",
	        "¿Seguro?",
	        JOptionPane.YES_NO_OPTION
	    );
	}
}