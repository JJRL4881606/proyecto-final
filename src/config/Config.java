package config;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Properties;

public class Config {

	private static final String FILE = "app.properties";
	private static final Properties properties = new Properties();
	
	static {
		load();
	}
	
	public static void load() {
		try {
			File file = new File(FILE);
			
			if(file.exists()) {
				try (FileInputStream fis = new FileInputStream(file)) {
					properties.load(fis);
				}
			}
		} catch(IOException ex) {
			ex.printStackTrace();
		}
	}
	
	public static String get(String key) {
		return properties.getProperty(key);
	}
	
	public static String get(String key, String defaultValue) {
		return properties.getProperty(key, defaultValue);
	}
	
	public static void set(String key, String value) {
		properties.setProperty(key, value);
		save();
	}
	
	public static void save() {
		try (FileOutputStream fos = new FileOutputStream(FILE)) {
			properties.store(fos, "Configuracion");
		}catch(IOException ex) {
			ex.printStackTrace();
		}
	}
}