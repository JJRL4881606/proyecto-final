package repository;

import java.io.IOException;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import config.DatabaseConnection;
import models.RoomType;

public class RoomTypeRepository {

    public void save(RoomType roomType) {

        String sql = "INSERT INTO room_types "
                + "(name, bedType, capacity, price, imagePath, features, featured, description, extraImages) "
                + "VALUES(?, ?, ?, ?, ?, ?, ?, ?, ?)";

        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement pst = connection.prepareStatement(sql)) {

            pst.setString(1, roomType.getName());
            pst.setString(2, roomType.getBedType());
            pst.setInt(3, roomType.getCapacity());
            pst.setDouble(4, roomType.getPrice());
            pst.setString(5, roomType.getImagePath());
            pst.setString(6, roomType.featuresToString());
            pst.setBoolean(7, roomType.isFeatured());
            pst.setString(8, roomType.getDescription());
            pst.setString(9, roomType.extraImagesToString());

            pst.executeUpdate();

        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }
    
    public List<RoomType> getRoomTypes(){

        List<RoomType> roomTypes = new ArrayList<>();

        String sql = "SELECT * FROM room_types";

        try(
            Connection conn = DatabaseConnection.getConnection();
            PreparedStatement ps = conn.prepareStatement(sql);
            ResultSet rs = ps.executeQuery()
        ){
            while(rs.next()){
                roomTypes.add(new RoomType(
                    rs.getInt("typeId"),
                    rs.getString("name"),
                    rs.getString("bedType"),
                    rs.getInt("capacity"),
                    rs.getDouble("price"),
                    rs.getString("imagePath"),
                    RoomType.stringToFeatures(rs.getString("features")),
                    rs.getBoolean("featured"),
                    rs.getString("description"),
                    RoomType.stringToFeatures(rs.getString("extraImages"))
                ));
            }

        }catch(Exception e){
            e.printStackTrace();
        }

        return roomTypes;
    }

    public boolean delete(int id){

        String sql = "DELETE FROM room_types WHERE typeId=?";

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

    public boolean update(RoomType updatedRoomType) throws IOException {

    	String sql = "UPDATE room_types SET name = ?, bedType = ?, capacity = ?, "
    	        + "price = ?, imagePath = ?, features = ?, featured = ?, description = ?, extraImages = ? "
    	        + "WHERE typeId = ?";
    	
		try (Connection connection = DatabaseConnection.getConnection();
				PreparedStatement pst = connection.prepareStatement(sql)) {

			pst.setString(1,updatedRoomType.getName());
			pst.setString(2,updatedRoomType.getBedType());
			pst.setInt(3,updatedRoomType.getCapacity());
			pst.setDouble(4,updatedRoomType.getPrice());
			pst.setString(5,updatedRoomType.getImagePath());
			pst.setString(6,updatedRoomType.featuresToString());
			pst.setBoolean(7,updatedRoomType.isFeatured());
			pst.setString(8,updatedRoomType.getDescription());
			pst.setString(9,updatedRoomType.extraImagesToString());
			pst.setInt(10,updatedRoomType.getTypeId());
			
			int affectedRows = pst.executeUpdate();

			if (affectedRows > 0) {
				return true;
			}

		} catch (SQLException ex) {
			ex.printStackTrace();
		}

		return false;
    }

    public List<RoomType> getFeaturedRoomTypes(){

        List<RoomType> roomTypes = new ArrayList<>();

        String sql = "SELECT * FROM room_types " + "WHERE featured = ? " + "LIMIT 3";

        try(
            Connection conn = DatabaseConnection.getConnection();
            PreparedStatement ps = conn.prepareStatement(sql)
        ){
            ps.setBoolean(1,true);
            ResultSet rs = ps.executeQuery();

            while(rs.next()){
                roomTypes.add(new RoomType(
                        rs.getInt("typeId"),
                        rs.getString("name"),
                        rs.getString("bedType"),
                        rs.getInt("capacity"),
                        rs.getDouble("price"),
                        rs.getString("imagePath"),
                        RoomType.stringToFeatures(rs.getString("features")),
                        rs.getBoolean("featured"),
                        rs.getString("description"),
                        RoomType.stringToFeatures(rs.getString("extraImages"))
                ));
            }

        }catch(Exception e){
            e.printStackTrace();
        }

        return roomTypes;
    }

    public List<RoomType> getAvailableRoomTypes(int guests){

        List<RoomType> roomTypes = new ArrayList<>();

        String sql = "SELECT * FROM room_types " + "WHERE capacity >= ?";

        try(
            Connection conn = DatabaseConnection.getConnection();
            PreparedStatement ps = conn.prepareStatement(sql)
        ){
            ps.setInt(1, guests);
            ResultSet rs = ps.executeQuery();

            while(rs.next()){
                roomTypes.add(new RoomType(
                        rs.getInt("typeId"),
                        rs.getString("name"),
                        rs.getString("bedType"),
                        rs.getInt("capacity"),
                        rs.getDouble("price"),
                        rs.getString("imagePath"),
                        RoomType.stringToFeatures(rs.getString("features")),
                        rs.getBoolean("featured"),
                        rs.getString("description"),
                        RoomType.stringToFeatures(rs.getString("extraImages"))
                ));
            }

        }catch(Exception e){
            e.printStackTrace();
        }

        return roomTypes;
    }
}