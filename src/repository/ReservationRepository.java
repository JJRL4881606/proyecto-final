package repository;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import config.DatabaseConnection;
import models.Reservation;
import models.ReservationStatus;

public class ReservationRepository {

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

    public boolean update(Reservation r) {
        String sql = "UPDATE reservations SET userId=?, roomId=?, checkInDate=?, checkOutDate=?, guests=?, status=?, total=? WHERE reservationId=?";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, r.getUserId());
            ps.setInt(2, r.getRoomId());
            ps.setDate(3, Date.valueOf(r.getCheckInDate()));
            ps.setDate(4, Date.valueOf(r.getCheckOutDate()));
            ps.setInt(5, r.getGuests());
            ps.setString(6, r.getStatus());
            ps.setDouble(7, r.getTotal());
            ps.setInt(8, r.getReservationId());

            return ps.executeUpdate() > 0;

        } catch (Exception e) {
            e.printStackTrace();
        }

        return false;
    }

    public boolean delete(int id) {
        String sql = "DELETE FROM reservations WHERE reservationId=?";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, id);
            return ps.executeUpdate() > 0;

        } catch (Exception e) {
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
            "AND status=? " +
            "AND reservationId<>? " +
            "AND (checkInDate < ? AND checkOutDate > ?) " +
            "LIMIT 1";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, roomId);
            ps.setString(2, ReservationStatus.CONFIRMED);
            ps.setInt(3, reservationIdToIgnore);
            ps.setDate(4, Date.valueOf(checkOut));
            ps.setDate(5, Date.valueOf(checkIn));

            return !ps.executeQuery().next();

        } catch (Exception e) {
            e.printStackTrace();
        }

        return false;
    }

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
}