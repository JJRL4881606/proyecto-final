package main;

import javax.swing.JFrame;

public class Ventana extends JFrame{
	
	public Ventana() {
		setSize(500,500); //Establece el tamaño
		//Termina la ejecución del programa al cerrar la ventana.
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		//Establece el lugar donde aparecerá la ventana
		setLocation(100,100); 
		//Establece la ubicación y el tamaño de la ventana 
		//setBounds(100,100,500,500);
		//Establece si la ventana puede redimensionarse
		setResizable(false);
		//Cambia el título de la ventana
		setTitle("Mi Aplicación 2.0");
		//Coloca la ventana al centro de la pantalla
		setLocationRelativeTo(null);
		
		setVisible(true); //Establece visibilidad
	}
	
}









