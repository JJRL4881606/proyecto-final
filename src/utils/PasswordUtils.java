package utils;

import org.mindrot.jbcrypt.BCrypt;

// para encriptar y verificar contraseñas
public class PasswordUtils {
	
    // Hashear una contraseña
    public static String hashPassword(String password) {
        return BCrypt.hashpw(password, BCrypt.gensalt());
    }
    
    // Compara una contraseña con el hash guardado
    public static boolean checkPassword(String password, String hash) {
        return BCrypt.checkpw(password, hash);
    }
}