package repository;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import config.DatabaseConnection;
import models.Amenity;

public class AmenityRepository {

    public void save(Amenity amenity) {

        String sql = "INSERT INTO amenities(name,icon) VALUES(?,?)";

        try(
            Connection conn = DatabaseConnection.getConnection();
            PreparedStatement ps = conn.prepareStatement(sql)
        ){

            ps.setString(1, amenity.getName());
            ps.setString(2, amenity.getIcon());

            ps.executeUpdate();

        }catch(Exception e){
            e.printStackTrace();
        }
    }

    public List<Amenity> getAmenities() {

        List<Amenity> amenities = new ArrayList<>();
        String sql = "SELECT * FROM amenities";

        try(
            Connection conn = DatabaseConnection.getConnection();
            PreparedStatement ps = conn.prepareStatement(sql);
            ResultSet rs = ps.executeQuery()
        ){

            while(rs.next()){
                amenities.add(new Amenity(
                    rs.getInt("amenityId"),
                    rs.getString("name"),
                    rs.getString("icon")
                ));
            }

        }catch(Exception e){
            e.printStackTrace();
        }

        return amenities;
    }

    public boolean delete(int id) {

        String sql = "DELETE FROM amenities WHERE amenityId=?";

        try(
            Connection conn = DatabaseConnection.getConnection();
            PreparedStatement ps = conn.prepareStatement(sql)
        ){

            ps.setInt(1,id);

            return ps.executeUpdate() > 0;

        }catch(Exception e){
            e.printStackTrace();
        }

        return false;
    }

    public List<Amenity> getAmenitiesByRoomType(int typeId) {

        List<Amenity> amenities = new ArrayList<>();

        String sql =
            "SELECT a.* FROM amenities a " +
            "INNER JOIN roomtype_amenities rta " +
            "ON a.amenityId=rta.amenityId " +
            "WHERE rta.typeId=?";

        try(
            Connection conn = DatabaseConnection.getConnection();
            PreparedStatement ps = conn.prepareStatement(sql);
        ){

            ps.setInt(1, typeId);

            ResultSet rs = ps.executeQuery();

            while(rs.next()){
                amenities.add(new Amenity(
                    rs.getInt("amenityId"),
                    rs.getString("name"),
                    rs.getString("icon")
                ));
            }

        }catch(Exception e){
            e.printStackTrace();
        }

        return amenities;
    }

    public boolean update(Amenity amenity) {

        String sql = "UPDATE amenities SET name=?,icon=? WHERE amenityId=?";

        try(
            Connection conn = DatabaseConnection.getConnection();
            PreparedStatement ps = conn.prepareStatement(sql)
        ){

            ps.setString(1, amenity.getName());
            ps.setString(2, amenity.getIcon());
            ps.setInt(3, amenity.getAmenityId());

            return ps.executeUpdate() > 0;

        }catch(Exception e){
            e.printStackTrace();
        }

        return false;
    }
    
    public boolean isUsed(int amenityId){

        String sql=
            "SELECT 1 FROM roomtype_amenities " +
            "WHERE amenityId=? LIMIT 1";

        try(
            Connection conn=
                DatabaseConnection.getConnection();

            PreparedStatement ps=
                conn.prepareStatement(sql)
        ){

            ps.setInt(1,amenityId);

            ResultSet rs=
                ps.executeQuery();

            return rs.next();

        }catch(Exception e){
            e.printStackTrace();
        }

        return false;
    }
}