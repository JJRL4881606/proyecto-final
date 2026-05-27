package views.account;

import java.awt.BorderLayout;
import java.awt.Component;
import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JCheckBox;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JScrollPane;

import components.RoundedButton;
import utils.ButtonFactory;
import utils.FormUtils;
import utils.UIColors;

@SuppressWarnings("serial")
public class PasswordDialog extends JDialog {

    private JPasswordField txtCurrentPassword;
    private JPasswordField txtNewPassword;
    private JPasswordField txtConfirmPassword;

    private JLabel lblCurrentError;
    private JLabel lblNewError;
    private JLabel lblConfirmError;

    private JCheckBox chkShowPassword;

    private RoundedButton btnSave;
    private RoundedButton btnCancel;

    int fieldWidth = 300;

    public PasswordDialog(JFrame parent){
        super(parent,true);

        setTitle("Cambiar contraseña");
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
        panel.add(new JLabel("Formulario cambio de contraseña"));
        return panel;
    }

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

        txtCurrentPassword = FormUtils.createPasswordField();
        lblCurrentError = FormUtils.createErrorLabel();
        panel.add(FormUtils.createField("Contraseña actual", txtCurrentPassword, lblCurrentError, "", fieldWidth));

        txtNewPassword = FormUtils.createPasswordField();
        lblNewError = FormUtils.createErrorLabel();
        panel.add(FormUtils.createField("Nueva contraseña", txtNewPassword, lblNewError, "", fieldWidth));

        txtConfirmPassword = FormUtils.createPasswordField();
        lblConfirmError = FormUtils.createErrorLabel();
        panel.add(FormUtils.createField("Confirmar contraseña", txtConfirmPassword, lblConfirmError, "", fieldWidth));

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

    public String getCurrentPassword(){
        return new String(
            txtCurrentPassword.getPassword()
        );
    }

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

    public JCheckBox getChkShowPassword(){
        return chkShowPassword;
    }

    public JPasswordField getTxtCurrentPassword(){
        return txtCurrentPassword;
    }

    public JPasswordField getTxtNewPassword(){
        return txtNewPassword;
    }

    public JPasswordField getTxtConfirmPassword(){
        return txtConfirmPassword;
    }
    
    public JLabel getLblCurrentError(){
        return lblCurrentError;
    }

    public JLabel getLblNewError(){
        return lblNewError;
    }

    public JLabel getLblConfirmError(){
        return lblConfirmError;
    }

    public void setCurrentError(String msg){
        lblCurrentError.setText(msg);
        txtCurrentPassword.setBorder(FormUtils.redBorder);
    }

    public void setNewError(String msg){
        lblNewError.setText(msg);
        txtNewPassword.setBorder(FormUtils.redBorder);
    }

    public void setConfirmError(String msg){
        lblConfirmError.setText(msg);
        txtConfirmPassword.setBorder(FormUtils.redBorder);
    }
    
    public void clearErrors(){
    	clearCurrentError();
    	clearNewError();
    	clearConfirmError();
    }
    
    public void clearCurrentError(){
        FormUtils.clearError(lblCurrentError, txtCurrentPassword);
    }

    public void clearNewError(){
        FormUtils.clearError(lblNewError, txtNewPassword);
    }

    public void clearConfirmError(){
        FormUtils.clearError(lblConfirmError, txtConfirmPassword);
    }
}