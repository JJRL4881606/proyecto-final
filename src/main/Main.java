package main;

import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import javax.swing.plaf.FontUIResource;

import com.formdev.flatlaf.FlatLightLaf;

import utils.AppFont;
import views.auth.LoginWindow;

public class Main {

    public static void main(String[] args) {
        FlatLightLaf.setup();
        UIManager.put("defaultFont", new FontUIResource(AppFont.normal()));

        SwingUtilities.invokeLater(() -> {
            new LoginWindow();
        });
    }
}