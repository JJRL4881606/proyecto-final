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

public class PanelBotones extends JPanel{

	public PanelBotones() {
		setLayout(new FlowLayout(FlowLayout.RIGHT, 20, 20));

			JButton botonIniciarSesion = new JButton("Iniciar sesión", new ImageIcon("src/img/login-icon.png"));
			//botonIniciarSesion.setBounds(ventanaCentroW - 210, 440, 200, 30);
			botonIniciarSesion.setBackground(new Color(255, 249, 179));
			botonIniciarSesion.setForeground(Color.BLACK);
			botonIniciarSesion.setToolTipText("Haz click aquí");
			botonIniciarSesion.setFont(new Font("Arial", Font.BOLD, 20));
			
			JButton botonCrearCuenta = new JButton("Crear cuenta", new ImageIcon("src/img/login-icon.png"));
			//botonCrearCuenta.setBounds(ventanaCentroW + 10, 440, 200, 30);
			botonCrearCuenta.setBackground(new Color(255, 249, 179));
			botonCrearCuenta.setForeground(Color.BLACK);
			botonCrearCuenta.setToolTipText("Haz click aquí");
			botonCrearCuenta.setFont(new Font("Arial", Font.BOLD, 20));
			
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
	
	
	
	
