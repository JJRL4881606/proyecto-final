package components;

import java.awt.*;
import javax.swing.*;

@SuppressWarnings("serial")
public class RoundedMenuBar extends JMenuBar {

    private int radius = 15;

    public RoundedMenuBar() {
        setOpaque(false);
        setBackground(new Color(0,0,0,0));
    }

    @Override
    protected void paintComponent(Graphics g) {

        Graphics2D g2 = (Graphics2D) g.create();

        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
                            RenderingHints.VALUE_ANTIALIAS_ON);

        g2.setColor(new Color(255,255,253));
        g2.fillRoundRect(0, 0, getWidth(), getHeight(), radius, radius);

        g2.dispose();
    }
}
