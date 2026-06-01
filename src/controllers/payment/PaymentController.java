package controllers.payment;

import java.awt.Point;
import java.awt.Window;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.Calendar;
import java.util.Date;
import java.sql.Statement;

import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

import config.DatabaseConnection;
import models.User;
import views.auth.LoginWindow;
import views.main.MainView;
import views.main.MainWindow;
import views.payment.PaymentView;
import views.payment.PaymentWindow;

public class PaymentController {

    private PaymentWindow paymentWindow;
    private PaymentView paymentView;
    private long estancia ;
    private User user;

    public PaymentController(PaymentWindow paymentWindow, PaymentView paymentView, User user) {
	    this.paymentWindow = paymentWindow;
	    this.paymentView = paymentView;
	    this.user = user;

	    estancia = ( ((Date) paymentView.getSpCheckOut().getValue()).getTime() 
	            - ((Date) paymentView.getSpCheckIn().getValue()).getTime() )
	            / (1000 * 60 * 60 * 24);
	    
        initListeners();
        setupDateValidation();
    }
    
	public void initListeners( ) {
		paymentView.getBtnPay().addActionListener(e -> processPayment());
		
		//paymentView.getLogOut().addActionListener(e -> handleClose());
		
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
	}
	
	
	protected void updateLabelCheckIn() {
		paymentView.getLblCheckIn().setText("Entrada: " + new java.text.SimpleDateFormat("dd/MM/yyyy").format(paymentView.getSpCheckIn().getValue()));
		estancia = ( ((Date) paymentView.getSpCheckOut().getValue()).getTime() 
	            - ((Date) paymentView.getSpCheckIn().getValue()).getTime() )
	            / (1000 * 60 * 60 * 24);
		paymentView.getLblNights().setText("Estancia: " + estancia + " noche/s");
		paymentView.getLblTotal().setText("$ " + (estancia * paymentView.getRoom().getPrice()));
	}
	
	protected void updateLabelCheckOut() {
		paymentView.getLblCheckOut().setText("Salida: " + new java.text.SimpleDateFormat("dd/MM/yyyy").format(paymentView.getSpCheckOut().getValue()));
		estancia = ( ((Date) paymentView.getSpCheckOut().getValue()).getTime() 
	            - ((Date) paymentView.getSpCheckIn().getValue()).getTime() )
	            / (1000 * 60 * 60 * 24);
		paymentView.getLblNights().setText("Estancia: " + estancia + " noche/s");
		paymentView.getLblTotal().setText("Total: $ " + (estancia * paymentView.getRoom().getPrice()));
	}

	private void handleClose() {
		new LoginWindow(user);
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

        boolean termsAccepted =
                paymentView.getChkTerms().isSelected();

        boolean policiesAccepted =
                paymentView.getChkPolicies().isSelected();

        // Validación básica
        if (name.isEmpty() ||
                lastName.isEmpty() ||
                email.isEmpty() ||
                phone.isEmpty() ||
                cmbPaymentMethodSelected == 0) {

            JOptionPane.showMessageDialog(
                    paymentWindow,
                    "Completa todos los campos",
                    "Error",
                    JOptionPane.ERROR_MESSAGE
            );
            return;
        }

        if (!termsAccepted || !policiesAccepted) {

            JOptionPane.showMessageDialog(
                    paymentWindow,
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
                        paymentView,
                        "Seleccione un método de pago"
                );

                return;
            }

            // ========= OBTENER DATOS =========
            int userId =
                    user.getId();

            int selectedTypeId =
                    paymentView
                            .getRoom()
                            .getTypeId();

            double total =
                    estancia *
                    paymentView
                            .getRoom()
                            .getPrice();
            
            // ========= BUSCAR HABITACIÓN DISPONIBLE =========
            int roomId = -1;

            String roomSql =
                    "SELECT roomId "
                  + "FROM rooms "
                  + "WHERE typeId = ? "
                  + "AND status = 'Disponible' "
                  + "LIMIT 1";

            PreparedStatement roomStmt =
                    conn.prepareStatement(
                            roomSql
                    );

            roomStmt.setInt(
                    1,
                    selectedTypeId
            );

            ResultSet roomRs =
                    roomStmt.executeQuery();

            if (roomRs.next()) {

                roomId =
                        roomRs.getInt(
                                "roomId"
                        );

            } else {

                JOptionPane.showMessageDialog(
                        paymentView,
                        "No hay habitaciones disponibles"
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
                    new java.sql.Date(
                            ((Date)
                                    paymentView
                                            .getSpCheckIn()
                                            .getValue())
                                    .getTime()
                    )
            );

            reservationStmt.setDate(
                    4,
                    new java.sql.Date(
                            ((Date)
                                    paymentView
                                            .getSpCheckOut()
                                            .getValue())
                                    .getTime()
                    )
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
                    "Confirmada"
            );

            reservationStmt.executeUpdate();

            // ========= OBTENER reservationId =========
            ResultSet generatedKeys =
                    reservationStmt
                            .getGeneratedKeys();

            int reservationId = -1;

            if (generatedKeys.next()) {

                reservationId =
                        generatedKeys.getInt(
                                1
                        );
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
                    String.valueOf(
                            paymentView
                                    .getCmbPaymentMethod()
                                    .getSelectedItem()
                    )
            );

            paymentStmt.setDate(
                    4,
                    java.sql.Date.valueOf(
                            LocalDate.now()
                    )
            );

            paymentStmt.executeUpdate();

            // ========= ACTUALIZAR HABITACIÓN =========
            String updateRoomSql =
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

            updateRoomStmt.executeUpdate();

            // ========= CONFIRMAR TRANSACCIÓN =========
            conn.commit();

            JOptionPane.showMessageDialog(
                    paymentView,
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
                    paymentView,
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

            Date checkInDate =
                    (Date) spCheckIn.getValue();

            Calendar cal = Calendar.getInstance();
            cal.setTime(checkInDate);

            // mínimo checkout = +1 día
            cal.add(Calendar.DAY_OF_MONTH, 1);

            Date minCheckOut = cal.getTime();

            SpinnerDateModel checkOutModel =
                    (SpinnerDateModel)
                            spCheckOut.getModel();

            // actualizar mínimo permitido
            checkOutModel.setStart(minCheckOut);

            // si checkout quedó inválido
            Date currentCheckOut =
                    (Date) spCheckOut.getValue();

            if (currentCheckOut.before(minCheckOut)) {
                spCheckOut.setValue(minCheckOut);
            }
        });


        // ================= CHECK OUT =================
        spCheckOut.addChangeListener(e -> {

            Date checkInDate =
                    (Date) spCheckIn.getValue();

            Calendar cal = Calendar.getInstance();
            cal.setTime(checkInDate);

            // mínimo permitido
            cal.add(Calendar.DAY_OF_MONTH, 1);

            Date minCheckOut = cal.getTime();

            Date selectedCheckOut =
                    (Date) spCheckOut.getValue();

            // si el usuario intenta una fecha inválida
            if (selectedCheckOut.before(minCheckOut)) {
                spCheckOut.setValue(minCheckOut);
            }
        });
    }
}