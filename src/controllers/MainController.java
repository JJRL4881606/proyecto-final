package controllers;

import javax.swing.JFrame;
import javax.swing.SwingUtilities;

import utils.Config;
import views.MainView;
import views.LoginWindow;

import java.awt.Dimension;
import java.awt.Point;
import java.awt.Window;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class MainController {

    private MainView view;
	private UserController userController;

	private JFrame frame;

	public MainController(MainView view, JFrame frame) {
	    this.view = view;
	    this.frame = frame;
	    
	    loadWindowPreferences();
	    registerListeners();
	}
	
	public void registerListeners( ) {
		view.logOut.addActionListener(e -> handleClose());
		
		frame.addWindowListener(new WindowAdapter() {
		    @Override
		    public void windowClosing(WindowEvent e) {
		        saveWindowPreferences();
		        handleClose();
		    }
		});
		
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
		new LoginWindow();
        Window window = SwingUtilities.getWindowAncestor(view);
        if (window != null) window.dispose();
    }
    
	private void updateMenuState(String viewName) {
		view.btnUsers.setEnabled(!viewName.equals(MainView.USERS));
		view.btnHome.setEnabled(!viewName.equals(MainView.HOME));
	}
	
	private void saveWindowPreferences() {
		Dimension size = frame.getSize();
		Point point = frame.getLocation();
		
		Config.set("registration.window.width", 
				String.valueOf(size.width));
		
		Config.set("registration.window.height", 
				String.valueOf(size.height));
		
		Config.set("registration.window.x", 
				String.valueOf(point.x));
		
		Config.set("registration.window.y", 
				String.valueOf(point.y));
		
	}
	
	private void loadWindowPreferences() {
	    int width = Integer.parseInt(
	            Config.get("registration.window.width", "500"));

	    int height = Integer.parseInt(
	            Config.get("registration.window.height", "500"));

	    String xValue = Config.get("registration.window.x", "");
	    String yValue = Config.get("registration.window.y", "");

	    if (!xValue.isBlank() && !yValue.isBlank()) {
	        frame.setLocation(
	            Integer.parseInt(xValue),
	            Integer.parseInt(yValue)
	        );
	    } else {
	        frame.setLocationRelativeTo(null);
	    }

	    frame.setSize(width, height);
	}
}