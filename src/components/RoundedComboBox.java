/*package components;

import java.awt.Color;
import java.awt.Component;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JList;

@SuppressWarnings("serial")
public class RoundedComboBox<E> extends JComboBox<E> {

    private int radius = 20;

    public RoundedComboBox(E[] items) {
        super(items);

        setOpaque(false);

        setRenderer(new DefaultListCellRenderer() {
            public Component getListCellRendererComponent(
                    JList<?> list,
                    Object value,
                    int index,
                    boolean isSelected,
                    boolean cellHasFocus) {

                Component c = super.getListCellRendererComponent(
                        list,
                        value,
                        index,
                        isSelected,
                        cellHasFocus
                );

                if (c instanceof JComponent) {
                    ((JComponent) c).setOpaque(false);
                }

                return c;
            }
        });

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
}*/