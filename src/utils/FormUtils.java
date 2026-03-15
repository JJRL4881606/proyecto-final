package utils;

import java.awt.Component;
import java.awt.Dimension;
import java.awt.Image;

import javax.imageio.ImageIO;
import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.border.Border;

public class FormUtils {

    public static JPanel createField(String labelText, JComponent field, JLabel errorLabel, String placeholder) {

        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setOpaque(false);
        panel.setAlignmentX(Component.CENTER_ALIGNMENT);

        if (labelText != null) {
            JLabel label = new JLabel(labelText);
            label.setAlignmentX(Component.CENTER_ALIGNMENT);
            panel.add(label);
        }

        if (field instanceof JTextField) {
            ((JTextField) field).putClientProperty("JTextField.placeholderText", placeholder);
        }

        errorLabel.setForeground(UIColors.ERROR);
        errorLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        panel.add(field);
        panel.add(Box.createRigidArea(new Dimension(0, 5)));
        panel.add(errorLabel);
        panel.add(Box.createRigidArea(new Dimension(0, 15)));

        return panel;
    }
    
    public static JLabel createErrorLabel() {
        JLabel label = new JLabel();
        label.setForeground(UIColors.ERROR);
        label.setFont(AppFont.small());
        label.setAlignmentX(Component.CENTER_ALIGNMENT);
        return label;
    }
    
    public static JTextField createTextField() {
        JTextField field = new JTextField();
        field.setBorder(BorderFactory.createEmptyBorder(8,10,8,10));
        field.setFont(AppFont.normal());
        field.setMaximumSize(new Dimension(Integer.MAX_VALUE, 35));
        return field;
    }
    
    public static JPasswordField createPasswordField() {
    	JPasswordField field = new JPasswordField();
        field.setBorder(BorderFactory.createEmptyBorder(8,10,8,10));
        field.setFont(AppFont.normal());
        field.setMaximumSize(new Dimension(Integer.MAX_VALUE, 35));
        return field;
    }
    
    
    public static Border redBorder = BorderFactory.createCompoundBorder(
        BorderFactory.createLineBorder(UIColors.ERROR, 2),
        BorderFactory.createEmptyBorder(8, 10, 8, 10)
    );
        
    public static Border normalBorder = BorderFactory.createEmptyBorder(8, 10, 8, 10);
    
    public static ImageIcon loadIcon(String path, int size) {
        try {
            Image img = ImageIO.read(FormUtils.class.getResource(path));
            img = img.getScaledInstance(size, size, Image.SCALE_SMOOTH);
            return new ImageIcon(img);
        } catch (Exception e) {
            System.out.println("No está la imagen del ícono");
            return null;
        }
    }
}