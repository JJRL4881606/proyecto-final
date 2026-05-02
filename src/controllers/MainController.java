package controllers;

import javax.swing.SwingUtilities;

import views.MainView;
import views.LoginWindow;

import java.awt.Window;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class MainController {

    private MainView view;
	private UserController userController;

    public MainController(MainView view) {
        
    	this.view = view;
		registerListeners();
    }
    
	public void registerListeners( ) {
		view.logOut.addActionListener(e -> handleClose());
		
		Window window = SwingUtilities.getWindowAncestor(view);

		if (window != null) {
		    window.addWindowListener(new WindowAdapter() {
		        @Override
		        public void windowClosing(WindowEvent e) {
		            handleClose();
		        }
		    });
		}

		view.btnUsers.addActionListener(e -> {
			showUsers();
		});
		
		view.btnHome.addActionListener(e -> {
			view.showView(MainView.HOME);
			updateMenuState(MainView.HOME);
		});		
	}
	
	private void showUsers() {
		if(userController == null) {
			userController = new UserController(view.usersPanel);
		}
			
		userController.loadUsers();
		
		view.showView(MainView.USERS);
		updateMenuState(MainView.USERS);
		
	}
	
    private void handleClose() {

        /*int option = JOptionPane.showConfirmDialog(
            view,
            "¿Seguro que deseas cerrar sesión?"
        );

        if (option == JOptionPane.YES_OPTION) {
            new LoginWindow();
        
            Window window = SwingUtilities.getWindowAncestor(view);
            if (window != null) window.dispose();
       	}*/
		new LoginWindow();
        Window window = SwingUtilities.getWindowAncestor(view);
        if (window != null) window.dispose();
    }
    
	private void updateMenuState(String viewName) {
		view.btnUsers.setEnabled(!viewName.equals(MainView.USERS));
		view.btnHome.setEnabled(!viewName.equals(MainView.HOME));
	}

}