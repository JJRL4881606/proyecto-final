package views.account;

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
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.JSpinner;
import javax.swing.JTextField;
import components.RoundedButton;
import models.User;
import utils.ButtonFactory;
import utils.FormUtils;
import utils.UIColors;

@SuppressWarnings("serial")
public class AccountEditDialog extends JDialog {

	private JTextField txtName;
	private JTextField txtSurname;
	private JTextField txtEmail;
	private JTextField txtPhone;
	private JSpinner spBirthDate;
	private JComboBox<String> comboCountry;
	private JRadioButton rbtnMale;
	private JRadioButton rbtnFemale;
	private ButtonGroup genderGroup;
	private RoundedButton btnSave;
	private RoundedButton btnCancel;
	private JLabel lblNameError;
	private JLabel lblSurnameError;
	private JLabel lblEmailError;
	private JLabel lblPhoneError;
	private JLabel lblCountryError;
	private JLabel lblBirthDateError;
	private JLabel lblGenderError;
	private User user;
	int fieldWidth = 300;

	public AccountEditDialog(JFrame parent, User user) {
		super(parent, true);
		this.user = user;
		
		setTitle("Editar perfil");
		setSize(450, 600);
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
        panel.add(new JLabel("Formulario de mi cuenta"));
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

		txtName = FormUtils.createTextField();
		lblNameError = FormUtils.createErrorLabel();
		panel.add(FormUtils.createField("Nombre", txtName, lblNameError, "Ingrese nombre", fieldWidth));

		txtSurname = FormUtils.createTextField();
		lblSurnameError = FormUtils.createErrorLabel();
		panel.add(FormUtils.createField("Apellidos", txtSurname, lblSurnameError, "Ingrese apellidos", fieldWidth));

		txtEmail = FormUtils.createTextField();
		lblEmailError = FormUtils.createErrorLabel();
		panel.add(FormUtils.createField("Correo", txtEmail, lblEmailError, "Ingrese correo", fieldWidth));

		txtPhone = FormUtils.createTextField();
		lblPhoneError = FormUtils.createErrorLabel();
		panel.add(FormUtils.createField("Teléfono", txtPhone, lblPhoneError, "Ingrese teléfono", fieldWidth));

		spBirthDate = FormUtils.createDateField();
		lblBirthDateError = FormUtils.createErrorLabel();
		panel.add(FormUtils.createField("Nacimiento", spBirthDate, lblBirthDateError, "", fieldWidth));

		comboCountry = FormUtils.createComboCountry();
		lblCountryError = FormUtils.createErrorLabel();
		panel.add(FormUtils.createField("País", comboCountry, lblCountryError, "", fieldWidth));

		rbtnMale = FormUtils.createRadioButton("Hombre");
		rbtnFemale = FormUtils.createRadioButton("Mujer");
		genderGroup = FormUtils.createRadioGroup(rbtnMale, rbtnFemale);
		JPanel genderPanel = FormUtils.createRadioPanel(rbtnMale, rbtnFemale);
		lblGenderError = FormUtils.createErrorLabel();
		panel.add(FormUtils.createField("Género", genderPanel, lblGenderError, "", fieldWidth));

		return scroll;
	}

	private JPanel createButtonPanel() {
		JPanel panel = new JPanel();
		btnSave = ButtonFactory.createGoldButton("GUARDAR", "/assets/img/btn-icons/button-save-icon.png", "Guardar");
		btnCancel = ButtonFactory.createGoldButton("CANCELAR", "/assets/img/btn-icons/button-cancel-icon.png", "Cancelar");
		panel.add(btnSave);
		panel.add(btnCancel);
		return panel;
	}

	private void loadData() {
		txtName.setText(user.getName());
		txtSurname.setText(user.getSurname());
		txtEmail.setText(user.getEmail());
		txtPhone.setText(user.getPhone());
		spBirthDate.setValue(user.getBirthDate());
		comboCountry.setSelectedItem(user.getCountry());
		rbtnMale.setSelected(user.getGender() == 'M');
		rbtnFemale.setSelected(user.getGender() == 'F');
	}

	public User getUser() { return user; }
	public String getName() { return txtName.getText(); }
	public String getSurname() { return txtSurname.getText(); }
	public String getEmail() { return txtEmail.getText(); }
	public String getPhone() { return txtPhone.getText(); }
	public Date getBirthDate() { return (Date) spBirthDate.getValue(); }
	public String getCountry() { return String.valueOf(comboCountry.getSelectedItem()); }

	public char getGender() {
		if (rbtnMale.isSelected()) return 'M';
		if (rbtnFemale.isSelected()) return 'F';
		return ' ';
	}

	public RoundedButton getBtnSave() { return btnSave; }
	public RoundedButton getBtnCancel() { return btnCancel; }
	
	// LIMPIAR TODOS LOS ERRORES
	public void clearErrors(){
	    clearNameError(); 
	    clearSurnameError(); 
	    clearEmailError(); 
	    clearPhoneError();
	    clearCountryError();
	    clearBirthDateError(); 
	    clearGenderError();
	}

	public void clearNameError(){ FormUtils.clearError(lblNameError, txtName); }
	public void clearSurnameError(){ FormUtils.clearError(lblSurnameError, txtSurname); }
	public void clearEmailError(){ FormUtils.clearError(lblEmailError, txtEmail); }
	public void clearPhoneError(){ FormUtils.clearError(lblPhoneError, txtPhone); }
	public void clearCountryError(){ FormUtils.clearError(lblCountryError, comboCountry); }
	public void clearBirthDateError(){ FormUtils.clearError(lblBirthDateError, spBirthDate); }
	public void clearGenderError(){ FormUtils.clearLabel(lblGenderError); }

	public void setNameError(String msg){ lblNameError.setText(msg); txtName.setBorder(FormUtils.redBorder); }
	public void setSurnameError(String msg){ lblSurnameError.setText(msg); txtSurname.setBorder(FormUtils.redBorder); }
	public void setEmailError(String msg){ lblEmailError.setText(msg); txtEmail.setBorder(FormUtils.redBorder); }
	public void setPhoneError(String msg){ lblPhoneError.setText(msg); txtPhone.setBorder(FormUtils.redBorder); }
	public void setCountryError(String msg){ lblCountryError.setText(msg); comboCountry.setBorder(FormUtils.redBorder); }
	public void setBirthDateError(String msg){ lblBirthDateError.setText(msg); spBirthDate.setBorder(FormUtils.redBorder); }
	public void setGenderError(String msg){ lblGenderError.setText(msg); }

	public JLabel getLblNameError(){ return lblNameError; }
	public JLabel getLblSurnameError(){ return lblSurnameError; }
	public JLabel getLblEmailError(){ return lblEmailError; }
	public JLabel getLblPhoneError(){ return lblPhoneError; }

	public JTextField getTxtName(){ return txtName; }
	public JTextField getTxtSurname(){ return txtSurname; }
	public JTextField getTxtEmail(){ return txtEmail; }
	public JTextField getTxtPhone(){ return txtPhone; }
	public JComboBox<String> getComboCountry(){ return comboCountry; }
	public JSpinner getSpBirthDate(){ return spBirthDate; }
	public JRadioButton getRbtnMale(){ return rbtnMale; }
	public JRadioButton getRbtnFemale(){ return rbtnFemale; }

	public boolean isMaleSelected(){ return rbtnMale.isSelected(); }
	public boolean isFemaleSelected(){ return rbtnFemale.isSelected(); }
	public int getCountryIndex(){ return comboCountry.getSelectedIndex(); }
}