package controllers.account;

import java.io.IOException;
import java.time.LocalDate;
import java.time.Period;
import java.time.ZoneId;
import java.util.Date;
import javax.swing.JOptionPane;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.text.JTextComponent;

import exceptions.DuplicateEmailException;
import models.User;
import repository.UserRepository;
import utils.FormUtils;
import utils.Session;
import utils.Validator;
import views.account.AccountEditDialog;

public class AccountEditController {

    private final AccountEditDialog view;
    private final UserRepository repository;

    public AccountEditController(AccountEditDialog view) {
        this.view = view;
        this.repository = new UserRepository();
        initListeners();
        initInputRestrictions();
    }

    private void initListeners() {
        view.getBtnSave().addActionListener(e -> save());
        view.getBtnCancel().addActionListener(e -> view.dispose());
        view.getComboCountry().addActionListener(e -> validateCountry());
        view.getSpBirthDate().addChangeListener(e -> validateBirthDate());
        view.getRbtnMale().addActionListener(e -> validateGender());
        view.getRbtnFemale().addActionListener(e -> validateGender());

        // DocumentListeners compactados mediante método utilitario
        addDocumentListener(view.getTxtName(), this::validateName);
        addDocumentListener(view.getTxtSurname(), this::validateSurname);
        addDocumentListener(view.getTxtEmail(), this::validateEmail);
        addDocumentListener(view.getTxtPhone(), this::validatePhone);

        FormUtils.addFocusEffect(view.getTxtName(), view.getLblNameError());
        FormUtils.addFocusEffect(view.getTxtSurname(), view.getLblSurnameError());
        FormUtils.addFocusEffect(view.getTxtEmail(), view.getLblEmailError());
        FormUtils.addFocusEffect(view.getTxtPhone(), view.getLblPhoneError());

    }

    private void addDocumentListener(JTextComponent textComponent, Runnable validationMethod) {
        textComponent.getDocument().addDocumentListener(new DocumentListener() {
            public void insertUpdate(DocumentEvent e) { validationMethod.run(); }
            public void removeUpdate(DocumentEvent e) { validationMethod.run(); }
            public void changedUpdate(DocumentEvent e) { validationMethod.run(); }
        });
    }

    private void initInputRestrictions() {
        Validator.onlyLetters(view.getTxtName());
        Validator.onlyLetters(view.getTxtSurname());
        Validator.onlyPhoneNumbers(view.getTxtPhone());
        Validator.noSpaces(view.getTxtEmail());
        FormUtils.onlyDateNumbers(view.getSpBirthDate());
    }

    private void save() {
        if (!validateForm()) return;

        User user = view.getUser();
        user.setName(view.getName());
        user.setSurname(view.getSurname());
        user.setEmail(view.getEmail());
        user.setPhone(view.getPhone());
        user.setCountry(view.getCountry());
        user.setBirthDate(view.getBirthDate());
        user.setGender(view.getGender());

        try {
            repository.update(user);
        } catch (IOException e) {
            JOptionPane.showMessageDialog(null, e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        Session.login(user);
        view.dispose();
    }

    private boolean validateForm() {
        view.clearErrors();
        boolean valid = true;
        if (!validateName()) valid = false;
        if (!validateSurname()) valid = false;
        if (!validateEmail()) valid = false;
        if (!validatePhone()) valid = false;
        if (!validateCountry()) valid = false;
        if (!validateGender()) valid = false;
        if (!validateBirthDate()) valid = false;
        return valid;
    }

    private boolean validateName() {
        String name = view.getName();
        if (name.isEmpty()) { view.setNameError("El nombre es obligatorio"); return false; }
        if (!Validator.isValidName(name)) { view.setNameError("Solo letras"); return false; }
        view.clearNameError();
        return true;
    }

    private boolean validateSurname() {
        String surname = view.getSurname();
        if (surname.isEmpty()) { view.setSurnameError("Los apellidos son obligatorios"); return false; }
        if (!Validator.isValidName(surname)) { view.setSurnameError("Solo letras"); return false; }
        view.clearSurnameError();
        return true;
    }

    private boolean validateEmail() {
        String email = view.getEmail();
        if (email.isEmpty()) { view.setEmailError("Correo obligatorio"); return false; }
        if (!Validator.isValidEmail(email)) { view.setEmailError("Correo inválido"); return false; }
        try {
            User current = view.getUser();
            if (!current.getEmail().equalsIgnoreCase(email)) {
                repository.validateDuplicateEmail(email);
            }
        } catch (DuplicateEmailException e) {
            view.setEmailError(e.getMessage());
            return false;
        } catch (IOException e) {
            return false;
        }
        view.clearEmailError();
        return true;
    }

    private boolean validatePhone() {
        String phone = view.getPhone();
        if (phone.isEmpty()) { view.setPhoneError("Teléfono obligatorio"); return false; }
        if (!Validator.isValidPhone(phone)) { view.setPhoneError("Debe tener 10 números"); return false; }
        view.clearPhoneError();
        return true;
    }

    private boolean validateCountry() {
        if (view.getCountryIndex() == 0) { view.setCountryError("Seleccione país"); return false; }
        view.clearCountryError();
        return true;
    }

    private boolean validateGender() {
        if (!view.isMaleSelected() && !view.isFemaleSelected()) {
            view.setGenderError("Seleccione género");
            return false;
        }
        view.clearGenderError();
        return true;
    }

    private boolean validateBirthDate() {
        Date date = view.getBirthDate();
        LocalDate birthDate = date.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
        LocalDate today = LocalDate.now();
        int age = Period.between(birthDate, today).getYears();

        if (birthDate.isAfter(today)) { view.setBirthDateError("Fecha futura inválida"); return false; }
        if (age < 18) { view.setBirthDateError("Debes ser mayor de edad"); return false; }
        if (age > 120) { view.setBirthDateError("Fecha inválida"); return false; }
        
        view.clearBirthDateError();
        return true;
    }
}