package views;

import java.awt.Font;

import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Image;
import java.awt.Toolkit;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;

import components.RoundButton;
import components.RoundedPanel;

public class FormularioUsuario extends JFrame 
{
    public FormularioUsuario() 
    {
        setSize(450, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setTitle("Registro");
        setLocationRelativeTo(null);
        
        getContentPane().setBackground(new Color(100,149,237)); 
        
        Toolkit tk = Toolkit.getDefaultToolkit();
        Image icono = tk.getImage("src/img/iconoRegistroUsuario.png");
        setIconImage(icono);

        inicializarComponentes();
        setVisible(true);
    }

    public void inicializarComponentes() 
    {
        this.setLayout(new BorderLayout());
        
        JPanel panelPrincipal = new JPanel();
        panelPrincipal.setBackground(new Color(151, 210, 251));
        panelPrincipal.setLayout(new BoxLayout(panelPrincipal, BoxLayout.Y_AXIS));
        panelPrincipal.setBorder(new EmptyBorder(30, 40, 30, 40));

        JLabel lblTitulo = new JLabel("NUEVO REGISTRO");
        lblTitulo.setFont(new Font("Arial", Font.BOLD, 20));
        lblTitulo.setAlignmentX(Component.CENTER_ALIGNMENT);
        panelPrincipal.add(lblTitulo);
        panelPrincipal.add(Box.createRigidArea(new Dimension(0, 25)));

        JLabel lblUsuario = new JLabel("Nombre de Usuario");
        lblUsuario.setFont(new Font("Arial", Font.BOLD, 13));
        panelPrincipal.add(lblUsuario);
        
        JTextField txtUsuario = new JTextField();
        panelPrincipal.add(txtUsuario);
        panelPrincipal.add(Box.createRigidArea(new Dimension(0, 15)));

        JLabel lblPassword = new JLabel("Contraseña");
        lblPassword.setFont(new Font("Arial", Font.BOLD, 13));
        panelPrincipal.add(lblPassword);
        
        JPasswordField txtPassword = new JPasswordField();
        panelPrincipal.add(txtPassword);

        JCheckBox chkMostrar = new JCheckBox("Mostrar contraseña");
        chkMostrar.setFont(new Font("Arial", Font.PLAIN, 11));
        panelPrincipal.add(chkMostrar);
        panelPrincipal.add(Box.createRigidArea(new Dimension(0, 15)));

        JLabel lblEmail = new JLabel("Correo Electrónico");
        lblEmail.setFont(new Font("Arial", Font.BOLD, 13));
        panelPrincipal.add(lblEmail);
        
        JTextField txtEmail = new JTextField();
        panelPrincipal.add(txtEmail);
        panelPrincipal.add(Box.createRigidArea(new Dimension(0, 15)));

        JLabel lblPais = new JLabel("País");
        lblPais.setFont(new Font("Arial", Font.BOLD, 13));
        panelPrincipal.add(lblPais);
        
        JTextField txtPais = new JTextField();
        panelPrincipal.add(txtPais);
        panelPrincipal.add(Box.createRigidArea(new Dimension(0, 20)));

        JCheckBox chkAceptarTerminos = new JCheckBox("Acepto los términos");
        chkAceptarTerminos.setFont(new Font("Arial", Font.PLAIN, 12));
        panelPrincipal.add(chkAceptarTerminos);
        panelPrincipal.add(Box.createRigidArea(new Dimension(0, 20)));

        JButton botonCrearCuenta = new JButton("CREAR CUENTA");
        botonCrearCuenta.setBackground(new Color(27, 73, 101));
        botonCrearCuenta.setForeground(Color.WHITE);
        botonCrearCuenta.setFont(new Font("Arial", Font.BOLD, 14));
        botonCrearCuenta.setFocusPainted(false);
        panelPrincipal.add(botonCrearCuenta);
        
        panelPrincipal.add(Box.createRigidArea(new Dimension(0, 15)));

        JPanel contenedorConMargen = new JPanel(new BorderLayout());
        contenedorConMargen.setBorder(new EmptyBorder(40, 40, 40, 40));
        contenedorConMargen.setOpaque(false);
        contenedorConMargen.add(panelPrincipal, BorderLayout.CENTER);

        add(contenedorConMargen);
    }
}