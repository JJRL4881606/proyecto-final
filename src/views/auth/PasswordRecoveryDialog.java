package views.auth;

import java.awt.*;
import javax.swing.*;

import components.RoundedButton;
import utils.FormUtils;
import utils.ButtonFactory;
import utils.UIColors;

@SuppressWarnings("serial")

// Dialog para recuperar contrasena de la cuenta
public class PasswordRecoveryDialog extends JDialog {

    private JTextField txtEmail;
    private JComboBox<String> comboCountry;

    private JPasswordField txtNewPassword;
    private JPasswordField txtConfirmPassword;

    private JLabel lblEmailError;
    private JLabel lblCountryError;
    private JLabel lblNewError;
    private JLabel lblConfirmError;
    
    private JCheckBox chkShowPassword;

    private RoundedButton btnSave;
    private RoundedButton btnCancel;

    private static final int FIELD_WIDTH = 300;
    
    public PasswordRecoveryDialog(JFrame parent){
        super(parent, true); //para bloquear la ventana principal cuando estas en el dialog

        setTitle("Recuperar contraseña");
		setSize(450, 600);
        setLocationRelativeTo(parent);
        setLayout(new BorderLayout());
        setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
        
        add(createTitlePanel(), BorderLayout.NORTH);
        add(createFormPanel());
        add(createButtonPanel(), BorderLayout.SOUTH);
    }	
    
    private JPanel createTitlePanel() {
        JPanel panel = new JPanel();
        panel.setBackground(UIColors.CARD);
        panel.add(new JLabel("Recuperación de contraseña"));
        return panel;
    }

    //en el formulario se pide email y país como forma de control
    private JScrollPane createFormPanel(){
    	JPanel panel = new JPanel();
        panel.setBackground(UIColors.CARD);
        panel.setAlignmentX(Component.CENTER_ALIGNMENT);
		panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
		panel.setBorder(BorderFactory.createEmptyBorder(15, 20, 15, 20));

		JScrollPane scroll = new JScrollPane(panel);
		scroll.setBorder(null);
		scroll.setHorizontalScrollBar(null);
		scroll.getVerticalScrollBar().setUnitIncrement(14);

		txtEmail = FormUtils.createTextField();
		lblEmailError = FormUtils.createErrorLabel();
		panel.add(FormUtils.createField("Correo", txtEmail, lblEmailError, "Ingrese el correo", FIELD_WIDTH));

		comboCountry = FormUtils.createComboCountry();
		lblCountryError = FormUtils.createErrorLabel();
		panel.add(FormUtils.createField("País", comboCountry, lblCountryError, "Ingrese el país", FIELD_WIDTH));

        txtNewPassword = FormUtils.createPasswordField();
        lblNewError = FormUtils.createErrorLabel();
        panel.add(FormUtils.createField("Nueva contraseña", txtNewPassword, lblNewError, "Ingrese la nueva contraseña", FIELD_WIDTH));

        txtConfirmPassword = FormUtils.createPasswordField();
        lblConfirmError = FormUtils.createErrorLabel();
        panel.add(FormUtils.createField("Confirmar contraseña", txtConfirmPassword, lblConfirmError, "Confirme la nueva contraseña", FIELD_WIDTH));

        chkShowPassword = FormUtils.createCheckBox();
        panel.add(chkShowPassword);

        return scroll;
    }

	private JPanel createButtonPanel() {
		JPanel panel = new JPanel();
		btnSave = ButtonFactory.createGoldButton("CAMBIAR", "/assets/img/btn-icons/button-save-icon.png", "Cambiar");
		btnCancel = ButtonFactory.createGoldButton("CANCELAR", "/assets/img/btn-icons/button-cancel-icon.png", "Cancelar");
		panel.add(btnSave);
		panel.add(btnCancel);
		return panel;
	}
	
	//limpiar errores
    public void clearErrors(){
	    clearEmailError(); 
	    clearCountryError(); 
	    clearNewError(); 
	    clearConfirmError(); 
    }

    // GETTERS
	
	//campos
	public JTextField getTxtEmail(){ return txtEmail; }

	public JComboBox<String> getComboCountry(){ return comboCountry; }

    public JPasswordField getTxtNewPassword(){
        return txtNewPassword;
    }

    public JPasswordField getTxtConfirmPassword(){
        return txtConfirmPassword;
    }

	//datos
    public String getEmail(){ return txtEmail.getText().trim(); }
	public String getCountry() { return String.valueOf(comboCountry.getSelectedItem()); }
	
    public String getNewPassword(){
        return new String(
            txtNewPassword.getPassword()
        );
    }

    public String getConfirmPassword(){
        return new String(
            txtConfirmPassword.getPassword()
        );
    }

    public RoundedButton getBtnSave(){ return btnSave; }
    public RoundedButton getBtnCancel(){ return btnCancel; }

    public JCheckBox getChkShowPassword(){ return chkShowPassword; }

    // SETTERS DE ERRORES
	public void setEmailError(String msg){ lblEmailError.setText(msg); txtEmail.setBorder(FormUtils.redBorder); }
	public void setCountryError(String msg){ lblCountryError.setText(msg); comboCountry.setBorder(FormUtils.redBorder); }

    public void setNewError(String msg){
        lblNewError.setText(msg);
        txtNewPassword.setBorder(FormUtils.redBorder);
    }

    public void setConfirmError(String msg){
        lblConfirmError.setText(msg);
        txtConfirmPassword.setBorder(FormUtils.redBorder);
    }
    
    // limpiar errores
    public void clearEmailError(){
        FormUtils.clearError(lblEmailError, txtEmail);
    }
    
    public void clearCountryError(){
        FormUtils.clearError(lblCountryError, comboCountry);
    }
    
    public void clearNewError(){
        FormUtils.clearError(lblNewError, txtNewPassword);
    }

    public void clearConfirmError(){
        FormUtils.clearError(lblConfirmError, txtConfirmPassword);
    }
    
    //getters de labels de error
	public JLabel getLblEmailError(){
		return lblEmailError; 
	}
	
	public JLabel getLblCountryError(){
		return lblCountryError; 
	}

    public JLabel getLblNewError(){
        return lblNewError;
    }

    public JLabel getLblConfirmError(){
        return lblConfirmError;
    }

}