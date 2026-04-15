package views;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.UIManager;
import javax.swing.border.EmptyBorder;

import components.RoundedMenuBar;

import javax.swing.ImageIcon;

import utils.AppFont;

@SuppressWarnings("serial")
public class HomeView extends JPanel{
	
	JMenuItem cerrarSesion;

	public HomeView() {
	    Color hover = new Color(210,210,210);
	    UIManager.put("Menu.selectionBackground", hover);
	    UIManager.put("MenuItem.selectionBackground", hover);
	    UIManager.put("MenuBar.highlight", hover);
	    UIManager.put("Menu.borderPainted", false);
	    UIManager.put("MenuItem.borderPainted", false);

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
        superiorPanel.setBackground(new Color(30,144,255));
        superiorPanel.setBorder(new EmptyBorder(30,30,35,30));

        superiorPanel.add(headerLeftSection());
        superiorPanel.add(headerCenterSection());
        superiorPanel.add(headerRightSection());

        return superiorPanel;
    }
    
    public JPanel headerCenterSection(){
        JPanel panel = new JPanel(new GridBagLayout());
        panel.setOpaque(false);

        JPanel titleBlock = new JPanel();
        titleBlock.setLayout(new BoxLayout(titleBlock, BoxLayout.Y_AXIS));
        titleBlock.setOpaque(false);

        titleBlock.add(createTitle());
        titleBlock.add(createSubtitle());

        panel.add(titleBlock);

        return panel;
    }
    
    public JPanel headerRightSection(){
        JPanel panel = new JPanel(new GridBagLayout());
        panel.setOpaque(false);

        JMenuBar menu = createMenu();
        
        panel.add(menu);

        return panel;
    }
    
    public JPanel headerLeftSection(){
        JPanel panel = new JPanel();
        panel.setOpaque(false);
        return panel;
    }
    
    public JPanel createTitle(){
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.X_AXIS));
        panel.setOpaque(false);
        panel.setAlignmentX(Component.CENTER_ALIGNMENT);

        ImageIcon icon = new ImageIcon(getClass().getResource("/img/hotel-icon.png"));
        Image img = icon.getImage().getScaledInstance(60, 60, Image.SCALE_SMOOTH);
        JLabel logo = new JLabel(new ImageIcon(img));

        JLabel lblTitulo = new JLabel("HOME - HOTEL MJ");
        lblTitulo.setBorder(new EmptyBorder(0,15,0,0));
        lblTitulo.setFont(AppFont.title());

        panel.add(logo);
        panel.add(lblTitulo);

        return panel;
    }
    
    public JPanel createSubtitle() {
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.X_AXIS));
        panel.setOpaque(false);
        
        JLabel lblInstrucciones = new JLabel("Bienvenido a la página principal del Hotel MJ");
        lblInstrucciones.setBorder(new EmptyBorder(20, 20, 0, 20)); 
        lblInstrucciones.setFont(AppFont.subtitle());
        lblInstrucciones.setAlignmentX(CENTER_ALIGNMENT);
        
        panel.add(lblInstrucciones);
		return panel;
    }
    
    public JMenuBar createMenu() {

    	RoundedMenuBar mb = new RoundedMenuBar();
    	mb.setFont(AppFont.big());
    	mb.setBorder(BorderFactory.createEmptyBorder(5,10,5,10));
    	mb.setOpaque(false);
    	mb.setBackground(new Color(0,0,0,0));

        // HABITACIONES
    	JMenu habitaciones = new JMenu("Habitaciones");
    	applyHover(habitaciones);
        habitaciones.setOpaque(true);
        habitaciones.setBorder(new EmptyBorder(5,15,5,15));
        habitaciones.setMnemonic(KeyEvent.VK_H);
        mb.add(habitaciones);

        JMenuItem verHabitaciones = new JMenuItem("Ver habitaciones");
        verHabitaciones.setMnemonic(KeyEvent.VK_V);
        habitaciones.add(verHabitaciones);

        JMenuItem disponibilidad = new JMenuItem("Disponibilidad");
        disponibilidad.setMnemonic(KeyEvent.VK_D);
        habitaciones.add(disponibilidad);

        // RESERVAS
        JMenu reservas = new JMenu("Reservas");
        applyHover(reservas);
        reservas.setOpaque(true);
        reservas.setBorder(new EmptyBorder(5,15,5,15));
        reservas.setMnemonic(KeyEvent.VK_R);
        mb.add(reservas);

        JMenuItem nuevaReserva = new JMenuItem("Nueva reserva");
        nuevaReserva.setMnemonic(KeyEvent.VK_N);
        reservas.add(nuevaReserva);

        JMenuItem misReservas = new JMenuItem("Mis reservas");
        misReservas.setMnemonic(KeyEvent.VK_M);
        reservas.add(misReservas);

        // INFORMACIÓN
        JMenu informacion = new JMenu("Información");
        applyHover(informacion);
        informacion.setOpaque(true);
        informacion.setBorder(new EmptyBorder(5,15,5,15));
        informacion.setMnemonic(KeyEvent.VK_I);
        mb.add(informacion);

        JMenuItem verInformacion = new JMenuItem("Ver información");
        verInformacion.setMnemonic(KeyEvent.VK_V);
        informacion.add(verInformacion);
        
        // SISTEMA
        JMenu sistema = new JMenu("Sistema");
        applyHover(sistema);
        sistema.setOpaque(true);
        sistema.setBorder(new EmptyBorder(5,15,5,15));
        sistema.setMnemonic(KeyEvent.VK_S);
        mb.add(sistema);

        JMenuItem inicio = new JMenuItem("Inicio");
        inicio.setMnemonic(KeyEvent.VK_I);
        sistema.add(inicio);
        sistema.addSeparator();

        cerrarSesion = new JMenuItem("Cerrar sesión");
        cerrarSesion.setMnemonic(KeyEvent.VK_C);
        
        sistema.add(cerrarSesion);

        return mb;
    }
    
    //CONTENT
    public JPanel content(){
    	JPanel contentPanel = new JPanel();
    	contentPanel.setBackground(new Color(0,0,0));
    	return contentPanel;
    }
    
    //INFERIOR
    
    public JPanel inferiorSection() {
        JPanel inferiorPanel = new JPanel();
        inferiorPanel.setBackground(new Color(30,144,255));
        inferiorPanel.setBorder(new EmptyBorder(30,30,30,30));

        JLabel relleno1 = new JLabel("texto de relleno");
        relleno1.setFont(AppFont.big());

        inferiorPanel.add(relleno1);

        return inferiorPanel;
    }
    
    private void applyHover(JMenu menu) {

        Color normal = new Color(255,255,253);
        Color hover = new Color(210,210,210);

        menu.setOpaque(true);
        menu.setBackground(normal);

        menu.addMouseListener(new MouseAdapter() {

            @Override
            public void mouseEntered(MouseEvent e) {
                menu.setBackground(hover);
            }

            @Override
            public void mouseExited(MouseEvent e) {
                menu.setBackground(normal);
            }

        });
    }
    
    public JMenuItem getCerrarSesion() {
        return cerrarSesion;
    }
}