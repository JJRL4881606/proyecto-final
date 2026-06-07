package controllers.auth;

import java.io.IOException;

import javax.swing.JOptionPane;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

import models.User;
import repository.UserRepository;
import utils.FormUtils;
import utils.Validator;
import views.auth.PasswordRecoveryDialog;

//Controlador para manejar la recuperación de contraseña
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

            // Si está marcado muestra la contraseña, si no la oculta con puntos
            char passwordChar = view.getChkShowPassword().isSelected() ? (char) 0 : '•';

            view.getTxtNewPassword().setEchoChar(passwordChar);
            view.getTxtConfirmPassword().setEchoChar(passwordChar);
        });

        view.getComboCountry().addActionListener(e -> validateCountry());

        // DOCUMENT LISTENERS
        view.getTxtEmail().getDocument().addDocumentListener(new DocumentListener() {
            public void insertUpdate(DocumentEvent e) { validateEmail(); }
            public void removeUpdate(DocumentEvent e) { validateEmail(); }
            public void changedUpdate(DocumentEvent e) { validateEmail(); }
        });

        view.getTxtNewPassword().getDocument().addDocumentListener(new DocumentListener() {
            public void insertUpdate(DocumentEvent e) {
                validateNewPassword();
                validateConfirmPassword();
            }

            public void removeUpdate(DocumentEvent e) {
                validateNewPassword();
                validateConfirmPassword();
            }

            public void changedUpdate(DocumentEvent e) {
                validateNewPassword();
                validateConfirmPassword();
            }
        });

        view.getTxtConfirmPassword().getDocument().addDocumentListener(new DocumentListener() {
            public void insertUpdate(DocumentEvent e) { validateConfirmPassword(); }
            public void removeUpdate(DocumentEvent e) { validateConfirmPassword(); }
            public void changedUpdate(DocumentEvent e) { validateConfirmPassword(); }
        });

        FormUtils.addFocusEffect(view.getTxtEmail(), view.getLblEmailError());
        FormUtils.addFocusEffect(view.getTxtNewPassword(), view.getLblNewError());
        FormUtils.addFocusEffect(view.getTxtConfirmPassword(), view.getLblConfirmError());
    }
    
    // Restringir datos inválidos en los campos
    private void initInputRestrictions() {
        Validator.noSpaces(view.getTxtEmail());
    }

    // Método para recuperar la contraseña de un usuario
    private void recoverPassword() {

        view.clearErrors();

        if (!validateForm()) return;

        String email = view.getEmail();
        String country = view.getCountry();
        String newPassword = view.getNewPassword();

        // Buscar el usuario
        User user = findUser(email, country);

        if(user == null){
            view.setEmailError(
                "No se encontró usuario con esos datos"
            );
            return;
        }

        // Actualizar la contraseña
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
    
    // Buscar un usuario por correo y país
    private User findUser(String email, String country) {
        try {
        	
        	// Revisar todos los usuarios registrados
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

    // Validar todos los campos del formulario
    private boolean validateForm() {
        view.clearErrors();
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

    // Checar que ambas contraseñas coincidan
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