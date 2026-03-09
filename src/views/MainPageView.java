package views;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.event.KeyEvent;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import components.RoundedMenuBar;

import javax.swing.ImageIcon;

import utils.AppFont;

@SuppressWarnings("serial")
public class MainPageView extends JPanel{
	
	public MainPageView(){
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

        superiorPanel.setBorder(new EmptyBorder(15,20,15,20));

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

        ImageIcon icon = new ImageIcon("C:/Users/Usuario/eclipse-workspace/ProyectoFinal/src/img/logoHotel.png");
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
        lblInstrucciones.setBorder(new EmptyBorder(0, 20, 0, 20)); 
        lblInstrucciones.setFont(AppFont.subtitle());
        lblInstrucciones.setAlignmentX(CENTER_ALIGNMENT);
        
        panel.add(lblInstrucciones);
		return panel;
    }
    
    public JMenuBar createMenu() {

    	RoundedMenuBar mb = new RoundedMenuBar();
    	mb.setFont(AppFont.big());


        // SISTEMA
        JMenu sistema = new JMenu("Sistema");
        sistema.setBorder(new EmptyBorder(5,15,5,15));
        sistema.setMnemonic(KeyEvent.VK_S);
        mb.add(sistema);

        JMenuItem inicio = new JMenuItem("Inicio");
        sistema.add(inicio);

        sistema.addSeparator();

        JMenuItem cerrarSesion = new JMenuItem("Cerrar sesión");
        sistema.add(cerrarSesion);

        // HABITACIONES
        JMenu habitaciones = new JMenu("Habitaciones");
        habitaciones.setBorder(new EmptyBorder(5,15,5,15));
        habitaciones.setMnemonic(KeyEvent.VK_H);
        mb.add(habitaciones);

        JMenuItem verHabitaciones = new JMenuItem("Ver habitaciones");
        habitaciones.add(verHabitaciones);

        JMenuItem disponibilidad = new JMenuItem("Disponibilidad");
        habitaciones.add(disponibilidad);

        // RESERVAS
        JMenu reservas = new JMenu("Reservas");
        reservas.setBorder(new EmptyBorder(5,15,5,15));
        reservas.setMnemonic(KeyEvent.VK_R);
        mb.add(reservas);

        JMenuItem nuevaReserva = new JMenuItem("Nueva reserva");
        reservas.add(nuevaReserva);

        JMenuItem misReservas = new JMenuItem("Mis reservas");
        reservas.add(misReservas);

        // CLIENTES
        JMenu clientes = new JMenu("Clientes");
        clientes.setBorder(new EmptyBorder(5,15,5,15));
        clientes.setMnemonic(KeyEvent.VK_C);
        mb.add(clientes);

        JMenuItem registrarCliente = new JMenuItem("Registrar cliente");
        clientes.add(registrarCliente);

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

        inferiorPanel.setBorder(new EmptyBorder(15,20,15,20));

        JLabel relleno1 = new JLabel("texto de relleno");
        relleno1.setFont(AppFont.big());

        inferiorPanel.add(relleno1);

        return inferiorPanel;
    }
}
