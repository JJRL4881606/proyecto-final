package components;

import javax.swing.*;
import java.awt.*;

@SuppressWarnings("serial")
public class GradientPanel extends JPanel {

    private Color color1;
    private Color color2;

    public GradientPanel(Color c1, Color c2) {
        this.color1 = c1;
        this.color2 = c2;
        setOpaque(false);
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        Graphics2D g2d = (Graphics2D) g.create();

        GradientPaint gp = new GradientPaint(
                0, 0, color1,
                0, getHeight(), color2
        );

        g2d.setPaint(gp);
        g2d.fillRect(0, 0, getWidth(), getHeight());
        g2d.dispose();
    }
}