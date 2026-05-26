package tablemodels;

import java.time.format.DateTimeFormatter;
import java.util.List;
import javax.swing.table.AbstractTableModel;
import models.Reservation;
import models.Room;
import models.User;
import repository.RoomRepository;
import repository.UserRepository;

@SuppressWarnings("serial")
public class ReservationTableModel extends AbstractTableModel {

    private List<Reservation> reservations;
    private UserRepository userRepo = new UserRepository();
    private RoomRepository roomRepo = new RoomRepository();
    private final String[] columns = {"Usuario", "Habitación", "Entrada", "Salida", "Huéspedes", "Estado", "Total", "Creada"};

    private static final DateTimeFormatter DATE_FORMAT = DateTimeFormatter.ofPattern("dd/MM/yyyy");
    private static final DateTimeFormatter DATE_TIME_FORMAT = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");

    public ReservationTableModel(List<Reservation> reservations) {
        this.reservations = reservations;
    }

    @Override
    public int getRowCount() { return reservations.size(); }

    @Override
    public int getColumnCount() { return columns.length; }

    @Override
    public String getColumnName(int column) { return columns[column]; }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        Reservation reservation = reservations.get(rowIndex);

        switch (columnIndex) {
	        case 0:
	            User user = userRepo.findById(
	                reservation.getUserId()
	            );
	
	            return user != null ? user.getName() + " " + user.getSurname() : "-";     
	            
            case 1:
                Room room = roomRepo.findById(
                    reservation.getRoomId()
                );
                return room != null  ? room.getRoomNumber(): "-";         
                
            case 2: return reservation.getCheckInDate().format(DATE_FORMAT);
            case 3: return reservation.getCheckOutDate().format(DATE_FORMAT);
            case 4: return reservation.getGuests();
            case 5: return reservation.getStatus();
            case 6: return "$" + reservation.getTotal();
            case 7: return reservation.getCreatedAt().format(DATE_TIME_FORMAT);
            default: return null;
        }
    }

    public Reservation getReservationAt(int row) { return reservations.get(row); }

    public void setReservations(List<Reservation> reservations) {
        this.reservations = reservations;
        fireTableDataChanged();
    }

    public void removeRow(int row) {
        reservations.remove(row);
        fireTableRowsDeleted(row, row);
    }

    public void addRow(Reservation reservation) {
        int row = reservations.size();
        reservations.add(reservation);
        fireTableRowsInserted(row, row);
    }

    public void updateRow(int row, Reservation reservation) {
        reservations.set(row, reservation);
        fireTableRowsUpdated(row, row);
    }
}