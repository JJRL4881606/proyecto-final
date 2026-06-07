package utils;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

import javax.swing.JPasswordField;
import javax.swing.JTextField;

// para validar datos y restringir caracteres en formularios

public class Validator {

    //impedir caracteres no permitidos en campos de nombre
    public static boolean isValidName(String text) {
        return text.matches("[a-zA-ZáéíóúÁÉÍÓÚñÑ ]+");
    }
    
    //impedir caracteres no permitidos en campos de email
    public static boolean isValidEmail(String email) {
        return email.matches("^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$");
    }

    //validar que campos de telefono tengan 10 numeros
    public static boolean isValidPhone(String phone) {
        return phone.matches("\\d{10}");
    }
    
    //permitir solo letras y espacios
    public static void onlyLetters(JTextField field) {
        field.addKeyListener(new KeyAdapter() {
            public void keyTyped(KeyEvent e) {
            	
                char c = e.getKeyChar();

                // Bloquear cualquier caracter que no sea letra o espacio
                if (!Character.isLetter(c) && c != ' ') {
                    e.consume();
                }
            }
        });
    }

    //permitir solo numeros para telefonos
    public static void onlyPhoneNumbers(JTextField field) {
        field.addKeyListener(new KeyAdapter() {
            public void keyTyped(KeyEvent e) {

            	// Bloquear caracteres que no sean números
                if (!Character.isDigit(e.getKeyChar())) {
                    e.consume();
                }

                // Limitar el telefono a 10 dígitos
                if (field.getText().length() >= 10) {
                    e.consume();
                }
            }
        });
    }
    
    //permitir solo numeros
    public static void onlyNumbers(JTextField field){
    	field.addKeyListener(new KeyAdapter(){

    		public void keyTyped(KeyEvent e){

    			// Bloquear cualquier carácter que no sea numero
    			if(!Character.isDigit(e.getKeyChar())){
    				e.consume();
    			}
    		}
    	});
    }
    
    // permitir números decimales
    public static void onlyDecimalNumbers(JTextField field) {
        field.addKeyListener(new KeyAdapter() {

            @Override
            public void keyTyped(KeyEvent e) {

                char c=e.getKeyChar();

                // Solo se permiten números y un punto decimal
                if(!Character.isDigit(c) && c!='.'){
                    e.consume();
                }

                // Evitar que hayan varios puntos decimales
                if(c=='.' && field.getText().contains(".")){
                    e.consume();
                }
            }
        });
    }
    
    // bloquaer espacios en blanco
    public static void noSpaces(JTextField field) {
        field.addKeyListener(new KeyAdapter() {
        	
            public void keyTyped(KeyEvent e) {
            	
            	// Evitar espacios al escribir
                if (Character.isWhitespace(e.getKeyChar())) {
                    e.consume();
                }
            }
        });
    }    
    
    // bloquear caracteres no permitidos en campos de contraseña
    public static void restrictedPassword(JPasswordField field) {
        field.addKeyListener(new KeyAdapter() {
            @Override
            public void keyTyped(KeyEvent e) {
                char c = e.getKeyChar();
                
                // lista de caracteres restringidos
                String forbidden = "<>\"'; }{¿?¡!|´°¬~^`,¨[]";

                // Cancelar la entrada si el caracter está restringido
                if (forbidden.indexOf(c) != -1) {
                    e.consume();
                }
            }
        });
    }
    
}
