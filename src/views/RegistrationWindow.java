package views;

import java.awt.BorderLayout;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

@SuppressWarnings("serial")
public class RegistrationWindow extends JFrame 
{
	private RegistrationView RegistrationView;
	
    public RegistrationWindow() 
    {
        this.setExtendedState(JFrame.MAXIMIZED_BOTH);
        this.setTitle("Registro | ATLANTIS THE PALM, DUBAI");
        this.setResizable(true);
        this.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        
        JPanel background = new JPanel();
        background.setLayout(new BorderLayout());
        setContentPane(background);    

        //Agregar icono
        Image icon = Toolkit.getDefaultToolkit().getImage(
    	    getClass().getResource("/img/icons/registration-icon.png")
    	);
        setIconImage(icon);
                
        //Agregar el panel
        add(createViewScroll(RegistrationView = new RegistrationView(this)), BorderLayout.CENTER);        
        
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
		}
	}
    
    private JScrollPane createViewScroll(JPanel panel) {
        JScrollPane scroll = new JScrollPane(panel);
        scroll.setBorder(null);
        scroll.getVerticalScrollBar().setUnitIncrement(12);
        scroll.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        return scroll;
    }
    
    public RegistrationView getRegistrationView() {
		return RegistrationView;
	}
}