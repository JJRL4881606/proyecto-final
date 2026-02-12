package main;

import javax.swing.JFrame;
import views.LoginView;
import views.MiPanel;

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

        LoginView login = new LoginView();
        this.add(login);  
        
        this.setVisible(true);
    }
}









