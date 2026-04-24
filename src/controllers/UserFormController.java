package controllers;

import javax.swing.JOptionPane;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

import models.User;
import views.UserFormDialog;

public class UserFormController {
	
	 private UserFormDialog view;
	 public UserFormController(UserFormDialog view) {
		 this.view = view;
		 registerListeners();
	 }
	 
	 private void registerListeners(){
		//BOTONES
		 view.getBtnSave().addActionListener(e -> {

		    if (!validateForm()) {
		        return;
		    }

		    User user = view.getUser();

		    if (user == null) {
		        user = new User(
		            view.getName(),
		            view.getSurname(),
		            view.getEmail(),
		            view.getPhone(),
		            view.getCountry(),
		            view.getBirthDate(),
		            view.getGender()
		        );
		    } else {
		        user.setName(view.getName());
		        user.setSurname(view.getSurname());
		        user.setEmail(view.getEmail());
		        user.setPhone(view.getPhone());
		        user.setCountry(view.getCountry());
		        user.setBirthDate(view.getBirthDate());
		        user.setGender(view.getGender());
		    }

		    view.setSaved(true);
		    view.setUser(user);
		    view.dispose();
		});
        
        view.getBtnCancel().addActionListener(e -> {

            int option = view.confirmCancel();

            if (option == JOptionPane.YES_OPTION) {
                if (view != null) {
                    view.dispose();
                }
            }
        });
        
        // COMBO
        view.getComboCountry().addActionListener(e -> validateCountry());

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
        
        // TELÉFONO
        view.getTxtPhone().getDocument().addDocumentListener(new DocumentListener() {
            public void insertUpdate(DocumentEvent e) { validatePhone(); }
            public void removeUpdate(DocumentEvent e) { validatePhone(); }
            public void changedUpdate(DocumentEvent e) { validatePhone(); }
        });
	 }
	 
	 private boolean validateForm(){
        view.clearErrors();
        boolean valid=true;

        if(!validateName()) valid=false;
        if(!validateSurname()) valid=false;
        if(!validateEmail()) valid=false;
        if(!validatePhone()) valid=false;
        if(!validateCountry()) valid=false;
        if(!validateGender()) valid=false;
        if(!validateBirthDate()) valid=false;

		return valid;
	 }
	
	 public boolean validateName() {
	    String name = view.getName();
	    
	    if (name.isEmpty()) {
	        view.setNameError("El nombre es obligatorio");
	        return false;
	    } else if (!name.matches("[a-zA-ZáéíóúÁÉÍÓÚñÑ ]+")) {
	        view.setNameError("Solo se permiten letras");
	        return false;
	    }
	    
	    view.clearLblNameError();
		return true;
	 }
	 
	 
	 public boolean validateSurname() {
	    String surname = view.getSurname();

	    if (surname.isEmpty()) {
	        view.setSurnameError("Los apellidos son obligatorios");
	        return false;
	    } else if (!surname.matches("[a-zA-ZáéíóúÁÉÍÓÚñÑ ]+")) {
	        view.setSurnameError("Solo se permiten letras");
	        return false;
	    }
	    
	    view.clearLblSurnameError();
		return true;
	 }
	 
	 public boolean validateEmail() {
	    String email = view.getEmail();

	    if (email.isEmpty()) {
	        view.setEmailError("El correo es obligatorio");
	        return false;
	    } else if (!email.matches("^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$")) {
	        view.setEmailError("Formato de correo inválido");
	        return false;
	    }
	    view.clearLblEmailError();
		return true;
	 }
	 
	 public boolean validatePhone() {
	    String phone = view.getPhone();

	    if (phone.isEmpty()) {
	        view.setPhoneError("El teléfono es obligatorio");
	        return false;
	    } else if (!phone.matches("\\d+")) {
	        view.setPhoneError("Solo se permiten números");
	        return false;
	    } else if (!phone.matches("\\d{10}")) {
	        view.setPhoneError("Debe tener 10 números");
	        return false;
	    }
	    view.clearLblPhoneError();
		return true;
	 }
	 
	 public boolean validateBirthDate() {
	    if (view.getBirthDate() == null) {
	        view.setBirthDateError("La fecha de nacimiento es obligatoria");
	        return false;
	    }
	 	view.clearLblBirthDateError();
		return true;
	 }
	 
	 public boolean validateCountry() {
	    if (view.getCountryIndex() == 0) {
	        view.setCountryError("Debe seleccionar un país");
	        return false;
	    }
		view.clearLblCountryError();
		return true;
	 }
	 
	 public boolean validateGender() {
	    if (!view.isMaleSelected() && !view.isFemaleSelected()) {
	        view.setGenderError("Seleccione un género");
	        return false;
	    }
	 	view.clearLblGenderError();
		return true;
	 }
}
