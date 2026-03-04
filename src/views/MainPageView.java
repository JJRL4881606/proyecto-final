package views;

import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import components.RoundedPanel;
import utils.AppFont;

public class MainPageView extends JPanel{
	
	public MainPageView(){
		this.setBackground(new Color(100,149,237)); 
	    setLayout(new GridBagLayout());
	    initializeComponents();
        setVisible(true);
	}
	
    public void initializeComponents() 
    {
	    JPanel card = new RoundedPanel(50);
	    card.setLayout(new BoxLayout(card, BoxLayout.Y_AXIS));
	    card.setBackground(new Color(151, 210, 251));
	    card.setBorder(BorderFactory.createEmptyBorder(25, 35, 25, 35));
	    card.setMaximumSize(new Dimension(700, Integer.MAX_VALUE));
	    card.setAlignmentX(CENTER_ALIGNMENT);
	    card.putClientProperty("FlatLaf.style", "arc:20");
	    
	    card.add(createTitle());
	    card.add(Box.createRigidArea(new Dimension(0, 10)));

	    GridBagConstraints gbc = new GridBagConstraints();
	    gbc.gridx = 0;
	    gbc.gridy = 0;
	    gbc.anchor = GridBagConstraints.CENTER;
	    gbc.insets = new java.awt.Insets(40, 40, 40, 40);
	    add(card, gbc);
    }
    public JPanel createTitle(){
	    JPanel panel = new JPanel();
	    panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
	    panel.setOpaque(false);
	    
    	JLabel lblTitulo = new JLabel("HOME - HOTEL MJ");
    	lblTitulo.setBorder(new EmptyBorder(30, 20, 20, 20)); 
    	lblTitulo.setFont(AppFont.title());
        lblTitulo.setAlignmentX(CENTER_ALIGNMENT);
    	
    	JLabel lblInstrucciones = new JLabel("Bienvenido a la página principal del Hotel MJ");
    	lblInstrucciones.setBorder(new EmptyBorder(10, 20, 30, 20)); 
    	lblInstrucciones.setFont(AppFont.subtitle());
    	lblInstrucciones.setAlignmentX(CENTER_ALIGNMENT); 

        panel.add(lblTitulo);
        panel.add(Box.createVerticalStrut(8));
        panel.add(lblInstrucciones);
        
        return panel;

    }

}
