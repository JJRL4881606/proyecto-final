package views;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Image;
import java.awt.Toolkit;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

import components.GradientPanel;

@SuppressWarnings("serial")
public class LoginWindow extends JFrame 
{
    public LoginWindow() 
    {
    	int ventanaW = 1000;
	   	int ventanaH = 800;
        this.setSize(ventanaW, ventanaH);
		this.setLocation(100,100); 
        this.setLocationRelativeTo(null);
        this.setTitle("Login | HOTEL MJ");
        this.setResizable(true);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        
        // Fondo degradado
        GradientPanel fondo = new GradientPanel(
            new Color(100,149,237), 
            new Color(25,25,112)
        );

        fondo.setLayout(new BorderLayout());
        setContentPane(fondo);    

        //Agregar ícono aplicación
        Toolkit tk = Toolkit.getDefaultToolkit();
        Image icono = tk.getImage("src/img/iconoRegistroUsuario.png");
        setIconImage(icono);
                
		//Agregar el panel
        LoginView loginview = new LoginView(this);
        add(crearVistaConScroll(loginview), BorderLayout.CENTER);        
        
        this.setVisible(true);
    }
    
    private JScrollPane crearVistaConScroll(JPanel panel) {
        JScrollPane scroll = new JScrollPane(panel);
        scroll.setBorder(null);
        scroll.getVerticalScrollBar().setUnitIncrement(12);
        scroll.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        return scroll;
    }
}