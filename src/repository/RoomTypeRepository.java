package repository;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import config.DatabaseConnection;
import models.RoomType;

public class RoomTypeRepository {

    public void save(RoomType roomType){

        String sql =
        "INSERT INTO room_types(" +
        "name,bedType,capacity,price,imagePath,features,featured)" +
        "VALUES(?,?,?,?,?,?,?)";

        try(
            Connection conn = DatabaseConnection.getConnection();
            PreparedStatement ps = conn.prepareStatement(sql)){

            ps.setString(1, roomType.getName());
            ps.setString(2, roomType.getBedType());
            ps.setInt(3, roomType.getCapacity());
            ps.setDouble(4, roomType.getPrice());
            ps.setString(5, roomType.getImagePath());
            ps.setString(6, roomType.featuresToString());
            ps.setBoolean(7, roomType.isFeatured());

            ps.executeUpdate();

        }catch(Exception e){
            e.printStackTrace();
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
                    rs.getBoolean("featured")
                ));
            }

        }catch(Exception e){
            e.printStackTrace();
        }

        return roomTypes;
    }

    public void delete(int id){

        String sql = "DELETE FROM room_types WHERE typeId=?";

        try(
            Connection conn = DatabaseConnection.getConnection();
            PreparedStatement ps = conn.prepareStatement(sql)
        ){
            ps.setInt(1,id);
            ps.executeUpdate();
            
        }catch(Exception e){
            e.printStackTrace();
        }
    }

    public void update(RoomType roomType){

        String sql =
            "UPDATE room_types SET " +
            "name=?," +
            "bedType=?," +
            "capacity=?," +
            "price=?," +
            "imagePath=?," +
            "features=?," +
            "featured=? " +
            "WHERE typeId=?";
        try(
            Connection conn = DatabaseConnection.getConnection();
            PreparedStatement ps = conn.prepareStatement(sql)
        ){
            ps.setString(1,roomType.getName());
            ps.setString(2,roomType.getBedType());
            ps.setInt(3,roomType.getCapacity());
            ps.setDouble(4,roomType.getPrice());
            ps.setString(5,roomType.getImagePath());
            ps.setString(6,roomType.featuresToString());
            ps.setBoolean(7,roomType.isFeatured());
            ps.setInt(8,roomType.getTypeId());
            ps.executeUpdate();

        }catch(Exception e){
            e.printStackTrace();
        }
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
                    rs.getBoolean("featured")
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
                    rs.getBoolean("featured")
                ));
            }

        }catch(Exception e){
            e.printStackTrace();
        }

        return roomTypes;
    }
}