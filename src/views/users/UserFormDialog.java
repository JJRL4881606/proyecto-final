package views.users;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Dimension;
import java.util.Date;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.ButtonGroup;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.JSpinner;
import javax.swing.JTextField;
import components.RoundedButton;
import models.Role;
import models.User;
import utils.ButtonFactory;
import utils.FormUtils;
import utils.UIColors;

@SuppressWarnings("serial")

//dialog para crear o editar un usuario
//Si se pasa null crea uno nuevo, si se pasa un usuario existente lo edita
//En modo crear aparece tmbn el campo de contraseña con su checkbox de mostrar/ocultar
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
	private JComboBox<String> comboRole;
	private JPasswordField txtPassword;
	private JCheckBox chkShowPassword;
	
    private RoundedButton btnSave;
    private RoundedButton btnCancel;

    private User user;
    
    // indica si el usuario guardó o cerró sin guardar
    private boolean saved = false;
	
    private JLabel lblNameError;
	private JLabel lblSurnameError;
	private JLabel lblEmailError;
	private JLabel lblPhoneError;
	private JLabel lblCountryError;
	private JLabel lblBirthDateError;
	private JLabel lblGenderError;
	private JLabel lblRoleError;
	private JLabel lblPasswordError;
	
	private final int fieldWidth = 300;
    		
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
        
        btnSave = ButtonFactory.createGoldButton(
    	    "GUARDAR",
    	    "/assets/img/btn-icons/button-save-icon.png",
    	    "Haz click para iniciar sesión"
    	);

        btnCancel = ButtonFactory.createGoldButton(
    	    "CANCELAR",
    	    "/assets/img/btn-icons/button-cancel-icon.png",
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
        panel.add(FormUtils.createField("Nombre(s)", txtName, lblNameError, "Ingrese su(s) nombre(s)", fieldWidth));

        //APELLIDOS
        txtSurname = FormUtils.createTextField();
        lblSurnameError = FormUtils.createErrorLabel();
        panel.add(FormUtils.createField("Apellidos", txtSurname, lblSurnameError, "Ingrese su(s) apellido(s)", fieldWidth));
        
        //PASSWORD solo visible al crear un usuario nuevo

        if(user == null) {
            txtPassword = FormUtils.createPasswordField();
            lblPasswordError = FormUtils.createErrorLabel();
            panel.add(FormUtils.createField("Contraseña", txtPassword, lblPasswordError, "Ingrese una contraseña", fieldWidth));
            
    	    chkShowPassword = FormUtils.createCheckBox();
    	    panel.add(chkShowPassword);
    	    panel.add(Box.createRigidArea(new Dimension(0, 20)));
    	}
        
        //EMAIL
	    txtEmail = FormUtils.createTextField();
        lblEmailError = FormUtils.createErrorLabel();
        panel.add(FormUtils.createField("Correo electrónico", txtEmail, lblEmailError, "Ingrese su email", fieldWidth));
        
        //TELÉFONO
	    txtPhone = FormUtils.createTextField();
        lblPhoneError = FormUtils.createErrorLabel();
        panel.add(FormUtils.createField("Número de teléfono", txtPhone, lblPhoneError, "Ingrese su número de teléfono", fieldWidth));

        //FECHA NACIMIENTO
        spBirthDate = FormUtils.createDateField();
        lblBirthDateError = FormUtils.createErrorLabel();
        panel.add(FormUtils.createField("Fecha de nacimiento", spBirthDate, lblBirthDateError, "", fieldWidth));
        
        //PAÍS
        comboCountry = FormUtils.createComboCountry();
    	lblCountryError = FormUtils.createErrorLabel();
    	panel.add(FormUtils.createField("País", comboCountry, lblCountryError, "", fieldWidth));
        
    	//GÉNERO, agrupar radiobutons
    	rbtnMale = FormUtils.createRadioButton("Hombre");
    	rbtnFemale = FormUtils.createRadioButton("Mujer");
    	    	
    	setGenderGroup(FormUtils.createRadioGroup(rbtnMale, rbtnFemale));
    	JPanel genderPanel = FormUtils.createRadioPanel(rbtnMale, rbtnFemale);
    	
    	lblGenderError = FormUtils.createErrorLabel();
    	panel.add(FormUtils.createField("Género", genderPanel, lblGenderError, "", fieldWidth));
    	
    	//ROL
        String[] roles = {"Seleccione un rol", Role.CUSTOMER, Role.ADMIN};
    	comboRole = FormUtils.createCombo(roles);
    	lblRoleError = FormUtils.createErrorLabel();
    	panel.add(FormUtils.createField("Rol", comboRole, lblRoleError, "", fieldWidth));

		return scroll;
    }
    
    // Rellena todos los campos con los datos del usuario existente, solo al editar
    // El genero se selecciona comparando el char guardado con M o F
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
            comboRole.setSelectedItem(user.getRole());
        }
    }
    
	public int confirmCancel() {
	    return JOptionPane.showConfirmDialog(
	        null,
	        "¿Seguro que deseas cancelar? Se perderán todos los datos",
	        "¿Seguro?",
	        JOptionPane.YES_NO_OPTION
	    );
	}
	
	//LIMPIAR LABELS ERROR
    // el de contraseña solo si el campo existe , en elmodo de crear
	public void clearErrors() {
		clearNameError();
		clearSurnameError();
		
	    if(txtPassword != null) {
	    	clearPasswordError();
	    }

		clearEmailError();
		clearPhoneError();
		clearCountryError();
		clearBirthDateError();
		clearGenderError();
		clearRoleError();
	}

	public void clearNameError(){
	    FormUtils.clearError(lblNameError, txtName);
	}

	public void clearSurnameError(){
		FormUtils.clearError(lblSurnameError, txtSurname);
	}

	public void clearPasswordError() {
	    if(txtPassword != null) {
	        FormUtils.clearError(lblPasswordError, txtPassword);
	    }
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
	
	public void clearRoleError(){
		FormUtils.clearError(lblRoleError, comboRole);
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
	
	public String getRole() {
		return String.valueOf(comboRole.getSelectedItem());
	}
	
	public int getRoleIndex() {
	    return comboRole.getSelectedIndex();
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

	public JPasswordField getTxtPassword() {
	    return txtPassword;
	}

	// COMBOBOX
	public JComboBox<String> getComboCountry() {
	    return comboCountry;
	}
	
	public JComboBox<String> getComboRole() {
	    return comboRole;
	}
	
	//JSPINNER
	public JSpinner getSpBirthDate() {
		return spBirthDate;
	}
	
	//CHECKBOX
	public JCheckBox getChkShowPassword() {
		return chkShowPassword;
	}

	// RADIO BUTTONS
	public JRadioButton getRbtnMale() {
	    return rbtnMale;
	}

	public JRadioButton getRbtnFemale() {
	    return rbtnFemale;
	}
        
	public ButtonGroup getGenderGroup() {
		return genderGroup;
	}

	public void setGenderGroup(ButtonGroup genderGroup) {
		this.genderGroup = genderGroup;
	}
	
	//user
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
	
	//getters botones
	public RoundedButton getBtnSave() {
	    return btnSave;
	}	
	
	public RoundedButton getBtnCancel() {
	    return btnCancel;
	}
	
	//SETTERS ERRORES
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
	
	public void setRoleError(String msg) {
	    lblRoleError.setText(msg);
	    comboRole.setBorder(FormUtils.redBorder);
	}
		
	//getters labels errores
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