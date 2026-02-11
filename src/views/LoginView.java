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
		
		inicializarComponentes();
	}
		
	private void inicializarComponentes() {
		crearTitulo();
		crearBotones();
		crearFormulario();
		crearMensajesError();
	}
	
	private void crearBotones() {
		JButton botonIniciarSesion = new JButton("Iniciar sesión", new ImageIcon("src/img/login-icon.png"));
		botonIniciarSesion.setBounds(80, 500, 220, 40);
		botonIniciarSesion.setBackground(new Color(201, 162, 77));
		botonIniciarSesion.setForeground(Color.BLACK);
		botonIniciarSesion.setToolTipText("Haz click aquí");
		botonIniciarSesion.setFont(new Font("Arial", Font.BOLD, 20));
		
		JButton botonCrearCuenta = new JButton("Crear cuenta", new ImageIcon("src/img/login-icon.png"));
		botonCrearCuenta.setBounds(80, 570, 220, 40);
		botonCrearCuenta.setBackground(new Color(201, 162, 77));
		botonCrearCuenta.setForeground(Color.BLACK);
		botonCrearCuenta.setToolTipText("Haz click aquí");
		botonCrearCuenta.setFont(new Font("Arial", Font.BOLD, 20));
		
		try{
			Image icono = ImageIO.read(getClass().getResource("/img/login-icon.png"));
			icono = icono.getScaledInstance(30,30, Image.SCALE_SMOOTH);
			botonIniciarSesion.setIcon(new ImageIcon(icono));
		}catch(Exception ex) {
			System.out.println("No está la imagen del ícono");
		}
		
		add(botonIniciarSesion);	
		add(botonCrearCuenta);
	}
	
	private void crearFormulario() {
		JLabel lblUsuario = new JLabel("Ingrese el usuario: ");
		lblUsuario.setFont(new Font("Arial", Font.BOLD,20));
		lblUsuario.setBounds(80,130,500,100);
		add(lblUsuario);
		
		JTextField usuario = new JTextField();
		usuario.setFont(new Font("Arial", Font.BOLD,25));
		usuario.setBounds(80,200,220,40);
		add(usuario);
		
		JLabel lblContrasena = new JLabel("Ingrese la contraseña: ");
		lblContrasena.setFont(new Font("Arial", Font.BOLD,20));
		lblContrasena.setBounds(80,230,500,100);
		add(lblContrasena);
		
		JPasswordField contrasena = new JPasswordField();
		contrasena.setFont(new Font("Arial", Font.BOLD,25));
		contrasena.setBounds(80,300,220,40);
		add(contrasena);
	}

	private void crearTitulo() {
		JLabel lblTitulo = new JLabel("Bienvenido a la Aplicación de Hotel");
		lblTitulo.setFont(new Font("Arial", Font.BOLD,30));
		lblTitulo.setBounds(40,30,700,100);
		add(lblTitulo);
		
		JLabel lblInstrucciones = new JLabel("Ingrese sus datos para iniciar sesión");
		lblInstrucciones.setFont(new Font("Arial", Font.BOLD,20));
		lblInstrucciones.setBounds(40,60,500,100);
		add(lblInstrucciones);	
	}
	
	private void crearMensajesError() {
		JLabel lblUsuarioObligatorio = new JLabel("Usuario obligatoria");
		lblUsuarioObligatorio.setFont(new Font("Arial", Font.BOLD,12));
		lblUsuarioObligatorio.setBounds(80,200,500,100);
		lblUsuarioObligatorio.setForeground(Color.RED);

		add(lblUsuarioObligatorio);
		
		JLabel lblContraObligatoria = new JLabel("Contraseña obligatoria");
		lblContraObligatoria.setFont(new Font("Arial", Font.BOLD,12));
		lblContraObligatoria.setBounds(80,300,500,100);
		lblContraObligatoria.setForeground(Color.RED);
		add(lblContraObligatoria);
		
		JLabel lblCredIncorrectas = new JLabel("Credenciales incorrectas");
		lblCredIncorrectas.setFont(new Font("Arial", Font.BOLD,12));
		lblCredIncorrectas.setBounds(80,320,500,100);
		lblCredIncorrectas.setForeground(Color.RED);
		add(lblCredIncorrectas);
	}
}
