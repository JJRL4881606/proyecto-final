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
    
    // Método para guardar las amenidades asociadas a un tipo de habitación
    // Utiliza la tabla intermedia roomtype_amenities
    
    private void saveAmenities(Connection conn, int typeId, List<Amenity> amenities)throws SQLException{
    	
        String sql = "INSERT INTO roomtype_amenities(typeId, amenityId) VALUES(?, ?)";

        for(Amenity a:amenities){

            PreparedStatement ps = conn.prepareStatement(sql);

            ps.setInt(1,typeId);
            ps.setInt(2,a.getAmenityId());

            ps.executeUpdate();
        }
    }

    // Método para obtener todos los tipos de habitación
    
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

        // Cargar las amenidades correspondientes de cada tipo de habitación
        
        for(RoomType room:roomTypes){
            room.setAmenities(
                amenityRepo.getAmenitiesByRoomType(
                    room.getTypeId()
                )
            );
        }

        return roomTypes;
    }
    
    // Método para eliminar un tipo de habitación
    // También elimina sus amenidades e imágenes asociadas
    
    public boolean delete(int id){

        try(Connection conn = DatabaseConnection.getConnection()){

            conn.setAutoCommit(false);

            // Eliminar relaciones con las amenidades
            
            PreparedStatement deleteAmenities =
                conn.prepareStatement(
                    "DELETE FROM roomtype_amenities WHERE typeId = ?"
                );

            deleteAmenities.setInt(1, id);
            deleteAmenities.executeUpdate();

            // Eliminar imágenes asociadas al tipo de habitación
            
            imageRepo.deleteByTypeId(conn, id);

            // Eliminar el tipo de habitación
            
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
    
    // Método para actualizar un tipo de habitación
    // Incluye sus amenidades e imágenes asociadas
    
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

            // Eliminar las amenidades actuales
            
            PreparedStatement delete =
                conn.prepareStatement(
                    "DELETE FROM roomtype_amenities WHERE typeId = ?"
                );

            delete.setInt(1, roomType.getTypeId());
            delete.executeUpdate();
            
            // Reemplazar las imágenes existentes
            
            imageRepo.deleteByTypeId(
        	    conn,
        	    roomType.getTypeId()
        	);

            imageRepo.saveImages(
        	    conn,
        	    roomType.getTypeId(),
        	    roomType.getExtraImages()
        	);

            // Guardar/insertar las nuevas amenidades seleccionadas
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
    
    // Método para obtener los tipos de habitación destacados
    // Se muestran en la página principal
    
    public List<RoomType> getFeaturedRoomTypes(){
        return getRoomTypes()
            .stream()
            .filter(RoomType::isFeatured)
            .limit(3)
            .toList();
    }
    
    // Método para obtener los tipos de habitación disponibles
    // según la cantidad de huéspedes y las fechas seleccionadas

    public List<RoomType> getAvailableRoomTypes(int guests, LocalDate checkIn, LocalDate checkOut){

        List<RoomType> available = new ArrayList<>();

        RoomRepository roomRepo = new RoomRepository();

        for (RoomType type : getRoomTypes()) {

        	// Ignorar tipos de habitación que no tienen capacidad suficiente
        	
            if (type.getCapacity() < guests) continue;

            List<Room> rooms = roomRepo.findByTypeId(type.getTypeId());

            boolean found = false;

            // Buscar al menos una habitación disponible de este tipo
            
            for (Room room : rooms) {

                if (roomRepo.isRoomAvailable(room.getRoomId(), checkIn, checkOut)) {
                    found = true;
                    break;
                }
            }

            // Si existe una habitación disponible, agregar el tipo a la lista
            
            if (found) {
                available.add(type);
            }
        }
        
        return available;
    }
    
    // Método para obtener un tipo de habitación utilizando su id
    
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

                // Cargar las amenidades asociadas
                
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
    
    // Método para obtener los tipos de habitación visibles
    // Solo muestra los roomtypes que tienen habitaciones activas
    
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