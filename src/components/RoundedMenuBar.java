package components;

import java.awt.*;
import javax.swing.*;

public class RoundedMenuBar extends JMenuBar {

    private int radius = 15;

    public RoundedMenuBar() {
        setOpaque(false);
    }

    @Override
    protected void paintComponent(Graphics g) {
        Graphics2D g2 = (Graphics2D) g.create();
        g2.setColor(new Color(255,255,255));
        g2.fillRoundRect(0,0,getWidth(),getHeight(),radius,radius);
        g2.dispose();

        super.paintComponent(g);
    }
}