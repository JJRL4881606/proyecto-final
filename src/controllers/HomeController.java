package controllers;

import javax.swing.JOptionPane;
import javax.swing.SwingUtilities;

import models.User;
import repository.UserRepository;
import tablemodels.UserTableModel;
import views.HomeView;
import views.LoginWindow;

import java.awt.Window;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.IOException;
import java.util.List;

public class HomeController {

    private HomeView view;

    public HomeController(HomeView view) {
        
    	this.view = view;
		registerListeners();
    }
    
	public void registerListeners( ) {
		
		view.getLogOut().addActionListener(e -> handleClose());
		
		Window window = SwingUtilities.getWindowAncestor(view);

		if (window != null) {
		    window.addWindowListener(new WindowAdapter() {
		        @Override
		        public void windowClosing(WindowEvent e) {
		            handleClose();
		        }
		    });
		}
		
		view.getBtnUsers().addActionListener(e -> showUsers());
		
		view.getBtnHome().addActionListener(e -> view.showView(HomeView.HOME));		
	}
	
	private void showUsers() {
		
		UserController controller = new UserController(view.usersPanel);
		
		UserRepository repository = new UserRepository();
		
		try {
			List<User> users = repository.getUsers();
			
			UserTableModel model = new UserTableModel(users);
			
			view.usersPanel.setTableModel(model);
			
			view.showView(HomeView.USERS);
			
		}catch (IOException ex) {
			JOptionPane.showMessageDialog(view, ex.getMessage());
		}
		
	}
	
    private void handleClose() {

        int option = JOptionPane.showConfirmDialog(
            view,
            "¿Seguro que deseas cerrar sesión?"
        );

        if (option == JOptionPane.YES_OPTION) {

            new LoginWindow();

            Window window = SwingUtilities.getWindowAncestor(view);
            if (window != null) window.dispose();
        }
    }
}