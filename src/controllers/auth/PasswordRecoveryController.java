package controllers.auth;

import java.io.IOException;

import javax.swing.JOptionPane;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.text.JTextComponent;

import models.User;
import repository.UserRepository;
import utils.FormUtils;
import utils.Validator;
import views.auth.PasswordRecoveryDialog;

public class PasswordRecoveryController {

    private final PasswordRecoveryDialog view;
    private final UserRepository repository;

    public PasswordRecoveryController(PasswordRecoveryDialog view) {
        this.view = view;
        this.repository = new UserRepository();
        initListeners();
        initInputRestrictions();
    }

    private void initListeners() {

        view.getBtnCancel().addActionListener(e -> view.dispose());
        view.getBtnSave().addActionListener(e -> recoverPassword());

        view.getChkShowPassword().addActionListener(e -> {
            char passwordChar = view.getChkShowPassword().isSelected() ? (char)0 : '•';

            view.getTxtNewPassword().setEchoChar(passwordChar);
            view.getTxtConfirmPassword().setEchoChar(passwordChar);
        });
        
        view.getComboCountry().addActionListener(e -> validateCountry());

        addDocumentListener(view.getTxtEmail(), this::validateEmail);
        addDocumentListener(
    	    view.getTxtNewPassword(),
    	    () -> {
    	        validateNewPassword();
    	        validateConfirmPassword();
    	    }
    	);
        addDocumentListener(view.getTxtConfirmPassword(), this::validateConfirmPassword);

        FormUtils.addFocusEffect(view.getTxtEmail(), view.getLblEmailError());
        FormUtils.addFocusEffect(view.getTxtNewPassword(), view.getLblNewError());
        
        
        FormUtils.addFocusEffect(view.getTxtConfirmPassword(), view.getLblConfirmError());
    }

    private void addDocumentListener(JTextComponent textComponent, Runnable validationMethod) {
        textComponent.getDocument().addDocumentListener(new DocumentListener() {
            public void insertUpdate(DocumentEvent e) { validationMethod.run(); }
            public void removeUpdate(DocumentEvent e) { validationMethod.run(); }
            public void changedUpdate(DocumentEvent e) { validationMethod.run(); }
        });
    }
    
    private void initInputRestrictions() {
        Validator.noSpaces(view.getTxtEmail());
    }

    private void recoverPassword() {

        view.clearErrors();

        if (!validateForm()) return;

        String email = view.getEmail();
        String country = view.getCountry();
        String newPassword = view.getNewPassword();

        User user = findUser(email, country);

        if(user == null){
            view.setEmailError(
                "No se encontró usuario con esos datos"
            );
            return;
        }

        repository.updatePassword(
            user.getId(),
            newPassword
        );

        JOptionPane.showMessageDialog(
            null,
            "Contraseña actualizada correctamente"
        );

        view.dispose();
    }
    
    private User findUser(String email, String country) {
        try {
            for (User u : repository.getUsers()) {
                if (u.getEmail().equalsIgnoreCase(email) && u.getCountry().equalsIgnoreCase(country)) {
                    return u;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }

    private boolean validateForm() {

        boolean valid = true;

        if (!validateEmail()) valid = false;
        if (!validateCountry()) valid = false;
        if (!validateNewPassword()) valid = false;
        if (!validateConfirmPassword()) valid = false;

        return valid;
    }

    private boolean validateEmail(){
        String email = view.getEmail();

        if(email.isBlank()){
            view.setEmailError("Campo obligatorio");
            return false;
        }

        if(!Validator.isValidEmail(email)){
            view.setEmailError("Correo inválido");
            return false;
        }

        view.clearEmailError();

        return true;
    }
    
    private boolean validateCountry(){
        if(view.getComboCountry().getSelectedIndex()==0){
            view.setCountryError("Seleccione un país");
            return false;
        }

        view.clearCountryError();

        return true;
    }

    private boolean validateNewPassword(){
        String pass = view.getNewPassword();

        if(pass.isBlank()){
            view.setNewError("Campo obligatorio");
            return false;
        }

        if(pass.length()<8){
            view.setNewError("Mínimo 8 caracteres");
            return false;
        }

        view.clearNewError();
        return true;
    }

    private boolean validateConfirmPassword(){
        String confirm = view.getConfirmPassword();

        if(confirm.isBlank()){
            view.setConfirmError("Campo obligatorio");
            return false;
        }

        if(!confirm.equals(view.getNewPassword())){
            view.setConfirmError("No coinciden");
            return false;
        }

        view.clearConfirmError();
        return true;
    }
}