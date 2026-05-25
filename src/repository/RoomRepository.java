package repository;

import java.io.IOException;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import config.DatabaseConnection;
import models.Room;

public class RoomRepository {

    public void save(Room room){

        String sql = "INSERT INTO rooms "
                + "(roomNumber, floor, typeId, status) "
                + "VALUES (?, ?, ?, ?)";

        try(
            Connection connection = DatabaseConnection.getConnection();
            PreparedStatement pst = connection.prepareStatement(sql)
        ){
            pst.setInt(1, room.getRoomNumber());
            pst.setInt(2, room.getFloor());
            pst.setInt(3, room.getTypeId());
            pst.setString(4, room.getStatus());
            
            System.out.println(room.getStatus());

            pst.executeUpdate();

        }catch(SQLException ex){
            ex.printStackTrace();
        }
        
    }

    public List<Room> getRooms(){

        List<Room> rooms = new ArrayList<>();

        String sql = "SELECT * FROM rooms";

        try(
            Connection conn = DatabaseConnection.getConnection();
            PreparedStatement ps = conn.prepareStatement(sql);
            ResultSet rs = ps.executeQuery()
        ){
            while(rs.next()){

                rooms.add(
                    new Room(
                        rs.getInt("roomId"),
                        rs.getInt("roomNumber"),
                        rs.getInt("floor"),
                        rs.getInt("typeId"),
                        rs.getString("status")
                    )
                );

            }

        }catch(Exception e){
            e.printStackTrace();
        }

        return rooms;
    }

    public boolean delete(int id){

        String sql = "DELETE FROM rooms WHERE roomId = ?";

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

    public boolean update(Room updatedRoom) throws IOException{

    	String sql = "UPDATE rooms "
    		      + "SET roomNumber=?, floor=?, "
    		      + "typeId=?, status=? "
    		      + "WHERE roomId=?";
    	
		try (Connection connection = DatabaseConnection.getConnection();
				PreparedStatement pst = connection.prepareStatement(sql)) {

			pst.setInt(1,updatedRoom.getRoomNumber());
            pst.setInt(2,updatedRoom.getFloor());
            pst.setInt(3,updatedRoom.getTypeId());
            pst.setString(4,updatedRoom.getStatus());
            pst.setInt(5,updatedRoom.getRoomId());

            int affectedRows = pst.executeUpdate();

            if (affectedRows > 0) {
                return true;
            }

        } catch (SQLException ex) {
            ex.printStackTrace();
        }

        return false;
    }

    public Room findById(int id){

        String sql = "SELECT * FROM rooms WHERE roomId = ?";

        try(
            Connection conn = DatabaseConnection.getConnection();
            PreparedStatement ps = conn.prepareStatement(sql)
        ){
            ps.setInt(1,id);

            ResultSet rs = ps.executeQuery();

            if(rs.next()){

                return new Room(
                    rs.getInt("roomId"),
                    rs.getInt("roomNumber"),
                    rs.getInt("floor"),
                    rs.getInt("typeId"),
                    rs.getString("status")
                );
            }

        }catch(Exception e){
            e.printStackTrace();
        }

        return null;
    }

    public List<Room> findByTypeId(int typeId){

        List<Room> rooms = new ArrayList<>();

        String sql = "SELECT * FROM rooms WHERE typeId = ?";

        try(
            Connection conn = DatabaseConnection.getConnection();
            PreparedStatement ps = conn.prepareStatement(sql)
        ){
            ps.setInt(1,typeId);

            ResultSet rs = ps.executeQuery();

            while(rs.next()){

                rooms.add(
                    new Room(
                        rs.getInt("roomId"),
                        rs.getInt("roomNumber"),
                        rs.getInt("floor"),
                        rs.getInt("typeId"),
                        rs.getString("status")
                    )
                );
            }

        }catch(Exception e){
            e.printStackTrace();
        }

        return rooms;
    }
    
    public boolean existsRoomNumber(int roomNumber){

        String sql = "SELECT 1 FROM rooms WHERE roomNumber=? LIMIT 1";

        try(
            Connection conn = DatabaseConnection.getConnection();
            PreparedStatement ps = conn.prepareStatement(sql)
        ){

            ps.setInt(1, roomNumber);

            ResultSet rs = ps.executeQuery();

            return rs.next();

        }catch(Exception e){
            e.printStackTrace();
        }

        return false;
    }
    
    //Checar si hay habitaciones que sean de este tipo de habitación
    public boolean existsByTypeId(int typeId){

        String sql = "SELECT COUNT(*) FROM rooms WHERE typeId = ?";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, typeId);

            ResultSet rs = ps.executeQuery();

            if(rs.next()){
                return rs.getInt(1) > 0;
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return false;
    }
    
    public List<Room> getByType(int typeId){

        List<Room> rooms=new ArrayList<>();

        String sql="SELECT * FROM rooms WHERE typeId=?";

        try(
            Connection conn=DatabaseConnection.getConnection();
            PreparedStatement ps=conn.prepareStatement(sql)
        ){

            ps.setInt(1,typeId);

            ResultSet rs=ps.executeQuery();

            while(rs.next()){
                rooms.add(
                    new Room(
                        rs.getInt("roomId"),
                        rs.getInt("roomNumber"),
                        rs.getInt("floor"),
                        rs.getInt("typeId"),
                        rs.getString("status")
                    )
                );
            }

        }catch(Exception e){
            e.printStackTrace();
        }

        return rooms;
    }
}