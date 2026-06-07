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

//Controlador encargado de la lógica del inicio de sesión
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
    	
    	// Evento para iniciar sesión
        view.getBtnLogin().addActionListener(e -> handleLogin());

        // Evento para abrir el formulario de registro
        view.getBtnRegistration().addActionListener(e -> handleRegister());

        // Validación en tiempo real del correo
        view.getTxtEmail().getDocument().addDocumentListener(new DocumentListener() {
            public void insertUpdate(DocumentEvent e) { validateEmail(); }
            public void removeUpdate(DocumentEvent e) { validateEmail(); }
            public void changedUpdate(DocumentEvent e) { validateEmail(); }
        });

        // Validación en tiempo real de la contraseña
        view.getTxtPassword().getDocument().addDocumentListener(new DocumentListener() {
            public void insertUpdate(DocumentEvent e) { validatePassword(); }
            public void removeUpdate(DocumentEvent e) { validatePassword(); }
            public void changedUpdate(DocumentEvent e) { validatePassword(); }
        });
        
        // Mostrar / ocultar la contrasena
        view.getChkShowPassword().addActionListener(e -> {
            if (view.getChkShowPassword().isSelected()) {
                view.getTxtPassword().setEchoChar((char) 0);
            } else {
                view.getTxtPassword().setEchoChar('•');
            }
        });
        
        // Abrir dialog de recuperación de contraseña
        view.getLblForgotPassword().addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e) {
            	
                PasswordRecoveryDialog dialog = new PasswordRecoveryDialog(null);
                new PasswordRecoveryController(dialog);
                dialog.setVisible(true);
            }
        });

        // Efectos visuales al seleccionar los campos
        FormUtils.addFocusEffect(view.getTxtEmail(), view.getLblEmailError());
        FormUtils.addFocusEffect(view.getTxtPassword(), view.getLblPasswordError());
    }
    
    // restricciones de entrada para los campos
	private void initInputRestrictions() {
		Validator.noSpaces(view.getTxtEmail());
		Validator.restrictedPassword(view.getTxtPassword());
	}
	
	// Procesa el intento de inicio de sesión
    private void handleLogin() {
    	view.clearErrors();
    	boolean valid = true;

    	// Validar los datos ingresados
    	if (!validateEmail()) valid = false;
    	if (!validatePassword()) valid = false;

        if (!valid) {
            return;
        }
        
    	String email = view.getEmail();
    	String password = view.getPassword();

    	// Verificar las credenciales en la bd
    	User user = repository.login(email, password);
    	
        if (user == null) {
            view.setWrongError();
            return;
        }

        // Guardar la sesión del usuario y abrir la ventana principal
        Session.login(user);
        new MainWindow(user);

        // Cerrar la ventana de inicio de sesión
        Window window = SwingUtilities.getWindowAncestor(view);
        if (window != null) {
            window.dispose();
        }
    }

    // Abre la ventana de registro de usuarios
    private void handleRegister() {
        RegistrationWindow reg = new RegistrationWindow();
        new RegistrationController(reg.getRegistrationView());

        // Cerrar la ventana actual
        Window window = SwingUtilities.getWindowAncestor(view);
        if (window != null) window.dispose();
    }
	
    // Valida que la contraseña haya sido ingresada
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
    
    // Valida que el correo tenga un formato correcto
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