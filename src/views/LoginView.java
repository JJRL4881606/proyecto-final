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

public class LoginView extends JPanel
{
	int ventanaCentroW = 400;
	public LoginView() 
	{
		this.setBackground(new Color(151, 210, 251)); 
		this.setLayout(null);
		inicializarComponentes();
	}
		
	private void inicializarComponentes() 
	{
		crearTitulo();
		crearBotones();
		crearFormulario();
		crearMensajesError();
	}
	
	private void crearBotones() 
	{
		JButton botonIniciarSesion = new JButton("Iniciar sesión", new ImageIcon("src/img/login-icon.png"));
		botonIniciarSesion.setBounds(ventanaCentroW - 210, 440, 200, 30);
		botonIniciarSesion.setBackground(new Color(241, 171, 134));
		botonIniciarSesion.setForeground(Color.BLACK);
		botonIniciarSesion.setToolTipText("Haz click aquí");
		botonIniciarSesion.setFont(new Font("Arial", Font.BOLD, 20));
		
		JButton botonCrearCuenta = new JButton("Crear cuenta", new ImageIcon("src/img/login-icon.png"));
		botonCrearCuenta.setBounds(ventanaCentroW + 10, 440, 200, 30);
		botonCrearCuenta.setBackground(new Color(241, 171, 134));
		botonCrearCuenta.setForeground(Color.BLACK);
		botonCrearCuenta.setToolTipText("Haz click aquí");
		botonCrearCuenta.setFont(new Font("Arial", Font.BOLD, 20));
		
		try
		{
			Image icono = ImageIO.read(getClass().getResource("/img/login-icon.png"));
			icono = icono.getScaledInstance(30,30, Image.SCALE_SMOOTH);
			botonIniciarSesion.setIcon(new ImageIcon(icono));
		}
		catch(Exception ex) 
		{
			System.out.println("No está la imagen del ícono");
		}
		
		add(botonIniciarSesion);	
		add(botonCrearCuenta);
	}
	
	private void crearFormulario() 
	{
		JLabel lblUsuario = new JLabel("Ingrese el usuario: ");
		lblUsuario.setFont(new Font("Arial", Font.BOLD,20));
		lblUsuario.setBounds(ventanaCentroW - 125,200,250,30);
		add(lblUsuario);
		
		JTextField usuario = new JTextField();
		usuario.setFont(new Font("Arial", Font.BOLD,25));
		usuario.setBounds(ventanaCentroW - 125,240,250,35);
		add(usuario);
		
		JLabel lblContrasena = new JLabel("Ingrese la contraseña: ");
		lblContrasena.setFont(new Font("Arial", Font.BOLD,20));
		lblContrasena.setBounds(ventanaCentroW - 125,305,250,30);
		add(lblContrasena);
		
		JPasswordField contrasena = new JPasswordField();
		contrasena.setFont(new Font("Arial", Font.BOLD,25));
		contrasena.setBounds(ventanaCentroW - 125,340,250,25);
		add(contrasena);
	}

	private void crearTitulo() 
	{
		JLabel lblTitulo = new JLabel("Bienvenido a la Aplicación de Hotel");
		lblTitulo.setFont(new Font("Arial", Font.BOLD,30));
		lblTitulo.setBounds(ventanaCentroW - 255,100,510,30);
		add(lblTitulo);
		
		JLabel lblInstrucciones = new JLabel("Ingrese sus datos para iniciar sesión");
		lblInstrucciones.setFont(new Font("Arial", Font.BOLD,20));
		lblInstrucciones.setBounds(ventanaCentroW - 175,150,350,20);
		add(lblInstrucciones);	
	}
	
	private void crearMensajesError() 
	{
		JLabel lblUsuarioObligatorio = new JLabel("Usuario obligatorio");
		lblUsuarioObligatorio.setFont(new Font("Arial", Font.BOLD,12));
		lblUsuarioObligatorio.setBounds(ventanaCentroW - 80,290,160,12);
		lblUsuarioObligatorio.setForeground(Color.RED);

		add(lblUsuarioObligatorio);
		
		JLabel lblContraObligatoria = new JLabel("Contraseña obligatoria");
		lblContraObligatoria.setFont(new Font("Arial", Font.BOLD,12));
		lblContraObligatoria.setBounds(ventanaCentroW - 80,380,160,12);
		lblContraObligatoria.setForeground(Color.RED);
		add(lblContraObligatoria);
		
		JLabel lblCredIncorrectas = new JLabel("Credenciales incorrectas");
		lblCredIncorrectas.setFont(new Font("Arial", Font.BOLD,12));
		lblCredIncorrectas.setBounds(ventanaCentroW - 80,500,160,12);
		lblCredIncorrectas.setForeground(Color.RED);
		add(lblCredIncorrectas);
	}
}
