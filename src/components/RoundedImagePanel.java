package components;

import javax.swing.*;
import java.awt.*;
import java.awt.geom.RoundRectangle2D;
import java.net.URL;

@SuppressWarnings("serial")
public class RoundedImagePanel extends JPanel {

    private Image image;
    private int radius;
    private int panelWidth;
    private int panelHeight;

    public RoundedImagePanel(String path, int width, int height, int radius) {
        this.radius = radius;
        this.panelWidth = width;
        this.panelHeight = height;

        try {
            URL url = getClass().getResource(path);

            if (url != null) {
                ImageIcon icon = new ImageIcon(url);

                image = icon.getImage().getScaledInstance(
                        width,
                        height,
                        Image.SCALE_SMOOTH
                );
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        setOpaque(false);
    }

    @Override
    public Dimension getPreferredSize() {
        return new Dimension(panelWidth, panelHeight);
    }

    @Override
    public Dimension getMaximumSize() {
        return new Dimension(panelWidth, panelHeight);
    }

    @Override
    public Dimension getMinimumSize() {
        return new Dimension(panelWidth, panelHeight);
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        if (image == null) return;

        Graphics2D g2 = (Graphics2D) g.create();

        g2.setRenderingHint(
                RenderingHints.KEY_ANTIALIASING,
                RenderingHints.VALUE_ANTIALIAS_ON
        );

        g2.setClip(new RoundRectangle2D.Float(
                0,
                0,
                panelWidth,
                panelHeight,
                radius,
                radius
        ));

        g2.drawImage(
                image,
                0,
                0,
                panelWidth,
                panelHeight,
                this
        );

        g2.dispose();
    }
}