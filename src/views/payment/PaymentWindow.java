package views.payment;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.KeyEvent;

import javax.swing.*;
import javax.swing.border.EmptyBorder;

import components.UnderlineMenu;
import utils.AppFont;
import utils.UIColors;

public class PaymentWindow extends JFrame {
	
	private PaymentWindow paymentWindow;
    private PaymentView paymentView;
    private JScrollPane scroll;

    public PaymentWindow() {

    		UIManager.put("Menu.borderPainted", false);
	    UIManager.put("MenuItem.borderPainted", false);
	    UIManager.put("Menu.selectionBackground", UIColors.HEADER);
	    UIManager.put("Menu.selectionForeground", Color.WHITE);
	    UIManager.put("MenuItem.selectionBackground", UIColors.HEADER);
	    UIManager.put("MenuItem.selectionForeground", Color.WHITE);
	    
	    this.setBackground(new Color(100,149,237)); 
	    setLayout(new BorderLayout());
	    add(headerSection(), BorderLayout.NORTH);
    	
        paymentView = new PaymentView();

        this.setExtendedState(JFrame.MAXIMIZED_BOTH);
        this.setTitle("ATLANTIS THE PALM, FORMA DE PAGO");
        this.setResizable(true);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setLocationRelativeTo(null);
		
		JPanel background = new JPanel();
        background.setLayout(new BorderLayout());
        setContentPane(background);    

        // Agregar icono
        Image icon = Toolkit.getDefaultToolkit().getImage(
    	    getClass().getResource("/assets/img/logos/hotel-icon.png"));
        
        setIconImage(icon);
        
        
        this.setVisible(true);
		
        add(paymentView);
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
    
    private JPanel createTransparentPanel() {
        JPanel panel = new JPanel();
        panel.setOpaque(false);
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

        //FALTA CREAR ESTAS VISTAS
        /*
        JMenuItem miCuenta = new JMenuItem("Mi cuenta");
        miCuenta.setMnemonic(KeyEvent.VK_C);
        usuario.add(miCuenta);

        JMenuItem misReservas = new JMenuItem("Mis reservas");
        misReservas.setMnemonic(KeyEvent.VK_R);
        usuario.add(misReservas);
        */
        
        usuario.addSeparator();

        // SISTEMA
    	JMenu sistema = new UnderlineMenu("Sistema");
    	sistema.setMnemonic(KeyEvent.VK_S);
        mb.add(sistema);

        JMenuItem btnExit = new JMenuItem("Salir");
        btnExit.setMnemonic(KeyEvent.VK_I);
        sistema.add(btnExit);

        return mb;
    }
    
    public PaymentView getPaymentView() {
        return paymentView;
    }
    
    public PaymentWindow getPaymentWindow() {
        return paymentWindow;
    }
    
	public void setWindowSize(int width, int height) {
		setSize(width, height);
	}
	
	public void setWindowLocation(int x, int y) {
		setLocation(x, y);
	}
	
	public JScrollPane getScroll() {
	    return scroll;
	}
}