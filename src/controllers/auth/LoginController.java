package controllers.auth;

import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

import models.User;

import javax.swing.SwingUtilities;

import utils.FormUtils;
import utils.Validator;
import utils.Session;
import views.auth.LoginView;
import views.auth.PasswordRecoveryDialog;
import views.auth.RegistrationWindow;
import views.main.MainWindow;

import java.awt.Window;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import repository.LoginRepository;

public class LoginController {

    private final LoginView view;
    private final LoginRepository repository;
    
	public LoginController(LoginView view) {
	    repository = new LoginRepository();
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
            public void mouseClicked(MouseEvent e) {
            	
                PasswordRecoveryDialog dialog = new PasswordRecoveryDialog(null);
                new PasswordRecoveryController(dialog);
                dialog.setVisible(true);
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

        if (!valid) {
            return;
        }
        
    	String email = view.getEmail();
    	String password = view.getPassword();

    	User user = repository.login(email, password);
    	
        if (user == null) {
            view.setWrongError();
            return;
        }

        Session.login(user);
        new MainWindow(user);

        Window window = SwingUtilities.getWindowAncestor(view);
        if (window != null) {
            window.dispose();
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