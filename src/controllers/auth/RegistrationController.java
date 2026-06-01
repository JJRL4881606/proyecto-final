package controllers.auth;

import java.awt.Window;
import java.io.IOException;
import java.time.LocalDate;
import java.time.Period;
import java.util.Date;

import javax.swing.JOptionPane;
import javax.swing.SwingUtilities;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

import exceptions.DuplicateEmailException;
import models.Role;
import models.User;
import repository.UserRepository;
import utils.FormUtils;
import utils.Session;
import utils.Validator;
import views.auth.LoginWindow;
import views.auth.RegistrationView;
import views.main.MainWindow;

public class RegistrationController {

	private RegistrationView view;
	private UserRepository repository;
	 
	public RegistrationController(RegistrationView view) {
	    this.view = view;
	    this.repository = new UserRepository();
	    initListeners();
	    initInputRestrictions();
	}
	 
	private void initListeners(){
		//BOTONES
        view.getBtnRegistration().addActionListener(e -> { handleBtnRegistration(); });
        view.getBtnReturn().addActionListener(e -> { handleReturn(); });
        
        view.getComboCountry().addActionListener(e -> validateCountry() );
        view.getChkTerms().addActionListener(e -> validateTerms() );
        view.getSpBirthDate().addChangeListener(e -> validateBirthDate() );
        view.getRbtnMale().addActionListener(e -> validateGender() );
        view.getRbtnFemale().addActionListener(e -> validateGender() );

        // DOCUMENT LISTENERS
        view.getTxtName().getDocument().addDocumentListener(new DocumentListener() {
            public void insertUpdate(DocumentEvent e) { validateName(); }
            public void removeUpdate(DocumentEvent e) { validateName(); }
            public void changedUpdate(DocumentEvent e) { validateName(); }
        });

        view.getTxtSurname().getDocument().addDocumentListener(new DocumentListener() {
            public void insertUpdate(DocumentEvent e) { validateSurname(); }
            public void removeUpdate(DocumentEvent e) { validateSurname(); }
            public void changedUpdate(DocumentEvent e) { validateSurname(); }
        });

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

        view.getTxtPhone().getDocument().addDocumentListener(new DocumentListener() {
            public void insertUpdate(DocumentEvent e) { validatePhone(); }
            public void removeUpdate(DocumentEvent e) { validatePhone(); }
            public void changedUpdate(DocumentEvent e) { validatePhone(); }
        });
        
        view.getChkShowPassword().addActionListener(e -> {
            if (view.getChkShowPassword().isSelected()) {
                view.getTxtPassword().setEchoChar((char) 0);
            } else {
                view.getTxtPassword().setEchoChar('•');
            }
        });
        
	    FormUtils.addFocusEffect(view.getTxtName(), view.getLblNameError());
	    FormUtils.addFocusEffect(view.getTxtSurname(), view.getLblSurnameError());
	    FormUtils.addFocusEffect(view.getTxtEmail(), view.getLblEmailError());
	    FormUtils.addFocusEffect(view.getTxtPhone(), view.getLblPhoneError());
	    FormUtils.addFocusEffect(view.getTxtPassword(), view.getLblPasswordError());
	}
	
	private void initInputRestrictions() {
		Validator.onlyLetters(view.getTxtName());
		Validator.onlyLetters(view.getTxtSurname());
		Validator.onlyPhoneNumbers(view.getTxtPhone());
		Validator.noSpaces(view.getTxtEmail());
		FormUtils.onlyDateNumbers(view.getSpBirthDate());
	}
	
	private void handleBtnRegistration(){
        if(validateForm()){

        	User user = new User(
    		    view.getName(),
    		    view.getSurname(),
    		    view.getPassword(),
    		    view.getEmail(),
    		    view.getPhone(),
    		    view.getCountry(),
    		    view.getBirthDate(),
    		    view.getGender(),
    		    Role.CUSTOMER
    		);
    	
        	if(handleRegisterUser(user)) {

        	    Session.login(user);

        	    new MainWindow(user);

        	    Window w = SwingUtilities.getWindowAncestor(view);
        	    if (w != null) {
        	        w.dispose();
        	    }
        	}
        }
	}
	
	private boolean handleRegisterUser(User user) {
	    try {
	        repository.save(user);

	        JOptionPane.showMessageDialog(
	            null,
	            "Usuario registrado",
	            "Éxito",
	            JOptionPane.INFORMATION_MESSAGE
	        );
	        return true;

	    } catch (Exception e) {
	        JOptionPane.showMessageDialog(
	            null,
	            e.getMessage(),
	            "Error",
	            JOptionPane.ERROR_MESSAGE
	        );
	        return false;
	    }
	}
	
	private void handleReturn() {
        int option = view.confirmReturn();

        if (option == JOptionPane.YES_OPTION) {
        	new LoginWindow(null);
        	Window window = SwingUtilities.getWindowAncestor(view);

            if (window != null) {
                window.dispose();
            }
        }
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
	    String name = view.getName();
	    
	    if (name.isEmpty()) {
	        view.setNameError("El nombre es obligatorio");
	        return false;
	    } else if (!Validator.isValidName(name)) {
	        view.setNameError("Solo se permiten letras");
	        return false;
	    }
	    
	    view.clearNameError();
		return true;
	 }
	 
	public boolean validateSurname() {
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
	    String email = view.getEmail();

	    if (email.isEmpty()) {
	        view.setEmailError("El correo es obligatorio");
	        return false;

	    } else if (!Validator.isValidEmail(email)) {
	        view.setEmailError("Formato de correo inválido");
	        return false;
	    }

	    try {
	        repository.validateDuplicateEmail(email);

	    } catch (DuplicateEmailException e) {
	        view.setEmailError(e.getMessage());
	        return false;
	    } catch (IOException e) {
	    	JOptionPane.showMessageDialog(
    		    null,
    		    e.getMessage(),
    		    "Error",
    		    JOptionPane.ERROR_MESSAGE
    		);
	        return false;
	    }
	    view.clearEmailError();
	    return true;
	}
	 
	public boolean validatePhone() {
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
	    Date date = view.getBirthDate();
	    LocalDate birthDate = date.toInstant()
	            .atZone(java.time.ZoneId.systemDefault())
	            .toLocalDate();

	    LocalDate today = LocalDate.now();

	    int age = Period.between(birthDate, today).getYears();
	    
	    if (birthDate.isAfter(today)) {
	        view.setBirthDateError("La fecha no puede ser futura");
	        return false;
	    }

	    if (age < 18) {
	        view.setBirthDateError("Debes ser mayor de edad (18+)");
	        return false;
	    }
	    
	    if (age > 120) {
	        view.setBirthDateError("Fecha no válida");
	        return false;
	    }

	 	view.clearBirthDateError();
		return true;
	}
	 
	public boolean validateCountry() {
	    if (view.getCountryIndex() == 0) {
	        view.setCountryError("Debe seleccionar un país");
	        return false;
	    }
		view.clearCountryError();
		return true;
	}
	 
	public boolean validateGender() {
	    if (!view.isMaleSelected() && !view.isFemaleSelected()) {
	        view.setGenderError("Seleccione un género");
	        return false;
	    }
	 	view.clearGenderError();
		return true;
	}
	 
	public boolean validateTerms() {
	    if (!view.isTermsAccepted()) {
	        view.setTermsError("Debe aceptar los términos y condiciones");
	        return false;
	    }
	 	view.clearTermsError();
		return true;
	}
}