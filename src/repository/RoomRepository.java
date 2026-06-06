package repository;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import config.DatabaseConnection;
import models.Room;
import models.RoomStatus;
import models.ReservationStatus;

public class RoomRepository {

    // Método para crear una habitación
	
    public void save(Room room) {

        String sql = "INSERT INTO rooms (roomNumber, floor, typeId, status) VALUES (?, ?, ?, ?)";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, room.getRoomNumber());
            ps.setInt(2, room.getFloor());
            ps.setInt(3, room.getTypeId());
            ps.setString(4, RoomStatus.ACTIVE);

            ps.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public boolean update(Room room) {

        String sql = "UPDATE rooms SET roomNumber=?, floor=?, typeId=?, status=? WHERE roomId=?";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, room.getRoomNumber());
            ps.setInt(2, room.getFloor());
            ps.setInt(3, room.getTypeId());
            ps.setString(4, room.getStatus());
            ps.setInt(5, room.getRoomId());

            return ps.executeUpdate() > 0;

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return false;
    }

    // Método para borrar una habitación
    // Utiliza el id de la habitación
    
    public boolean delete(int id) {

        String sql = "DELETE FROM rooms WHERE roomId=?";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, id);
            return ps.executeUpdate() > 0;

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return false;
    }

    // Método para obtener una habitación utilizando su id
    
    public Room findById(int id) {

        String sql = "SELECT * FROM rooms WHERE roomId=?";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, id);

            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                return new Room(
                        rs.getInt("roomId"),
                        rs.getInt("roomNumber"),
                        rs.getInt("floor"),
                        rs.getInt("typeId"),
                        rs.getString("status")
                );
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null;
    }

    // Método para obtener todas las habitaciones
    
    public List<Room> getRooms() {

        List<Room> list = new ArrayList<>();

        String sql = "SELECT * FROM rooms";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                list.add(new Room(
                        rs.getInt("roomId"),
                        rs.getInt("roomNumber"),
                        rs.getInt("floor"),
                        rs.getInt("typeId"),
                        rs.getString("status")
                ));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return list;
    }
    
    // Método para obtener todas las habitaciones que son de un tipo de habitación 
    // Utiliza el id del RoomType

    public List<Room> findByTypeId(int typeId) {

        List<Room> list = new ArrayList<>();

        String sql = "SELECT * FROM rooms WHERE typeId=?";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, typeId);

            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                list.add(new Room(
                        rs.getInt("roomId"),
                        rs.getInt("roomNumber"),
                        rs.getInt("floor"),
                        rs.getInt("typeId"),
                        rs.getString("status")
                ));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return list;
    }

    // VALIDATIONS
    public boolean existsRoomNumber(int roomNumber) {

        String sql = "SELECT 1 FROM rooms WHERE roomNumber=? LIMIT 1";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, roomNumber);
            return ps.executeQuery().next();

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return false;
    }

    public boolean existsByTypeId(int typeId) {

        String sql = "SELECT 1 FROM rooms WHERE typeId=? LIMIT 1";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, typeId);
            return ps.executeQuery().next();

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return false;
    }

    // Lógica de negocios
    // Método para verificar que una habitación está activa (significa si la habitación funciona o no)
    
    public boolean isRoomOperational(int roomId) {

        String sql = "SELECT status FROM rooms WHERE roomId=?";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, roomId);

            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                return RoomStatus.ACTIVE.equals(rs.getString(1));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return false;
    }
    
    // Método que checa si una habitación está disponible entre ciertas fechas
    // Utiliza el id de la habitación, la fecha de entrada y la fecha de salida

    public boolean isRoomAvailableByDates(int roomId, LocalDate checkIn, LocalDate checkOut) {

        String sql =
                "SELECT 1 FROM reservations " +
                "WHERE roomId=? " +
                "AND status=? " +
                "AND (checkInDate < ? AND checkOutDate > ?) " +
                "LIMIT 1";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, roomId);
            ps.setString(2, ReservationStatus.CONFIRMED);
            ps.setDate(3, Date.valueOf(checkOut));
            ps.setDate(4, Date.valueOf(checkIn));

            return !ps.executeQuery().next();

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return false;
    }

    // Método que checa si una habitación está disponible para reservar
    
    public boolean isRoomAvailable(int roomId, LocalDate checkIn, LocalDate checkOut) {
        return isRoomOperational(roomId) && isRoomAvailableByDates(roomId, checkIn, checkOut);
    }
    
    // Método para desabilitar botón de reservar cuando no hayan habitaciones de ese tipo
    
    public boolean hasActiveRoomsByType(int typeId) {

        String sql =
            "SELECT 1 " +
            "FROM rooms " +
            "WHERE typeId=? " +
            "AND status=? " +
            "LIMIT 1";

        try(Connection conn = DatabaseConnection.getConnection();
            PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, typeId);
            ps.setString(2, RoomStatus.ACTIVE);
            
            return ps.executeQuery().next();
            
        } catch(Exception e) {
            e.printStackTrace();
        }

        return false;
    }
}