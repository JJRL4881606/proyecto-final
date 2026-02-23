package views;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Font;
import javax.swing.*;
import javax.swing.border.EmptyBorder;

public class PanelFormulario extends JPanel {

    public PanelFormulario() {
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
		setMaximumSize(new Dimension(450, 120));
		setOpaque(false);
		
        add(crearFilaUsuario());
        add(crearFilaPassword());   
    }

    private JPanel crearFilaUsuario() {    	
        JPanel fila = new JPanel(new BorderLayout(10, 10)); 
		fila.setBorder(new EmptyBorder(20, 20, 10, 20)); 
        fila.setOpaque(false);

        JLabel lblUsuario = new JLabel("Usuario:");
        lblUsuario.setFont(new Font("Arial", Font.BOLD, 18));

        JTextField txtUsuario = new JTextField(15);
        txtUsuario.setFont(new Font("Arial", Font.PLAIN, 18));
        txtUsuario.setBorder(BorderFactory.createEmptyBorder(8,10,8,10));

        fila.add(lblUsuario, BorderLayout.WEST);
        fila.add(txtUsuario, BorderLayout.EAST); 

        return fila;
    }

    private JPanel crearFilaPassword() {
        JPanel fila = new JPanel(new BorderLayout(10, 10));
		fila.setBorder(new EmptyBorder(10, 20, 30, 20)); 
		fila.setOpaque(false);

        JLabel lblPassword = new JLabel("Contraseña:");
        lblPassword.setFont(new Font("Arial", Font.BOLD, 18));

        JPasswordField txtPassword = new JPasswordField(15);
        txtPassword.setFont(new Font("Arial", Font.PLAIN, 18));
        txtPassword.setBorder(BorderFactory.createEmptyBorder(8,10,8,10));

        fila.add(lblPassword, BorderLayout.WEST);
        fila.add(txtPassword, BorderLayout.EAST);

        return fila;
    }
}