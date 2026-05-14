package repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import config.DatabaseConnection;
import models.User;
import utils.PasswordUtils;

public class LoginRepository {

	public User login(String email, String password) {
				
		String sql = "SELECT id, email, password, role, name FROM users WHERE email = ?";
		
		try (
			Connection conn = DatabaseConnection.getConnection();
			PreparedStatement stmt = conn.prepareStatement(sql);
		){
			stmt.setString(1, email);
			ResultSet rs = stmt.executeQuery();
			
			if(rs.next()) {
				
				String hashedPassword = rs.getString("password");
				System.out.println(hashedPassword);
				
				boolean correctPassword = PasswordUtils.checkPassword(password, hashedPassword);
				
				if(!correctPassword) 
					return null;
				
				User user = new User();
				user.setId(rs.getInt("id"));
				user.setEmail(rs.getString("email"));
				user.setName(rs.getString("name"));
				user.setRole(rs.getString("role"));

				return user;
			}
			
		}catch(SQLException ex) {
			ex.printStackTrace();
		}
		
		return null;
	}	
}