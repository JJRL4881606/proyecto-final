package views;

import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import javax.swing.*;

public class PanelFormulario extends JPanel {

    public PanelFormulario() {
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
		setMaximumSize(new Dimension(400, 120));
		
        add(crearFilaUsuario());
        add(crearFilaPassword());
    }

    private JPanel crearFilaUsuario() {
        JPanel fila = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 10));
		setOpaque(false);

        JLabel lblUsuario = new JLabel("Usuario:");
        lblUsuario.setFont(new Font("Arial", Font.BOLD, 18));

        JTextField txtUsuario = new JTextField(15);
        txtUsuario.setFont(new Font("Arial", Font.PLAIN, 18));

        fila.add(lblUsuario);
        fila.add(txtUsuario);

        return fila;
    }

    private JPanel crearFilaPassword() {
        JPanel fila = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 10));
		setOpaque(false);

        JLabel lblPassword = new JLabel("Contraseña:");
        lblPassword.setFont(new Font("Arial", Font.BOLD, 18));

        JPasswordField txtPassword = new JPasswordField(15);
        txtPassword.setFont(new Font("Arial", Font.PLAIN, 18));

        fila.add(lblPassword);
        fila.add(txtPassword);

        return fila;
    }
}