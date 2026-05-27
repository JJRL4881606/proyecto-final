package components;

import java.awt.*;
import javax.swing.*;
import javax.swing.border.EmptyBorder;

@SuppressWarnings("serial")
public class RoundedSpinner extends JSpinner {

    private int radius = 20;

    public RoundedSpinner(SpinnerModel model) {
        super(model);

        setOpaque(false);
        setBorder(new EmptyBorder(0,10,0,10));

        DefaultEditor editor = (DefaultEditor)getEditor();

        JFormattedTextField tf = editor.getTextField();

        tf.setOpaque(false);
        tf.setBorder(new EmptyBorder(0,8,0,8));
        tf.setBackground(new Color(0,0,0,0));

        // quitar borde del panel editor
        editor.setBorder(null);
        editor.setOpaque(false);

        // quitar fondo y borde de flechas
        for(Component c : getComponents()) {
            if(c instanceof JButton b) {
                b.setOpaque(false);
                b.setContentAreaFilled(false);
                b.setBorder(null);
                b.setFocusPainted(false);
            }
        }
    }

    @Override
    protected void paintComponent(Graphics g) {

        Graphics2D g2=(Graphics2D)g.create();

        g2.setRenderingHint(
            RenderingHints.KEY_ANTIALIASING,
            RenderingHints.VALUE_ANTIALIAS_ON
        );

        g2.setColor(getBackground());

        g2.fillRoundRect(
            0,
            0,
            getWidth()-1,
            getHeight()-1,
            radius,
            radius
        );

        g2.dispose();

        super.paintComponent(g);
    }

    @Override
    public void paint(Graphics g){

        Graphics2D g2=(Graphics2D)g.create();

        g2.setClip(new java.awt.geom.RoundRectangle2D.Float(
            0,
            0,
            getWidth(),
            getHeight(),
            radius,
            radius
        ));

        super.paint(g2);

        g2.dispose();
    }
}