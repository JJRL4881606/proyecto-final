package views;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Image;
import java.awt.Toolkit;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

import components.GradientPanel;

@SuppressWarnings("serial")
public class MainPageWindow extends JFrame 
{
    public MainPageWindow() 
    {
        // Pantalla completa (maximizada)
        this.setExtendedState(JFrame.MAXIMIZED_BOTH);
        this.setTitle("Home | HOTEL MJ");
        this.setResizable(true);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        
        // Fondo degradado
        GradientPanel fondo = new GradientPanel(
            new Color(100,149,237), 
            new Color(25,25,112)
        );

        fondo.setLayout(new BorderLayout());
        setContentPane(fondo);    

        // Agregar ícono aplicación
        Toolkit tk = Toolkit.getDefaultToolkit();
        Image icono = tk.getImage("src/img/logoHotel.png");
        setIconImage(icono);
                
        // Agregar el panel con scroll
        add(crearVistaConScroll(new MainPageView()), BorderLayout.CENTER);
        this.setVisible(true);
    }
    
    private JScrollPane crearVistaConScroll(JPanel panel) {
        JScrollPane scroll = new JScrollPane(panel);
        scroll.setBorder(null);
        scroll.getVerticalScrollBar().setUnitIncrement(12);
        scroll.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        return scroll;
    }
}