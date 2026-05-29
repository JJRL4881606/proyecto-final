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
        String sql = "INSERT INTO reservations(userId, roomId, checkInDate, checkOutDate, guests, status, total, createdAt) VALUES(?,?,?,?,?,?,?,?)";

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
        List<Reservation> reservations = new ArrayList<>();
        String sql = "SELECT * FROM reservations";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                reservations.add(new Reservation(
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
        return reservations;
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
    
    public boolean isRoomAvailable(int roomId, LocalDate checkIn, LocalDate checkOut, int reservationId) {
        String sql = "SELECT 1 FROM reservations WHERE roomId=? AND reservationId != ? " +
                     "AND status IN (?, ?) AND (checkInDate < ? AND checkOutDate > ?) LIMIT 1";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, roomId);
            ps.setInt(2, reservationId);
            ps.setString(3, ReservationStatus.PENDING);
            ps.setString(4, ReservationStatus.CONFIRMED);
            ps.setDate(5, Date.valueOf(checkOut));
            ps.setDate(6, Date.valueOf(checkIn));

            try (ResultSet rs = ps.executeQuery()) {
                return !rs.next();
            }
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
    
    public boolean hasActiveReservation(int roomId){

        String sql =
            "SELECT 1 FROM reservations " +
            "WHERE roomId=? " +
            "AND status IN (?,?) " +
            "LIMIT 1";

        try(
            Connection conn=DatabaseConnection.getConnection();
            PreparedStatement ps=conn.prepareStatement(sql)
        ){

            ps.setInt(1,roomId);
            ps.setString(2,ReservationStatus.PENDING);
            ps.setString(3,ReservationStatus.CONFIRMED);

            ResultSet rs=ps.executeQuery();

            return rs.next();

        }catch(Exception e){
            e.printStackTrace();
        }

        return false;
    }
    
    public boolean hasReservationsByUser(int userId){

        String sql =
            "SELECT 1 FROM reservations " +
            "WHERE userId=? LIMIT 1";

        try(
            Connection conn = DatabaseConnection.getConnection();
            PreparedStatement ps = conn.prepareStatement(sql)
        ){

            ps.setInt(1,userId);

            ResultSet rs=
                ps.executeQuery();

            return rs.next();

        }catch(Exception e){
            e.printStackTrace();
        }

        return false;
    }
    
    public boolean hasReservationsByRoom(int roomId){

        String sql =
            "SELECT 1 FROM reservations " +
            "WHERE roomId=? LIMIT 1";

        try(
            Connection conn =
                DatabaseConnection.getConnection();

            PreparedStatement ps =
                conn.prepareStatement(sql)
        ){

            ps.setInt(1, roomId);

            ResultSet rs =
                ps.executeQuery();

            return rs.next();

        }catch(Exception e){
            e.printStackTrace();
        }

        return false;
    }
    
    
}