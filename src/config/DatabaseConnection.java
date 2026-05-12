package config;

import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.util.Properties;

public class DatabaseConnection {

	private static Connection connection;
	
	public static Connection getConnection() {
		
		try {
			
			if(connection == null || connection.isClosed()) {
				Properties props = new Properties();
				
				InputStream input = DatabaseConnection.class.getClassLoader().getResourceAsStream("config/database.properties");
				props.load(input);
				
				String url = props.getProperty("db.url");
				String user = props.getProperty("db.user");
				String password = props.getProperty("db.password");
				String driver = props.getProperty("db.driver");
								
				Class.forName(driver);
				
				connection = DriverManager.getConnection(url, user, password);
			}
			
		} catch(Exception e) {
			e.printStackTrace();
		}
		
		return connection;
	}
}