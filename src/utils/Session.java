package utils;

import models.User;

//Guarda la información del usuario que inició sesión
public class Session {

	// Usuario actual
	private static User currentUser;

	// Iniciar sesión
	public static void login(User user) {
		currentUser = user;
	}

	// obtener usuario actual
	public static User getCurrentUser() {
		return currentUser;
	}

	// Actualizar el usuario actual
	public static void setCurrentUser(User user) {
		currentUser = user;
	}

	// Cerrar sesión
	public static void logout() {
		currentUser = null;
	}

	// Verificar si hay una sesión activa
	public static boolean isLoggedIn() {
		return currentUser != null;
	}

	// Obtener el rol del usuario actual
	public static String getRole() {
		
		//si no hay sesion iniciada regresa cadena vacía
	    if(currentUser == null){
	        return "";
	    }

	    return currentUser.getRole();
	}
}