package repository;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import config.DatabaseConnection;
import models.Amenity;
import models.Room;
import models.RoomType;

public class RoomTypeRepository {

    private AmenityRepository amenityRepo = new AmenityRepository();
    private RoomImageRepository imageRepo = new RoomImageRepository();

    //Método para crear un tipo de habitación 
    
    public void save(RoomType roomType){

    	String sql = "INSERT INTO room_types(name, bedType, capacity, price, imagePath, featured, description) "
    			+ "VALUES(?, ?, ?, ?, ?, ?, ?)";

        try(
            Connection conn = DatabaseConnection.getConnection();
            PreparedStatement pst = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)
        ){
            pst.setString(1,roomType.getName());
            pst.setString(2,roomType.getBedType());
            pst.setInt(3,roomType.getCapacity());
            pst.setDouble(4,roomType.getPrice());
            pst.setString(5,roomType.getImagePath());
            pst.setBoolean(6,roomType.isFeatured());
            pst.setString(7,roomType.getDescription());

            pst.executeUpdate();

            ResultSet rs = pst.getGeneratedKeys();

            if(rs.next()){

                int typeId = rs.getInt(1);

                saveAmenities(
                    conn,
                    typeId,
                    roomType.getAmenities()
                );
                
                imageRepo.saveImages(
            	    typeId,
            	    roomType.getExtraImages()
            	);
            }

        } catch(Exception e){
            e.printStackTrace();
        }
    }
    
    // Método para guardar las amenidades de un roomType
    // Hay una tabla intermedia entre RoomType y Amenity

    private void saveAmenities(Connection conn, int typeId, List<Amenity> amenities)throws SQLException{
    	
        String sql = "INSERT INTO roomtype_amenities(typeId, amenityId) VALUES(?, ?)";

        for(Amenity a:amenities){

            PreparedStatement ps = conn.prepareStatement(sql);

            ps.setInt(1,typeId);
            ps.setInt(2,a.getAmenityId());

            ps.executeUpdate();
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
                        imageRepo.getImagesByTypeId(
                    	    rs.getInt("typeId")
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

        try(Connection conn = DatabaseConnection.getConnection()){

            conn.setAutoCommit(false);

            PreparedStatement deleteAmenities =
                conn.prepareStatement(
                    "DELETE FROM roomtype_amenities WHERE typeId = ?"
                );

            deleteAmenities.setInt(1, id);
            deleteAmenities.executeUpdate();

            imageRepo.deleteByTypeId(conn, id);

            PreparedStatement deleteType =
                conn.prepareStatement(
                    "DELETE FROM room_types WHERE typeId = ?"
                );

            deleteType.setInt(1, id);

            boolean deleted = deleteType.executeUpdate() > 0;

            conn.commit();

            return deleted;

        }catch(Exception e){
            e.printStackTrace();
        }

        return false;
    }    
    
    public boolean update(RoomType roomType){

        String sql = "UPDATE room_types " +
        "SET name = ?, bedType = ?, capacity = ?, price = ?, imagePath = ?, featured = ?, description = ? " +
        "WHERE typeId = ?";

        try(Connection conn = DatabaseConnection.getConnection()){

            conn.setAutoCommit(false);

            PreparedStatement ps = conn.prepareStatement(sql);

            ps.setString(1,roomType.getName());
            ps.setString(2,roomType.getBedType());
            ps.setInt(3,roomType.getCapacity());
            ps.setDouble(4,roomType.getPrice());
            ps.setString(5,roomType.getImagePath());
            ps.setBoolean(6,roomType.isFeatured());
            ps.setString(7,roomType.getDescription());
            ps.setInt(8, roomType.getTypeId());
            
            ps.executeUpdate();

            PreparedStatement delete =
                conn.prepareStatement(
                    "DELETE FROM roomtype_amenities WHERE typeId = ?"
                );

            delete.setInt(1, roomType.getTypeId());
            delete.executeUpdate();
            
            imageRepo.deleteByTypeId(
        	    conn,
        	    roomType.getTypeId()
        	);

            imageRepo.saveImages(
        	    conn,
        	    roomType.getTypeId(),
        	    roomType.getExtraImages()
        	);

            // insertar nuevas
            PreparedStatement insert = conn.prepareStatement(
                "INSERT INTO roomtype_amenities(typeId, amenityId) VALUES(?, ?)"
            );

            for(Amenity a : roomType.getAmenities()){
                insert.setInt(1, roomType.getTypeId());
                insert.setInt(2, a.getAmenityId());
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

    public List<RoomType> getAvailableRoomTypes(int guests, LocalDate checkIn, LocalDate checkOut){

        List<RoomType> available = new ArrayList<>();

        RoomRepository roomRepo = new RoomRepository();
        ReservationRepository reservationRepo = new ReservationRepository();

        for (RoomType type : getRoomTypes()) {

            if (type.getCapacity() < guests) continue;

            List<Room> rooms = roomRepo.findByTypeId(type.getTypeId());

            boolean found = false;

            for (Room room : rooms) {

                if (roomRepo.isRoomAvailable(room.getRoomId(), checkIn, checkOut)) {
                    found = true;
                    break;
                }
            }

            if (found) {
                available.add(type);
            }
        }
        
        return available;
    }
    
    public RoomType getById(int id){

        String sql = "SELECT * FROM room_types WHERE typeId = ?";

        try(
            Connection conn = DatabaseConnection.getConnection();
            PreparedStatement ps = conn.prepareStatement(sql)
        ){

            ps.setInt(1,id);

            ResultSet rs = ps.executeQuery();

            if(rs.next()){

                RoomType room = new RoomType(
                    rs.getInt("typeId"),
                    rs.getString("name"),
                    rs.getString("bedType"),
                    rs.getInt("capacity"),
                    rs.getDouble("price"),
                    rs.getString("imagePath"),
                    null,
                    rs.getBoolean("featured"),
                    rs.getString("description"),
                    imageRepo.getImagesByTypeId(
                	    rs.getInt("typeId")
                	)                
                );

                room.setAmenities(
                    amenityRepo.getAmenitiesByRoomType(
                        room.getTypeId()
                    )
                );

                return room;
            }

        }catch(Exception e){
            e.printStackTrace();
        }

        return new RoomType();
    }
    
    public List<RoomType> getVisibleRoomTypes(){

        RoomRepository roomRepo = new RoomRepository();

        return getRoomTypes()
            .stream()
            .filter(room ->
                roomRepo.hasActiveRoomsByType(
                    room.getTypeId()
                )
            )
            .toList();
    }
}