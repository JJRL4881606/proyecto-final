package controllers.main;

import javax.swing.SwingUtilities;

import config.Config;
import controllers.rooms.RoomController;
import controllers.roomtypes.RoomTypeController;
import controllers.users.UserController;
import utils.Session;
import views.auth.LoginWindow;
import views.main.MainView;
import views.main.MainWindow;

import java.awt.Dimension;
import java.awt.Point;
import java.awt.Window;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class MainController {

    private MainView view;
	private UserController userController;
	private RoomTypeController roomTypeController;
	private RoomController roomController;
	private MainWindow frame;

	public MainController(MainView view, MainWindow frame) {
	    this.view = view;
	    this.frame = frame;
	    
	    loadWindowPreferences();
	    initListeners();
	}
	
	public void initListeners( ) {
		view.getLogOut().addActionListener(e -> handleClose());
		
		frame.addWindowListener(new WindowAdapter() {
		    @Override
		    public void windowClosing(WindowEvent e) {
		        saveWindowPreferences();
		        handleClose();
		    }
		    
		    public void windowOpened(WindowEvent e) {
		        resetScroll();
		    }
		});
		
		view.getBtnUsers().addActionListener(e -> { handleShowUsers(); });
		view.getBtnRoomTypes().addActionListener(e -> { handleShowRoomTypes(); });
		view.getBtnRooms().addActionListener(e -> { handleShowRooms(); });
		
		view.getBtnHome().addActionListener(e -> {
			view.showView(MainView.HOME);
			updateMenuState(MainView.HOME);
			
		    frame.revalidate();
		    frame.repaint();

		    resetScroll();
		});		
	}
	
	private void handleShowUsers() {
		if(userController == null) {
			userController = new UserController(view.usersPanel);
		}
			
		userController.loadUsers();
		
		view.showView(MainView.ADMIN_USERS);
		updateMenuState(MainView.ADMIN_USERS);
		
	    frame.revalidate();
	    frame.repaint();

	    resetScroll();
	}
	
	private void handleShowRoomTypes() {
		if(roomTypeController == null) {
			roomTypeController = new RoomTypeController(view.roomTypesPanel);
		}
			
		roomTypeController.loadRoomTypes();
		
		view.showView(MainView.ADMIN_ROOMTYPES);
		updateMenuState(MainView.ADMIN_ROOMTYPES);
		
	    frame.revalidate();
	    frame.repaint();

	    resetScroll();
	}	
	
	private void handleShowRooms() {
		if(roomController == null) {
			roomController = new RoomController(view.roomsPanel);
		}
			
		roomController.loadRooms();
		
		view.showView(MainView.ADMIN_ROOMS);
		updateMenuState(MainView.ADMIN_ROOMS);
		
	    frame.revalidate();
	    frame.repaint();

	    resetScroll();
	}
	
    private void handleClose() {
        Session.logout();

		new LoginWindow();
        Window window = SwingUtilities.getWindowAncestor(view);
        if (window != null) window.dispose();
    }
    
	private void updateMenuState(String viewName) {
		view.getBtnHome().setEnabled(!viewName.equals(MainView.HOME));
		view.getBtnUsers().setEnabled(!viewName.equals(MainView.ADMIN_USERS));
		view.getBtnRoomTypes().setEnabled(!viewName.equals(MainView.ADMIN_ROOMTYPES));
		view.getBtnRooms().setEnabled(!viewName.equals(MainView.ADMIN_ROOMS));
	}
	
	private void saveWindowPreferences() {
		Dimension size = frame.getSize();
		Point point = frame.getLocation();
		
		Config.set("main.window.width", 
				String.valueOf(size.width));

		Config.set("main.window.height", 
				String.valueOf(size.height));

		Config.set("main.window.x", 
				String.valueOf(point.x));

		Config.set("main.window.y", 
				String.valueOf(point.y));
	}
	
	private void loadWindowPreferences() {
		int width = Integer.parseInt(
		        Config.get("main.window.width", "500"));

		int height = Integer.parseInt(
		        Config.get("main.window.height", "500"));

		String xValue = Config.get("main.window.x", "");
		String yValue = Config.get("main.window.y", "");
		
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
	
	private void resetScroll() {
	    SwingUtilities.invokeLater(() -> {
	        frame.getScroll().getViewport().setViewPosition(new Point(0, 0));
	    });
	}
}