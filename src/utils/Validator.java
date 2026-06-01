package utils;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

import javax.swing.JPasswordField;
import javax.swing.JTextField;

//validaciones de caracteres permitidos en campos

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
    
    //impedir caracteres que no sean letras
    public static void onlyLetters(JTextField field) {
        field.addKeyListener(new KeyAdapter() {
            public void keyTyped(KeyEvent e) {
                char c = e.getKeyChar();

                if (!Character.isLetter(c) && c != ' ') {
                    e.consume();
                }
            }
        });
    }

    //validar que sean 10 numeros en campos de telefono
    public static void onlyPhoneNumbers(JTextField field) {
        field.addKeyListener(new KeyAdapter() {
            public void keyTyped(KeyEvent e) {

                if (!Character.isDigit(e.getKeyChar())) {
                    e.consume();
                }

                if (field.getText().length() >= 10) {
                    e.consume();
                }
            }
        });
    }
    
    //impedir caracteres que no sean numeros
    public static void onlyNumbers(JTextField field){
    	field.addKeyListener(new KeyAdapter(){

    		public void keyTyped(KeyEvent e){

    			if(!Character.isDigit(e.getKeyChar())){
    				e.consume();
    			}
    		}
    	});
    }
    
    //impedir caracteres que no sean numeros, si permite decimales
    public static void onlyDecimalNumbers(JTextField field) {
        field.addKeyListener(new KeyAdapter() {

            @Override
            public void keyTyped(KeyEvent e) {

                char c=e.getKeyChar();

                if(!Character.isDigit(c) && c!='.'){
                    e.consume();
                }

                if(c=='.' && field.getText().contains(".")){
                    e.consume();
                }
            }
        });
    }
    
    //impedir ingresar espacios
    public static void noSpaces(JTextField field) {
        field.addKeyListener(new KeyAdapter() {
            public void keyTyped(KeyEvent e) {
                if (Character.isWhitespace(e.getKeyChar())) {
                    e.consume();
                }
            }
        });
    }    
    
    //impedir caracteres no permitidos en campos de contraseña
    public static void restrictedPassword(JPasswordField field) {
        field.addKeyListener(new KeyAdapter() {
            @Override
            public void keyTyped(KeyEvent e) {
                char c = e.getKeyChar();
                String forbidden = "<>\"'; }{¿?¡!|´°¬~^`,¨[]";
                if (forbidden.indexOf(c) != -1) {
                    e.consume();
                }
            }
        });
    }
    
}
