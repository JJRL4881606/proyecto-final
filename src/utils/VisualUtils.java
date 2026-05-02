package utils;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.BorderFactory;
import javax.swing.JComponent;
import javax.swing.JPanel;

public class VisualUtils {

    public static void addHoverEffect(
            JComponent comp,
            int normalPadding,
            int hoverPadding,
            Color normalColor,
            Color hoverColor
    ) {

        comp.setBorder(BorderFactory.createEmptyBorder(
                normalPadding,
                normalPadding,
                normalPadding,
                normalPadding
        ));

        comp.setBackground(normalColor);

        comp.addMouseListener(new MouseAdapter() {

            @Override
            public void mouseEntered(MouseEvent e) {
                comp.setBorder(BorderFactory.createEmptyBorder(
                        hoverPadding,
                        hoverPadding,
                        hoverPadding,
                        hoverPadding
                ));
                comp.setBackground(hoverColor);
            }

            @Override
            public void mouseExited(MouseEvent e) {
                comp.setBorder(BorderFactory.createEmptyBorder(
                        normalPadding,
                        normalPadding,
                        normalPadding,
                        normalPadding
                ));
                comp.setBackground(normalColor);
            }
        });
    }
    
    public static JPanel createDivider() {
        JPanel line = new JPanel();
        line.setMaximumSize(new Dimension(1100, 1));
        line.setPreferredSize(new Dimension(1100, 1));
        line.setBackground(new Color(0, 0, 0, 30));
        return line;
    }
}