package components;

import java.awt.*;
import java.awt.geom.RoundRectangle2D;

import javax.swing.*;
import javax.swing.border.EmptyBorder;

@SuppressWarnings("serial")
public class RoundedComboBox<E> extends JComboBox<E> {

    private int radius = 20;

    public RoundedComboBox(E[] items) {
        super(items);

        setOpaque(false);
        setBorder(new EmptyBorder(2,10,2,10));

        // Render del elemento seleccionado y lista
        setRenderer(new DefaultListCellRenderer() {

            @Override
            public Component getListCellRendererComponent(
                    JList<?> list,
                    Object value,
                    int index,
                    boolean isSelected,
                    boolean cellHasFocus) {

                JLabel c=(JLabel)super.getListCellRendererComponent(
                        list,
                        value,
                        index,
                        isSelected,
                        cellHasFocus
                );

                c.setBorder(new EmptyBorder(5,8,5,8));

                // solo el seleccionado transparente
                if(index==-1){
                    c.setOpaque(false);
                }

                return c;
            }
        });

        // botón flecha
        for(Component c : getComponents()) {

            if(c instanceof JButton b){

                b.setOpaque(false);
                b.setContentAreaFilled(false);
                b.setBorderPainted(false);
                b.setFocusPainted(false);
            }
        }
    }

    @Override
    protected void paintComponent(Graphics g){

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

        g2.setClip(new RoundRectangle2D.Float(
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