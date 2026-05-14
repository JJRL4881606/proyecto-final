package utils;

import org.mindrot.jbcrypt.BCrypt;

public class PasswordUtils {
	
    // Hashea una contraseña
    public static String hashPassword(String password) {
        return BCrypt.hashpw(password, BCrypt.gensalt());
    }
    
    // Verifica una contraseña con el hash almacenado
    public static boolean checkPassword(String password, String hash) {
        return BCrypt.checkpw(password, hash);
    }
}