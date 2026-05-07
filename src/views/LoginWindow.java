package views;

import java.awt.BorderLayout;
import java.awt.Image;
import java.awt.Toolkit;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

import controllers.LoginController;

@SuppressWarnings("serial")
public class LoginWindow extends JFrame {
	
	private LoginView loginView;

    public LoginWindow() {
        this.setExtendedState(JFrame.MAXIMIZED_BOTH);
        this.setTitle("Login | ATLANTIS THE PALM, DUBAI");
        this.setResizable(true);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        
        JPanel background = new JPanel();
        background.setLayout(new BorderLayout());
        setContentPane(background);    

        //Agregar icono
        Image icon = Toolkit.getDefaultToolkit().getImage(
    	    getClass().getResource("/assets/img/icons/registration-icon.png")
    	);
        setIconImage(icon);
                
		//Agregar panel
        LoginView loginview = new LoginView();
        new LoginController(loginview);
        background.add(createViewScroll(loginview), BorderLayout.CENTER);
                
        this.setVisible(true);
    }
    
    private JScrollPane createViewScroll(JPanel panel) {
        JScrollPane scroll = new JScrollPane(panel);
        
        scroll.setBorder(null);
        scroll.setOpaque(false);
        scroll.getViewport().setOpaque(false);

        scroll.getVerticalScrollBar().setUnitIncrement(12);
        scroll.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);

        return scroll;
    }
    
	public LoginView getLoginView() {
		return loginView;
	}

}