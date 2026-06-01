package utils;

import java.awt.Color;
import java.awt.Dimension;
import javax.swing.JPanel;

public class VisualUtils {

	//linea en las roomCards
    public static JPanel createDivider() {
        JPanel line = new JPanel();
        line.setMaximumSize(new Dimension(1100, 1));
        line.setPreferredSize(new Dimension(1100, 1));
        line.setBackground(new Color(0, 0, 0, 30));
        return line;
    }
    
    //linea para separar secciones en Home
    public static JPanel createSmallDivider() {
        JPanel line = new JPanel();
        line.setMaximumSize(new Dimension(300, 1));
        line.setPreferredSize(new Dimension(300, 1));
        line.setBackground(new Color(0, 0, 0, 30));
        return line;
    }
}