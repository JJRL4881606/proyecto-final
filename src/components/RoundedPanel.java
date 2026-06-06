package components;

import javax.swing.*;
import java.awt.*;

@SuppressWarnings("serial")
public class RoundedPanel extends JPanel {

    private int radio;

    public RoundedPanel(int radio) {
        this.radio = radio;
        setOpaque(false);
    }

    @Override
    protected void paintComponent(Graphics g) {
        Graphics2D g2 = (Graphics2D) g.create();

        g2.setRenderingHint(
            RenderingHints.KEY_ANTIALIASING,
            RenderingHints.VALUE_ANTIALIAS_ON
        );

        // sombra
        g2.setColor(new Color(0, 0, 0, 40));
        g2.fillRoundRect(4, 4, getWidth() - 4, getHeight() - 4, radio, radio);

        // panel
        g2.setColor(getBackground());
        g2.fillRoundRect(0, 0, getWidth() - 4, getHeight() - 4, radio, radio);

        g2.dispose();
        super.paintComponent(g);
    }
}