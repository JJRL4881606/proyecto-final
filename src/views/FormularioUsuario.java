package views;

import java.awt.Font;
import java.awt.GridBagLayout;
import java.awt.GridLayout;

import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JRadioButton;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Image;
import java.awt.Toolkit;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;

import components.RoundButton;
import components.RoundedPanel;

@SuppressWarnings("serial")
public class FormularioUsuario extends JFrame 
{
    public FormularioUsuario() 
    {
        setSize(500, 800);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setTitle("Registro");
        setLocationRelativeTo(null);
        
        getContentPane().setBackground(new Color(100,149,237)); 
	    setLayout(new GridBagLayout());
        
        Toolkit tk = Toolkit.getDefaultToolkit();
        Image icono = tk.getImage("src/img/iconoRegistroUsuario.png");
        setIconImage(icono);

        inicializarComponentes();
        setVisible(true);
    }
    

    public void inicializarComponentes() 
    {
	    JPanel card = new RoundedPanel(50);
	    card.setLayout(new BoxLayout(card, BoxLayout.Y_AXIS));
	    card.setBackground(new Color(151, 210, 251));
	    card.setBorder(BorderFactory.createEmptyBorder(25, 35, 25, 35));
	    card.setMaximumSize(new Dimension(450, 350));
	    card.setAlignmentX(CENTER_ALIGNMENT);
	    
	    card.add(crearTitulo());
	    card.add(panelFormularioCrearCuenta());
	    card.add(crearBoton());

	    add(card); 
    }
    
    public JPanel panelFormularioCrearCuenta() {
        JPanel panelPrincipal = new JPanel();
        panelPrincipal.setBackground(new Color(151, 210, 251));
        panelPrincipal.setLayout(new BoxLayout(panelPrincipal, BoxLayout.Y_AXIS));
        panelPrincipal.setAlignmentX(Component.CENTER_ALIGNMENT);
        panelPrincipal.setBorder(new EmptyBorder(30, 40, 30, 40));

        
        JLabel lblUsuario = new JLabel("Nombre de Usuario");
        lblUsuario.setFont(new Font("Arial", Font.BOLD, 13));
        panelPrincipal.add(lblUsuario);
        
        JTextField txtUsuario = new JTextField();
        txtUsuario.setBorder(BorderFactory.createEmptyBorder(8,10,8,10));
        panelPrincipal.add(txtUsuario);
        panelPrincipal.add(Box.createRigidArea(new Dimension(0, 15)));

        
        JLabel lblPassword = new JLabel("Contraseña");
        lblPassword.setFont(new Font("Arial", Font.BOLD, 13));
        panelPrincipal.add(lblPassword);
        
        JPasswordField txtPassword = new JPasswordField();
        txtPassword.setBorder(BorderFactory.createEmptyBorder(8,10,8,10));
        panelPrincipal.add(txtPassword);
        panelPrincipal.add(Box.createRigidArea(new Dimension(0, 15)));
        
        JCheckBox chkMostrar = new JCheckBox("Mostrar contraseña");
        chkMostrar.setFont(new Font("Arial", Font.PLAIN, 11));
        panelPrincipal.add(chkMostrar);
        panelPrincipal.add(Box.createRigidArea(new Dimension(0, 15)));

        JLabel lblEmail = new JLabel("Correo Electrónico");
        lblEmail.setFont(new Font("Arial", Font.BOLD, 13));
        panelPrincipal.add(lblEmail);
        
        JTextField txtEmail = new JTextField();
        txtEmail.setBorder(BorderFactory.createEmptyBorder(8,10,8,10));
        panelPrincipal.add(txtEmail);
        panelPrincipal.add(Box.createRigidArea(new Dimension(0, 15)));

        JLabel lblPais = new JLabel("País");
        lblPais.setFont(new Font("Arial", Font.BOLD, 13));
        panelPrincipal.add(lblPais);
        
        String[] listaPaises = {"Afganistán", "Albania", "Alemania", "Andorra", "Angola", "Antigua y Barbuda", "Arabia Saudita", "Argelia", "Argentina", "Armenia", "Australia", "Austria", "Azerbaiyán", "Bahamas", "Bangladés", "Baréin", "Bélgica", "Belice", "Benín", "Bielorrusia", "Birmania", "Bolivia", "Bosnia y Herzegovina", "Botsuana", "Brasil", "Brunéi", "Bulgaria", "Burkina Faso", "Burundi", "Bután", "Cabo Verde", "Camboya", "Camerún", "Canadá", "Catar", "Chad", "Chile", "China", "Chipre", "Colombia", "Comoras", "Corea del Norte", "Corea del Sur", "Costa de Marfil", "Costa Rica", "Croacia", "Cuba", "Dinamarca", "Dominica", "Ecuador", "Egipto", "El Salvador", "Emiratos Árabes Unidos", "Eritrea", "Eslovaquia", "Eslovenia", "España", "Estados Unidos", "Estonia", "Esuatini", "Etiopía", "Filipinas", "Finlandia", "Fiyi", "Francia", "Gabón", "Gambia", "Georgia", "Ghana", "Granada", "Grecia", "Guatemala", "Guinea", "Guinea-Bisáu", "Guinea Ecuatorial", "Guyana", "Haití", "Honduras", "Hungría", "India", "Indonesia", "Irak", "Irán", "Irlanda", "Islandia", "Islas Marshall", "Islas Salomón", "Israel", "Italia", "Jamaica", "Japón", "Jordania", "Kazajistán", "Kenia", "Kirguistán", "Kiribati", "Kuwait", "Laos", "Lesoto", "Letonia", "Líbano", "Liberia", "Libia", "Liechtenstein", "Lituania", "Luxemburgo", "Madagascar", "Malasia", "Malaui", "Maldivas", "Malí", "Malta", "Marruecos", "Mauricio", "Mauritania", "México", "Micronesia", "Moldavia", "Mónaco", "Mongolia", "Montenegro", "Mozambique", "Namibia", "Nauru", "Nepal", "Nicaragua", "Níger", "Nigeria", "Noruega", "Nueva Zelanda", "Omán", "Países Bajos", "Pakistán", "Palaos", "Palestina", "Panamá", "Papúa Nueva Guinea", "Paraguay", "Perú", "Polonia", "Portugal", "Reino Unido", "República Centroafricana", "República Checa", "República del Congo", "República Democrática del Congo", "República Dominicana", "Ruanda", "Rumania", "Rusia", "Samoa", "San Cristóbal y Nieves", "San Marino", "San Vicente y las Granadinas", "Santa Lucía", "Santo Tomé y Príncipe", "Senegal", "Serbia", "Seychelles", "Sierra Leona", "Singapur", "Siria", "Somalia", "Sri Lanka", "Sudáfrica", "Sudán", "Sudán del Sur", "Suecia", "Suiza", "Surinam", "Tailandia", "Taiwán", "Tanzania", "Tayikistán", "Timor Oriental", "Togo", "Tonga", "Trinidad y Tobago", "Túnez", "Turkmenistán", "Turquía", "Tuvalu", "Ucrania", "Uganda", "Uruguay", "Uzbekistán", "Vanuatu", "Vaticano", "Venezuela", "Vietnam", "Yemen", "Yibuti", "Zambia", "Zimbabue"};
        JComboBox<String> comboPais = new JComboBox<>(listaPaises);
        comboPais.setBorder(BorderFactory.createEmptyBorder(8,10,8,10));
        panelPrincipal.add(comboPais);
        panelPrincipal.add(Box.createRigidArea(new Dimension(0, 15)));
        
        JPanel radGenero = new JPanel();
        radGenero.setLayout(new FlowLayout());
        radGenero.setOpaque(false);
        radGenero.setBorder(BorderFactory.createTitledBorder("Seleccione el género"));
        radGenero.setFont(new Font("Arial", Font.BOLD, 13));
        radGenero.setLayout(new GridLayout(0, 1));
        JRadioButton hombre = new JRadioButton("Hombre");
        JRadioButton mujer = new JRadioButton("Mujer");
        radGenero.add(hombre);
        radGenero.add(mujer);
        panelPrincipal.add(radGenero);
        panelPrincipal.add(Box.createRigidArea(new Dimension(0, 15)));

        JCheckBox chkAceptarTerminos = new JCheckBox("Acepto los términos");
        chkAceptarTerminos.setFont(new Font("Arial", Font.PLAIN, 12));
        panelPrincipal.add(chkAceptarTerminos);
        panelPrincipal.add(Box.createRigidArea(new Dimension(0, 15)));        
        
        JCheckBox chkNewsletter = new JCheckBox("Suscribirme al newsletter");
        chkNewsletter.setFont(new Font("Arial", Font.PLAIN, 12));
        panelPrincipal.add(chkNewsletter);
        panelPrincipal.add(Box.createRigidArea(new Dimension(0, 15)));

        return panelPrincipal;
    }
    
	private JPanel crearTitulo() {
	    JPanel panel = new JPanel();
	    panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
	    panel.setOpaque(false);

        JLabel lblTitulo = new JLabel("NUEVO REGISTRO");
        lblTitulo.setFont(new Font("Arial", Font.BOLD, 20));
		lblTitulo.setBorder(new EmptyBorder(50, 10, 20, 10)); 
        lblTitulo.setAlignmentX(Component.CENTER_ALIGNMENT);
        panel.add(lblTitulo);
        panel.add(Box.createRigidArea(new Dimension(0, 25)));

        return panel;
	}
	
	private JPanel crearBoton() {
	    JPanel panel = new JPanel();
	    panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
	    panel.setOpaque(false);

        RoundButton botonCrearCuenta = new RoundButton("CREAR CUENTA", new ImageIcon("src/img/login-icon.png"));
        botonCrearCuenta.setBackground(new Color(27, 73, 101));
        botonCrearCuenta.setForeground(Color.WHITE);
        botonCrearCuenta.setFont(new Font("Arial", Font.BOLD, 14));
        botonCrearCuenta.setFocusPainted(false);
        botonCrearCuenta.setAlignmentX(Component.CENTER_ALIGNMENT);

        panel.add(botonCrearCuenta);
        
        return panel;
	}

}