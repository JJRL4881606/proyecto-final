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
	        view.getBtnRegistration().addActionListener(e -> registrarCuenta(valid));
	        
	        // COMBO
	        view.getComboCountry().addActionListener(e -> register());

	        // CHECKBOX
	        view.getChkTerms().addActionListener(e -> register());

	        // RADIO BUTTONS
	        view.getRbtnMale().addActionListener(e -> register());
	        view.getRbtnFemale().addActionListener(e -> register());

	        // DOCUMENT LISTENER NOMBRE
	        view.getTxtName().getDocument().addDocumentListener(new DocumentListener() {
	            public void insertUpdate(DocumentEvent e) { register(); }
	            public void removeUpdate(DocumentEvent e) { register(); }
	            public void changedUpdate(DocumentEvent e) { register(); }
	        });

	        // APELLIDO
	        view.getTxtSurname().getDocument().addDocumentListener(new DocumentListener() {
	            public void insertUpdate(DocumentEvent e) { register(); }
	            public void removeUpdate(DocumentEvent e) { register(); }
	            public void changedUpdate(DocumentEvent e) { register(); }
	        });

	        // EMAIL
	        view.getTxtEmail().getDocument().addDocumentListener(new DocumentListener() {
	            public void insertUpdate(DocumentEvent e) { register(); }
	            public void removeUpdate(DocumentEvent e) { register(); }
	            public void changedUpdate(DocumentEvent e) { register(); }
	        });

	        // TELÉFONO
	        view.getTxtPhone().getDocument().addDocumentListener(new DocumentListener() {
	            public void insertUpdate(DocumentEvent e) { register(); }
	            public void removeUpdate(DocumentEvent e) { register(); }
	            public void changedUpdate(DocumentEvent e) { register(); }
	        });

	        // PASSWORD
	        view.getTxtPassword().getDocument().addDocumentListener(new DocumentListener() {
	            public void insertUpdate(DocumentEvent e) { register(); }
	            public void removeUpdate(DocumentEvent e) { register(); }
	            public void changedUpdate(DocumentEvent e) { register(); }
	        });
	 }
	 	
	 private void register() 
	 {
		 	view.clearErrors();
		 	valid = true;
		    // NOMBRE
		    String name = view.getName();

		    if (name.isEmpty()) {
		        view.setNameError("El nombre es obligatorio");
		        this.valid = false;
		    } else if (!name.matches("[a-zA-ZáéíóúÁÉÍÓÚñÑ ]+")) {
		        view.setNameError("Solo se permiten letras");
		        valid = false;
		    }

		    // APELLIDOS
		    String surname = view.getSurname();

		    if (surname.isEmpty()) {
		        view.setSurnameError("Los apellidos son obligatorios");
		        valid = false;
		    } else if (!surname.matches("[a-zA-ZáéíóúÁÉÍÓÚñÑ ]+")) {
		        view.setSurnameError("Solo se permiten letras");
		        valid = false;
		    }

		    // PASSWORD
		    String password = view.getPassword();

		    if (password.isEmpty()) {
		        view.setPasswordError("La contraseña es obligatoria");
		        valid = false;
		    }

		    // EMAIL
		    String email = view.getEmail();

		    if (email.isEmpty()) {
		        view.setEmailError("El correo es obligatorio");
		        valid = false;
		    } else if (!email.matches("^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$")) {
		        view.setEmailError("Formato de correo inválido");
		        valid = false;
		    }

		    // TELÉFONO
		    String phone = view.getPhone();

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

		    // FECHA
		    if (view.getBirthDate() == null) {
		        view.setBirthDateError("La fecha de nacimiento es obligatoria");
		        valid = false;
		    }

		    // PAÍS
		    if (view.getCountryIndex() == 0) {
		        view.setCountryError("Debe seleccionar un país");
		        valid = false;
		    }

		    // GÉNERO
		    if (!view.isMaleSelected() && !view.isFemaleSelected()) {
		        view.setGenderError("Seleccione un género");
		        valid = false;
		    }

		    // TÉRMINOS
		    if (!view.isTermsAccepted()) {
		        view.setTermsError("Debe aceptar los términos y condiciones");
		        valid = false;
		    }
		    
	 }
	 
	 public void registrarCuenta(boolean valid) {
		 if (valid) 
		 {
	        JOptionPane.showMessageDialog(view, "Registro exitoso");
	        new MainPageWindow();
	        Window window = SwingUtilities.getWindowAncestor(view);

	        if (window != null) {
	            window.dispose();
	        }
	    }else {
	    	register();
	    }
	 }
	
	//VALIDACIONES DE LOS CAMPOS (por mejorar o implementar)
	/*private void validateRegistration() {

	    resetErrorLabels();

	    boolean valid = true;

	    if (!validateName()) valid = false;
	    if (!validateSurname()) valid = false;
	    if (!validatePassword()) valid = false;
	    if (!validateEmail()) valid = false;
	    if (!validatePhone()) valid = false;
	    if (!validateBirthDate()) valid = false;
	    if (!validateCountry()) valid = false;
	    if (!validateGender()) valid = false;
	    if (!validateTerms()) valid = false;

	    if (valid) {
	        JOptionPane.showMessageDialog(this, "Registro exitoso");
	        new MainPageWindow();
	        Window window = 
	            SwingUtilities.getWindowAncestor(this);

	        if (window != null) {
	            window.dispose();
	        }
	    }
	}*/
}
