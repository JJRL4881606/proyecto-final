package controllers.main;

import javax.swing.JOptionPane;
import javax.swing.SwingUtilities;

import config.Config;
import controllers.amenities.AmenityController;
import controllers.reservations.PaymentAdminController;
import controllers.reservations.ReservationController;
import controllers.rooms.RoomController;
import controllers.roomtypes.RoomTypeController;
import controllers.users.UserController;
import models.Role;
import utils.Session;
import views.auth.LoginWindow;
import views.main.MainView;
import views.main.MainWindow;

import java.awt.Dimension;
import java.awt.Point;
import java.awt.Window;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class MainController {

	private MainView view;
	private UserController userController;
	private RoomTypeController roomTypeController;
	private RoomController roomController;
	private AmenityController amenityController;
	private ReservationController reservationController;
	private PaymentAdminController paymentAdminController;
	private MainWindow frame;

	// Controlador principal que maneja la navegación del programa
	// y administracion de la ventana
	public MainController(MainView view, MainWindow frame) {
		this.view = view;
	    this.frame = frame;
	    loadWindowPreferences();
	    initListeners();
	}
	
	//registra eventos de navegacion, cierre de sesion,. cambios de vistas, y adminsitracion de la ventana
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
		
		//secciones de admin
		view.getBtnUsers().addActionListener(e -> { handleTableUsers(); });
		view.getBtnRoomTypes().addActionListener(e -> { handleTableRoomTypes(); });
		view.getBtnRooms().addActionListener(e -> { handleTableRooms(); });
		view.getBtnAmenities().addActionListener(e -> { handleTableAmenities(); });
		view.getBtnReservations().addActionListener(e -> { handleTableReservations(); });
		view.getBtnPayments().addActionListener(e -> handleTablePayments());
		
		//secciones de usuario
		view.getBtnAccount().addActionListener(e -> { handleAccount(); });
		view.getBtnMyReservations().addActionListener(e -> handleMyReservations());
		
		//ir al inicio
		view.getBtnHome().addActionListener(e -> {
			view.showView(MainView.HOME);
			updateMenuState(MainView.HOME);
			
		    frame.revalidate();
		    frame.repaint();

		    resetScroll();
		});		
		
		//ir a ver tipos habitaciones
		view.getBtnShowRooms().addActionListener(e -> { handleShowRooms(); });
		
		//clic en el logo lleva a home
		view.getLblLogo().addMouseListener(
		    new MouseAdapter() {
		    	
		        @Override
		        public void mouseClicked(MouseEvent e) {
		            view.showView(MainView.HOME);
		            updateMenuState(MainView.HOME);

		            frame.revalidate();
		            frame.repaint();

		            resetScroll();
		        }
		    }
		);
	}
	
	//muestra la vista de info de la cuenta
	private void handleAccount() {
	    showView(MainView.ACCOUNT);
	}
	
	//muestra la vista de reservaciones del usuario
	private void handleMyReservations() {
	    showView(MainView.MY_RESERVATIONS);
	}	
	
	//muestra vista de todas las roomtypes
	private void handleShowRooms() {
	    showView(MainView.SHOW_ROOMS);
	}
	
	//modulo de usuarios
	private void handleTableUsers() {
	    // Controlar el acceso
	    if (Role.ADMIN.equals(Session.getRole())) {
	        // iniciar el controlador si es la primera vez
	        if (userController == null) {
	            userController = new UserController(view.getUsersPanel());
	        }
	        
	        // Cargar datos y mostrar la vista
	        userController.loadUsers();
	        showView(MainView.ADMIN_USERS);
	        
	    } else {
	        JOptionPane.showMessageDialog(null, "No tienes permisos para realizar esta acción", "Acceso Denegado", JOptionPane.ERROR_MESSAGE);
	    }
	}
	
	//modulo de tipos de habitaciones
	private void handleTableRoomTypes() {
	    // Controlar el acceso
	    if (Role.ADMIN.equals(Session.getRole())) { 
	        // iniciar el controlador si es la primera vez
	        if (roomTypeController == null) {
	            roomTypeController = new RoomTypeController(view.getRoomTypesPanel(), view);
	        }
	        
	        // Cargar datos y mostrar la vista
	        roomTypeController.loadRoomTypes();
	        showView(MainView.ADMIN_ROOMTYPES);
	        
	    } else {
	        JOptionPane.showMessageDialog(null, "No tienes permisos para realizar esta acción", "Acceso Denegado", JOptionPane.ERROR_MESSAGE);
	    }
	}	
	
	//modulo de habitaciones
	private void handleTableRooms() {
	    // Controlar el acceso
	    if (Role.ADMIN.equals(Session.getRole())) {
	        // iniciar el controlador si es la primera vez
	        if (roomController == null) {
	            roomController = new RoomController(view.getRoomsPanel(), view);
	        }
	        
	        // Cargar datos y mostrar la vista
	        roomController.loadRooms();
	        showView(MainView.ADMIN_ROOMS);
	        
	    } else {
	        JOptionPane.showMessageDialog(null, "No tienes permisos para realizar esta acción", "Acceso Denegado", JOptionPane.ERROR_MESSAGE);
	    }
	}
	
	//modulo de amenidads
	private void handleTableAmenities() {
	    // Controlar el acceso
	    if (Role.ADMIN.equals(Session.getRole())) { 
	        // iniciar el controlador si es la primera vez
	        if (amenityController == null) {
	            amenityController = new AmenityController(view.getAmenitiesPanel());
	        }
	        
	        // Cargar datos y mostrar la vista
	        amenityController.loadAmenities();
	        showView(MainView.ADMIN_AMENITIES);
	        
	    } else {
	        JOptionPane.showMessageDialog(null, "No tienes permisos para realizar esta acción", "Acceso Denegado", JOptionPane.ERROR_MESSAGE);
	    }
	}	
	
	//modulo de resrvaciones
	private void handleTableReservations() {
	    // Controlar el acceso
	    if (Role.ADMIN.equals(Session.getRole())) {
	        // iniciar el controlador si es la primera vez
	        if (reservationController == null) {
	            reservationController = new ReservationController(view.getReservationsPanel());
	        }
	        
	        // Cargar datos y mostrar la vista
	        reservationController.loadReservations();
	        showView(MainView.ADMIN_RESERVATIONS);
	        
	    } else {
	        JOptionPane.showMessageDialog(null, "No tienes permisos para realizar esta acción", "Acceso Denegado", JOptionPane.ERROR_MESSAGE);
	    }
	}
	
	//modulo de pagos
	private void handleTablePayments() {
	    // Controlar el acceso
	    if (Role.ADMIN.equals(Session.getRole())) {
	        // iniciar el controlador si es la primera vez
	        if (paymentAdminController == null) {
	            paymentAdminController = new PaymentAdminController(view.getPaymentsPanel());
	        }
	        
	        // Cargar datos y mostrar la vista
	        paymentAdminController.loadPayments();
	        showView(MainView.ADMIN_PAYMENTS);
	        
	    } else {
	        JOptionPane.showMessageDialog(null, "No tienes permisos para realizar esta acción", "Acceso Denegado", JOptionPane.ERROR_MESSAGE);
	    }
	}
		
	// Cierra la sesión actual y regresa al login
    private void handleClose() {
        Session.logout();

        new LoginWindow();
        Window window = SwingUtilities.getWindowAncestor(view);
        if (window != null) window.dispose();
    }
    
    // Deshabilita la opción de la vista actual
	private void updateMenuState(String viewName) {
		view.getBtnHome().setEnabled(!viewName.equals(MainView.HOME));
		
		view.getBtnUsers().setEnabled(!viewName.equals(MainView.ADMIN_USERS));
		view.getBtnRoomTypes().setEnabled(!viewName.equals(MainView.ADMIN_ROOMTYPES));
		view.getBtnRooms().setEnabled(!viewName.equals(MainView.ADMIN_ROOMS));
		view.getBtnAmenities().setEnabled(!viewName.equals(MainView.ADMIN_AMENITIES));
		view.getBtnReservations().setEnabled(!viewName.equals(MainView.ADMIN_RESERVATIONS));
		view.getBtnPayments().setEnabled(!viewName.equals(MainView.ADMIN_PAYMENTS));
		
		view.getBtnShowRooms().setEnabled(!viewName.equals(MainView.SHOW_ROOMS));
		view.getBtnAccount().setEnabled(!viewName.equals(MainView.ACCOUNT));
		view.getBtnMyReservations().setEnabled(!viewName.equals(MainView.MY_RESERVATIONS));
	}
	
	// Cambio de vistas, actualizar el deshabilitar boton del menu, y reinicio del scroll
	private void showView(String viewName) {
	    view.showView(viewName);
	    updateMenuState(viewName);

	    frame.revalidate();
	    frame.repaint();

	    resetScroll();
	}
	
	// Guarda el tamaño y posición actuales de la ventana para la siguiente vez que se abra el programa
	private void saveWindowPreferences() {
		Dimension size = frame.getSize();
		Point point = frame.getLocation();
		
		//guardar dimensiones
		Config.set("main.window.width", 
				String.valueOf(size.width));

		Config.set("main.window.height", 
				String.valueOf(size.height));

		//guardar posición
		Config.set("main.window.x", 
				String.valueOf(point.x));

		Config.set("main.window.y", 
				String.valueOf(point.y));
	}
	
	//recupera las preferencias de ventana (tamano y posicion)
	private void loadWindowPreferences() {
	    try {
			int width = Integer.parseInt(Config.get("main.window.width", "500"));

			int height = Integer.parseInt(Config.get("main.window.height", "500"));

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
	    // usa configuracion por defecto si pasa un error
	    catch(Exception e) {
	        frame.setSize(1200,700);
	        frame.setLocationRelativeTo(null);
	    }
	}
	
	//coloca el scroll al inicio de la pagina
	private void resetScroll() {
	    SwingUtilities.invokeLater(() -> {
	    	//ejecuta despues de que termina d actualizar la interfaz
	        frame.getScroll().getViewport().setViewPosition(new Point(0, 0));
	    });
	}
}