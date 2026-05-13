package repository;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;

import models.RoomType;

public class RoomTypeRepository {

	private final String FILE = "."
			+ File.separator 
			+ "data"
			+ File.separator
			+ "RoomTypes.json";
    
	private final ObjectMapper mapper = 
			new ObjectMapper().enable(SerializationFeature.INDENT_OUTPUT);

	public void save(RoomType roomType) throws IOException {
		List<RoomType> roomTypes = getRoomTypes();
		roomTypes.add(roomType);
		updateAll(roomTypes);
	}

    public List<RoomType> getRoomTypes() throws IOException {
		File file = new File(FILE);	
		file.getParentFile().mkdirs();
		
		if(!file.exists() || file.length() == 0) {
			return new ArrayList<>();
		}
		
		return mapper.readValue(
			file, 
			new TypeReference<List<RoomType>>() {}
		);
    }

    public void updateAll(List<RoomType> roomTypes) throws IOException {
		File file = new File(FILE);
		file.getParentFile().mkdir();
		
	    mapper.writeValue(file, roomTypes);
    }
    
    public void delete(int index) throws IOException {
        List<RoomType> roomTypes = getRoomTypes();
        roomTypes.remove(index);
        updateAll(roomTypes);
    }

    public void update(int index, RoomType updatedRoomType) throws IOException {
        List<RoomType> roomTypes = getRoomTypes();
        roomTypes.set(index, updatedRoomType);
        updateAll(roomTypes);
    }
    
    //Muestra solo las habitaciones destacadas (las que tienen el atributo featured como true, máximo 3)
    public List<RoomType> getFeaturedRoomTypes() throws IOException {
        
        List<RoomType> featuredRoomTypes = new ArrayList<>();

        for (RoomType roomType : getRoomTypes()) {
            if (roomType.isFeatured()) {
                featuredRoomTypes.add(roomType);
            }

            if (featuredRoomTypes.size() == 3) {
                break;
            }
        }

        return featuredRoomTypes;
    }
    
 // habitaciones disponibles para reservas
    public List<RoomType> getAvailableRoomTypes(
            int guests
    ) throws IOException {

        List<RoomType> availableRoomTypes =
                new ArrayList<>();

        for (RoomType roomType : getRoomTypes()) {

            if (roomType.getCapacity() >= guests) {
                availableRoomTypes.add(roomType);
            }
        }

        return availableRoomTypes;
    }
    
}