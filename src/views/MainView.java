package views;

import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.event.KeyEvent;
import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.UIManager;
import javax.swing.border.EmptyBorder;

import components.UnderlineMenu;
import controllers.HomeController;
import controllers.ReservationController;

import javax.swing.ImageIcon;
import utils.AppFont;
import utils.UIColors;

@SuppressWarnings("serial")
public class MainView extends JPanel{
	
	public static final String HOME = "HOME";
	public static final String USERS = "USERS";
	public static final String ROOMTYPES = "ROOMTYPES";
	public static final String RESERVATIONS = "RESERVATIONS";
	
	public JMenuItem btnHome;
	public JMenuItem btnUsers;
	public JMenuItem btnRoomTypes;
	public JMenuItem logOut;
	public HomeView homePanel;
	public UsersView usersPanel;
	public RoomTypesView roomTypesPanel;
	public ReservationView reservationPanel;
	
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
	    initializeComponents();
	    setVisible(true);
	}

    public void initializeComponents() 
    {	    
        add(headerSection(), BorderLayout.NORTH);
        add(content(), BorderLayout.CENTER);
        add(inferiorSection(), BorderLayout.SOUTH);
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
                
        // HABITACIONES
        JMenu habitaciones = new UnderlineMenu("Habitaciones");
        habitaciones.setMnemonic(KeyEvent.VK_H);
        mb.add(habitaciones);

        JMenuItem verHabitaciones = new JMenuItem("Ver habitaciones");
        verHabitaciones.setMnemonic(KeyEvent.VK_V);
        habitaciones.add(verHabitaciones);

        // RESERVAS
        JMenu reservas = new UnderlineMenu("Reservas");
        reservas.setMnemonic(KeyEvent.VK_R);
        mb.add(reservas);

        JMenuItem nuevaReserva = new JMenuItem("Nueva reserva");
        nuevaReserva.setMnemonic(KeyEvent.VK_N);
        reservas.add(nuevaReserva);
        
        // SISTEMA
    	JMenu sistema = new UnderlineMenu("Sistema");
    	sistema.setMnemonic(KeyEvent.VK_S);
        mb.add(sistema);

        btnHome = new JMenuItem("Inicio");
        btnHome.setMnemonic(KeyEvent.VK_I);
        sistema.add(btnHome);
        
        btnUsers = new JMenuItem("Ver Usuarios");
        btnUsers.setMnemonic(KeyEvent.VK_U);
        sistema.add(btnUsers);
        
        btnRoomTypes = new JMenuItem("Ver Tipos de Habitaciones");
        btnRoomTypes.setMnemonic(KeyEvent.VK_T);
        sistema.add(btnRoomTypes);

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

        JLabel lblCopy = new JLabel("Copyright © 2026 ATLANTIS THE PALM, DUBAI. All rights reserved");
        lblCopy.setFont(AppFont.normal());
        lblCopy.setForeground(Color.white);

        inferiorPanel.add(lblCopy);

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
        
        reservationPanel = new ReservationView();
        new ReservationController(reservationPanel);
        
        container.add(homePanel, HOME);
        container.add(usersPanel, USERS);
        container.add(roomTypesPanel, ROOMTYPES);
        container.add(reservationPanel, RESERVATIONS);
    }        
    
    private JPanel createTransparentPanel() {
        JPanel panel = new JPanel();
        panel.setOpaque(false);
        return panel;
    }
    
    public void showView(String view) {
        cardLayout.show(container, view);

        container.revalidate();
        container.repaint();

        revalidate();
        repaint();
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

}