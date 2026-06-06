package views.main;

import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.Point;
import java.awt.event.KeyEvent;
import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import javax.swing.border.EmptyBorder;

import views.account.AccountView;
import views.account.MyReservationsView;
import components.UnderlineMenu;
import controllers.account.AccountController;
import controllers.account.MyReservationsController;
import controllers.home.HomeController;
import controllers.payment.BookingSearchController;
import models.Role;
import models.User;
import controllers.roomtypes.RoomDetailsController;
import controllers.roomtypes.ShowRoomsController;

import javax.swing.ImageIcon;
import utils.AppFont;
import utils.Session;
import utils.UIColors;
import views.amenities.AmenitiesView;
import views.home.HomeView;
import views.payment.BookingSearchView;
import views.reservations.PaymentsAdminView;
import views.reservations.ReservationsView;
import views.rooms.RoomsView;
import views.roomtypes.RoomDetailsView;
import views.roomtypes.RoomTypesView;
import views.roomtypes.ShowRoomsView;
import views.users.UsersView;

@SuppressWarnings("serial")
public class MainView extends JPanel{
	
	public static final String HOME = "HOME";
	
	public static final String ADMIN_USERS = "USERS";
	public static final String ADMIN_ROOMTYPES = "ROOMTYPES";
	public static final String ADMIN_ROOMS = "ROOMS";
	public static final String ADMIN_AMENITIES = "AMENITIES";
	public static final String ADMIN_RESERVATIONS = "RESERVATIONS";
	public static final String ADMIN_PAYMENTS = "PAYMENTS";
	
	public static final String BOOKING_SEARCH = "BOOKING_SEARCH";
	public static final String SHOW_ROOMS = "SHOW_ROOMS";
	public static final String ROOM_DETAILS = "ROOM_DETAILS";
	
	public static final String ACCOUNT = "ACCOUNT";
	public static final String MY_RESERVATIONS = "MY_RESERVATIONS";
	
	private JMenuItem btnHome;
	
	private JMenuItem btnUsers;
	private JMenuItem btnRoomTypes;
	private JMenuItem btnRooms;
	private JMenuItem btnAmenities;
	private JMenuItem btnReservations;
	private PaymentsAdminView paymentsPanel;
	
	private JMenuItem btnShowRooms;
	
	private JMenuItem btnAccount;
	private JMenuItem btnMyReservations;
	
	private UnderlineMenu system;
	
	private JMenuItem logOut;
	
	private HomeView homePanel;
	
	private UsersView usersPanel;
	private RoomTypesView roomTypesPanel;
	private RoomsView roomsPanel;
	private AmenitiesView amenitiesPanel;
	private ReservationsView reservationsPanel;
	private JMenuItem btnPayments;
	
	private BookingSearchView bookingSearchPanel;
	private ShowRoomsView showRoomsPanel;
	private RoomDetailsView roomDetailsPanel;
	
	private AccountView accountPanel;
	private MyReservationsView myReservationsPanel;
	
	private HomeController homeController;
	private BookingSearchController bookingSearchController;
	private ShowRoomsController showRoomsController;
	
	private CardLayout cardLayout;
	private JPanel container;
	
	private User user;
	private JLabel lblLogo;
	
	public MainView(User user) {
		this.user = user;
	    UIManager.put("Menu.borderPainted", false);
	    UIManager.put("MenuItem.borderPainted", false);
	    UIManager.put("Menu.selectionBackground", UIColors.HEADER);
	    UIManager.put("Menu.selectionForeground", Color.WHITE);
	    UIManager.put("MenuItem.selectionBackground", UIColors.HEADER);
	    UIManager.put("MenuItem.selectionForeground", Color.WHITE);
	    
	    this.setBackground(new Color(100,149,237)); 
	    setLayout(new BorderLayout());
	    setVisible(true);
	    
	    initializeComponents();
	    configurePermissions();
	}

    public void initializeComponents() 
    {	    
        add(headerSection(), BorderLayout.NORTH);
        add(content(), BorderLayout.CENTER);
        add(inferiorSection(), BorderLayout.SOUTH);
    }
        
    private void configurePermissions() {
        if(!Session.getRole().equals(Role.ADMIN)) {
            system.setVisible(false);
            btnUsers.setVisible(false);
            btnRoomTypes.setVisible(false);
            btnRooms.setVisible(false);
            btnAmenities.setVisible(false);
            btnReservations.setVisible(false);
            btnPayments.setVisible(false);
        }
    }
    
    //HEADER
    public JPanel headerSection() {
        JPanel superiorPanel = new JPanel();
        superiorPanel.setLayout(new GridLayout(1,3));
        superiorPanel.setBackground(UIColors.HEADER);
        superiorPanel.setBorder(new EmptyBorder(30,30,35,30));

        superiorPanel.add(headerLeftSection());
        superiorPanel.add(headerCenterSection());
        superiorPanel.add(headerRightSection());

        return superiorPanel;
    }
    
    public JPanel headerCenterSection() {
        JPanel panel = createTransparentPanel();
        panel.setLayout(new FlowLayout(FlowLayout.CENTER));

        ImageIcon icon = new ImageIcon(getClass().getResource("/assets/img/logos/hotel-logo.png"));

    	Image img = icon.getImage().getScaledInstance( 250, 80, Image.SCALE_SMOOTH);

    	lblLogo = new JLabel(new ImageIcon(img));
    	lblLogo.setCursor(new Cursor(Cursor.HAND_CURSOR));
    	
    	panel.add(lblLogo);
    	
        return panel;
    }
    
    public JPanel headerRightSection(){
        JPanel panel = createTransparentPanel();
    	panel.setLayout(new GridBagLayout());
        
        if(Session.getRole().equals(Role.ADMIN)) {
        	JLabel lblCurrentView = new JLabel("VISTA DE ADMINISTRADOR");
        	lblCurrentView.setFont(AppFont.big());
        	lblCurrentView.setForeground(Color.WHITE);
        	lblCurrentView.setAlignmentX(CENTER_ALIGNMENT);
        	panel.add(lblCurrentView);
        }

        return panel;
    }
    
    public JPanel headerLeftSection(){
    	JPanel panel = createTransparentPanel();
    	panel.setLayout(new GridBagLayout());
    	
        JMenuBar menu = createMenu();
        panel.add(menu);

        return panel;
    }    
        
    public JMenuBar createMenu() {
    	JMenuBar mb = new JMenuBar();
    	mb.setFont(AppFont.big());
    	mb.setForeground(Color.white);
    	mb.setBorder(BorderFactory.createEmptyBorder(5,10,5,10));
    	mb.setOpaque(true);
    	mb.setBackground(UIColors.HEADER);
    	
        // INICIO
        JMenu home = new UnderlineMenu("Inicio");
        home.setMnemonic(KeyEvent.VK_R);
        mb.add(home);
        
        btnHome = new JMenuItem("Inicio");
        btnHome.setMnemonic(KeyEvent.VK_I);
        home.add(btnHome);
                
        // HABITACIONES
        JMenu rooms = new UnderlineMenu("Habitaciones");
        rooms.setMnemonic(KeyEvent.VK_H);
        mb.add(rooms);

        btnShowRooms = new JMenuItem("Ver tipos de habitaciones");
        btnShowRooms.setMnemonic(KeyEvent.VK_V);
        rooms.add(btnShowRooms);
        
        // SISTEMA
    	system = new UnderlineMenu("Sistema");
    	system.setMnemonic(KeyEvent.VK_S);
        mb.add(system);
        
        btnUsers = new JMenuItem("Ver usuarios");
        btnUsers.setMnemonic(KeyEvent.VK_U);
        system.add(btnUsers);
        
        btnRoomTypes = new JMenuItem("Ver tipos de habitaciones");
        btnRoomTypes.setMnemonic(KeyEvent.VK_T);
        system.add(btnRoomTypes);
        
        setBtnRooms(new JMenuItem("Ver habitaciones"));
        getBtnRooms().setMnemonic(KeyEvent.VK_H);
        system.add(getBtnRooms());
        
        btnAmenities = new JMenuItem("Ver amenidades");
        btnAmenities.setMnemonic(KeyEvent.VK_A);
        system.add(btnAmenities);
        
        btnReservations = new JMenuItem("Ver reservaciones");
        btnReservations.setMnemonic(KeyEvent.VK_R);
        system.add(btnReservations);
        
        btnPayments = new JMenuItem("Ver pagos");
        btnPayments.setMnemonic(KeyEvent.VK_P);
        system.add(btnPayments);
        
        // USUARIO
        JMenu user = new UnderlineMenu("Usuario");
        user.setMnemonic(KeyEvent.VK_U);
        mb.add(user);

        btnAccount = new JMenuItem("Mi cuenta");
        btnAccount.setMnemonic(KeyEvent.VK_C);
        user.add(btnAccount);

        btnMyReservations = new JMenuItem("Mis reservas");
        user.add(btnMyReservations);        
        user.addSeparator();

        logOut = new JMenuItem("Cerrar sesión");
        logOut.setMnemonic(KeyEvent.VK_C);
        user.add(logOut); 

        return mb;
    }
    
    //CONTENT
    public JPanel content(){
        JPanel contentPanel = new JPanel(new BorderLayout());
        contentPanel.setBackground(Color.WHITE);

        createViews();
        contentPanel.add(container, BorderLayout.CENTER);

        return contentPanel;
    }
    
    //INFERIOR
    public JPanel inferiorSection() {

        JPanel inferiorPanel = new JPanel(new BorderLayout());
        inferiorPanel.setBackground(UIColors.HEADER);
        inferiorPanel.setBorder(new EmptyBorder(25, 35, 25, 35));

        // CONTENIDO PRINCIPAL
        JPanel topPanel = new JPanel(new BorderLayout(50, 0));
        topPanel.setOpaque(false);

        // Logo
        ImageIcon icon = new ImageIcon(
            getClass().getResource("/assets/img/logos/hotel-logo.png")
        );

        Image img = icon.getImage().getScaledInstance(
            180,
            60,
            Image.SCALE_SMOOTH
        );

        JLabel logo = new JLabel(new ImageIcon(img));

        // Panel derecho
        JPanel infoPanel = new JPanel(new GridLayout(1, 2, 60, 0));
        infoPanel.setOpaque(false);

        // COLUMNA CONTACTO
        JPanel contactPanel = createTransparentPanel();
        contactPanel.setLayout(new BoxLayout(contactPanel, BoxLayout.Y_AXIS));

        JLabel contactTitle = new JLabel("CONTACTO");
        contactTitle.setFont(AppFont.subtitle());
        contactTitle.setForeground(Color.WHITE);

        JLabel lblPhone = new JLabel("Teléfono: +971 4 426 0000");
        JLabel lblEmail = new JLabel("Reservaciones: info@atlantishotel.com");
        JLabel lblEvents = new JLabel("Eventos: events@atlantishotel.com");

        JLabel[] contactLabels = {
            lblPhone,
            lblEmail,
            lblEvents
        };

        for (JLabel lbl : contactLabels) {
            lbl.setFont(AppFont.normal());
            lbl.setForeground(Color.WHITE);
        }

        contactPanel.add(contactTitle);
        contactPanel.add(Box.createVerticalStrut(10));
        contactPanel.add(lblPhone);
        contactPanel.add(Box.createVerticalStrut(6));
        contactPanel.add(lblEmail);
        contactPanel.add(Box.createVerticalStrut(6));
        contactPanel.add(lblEvents);

        // COLUMNA HOTEL
        JPanel hotelPanel = createTransparentPanel();
        hotelPanel.setLayout(new BoxLayout(hotelPanel, BoxLayout.Y_AXIS));

        JLabel hotelTitle = new JLabel("HOTEL");
        hotelTitle.setFont(AppFont.subtitle());
        hotelTitle.setForeground(Color.WHITE);

        JLabel lblLocation = new JLabel("Dubai, United Arab Emirates");
        JLabel lblCheckIn = new JLabel("Check-in: 3:00 PM");
        JLabel lblCheckOut = new JLabel("Check-out: 12:00 PM");

        JLabel[] hotelLabels = {
            lblLocation,
            lblCheckIn,
            lblCheckOut
        };

        for (JLabel lbl : hotelLabels) {
            lbl.setFont(AppFont.normal());
            lbl.setForeground(Color.WHITE);
        }

        hotelPanel.add(hotelTitle);
        hotelPanel.add(Box.createVerticalStrut(10));
        hotelPanel.add(lblLocation);
        hotelPanel.add(Box.createVerticalStrut(6));
        hotelPanel.add(lblCheckIn);
        hotelPanel.add(Box.createVerticalStrut(6));
        hotelPanel.add(lblCheckOut);

        infoPanel.add(contactPanel);
        infoPanel.add(hotelPanel);

        topPanel.add(logo, BorderLayout.WEST);
        topPanel.add(infoPanel, BorderLayout.CENTER);

        // COPYRIGHT
        JPanel bottomPanel = createTransparentPanel();
        bottomPanel.setLayout(new BorderLayout());
        bottomPanel.setBorder(new EmptyBorder(20, 0, 0, 0));

        JLabel copyright = new JLabel(
            "© 2026 Atlantis The Palm, Dubai. All rights reserved."
        );

        copyright.setFont(AppFont.small());
        copyright.setForeground(new Color(220, 220, 220));

        bottomPanel.add(copyright, BorderLayout.WEST);

        inferiorPanel.add(topPanel, BorderLayout.CENTER);
        inferiorPanel.add(bottomPanel, BorderLayout.SOUTH);

        return inferiorPanel;
    }
    
    private void createViews() {
        cardLayout = new CardLayout();

        container = new JPanel(cardLayout) {
            @Override
            public Dimension getPreferredSize() {
                for (Component c : getComponents()) {
                    if (c.isVisible()) {
                        return c.getPreferredSize();
                    }
                }
                return super.getPreferredSize();
            }
        };
        homePanel = new HomeView(this, user);
        homeController = new HomeController(homePanel, this);
        
        setUsersPanel(new UsersView());
        setRoomTypesPanel(new RoomTypesView());
        setRoomsPanel(new RoomsView());
        
        setBookingSearchPanel(new BookingSearchView(user, this));
        bookingSearchController = new BookingSearchController(getBookingSearchPanel());
        
        setAmenitiesPanel(new AmenitiesView());
        
        setReservationsPanel(new ReservationsView());
        
        paymentsPanel = new PaymentsAdminView();
        
        showRoomsPanel = new ShowRoomsView();
        showRoomsController = new ShowRoomsController(showRoomsPanel, this);
        
        setRoomDetailsPanel(new RoomDetailsView());
        new RoomDetailsController(getRoomDetailsPanel());
        
        accountPanel = new AccountView();
        new AccountController(accountPanel, user);
        
        myReservationsPanel = new MyReservationsView();
        new MyReservationsController(myReservationsPanel);
        
        container.add(homePanel, HOME);
        
        container.add(getUsersPanel(), ADMIN_USERS);
        container.add(getRoomTypesPanel(), ADMIN_ROOMTYPES);
        container.add(getRoomsPanel(), ADMIN_ROOMS);
        container.add(getAmenitiesPanel(), ADMIN_AMENITIES);
        container.add(getReservationsPanel(), ADMIN_RESERVATIONS);
        container.add(paymentsPanel, ADMIN_PAYMENTS);
        
        container.add(getBookingSearchPanel(), BOOKING_SEARCH);
        container.add(showRoomsPanel, SHOW_ROOMS);
        container.add(getRoomDetailsPanel(), ROOM_DETAILS);
        
        container.add(accountPanel, ACCOUNT);
        container.add(myReservationsPanel, MY_RESERVATIONS);
    }        
    
    private JPanel createTransparentPanel() {
        JPanel panel = new JPanel();
        panel.setOpaque(false);
        return panel;
    }
    
    public void showView(String view) {
        cardLayout.show(container, view);

        // refrescar
        container.revalidate();
        container.repaint();

        revalidate();
        repaint();

        // reiniciar scroll
        MainWindow frame = (MainWindow)SwingUtilities.getWindowAncestor(this);

        if(frame != null){
            SwingUtilities.invokeLater(() -> {
                frame.getScroll().getViewport().setViewPosition(new Point(0,0));
            });
        }
    }	
    
    public void refreshRoomViews(){

        if(homeController != null){
            homeController.loadRooms();
        }

        if(showRoomsController != null){
            showRoomsController.reloadRooms();
        }

        if(bookingSearchController != null){
            bookingSearchController.reloadRooms();
        }

        container.revalidate();
        container.repaint();
    }
    
    //getters y setters
    public JMenuItem getLogOut() {
        return logOut;
    }

	public JMenuItem getBtnUsers() {
		return btnUsers;
	}
	
	public JMenuItem getBtnHome() {
		return btnHome;
	}	
	
	public JMenuItem getBtnRoomTypes() {
		return btnRoomTypes;
	}

	public JMenuItem getBtnRooms() {
		return btnRooms;
	}
	
	public JMenuItem getBtnAmenities() {
		return btnAmenities;
	}
	
	public JMenuItem getBtnReservations() {
		return btnReservations;
	}

	public JMenuItem getBtnShowRooms() {
		return btnShowRooms;
	}

	public JMenuItem getBtnAccount() {
		return btnAccount;
	}

	public void setBtnRooms(JMenuItem btnRooms) {
		this.btnRooms = btnRooms;
	}	
	
	public JLabel getLblLogo() {
	    return lblLogo;
	}
	
	public JMenuItem getBtnMyReservations() {
	    return btnMyReservations;
	}
	
	// getters y setters de paneles

	public UsersView getUsersPanel() {
		return usersPanel;
	}

	public void setUsersPanel(UsersView usersPanel) {
		this.usersPanel = usersPanel;
	}

	public RoomTypesView getRoomTypesPanel() {
		return roomTypesPanel;
	}

	public void setRoomTypesPanel(RoomTypesView roomTypesPanel) {
		this.roomTypesPanel = roomTypesPanel;
	}

	public RoomsView getRoomsPanel() {
		return roomsPanel;
	}

	public void setRoomsPanel(RoomsView roomsPanel) {
		this.roomsPanel = roomsPanel;
	}

	public AmenitiesView getAmenitiesPanel() {
		return amenitiesPanel;
	}

	public void setAmenitiesPanel(AmenitiesView amenitiesPanel) {
		this.amenitiesPanel = amenitiesPanel;
	}

	public ReservationsView getReservationsPanel() {
		return reservationsPanel;
	}

	public void setReservationsPanel(ReservationsView reservationsPanel) {
		this.reservationsPanel = reservationsPanel;
	}

	public RoomDetailsView getRoomDetailsPanel() {
		return roomDetailsPanel;
	}

	public void setRoomDetailsPanel(RoomDetailsView roomDetailsPanel) {
		this.roomDetailsPanel = roomDetailsPanel;
	}

	public BookingSearchView getBookingSearchPanel() {
		return bookingSearchPanel;
	}

	public void setBookingSearchPanel(BookingSearchView bookingSearchPanel) {
		this.bookingSearchPanel = bookingSearchPanel;
	}
	
	public PaymentsAdminView getPaymentsPanel() {
	    return paymentsPanel;
	}

	public JMenuItem getBtnPayments() {
	    return btnPayments;
	}
}