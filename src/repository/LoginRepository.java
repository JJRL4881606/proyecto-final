package repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import config.DatabaseConnection;
import models.User;

public class LoginRepository {

	public User login(String email, String password) {
				
		String sql = "SELECT id, email, password FROM users WHERE email = ? AND password = ?";
		
		try (
			Connection conn = DatabaseConnection.getConnection();
			PreparedStatement stmt = conn.prepareStatement(sql);
		){
			
			stmt.setString(1, email);
			stmt.setString(2, password);
			ResultSet rs = stmt.executeQuery();
			
			if(rs.next()) {
				User user = new User();
				user.setId(rs.getInt("id"));
				user.setEmail(rs.getString("email"));
				
				return user;
			}
			
		}catch(SQLException ex) {
			ex.printStackTrace();
		}
		
		return null;
	}	
}