package controllers.reservations;

import java.util.List;
import models.Payment;
import repository.PaymentRepository;
import tablemodels.PaymentTableModel;
import views.reservations.PaymentsAdminView;

public class PaymentAdminController {

    private PaymentsAdminView view;
    private PaymentRepository repo;
    private PaymentTableModel model;

    public PaymentAdminController(PaymentsAdminView view) {
        this.view = view;
        this.repo = new PaymentRepository();
    }

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