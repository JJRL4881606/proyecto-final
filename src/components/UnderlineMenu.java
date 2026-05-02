package components;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JMenu;
import javax.swing.border.EmptyBorder;

@SuppressWarnings("serial")
public class UnderlineMenu extends JMenu {

    private boolean hover = false;

    public UnderlineMenu(String text) {
        super(text);
        setForeground(Color.WHITE);
        setOpaque(false);

        setBorder(new EmptyBorder(5,15,5,15));

        addMouseListener(new MouseAdapter() {
            public void mouseEntered(MouseEvent e) {
                hover = true;
                repaint();
            }

            public void mouseExited(MouseEvent e) {
                hover = false;
                repaint();
            }
        });
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        if (hover) {
            g.setColor(Color.WHITE);
            g.fillRect(0, getHeight() - 2, getWidth(), 2); // línea abajo
        }
    }
}