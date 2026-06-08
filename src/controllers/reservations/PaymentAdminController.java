package controllers.reservations;

import java.util.List;
import models.Payment;
import repository.PaymentRepository;
import tablemodels.PaymentTableModel;
import views.reservations.PaymentsAdminView;

//controller del panel de pagos. carga los datos desde la bd y los pasa a la tabla
public class PaymentAdminController {

    private PaymentsAdminView view;
    private PaymentRepository repo;
    private PaymentTableModel model;

    public PaymentAdminController(PaymentsAdminView view) {
        this.view = view;
        this.repo = new PaymentRepository();
    }

    // Si el modelo todavía no existe lo crea y lo asigna a la tabla
    // si ya existe solo actualiza sus datos 
    public void loadPayments() {
        List<Payment> payments = repo.getAll();

        if (model == null) {
            model = new PaymentTableModel(payments);
            view.setTableModel(model);
        } else {
            model.setPayments(payments);
        }
    }
}