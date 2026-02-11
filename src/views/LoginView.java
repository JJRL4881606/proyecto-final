package views;

import java.awt.Color;
import java.awt.Font;
import java.awt.Image;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

public class LoginView extends JPanel{
	
	public LoginView() {
		this.setBackground(new Color(250, 233, 190)); 
		this.setLayout(null);

		JLabel tituloVentana = new JLabel("Bienvenido a la Aplicación de Hotel");
		tituloVentana.setFont(new Font("Arial", Font.BOLD,30));
		tituloVentana.setBounds(40,30,700,100);
		add(tituloVentana);
		
		JLabel instrucciones = new JLabel("Ingrese sus datos para iniciar sesión");
		instrucciones.setFont(new Font("Arial", Font.BOLD,20));
		instrucciones.setBounds(40,60,500,100);
		add(instrucciones);
		
		JTextField usuario = new JTextField();
		usuario.setFont(new Font("Arial", Font.BOLD,25));
		usuario.setBounds(80,150,220,40);
		add(usuario);
		
		JPasswordField contrasena = new JPasswordField();
		contrasena.setFont(new Font("Arial", Font.BOLD,25));
		contrasena.setBounds(80,220,220,40);
		add(contrasena);
		
		JButton boton = new JButton("Iniciar sesión", new ImageIcon("src/img/login-icon.png"));
		boton.setBounds(80, 300, 220, 30);
		boton.setBackground(new Color(201, 162, 77));
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
