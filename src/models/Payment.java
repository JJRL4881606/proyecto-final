package models;

import java.time.LocalDate;

public class Payment {

    private int paymentId;
    private int reservationId;
    private double amount;
    private String method;
    private LocalDate paymentDate;

    // Constructor para crear un pago (con el id)
    // Usado en getAll() en PaymentRepository
    
    public Payment(int paymentId, int reservationId, double amount, String method, LocalDate paymentDate) {
        this.paymentId = paymentId;
        this.reservationId = reservationId;
        this.amount = amount;
        this.method = method;
        this.paymentDate = paymentDate;
    }

    // Constructor para crear un pago (sin el id)
    // usado en handleSave() en ReservationFormController,
    // y en finishPayment() en PaymentController
    
    public Payment(int reservationId, double amount, String method, LocalDate paymentDate) {
        this.reservationId = reservationId;
        this.amount = amount;
        this.method = method;
        this.paymentDate = paymentDate;
    }

    //getters y setters
    public int getPaymentId() {
        return paymentId;
    }

    public int getReservationId() {
        return reservationId;
    }

    public double getAmount() {
        return amount;
    }

    public String getMethod() {
        return method;
    }

    public LocalDate getPaymentDate() {
        return paymentDate;
    }

    public void setPaymentId(int paymentId) {
        this.paymentId = paymentId;
    }

    public void setReservationId(int reservationId) {
        this.reservationId = reservationId;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public void setMethod(String method) {
        this.method = method;
    }

    public void setPaymentDate(LocalDate paymentDate) {
        this.paymentDate = paymentDate;
    }
}