package controllers.payment;

import java.awt.Point;
import java.awt.Window;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.sql.Statement;
import java.text.SimpleDateFormat;

import javax.swing.*;
import config.DatabaseConnection;
import models.ReservationStatus;
import models.Room;
import models.RoomStatus;
import models.User;
import repository.ReservationRepository;
import repository.RoomRepository;
import views.main.MainWindow;
import views.payment.PaymentView;
import views.payment.PaymentWindow;

public class PaymentController {

    private PaymentWindow paymentWindow;
    private PaymentView paymentView;
    private long nights;
    private User user;

    public PaymentController(PaymentWindow paymentWindow, PaymentView paymentView, User user) {
	    this.paymentWindow = paymentWindow;
	    this.paymentView = paymentView;
	    this.user = user;

	    nights = ( ((Date) paymentView.getSpCheckOut().getValue()).getTime() 
	            - ((Date) paymentView.getSpCheckIn().getValue()).getTime() )
	            / (1000 * 60 * 60 * 24);
	    
        initListeners();
        setupDateValidation();
    }
    
	public void initListeners( ) {
		paymentView.getBtnPay().addActionListener(e -> processPayment());
				
		paymentWindow.addWindowListener(new WindowAdapter() {
		    @Override
		    public void windowClosing(WindowEvent e) {
		        handleClose();
		    }
		    
		    public void windowOpened(WindowEvent e) {
		        resetScroll();
		    }
		});
			
		paymentView.getBtnHome().addActionListener(e -> {
			new MainWindow(user);
            
            Window window = SwingUtilities.getWindowAncestor(paymentView);
            if (window != null) {
                window.dispose();
            }
		});	
		
		paymentView.getSpCheckIn().addChangeListener(e -> updateLabelCheckIn() );
		paymentView.getSpCheckOut().addChangeListener(e -> updateLabelCheckOut() );
		
		paymentView.getLblLogo().addMouseListener(
		    new MouseAdapter() {
		    	
		        @Override
		        public void mouseClicked(MouseEvent e) {
		        	
					new MainWindow(user);
		            
		            Window window = SwingUtilities.getWindowAncestor(paymentView);
		            if (window != null) {
		                window.dispose();
		            }

		            resetScroll();
		        }
		    }
		);

	}
	
	protected void updateLabelCheckIn() {
		paymentView.getLblCheckIn().setText("Entrada: " + new SimpleDateFormat("dd/MM/yyyy").format(paymentView.getSpCheckIn().getValue()));
		nights = ( ((Date) paymentView.getSpCheckOut().getValue()).getTime() 
	            - ((Date) paymentView.getSpCheckIn().getValue()).getTime() )
	            / (1000 * 60 * 60 * 24);
		paymentView.getLblNights().setText("Estancia: " + nights + " noche/s");
		paymentView.getLblTotal().setText("$ " + (nights * paymentView.getRoom().getPrice()));
	}
	
	protected void updateLabelCheckOut() {
		paymentView.getLblCheckOut().setText("Salida: " + new SimpleDateFormat("dd/MM/yyyy").format(paymentView.getSpCheckOut().getValue()));
		nights = ( ((Date) paymentView.getSpCheckOut().getValue()).getTime() 
	            - ((Date) paymentView.getSpCheckIn().getValue()).getTime() )
	            / (1000 * 60 * 60 * 24);
		paymentView.getLblNights().setText("Estancia: " + nights + " noche/s");
		paymentView.getLblTotal().setText("Total: $ " + (nights * paymentView.getRoom().getPrice()));
	}

	private void handleClose() {
		new MainWindow(user);
        Window window = SwingUtilities.getWindowAncestor(paymentView);
        if (window != null) window.dispose();
    }
	
	private void resetScroll() {
	    SwingUtilities.invokeLater(() -> {
	    	paymentWindow.getScroll().getViewport().setViewPosition(new Point(0, 0));
	    });
	}

    private void processPayment() {

        String name = paymentView.getTxtFirstName().getText().trim();
        String lastName = paymentView.getTxtLastName().getText().trim();
        String email = paymentView.getTxtEmail().getText().trim();
        String phone = paymentView.getTxtPhone().getText().trim();
        int cmbPaymentMethodSelected = paymentView.getCmbPaymentMethod().getSelectedIndex();

        boolean termsAccepted = paymentView.getChkTerms().isSelected();
        boolean policiesAccepted = paymentView.getChkPolicies().isSelected();

        // Validación básica
        if (name.isEmpty() || lastName.isEmpty() || email.isEmpty() || phone.isEmpty() || cmbPaymentMethodSelected == 0) {
        	
            JOptionPane.showMessageDialog(
                null,
                "Completa todos los campos",
                "Error",
                JOptionPane.ERROR_MESSAGE
            );
            return;
        }

        if (!termsAccepted || !policiesAccepted) {

            JOptionPane.showMessageDialog(
                null,
                "Debes aceptar los términos y políticas",
                "Advertencia",
                JOptionPane.WARNING_MESSAGE
            );
            return;
        }
        
        finishPayment();
    }
    
    public void finishPayment() {

        Connection conn = null;

        try {

            conn = DatabaseConnection.getConnection();
            conn.setAutoCommit(false);

            // ========= VALIDAR MÉTODO DE PAGO =========
            if (paymentView.getCmbPaymentMethod().getSelectedIndex() == 0) {

                JOptionPane.showMessageDialog(
                    null,
                    "Seleccione un método de pago"
                );

                return;
            }

            // ========= OBTENER DATOS =========
            int userId = user.getId();

            int selectedTypeId = paymentView.getRoom().getTypeId();

            double total = nights * paymentView.getRoom().getPrice();
            
            // ========= BUSCAR HABITACIÓN DISPONIBLE =========
            int roomId = -1;

            LocalDate checkIn =
                    ((Date) paymentView.getSpCheckIn().getValue())
                            .toInstant()
                            .atZone(ZoneId.systemDefault())
                            .toLocalDate();

            LocalDate checkOut =
                    ((Date) paymentView.getSpCheckOut().getValue())
                            .toInstant()
                            .atZone(ZoneId.systemDefault())
                            .toLocalDate();

            RoomRepository roomRepo = new RoomRepository();
            ReservationRepository reservationRepo = new ReservationRepository();

            List<Room> rooms = roomRepo.findByTypeId(selectedTypeId);

            for (Room room : rooms) {

                if (!RoomStatus.ACTIVE.equals(room.getStatus())) {
                    continue;
                }

                boolean available =
                        reservationRepo.isRoomAvailableByDates(
                                room.getRoomId(),
                                checkIn,
                                checkOut
                        );

                if (available) {
                    roomId = room.getRoomId();
                    break;
                }
            }

            if (roomId == -1) {

                JOptionPane.showMessageDialog(
                        null,
                        "No hay habitaciones disponibles para esas fechas"
                );

                conn.rollback();
                return;
            }

            // ========= GUARDAR RESERVACIÓN =========
            String reservationSql =
                    "INSERT INTO reservations "
                  + "(userId, roomId, "
                  + "checkInDate, "
                  + "checkOutDate, "
                  + "guests, total, status) "
                  + "VALUES (?, ?, ?, ?, ?, ?, ?)";

            PreparedStatement reservationStmt =
                    conn.prepareStatement(
                            reservationSql,
                            Statement.RETURN_GENERATED_KEYS
                    );

            reservationStmt.setInt(
                    1,
                    userId
            );

            reservationStmt.setInt(
                    2,
                    roomId
            );

            reservationStmt.setDate(
                    3,
                    new java.sql.Date(((Date)paymentView.getSpCheckIn().getValue()).getTime())
            );

            reservationStmt.setDate(
                    4,
                    new java.sql.Date(((Date)paymentView.getSpCheckOut().getValue()).getTime())
            );

            reservationStmt.setInt(
                    5,
                    paymentView.getGuests()
            );

            reservationStmt.setDouble(
                    6,
                    total
            );

            reservationStmt.setString(
                    7,
                    ReservationStatus.CONFIRMED
            );

            reservationStmt.executeUpdate();

            // ========= OBTENER reservationId =========
            ResultSet generatedKeys = reservationStmt.getGeneratedKeys();

            int reservationId = -1;

            if (generatedKeys.next()) {
                reservationId = generatedKeys.getInt(1);
            }

            if (reservationId == -1) {
                throw new SQLException(
                	"No se pudo generar reservationId"
                );
            }

            // ========= GUARDAR PAYMENT =========
            String paymentSql =
                    "INSERT INTO payments "
                  + "(reservationId, amount, method, paymentDate) "
                  + "VALUES (?, ?, ?, ?)";

            PreparedStatement paymentStmt =
                    conn.prepareStatement(
                            paymentSql
                    );

            paymentStmt.setInt(
                    1,
                    reservationId
            );

            paymentStmt.setDouble(
                    2,
                    total
            );

            paymentStmt.setString(
                    3,
                    String.valueOf(paymentView.getCmbPaymentMethod().getSelectedItem())
            );

            paymentStmt.setDate(
                    4,
                    java.sql.Date.valueOf(LocalDate.now())
            );

            paymentStmt.executeUpdate();

            // ========= ACTUALIZAR HABITACIÓN =========
            /*String updateRoomSql =
                    "UPDATE rooms "
                  + "SET status = ? "
                  + "WHERE roomId = ?";

            PreparedStatement updateRoomStmt =
                    conn.prepareStatement(
                            updateRoomSql
                    );

            updateRoomStmt.setString(
                    1,
                    "Ocupado"
            );

            updateRoomStmt.setInt(
                    2,
                    roomId
            );

            updateRoomStmt.executeUpdate();*/

            // ========= CONFIRMAR TRANSACCIÓN =========
            conn.commit();

            JOptionPane.showMessageDialog(
                    null,
                    "Pago realizado correctamente"
            );
            handleClose();

        } catch (Exception ex) {

            try {

                if (conn != null) {
                    conn.rollback();
                }

            } catch (SQLException e) {
                e.printStackTrace();
            }

            ex.printStackTrace();

            JOptionPane.showMessageDialog(
                null,
                "Error al finalizar pago"
            );

        } finally {

            try {
                if (conn != null) {
                    conn.close();
                }

            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

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
            
            // si checkout quedó inválido
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

            Date selectedCheckOut = (Date) spCheckOut.getValue();

            // si el usuario intenta una fecha inválida
            // máximo permitido = checkin + 30 días
            Calendar maxCal = Calendar.getInstance();
            maxCal.setTime(checkInDate);
            maxCal.add(Calendar.DAY_OF_MONTH, 30);

            Date maxCheckOut = maxCal.getTime();

            if (selectedCheckOut.before(minCheckOut)) {
                spCheckOut.setValue(minCheckOut);
            } else if (selectedCheckOut.after(maxCheckOut)) {
                spCheckOut.setValue(maxCheckOut);
            }
        });
    }
}