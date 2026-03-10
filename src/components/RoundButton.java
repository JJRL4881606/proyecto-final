package components;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.geom.RoundRectangle2D;

@SuppressWarnings("serial")
public class RoundButton extends JButton {

    private boolean drawBorder;
    private float borderThickness;
    private int cornerRadius = 15;
    private Color hoverColor;
    private boolean hovering = false;

    public RoundButton(String label, Icon icon) {
        super(label, icon);
        setContentAreaFilled(false);
        setFocusPainted(false);
        setBorderPainted(false);
        setOpaque(false);

        hoverColor = getBackground().brighter();

        addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                hovering = true;
                repaint();
            }

            @Override
            public void mouseExited(MouseEvent e) {
                hovering = false;
                repaint();
            }
        });
    }

    public void setCornerRadius(int radius) {
        this.cornerRadius = radius;
        repaint();
    }

    @Override
    protected void paintComponent(Graphics g) {
        Graphics2D g2 = (Graphics2D) g.create();
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        Color colorBase = hovering ? hoverColor : getBackground();

        if (getModel().isArmed()) {
            colorBase = colorBase.darker();
        }

        g2.setColor(colorBase);
        g2.fillRoundRect(0, 0, getWidth(), getHeight(), cornerRadius, cornerRadius);
        g2.dispose();
        super.paintComponent(g);
    }

    @Override
    protected void paintBorder(Graphics g) {
        if (drawBorder && borderThickness > 0) {
            Graphics2D g2 = (Graphics2D) g.create();
            
            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            g2.setStroke(new BasicStroke(borderThickness));
            g2.setColor(getForeground());
            
            int offset = (int) (borderThickness / 2);
            g2.drawRoundRect(offset, offset,
                    getWidth() - offset * 2 - 1,
                    getHeight() - offset * 2 - 1,
                    cornerRadius, cornerRadius);

            g2.dispose();
        }
    }

    @Override
    public boolean contains(int x, int y) {
        Shape shape = new RoundRectangle2D.Float(
                0, 0, getWidth(), getHeight(),
                cornerRadius, cornerRadius);
        return shape.contains(x, y);
    }
}