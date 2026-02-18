package components;

import javax.swing.*;
import java.awt.*;
import java.awt.geom.Ellipse2D;

public class RoundButton extends JButton {

    private boolean drawBorder;      // Bandera que indica si se dibuja o no el borde
    private float borderThickness;   // Grosor del borde

    public RoundButton(String label) {
        super(label);
        // Ajustes para eliminar el look & feel rectangular
        setContentAreaFilled(false);
        setFocusPainted(false);
        setBorderPainted(false);
        setOpaque(false);

        this.drawBorder = false;
        this.borderThickness = 0;
    }

    /**
     * Activa o desactiva el dibujo del borde.
     */
    public void setDrawBorder(boolean drawBorder) {
        this.drawBorder = drawBorder;
        repaint(); 
    }

    /**
     * Devuelve si el borde está activo o no.
     */
    public boolean isDrawBorder() {
        return drawBorder;
    }

    /**
     * Ajusta el grosor del borde en píxeles.
     */
    public void setBorderThickness(float thickness) {
        this.borderThickness = thickness;
        repaint();
    }

    /**
     * Retorna el grosor actual del borde.
     */
    public float getBorderThickness() {
        return borderThickness;
    }

    @Override
    protected void paintComponent(Graphics g) {
        // Se dibuja el círculo de fondo
        Graphics2D g2 = (Graphics2D) g.create();
        //Evita el efecto "pixeleado"
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        if (getModel().isArmed()) {
            g2.setColor(Color.LIGHT_GRAY);
        } else {
            g2.setColor(getBackground());
        }
        g2.fillOval(0, 0, getWidth(), getHeight());
        g2.dispose();

        // Se llama al super para que se pinte el texto
        super.paintComponent(g);
    }

    @Override
    protected void paintBorder(Graphics g) {
        // Dibuja el borde solo si está en true y el grosor es mayor que 0
        if (drawBorder && borderThickness > 0) {
            Graphics2D g2 = (Graphics2D) g.create();
            //Evita el efecto "pixeleado"
            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

            // Se aplica el grosor del borde
            g2.setStroke(new BasicStroke(borderThickness));
            g2.setColor(getForeground());

            /* Para que el trazo no se "corte", se reduce un poco el tamaño
             * del óvalo en función del grosor. Así el borde queda dentro del botón.
             */
            int offset = (int) (borderThickness / 2);
            g2.drawOval(offset, offset, getWidth() - offset * 2, getHeight() - 1 - offset * 2);

            g2.dispose();
        }
    }

    @Override
    public boolean contains(int x, int y) {
        // Área clicable en forma de círculo
        Ellipse2D shape = new Ellipse2D.Float(0, 0, getWidth(), getHeight());
        return shape.contains(x, y);
    }

}