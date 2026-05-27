package controllers.booking;

import java.time.LocalDate;
import java.util.List;
import javax.swing.JOptionPane;
import models.Reservation;
import repository.ReservationRepository;
import tablemodels.ReservationTableModel;
import views.booking.ReservationFormDialog;
import views.booking.ReservationsView;

public class ReservationController {

    private ReservationsView view;
    private ReservationRepository repo;
    private ReservationTableModel model;

    public ReservationController(ReservationsView view) {
        this.view = view;
        this.repo = new ReservationRepository();
        initListeners();
    }

    public void initListeners() {
        view.getBtnAdd().addActionListener(e -> openForm(null));
        view.getBtnEdit().addActionListener(e -> handleEdit());
        view.getBtnDelete().addActionListener(e -> handleDelete());
    }

    public void loadReservations() {
        List<Reservation> reservations = repo.getReservations();

        if (model == null) {
            model = new ReservationTableModel(reservations);
            view.setTableModel(model);
        } else {
            model.setReservations(reservations);
        }
    }

    private void openForm(Reservation reservation) {
        ReservationFormDialog dialog = new ReservationFormDialog(null, reservation);
        new ReservationFormController(dialog);
        dialog.setVisible(true);

        if (dialog.isSaved()) {
            Reservation savedReservation = dialog.getReservation();
            try {
                if (!validateReservation(savedReservation)) return;

                if (reservation == null) {
                    repo.save(savedReservation);
                    loadReservations();
                } else {
                    int row = view.getSelectedModelRow();
                    Reservation original = model.getReservationAt(row);
                    savedReservation.setReservationId(original.getReservationId());

                    if (repo.update(savedReservation)) loadReservations();
                }
                view.revalidate();
                view.repaint();
            } catch (Exception e) {
                e.printStackTrace();
                JOptionPane.showMessageDialog(view, e.getMessage());
            }
        }
    }

    private boolean validateReservation(Reservation reservation) {
        LocalDate checkIn = reservation.getCheckInDate();
        LocalDate checkOut = reservation.getCheckOutDate();

        if (checkOut.isBefore(checkIn) || checkOut.equals(checkIn)) {
            JOptionPane.showMessageDialog(null, "La fecha de salida debe ser después de la entrada", "Error", JOptionPane.ERROR_MESSAGE);
            return false;
        }
        if (reservation.getGuests() <= 0) {
            JOptionPane.showMessageDialog(null, "Debe haber al menos un huésped", "Error", JOptionPane.ERROR_MESSAGE);
            return false;
        }
        if (reservation.getTotal() < 0) {
            JOptionPane.showMessageDialog(null, "El total es inválido", "Error", JOptionPane.ERROR_MESSAGE);
            return false;
        }
        return true;
    }

    private void handleEdit() {
        int row = view.getSelectedModelRow();
        if (row == -1) {
            JOptionPane.showMessageDialog(null, "Selecciona una reservación", "Advertencia", JOptionPane.WARNING_MESSAGE);
            return;
        }
        openForm(model.getReservationAt(row));
    }

    private void handleDelete() {
        int row = view.getSelectedModelRow();
        if (row == -1) {
            JOptionPane.showMessageDialog(null, "Selecciona una reservación", "Advertencia", JOptionPane.WARNING_MESSAGE);
            return;
        }

        Reservation reservation = model.getReservationAt(row);
        String status = reservation.getStatus();
        if (status.equalsIgnoreCase("Confirmada") || status.equalsIgnoreCase("Completada")) {
            JOptionPane.showMessageDialog(null, "No puedes eliminar esta reservación", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        int confirm = JOptionPane.showConfirmDialog(null, "¿Eliminar reservación?", "Confirmar", JOptionPane.YES_NO_OPTION);
        if (confirm != JOptionPane.YES_OPTION) return;

        if (repo.delete(reservation.getReservationId())) {
            loadReservations();
        }
    }
}