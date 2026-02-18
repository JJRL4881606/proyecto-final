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
import javax.swing.JPasswordField;
import javax.swing.JTextField;

public class PanelFormulario extends JPanel{

	public PanelFormulario() {
		setLayout(new FlowLayout(FlowLayout.RIGHT, 20, 20));

		JLabel lblUsuario = new JLabel("Ingrese el usuario: ");
		lblUsuario.setFont(new Font("Arial", Font.BOLD,20));
		//lblUsuario.setBounds(ventanaCentroW - 125,200,250,30);
		add(lblUsuario);
		
		JTextField usuario = new JTextField();
		usuario.setFont(new Font("Arial", Font.BOLD,25));
		//usuario.setBounds(ventanaCentroW - 125,240,250,35);
		add(usuario);
		
		JLabel lblUsuarioObligatorio = new JLabel("Usuario obligatorio");
		lblUsuarioObligatorio.setFont(new Font("Arial", Font.BOLD,12));
		//lblUsuarioObligatorio.setBounds(ventanaCentroW - 80,290,160,12);
		lblUsuarioObligatorio.setForeground(Color.RED);
		add(lblUsuarioObligatorio);
		
		JLabel lblContrasena = new JLabel("Ingrese la contraseña: ");
		lblContrasena.setFont(new Font("Arial", Font.BOLD,20));
		//lblContrasena.setBounds(ventanaCentroW - 125,305,250,30);
		add(lblContrasena);
		
		JPasswordField contrasena = new JPasswordField();
		contrasena.setFont(new Font("Arial", Font.BOLD,25));
		//contrasena.setBounds(ventanaCentroW - 125,340,250,25);
		add(contrasena);
		
		JLabel lblContraObligatoria = new JLabel("Contraseña obligatoria");
		lblContraObligatoria.setFont(new Font("Arial", Font.BOLD,12));
		//lblContraObligatoria.setBounds(ventanaCentroW - 80,380,160,12);
		lblContraObligatoria.setForeground(Color.RED);
		add(lblContraObligatoria);
		
		JLabel lblCredIncorrectas = new JLabel("Credenciales incorrectas");
		lblCredIncorrectas.setFont(new Font("Arial", Font.BOLD,12));
		//lblCredIncorrectas.setBounds(ventanaCentroW - 80,500,160,12);
		lblCredIncorrectas.setForeground(Color.RED);
		add(lblCredIncorrectas);
	}
}
	
	
	
	
