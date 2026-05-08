package utils;

import javax.swing.ImageIcon;
import components.RoundedButton;

public class ButtonFactory {

    public static RoundedButton createBigButton(String text, String iconPath, String tooltip){
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
}