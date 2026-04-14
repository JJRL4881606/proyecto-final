package controllers;

import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

import views.LoginView;
import views.MainPageWindow;
import views.RegistrationWindow;

import java.awt.Window;
import javax.swing.SwingUtilities;

public class LoginController {

    private LoginView view;
    private boolean valid = false;

    public LoginController(LoginView view) {
        this.view = view;

        // BOTÓN LOGIN
        view.getBtnLogin().addActionListener(e -> login());

        // BOTÓN REGISTRO
        view.getBtnRegistration().addActionListener(e -> goToRegister());

        // LISTENERS (como registration)
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
        
        
    }

    public void validatePassword() {
	 	// PASSWORD
    		String password = view.getPassword();
    		view.clearLblPasswordError();
    		
        if (password.isEmpty()) {
            view.setPasswordError("La contraseña es obligatoria");
            valid = false;
        }
	 }
	 
	 public void validateEmail() {
		// EMAIL
		String email = view.getEmail();
		view.clearLblEmailError();
		 
		if (email.isEmpty()) {
		    view.setEmailError("El correo es obligatorio");
		    valid = false;
		} else if (!email.matches("^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$")) {
		    view.setEmailError("Formato inválido");
		    valid = false;
		}
	 }
    
    private void login() {
    		view.clearErrors();
    		valid = true;
    		
	    	validatePassword();
	    	validateEmail();
	    	
        if (valid) {
            // Simulación login
        	String email = view.getEmail();
        	String password = view.getPassword();

        	if (email.equals("correo@gmail.com") &&
        	    password.equals("1234")) {
        		
                JOptionPane.showMessageDialog(view, "Sesión iniciada");
                new MainPageWindow();

                Window window = SwingUtilities.getWindowAncestor(view);
                if (window != null) window.dispose();

            } else {
                view.showWrongError();
            }
        }
    }

    private void goToRegister() {
        RegistrationWindow reg = new RegistrationWindow();
        new RegistrationController(reg.getRegistrationView());

        Window window = SwingUtilities.getWindowAncestor(view);
        if (window != null) window.dispose();
    }
    
    
}