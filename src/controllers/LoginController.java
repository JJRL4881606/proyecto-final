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
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.net.URI;

import javax.swing.SwingUtilities;

public class LoginController {

    private LoginView view;
    private boolean valid = false;

    public LoginController(LoginView view) {
        this.view = view;
        addListeners();
    }

    public void validatePassword() {
        view.clearWrongError();
		String password = view.getPassword();
		view.clearPasswordError();
    		
        if (password.isEmpty()) {
            view.setPasswordError("La contraseña es obligatoria");
            valid = false;
        }
	 }
	 
	 public void validateEmail() {
		view.clearWrongError();
		String email = view.getEmail();
		view.clearEmailError();
		 
		if (email.isEmpty()) {
		    view.setEmailError("El correo es obligatorio");
		    valid = false;
	    } else if (!Validator.isValidEmail(email)) {
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
        	String email = view.getEmail();
        	String password = view.getPassword();

        	if (email.equals("correo@gmail.com") &&
        	    password.equals("1234")) {
        		
                JOptionPane.showMessageDialog(view, "Sesión iniciada");
                new MainWindow();

                Window window = SwingUtilities.getWindowAncestor(view);
                if (window != null) window.dispose();

            } else {
                view.setWrongError();
            }
        }
    }

    private void goToRegister() {
        RegistrationWindow reg = new RegistrationWindow();
        new RegistrationController(reg.getRegistrationView());

        Window window = SwingUtilities.getWindowAncestor(view);
        if (window != null) window.dispose();
    }
    
    private void addListeners() {
        view.getBtnLogin().addActionListener(e -> login());

        view.getBtnRegistration().addActionListener(e -> goToRegister());

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
        
        view.getTxtEmail().addKeyListener(new KeyAdapter() {
            @Override
            public void keyTyped(KeyEvent e) {
                if (Character.isWhitespace(e.getKeyChar())) {
                    e.consume();
                }
            }
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
                    Desktop.getDesktop().browse(new URI("https://www.google.com"));
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            }
        });

        FormUtils.addFocusEffect(view.getTxtEmail(), view.getLblEmailError());
        FormUtils.addFocusEffect(view.getTxtPassword(), view.getLblPasswordError());
    }
}