package tablemodels;

import java.util.List;
import javax.swing.table.AbstractTableModel;
import models.Payment;

@SuppressWarnings("serial")
public class PaymentTableModel extends AbstractTableModel {

    private List<Payment> payments;

    private final String[] columns = {
        "ID Pago",
        "ID Reservación",
        "Monto",
        "Método de pago",
        "Fecha de pago"
    };

    public PaymentTableModel(List<Payment> payments) {
        this.payments = payments;
    }

    @Override
    public int getRowCount() {
        return payments.size();
    }

    @Override
    public int getColumnCount() {
        return columns.length;
    }

    @Override
    public String getColumnName(int column) {
        return columns[column];
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        Payment payment = payments.get(rowIndex);
        switch (columnIndex) {
            case 0: return payment.getPaymentId();
            case 1: return payment.getReservationId();
            case 2: return String.format("$%.2f", payment.getAmount());
            case 3: return payment.getMethod();
            case 4: return payment.getPaymentDate().format(
                        java.time.format.DateTimeFormatter.ofPattern("dd/MM/yyyy")
                    );
        }
        return null;
    }

    public Payment getPaymentAt(int row) {
        return payments.get(row);
    }

    public void setPayments(List<Payment> payments) {
        this.payments = payments;
        fireTableDataChanged();
    }
}