package components;

import javax.swing.*;
import java.awt.*;
import java.awt.geom.RoundRectangle2D;
import java.net.URL;

@SuppressWarnings("serial")
public class RoundedImageOverlayPanel extends JPanel {

    private Image image;
    private int radius;
    private Color overlayColor;

    public RoundedImageOverlayPanel(
            String path,
            int radius,
            Color overlayColor
    ) {
        this.radius = radius;
        this.overlayColor = overlayColor;

        try {
            URL url = getClass().getResource(path);
            if (url != null) {
                image = new ImageIcon(url).getImage(); // sin escalar aquí
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        setOpaque(false);
    }

    @Override
    public Dimension getPreferredSize() {
        return new Dimension(800, 300); // valor base (el layout lo ajusta)
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        if (image == null) return;

        int w = getWidth();
        int h = getHeight();

        if (w <= 0 || h <= 0) return;

        Graphics2D g2 = (Graphics2D) g.create();

        g2.setRenderingHint(
                RenderingHints.KEY_ANTIALIASING,
                RenderingHints.VALUE_ANTIALIAS_ON
        );

        Shape clip = new RoundRectangle2D.Float(
                0,
                0,
                w,
                h,
                radius,
                radius
        );

        g2.setClip(clip);

        g2.drawImage(image, 0, 0, w, h, this);

        if (overlayColor != null) {
            g2.setColor(overlayColor);
            g2.fillRect(0, 0, w, h);
        }

        g2.dispose();
    }
}