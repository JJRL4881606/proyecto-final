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
import javax.swing.border.EmptyBorder;

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
	
	private void crearBotones()
	{
		add(new PanelBotones());		
	}

	private void crearFormulario() 
	{
		add(new PanelFormulario());
	}

	private void crearTitulo() 
	{
		JLabel lblTitulo = new JLabel("Bienvenido a la Aplicación de Hotel");
		lblTitulo.setBorder(new EmptyBorder(30, 20, 20, 20)); 
		lblTitulo.setFont(new Font("Arial", Font.BOLD,30));
	    lblTitulo.setAlignmentX(CENTER_ALIGNMENT); 
		add(lblTitulo);
		
		JLabel lblInstrucciones = new JLabel("Ingrese sus datos para iniciar sesión");
		lblInstrucciones.setBorder(new EmptyBorder(10, 20, 30, 20)); 
		lblInstrucciones.setFont(new Font("Arial", Font.BOLD,20));
		lblInstrucciones.setAlignmentX(CENTER_ALIGNMENT); 

		add(lblInstrucciones);	
	}

}
