package views;

import java.awt.BorderLayout;
import java.awt.Component;
import java.util.Date;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.ButtonGroup;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.JSpinner;
import javax.swing.JTextField;
import components.RoundButton;
import models.User;
import utils.ButtonFactory;
import utils.FormUtils;
import utils.UIColors;

@SuppressWarnings("serial")
public class UserFormDialog extends JDialog{

	private JTextField txtName;
	private JTextField txtSurname;
	private JTextField txtEmail;
	private JTextField txtPhone;
	private JSpinner spBirthDate;
	private JComboBox<String> comboCountry;
	private JRadioButton rbtnMale;
	private JRadioButton rbtnFemale;
	private ButtonGroup genderGroup;
	
    private RoundButton btnSave;
    private RoundButton btnCancel;

    private User user;
    private boolean saved = false;
	
	JLabel lblNameError;
	JLabel lblSurnameError;
	JLabel lblEmailError;
	JLabel lblPhoneError;
	JLabel lblCountryError;
	JLabel lblBirthDateError;
	JLabel lblGenderError;
    		
    public UserFormDialog(JFrame parent, User user) {
    	super(parent, true);
    	
    	this.user = user;
    	setTitle(user == null ? "Agregar usuario" : "Editar usuario");
    	
    	setSize(400, 500);
        setLocationRelativeTo(parent);
        setLayout(new BorderLayout());
        setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
        
        add(createTitlePanel(), BorderLayout.NORTH);
        add(createFormPanel());
        add(createButtonPanel(), BorderLayout.SOUTH);

        loadData();
    }
    
    private JPanel createTitlePanel() {
        JPanel panel = new JPanel();
        panel.setBackground(UIColors.CARD);
        panel.add(new JLabel("Formulario de Usuario"));
        return panel;
    }
    
    private JPanel createButtonPanel() {

        JPanel panel = new JPanel();
        panel.setBackground(UIColors.CARD);
        
        btnSave = ButtonFactory.createButton(
    	    "GUARDAR",
    	    "/img/button-save-icon.png",
    	    "Haz click para iniciar sesión"
    	);

        btnCancel = ButtonFactory.createButton(
    	    "CANCELAR",
    	    "/img/button-cancel-icon.png",
    	    "Haz click para cancelar"
    	);

        panel.add(btnSave);
        panel.add(btnCancel);
                
        return panel;
    }

    private JScrollPane createFormPanel() {

    	JPanel panel = new JPanel();
        panel.setBackground(UIColors.CARD);
        panel.setAlignmentX(Component.CENTER_ALIGNMENT);
		panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
		panel.setBorder(BorderFactory.createEmptyBorder(15, 20, 15, 20));

		JScrollPane scroll = new JScrollPane(panel);
		scroll.setBorder(null);
		scroll.setHorizontalScrollBar(null);
		scroll.getVerticalScrollBar().setUnitIncrement(14);

        //NOMBRE
        txtName = FormUtils.createTextField();
        lblNameError = FormUtils.createErrorLabel();
        panel.add(FormUtils.createField("Nombre(s)", txtName, lblNameError, "Ingrese su(s) nombre(s)"));

        //APELLIDOS
        txtSurname = FormUtils.createTextField();
        lblSurnameError = FormUtils.createErrorLabel();
        panel.add(FormUtils.createField("Apellidos", txtSurname, lblSurnameError, "Ingrese su(s) apellido(s)"));
        
        //EMAIL
	    txtEmail = FormUtils.createTextField();
        lblEmailError = FormUtils.createErrorLabel();
        panel.add(FormUtils.createField("Correo electrónico", txtEmail, lblEmailError, "Ingrese su email"));
        
        //TELÉFONO
	    txtPhone = FormUtils.createTextField();
        lblPhoneError = FormUtils.createErrorLabel();
        panel.add(FormUtils.createField("Número de teléfono", txtPhone, lblPhoneError, "Ingrese su número de teléfono"));

        //FECHA NACIMIENTO
        spBirthDate = FormUtils.createSpinner();
        lblBirthDateError = FormUtils.createErrorLabel();
        panel.add(FormUtils.createField("Fecha de nacimiento", spBirthDate, lblBirthDateError, ""));
        
        //PAÍS
        comboCountry = FormUtils.createComboCountry();
    	lblCountryError = FormUtils.createErrorLabel();
    	panel.add(FormUtils.createField("País", comboCountry, lblCountryError, ""));
        
    	//GÉNERO
    	rbtnMale = FormUtils.createRadioButton("Hombre");
    	rbtnFemale = FormUtils.createRadioButton("Mujer");
    	
    	setGenderGroup(FormUtils.createRadioGroup(rbtnMale, rbtnFemale));
    	JPanel genderPanel = FormUtils.createRadioPanel(rbtnMale, rbtnFemale);
    	
    	lblGenderError = FormUtils.createErrorLabel();
    	panel.add(FormUtils.createField("Género", genderPanel, lblGenderError, ""));

		return scroll;
    }
    
    private void loadData() {
    	if(user != null) {
    		txtName.setText(user.getName());
            txtSurname.setText(user.getSurname());
            txtEmail.setText(user.getEmail());
            txtPhone.setText(user.getPhone());
            spBirthDate.setValue(user.getBirthDate());
            comboCountry.setSelectedItem(user.getCountry());
            rbtnMale.setSelected(user.getGender() == 'M');
            rbtnFemale.setSelected(user.getGender() == 'F');
        }
    }
	
	//LABELS ERROR
	public void clearErrors() {
		clearNameError();
		clearSurnameError();
		clearEmailError();
		clearPhoneError();
		clearCountryError();
		clearBirthDateError();
		clearGenderError();
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

	// COMBOBOX
	public JComboBox<String> getComboCountry() {
	    return comboCountry;
	}
	
	//JSPINNER
	public JSpinner getSpBirthDate() {
		return spBirthDate;
	}

	// RADIO BUTTONS
	public JRadioButton getRbtnMale() {
	    return rbtnMale;
	}

	public JRadioButton getRbtnFemale() {
	    return rbtnFemale;
	}
    
    public boolean isSaved() {
    	return saved;
    }
    
    public User getUser() {
    	return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public void setSaved(boolean saved) {
        this.saved = saved;
    }
    
	public ButtonGroup getGenderGroup() {
		return genderGroup;
	}

	public void setGenderGroup(ButtonGroup genderGroup) {
		this.genderGroup = genderGroup;
	}
	
	//getters botones
	
	public RoundButton getBtnSave() {
	    return btnSave;
	}	
	
	public RoundButton getBtnCancel() {
	    return btnCancel;
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
	
	public int confirmCancel() {
	    return JOptionPane.showConfirmDialog(
	        this,
	        "¿Seguro que deseas cancelar? Se perderán todos los datos",
	        "¿Seguro?",
	        JOptionPane.YES_NO_OPTION
	    );
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
}