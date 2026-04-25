package views;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Insets;
import java.awt.Component;
import java.awt.Dimension;
import java.util.Date;

import javax.swing.BoxLayout;
import javax.swing.ButtonGroup;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JRadioButton;
import javax.swing.JSpinner;
import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;

import components.RoundButton;
import components.RoundedPanel;
import utils.AppFont;
import utils.ButtonFactory;
import utils.FormUtils;
import utils.UIColors;

@SuppressWarnings("serial")
public class RegistrationView extends JPanel
{		
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
	
	JLabel lblNameError;
	JLabel lblSurnameError;
	JLabel lblPasswordError;
	JLabel lblEmailError;
	JLabel lblPhoneError;
	JLabel lblCountryError;
	JLabel lblBirthDateError;
	JLabel lblGenderError;
	JLabel lblTermsError;	
	    
    public RegistrationView(RegistrationWindow window) 
    {
		this.setBackground(UIColors.BACKGROUND); 
	    setLayout(new GridBagLayout());
	    initializeComponents();
        setVisible(true);
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
        spBirthDate = FormUtils.createSpinner();
        lblBirthDateError = FormUtils.createErrorLabel();
        mainPanel.add(FormUtils.createField("Fecha de nacimiento", spBirthDate, lblBirthDateError, ""));
        
        //PAÍS
        comboCountry = FormUtils.createComboCountry();
    	lblCountryError = FormUtils.createErrorLabel();
    	mainPanel.add(FormUtils.createField("País", comboCountry, lblCountryError, ""));
        
    	//GÉNERO
    	rbtnMale = FormUtils.createRadioButton("Hombre");
    	rbtnFemale = FormUtils.createRadioButton("Mujer");
    	
    	setGenderGroup(FormUtils.createRadioGroup(rbtnMale, rbtnFemale));
    	JPanel genderPanel = FormUtils.createRadioPanel(rbtnMale, rbtnFemale);
    	
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
        
        btnRegistration = ButtonFactory.createButton(
    	    "CREAR CUENTA",
    	    "/img/button-save-icon.png",
    	    "Haz click para registrarte"
    	);

        btnReturn = ButtonFactory.createButton(
    	    "REGRESAR",
    	    "/img/button-back-icon.png",
    	    "Haz click para regresar al login"
    	);
				
        panel.add(btnRegistration);
		panel.add(btnReturn);

        return panel;
	}	
	
	//LABELS ERROR
	public void clearErrors() {
		clearNameError();
		clearSurnameError();
		clearEmailError();
		clearPasswordError();
		clearPhoneError();
		clearCountryError();
		clearBirthDateError();
		clearGenderError();
		clearTermsError();
	}

	public void clearNameError(){
	    FormUtils.clearError(lblNameError, txtName);
	}

	public void clearSurnameError(){
		FormUtils.clearError(lblSurnameError, txtSurname);
	}

	public void clearEmailError(){
		FormUtils.clearError(lblEmailError, txtEmail);
	}

	public void clearPasswordError(){
		FormUtils.clearError(lblPasswordError, txtPassword);
	}

	public void clearPhoneError(){
		FormUtils.clearError(lblPhoneError, txtPhone);
	}

	public void clearCountryError(){
		FormUtils.clearError(lblCountryError, comboCountry);
	}

	public void clearBirthDateError(){
		FormUtils.clearError(lblBirthDateError, spBirthDate);
	}

	public void clearGenderError(){
		FormUtils.clearLabel(lblGenderError);
	}

	public void clearTermsError(){
		FormUtils.clearLabel(lblTermsError);
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

	public ButtonGroup getGenderGroup() {
		return genderGroup;
	}

	public void setGenderGroup(ButtonGroup genderGroup) {
		this.genderGroup = genderGroup;
	}

	public JLabel getLblNameError() {
		return lblNameError;
	}

	public JLabel getLblSurnameError() {
		return lblSurnameError;
	}

	public JLabel getLblEmailError() {
		return lblEmailError;
	}

	public JLabel getLblPhoneError() {
		return lblPhoneError;
	}

	public JLabel getLblPasswordError() {
		return lblPasswordError;
	}
}