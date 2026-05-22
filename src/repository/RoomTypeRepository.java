package repository;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import config.DatabaseConnection;
import models.Amenity;
import models.RoomType;

public class RoomTypeRepository {

    private AmenityRepository amenityRepo=new AmenityRepository();

    public void save(RoomType roomType){

        String sql = "INSERT INTO room_types(name, bedType, capacity, price, imagePath, featured, description, extraImages) "
        + "VALUES(?,?,?,?,?,?,?,?)";

        try(
            Connection conn=DatabaseConnection.getConnection();
            PreparedStatement pst=conn.prepareStatement(
                sql,
                Statement.RETURN_GENERATED_KEYS
            )
        ){
            pst.setString(1,roomType.getName());
            pst.setString(2,roomType.getBedType());
            pst.setInt(3,roomType.getCapacity());
            pst.setDouble(4,roomType.getPrice());
            pst.setString(5,roomType.getImagePath());
            pst.setBoolean(6,roomType.isFeatured());
            pst.setString(7,roomType.getDescription());
            pst.setString(8,roomType.extraImagesToString());

            pst.executeUpdate();

            ResultSet rs=pst.getGeneratedKeys();

            if(rs.next()){

                int typeId=rs.getInt(1);

                saveAmenities(
                    conn,
                    typeId,
                    roomType.getAmenities()
                );
            }

        } catch(Exception e){
            e.printStackTrace();
        }
    }

    private void saveAmenities(Connection conn, int typeId, List<Amenity> amenities)throws SQLException{
    	
        String sql = "INSERT INTO roomtype_amenities(typeId,amenityId) VALUES(?,?)";

        for(Amenity a:amenities){

            PreparedStatement ps=
            conn.prepareStatement(sql);

            ps.setInt(1,typeId);
            ps.setInt(2,a.getAmenityId());

            ps.executeUpdate();
        }
    }

    public List<RoomType> getRoomTypes(){

        List<RoomType> roomTypes=new ArrayList<>();

        String sql="SELECT * FROM room_types";

        try(
            Connection conn=DatabaseConnection.getConnection();
            PreparedStatement ps=conn.prepareStatement(sql);
            ResultSet rs=ps.executeQuery()
        ){

            while(rs.next()){

                roomTypes.add(
                    new RoomType(
                        rs.getInt("typeId"),
                        rs.getString("name"),
                        rs.getString("bedType"),
                        rs.getInt("capacity"),
                        rs.getDouble("price"),
                        rs.getString("imagePath"),
                        null,
                        rs.getBoolean("featured"),
                        rs.getString("description"),
                        RoomType.stringToImages(
                            rs.getString("extraImages")
                        )
                    )
                );
            }

        }catch(Exception e){
            e.printStackTrace();
        }

        for(RoomType room:roomTypes){

            room.setAmenities(
                amenityRepo.getAmenitiesByRoomType(
                    room.getTypeId()
                )
            );
        }

        return roomTypes;
    }
    
    public boolean delete(int id){

        String sql = "DELETE FROM room_types WHERE typeId = ?";

        try(Connection conn = DatabaseConnection.getConnection();
            PreparedStatement pst = conn.prepareStatement(sql)){
	            pst.setInt(1,id);
	            return pst.executeUpdate()>0;

        }catch(Exception e){
            e.printStackTrace();
        }

        return false;
    }

    public boolean update(RoomType roomType){

        String sql = "UPDATE room_types " +
        "SET name=?, bedType=?, capacity=?, price=?, imagePath=?, featured=?, description=?, extraImages=? " +
        "WHERE typeId=?";

        try(Connection conn=DatabaseConnection.getConnection()){

            conn.setAutoCommit(false);

            PreparedStatement ps = conn.prepareStatement(sql);

            ps.setString(1,roomType.getName());
            ps.setString(2,roomType.getBedType());
            ps.setInt(3,roomType.getCapacity());
            ps.setDouble(4,roomType.getPrice());
            ps.setString(5,roomType.getImagePath());
            ps.setBoolean(6,roomType.isFeatured());
            ps.setString(7,roomType.getDescription());
            ps.setString(8,roomType.extraImagesToString());
            ps.setInt(9,roomType.getTypeId());

            ps.executeUpdate();

            PreparedStatement delete =
                conn.prepareStatement(
                    "DELETE FROM roomtype_amenities WHERE typeId=?"
                );

            delete.setInt(
                1,
                roomType.getTypeId()
            );

            delete.executeUpdate();

            // insertar nuevas
            PreparedStatement insert =
                conn.prepareStatement(
                    "INSERT INTO roomtype_amenities(typeId,amenityId) VALUES(?,?)"
                );

            for(Amenity a : roomType.getAmenities()){

                insert.setInt(
                    1,
                    roomType.getTypeId()
                );

                insert.setInt(
                    2,
                    a.getAmenityId()
                );

                insert.addBatch();
            }

            insert.executeBatch();

            conn.commit();

            return true;

        }catch(Exception e){
            e.printStackTrace();
        }

        return false;
    }
    public List<RoomType> getFeaturedRoomTypes(){
        return getRoomTypes()
            .stream()
            .filter(RoomType::isFeatured)
            .limit(3)
            .toList();
    }

    public List<RoomType> getAvailableRoomTypes(int guests){
        return getRoomTypes()
            .stream()
            .filter(
                r->r.getCapacity()>=guests
            )
            .toList();
    }
}