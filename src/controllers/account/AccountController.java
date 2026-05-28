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
        loadUser();
        initListeners();
    }

    private void initListeners() {
    	view.getBtnPassword().addActionListener(e->{

    	    PasswordDialog dialog = new PasswordDialog(null);

    	    new PasswordController(dialog);

    	    dialog.setVisible(true);

    	});

        view.getBtnLogout().addActionListener(e -> {
            Session.logout();
            new LoginWindow(user);
            Window window = SwingUtilities.getWindowAncestor(view);
            if (window != null) {
                window.dispose();
            }
        });
        
        view.getBtnLogin().addActionListener(e -> {
        	
            new LoginWindow(user);
            Window window = SwingUtilities.getWindowAncestor(view);

            if(window != null){
                window.dispose();
            }
        });
        
        view.getBtnEdit().addActionListener(e->{

        	AccountEditDialog dialog = new AccountEditDialog(
    			null,
    			Session.getCurrentUser()
    		);

        	new AccountEditController(dialog);

        	dialog.setVisible(true);

        	loadUser();
        });
    }
    
    private void loadUser() {

        User user = Session.getCurrentUser();

        if(user == null){
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

        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");

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