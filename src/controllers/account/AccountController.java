package controllers.account;

import java.awt.Window;
import java.text.SimpleDateFormat;
import javax.swing.SwingUtilities;
import models.User;
import utils.Session;
import views.account.AccountEditDialog;
import views.account.AccountView;
import views.account.PasswordDialog;
import views.auth.LoginWindow;

public class AccountController {

    private AccountView view;
    private User user;

    public AccountController(AccountView view, User user) {
        this.view = view;

        // Mostrar la info de la sesión actual
        loadUser();

        initListeners();
    }

    private void initListeners() {
       	view.getBtnPassword().addActionListener(e->{ handlePassword(); });
        view.getBtnLogout().addActionListener(e -> { handleLogout(); });
        view.getBtnLogin().addActionListener(e -> { handleLogin(); });
        view.getBtnEdit().addActionListener(e->{ handleEdit(); });
    }
    
	//abrir dialog de cambiar contrasena
    private void handlePassword() {
	    PasswordDialog dialog = new PasswordDialog(null);
	    
	    new PasswordController(dialog);
	    
	    dialog.setVisible(true);
    }
    
    // Cerrar sesión y volver al login
    private void handleLogout() {
        Session.logout();
        
        new LoginWindow();
        Window window = SwingUtilities.getWindowAncestor(view);
        
        if (window != null) {
            window.dispose();
        }
    }
    
    // Abrir login cuando no existe una sesión activa
    private void handleLogin() {
        new LoginWindow();
        Window window = SwingUtilities.getWindowAncestor(view);

        if(window != null){
            window.dispose();
        }
    }
    
    // Abrir formulario para editar los datos de la cuenta
    private void handleEdit() {
    	AccountEditDialog dialog = new AccountEditDialog(
			null,
			Session.getCurrentUser()
		);

    	new AccountEditController(dialog);

    	dialog.setVisible(true);
    	
    	// Recargar los datos por si fueron modificados
    	loadUser();
    }
    
    // CARGAR INFORMACIÓN DEL USUARIO EN LA VISTA
    private void loadUser() {

        User user = Session.getCurrentUser();

        // Mostrar interfaz para usuarios sin sesión iniciada
        if(user == null){
        	
        	// Mostrar info de la cuenta del usuario actual
            view.getLblNoSession().setVisible(true);
            view.getBtnLogin().setVisible(true);
            view.getLblName().setVisible(false);
            view.getLblEmail().setVisible(false);
            view.getLblPhone().setVisible(false);
            view.getLblCountry().setVisible(false);
            view.getLblBirthDate().setVisible(false);
            view.getLblGender().setVisible(false);
            view.getBtnEdit().setVisible(false);
            view.getBtnPassword().setVisible(false);
            view.getBtnLogout().setVisible(false);
            return;
        }

        view.getLblNoSession().setVisible(false);
        view.getBtnLogin().setVisible(false);

        // Formato para mostrar la fecha de nac
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        
        // Cargar datos del usuario en las labels
        
        view.getLblName().setText(
            "Nombre: "
            + user.getName()
            + " "
            + user.getSurname()
        );

        view.getLblEmail().setText(
            "Correo: "
            + user.getEmail()
        );

        view.getLblPhone().setText(
            "Teléfono: "
            + user.getPhone()
        );

        view.getLblCountry().setText(
            "País: "
            + user.getCountry()
        );

        view.getLblBirthDate().setText(
            "Nacimiento: "
            + sdf.format(
                user.getBirthDate()
            )
        );

        view.getLblGender().setText(
            "Género: "
            + user.getGender()
        );
    }
}