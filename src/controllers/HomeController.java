package controllers;

import javax.swing.JOptionPane;
import javax.swing.SwingUtilities;

import views.HomeView;
import views.LoginWindow;

import java.awt.Window;

public class HomeController {

    private HomeView view;

    public HomeController(HomeView view) {
        this.view = view;

        view.getCerrarSesion().addActionListener(e -> cerrarSesion());
    }

    private void cerrarSesion() {

        int option = JOptionPane.showConfirmDialog(
            view,
            "¿Seguro que deseas cerrar sesión?"
        );

        if (option == JOptionPane.YES_OPTION) {

            new LoginWindow();

            Window window = SwingUtilities.getWindowAncestor(view);
            if (window != null) window.dispose();
        }
    }
}