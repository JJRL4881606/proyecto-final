package views;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

import components.GradientPanel;

@SuppressWarnings("serial")
public class RegistrationWindow extends JFrame 
{
    public RegistrationWindow() 
    {
        // Pantalla completa (maximizada)
        this.setExtendedState(JFrame.MAXIMIZED_BOTH);
        this.setTitle("Registro | HOTEL MJ");
        this.setResizable(true);
        this.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        
        // Fondo degradado
        GradientPanel fondo = new GradientPanel(
            new Color(100,149,237), 
            new Color(25,25,112)
        );

        fondo.setLayout(new BorderLayout());
        setContentPane(fondo);    

        //Agregar ícono aplicación
        Toolkit tk = Toolkit.getDefaultToolkit();
        Image icono = tk.getImage("src/img/iconoRegistroUsuario.png");
        setIconImage(icono);
                
        //Agregar el panel
        add(crearVistaConScroll(new RegistrationView()), BorderLayout.CENTER);        
        
        this.setVisible(true);
        
        addWindowListener(new WindowListener() 
        {
			public void windowOpened(WindowEvent e){}
			public void windowIconified(WindowEvent e){}
			public void windowDeiconified(WindowEvent e){}
			public void windowDeactivated(WindowEvent e){}
			public void windowClosing(WindowEvent e) 
			{
				handleClose();
			}
			public void windowClosed(WindowEvent e){}
			public void windowActivated(WindowEvent e) {}
		});
    }
    
    private void handleClose() {
		int option = JOptionPane.showConfirmDialog(this, "¿Seguro que deseas salir? Se perderán todos los datos");
		
		if(option == JOptionPane.YES_OPTION) {
			System.exit(0);
			//dispose();*/
		}
	}
    
    private JScrollPane crearVistaConScroll(JPanel panel) {
        JScrollPane scroll = new JScrollPane(panel);
        scroll.setBorder(null);
        scroll.getVerticalScrollBar().setUnitIncrement(12);
        scroll.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        return scroll;
    }
}