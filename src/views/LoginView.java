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
		
		JLabel usuarioLabel = new JLabel("Ingrese el usuario: ");
		usuarioLabel.setFont(new Font("Arial", Font.BOLD,20));
		usuarioLabel.setBounds(80,130,500,100);
		add(usuarioLabel);
		
		JTextField usuario = new JTextField();
		usuario.setFont(new Font("Arial", Font.BOLD,25));
		usuario.setBounds(80,200,220,40);
		add(usuario);
		
		JLabel contrasenaLabel = new JLabel("Ingrese la contraseña: ");
		contrasenaLabel.setFont(new Font("Arial", Font.BOLD,20));
		contrasenaLabel.setBounds(80,230,500,100);
		add(contrasenaLabel);
		
		JPasswordField contrasena = new JPasswordField();
		contrasena.setFont(new Font("Arial", Font.BOLD,25));
		contrasena.setBounds(80,300,220,40);
		add(contrasena);
		
		JButton botonIniciarSesion = new JButton("Iniciar sesión", new ImageIcon("src/img/login-icon.png"));
		botonIniciarSesion.setBounds(80, 370, 220, 40);
		botonIniciarSesion.setBackground(new Color(201, 162, 77));
		botonIniciarSesion.setForeground(Color.BLACK);
		botonIniciarSesion.setToolTipText("Haz click aquí");
		botonIniciarSesion.setFont(new Font("Arial", Font.BOLD, 20));
		
		try{
			Image icono = ImageIO.read(getClass().getResource("/img/login-icon.png"));
			icono = icono.getScaledInstance(30,30, Image.SCALE_SMOOTH);
			botonIniciarSesion.setIcon(new ImageIcon(icono));
		}catch(Exception ex) {
			System.out.println("No está la imagen del ícono");
		}
		
		add(botonIniciarSesion);	
		
		JButton botonCrearCuenta = new JButton("Crear cuenta", new ImageIcon("src/img/login-icon.png"));
		botonCrearCuenta.setBounds(80, 430, 220, 40);
		botonCrearCuenta.setBackground(new Color(201, 162, 77));
		botonCrearCuenta.setForeground(Color.BLACK);
		botonCrearCuenta.setToolTipText("Haz click aquí");
		botonCrearCuenta.setFont(new Font("Arial", Font.BOLD, 20));
		
		add(botonCrearCuenta);
	}
}
