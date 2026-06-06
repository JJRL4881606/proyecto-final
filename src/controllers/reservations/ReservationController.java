package controllers.reservations;

import java.util.List;
import javax.swing.JOptionPane;
import models.Reservation;
import models.ReservationStatus;
import repository.ReservationRepository;
import tablemodels.ReservationTableModel;
import views.reservations.ReservationFormDialog;
import views.reservations.ReservationsView;

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
            loadReservations();
            view.revalidate();
            view.repaint();
        }
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
            JOptionPane.showMessageDialog(null, "Selecciona una reservación");
            return;
        }

        Reservation r = model.getReservationAt(row);

        if (r.getStatus().equals(ReservationStatus.CONFIRMED) ||
            r.getStatus().equals(ReservationStatus.COMPLETED)) {

            JOptionPane.showMessageDialog(null,
                "No puedes eliminar una reservación activa o completada"
            );
            return;
        }

        int confirm = JOptionPane.showConfirmDialog(
            null,
            "¿Eliminar reservación?",
            "Confirmar",
            JOptionPane.YES_NO_OPTION
        );

        if (confirm != JOptionPane.YES_OPTION) return;

        if (repo.delete(r.getReservationId())) {
            loadReservations();
        }
    }
}