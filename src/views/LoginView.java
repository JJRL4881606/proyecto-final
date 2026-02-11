package views;

import java.awt.Color;
import java.awt.Font;
import java.awt.Image;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JPanel;

public class LoginView extends JPanel{
	
	public LoginView() {
		this.setBackground(new Color(210, 180, 140)); 
		this.setLayout(null);

		JButton boton = new JButton("Iniciar sesión", new ImageIcon("src/img/login-icon.png"));
		boton.setBounds(50, 50, 230, 30);
		boton.setBackground(new Color(250, 233, 190));
		boton.setForeground(Color.BLACK);
		boton.setToolTipText("Haz click aquí");
		boton.setFont(new Font("Arial", Font.BOLD, 20));
		
		try{
			Image icono = ImageIO.read(getClass().getResource("/img/login-icon.png"));
			icono = icono.getScaledInstance(30,30, Image.SCALE_SMOOTH);
			boton.setIcon(new ImageIcon(icono));
		}catch(Exception ex) {
			System.out.println("No está la imagen del ícono");
		}
		
		add(boton);	
	}
}
