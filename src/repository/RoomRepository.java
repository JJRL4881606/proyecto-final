package repository;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import models.User;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;

import models.Room;

public class RoomRepository {

    private final String FILE = "src/assets/files/rooms.json";
    
	private final ObjectMapper mapper = 
			new ObjectMapper().enable(SerializationFeature.INDENT_OUTPUT);

	public void save(Room room) throws IOException {
		List<Room> rooms = getRooms();
		rooms.add(room);
		updateAll(rooms);
	}

    public List<Room> getRooms() throws IOException {

		File file = new File(FILE);	
		
		if(!file.exists() || file.length() == 0) {
			return new ArrayList<>();
		}
		
		return mapper.readValue(
			file, 
			new TypeReference<List<Room>>() {}
		);
    }

	public void updateAll(List<Room> rooms) throws IOException {
	    mapper.writeValue(new File(FILE), rooms);
	}

    public void delete(int index) throws IOException {
        List<Room> rooms = getRooms();
        rooms.remove(index);
        updateAll(rooms);
    }

    public void update(int index, Room updatedRoom) throws IOException {
        List<Room> rooms = getRooms();
        rooms.set(index, updatedRoom);
        updateAll(rooms);
    }
    
    //Muestra solo las habitaciones destacadas (las que tienen el atributo featured como true, máximo 3)
    public List<Room> getFeaturedRooms() throws IOException {
        
        List<Room> featuredRooms = new ArrayList<>();

        for (Room room : getRooms()) {
            if (room.isFeatured()) {
                featuredRooms.add(room);
            }

            if (featuredRooms.size() == 3) {
                break;
            }
        }

        return featuredRooms;
    }
}