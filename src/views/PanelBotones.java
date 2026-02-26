package views;

import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Image;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import components.RoundButton;
import utils.AppFont;

@SuppressWarnings("serial")
public class PanelBotones extends JPanel{

	public PanelBotones() {
		setLayout(new FlowLayout(FlowLayout.CENTER, 20, 20));
		setOpaque(false);

		JButton botonIniciarSesion = new RoundButton("INICIAR SESIÓN", new ImageIcon("src/img/login-icon.png"));
		botonIniciarSesion.setBackground(new Color(255, 249, 179));
		botonIniciarSesion.setForeground(Color.BLACK);
		botonIniciarSesion.setToolTipText("Haz click aquí");
		botonIniciarSesion.setFont(AppFont.big());
		
		JButton botonCrearCuenta = new RoundButton("CREAR CUENTA", new ImageIcon("src/img/login-icon.png"));
		botonCrearCuenta.setBackground(new Color(255, 249, 179));
		botonCrearCuenta.setForeground(Color.BLACK);
		botonCrearCuenta.setToolTipText("Haz click aquí");
		botonCrearCuenta.setFont(AppFont.big());
		
		try
		{
			Image icono = ImageIO.read(getClass().getResource("/img/login-icon.png"));
			icono = icono.getScaledInstance(30,30, Image.SCALE_SMOOTH);
			botonIniciarSesion.setIcon(new ImageIcon(icono));
		}
		catch(Exception ex) 
		{
			System.out.println("No está la imagen del ícono");
		}
		
		add(botonIniciarSesion);	
		add(botonCrearCuenta);
	}
}
	
	
	
	
