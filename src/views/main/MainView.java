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
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import javax.swing.border.EmptyBorder;

import views.account.AccountView;
import components.UnderlineMenu;
import controllers.account.AccountController;
import controllers.booking.BookingSearchController;
import controllers.home.HomeController;
import models.User;
import controllers.rooms.RoomDetailsController;
import controllers.rooms.ShowRoomsController;

import javax.swing.ImageIcon;
import utils.AppFont;
import utils.Session;
import utils.UIColors;
import views.amenities.AmenitiesView;
import views.booking.BookingSearchView;
import views.booking.ReservationsView;
import views.home.HomeView;
import views.rooms.RoomDetailsView;
import views.rooms.RoomsView;
import views.rooms.ShowRoomsView;
import views.roomtypes.RoomTypesView;
import views.users.UsersView;

@SuppressWarnings("serial")
public class MainView extends JPanel{
	
	public static final String HOME = "HOME";
	public static final String ADMIN_USERS = "USERS";
	public static final String ADMIN_ROOMTYPES = "ROOMTYPES";
	public static final String ADMIN_ROOMS = "ROOMS";
	public static final String ADMIN_AMENITIES = "AMENITIES";
	public static final String BOOKING_SEARCH = "BOOKING_SEARCH";
	public static final String ADMIN_RESERVATIONS = "RESERVATIONS";
	public static final String SHOW_ROOMS = "SHOW_ROOMS";
	public static final String ROOM_DETAILS = "ROOM_DETAILS";
	public static final String ACCOUNT = "ACCOUNT";
	
	private JMenuItem btnHome;
	private JMenuItem btnUsers;
	private JMenuItem btnRoomTypes;
	private JMenuItem btnRooms;
	private JMenuItem btnAmenities;
	private JMenuItem btnReservations;
	private JMenuItem btnShowRooms;
	private JMenuItem btnAccount;
	
	private JMenuItem logOut;
	
	public HomeView homePanel;
	public UsersView usersPanel;
	public RoomTypesView roomTypesPanel;
	public RoomsView roomsPanel;
	public AmenitiesView amenitiesPanel;
	public ReservationsView reservationsPanel;
	public ShowRoomsView showRoomsPanel;
	public RoomDetailsView roomDetailsPanel;
	public AccountView accountPanel;
	
	public BookingSearchView bookingSearchPanel;
	public BookingSearchController bookingSearchController;
	
	private CardLayout cardLayout;
	private JPanel container;
	
	private User user;
	
	public MainView(User user) {
		this.user = user;
	private JLabel lblLogo;
	
	public MainView() {
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
	    //configurePermissions();
	}

    public void initializeComponents() 
    {	    
        add(headerSection(), BorderLayout.NORTH);
        add(content(), BorderLayout.CENTER);
        add(inferiorSection(), BorderLayout.SOUTH);
    }
    
    //POR AHORA ESTÁ DESACTIVADO PARA AVANZAR, agregar faltantes
    /*
    private void configurePermissions() {
        if(!Session.getRole().equals("Admin")) {
            btnUsers.setVisible(false);
            btnRoomTypes.setVisible(false);
            btnRooms.setVisible(false);
            btnAmenities.setVisible(false);
        }
    }*/
    
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
        return createTransparentPanel();
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
    	JMenu sistema = new UnderlineMenu("Sistema");
    	sistema.setMnemonic(KeyEvent.VK_S);
        mb.add(sistema);
        
        btnUsers = new JMenuItem("Ver usuarios");
        btnUsers.setMnemonic(KeyEvent.VK_U);
        sistema.add(btnUsers);
        
        btnRoomTypes = new JMenuItem("Ver tipos de habitaciones");
        btnRoomTypes.setMnemonic(KeyEvent.VK_T);
        sistema.add(btnRoomTypes);
        
        setBtnRooms(new JMenuItem("Ver habitaciones"));
        getBtnRooms().setMnemonic(KeyEvent.VK_H);
        sistema.add(getBtnRooms());
        
        btnAmenities = new JMenuItem("Ver amenidades");
        btnAmenities.setMnemonic(KeyEvent.VK_A);
        sistema.add(btnAmenities);
        
        btnReservations = new JMenuItem("Ver reservaciones");
        btnReservations.setMnemonic(KeyEvent.VK_R);
        sistema.add(btnReservations);
        
        // USUARIO
        JMenu usuario = new UnderlineMenu("Usuario");
        usuario.setMnemonic(KeyEvent.VK_U);
        mb.add(usuario);

        btnAccount = new JMenuItem("Mi cuenta");
        btnAccount.setMnemonic(KeyEvent.VK_C);
        usuario.add(btnAccount);

        JMenuItem misReservas = new JMenuItem("Mis reservas");
        misReservas.setMnemonic(KeyEvent.VK_R);
        usuario.add(misReservas);
        
        usuario.addSeparator();

        logOut = new JMenuItem("Cerrar sesión");
        logOut.setMnemonic(KeyEvent.VK_C);
        usuario.add(logOut); 

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
        JPanel inferiorPanel = new JPanel(new BorderLayout(25,0));
        inferiorPanel.setBackground(UIColors.HEADER);
        inferiorPanel.setBorder(new EmptyBorder(25,35,25,35));

        // Logo izquierda
        ImageIcon icon = new ImageIcon(getClass().getResource("/assets/img/logos/hotel-logo.png"));
        Image img = icon.getImage().getScaledInstance(125, 40, Image.SCALE_SMOOTH);
        JLabel logo = new JLabel(new ImageIcon(img));

        // Información derecha
        JPanel infoPanel = createTransparentPanel();
        infoPanel.setLayout(new GridLayout(3,1,0,8));

        JLabel lblCopy = new JLabel("Copyright © 2026 ATLANTIS THE PALM, DUBAI. All rights reserved");
        JLabel lblNumber = new JLabel("Teléfono: 555-666-999-1");
        JLabel lblEmail = new JLabel("Correo: atlantis_the_palm_dubai_info@atlantishotel.com");

        JLabel[] labels = {lblCopy, lblNumber, lblEmail};

        for(JLabel lbl : labels){
            lbl.setFont(AppFont.normal());
            lbl.setForeground(Color.WHITE);
            infoPanel.add(lbl);
        }

        inferiorPanel.add(logo, BorderLayout.WEST);
        inferiorPanel.add(infoPanel, BorderLayout.CENTER);

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

        homePanel = new HomeView(user);
        new HomeController(homePanel, this);
        
        usersPanel = new UsersView();
        roomTypesPanel = new RoomTypesView();
        roomsPanel = new RoomsView();
        
        bookingSearchPanel = new BookingSearchView(user);
        new BookingSearchController(bookingSearchPanel);
        amenitiesPanel = new AmenitiesView();
        reservationsPanel = new ReservationsView();
        bookingSearchController = new BookingSearchController(bookingSearchPanel, this);        
        
        showRoomsPanel = new ShowRoomsView();
        new ShowRoomsController(showRoomsPanel, this);
        
        roomDetailsPanel = new RoomDetailsView();
        new RoomDetailsController(roomDetailsPanel, this);
        
        accountPanel = new AccountView();
        new AccountController(accountPanel);
        
        container.add(homePanel, HOME);
        container.add(usersPanel, ADMIN_USERS);
        container.add(roomTypesPanel, ADMIN_ROOMTYPES);
        container.add(roomsPanel, ADMIN_ROOMS);
        container.add(amenitiesPanel, ADMIN_AMENITIES);
        container.add(reservationsPanel, ADMIN_RESERVATIONS);
        container.add(bookingSearchPanel, BOOKING_SEARCH);
        container.add(showRoomsPanel, SHOW_ROOMS);
        container.add(roomDetailsPanel, ROOM_DETAILS);
        container.add(accountPanel, ACCOUNT);
    }        
    
    private JPanel createTransparentPanel() {
        JPanel panel = new JPanel();
        panel.setOpaque(false);
        return panel;
    }
    
    public void showView(String view) {
        cardLayout.show(container, view);

        // actualizar botones
        btnHome.setEnabled(!view.equals(HOME));
        btnUsers.setEnabled(!view.equals(ADMIN_USERS));
        btnRoomTypes.setEnabled(!view.equals(ADMIN_ROOMTYPES));
        btnRooms.setEnabled(!view.equals(ADMIN_ROOMS));
        btnShowRooms.setEnabled(!view.equals(SHOW_ROOMS));

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
    
    //getters
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
}