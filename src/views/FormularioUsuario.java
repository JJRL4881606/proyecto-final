package views;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;

import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JRadioButton;
import javax.swing.JSpinner;

import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Image;
import java.awt.Toolkit;

import javax.imageio.ImageIO;
import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.JTextField;
import javax.swing.SpinnerDateModel;
import javax.swing.border.EmptyBorder;

import components.GradientPanel;
import components.RoundButton;
import components.RoundedPanel;
import utils.AppFont;

@SuppressWarnings("serial")
public class FormularioUsuario extends JFrame 
{
    public FormularioUsuario() 
    {
        setSize(500, 800);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setTitle("Registro");
        setLocationRelativeTo(null);
        
        GradientPanel fondo = new GradientPanel(
                new Color(100,149,237), 
                new Color(25,25,112)
        );
        fondo.setLayout(new GridBagLayout());
        setContentPane(fondo);        
        
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
	    card.setMaximumSize(new Dimension(450, 700));
	    card.setAlignmentX(CENTER_ALIGNMENT);
	    card.add(crearTitulo());
	    card.add(panelFormularioCrearCuenta());
	    card.add(crearBoton());

	    GridBagConstraints gbc = new GridBagConstraints();
	    gbc.gridx = 0;
	    gbc.gridy = 0;
	    gbc.anchor = GridBagConstraints.CENTER;
	    add(card, gbc);
    }
    
    public JPanel panelFormularioCrearCuenta() {
        JPanel panelPrincipal = new JPanel();
        panelPrincipal.setBackground(new Color(151, 210, 251));
        panelPrincipal.setLayout(new BoxLayout(panelPrincipal, BoxLayout.Y_AXIS));
        panelPrincipal.setAlignmentX(Component.CENTER_ALIGNMENT);
        panelPrincipal.setBorder(new EmptyBorder(0, 40, 10, 40));

        
        JLabel lblNombre = new JLabel("Nombre(s)");
        lblNombre.setFont(AppFont.normal());
        lblNombre.setAlignmentX(Component.CENTER_ALIGNMENT);
        panelPrincipal.add(lblNombre);
        
        JTextField txtNombre = new JTextField();
        txtNombre.setBorder(BorderFactory.createEmptyBorder(8,10,8,10));
        txtNombre.setFont(AppFont.normal());
        txtNombre.putClientProperty("JTextField.placeholderText", "Ingrese su(s) nombre(s)");
        txtNombre.setMaximumSize(new Dimension(Integer.MAX_VALUE, 35));
        panelPrincipal.add(txtNombre);
        panelPrincipal.add(Box.createRigidArea(new Dimension(0, 15)));
        
        
        JLabel lblApellidos = new JLabel("Apellidos");
        lblApellidos.setFont(AppFont.normal());
        lblApellidos.setAlignmentX(Component.CENTER_ALIGNMENT);
        panelPrincipal.add(lblApellidos);
        
        JTextField txtApellidos = new JTextField();
        txtApellidos.setBorder(BorderFactory.createEmptyBorder(8,10,8,10));
        txtApellidos.setFont(AppFont.normal());
        txtApellidos.putClientProperty("JTextField.placeholderText", "Ingrese sus apellidos");
        txtApellidos.setMaximumSize(new Dimension(Integer.MAX_VALUE, 35));
        panelPrincipal.add(txtApellidos);
        panelPrincipal.add(Box.createRigidArea(new Dimension(0, 15)));
        
        
        JLabel lblPassword = new JLabel("Contraseña");
        lblPassword.setAlignmentX(Component.CENTER_ALIGNMENT);
        lblPassword.setFont(AppFont.normal());
        panelPrincipal.add(lblPassword);
        
        JPasswordField txtPassword = new JPasswordField();
        txtPassword.setBorder(BorderFactory.createEmptyBorder(8,10,8,10));
        txtPassword.setFont(AppFont.normal());
        txtPassword.putClientProperty("JTextField.placeholderText", "Ingrese su contraseña");
        txtPassword.setMaximumSize(new Dimension(Integer.MAX_VALUE, 35));
        panelPrincipal.add(txtPassword);
        panelPrincipal.add(Box.createRigidArea(new Dimension(0, 15)));
        txtPassword.setAlignmentX(Component.CENTER_ALIGNMENT);
    
        
        JCheckBox chkMostrar = new JCheckBox("Mostrar contraseña");
        chkMostrar.setFont(AppFont.small());
        panelPrincipal.add(chkMostrar);
        panelPrincipal.add(Box.createRigidArea(new Dimension(0, 15)));
        chkMostrar.setAlignmentX(Component.CENTER_ALIGNMENT);

        
        JLabel lblEmail = new JLabel("Correo Electrónico");
        lblEmail.setAlignmentX(Component.CENTER_ALIGNMENT);
        lblEmail.setFont(AppFont.normal());
        panelPrincipal.add(lblEmail);
        
        JTextField txtEmail = new JTextField();
        txtEmail.setBorder(BorderFactory.createEmptyBorder(8,10,8,10));
        txtEmail.setFont(AppFont.normal());
        txtEmail.putClientProperty("JTextField.placeholderText", "Ingrese su correo electrónico");
        txtEmail.setMaximumSize(new Dimension(Integer.MAX_VALUE, 35));
        txtEmail.setAlignmentX(Component.CENTER_ALIGNMENT);
        panelPrincipal.add(txtEmail);
        panelPrincipal.add(Box.createRigidArea(new Dimension(0, 15)));
        
        
        JLabel lblFechaNacimiento = new JLabel("Fecha de nacimiento");
        lblFechaNacimiento.setFont(AppFont.normal());
        lblFechaNacimiento.setAlignmentX(Component.CENTER_ALIGNMENT);
        panelPrincipal.add(lblFechaNacimiento);

        JSpinner spFechaNacimiento = new JSpinner(new SpinnerDateModel());
        spFechaNacimiento.setMaximumSize(new Dimension(Integer.MAX_VALUE, 35));
        JSpinner.DateEditor editorFecha = new JSpinner.DateEditor(spFechaNacimiento, "dd/MM/yyyy");
        spFechaNacimiento.setEditor(editorFecha);
        panelPrincipal.add(spFechaNacimiento);
        panelPrincipal.add(Box.createRigidArea(new Dimension(0, 15)));

        
        JLabel lblPais = new JLabel("País");
        lblPais.setAlignmentX(Component.CENTER_ALIGNMENT);
        lblPais.setFont(AppFont.normal());
        panelPrincipal.add(lblPais);
        
        String[] listaPaises = {"Seleccione el país", "Afganistán", "Albania", "Alemania", "Andorra", "Angola", "Antigua y Barbuda", "Arabia Saudita", "Argelia", "Argentina", "Armenia", "Australia", "Austria", "Azerbaiyán", "Bahamas", "Bangladés", "Baréin", "Bélgica", "Belice", "Benín", "Bielorrusia", "Birmania", "Bolivia", "Bosnia y Herzegovina", "Botsuana", "Brasil", "Brunéi", "Bulgaria", "Burkina Faso", "Burundi", "Bután", "Cabo Verde", "Camboya", "Camerún", "Canadá", "Catar", "Chad", "Chile", "China", "Chipre", "Colombia", "Comoras", "Corea del Norte", "Corea del Sur", "Costa de Marfil", "Costa Rica", "Croacia", "Cuba", "Dinamarca", "Dominica", "Ecuador", "Egipto", "El Salvador", "Emiratos Árabes Unidos", "Eritrea", "Eslovaquia", "Eslovenia", "España", "Estados Unidos", "Estonia", "Esuatini", "Etiopía", "Filipinas", "Finlandia", "Fiyi", "Francia", "Gabón", "Gambia", "Georgia", "Ghana", "Granada", "Grecia", "Guatemala", "Guinea", "Guinea-Bisáu", "Guinea Ecuatorial", "Guyana", "Haití", "Honduras", "Hungría", "India", "Indonesia", "Irak", "Irán", "Irlanda", "Islandia", "Islas Marshall", "Islas Salomón", "Israel", "Italia", "Jamaica", "Japón", "Jordania", "Kazajistán", "Kenia", "Kirguistán", "Kiribati", "Kuwait", "Laos", "Lesoto", "Letonia", "Líbano", "Liberia", "Libia", "Liechtenstein", "Lituania", "Luxemburgo", "Madagascar", "Malasia", "Malaui", "Maldivas", "Malí", "Malta", "Marruecos", "Mauricio", "Mauritania", "México", "Micronesia", "Moldavia", "Mónaco", "Mongolia", "Montenegro", "Mozambique", "Namibia", "Nauru", "Nepal", "Nicaragua", "Níger", "Nigeria", "Noruega", "Nueva Zelanda", "Omán", "Países Bajos", "Pakistán", "Palaos", "Palestina", "Panamá", "Papúa Nueva Guinea", "Paraguay", "Perú", "Polonia", "Portugal", "Reino Unido", "República Centroafricana", "República Checa", "República del Congo", "República Democrática del Congo", "República Dominicana", "Ruanda", "Rumania", "Rusia", "Samoa", "San Cristóbal y Nieves", "San Marino", "San Vicente y las Granadinas", "Santa Lucía", "Santo Tomé y Príncipe", "Senegal", "Serbia", "Seychelles", "Sierra Leona", "Singapur", "Siria", "Somalia", "Sri Lanka", "Sudáfrica", "Sudán", "Sudán del Sur", "Suecia", "Suiza", "Surinam", "Tailandia", "Taiwán", "Tanzania", "Tayikistán", "Timor Oriental", "Togo", "Tonga", "Trinidad y Tobago", "Túnez", "Turkmenistán", "Turquía", "Tuvalu", "Ucrania", "Uganda", "Uruguay", "Uzbekistán", "Vanuatu", "Vaticano", "Venezuela", "Vietnam", "Yemen", "Yibuti", "Zambia", "Zimbabue"};
        JComboBox<String> comboPais = new JComboBox<>(listaPaises);
        comboPais.setBorder(BorderFactory.createEmptyBorder(8,10,8,10));
        comboPais.putClientProperty("JComboBox.placeholderText", "Seleccione el país");
        comboPais.setAlignmentX(Component.CENTER_ALIGNMENT);
        panelPrincipal.add(comboPais);
        panelPrincipal.add(Box.createRigidArea(new Dimension(0, 15)));
        
        
        JPanel radGenero = new JPanel();
        radGenero.setLayout(new GridLayout(0, 1));
        radGenero.setOpaque(false);
        radGenero.setBorder(BorderFactory.createTitledBorder("Seleccione el género"));
        radGenero.setFont(AppFont.normal());
        JRadioButton hombre = new JRadioButton("Hombre");
        JRadioButton mujer = new JRadioButton("Mujer");
        radGenero.add(hombre);
        radGenero.add(mujer);
        radGenero.setAlignmentX(Component.CENTER_ALIGNMENT);
        radGenero.setMaximumSize(new Dimension(Integer.MAX_VALUE, 70));
        panelPrincipal.add(radGenero);
        panelPrincipal.add(Box.createRigidArea(new Dimension(0, 15)));

        
        JCheckBox chkAceptarTerminos = new JCheckBox("Acepto los términos y condiciones");
        chkAceptarTerminos.setFont(AppFont.normal());
        chkAceptarTerminos.setAlignmentX(Component.CENTER_ALIGNMENT);
        panelPrincipal.add(chkAceptarTerminos);
        panelPrincipal.add(Box.createRigidArea(new Dimension(0, 15)));        
        
        
        JCheckBox chkNewsletter = new JCheckBox("Suscribirme al newsletter");
        chkNewsletter.setFont(AppFont.normal());
        chkNewsletter.setAlignmentX(Component.CENTER_ALIGNMENT);
        panelPrincipal.add(chkNewsletter);
        panelPrincipal.add(Box.createRigidArea(new Dimension(0, 15)));

        return panelPrincipal;
    }
    
	private JPanel crearTitulo() {
	    JPanel panel = new JPanel();
	    panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
	    panel.setOpaque(false);

        JLabel lblTitulo = new JLabel("NUEVO REGISTRO");
		lblTitulo.setFont(AppFont.subtitle());
		lblTitulo.setBorder(new EmptyBorder(20, 10, 10, 10)); 
        lblTitulo.setAlignmentX(Component.CENTER_ALIGNMENT);
        panel.add(lblTitulo);
        panel.add(Box.createRigidArea(new Dimension(0, 25)));

        return panel;
	}
	
	private JPanel crearBoton() {
	    JPanel panel = new JPanel();
	    panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
	    panel.setOpaque(false);
	    panel.setAlignmentX(Component.CENTER_ALIGNMENT);

        RoundButton botonCrearCuenta = new RoundButton("CREAR CUENTA", new ImageIcon("src/img/login-icon.png"));
		botonCrearCuenta.setBackground(new Color(255, 249, 179));
		botonCrearCuenta.setForeground(Color.BLACK);
		botonCrearCuenta.setFont(AppFont.big());
        botonCrearCuenta.setFocusPainted(false);
        botonCrearCuenta.setAlignmentX(Component.CENTER_ALIGNMENT);
		
		try
		{
			Image icono = ImageIO.read(getClass().getResource("/img/login-icon.png"));
			icono = icono.getScaledInstance(30,30, Image.SCALE_SMOOTH);
			botonCrearCuenta.setIcon(new ImageIcon(icono));
		}
		catch(Exception ex) 
		{
			System.out.println("No está la imagen del ícono");
		}
		
        panel.add(botonCrearCuenta);
        
        return panel;
	}

}