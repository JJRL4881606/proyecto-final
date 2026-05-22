package views.main;

import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Color;
import java.awt.Component;
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

import components.UnderlineMenu;
import controllers.booking.BookingSearchController;
import controllers.home.HomeController;
import controllers.rooms.ShowRoomsController;

import javax.swing.ImageIcon;
import utils.AppFont;
import utils.Session;
import utils.UIColors;
import views.amenities.AmenitiesView;
import views.booking.BookingSearchView;
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
	public static final String SHOW_ROOMS = "SHOW_ROOMS";
	public static final String ROOM_DETAILS = "ROOM_DETAILS";
	
	private JMenuItem btnHome;
	private JMenuItem btnUsers;
	private JMenuItem btnRoomTypes;
	private JMenuItem btnRooms;
	private JMenuItem btnAmenities;
	private JMenuItem btnShowRooms;
	
	private JMenuItem logOut;
	
	public HomeView homePanel;
	public UsersView usersPanel;
	public RoomTypesView roomTypesPanel;
	public RoomsView roomsPanel;
	public AmenitiesView amenitiesPanel;
	public ShowRoomsView showRoomsPanel;
	public RoomDetailsView roomDetailsPanel;
	
	public BookingSearchView bookingSearchPanel;
	
	private CardLayout cardLayout;
	private JPanel container;
	
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
    
    //POR AHORA ESTÁ DESACTIVADO PARA AVANZAR
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
        Image img = icon.getImage().getScaledInstance(250, 80, Image.SCALE_SMOOTH);
        JLabel logo = new JLabel(new ImageIcon(img));

        panel.add(logo);

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
        
        // USUARIO
        JMenu usuario = new UnderlineMenu("Usuario");
        usuario.setMnemonic(KeyEvent.VK_U);
        mb.add(usuario);

        JMenuItem miCuenta = new JMenuItem("Mi cuenta");
        miCuenta.setMnemonic(KeyEvent.VK_C);
        usuario.add(miCuenta);

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
        JPanel inferiorPanel = new JPanel();
        inferiorPanel.setBackground(UIColors.HEADER);
        inferiorPanel.setBorder(new EmptyBorder(30,30,30,30));

        JLabel lblCopy = new JLabel("Copyright © 2026 ATLANTIS THE PALM, DUBAI. All rights reserved ");
        lblCopy.setFont(AppFont.normal());
        lblCopy.setForeground(Color.white);
        inferiorPanel.add(lblCopy);

        JLabel lblNumber = new JLabel("| Teléfono: 555-666-999-1 ");
        lblNumber.setFont(AppFont.normal());
        lblNumber.setForeground(Color.white);
        inferiorPanel.add(lblNumber);

        JLabel lblEmail = new JLabel("| Correo: atlantis_the_palm_dubai_info@atlantishotel.com");
        lblEmail.setFont(AppFont.normal());
        lblEmail.setForeground(Color.white);
        inferiorPanel.add(lblEmail);

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

        homePanel = new HomeView();
        new HomeController(homePanel, this);
        
        usersPanel = new UsersView();
        roomTypesPanel = new RoomTypesView();
        roomsPanel = new RoomsView();
        amenitiesPanel = new AmenitiesView();
        
        bookingSearchPanel = new BookingSearchView();
        new BookingSearchController(bookingSearchPanel);
        
        showRoomsPanel = new ShowRoomsView();
        new ShowRoomsController(showRoomsPanel, this);
        
        roomDetailsPanel = new RoomDetailsView();
        
        container.add(homePanel, HOME);
        container.add(usersPanel, ADMIN_USERS);
        container.add(roomTypesPanel, ADMIN_ROOMTYPES);
        container.add(roomsPanel, ADMIN_ROOMS);
        container.add(amenitiesPanel, ADMIN_AMENITIES);
        container.add(bookingSearchPanel, BOOKING_SEARCH);
        container.add(showRoomsPanel, SHOW_ROOMS);
        container.add(roomDetailsPanel, ROOM_DETAILS);
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

	public JMenuItem getBtnShowRooms() {
		return btnShowRooms;
	}

	public void setBtnRooms(JMenuItem btnRooms) {
		this.btnRooms = btnRooms;
	}	

}