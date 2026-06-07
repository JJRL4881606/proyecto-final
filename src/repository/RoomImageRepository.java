package repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import config.DatabaseConnection;
import models.RoomImage;

public class RoomImageRepository {

	// Método para guardar las imágenes extra de un tipo de habitación
	
    public void saveImages(int typeId, List<RoomImage> images) {

        String sql = "INSERT INTO room_images(typeId, imagePath) VALUES(?, ?)";

        try(
            Connection conn = DatabaseConnection.getConnection();
            PreparedStatement ps = conn.prepareStatement(sql)
        ){

            for(RoomImage img : images){

                ps.setInt(1, typeId);
                ps.setString(2, img.getImagePath());

                ps.addBatch();
            }

            ps.executeBatch();

        }catch(Exception e){
            e.printStackTrace();
        }
    }

	// Método para obtener las imágenes extra de un tipo de habitación
	// Utiliza el id del RoomType
    
    public List<RoomImage> getImagesByTypeId(int typeId){

        List<RoomImage> images = new ArrayList<>();

        String sql = "SELECT * FROM room_images WHERE typeId = ?";

        try(
            Connection conn = DatabaseConnection.getConnection();
            PreparedStatement ps = conn.prepareStatement(sql)
        ){

            ps.setInt(1, typeId);

            ResultSet rs = ps.executeQuery();

            while(rs.next()){

                images.add(
                    new RoomImage(
                        rs.getInt("imageId"),
                        rs.getInt("typeId"),
                        rs.getString("imagePath")
                    )
                );
            }

        }catch(Exception e){
            e.printStackTrace();
        }

        return images;
    }
    
    // Elimina todas las imágenes asociadas a un tipo de habitación

    public void deleteByTypeId(Connection conn, int typeId)throws Exception{

        String sql = "DELETE FROM room_images WHERE typeId = ?";

        PreparedStatement ps = conn.prepareStatement(sql);

        ps.setInt(1, typeId);

        ps.executeUpdate();
    }
    
    // Guarda las imágenes de un tipo de habitación usando una conexión existente
    
    public void saveImages(Connection conn, int typeId, List<RoomImage> images)throws Exception{

    	    String sql = "INSERT INTO room_images(typeId, imagePath) VALUES(?, ?)";

    	    PreparedStatement ps = conn.prepareStatement(sql);

    	    for(RoomImage img : images){

    	        ps.setInt(1, typeId);
    	        ps.setString(2, img.getImagePath());

    	        ps.addBatch();
    	    }

    	    ps.executeBatch();
    	}
}