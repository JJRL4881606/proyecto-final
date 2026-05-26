package views.account;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Dimension;

import javax.swing.BorderFactory;
import javax.swing.Box;
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

    int fieldWidth=300;

    public PasswordDialog(JFrame parent){
        super(parent,true);

        setTitle("Cambiar contraseña");
        setSize(450,500);
        setLocationRelativeTo(parent);
        setLayout(new BorderLayout());

        add(createFormPanel());
        add(createButtons(),BorderLayout.SOUTH);
    }

    private JScrollPane createFormPanel(){

        JPanel panel=new JPanel();

        panel.setBackground(UIColors.CARD);
        panel.setLayout(new BoxLayout(panel,BoxLayout.Y_AXIS));
        panel.setBorder(
            BorderFactory.createEmptyBorder(
                20,25,20,25
            )
        );

        JScrollPane scroll=new JScrollPane(panel);

        scroll.setBorder(null);

        JLabel title=new JLabel("Cambiar contraseña");
        title.setAlignmentX(Component.CENTER_ALIGNMENT);

        panel.add(title);
        panel.add(Box.createRigidArea(
            new Dimension(0,25)
        ));

        txtCurrentPassword=
            FormUtils.createPasswordField();

        lblCurrentError=
            FormUtils.createErrorLabel();

        panel.add(
            FormUtils.createField(
                "Contraseña actual",
                txtCurrentPassword,
                lblCurrentError,
                "",
                fieldWidth
            )
        );

        txtNewPassword=
            FormUtils.createPasswordField();

        lblNewError=
            FormUtils.createErrorLabel();

        panel.add(
            FormUtils.createField(
                "Nueva contraseña",
                txtNewPassword,
                lblNewError,
                "",
                fieldWidth
            )
        );

        txtConfirmPassword=
            FormUtils.createPasswordField();

        lblConfirmError=
            FormUtils.createErrorLabel();

        panel.add(
            FormUtils.createField(
                "Confirmar contraseña",
                txtConfirmPassword,
                lblConfirmError,
                "",
                fieldWidth
            )
        );

        chkShowPassword=
            FormUtils.createCheckBox();

        panel.add(chkShowPassword);

        return scroll;
    }

    private JPanel createButtons(){

        JPanel panel=new JPanel();

        btnSave=
            ButtonFactory.createGoldButton(
                "GUARDAR",
                "/assets/img/btn-icons/button-save-icon.png",
                ""
            );

        btnCancel=
            ButtonFactory.createGoldButton(
                "CANCELAR",
                "/assets/img/btn-icons/button-cancel-icon.png",
                ""
            );

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
        txtCurrentPassword.setBorder(
            FormUtils.redBorder
        );
    }

    public void setNewError(String msg){
        lblNewError.setText(msg);
        txtNewPassword.setBorder(
            FormUtils.redBorder
        );
    }

    public void setConfirmError(String msg){
        lblConfirmError.setText(msg);
        txtConfirmPassword.setBorder(
            FormUtils.redBorder
        );
    }
    
    public void clearErrors(){

        FormUtils.clearError(
            lblCurrentError,
            txtCurrentPassword
        );

        FormUtils.clearError(
            lblNewError,
            txtNewPassword
        );

        FormUtils.clearError(
            lblConfirmError,
            txtConfirmPassword
        );
    }
    
    public void clearCurrentError(){
        FormUtils.clearError(
            lblCurrentError,
            txtCurrentPassword
        );
    }

    public void clearNewError(){
        FormUtils.clearError(
            lblNewError,
            txtNewPassword
        );
    }

    public void clearConfirmError(){
        FormUtils.clearError(
            lblConfirmError,
            txtConfirmPassword
        );
    }
}