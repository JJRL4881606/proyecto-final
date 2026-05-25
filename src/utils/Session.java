package utils;

import models.User;

public class Session {

	private static User currentUser;

	public static void login(User user) {
		currentUser = user;
	}

	public static User getCurrentUser() {
		return currentUser;
	}

	public static void setCurrentUser(User user) {
		currentUser = user;
	}

	public static void logout() {
		currentUser = null;
	}

	public static boolean isLoggedIn() {
		return currentUser != null;
	}

	public static String getRole() {
	    if(currentUser == null){
	        return "";
	    }

	    return currentUser.getRole();
	}
}