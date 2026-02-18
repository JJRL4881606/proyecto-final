package views;

import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Image;

import javax.imageio.ImageIO;
import javax.swing.BoxLayout;
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
		setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
		inicializarComponentes();
	}
		
	private void inicializarComponentes() 
	{
		crearTitulo();
		crearFormulario();
		crearBotones();
	}
	
	private void crearBotones() {
		PanelBotones botonesLogin = new PanelBotones();
		add(botonesLogin);
		
	}

	private void crearFormulario() 
	{
		//setLayout(new FlowLayout(FlowLayout.RIGHT, 50, 50));


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

}
