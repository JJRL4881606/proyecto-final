package controllers;

import java.awt.Window;

import javax.swing.JOptionPane;
import javax.swing.SwingUtilities;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import views.MainPageWindow;
import views.RegistrationView;

public class RegistrationController {

	 private RegistrationView view;
	 boolean valid = false;
	 
	 public RegistrationController(RegistrationView view) {
	        this.view = view;
	        
	        //BOTON
	        view.getBtnRegistration().addActionListener(e -> registrarCuenta());
	        
	        // COMBO
	        view.getComboCountry().addActionListener(e -> validateCountry());

	        // CHECKBOX
	        view.getChkTerms().addActionListener(e -> validateTerms());

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

	        // TELÉFONO
	        view.getTxtPhone().getDocument().addDocumentListener(new DocumentListener() {
	            public void insertUpdate(DocumentEvent e) { validatePhone(); }
	            public void removeUpdate(DocumentEvent e) { validatePhone(); }
	            public void changedUpdate(DocumentEvent e) { validatePhone(); }
	        });

	        // PASSWORD
	        view.getTxtPassword().getDocument().addDocumentListener(new DocumentListener() {
	            public void insertUpdate(DocumentEvent e) { validatePassword(); }
	            public void removeUpdate(DocumentEvent e) { validatePassword(); }
	            public void changedUpdate(DocumentEvent e) { validatePassword(); }
	        });
	 }
	 
	 private void registrarCuenta() {

		 view.clearErrors();
		 valid = true;

		 validateName();
		 validateSurname();
		 validatePassword();
		 validateEmail();
		 validatePhone();
		 validateBirthDate();
		 validateCountry();
		 validateGender();
		 validateTerms();

		 if (valid) 
		 {
	        JOptionPane.showMessageDialog(view, "Registro exitoso");
	        new MainPageWindow();
	        Window window = SwingUtilities.getWindowAncestor(view);

	        if (window != null) {
	            window.dispose();
	        }
	    }
	 }
	
	 public void validateName() {
		 	// NOMBRE
		    String name = view.getName();
		    view.clearLblNameError();
		    
		    if (name.isEmpty()) {
		        view.setNameError("El nombre es obligatorio");
		        this.valid = false;
		    } else if (!name.matches("[a-zA-ZáéíóúÁÉÍÓÚñÑ ]+")) {
		        view.setNameError("Solo se permiten letras");
		        valid = false;
		    }
	 }
	 
	 public void validateSurname() {
		 	// APELLIDOS
		    String surname = view.getSurname();
		    view.clearLblSurnameError();

		    if (surname.isEmpty()) {
		        view.setSurnameError("Los apellidos son obligatorios");
		        valid = false;
		    } else if (!surname.matches("[a-zA-ZáéíóúÁÉÍÓÚñÑ ]+")) {
		        view.setSurnameError("Solo se permiten letras");
		        valid = false;
		    }
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
		        view.setEmailError("Formato de correo inválido");
		        valid = false;
		    }
	 }
	 
	 public void validatePhone() {
		 	// TELÉFONO
		    String phone = view.getPhone();
		    view.clearLblPhoneError();

		    if (phone.isEmpty()) {
		        view.setPhoneError("El teléfono es obligatorio");
		        valid = false;
		    } else if (!phone.matches("\\d+")) {
		        view.setPhoneError("Solo se permiten números");
		        valid = false;
		    } else if (!phone.matches("\\d{10,}")) {
		        view.setPhoneError("Debe tener al menos 10 números");
		        valid = false;
		    }
	 }
	 
	 public void validateBirthDate() {
		    // FECHA
		 	view.clearLblBirthDateError();
		 	
		    if (view.getBirthDate() == null) {
		        view.setBirthDateError("La fecha de nacimiento es obligatoria");
		        valid = false;
		    }
	 }
	 
	 public void validateCountry() {
		 	// PAÍS
			view.clearLblCountryError();
			
		    if (view.getCountryIndex() == 0) {
		        view.setCountryError("Debe seleccionar un país");
		        valid = false;
		    }
	 }
	 
	 public void validateGender() {
		 	// GÉNERO
		 	view.clearLblGenderError();
		 	
		    if (!view.isMaleSelected() && !view.isFemaleSelected()) {
		        view.setGenderError("Seleccione un género");
		        valid = false;
		    }
	 }
	 
	 public void validateTerms() {
		 	// TÉRMINOS
		 	view.clearLblTermsError();
		 
		    if (!view.isTermsAccepted()) {
		        view.setTermsError("Debe aceptar los términos y condiciones");
		        valid = false;
		    }
	 }
	
}
