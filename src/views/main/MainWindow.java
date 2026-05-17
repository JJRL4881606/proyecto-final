package views.main;

import java.awt.BorderLayout;
import java.awt.Image;
import java.awt.Toolkit;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

import controllers.main.MainController;
import models.User;

@SuppressWarnings("serial")
public class MainWindow extends JFrame {
	
	private MainView mainView;
	private JScrollPane scroll;
	private User user;

    public MainWindow() {
        this.setExtendedState(JFrame.MAXIMIZED_BOTH);
        this.setTitle("ATLANTIS THE PALM, DUBAI");
        this.setResizable(true);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setLocationRelativeTo(null);
        
        JPanel background = new JPanel();
        background.setLayout(new BorderLayout());
        setContentPane(background);    

        // Agregar icono
        Image icon = Toolkit.getDefaultToolkit().getImage(
    	    getClass().getResource("/assets/img/logos/hotel-icon.png")
    	);
        setIconImage(icon);
         
        // Agregar el panel con scroll
        mainView = new MainView();
        new MainController(mainView, this);
        add(createViewScroll(mainView), BorderLayout.CENTER);
        
        this.setVisible(true);
    }
    
    private JScrollPane createViewScroll(JPanel panel) {
        scroll = new JScrollPane(panel);
        scroll.setBorder(null);
        scroll.getVerticalScrollBar().setUnitIncrement(12);
        scroll.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        return scroll;
    }
    
    public MainView getMainView() {
        return mainView;
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