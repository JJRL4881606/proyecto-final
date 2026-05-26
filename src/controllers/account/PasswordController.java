package controllers.account;

import javax.swing.JOptionPane;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

import models.User;
import repository.UserRepository;
import utils.FormUtils;
import utils.PasswordUtils;
import utils.Session;
import views.account.PasswordDialog;

public class PasswordController {

    private final PasswordDialog view;
    private final UserRepository repository;

    public PasswordController(PasswordDialog view) {
        this.view = view;
        this.repository = new UserRepository();
        initListeners();
    }

    private void initListeners() {
        view.getBtnCancel().addActionListener(e -> view.dispose());
        view.getBtnSave().addActionListener(e -> save());

        view.getChkShowPassword().addActionListener(e -> {

            char passwordChar =
                view.getChkShowPassword().isSelected()
                ? (char)0
                : '•';

            view.getTxtCurrentPassword()
                .setEchoChar(passwordChar);

            view.getTxtNewPassword()
                .setEchoChar(passwordChar);

            view.getTxtConfirmPassword()
                .setEchoChar(passwordChar);
        });
        
        view.getTxtCurrentPassword().getDocument().addDocumentListener(
    	    new DocumentListener() {

    	    public void insertUpdate(DocumentEvent e){
    	        validateCurrentPassword();
    	    }

    	    public void removeUpdate(DocumentEvent e){
    	        validateCurrentPassword();
    	    }

    	    public void changedUpdate(DocumentEvent e){
    	        validateCurrentPassword();
    	    }
    	});

    	view.getTxtNewPassword().getDocument().addDocumentListener(
    	    new DocumentListener() {

    	    public void insertUpdate(DocumentEvent e){
    	        validateNewPassword();
    	    }

    	    public void removeUpdate(DocumentEvent e){
    	        validateNewPassword();
    	    }

    	    public void changedUpdate(DocumentEvent e){
    	        validateNewPassword();
    	    }
    	});

    	view.getTxtConfirmPassword().getDocument().addDocumentListener(
    	    new DocumentListener() {

    	    public void insertUpdate(DocumentEvent e){
    	        validateConfirmPassword();
    	    }

    	    public void removeUpdate(DocumentEvent e){
    	        validateConfirmPassword();
    	    }

    	    public void changedUpdate(DocumentEvent e){
    	        validateConfirmPassword();
    	    }
    	});
    	
    	FormUtils.addFocusEffect(
		    view.getTxtCurrentPassword(),
		    view.getLblCurrentError()
		);

		FormUtils.addFocusEffect(
		    view.getTxtNewPassword(),
		    view.getLblNewError()
		);

		FormUtils.addFocusEffect(
		    view.getTxtConfirmPassword(),
		    view.getLblConfirmError()
		);
    }
    
    private boolean validateForm(){

        boolean valid=true;

        if(!validateCurrentPassword()) valid=false;
        if(!validateNewPassword()) valid=false;
        if(!validateConfirmPassword()) valid=false;

        return valid;
    }

    private void save() {

    	view.clearErrors();

    	if(!validateForm()){
    	    return;
    	}
    	
        String current = view.getCurrentPassword();
        String newPassword = view.getNewPassword();
        String confirm = view.getConfirmPassword();

        User user = Session.getCurrentUser();

        if(current.isBlank()){
            view.setCurrentError("Campo obligatorio");
            return;
        }

        if(newPassword.length()<8){
            view.setNewError("Mínimo 8 caracteres");
            return;
        }

        if(!newPassword.equals(confirm)){
            view.setConfirmError("Las contraseñas no coinciden");
            return;
        }

        boolean correct = PasswordUtils.checkPassword(
            current,
            user.getPassword()
        );

        if(!correct){
            view.setCurrentError("Contraseña incorrecta");
            return;
        }

        repository.updatePassword(
            user.getId(),
            newPassword
        );

        user.setPassword(PasswordUtils.hashPassword(newPassword));

        Session.login(user);

        JOptionPane.showMessageDialog(
            null,
            "Contraseña actualizada"
        );

        view.dispose();
    }
    
    public boolean validateCurrentPassword(){

        String current =
            view.getCurrentPassword();

        if(current.isBlank()){

            view.setCurrentError(
                "Campo obligatorio"
            );

            return false;
        }

        User user =
            Session.getCurrentUser();

        boolean correct =
            PasswordUtils.checkPassword(
                current,
                user.getPassword()
            );

        if(!correct){

            view.setCurrentError(
                "Contraseña incorrecta"
            );

            return false;
        }

        view.clearCurrentError();

        return true;
    }
    
    public boolean validateNewPassword(){

        String password =
            view.getNewPassword();

        if(password.isBlank()){

            view.setNewError(
                "Campo obligatorio"
            );

            return false;
        }

        if(password.length()<8){

            view.setNewError(
                "Mínimo 8 caracteres"
            );

            return false;
        }

        view.clearNewError();

        return true;
    }
    
    public boolean validateConfirmPassword(){

        String confirm =
            view.getConfirmPassword();

        if(confirm.isBlank()){

            view.setConfirmError(
                "Campo obligatorio"
            );

            return false;
        }

        if(!confirm.equals(
            view.getNewPassword()
        )){

            view.setConfirmError(
                "Las contraseñas no coinciden"
            );

            return false;
        }

        view.clearConfirmError();

        return true;
    }
}