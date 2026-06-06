package repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import config.DatabaseConnection;
import models.User;
import utils.PasswordUtils;

public class LoginRepository {

	// Método para iniciar sesión, utiliza el email y la contraseña del usuario
	
    public User login(String email, String password) {
        String sql = "SELECT * FROM users WHERE email = ?";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, email);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
            	
            	//Validar contraseña correcta, compara con la contraseña hasheada
                String hashedPassword = rs.getString("password");
                boolean correctPassword = PasswordUtils.checkPassword(password, hashedPassword);

                if (!correctPassword) return null;

                //Si los datos son correctos, inicia sesión
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