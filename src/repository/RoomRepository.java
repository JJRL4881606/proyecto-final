package repository;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

import models.Room;

public class RoomRepository {

    private final String FILE = "src/assets/files/rooms.csv";

    public void save(Room room) throws IOException {

        try (BufferedWriter writer = new BufferedWriter(
                new OutputStreamWriter(
                        new FileOutputStream(FILE, true),
                        StandardCharsets.UTF_8))) {

            writer.write(room.toCsv());
            writer.newLine();
        }
    }

    public List<Room> getRooms() throws IOException {

        List<Room> rooms = new ArrayList<>();

        try (BufferedReader reader =
                     new BufferedReader(new FileReader(FILE))) {

            String line;

            while ((line = reader.readLine()) != null) {
                Room room = Room.fromCsv(line);
                rooms.add(room);
            }
        }

        return rooms;
    }

    public void updateAll(List<Room> rooms) throws IOException {

        try (BufferedWriter writer = new BufferedWriter(
                new OutputStreamWriter(
                        new FileOutputStream(FILE),
                        StandardCharsets.UTF_8))) {

            for (Room room : rooms) {
                writer.write(room.toCsv());
                writer.newLine();
            }
        }
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