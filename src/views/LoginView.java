package views;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridBagLayout;
import java.awt.Image;

import javax.imageio.ImageIO;
import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;

import components.RoundedPanel;

public class LoginView extends JPanel
{
	int ventanaCentroW = 400;
	public LoginView() 
	{
		this.setBackground(new Color(100,149,237)); 
		//setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
	    setLayout(new GridBagLayout());
		inicializarComponentes();
	}
		
	private void inicializarComponentes() 
	{
	    JPanel card = new RoundedPanel(50);
	    card.setLayout(new BoxLayout(card, BoxLayout.Y_AXIS));
	    card.setBackground(new Color(151, 210, 251));
	    card.setBorder(BorderFactory.createEmptyBorder(25, 35, 25, 35));
	    card.setMaximumSize(new Dimension(450, 350));
	    card.setAlignmentX(CENTER_ALIGNMENT);
	    
		/*crearTitulo();
		crearFormulario();
		crearBotones();*/
		
	    card.add(crearTitulo());
	    card.add(Box.createVerticalStrut(15));
	    card.add(new PanelFormulario());
	    card.add(Box.createVerticalStrut(20));
	    card.add(new PanelBotones());

	    add(card); 
	}
	
	/*private void crearBotones()
	{
		add(new PanelBotones());		
	}

	private void crearFormulario() 
	{
		add(new PanelFormulario());
	}*/

	private JPanel crearTitulo() 
	{
	    JPanel panel = new JPanel();
	    panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
	    panel.setOpaque(false);
	    
		JLabel lblTitulo = new JLabel("Bienvenido a la Aplicación de Hotel");
		lblTitulo.setBorder(new EmptyBorder(50, 20, 20, 20)); 
		lblTitulo.setFont(new Font("Arial", Font.BOLD,30));
	    lblTitulo.setAlignmentX(CENTER_ALIGNMENT); 
		add(lblTitulo);
		
		JLabel lblInstrucciones = new JLabel("Ingrese sus datos para iniciar sesión");
		lblInstrucciones.setBorder(new EmptyBorder(10, 20, 30, 20)); 
		lblInstrucciones.setFont(new Font("Arial", Font.BOLD,20));
		lblInstrucciones.setAlignmentX(CENTER_ALIGNMENT); 

	    panel.add(lblTitulo);
	    panel.add(Box.createVerticalStrut(8));
	    panel.add(lblInstrucciones);

	    return panel;

	}

}
