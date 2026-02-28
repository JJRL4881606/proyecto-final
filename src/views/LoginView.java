package views;

import java.awt.GridBagLayout;

import javax.swing.BoxLayout;
import javax.swing.JLabel;
import javax.swing.JPanel;
import java.awt.Color;
import java.awt.Dimension;
import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.border.EmptyBorder;

import components.RoundedPanel;
import utils.AppFont;

@SuppressWarnings("serial")
public class LoginView extends JPanel
{
	int ventanaCentroW = 400;
	public LoginView() 
	{
		this.setBackground(new Color(100,149,237)); 
	    setLayout(new GridBagLayout());
        
		inicializarComponentes();
        setVisible(true);

	}

	private void inicializarComponentes() 
	{
	    JPanel card = new RoundedPanel(50);
	    card.setLayout(new BoxLayout(card, BoxLayout.Y_AXIS));
	    card.setBackground(new Color(151, 210, 251));
	    card.setBorder(BorderFactory.createEmptyBorder(25, 35, 25, 35));
	    card.setMaximumSize(new Dimension(450, 350));
	    card.setAlignmentX(CENTER_ALIGNMENT);
		
	    card.add(crearTitulo());
	    card.add(Box.createVerticalStrut(15));
	    card.add(new PanelFormulario());
	    card.add(Box.createVerticalStrut(20));
	    card.add(new PanelBotones());

	    add(card); 
	}
	
	private JPanel crearTitulo() 
	{
	    JPanel panel = new JPanel();
	    panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
	    panel.setOpaque(false);
	    
		JLabel lblTitulo = new JLabel("Bienvenido a la Aplicación de Hotel");
		lblTitulo.setBorder(new EmptyBorder(50, 20, 20, 20)); 
		lblTitulo.setFont(AppFont.title());
	    lblTitulo.setAlignmentX(CENTER_ALIGNMENT);
		add(lblTitulo);
		
		JLabel lblInstrucciones = new JLabel("Ingrese sus datos para iniciar sesión");
		lblInstrucciones.setBorder(new EmptyBorder(10, 20, 30, 20)); 
		lblInstrucciones.setFont(AppFont.subtitle());
		lblInstrucciones.setAlignmentX(CENTER_ALIGNMENT); 

	    panel.add(lblTitulo);
	    panel.add(Box.createVerticalStrut(8));
	    panel.add(lblInstrucciones);

	    return panel;

	}

}
