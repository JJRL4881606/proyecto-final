package controllers;

import java.awt.Window;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.io.IOException;

import javax.swing.JOptionPane;
import javax.swing.SwingUtilities;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

import models.User;
import repository.UserRepository;
import utils.FormUtils;
import utils.Validator;
import views.LoginWindow;
import views.RegistrationView;
import views.HomeWindow;

public class RegistrationController {

	 private RegistrationView view;
	 private UserRepository repository;
	 
	 public RegistrationController(RegistrationView view) {
        this.view = view;
        this.repository = new UserRepository();
        registerListeners();
        registerInputRestrictions();
	 }
	 
	 private void registerListeners(){
		//BOTONES
        view.getBtnRegistration().addActionListener(e -> {

            if(validateForm()){

                User user = new User(
                        view.getName(),
                        view.getSurname(),
                        view.getEmail(),
                        view.getPhone(),
                        view.getCountry(),
                        view.getBirthDate(),
                        view.getGender()
                );
                
                registerUser(user);
                
                HomeWindow window = new HomeWindow();
                new HomeController(window.getHomeView());
                
                Window w = SwingUtilities.getWindowAncestor(view);
                if (w != null) {
                    w.dispose();
                }
            }
        });
        
        view.getBtnReturn().addActionListener(e -> {

            int option = view.confirmReturn();

            if (option == JOptionPane.YES_OPTION) {
                new LoginWindow();
    	        Window window = SwingUtilities.getWindowAncestor(view);

                if (window != null) {
                    window.dispose();
                }
            }
        });
        
        // COMBO
        view.getComboCountry().addActionListener(e -> validateCountry());

        // CHECKBOX
        view.getChkTerms().addActionListener(e -> validateTerms());

        // JSPINNER
        view.getSpBirthDate().addChangeListener(e -> validateBirthDate());
        
        // RADIO BUTTONS
        view.getRbtnMale().addActionListener(e -> validateGender());
        view.getRbtnFemale().addActionListener(e -> validateGender());

        // DOCUMENT LISTENER NOMBRE
        view.getTxtName().getDocument().addDocumentListener(new DocumentListener() {
            public void insertUpdate(DocumentEvent e) { validateName(); }
            public void removeUpdate(DocumentEvent e) { validateName(); }
            public void changedUpdate(DocumentEvent e) { validateName(); }
        });

        // APELLIDO
        view.getTxtSurname().getDocument().addDocumentListener(new DocumentListener() {
            public void insertUpdate(DocumentEvent e) { validateSurname(); }
            public void removeUpdate(DocumentEvent e) { validateSurname(); }
            public void changedUpdate(DocumentEvent e) { validateSurname(); }
        });

        // EMAIL
        view.getTxtEmail().getDocument().addDocumentListener(new DocumentListener() {
            public void insertUpdate(DocumentEvent e) { validateEmail(); }
            public void removeUpdate(DocumentEvent e) { validateEmail(); }
            public void changedUpdate(DocumentEvent e) { validateEmail(); }
        });
        
        // PASSWORD
        view.getTxtPassword().getDocument().addDocumentListener(new DocumentListener() {
            public void insertUpdate(DocumentEvent e) { validatePassword(); }
            public void removeUpdate(DocumentEvent e) { validatePassword(); }
            public void changedUpdate(DocumentEvent e) { validatePassword(); }
        });


        // TELÉFONO
        view.getTxtPhone().getDocument().addDocumentListener(new DocumentListener() {
            public void insertUpdate(DocumentEvent e) { validatePhone(); }
            public void removeUpdate(DocumentEvent e) { validatePhone(); }
            public void changedUpdate(DocumentEvent e) { validatePhone(); }
        });
	 }
	 
	 private void registerUser(User user) {
    	try {
    		repository.save(user);
    		
    		JOptionPane.showMessageDialog(view, "Usuario registrado");
    		
    	}catch(IOException e) {
    		JOptionPane.showMessageDialog(view, e.getMessage());
    	}
	 }
	 
	 private void registerInputRestrictions() {

	    view.getTxtName().addKeyListener(new KeyAdapter() {
	        @Override
	        public void keyTyped(KeyEvent e) {
	            char c = e.getKeyChar();

	            if (!Character.isLetter(c) && c != ' ') {
	                e.consume();
	            }
	        }
	    });

	    view.getTxtSurname().addKeyListener(new KeyAdapter() {
	        @Override
	        public void keyTyped(KeyEvent e) {
	            char c = e.getKeyChar();

	            if (!Character.isLetter(c) && c != ' ') {
	                e.consume();
	            }
	        }
	    });

	    view.getTxtPhone().addKeyListener(new KeyAdapter() {
	        @Override
	        public void keyTyped(KeyEvent e) {
	            char c = e.getKeyChar();

	            if (!Character.isDigit(c)) {
	                e.consume();
	            }
	        }
	    });

	    FormUtils.addFocusEffect(view.getTxtName(), view.getLblNameError());
	    FormUtils.addFocusEffect(view.getTxtSurname(), view.getLblSurnameError());
	    FormUtils.addFocusEffect(view.getTxtEmail(), view.getLblEmailError());
	    FormUtils.addFocusEffect(view.getTxtPhone(), view.getLblPhoneError());
	    FormUtils.addFocusEffect(view.getTxtPassword(), view.getLblPasswordError());
	 }
	 
	 private boolean validateForm(){
        view.clearErrors();
        boolean valid=true;

        if(!validateName()) valid=false;
        if(!validateSurname()) valid=false;
        if(!validateEmail()) valid=false;
        if(!validatePassword()) valid = false;
        if(!validatePhone()) valid=false;
        if(!validateCountry()) valid=false;
        if(!validateGender()) valid=false;
        if(!validateBirthDate()) valid=false;
        if(!validateTerms()) valid=false;

		return valid;
	 }
	
	 public boolean validateName() {
	 	// NOMBRE
	    String name = view.getName();
	    
	    if (name.isEmpty()) {
	        view.setNameError("El nombre es obligatorio");
	        return false;
	    } else if (!Validator.isValidName(name)) {
	        view.setNameError("Solo se permiten letras");
	        return false;
	    }
	    
	    view.clearEmailError();
		return true;
	 }
	 
	 public boolean validateSurname() {
	 	// APELLIDOS
	    String surname = view.getSurname();

	    if (surname.isEmpty()) {
	        view.setSurnameError("Los apellidos son obligatorios");
	        return false;
	    } else if (!Validator.isValidName(surname)) {
	        view.setSurnameError("Solo se permiten letras");
	        return false;
	    }
	    
	    view.clearSurnameError();
		return true;
	 }
	 
	 public boolean validatePassword() {
	    String password = view.getPassword();

	    if (password.isEmpty()) {
	        view.setPasswordError("La contraseña es obligatoria");
	        return false;
	    } 
	    else if (password.length() < 8) {
	        view.setPasswordError("Debe tener mínimo 8 caracteres");
	        return false;
	    }

	    view.clearPasswordError();
	    return true;
	}
	 
	 public boolean validateEmail() {
	 	// EMAIL
	    String email = view.getEmail();

	    if (email.isEmpty()) {
	        view.setEmailError("El correo es obligatorio");
	        return false;
	    } else if (!Validator.isValidEmail(email)) {
	        view.setEmailError("Formato de correo inválido");
	        return false;
	    }
	    view.clearEmailError();
		return true;
	 }
	 
	 public boolean validatePhone() {
	 	// TELÉFONO
	    String phone = view.getPhone();

	    if (phone.isEmpty()) {
	        view.setPhoneError("El teléfono es obligatorio");
	        return false;
	    } else if (!Validator.isValidPhone(phone)) {
	        view.setPhoneError("Debe contener exactamente 10 números");
	        return false;
	    }
	    view.clearPhoneError();
		return true;
	 }
	 
	 public boolean validateBirthDate() {
	    // FECHA
	    if (view.getBirthDate() == null) {
	        view.setBirthDateError("La fecha de nacimiento es obligatoria");
	        return false;
	    }
	 	view.clearBirthDateError();
		return true;
	 }
	 
	 public boolean validateCountry() {
	 	// PAÍS
	    if (view.getCountryIndex() == 0) {
	        view.setCountryError("Debe seleccionar un país");
	        return false;
	    }
		view.clearCountryError();
		return true;
	 }
	 
	 public boolean validateGender() {
	 	// GÉNERO
	    if (!view.isMaleSelected() && !view.isFemaleSelected()) {
	        view.setGenderError("Seleccione un género");
	        return false;
	    }
	 	view.clearGenderError();
		return true;
	 }
	 
	 public boolean validateTerms() {
	 	// TÉRMINOS
	    if (!view.isTermsAccepted()) {
	        view.setTermsError("Debe aceptar los términos y condiciones");
	        return false;
	    }
	 	view.clearTermsError();
		return true;
	 }
}