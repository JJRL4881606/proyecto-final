package views;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import javax.swing.*;
import javax.swing.border.EmptyBorder;

import utils.AppFont;

@SuppressWarnings("serial")
public class PanelFormulario extends JPanel {

    public PanelFormulario() {
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
		setMaximumSize(new Dimension(420, 120));
		setOpaque(false);
		
        add(crearFilaUsuario());
        add(crearFilaUsuarioObligatorio());   
        add(crearFilaContrasena());
        add(crearFilaMostrarContrasena()); 
        add(crearFilaContrasenaObligatoria()); 
        add(crearFilaRecordarme());
        add(crearFilaAceptarTerminos());   
        add(crearFilaError());
        add(crearFilaOlvido());

    }

    private JPanel crearFilaUsuario() {    	
        JPanel fila = new JPanel(new BorderLayout(10, 10)); 
		fila.setBorder(new EmptyBorder(20, 20, 10, 20)); 
        fila.setOpaque(false);

        JLabel lblUsuario = new JLabel("Usuario:");
        lblUsuario.setFont(AppFont.big());

        JTextField txtUsuario = new JTextField(15);
        txtUsuario.setFont(AppFont.big());
        txtUsuario.setBorder(BorderFactory.createEmptyBorder(8,10,8,10));
        txtUsuario.putClientProperty("JTextField.placeholderText", "Ingrese el usuario");

        fila.add(lblUsuario, BorderLayout.WEST);
        fila.add(txtUsuario, BorderLayout.EAST); 

        return fila;
    }
    
    private JPanel crearFilaUsuarioObligatorio() {
        JPanel fila = new JPanel(new BorderLayout(10, 10)); 
		fila.setBorder(new EmptyBorder(5, 20, 5, 20)); 
        fila.setOpaque(false);

        JLabel lblUsuarioObligatorio = new JLabel("El usuario es obligatorio");
        lblUsuarioObligatorio.setFont(AppFont.small());
        lblUsuarioObligatorio.setForeground(Color.RED);

        fila.add(lblUsuarioObligatorio, BorderLayout.EAST);
        
		return fila; 
    }

    private JPanel crearFilaContrasena() {
        JPanel fila = new JPanel(new BorderLayout(10, 10));
		fila.setBorder(new EmptyBorder(10, 20, 10, 20)); 
		fila.setOpaque(false);

        JLabel lblPassword = new JLabel("Contraseña:");
        lblPassword.setFont(AppFont.big());

        JPasswordField txtPassword = new JPasswordField(15);
        txtPassword.setFont(AppFont.big());
        txtPassword.setBorder(BorderFactory.createEmptyBorder(8,10,8,10));
        txtPassword.putClientProperty("JTextField.placeholderText", "Ingrese la contraseña");

        fila.add(lblPassword, BorderLayout.WEST);
        fila.add(txtPassword, BorderLayout.EAST);
        
        return fila;
    }
    
    private JPanel crearFilaMostrarContrasena() {
        JPanel fila = new JPanel(new BorderLayout(10, 10));
		fila.setBorder(new EmptyBorder(10, 20, 10, 20)); 
		fila.setOpaque(false);

        JCheckBox chkMostrar = new JCheckBox("Mostrar");
        chkMostrar.setOpaque(false);
        chkMostrar.setFont(new Font("Arial", Font.BOLD, 12));
        
        fila.add(chkMostrar, BorderLayout.EAST);
    
        return fila;
    }
    
    
    private JPanel crearFilaContrasenaObligatoria() {
        JPanel fila = new JPanel(new BorderLayout(10, 10)); 
		fila.setBorder(new EmptyBorder(5, 20, 5, 20)); 
        fila.setOpaque(false);

        JLabel lblContraObligatoria = new JLabel("La contraseña es obligatoria");
        lblContraObligatoria.setFont(AppFont.small());
        lblContraObligatoria.setForeground(Color.RED);
        
        fila.add(lblContraObligatoria, BorderLayout.EAST);
        
		return fila; 
    }
    
    private JPanel crearFilaAceptarTerminos() {
        JPanel fila = new JPanel(new BorderLayout(10, 10));
		fila.setBorder(new EmptyBorder(10, 20, 20, 20)); 
		fila.setOpaque(false);
		        
        JCheckBox chkAceptarTerminos = new JCheckBox("Aceptar términos y condiciones");
        chkAceptarTerminos.setFont(AppFont.normal());
        fila.add(chkAceptarTerminos, BorderLayout.WEST);
		
		return fila;
    }
    
    private JPanel crearFilaRecordarme() {
        JPanel fila = new JPanel(new BorderLayout());
        fila.setBorder(new EmptyBorder(5, 20, 5, 20));
        fila.setOpaque(false);

        JCheckBox chkRecordar = new JCheckBox("Recordar usuario");
        chkRecordar.setOpaque(false);
        chkRecordar.setFont(AppFont.normal());

        fila.add(chkRecordar, BorderLayout.WEST);

        return fila;
    }
    
    private JPanel crearFilaOlvido() {
        JPanel fila = new JPanel();
        fila.setBorder(new EmptyBorder(5, 20, 0, 20));
        fila.setOpaque(false);

        JLabel lblOlvido = new JLabel("<html><u>¿Olvidaste tu contraseña?</u></html>");
        lblOlvido.setForeground(new Color(0,0,0));
        lblOlvido.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        lblOlvido.setFont(AppFont.small());

        fila.add(lblOlvido);

        return fila;
    }
    
    private JPanel crearFilaError() {
        JPanel fila = new JPanel();
        fila.setBorder(new EmptyBorder(5, 20, 10, 20));
        fila.setOpaque(false);

        JLabel lblError = new JLabel("Usuario o contraseña incorrectos");
        lblError.setForeground(Color.RED);
        lblError.setFont(AppFont.small());

        fila.add(lblError);

        return fila;
    }
}