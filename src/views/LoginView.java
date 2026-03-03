package views;

import java.awt.GridBagLayout;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridBagConstraints;

import javax.imageio.ImageIO;
import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.border.Border;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;

import components.RoundButton;
import components.RoundedPanel;
import utils.AppFont;

@SuppressWarnings("serial")
public class LoginView extends JPanel
{
	int ventanaCentroW = 400;
	JTextField txtCorreo;
	JPasswordField txtContrasena;
	JLabel lblCorreoObligatorio;
	JLabel lblContraObligatoria;
	
    Border redBorder = BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(Color.RED, 2),
            BorderFactory.createEmptyBorder(8, 10, 8, 10)
    );
    
    Border normalBorder = BorderFactory.createEmptyBorder(8, 10, 8, 10);
	
	public LoginView() 
	{
		this.setBackground(new Color(100,149,237)); 
	    setLayout(new GridBagLayout());
		inicializarComponentes();
        setVisible(true);
	}

	private void inicializarComponentes() 
	{
	    JPanel card = new RoundedPanel(50);
	    card.setLayout(new BoxLayout(card, BoxLayout.Y_AXIS));
	    card.setBackground(new Color(151, 210, 251));
	    card.setBorder(BorderFactory.createEmptyBorder(25, 35, 25, 35));
	    card.setMaximumSize(new Dimension(450, 350));
	    card.setAlignmentX(CENTER_ALIGNMENT);
	    card.setBorder(BorderFactory.createEmptyBorder(25, 35, 25, 35));
	    
	    card.add(crearTitulo());
	    card.add(Box.createRigidArea(new Dimension(0, 10)));
	    card.add(crearFilaCorreo());
        card.add(crearFilaCorreoObligatorio());   
	    card.add(Box.createRigidArea(new Dimension(0, 10)));
        card.add(crearFilaContrasena());
        card.add(crearFilaMostrarContrasena()); 
        card.add(crearFilaContrasenaObligatoria()); 
	    card.add(Box.createRigidArea(new Dimension(0, 10)));
        card.add(crearFilaError());
	    card.add(Box.createRigidArea(new Dimension(0, 10)));
        card.add(crearFilaRecordarme());
	    card.add(Box.createRigidArea(new Dimension(0, 10)));
        card.add(crearFilaOlvido());
        card.add(Box.createRigidArea(new Dimension(0, 10)));
        card.add(crearBotones());
	    card.add(Box.createRigidArea(new Dimension(0, 10)));

	    GridBagConstraints gbc = new GridBagConstraints();
	    gbc.gridx = 0;
	    gbc.gridy = 0;
	    gbc.anchor = GridBagConstraints.CENTER;
	    gbc.insets = new java.awt.Insets(40, 40, 40, 40);
	    add(card, gbc);
	}
	
	private JPanel crearTitulo() {
	    JPanel panel = new JPanel();
	    panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
	    panel.setOpaque(false);
	    
		JLabel lblTitulo = new JLabel("HOTEL MJ - LOGIN");
		lblTitulo.setBorder(new EmptyBorder(30, 20, 20, 20)); 
		lblTitulo.setFont(AppFont.title());
	    lblTitulo.setAlignmentX(CENTER_ALIGNMENT);
		
		JLabel lblInstrucciones = new JLabel("Ingrese sus datos para iniciar sesión");
		lblInstrucciones.setBorder(new EmptyBorder(10, 20, 30, 20)); 
		lblInstrucciones.setFont(AppFont.subtitle());
		lblInstrucciones.setAlignmentX(CENTER_ALIGNMENT); 

	    panel.add(lblTitulo);
	    panel.add(Box.createVerticalStrut(8));
	    panel.add(lblInstrucciones);

	    return panel;
	}
	private JPanel crearFilaCorreo() {
	    JPanel fila = new JPanel();
	    fila.setLayout(new BoxLayout(fila, BoxLayout.Y_AXIS));
	    fila.setOpaque(false);
	    fila.setBorder(new EmptyBorder(10, 20, 10, 20));

	    JLabel lblCorreo = new JLabel("Correo electrónico");
	    lblCorreo.setFont(AppFont.normal());
	    lblCorreo.setAlignmentX(CENTER_ALIGNMENT);

	    txtCorreo = new JTextField();
	    txtCorreo.setFont(AppFont.normal());
	    txtCorreo.setBorder(normalBorder);
	    txtCorreo.setMaximumSize(new Dimension(Integer.MAX_VALUE, 35));
	    txtCorreo.putClientProperty("JTextField.placeholderText", "Ingrese su correo electrónico");

	    fila.add(lblCorreo);
	    fila.add(Box.createRigidArea(new Dimension(0, 6)));
	    fila.add(txtCorreo);

	    return fila;
	}
    
    private JPanel crearFilaContrasena() {
        JPanel fila = new JPanel(new BorderLayout(10, 10));
	    fila.setLayout(new BoxLayout(fila, BoxLayout.Y_AXIS));
	    fila.setOpaque(false);
	    fila.setBorder(new EmptyBorder(10, 20, 10, 20));

        JLabel lblContrasena = new JLabel("Contraseña:");
        lblContrasena.setFont(AppFont.normal());
        lblContrasena.setAlignmentX(CENTER_ALIGNMENT);

        txtContrasena = new JPasswordField();
        txtContrasena.setFont(AppFont.normal());
        txtContrasena.setBorder(normalBorder);
        txtContrasena.setMaximumSize(new Dimension(Integer.MAX_VALUE, 35));
        txtContrasena.putClientProperty("JTextField.placeholderText", "Ingrese su contraseña");

	    fila.add(lblContrasena);
	    fila.add(Box.createRigidArea(new Dimension(0, 6)));
	    fila.add(txtContrasena);
        
        return fila;
    }
    
    private JPanel crearFilaCorreoObligatorio() {
        JPanel fila = new JPanel();
        fila.setLayout(new BoxLayout(fila, BoxLayout.Y_AXIS));
        fila.setOpaque(false);
        fila.setBorder(new EmptyBorder(0, 20, 10, 20));

        lblCorreoObligatorio = new JLabel("El correo electrónico es obligatorio");
		lblCorreoObligatorio.setVisible(false);
		lblCorreoObligatorio.setFont(AppFont.small());
		lblCorreoObligatorio.setForeground(Color.RED);
        lblCorreoObligatorio.setAlignmentX(CENTER_ALIGNMENT);

        fila.add(lblCorreoObligatorio);

        return fila;
    }
    
    private JPanel crearFilaContrasenaObligatoria() {
        JPanel fila = new JPanel();
        fila.setLayout(new BoxLayout(fila, BoxLayout.Y_AXIS));
        fila.setOpaque(false);
        fila.setBorder(new EmptyBorder(0, 20, 10, 20));

        lblContraObligatoria = new JLabel("La contraseña es obligatoria");
        lblContraObligatoria.setVisible(false);
        lblContraObligatoria.setFont(AppFont.small());
        lblContraObligatoria.setForeground(Color.RED);
        lblContraObligatoria.setAlignmentX(CENTER_ALIGNMENT);

        fila.add(lblContraObligatoria);
        
		return fila; 
    }
    
    private JPanel crearFilaMostrarContrasena() {
        JPanel fila = new JPanel();
        fila.setLayout(new BoxLayout(fila, BoxLayout.Y_AXIS));
        fila.setOpaque(false);
        fila.setBorder(new EmptyBorder(0, 20, 10, 20));

        JCheckBox chkMostrar = new JCheckBox("Mostrar contraseña");
        chkMostrar.setOpaque(false);
        chkMostrar.setFont(AppFont.small());
        chkMostrar.setAlignmentX(CENTER_ALIGNMENT);
        
        fila.add(chkMostrar);
    
        return fila;
    }
    
    private JPanel crearFilaRecordarme() {
        JPanel fila = new JPanel();
        fila.setLayout(new BoxLayout(fila, BoxLayout.Y_AXIS));
        fila.setOpaque(false);
        fila.setBorder(new EmptyBorder(0, 20, 10, 20));

        JCheckBox chkRecordar = new JCheckBox("Recordar usuario");
        chkRecordar.setOpaque(false);
        chkRecordar.setFont(AppFont.normal());
        chkRecordar.setAlignmentX(CENTER_ALIGNMENT);

        fila.add(chkRecordar);
        
		return fila;
    }
    
    private JPanel crearFilaOlvido() {
        JPanel fila = new JPanel();
        fila.setLayout(new BoxLayout(fila, BoxLayout.Y_AXIS));
        fila.setOpaque(false);
        fila.setBorder(new EmptyBorder(0, 20, 10, 20));

        JLabel lblOlvido = new JLabel("<html><u>¿Olvidaste tu contraseña?</u></html>");
        lblOlvido.setForeground(new Color(0,0,0));
        lblOlvido.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        lblOlvido.setFont(AppFont.small());
        lblOlvido.setAlignmentX(CENTER_ALIGNMENT);
        lblOlvido.setHorizontalAlignment(SwingConstants.CENTER);

        fila.add(lblOlvido);

        return fila;
    }
    
    private JPanel crearFilaError() {
        JPanel fila = new JPanel();
        fila.setLayout(new BoxLayout(fila, BoxLayout.Y_AXIS));
        fila.setOpaque(false);
        fila.setBorder(new EmptyBorder(0, 20, 10, 20));

        JLabel lblError = new JLabel("Usuario o contraseña incorrectos");
        lblError.setForeground(Color.RED);
        lblError.setFont(AppFont.small());
        lblError.setAlignmentX(CENTER_ALIGNMENT);

        fila.add(lblError);

        return fila;
    }
    
    //CREAR LOS BOTONES
    
	public JPanel crearBotones() {
        JPanel fila = new JPanel();
        fila.setBorder(new EmptyBorder(5, 20, 10, 20));
        fila.setOpaque(false);

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
		
		fila.add(botonIniciarSesion);	
		fila.add(botonCrearCuenta);
		
		botonIniciarSesion.addActionListener(e -> login());
		
		return fila;
	}
	
	//MOSTRAR MENSAJE DE QUE SE INICIÓ SESIÓN
	
	private void login() {
	    if(validarLogin()) {
	        JOptionPane.showMessageDialog(
	            this,
	            "Se inició la sesión",
	            "Sesión iniciada",
	            JOptionPane.INFORMATION_MESSAGE
	        );
	    }
	}
	
	//MOSTRAR LABELS DE ERROR
	
	private void mostrarErrorCorreo(String mensaje) {
		lblCorreoObligatorio.setText(mensaje);
		lblCorreoObligatorio.setVisible(true);
		txtCorreo.setBorder(redBorder);
	}	
	
	private void mostrarErrorContrasena(String mensaje) {
		lblContraObligatoria.setText(mensaje);
		lblContraObligatoria.setVisible(true);
		txtContrasena.setBorder(redBorder);
	}
	
	private void resetearMensajeError() {
	    lblCorreoObligatorio.setVisible(false);
		txtCorreo.setBorder(normalBorder);

	    lblContraObligatoria.setVisible(false);
	    txtContrasena.setBorder(normalBorder);

	}
	
	//VALIDAR QUE LOS CAMPOS NO ESTÉN VACÍOS
	
	private boolean validarLogin() {
	    resetearMensajeError();
	    boolean valido = true;

	    if(txtCorreo.getText().trim().isEmpty()) {
	        mostrarErrorCorreo("El correo es obligatorio");
	        valido = false;
	    }

	    String contrasena = String.valueOf(txtContrasena.getPassword());
	    
	    if(contrasena.trim().isEmpty()) {
	        mostrarErrorContrasena("La contraseña es obligatoria");
	        valido = false;
	    }

	    return valido;
	}
}
