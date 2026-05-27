package utils;

import java.awt.Color;

import javax.swing.ImageIcon;
import components.RoundedButton;

public class ButtonFactory {

    public static RoundedButton createGoldButton(String text, String iconPath, String tooltip){
        RoundedButton btn = new RoundedButton(
            text,
            new ImageIcon(ButtonFactory.class.getResource(iconPath))
        );

        btn.setBackground(UIColors.BUTTON);
        btn.setToolTipText(tooltip);
        btn.setFont(AppFont.big());
        btn.setForeground(UIColors.BUTTON_TEXT);
        btn.setFocusPainted(false);

        return btn;
    }
    
    public static RoundedButton createBlueButton(String text, String iconPath, String tooltip){
        RoundedButton btn = new RoundedButton(
            text,
            new ImageIcon(ButtonFactory.class.getResource(iconPath))
        );

        btn.setBackground(UIColors.BACKGROUND);
        btn.setToolTipText(tooltip);
        btn.setFont(AppFont.big());
        btn.setForeground(Color.white);
        btn.setFocusPainted(false);

        return btn;
    }

}