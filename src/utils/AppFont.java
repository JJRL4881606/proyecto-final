package utils;

import java.awt.Font;

public class AppFont {

	private static Font roboto;
	private static Font firasans;
	
	static {
		try {
            // Fuente base para texto
            roboto = Font.createFont(
                    Font.TRUETYPE_FONT,
                    AppFont.class.getResourceAsStream("/fonts/Roboto.ttf"));

            // Fuente para títulos
            firasans = Font.createFont(
                    Font.TRUETYPE_FONT,
                    AppFont.class.getResourceAsStream("/fonts/FiraSans-Black.ttf"));
			
		} catch(Exception e) {
            roboto = new Font("SansSerif", Font.PLAIN, 14);
            firasans = new Font("Serif", Font.BOLD, 30);
		}
	}
	
    public static Font big() {
        return roboto.deriveFont(Font.BOLD, 18f);
    }

    public static Font normal() {
        return roboto.deriveFont(Font.BOLD, 15f);
    }    
    
    public static Font paragraph() {
        return roboto.deriveFont(15f);
    }

    public static Font small() {
        return roboto.deriveFont(Font.BOLD, 13f);
    }
	
    public static Font title() {
        return firasans.deriveFont(Font.BOLD, 30f);
    }
	
	public static Font subtitle() {
		return roboto.deriveFont(Font.BOLD, 22f);
	}
	
}
