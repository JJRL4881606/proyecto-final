package components;

import java.awt.*;
import javax.swing.border.AbstractBorder;

@SuppressWarnings("serial")
public class RoundedBorder extends AbstractBorder {

    private Color color;
    private int thickness;
    private int radius;

    public RoundedBorder(Color color, int thickness, int radius) {
        this.color = color;
        this.thickness = thickness;
        this.radius = radius;
    }

    @Override
    public void paintBorder(Component c, Graphics g, int x, int y, int width, int height) {
        Graphics2D g2 = (Graphics2D) g.create();

        g2.setRenderingHint(
            RenderingHints.KEY_ANTIALIASING,
            RenderingHints.VALUE_ANTIALIAS_ON
        );

        g2.setColor(color);
        g2.setStroke(new BasicStroke(thickness));
        g2.drawRoundRect(
            x + 1,
            y + 1,
            width - 3,
            height - 3,
            radius,
            radius
        );

        g2.dispose();
    }

    @Override
    public Insets getBorderInsets(Component c) {
        return new Insets(10, 10, 10, 10);
    }

    @Override
    public Insets getBorderInsets(Component c, Insets insets) {
        insets.left = 10;
        insets.right = 10;
        insets.top = 10;
        insets.bottom = 10;
        return insets;
    }
}