package repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import config.DatabaseConnection;
import models.User;
import utils.PasswordUtils;

public class LoginRepository {

	// Método para iniciar sesión utilizando el correo y la contraseña
	
    public User login(String email, String password) {
        String sql = "SELECT * FROM users WHERE email = ?";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, email);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
            	
            	// Verifica que la contraseña ingresada coincida con la almacenada
            	String hashedPassword = rs.getString("password");
                boolean correctPassword = PasswordUtils.checkPassword(password, hashedPassword);

                if (!correctPassword) return null;

                // Si las credenciales son correctas, crea el objeto User
                
                User user = new User();

                user.setId(rs.getInt("id"));
                user.setName(rs.getString("name"));
                user.setSurname(rs.getString("surname"));
                user.setEmail(rs.getString("email"));
                user.setPhone(rs.getString("phone"));
                user.setCountry(rs.getString("country"));
                user.setBirthDate(rs.getDate("birthDate"));
                user.setGender(rs.getString("gender").charAt(0));
                user.setRole(rs.getString("role"));

                user.setPassword(
                    rs.getString("password")
                );

                return user;
            }

        } catch (SQLException ex) {
            ex.printStackTrace();
        }

        return null;
    }
}