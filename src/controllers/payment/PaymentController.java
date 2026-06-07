package controllers.payment;

import java.awt.*;
import java.awt.event.*;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.swing.*;

import models.*;
import repository.PaymentRepository;
import repository.ReservationRepository;
import repository.RoomRepository;
import views.main.MainWindow;
import views.payment.PaymentView;
import views.payment.PaymentWindow;

// Controla toda la lógica de la pantalla de pago, que es
// validaciones, cálculo de noches/total, búsqueda de habitación disponible
// y guardado de la reservación y el pago en la bd
public class PaymentController {

    private PaymentWindow paymentWindow;
    private PaymentView paymentView;
    private User user;

    private ReservationRepository reservationRepo = new ReservationRepository();
    private PaymentRepository paymentRepo = new PaymentRepository();
    private RoomRepository roomRepo = new RoomRepository();

    public PaymentController(PaymentWindow paymentWindow, PaymentView paymentView, User user) {

        this.paymentWindow = paymentWindow;
        this.paymentView = paymentView;
        this.user = user;

        calculateNights();
        initListeners();
        setupDateValidation();
    }

    // Calcula cuántas noches hay entre check-in y check-out convirtiendo la diferencia de milisegundos a días
    private long calculateNights() {
        Date in = (Date) paymentView.getSpCheckIn().getValue();
        Date out = (Date) paymentView.getSpCheckOut().getValue();

        return (out.getTime() - in.getTime()) / (1000 * 60 * 60 * 24);
    }

    //conectar eventos
    private void initListeners() {

        paymentView.getBtnPay().addActionListener(e -> processPayment());

        paymentWindow.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                goHome();
            }
        });

        paymentView.getBtnHome().addActionListener(e -> goHome());

        paymentView.getSpCheckIn().addChangeListener(e -> updateUI());
        paymentView.getSpCheckOut().addChangeListener(e -> updateUI());

        paymentView.getLblLogo().addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                goHome();
            }
        });
    }

    // Abre la ventana principal y cierra la ventana de pago actual
    private void goHome() {
        new MainWindow(user);

        Window window = SwingUtilities.getWindowAncestor(paymentView);
        if (window != null) window.dispose();
    }

    // Actualiza las labels del panel de resumen (fechas, noches y total)
    // cuando el usuario cambia alguna fecha en los spinners
    private void updateUI() {

        Date in = (Date) paymentView.getSpCheckIn().getValue();
        Date out = (Date) paymentView.getSpCheckOut().getValue();

        long nights = calculateNights();

        double total = nights * paymentView.getRoom().getPrice();

        SimpleDateFormat df = new SimpleDateFormat("dd/MM/yyyy");

        paymentView.getLblCheckIn().setText("Entrada: " + df.format(in));
        paymentView.getLblCheckOut().setText("Salida: " + df.format(out));
        paymentView.getLblNights().setText("Estancia: " + nights + " noche/s");
        paymentView.getLblTotal().setText("Total: $" + total);
    }

    // Valida que los datos personales estén completos y que el usuario
    //haya aceptado los checkboxes antes de continuar con el pago
    private void processPayment() {

        String name = paymentView.getTxtFirstName().getText().trim();
        String last = paymentView.getTxtLastName().getText().trim();
        String email = paymentView.getTxtEmail().getText().trim();
        String phone = paymentView.getTxtPhone().getText().trim();

        if (name.isEmpty() || last.isEmpty() || email.isEmpty() || phone.isEmpty()) {
            JOptionPane.showMessageDialog(null, "Debes completar todos los campos");
            return;
        }

        if (!paymentView.getChkTerms().isSelected() || !paymentView.getChkPolicies().isSelected()) {
            JOptionPane.showMessageDialog(null, "Debes aceptar términos y políticas");
            return;
        }

        finishPayment();
    }

    // Hace el proceso completo de guardar la reservación y el pago en la bd
    // Primero busca una habitación física disponible del tipo seleccionado
    // (un RoomType puede tener varias habitaciones, se recorre la lista
    // hasta encontrar una que esté activa y sin reservas en esas fechas)
    // Si encuentra una, guarda la Reservation y luego el Payment vinculado a ella
    // Si no hay ninguna disponible, muestra un error y no guarda nada
    private void finishPayment() {

        int userId = user.getId();
        int typeId = paymentView.getRoom().getTypeId();

        // Convertir las fechas del spinner a LocalDate para usarlas en el repositorio
        LocalDate checkIn = ((Date) paymentView.getSpCheckIn().getValue())
                .toInstant().atZone(ZoneId.systemDefault()).toLocalDate();

        LocalDate checkOut = ((Date) paymentView.getSpCheckOut().getValue())
                .toInstant().atZone(ZoneId.systemDefault()).toLocalDate();

        int roomId = -1;

        // Buscar una habitación disponible del tipo elegido
        List<Room> rooms = roomRepo.findByTypeId(typeId);
        for (Room r : rooms) {
        	
            // Solo considerar habitaciones activas
            if (!RoomStatus.ACTIVE.equals(r.getStatus())) continue;

            boolean available = reservationRepo.isRoomAvailableByDates(r.getRoomId(), checkIn, checkOut);

            if (available) {
                roomId = r.getRoomId();
                break;
            }
        }

        if (roomId == -1) {
            JOptionPane.showMessageDialog(null, "No hay habitaciones disponibles");
            return;
        }

        long nights = calculateNights();
        double total = nights * paymentView.getRoom().getPrice();
        
        // Crear la reservación con estado CONFIRMED y guardarla para obtener su id
        Reservation reservation = new Reservation(
            0,
            userId,
            roomId,
            checkIn,
            checkOut,
            paymentView.getGuests(),
            ReservationStatus.CONFIRMED,
            total,
            LocalDateTime.now()
        );

        int reservationId = reservationRepo.saveAndReturnId(reservation);

        if (reservationId == -1) {
            JOptionPane.showMessageDialog(null, "Error creando reservación");
            return;
        }

        // Crear el pago vinculado a la reservación recién guardada
        Payment payment = new Payment(
            reservationId,
            total,
            paymentView.getCmbPaymentMethod().getSelectedItem().toString(),
            LocalDate.now()
        );

        paymentRepo.save(payment);

        JOptionPane.showMessageDialog(null, "Pago realizado correctamente");

        goHome();
    }

    // Configura las reglas de los spinners de fecha para que el usuario no pueda elegir
    // fechas inválidas (checkout antes que checkin, o estancia mayor a 30 días)
    // Hay dos listeners, el de check-in actualiza el rango permitido del checkout cada vez que cambia,
    // y el de check-out corrige su propio valor si queda fuera del rango válido
	private void setupDateValidation() {

        JSpinner spCheckIn = paymentView.getSpCheckIn();
        JSpinner spCheckOut = paymentView.getSpCheckOut();

        // ================= CHECK IN =================
        spCheckIn.addChangeListener(e -> {

            Date checkInDate = (Date) spCheckIn.getValue();

            Calendar cal = Calendar.getInstance();
            cal.setTime(checkInDate);

            // mínimo checkout = +1 día
            cal.add(Calendar.DAY_OF_MONTH, 1);

            Date minCheckOut = cal.getTime();

            SpinnerDateModel checkOutModel = (SpinnerDateModel)spCheckOut.getModel();

            // actualizar mínimo permitido
            checkOutModel.setStart(minCheckOut);

            // máximo checkout = +30 días desde checkin
            Calendar maxCal = Calendar.getInstance();
            maxCal.setTime(checkInDate);
            maxCal.add(Calendar.DAY_OF_MONTH, 30);

            Date maxCheckOut = maxCal.getTime();

            checkOutModel.setEnd(maxCheckOut);
            
            // si checkout quedó inválido, corregirlo automáticamente
            Date currentCheckOut = (Date) spCheckOut.getValue();

            if (currentCheckOut.before(minCheckOut)) {
                spCheckOut.setValue(minCheckOut);
            }
            
            if (currentCheckOut.after(maxCheckOut)) {
                spCheckOut.setValue(maxCheckOut);
            }
        });

        // ================= CHECK OUT =================
        spCheckOut.addChangeListener(e -> {
            Date checkInDate = (Date) spCheckIn.getValue();

            Calendar cal = Calendar.getInstance();
            cal.setTime(checkInDate);

            // mínimo permitido
            cal.add(Calendar.DAY_OF_MONTH, 1);
            Date minCheckOut = cal.getTime();

            // máximo permitido = checkin + 30 días
            Calendar maxCal = Calendar.getInstance();
            maxCal.setTime(checkInDate);
            maxCal.add(Calendar.DAY_OF_MONTH, 30);
            Date maxCheckOut = maxCal.getTime();

            // si el usuario intenta una fecha inválida, regresarla al límite más cercano
            Date selectedCheckOut = (Date) spCheckOut.getValue();
            if (selectedCheckOut.before(minCheckOut)) {
                spCheckOut.setValue(minCheckOut);
            } else if (selectedCheckOut.after(maxCheckOut)) {
                spCheckOut.setValue(maxCheckOut);
            }
        });
    }
}