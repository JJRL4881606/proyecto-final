package main;

import com.formdev.flatlaf.FlatDarkLaf;
import com.formdev.flatlaf.FlatLightLaf;

import views.FormularioUsuario;

public class Main 
{
    public static void main(String[] args) {
        FlatLightLaf.setup();
        //Ventana ventanita = new Ventana();
        FormularioUsuario registro = new FormularioUsuario();
    }
}