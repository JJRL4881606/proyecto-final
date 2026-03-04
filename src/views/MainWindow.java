package views;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridBagLayout;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JPanel;

import components.RoundedPanel;

public class MainWindow extends JPanel {

	int ventanaCentroW = 400;
	
	public MainWindow() {
		this.setBackground(new Color(100,149,237)); 
	    setLayout(new GridBagLayout());
	    initializeComponents();
        setVisible(true);
    }
    
    public void initializeComponents() {
	    JPanel card = new RoundedPanel(50);
	    card.setLayout(new BoxLayout(card, BoxLayout.Y_AXIS));
	    card.setBackground(new Color(151, 210, 251));
	    card.setBorder(BorderFactory.createEmptyBorder(25, 35, 25, 35));
	    card.setMaximumSize(new Dimension(1000, Integer.MAX_VALUE));
	    card.setAlignmentX(CENTER_ALIGNMENT);
	    card.putClientProperty("FlatLaf.style", "arc:20");

    }
}
