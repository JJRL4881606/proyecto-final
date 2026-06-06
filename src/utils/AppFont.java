package utils;

import java.awt.Font;

public class AppFont {

	private static Font roboto;
	private static Font nexa;
	
	static {
		try {
            // Fuente base para texto
            roboto = Font.createFont(
                    Font.TRUETYPE_FONT,
                    AppFont.class.getResourceAsStream("/assets/fonts/Roboto.ttf"));

            // Fuente para títulos
            nexa = Font.createFont(
                    Font.TRUETYPE_FONT,
                    AppFont.class.getResourceAsStream("/assets/fonts/Nexa.ttf"));
			
		} catch(Exception e) {
            roboto = new Font("SansSerif", Font.PLAIN, 14);
            nexa = new Font("Serif", Font.BOLD, 30);
		}
	}
	
    public static Font title() {
        return nexa.deriveFont(Font.BOLD, 34f);
    }
    
	public static Font subtitle2() {
		return roboto.deriveFont(Font.BOLD, 28f);
	}
	
	public static Font subtitle() {
		return roboto.deriveFont(Font.BOLD, 22f);
	}
	
    public static Font big() {
        return roboto.deriveFont(Font.BOLD, 18f);
    }

    public static Font normal() {
        return roboto.deriveFont(Font.BOLD, 15f);
    }    
    
    public static Font small() {
        return roboto.deriveFont(Font.BOLD, 13f);
    }
}
