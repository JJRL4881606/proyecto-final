package utils;

import javax.swing.ImageIcon;
import components.RoundButton;

public class ButtonFactory {

    public static RoundButton createBigButton(
            String text,
            String iconPath,
            String tooltip
    ) {
        RoundButton btn = new RoundButton(
                text,
                new ImageIcon(ButtonFactory.class.getResource(iconPath))
        );

        btn.setBackground(UIColors.BUTTON_BIG);
        btn.setToolTipText(tooltip);
        btn.setFont(AppFont.big());
        btn.setForeground(UIColors.BUTTON_BIG_TEXT);
        btn.setFocusPainted(false);

        return btn;
    }
    
    public static RoundButton createNormalButton(
            String text,
            String iconPath,
            String tooltip
    ) {
        RoundButton btn = new RoundButton(
                text,
                new ImageIcon(ButtonFactory.class.getResource(iconPath))
        );

        btn.setBackground(UIColors.BUTTON_NORMAL);
        btn.setToolTipText(tooltip);
        btn.setFont(AppFont.normal());
        btn.setForeground(UIColors.BUTTON_NORMAL_TEXT);
        btn.setFocusPainted(false);

        return btn;
    }
}