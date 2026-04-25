package utils;

import javax.swing.ImageIcon;
import components.RoundButton;

public class ButtonFactory {

    public static RoundButton createButton(
            String text,
            String iconPath,
            String tooltip
    ) {
        RoundButton btn = new RoundButton(
                text,
                new ImageIcon(ButtonFactory.class.getResource(iconPath))
        );

        btn.setBackground(UIColors.BUTTON);
        btn.setToolTipText(tooltip);
        btn.setFont(AppFont.big());
        btn.setFocusPainted(false);

        return btn;
    }
}