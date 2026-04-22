package views;

import java.awt.BorderLayout;
import java.awt.Component;
import java.util.Date;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.ButtonGroup;
import javax.swing.ImageIcon;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.JSpinner;
import javax.swing.JTextField;
import components.RoundButton;
import models.User;
import utils.AppFont;
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
	
	JLabel lblNameError;
	JLabel lblSurnameError;
	JLabel lblEmailError;
	JLabel lblPhoneError;
	JLabel lblCountryError;
	JLabel lblBirthDateError;
	JLabel lblGenderError;
	JLabel lblTermsError;	

    private RoundButton btnSave;
    private RoundButton btnCancel;

    private User user;
    private boolean saved = false;
    		
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

        btnSave = new RoundButton("GUARDAR",
    	    new ImageIcon(getClass().getResource("/img/button-save-icon.png")));
    	btnSave.setBackground(UIColors.BUTTON);
        btnSave.setToolTipText("Haz click para iniciar sesión");
		btnSave.setFont(AppFont.big());
		btnSave.setFocusPainted(false);

		btnCancel = new RoundButton("CANCELAR",
    	    new ImageIcon(getClass().getResource("/img/button-cancel-icon.png")));
		btnCancel.setBackground(UIColors.BUTTON);
    	btnCancel.setToolTipText("Haz click para cancelar");
        btnCancel.setFont(AppFont.big());
        btnCancel.setFocusPainted(false);

        panel.add(btnSave);
        panel.add(btnCancel);
        
        btnSave.addActionListener(e -> save());
        btnCancel.addActionListener(e -> dispose());
        
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

            if (user.getGender() == 'M') {
                rbtnMale.setSelected(true);
            } else {
                rbtnFemale.setSelected(true);
            }
        }
    }
    
    private void save() {
    	String name = txtName.getText();
    	String surname = txtSurname.getText();
    	String email = txtEmail.getText();
    	String phone = txtPhone.getText();
    	Date birthDate = (Date) spBirthDate.getValue();
        String country = (String) comboCountry.getSelectedItem();

        char gender = rbtnMale.isSelected() ? 'M' : 'F';
        
        if(user == null) {
        	user = new User(name, surname, email, phone, country, birthDate, gender);
        }else {
        	user.setName(name);
        	user.setSurname(surname);
        	user.setEmail(email);
        	user.setPhone(phone);
        	user.setCountry(country);
        	user.setBirthDate(birthDate);
            user.setGender(gender);
        }
        
        saved = true;
        dispose();
    }
    
    public boolean isSaved() {
    	return saved;
    }
    
    public User getUser() {
    	return user;
    }

	public ButtonGroup getGenderGroup() {
		return genderGroup;
	}

	public void setGenderGroup(ButtonGroup genderGroup) {
		this.genderGroup = genderGroup;
	}
}