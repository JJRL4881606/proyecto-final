package views.auth;

import java.awt.BorderLayout;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

@SuppressWarnings("serial")

//Ventana principal para el registro de nuevos usuarios
public class RegistrationWindow extends JFrame {
	
	private RegistrationView registrationView;
	
    public RegistrationWindow() {
        this.setExtendedState(JFrame.MAXIMIZED_BOTH);
        this.setTitle("Registro | ATLANTIS THE PALM, DUBAI");
        this.setResizable(true);
        this.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        
        JPanel background = new JPanel();
        background.setLayout(new BorderLayout());
        setContentPane(background);    

        // Configurar el icono de la ventana
        Image icon = Toolkit.getDefaultToolkit().getImage(
    	    getClass().getResource("/assets/img/icons/registration-icon.png")
    	);
        setIconImage(icon);
                
        // Crear y mostrar la vista de registro
        add(createViewScroll(registrationView = new RegistrationView()), BorderLayout.CENTER);        
        
        this.setVisible(true);
        
        // Confirmar antes de cerrar la ventana para no perder info
        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                handleClose();
            }
        });
    }
    
    // Muestra confirmación antes de salir
    private void handleClose() {
		int option = JOptionPane.showConfirmDialog(this, "¿Seguro que deseas salir? Se perderán todos los datos");
		
		if(option == JOptionPane.YES_OPTION) {
			System.exit(0);
		}
	}
    
	// Agrega scroll
    private JScrollPane createViewScroll(JPanel panel) {
        JScrollPane scroll = new JScrollPane(panel);
        
        scroll.setBorder(null);
        scroll.setOpaque(false);
        scroll.getViewport().setOpaque(false);

        scroll.getVerticalScrollBar().setUnitIncrement(12);
        scroll.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);

        return scroll;
    }
    
    public RegistrationView getRegistrationView() {
		return registrationView;
	}
}