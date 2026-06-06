package repository;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import config.DatabaseConnection;
import models.Reservation;
import models.ReservationStatus;

public class ReservationRepository {

	// Método para crear una reservación
	
    public void save(Reservation reservation) {
        String sql = "INSERT INTO reservations(userId, roomId, checkInDate, checkOutDate, guests, status, total, createdAt) VALUES(?, ?, ?, ?, ?, ?, ?, ?)";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, reservation.getUserId());
            ps.setInt(2, reservation.getRoomId());
            ps.setDate(3, Date.valueOf(reservation.getCheckInDate()));
            ps.setDate(4, Date.valueOf(reservation.getCheckOutDate()));
            ps.setInt(5, reservation.getGuests());
            ps.setString(6, reservation.getStatus());
            ps.setDouble(7, reservation.getTotal());
            ps.setTimestamp(8, Timestamp.valueOf(reservation.getCreatedAt()));

            ps.executeUpdate();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    // Método para obtener todas las reservaciones en un arreglo

    public List<Reservation> getReservations() {
    	
        List<Reservation> list = new ArrayList<>();
        String sql = "SELECT * FROM reservations";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                list.add(new Reservation(
                    rs.getInt("reservationId"),
                    rs.getInt("userId"),
                    rs.getInt("roomId"),
                    rs.getDate("checkInDate").toLocalDate(),
                    rs.getDate("checkOutDate").toLocalDate(),
                    rs.getInt("guests"),
                    rs.getString("status"),
                    rs.getDouble("total"),
                    rs.getTimestamp("createdAt").toLocalDateTime()
                ));
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return list;
    }

    public boolean update(Reservation reservation) {
        String sql = "UPDATE reservations SET userId=?, roomId=?, checkInDate=?, checkOutDate=?, guests=?, status=?, total=? WHERE reservationId=?";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, reservation.getUserId());
            ps.setInt(2, reservation.getRoomId());
            ps.setDate(3, Date.valueOf(reservation.getCheckInDate()));
            ps.setDate(4, Date.valueOf(reservation.getCheckOutDate()));
            ps.setInt(5, reservation.getGuests());
            ps.setString(6, reservation.getStatus());
            ps.setDouble(7, reservation.getTotal());
            ps.setInt(8, reservation.getReservationId());

            return ps.executeUpdate() > 0;

        } catch (Exception e) {
            e.printStackTrace();
        }

        return false;
    }
    
    // Método para borrar una reservación
    // Necesita el id de la reservación

    public boolean delete(int id) {

        try(Connection conn = DatabaseConnection.getConnection()) {

            // borrar pagos relacionados
            String deletePayments =
                "DELETE FROM payments WHERE reservationId=?";

            try(PreparedStatement ps = conn.prepareStatement(deletePayments)) {
                ps.setInt(1, id);
                ps.executeUpdate();
            }

            // borrar reservación
            String deleteReservation =
                "DELETE FROM reservations WHERE reservationId=?";

            try(PreparedStatement ps = conn.prepareStatement(deleteReservation)) {
                ps.setInt(1, id);
                return ps.executeUpdate() > 0;
            }

        } catch(Exception e) {
            e.printStackTrace();
        }

        return false;
    }

    public boolean hasReservationsByUser(int userId) {
        String sql = "SELECT 1 FROM reservations WHERE userId=? LIMIT 1";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, userId);
            return ps.executeQuery().next();

        } catch (Exception e) {
            e.printStackTrace();
        }

        return false;
    }

    public boolean hasReservationsByRoom(int roomId) {
        String sql = "SELECT 1 FROM reservations WHERE roomId=? LIMIT 1";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, roomId);
            return ps.executeQuery().next();

        } catch (Exception e) {
            e.printStackTrace();
        }

        return false;
    }

    public boolean isRoomAvailableByDates(int roomId, LocalDate checkIn, LocalDate checkOut) {

        String sql =
            "SELECT 1 FROM reservations " +
            "WHERE roomId=? " +
            "AND status <> ? " +
            "AND (checkInDate < ? AND checkOutDate > ?) " +
            "LIMIT 1";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, roomId);
            ps.setString(2, ReservationStatus.CANCELED);
            ps.setDate(3, Date.valueOf(checkOut));
            ps.setDate(4, Date.valueOf(checkIn));

            return !ps.executeQuery().next();

        } catch (Exception e) {
            e.printStackTrace();
        }

        return false;
    }
    
    //ignora la reservación propia que se está editando
    public boolean isRoomAvailableByDates(
            int roomId,
            LocalDate checkIn,
            LocalDate checkOut,
            int reservationIdToIgnore) {

        String sql =
            "SELECT 1 FROM reservations " +
            "WHERE roomId=? " +
            "AND status <> ? " +
            "AND reservationId<>? " +
            "AND (checkInDate < ? AND checkOutDate > ?) " +
            "LIMIT 1";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, roomId);
            ps.setString(2, ReservationStatus.CANCELED);
            ps.setInt(3, reservationIdToIgnore);
            ps.setDate(4, Date.valueOf(checkOut));
            ps.setDate(5, Date.valueOf(checkIn));

            return !ps.executeQuery().next();

        } catch (Exception e) {
            e.printStackTrace();
        }

        return false;
    }
    
    // Obtener las reservaciones asociadas a un usuario, en orden de creación
    // Utiliza el id del usuario
    
    public List<Reservation> getReservationsByUser(int userId) {

        List<Reservation> list = new ArrayList<>();

        String sql = "SELECT * FROM reservations WHERE userId=? ORDER BY createdAt DESC";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, userId);

            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                list.add(new Reservation(
                    rs.getInt("reservationId"),
                    rs.getInt("userId"),
                    rs.getInt("roomId"),
                    rs.getDate("checkInDate").toLocalDate(),
                    rs.getDate("checkOutDate").toLocalDate(),
                    rs.getInt("guests"),
                    rs.getString("status"),
                    rs.getDouble("total"),
                    rs.getTimestamp("createdAt").toLocalDateTime()
                ));
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return list;
    }
    
    // Método para cancelar una reservación
    // Utiliza el id de la reservación

    public boolean cancelReservation(int reservationId) {

        String sql = "UPDATE reservations SET status=? WHERE reservationId=?";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, ReservationStatus.CANCELED);
            ps.setInt(2, reservationId);

            return ps.executeUpdate() > 0;

        } catch (Exception e) {
            e.printStackTrace();
        }

        return false;
    }
    
    // Método para obtener el id de una habitación de una reservación
    // Utiliza el id de la reservación

    public int getRoomIdByReservation(int reservationId) {

        String sql = "SELECT roomId FROM reservations WHERE reservationId=?";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, reservationId);

            ResultSet rs = ps.executeQuery();
            return rs.next() ? rs.getInt(1) : -1;

        } catch (Exception e) {
            e.printStackTrace();
        }

        return -1;
    }
    
    public boolean hasActiveReservation(int roomId) {

        String sql =
            "SELECT 1 " +
            "FROM reservations " +
            "WHERE roomId=? " +
            "AND status=? " +
            "AND checkOutDate > CURDATE() " +
            "LIMIT 1";

        try(Connection conn = DatabaseConnection.getConnection();
            PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, roomId);
            ps.setString(2, ReservationStatus.CONFIRMED);

            return ps.executeQuery().next();

        } catch(Exception e) {
            e.printStackTrace();
        }

        return false;
    }
    
    public int saveAndReturnId(Reservation reservation) {

        String sql =
            "INSERT INTO reservations " +
            "(userId, roomId, checkInDate, checkOutDate, guests, total, status) " +
            "VALUES (?, ?, ?, ?, ?, ?, ?)";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            ps.setInt(1, reservation.getUserId());
            ps.setInt(2, reservation.getRoomId());
            ps.setDate(3, java.sql.Date.valueOf(reservation.getCheckInDate()));
            ps.setDate(4, java.sql.Date.valueOf(reservation.getCheckOutDate()));
            ps.setInt(5, reservation.getGuests());
            ps.setDouble(6, reservation.getTotal());
            ps.setString(7, reservation.getStatus());

            ps.executeUpdate();

            ResultSet rs = ps.getGeneratedKeys();

            if (rs.next()) {
                return rs.getInt(1);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return -1;
    }
}