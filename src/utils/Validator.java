package utils;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

import javax.swing.JPasswordField;
import javax.swing.JTextField;

public class Validator {

    public static boolean isValidName(String text) {
        return text.matches("[a-zA-ZáéíóúÁÉÍÓÚñÑ ]+");
    }

    public static boolean isValidEmail(String email) {
        return email.matches("^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$");
    }

    public static boolean isValidPhone(String phone) {
        return phone.matches("\\d{10}");
    }
    
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

    public static void onlyNumbers(JTextField field) {
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
    
    public static void noSpaces(JTextField field) {
        field.addKeyListener(new KeyAdapter() {
            public void keyTyped(KeyEvent e) {
                if (Character.isWhitespace(e.getKeyChar())) {
                    e.consume();
                }
            }
        });
    }    
    
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
