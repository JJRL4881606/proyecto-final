package controllers;

import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

import utils.FormUtils;
import utils.Validator;
import views.LoginView;
import views.MainWindow;
import views.RegistrationWindow;

import java.awt.Desktop;
import java.awt.Window;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.net.URI;

import javax.swing.SwingUtilities;

public class LoginController {

    private LoginView view;

    public LoginController(LoginView view) {
        this.view = view;
        initListeners();
		initInputRestrictions();
    }
    
    private void initListeners() {
        view.getBtnLogin().addActionListener(e -> handleLogin());

        view.getBtnRegistration().addActionListener(e -> handleRegister());

        view.getTxtEmail().getDocument().addDocumentListener(new DocumentListener() {
            public void insertUpdate(DocumentEvent e) { validateEmail(); }
            public void removeUpdate(DocumentEvent e) { validateEmail(); }
            public void changedUpdate(DocumentEvent e) { validateEmail(); }
        });

        view.getTxtPassword().getDocument().addDocumentListener(new DocumentListener() {
            public void insertUpdate(DocumentEvent e) { validatePassword(); }
            public void removeUpdate(DocumentEvent e) { validatePassword(); }
            public void changedUpdate(DocumentEvent e) { validatePassword(); }
        });
        
        view.getChkShowPassword().addActionListener(e -> {
            if (view.getChkShowPassword().isSelected()) {
                view.getTxtPassword().setEchoChar((char) 0);
            } else {
                view.getTxtPassword().setEchoChar('•');
            }
        });
        
        view.getLblForgotPassword().addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                try {
                	//esto es mientras hacemos el modulo de cambiar contraseña
                    Desktop.getDesktop().browse(new URI("https://www.google.com"));
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            }
        });

        FormUtils.addFocusEffect(view.getTxtEmail(), view.getLblEmailError());
        FormUtils.addFocusEffect(view.getTxtPassword(), view.getLblPasswordError());
    }
    
	private void initInputRestrictions() {
		Validator.noSpaces(view.getTxtEmail());
		Validator.restrictedPassword(view.getTxtPassword());
	}
    
    private void handleLogin() {
    	
    	view.clearErrors();
    	boolean valid = true;

    	if (!validateEmail()) valid = false;
    	if (!validatePassword()) valid = false;

    	if (valid) {
        	String email = view.getEmail();
        	String password = view.getPassword();

        	if (email.equals("correo@gmail.com") &&
        	    password.equals("1234")) {
        		
        		JOptionPane.showMessageDialog(
    			    null,
    			    "Sesión iniciada",
    			    "Éxito",
    			    JOptionPane.INFORMATION_MESSAGE
    			);
        		new MainWindow();

                Window window = SwingUtilities.getWindowAncestor(view);
                if (window != null) window.dispose();

            } else {
                view.setWrongError();
            }
        }
    }

    private void handleRegister() {
        RegistrationWindow reg = new RegistrationWindow();
        new RegistrationController(reg.getRegistrationView());

        Window window = SwingUtilities.getWindowAncestor(view);
        if (window != null) window.dispose();
    }
	
    public boolean validatePassword() {
        view.clearWrongError();
        String password = view.getPassword();

        if (password.isEmpty()) {
            view.setPasswordError("La contraseña es obligatoria");
            return false;
        }

        view.clearPasswordError();
        return true;
    }
    
    public boolean validateEmail() {
        view.clearWrongError();
        String email = view.getEmail();

        if (email.isEmpty()) {
            view.setEmailError("El correo es obligatorio");
            return false;

        } else if (!Validator.isValidEmail(email)) {
            view.setEmailError("Formato inválido");
            return false;
        }

        view.clearEmailError();
        return true;
    }
}