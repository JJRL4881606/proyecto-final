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
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Image;
import java.awt.Toolkit;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;

import components.RoundButton;

public class FormularioUsuario extends JFrame
{
	
	public FormularioUsuario() 
	{
		
		setSize(500, 500);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setResizable(true);
		setTitle("Registro");
		setLocationRelativeTo(null);
		
		Toolkit tk = Toolkit.getDefaultToolkit();
		Image icono = tk.getImage("src/img/icono.png");
		setIconImage(icono);
		
		inicializarComponentes();
		
		setVisible(true);		
	}
	
	public void inicializarComponentes() 
	{
		
		JLabel lblTitulo = new JLabel("Registro");
		add(lblTitulo, BorderLayout.NORTH);
		lblTitulo.setHorizontalAlignment(SwingConstants.CENTER);
		
		JPanel panelComponentes = new JPanel();
		panelComponentes.setLayout(new BoxLayout(panelComponentes, BoxLayout.Y_AXIS));
		panelComponentes.setBorder(BorderFactory.createEmptyBorder(20,20,20,20));
		
		JScrollPane scroll = new JScrollPane(panelComponentes);
		scroll.setHorizontalScrollBar(null);

        JLabel lblUsuario = new JLabel("Usuario:");
        lblUsuario.setFont(new Font("Arial", Font.BOLD, 18));
        panelComponentes.add(lblUsuario);

        JTextField txtUsuario = new JTextField(15);
        txtUsuario.setFont(new Font("Arial", Font.PLAIN, 18));
        txtUsuario.setBorder(BorderFactory.createEmptyBorder(8,10,8,10));
        panelComponentes.add(txtUsuario);

        JLabel lblPassword = new JLabel("Contraseña:");
        lblPassword.setFont(new Font("Arial", Font.BOLD, 18));
        panelComponentes.add(lblPassword);

        JPasswordField txtPassword = new JPasswordField(15);
        txtPassword.setFont(new Font("Arial", Font.PLAIN, 18));
        txtPassword.setBorder(BorderFactory.createEmptyBorder(8,10,8,10));
        panelComponentes.add(txtPassword);

        JCheckBox chkMostrar = new JCheckBox("Mostrar");
        chkMostrar.setOpaque(false);
        chkMostrar.setFont(new Font("Arial", Font.PLAIN, 12));
        panelComponentes.add(chkMostrar);
        
        JLabel lblEmail = new JLabel("Email:");
        lblEmail.setFont(new Font("Arial", Font.BOLD, 18));
        panelComponentes.add(lblEmail);

        JTextField txtEmail = new JTextField(15);
        txtEmail.setFont(new Font("Arial", Font.PLAIN, 18));
        txtEmail.setBorder(BorderFactory.createEmptyBorder(8,10,8,10));
        panelComponentes.add(txtEmail);
        
        JLabel lblPais = new JLabel("Pais de residencia:");
        lblPais.setFont(new Font("Arial", Font.BOLD, 18));
        panelComponentes.add(lblPais);

        JTextField txtPais = new JTextField(15);
        txtPais.setFont(new Font("Arial", Font.PLAIN, 18));
        txtPais.setBorder(BorderFactory.createEmptyBorder(8,10,8,10));
        panelComponentes.add(txtPais);
        
        JCheckBox chkAceptarTerminos = new JCheckBox("Aceptar términos y condiciones");
        chkAceptarTerminos.setFont(new Font("Arial", Font.BOLD, 12));
        panelComponentes.add(chkAceptarTerminos);
   
        JLabel lblOlvido = new JLabel("<html><u>¿Olvidaste tu contraseña?</u></html>");
        lblOlvido.setForeground(new Color(0,0,0));
        lblOlvido.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        lblOlvido.setFont(new Font("Arial", Font.PLAIN, 12));
        panelComponentes.add(lblOlvido);
        
        JButton botonCrearCuenta = new RoundButton("Crear cuenta", new ImageIcon("src/img/login-icon.png"));
		botonCrearCuenta.setBackground(new Color(255, 249, 179));
		botonCrearCuenta.setForeground(Color.BLACK);
		botonCrearCuenta.setToolTipText("Haz click aquí");
		botonCrearCuenta.setFont(new Font("Arial", Font.BOLD, 20));
		panelComponentes.add(botonCrearCuenta);

		add(scroll);
	}
}