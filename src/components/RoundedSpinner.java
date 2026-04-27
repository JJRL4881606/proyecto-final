package components;

import java.awt.Color;
import java.awt.Component;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;

import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JFormattedTextField;
import javax.swing.JSpinner;
import javax.swing.SpinnerModel;

@SuppressWarnings("serial")
public class RoundedSpinner extends JSpinner {

    private int radius = 20;

    public RoundedSpinner(SpinnerModel model) {
        super(model);

        setOpaque(false);

        JComponent editor = getEditor();
        editor.setOpaque(false);

        if (editor instanceof JSpinner.DefaultEditor) {
            JFormattedTextField tf =
                    ((JSpinner.DefaultEditor) editor).getTextField();

            tf.setOpaque(false);
            tf.setBorder(null);
        }

        for (Component c : getComponents()) {
            if (c instanceof JButton) {
                ((JButton) c).setOpaque(false);
                c.setBackground(new Color(0,0,0,0));
            }
        }
    }

    @Override
    protected void paintComponent(Graphics g) {
        Graphics2D g2 = (Graphics2D) g.create();

        g2.setRenderingHint(
                RenderingHints.KEY_ANTIALIASING,
                RenderingHints.VALUE_ANTIALIAS_ON
        );

        g2.setColor(getBackground());
        g2.fillRoundRect(
                0, 0,
                getWidth(),
                getHeight(),
                radius,
                radius
        );

        g2.dispose();

        super.paintComponent(g);
    }
}