package components;

import javax.swing.*;
import java.awt.*;
import java.awt.geom.RoundRectangle2D;
import java.net.URL;

@SuppressWarnings("serial")
public class RoundedImageOverlayPanel extends JPanel {

    private Image image;
    private int radius;
    private int width;
    private int height;
    private Color overlayColor;

    public RoundedImageOverlayPanel(
            String path,
            int width,
            int height,
            int radius,
            Color overlayColor
    ) {
        this.radius = radius;
        this.width = width;
        this.height = height;
        this.overlayColor = overlayColor;

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
        return new Dimension(width, height);
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

        // 🔥 CLIP REDONDEADO (clave)
        Shape clip = new RoundRectangle2D.Float(
                0,
                0,
                width,
                height,
                radius,
                radius
        );

        g2.setClip(clip);

        // 🖼️ 1. DIBUJAR IMAGEN
        g2.drawImage(image, 0, 0, width, height, this);

        // 🌑 2. DIBUJAR OVERLAY ENCIMA
        if (overlayColor != null) {
            g2.setColor(overlayColor);
            g2.fillRect(0, 0, width, height);
        }

        g2.dispose();
    }
}