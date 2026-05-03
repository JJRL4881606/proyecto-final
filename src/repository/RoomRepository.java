package repository;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;

import models.Room;

public class RoomRepository {

    private final String FILE = "src/assets/files/Rooms.json";
    
    private final ObjectMapper mapper =
            new ObjectMapper().enable(SerializationFeature.INDENT_OUTPUT);

    public void save(Room room) throws IOException {
        List<Room> rooms = getRooms();
        rooms.add(room);
        updateAll(rooms);
    }

    public List<Room> getRooms() throws IOException {
        File file = new File(FILE);

        if (!file.exists() || file.length() == 0) {
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

    // Buscar por ID
    public Room findById(String id) throws IOException {
        for (Room room : getRooms()) {
            if (room.getId().equals(id)) {
                return room;
            }
        }
        return null;
    }

    // Eliminar por ID
    public void deleteById(String id) throws IOException {
        List<Room> rooms = getRooms();
        rooms.removeIf(room -> room.getId().equals(id));
        updateAll(rooms);
    }

    // Actualizar por ID
    public void updateById(String id, Room updatedRoom) throws IOException {
        List<Room> rooms = getRooms();

        for (int i = 0; i < rooms.size(); i++) {
            if (rooms.get(i).getId().equals(id)) {
                rooms.set(i, updatedRoom);
                break;
            }
        }

        updateAll(rooms);
    }

    // Buscar habitaciones por tipo
    public List<Room> findByTypeId(String typeId) throws IOException {
        List<Room> result = new ArrayList<>();

        for (Room room : getRooms()) {
            if (room.getTypeId().equals(typeId)) {
                result.add(room);
            }
        }

        return result;
    }
}