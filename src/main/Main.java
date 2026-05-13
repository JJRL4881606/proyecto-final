package main;

import java.awt.GraphicsDevice;
import java.awt.GraphicsEnvironment;

import javax.swing.JFrame;
import javax.swing.UIManager;
import javax.swing.plaf.FontUIResource;

import com.formdev.flatlaf.FlatLightLaf;

import utils.AppFont;
import views.LoginWindow;
import views.MainWindow;

public class Main 
{
    @SuppressWarnings("unused")
	public static void main(String[] args) {
        FlatLightLaf.setup();
        UIManager.put("defaultFont", new FontUIResource(AppFont.normal()));
        //LoginWindow ventanita = new LoginWindow();
        MainWindow ventanita = new MainWindow();
        //showOnScreen(0, ventanita);
    }

	public static void showOnScreen(int screen, JFrame frame ) {
	    GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
	    GraphicsDevice[] gd = ge.getScreenDevices();
	    int width = 0, height = 0;
	    
	    if( screen > -1 && screen < gd.length ) {
	        width = gd[screen].getDefaultConfiguration().getBounds().width;
	        height = gd[screen].getDefaultConfiguration().getBounds().height;
	        frame.setLocation(
	            ((width / 2) - (frame.getSize().width / 2)) + gd[screen].getDefaultConfiguration().getBounds().x, 
	            ((height / 2) - (frame.getSize().height / 2)) + gd[screen].getDefaultConfiguration().getBounds().y
	        );
	    } else {
	        throw new RuntimeException( "No se encontró la pantalla" );
	    }
	}
}