package repository;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import config.DatabaseConnection;
import models.Payment;

public class PaymentRepository {

	//Método para crear un pago
	
    public void save(Payment payment) {

        String sql =
            "INSERT INTO payments (reservationId, amount, method, paymentDate) " +
            "VALUES (?, ?, ?, ?)";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, payment.getReservationId());
            ps.setDouble(2, payment.getAmount());
            ps.setString(3, payment.getMethod());
            ps.setDate(4, Date.valueOf(payment.getPaymentDate()));

            ps.executeUpdate();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    //Método para obtener todos los pagos, en orden ascendente
    
    public List<Payment> getAll() {
        List<Payment> list = new ArrayList<>();
        String sql = "SELECT * FROM payments ORDER BY paymentDate ASC";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                list.add(new Payment(
                    rs.getInt("paymentId"),
                    rs.getInt("reservationId"),
                    rs.getDouble("amount"),
                    rs.getString("method"),
                    rs.getDate("paymentDate").toLocalDate()
                ));
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return list;
    }
}