package views;

import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Color;
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

import javax.swing.ImageIcon;
import utils.AppFont;
import utils.UIColors;

@SuppressWarnings("serial")
public class MainView extends JPanel{
	
	public static final String HOME = "HOME";
	public static final String USERS = "USERS";
	
	public JMenuItem btnUsers;
	public JMenuItem btnHome;
	public JMenuItem logOut;
	public UsersView usersPanel;
	public HomeView homePanel;
	
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

        ImageIcon icon = new ImageIcon(getClass().getResource("/img/logos/hotel-logo.png"));
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
        
        sistema.addSeparator();

        logOut = new JMenuItem("Cerrar sesión");
        logOut.setMnemonic(KeyEvent.VK_C);
        
        sistema.add(logOut); 

        // HABITACIONES
        JMenu habitaciones = new UnderlineMenu("Habitaciones");
        habitaciones.setMnemonic(KeyEvent.VK_H);
        mb.add(habitaciones);

        JMenuItem verHabitaciones = new JMenuItem("Ver habitaciones");
        verHabitaciones.setMnemonic(KeyEvent.VK_V);
        habitaciones.add(verHabitaciones);

        JMenuItem disponibilidad = new JMenuItem("Disponibilidad");
        disponibilidad.setMnemonic(KeyEvent.VK_D);
        habitaciones.add(disponibilidad);

        // RESERVAS
        JMenu reservas = new UnderlineMenu("Reservas");
        reservas.setMnemonic(KeyEvent.VK_R);
        mb.add(reservas);

        JMenuItem nuevaReserva = new JMenuItem("Nueva reserva");
        nuevaReserva.setMnemonic(KeyEvent.VK_N);
        reservas.add(nuevaReserva);

        JMenuItem misReservas = new JMenuItem("Mis reservas");
        misReservas.setMnemonic(KeyEvent.VK_M);
        reservas.add(misReservas);

        // INFORMACIÓN
        JMenu informacion = new UnderlineMenu("Información");
        informacion.setMnemonic(KeyEvent.VK_I);
        mb.add(informacion);

        JMenuItem verInformacion = new JMenuItem("Ver información");
        verInformacion.setMnemonic(KeyEvent.VK_V);
        informacion.add(verInformacion);

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
        lblCopy.setForeground(UIColors.HOME_TITLE);

        inferiorPanel.add(lblCopy);

        return inferiorPanel;
    }
    
    private void createViews() {
        cardLayout = new CardLayout();
        container = new JPanel(cardLayout);
                
        homePanel = new HomeView();
        new HomeController(homePanel);
        usersPanel = new UsersView();
        
        container.add(homePanel, HOME);
        container.add(usersPanel, USERS);
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
}