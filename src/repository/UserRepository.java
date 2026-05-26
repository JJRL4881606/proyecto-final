package repository;

import java.io.IOException;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import models.User;
import utils.PasswordUtils;
import config.DatabaseConnection;
import exceptions.DuplicateEmailException;

public class UserRepository {

	public void save(User user) {

		String sql = "INSERT INTO users "
				+ "(name, surname, password, email, phone, country, birth_date, gender, role) "
				+ "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";

		try (Connection connection = DatabaseConnection.getConnection();
				PreparedStatement pst = connection.prepareStatement(sql)) {
			pst.setString(1, user.getName());
			pst.setString(2, user.getSurname());
			String hashedPassword = PasswordUtils.hashPassword(user.getPassword());
			pst.setString(3, hashedPassword);
			pst.setString(4, user.getEmail());
			pst.setString(5, user.getPhone());
			pst.setString(6, user.getCountry());
			pst.setDate(7, new Date(user.getBirthDate().getTime()));
			pst.setString(8, String.valueOf(user.getGender()));
			pst.setString(9, user.getRole());
			pst.executeUpdate();

		} catch (SQLException ex) {
			ex.printStackTrace();
		}
	}

	public List<User> getUsers() throws IOException {

		List<User> users = new ArrayList<User>();

		try (Connection connection = DatabaseConnection.getConnection();
				Statement st = connection.createStatement();
				ResultSet rs = st.executeQuery("SELECT * FROM users");) {

			while (rs.next()) {
				User user = new User(rs.getInt("id"), rs.getString("name"), rs.getString("surname"),
						rs.getString("email"), rs.getString("phone"), rs.getString("country"), rs.getDate("birth_date"),
						rs.getString("gender").charAt(0), rs.getString("role"));
				
				user.setPassword(
				    rs.getString("password")
				);
				
				users.add(user);
			}

		} catch (SQLException ex) {
			ex.printStackTrace();
		}

		return users;
	}

	public boolean delete(int id) {
		
		String sql = "DELETE FROM users WHERE id = ?";

		try (Connection connection = DatabaseConnection.getConnection();
				PreparedStatement pst = connection.prepareStatement(sql)) {

			pst.setInt(1, id);
			int affectedRows = pst.executeUpdate();
			if (affectedRows > 0) {
				System.out.println("Se eliminó");
				return true;
			}

		} catch (SQLException ex) {
			ex.printStackTrace();
		}

		return false;
	}

	public boolean update(User updatedUser) throws IOException {
		
		String sql = "UPDATE users SET name = ?, surname = ?, email = ?, phone = ?, country = ?,"
				+ " birth_date = ?, gender = ?, role = ? " + "WHERE id = ?";

		try (Connection connection = DatabaseConnection.getConnection();
				PreparedStatement pst = connection.prepareStatement(sql)) {

			pst.setString(1, updatedUser.getName());
			pst.setString(2, updatedUser.getSurname());
			pst.setString(3, updatedUser.getEmail());
			pst.setString(4, updatedUser.getPhone());
			pst.setString(5, updatedUser.getCountry());
			pst.setDate(6, new Date(updatedUser.getBirthDate().getTime()));
			pst.setString(7, String.valueOf(updatedUser.getGender()));
			pst.setString(8, updatedUser.getRole());
			pst.setInt(9, updatedUser.getId());

			int affectedRows = pst.executeUpdate();

			if (affectedRows > 0) {
				return true;
			}

		} catch (SQLException ex) {
			ex.printStackTrace();
		}

		return false;
	}

	public void validateDuplicateEmail(String email) throws IOException, DuplicateEmailException {

		List<User> users = getUsers();

		for (User u : users) {
			if (u.getEmail().equalsIgnoreCase(email)) {
				throw new DuplicateEmailException("Este correo es usado por otra cuenta");
			}
		}
	}
	
	public User findById(int id){

	    String sql = "SELECT * FROM users WHERE id=?";

	    try(
	        Connection connection = DatabaseConnection.getConnection();
	        PreparedStatement pst = connection.prepareStatement(sql)
	    ){

	        pst.setInt(1,id);

	        ResultSet rs = pst.executeQuery();

	        if(rs.next()){

	        	User user = new User(
        		    rs.getInt("id"),
        		    rs.getString("name"),
        		    rs.getString("surname"),
        		    rs.getString("email"),
        		    rs.getString("phone"),
        		    rs.getString("country"),
        		    rs.getDate("birth_date"),
        		    rs.getString("gender").charAt(0),
        		    rs.getString("role")
        		);

        		user.setPassword(
        		    rs.getString("password")
        		);

        		return user;
	        }

	    }catch(Exception e){
	        e.printStackTrace();
	    }

	    return null;
	}
	
	public void updatePassword(int id, String password){

	    String sql = "UPDATE users SET password=? WHERE id=?";

	    try(
	        Connection conn = DatabaseConnection.getConnection();
	        PreparedStatement stmt = conn.prepareStatement(sql)){

	        String hash = PasswordUtils.hashPassword(password);

	        stmt.setString(1, hash);
	        stmt.setInt(2, id);
	        stmt.executeUpdate();

	    }catch(SQLException e){
	        e.printStackTrace();
	    }
	}
	
	public User findByEmail(String email){

	    String sql = "SELECT * FROM users WHERE email=?";

	    try(Connection conn = DatabaseConnection.getConnection();
	        PreparedStatement stmt = conn.prepareStatement(sql)){

	        stmt.setString(1, email);

	        ResultSet rs = stmt.executeQuery();

	        if(rs.next()){

	            User user = new User();
	            user.setId(rs.getInt("id"));
	            user.setName(rs.getString("name"));
	            user.setSurname(rs.getString("surname"));
	            user.setEmail(rs.getString("email"));
	            user.setPhone(rs.getString("phone"));
	            user.setCountry(rs.getString("country"));
	            user.setBirthDate(rs.getDate("birth_date"));
	            user.setGender(rs.getString("gender").charAt(0));
	            user.setRole(rs.getString("role"));
	            user.setPassword(rs.getString("password"));

	            return user;
	        }

	    }catch(Exception e){
	        e.printStackTrace();
	    }

	    return null;
	}
}