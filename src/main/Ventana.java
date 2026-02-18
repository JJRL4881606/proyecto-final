package main;

import javax.swing.BoxLayout;
import javax.swing.JFrame;
import views.LoginView;
import views.PanelLogin;

public class Ventana extends JFrame 
{
    public Ventana() 
    {
    	int ventanaW = 800;
    	int ventanaH = 800;
        this.setSize(ventanaW, ventanaH);
		this.setLocation(100,100); 
        this.setLocationRelativeTo(null);
        this.setTitle("Aplicación de Hotel");
        this.setResizable(false);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));

		PanelLogin login = new PanelLogin();

        this.setVisible(true);
    }
}









