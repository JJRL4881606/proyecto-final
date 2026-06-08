package views.reservations;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.sql.Date;
import java.util.List;
import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSpinner;
import javax.swing.JTextField;
import components.RoundedButton;
import models.Reservation;
import models.ReservationStatus;
import models.Room;
import models.RoomStatus;
import models.RoomType;
import models.User;
import repository.RoomRepository;
import repository.RoomTypeRepository;
import repository.UserRepository;
import utils.AppFont;
import utils.ButtonFactory;
import utils.FormUtils;
import utils.UIColors;

@SuppressWarnings("serial")

// Dialog para crear o editar una reservación desde el panel de admin
public class ReservationFormDialog extends JDialog {

    private JComboBox<String> comboUser;
    private JComboBox<String> comboRoom;
    private JSpinner spCheckIn;
    private JSpinner spCheckOut;
    private JSpinner spGuests;
    private JComboBox<String> comboStatus;
    private JTextField txtTotal;

    private JLabel lblUserError;
    private JLabel lblRoomError;
    private JLabel lblCheckInError;
    private JLabel lblCheckOutError;
    private JLabel lblGuestsError;
    private JLabel lblTotalError;
    private JLabel lblStatusError;
    
    //pago
    private JLabel lblPaymentMethodError;
    private JComboBox<String> comboPaymentMethod;
    private JLabel lblTermsError;
    private JLabel lblPoliciesError;
    private JCheckBox chkTerms;
    private JCheckBox chkPolicies;

    private RoundedButton btnSave;
    private RoundedButton btnCancel;

    private List<User> users;
    private List<Room> rooms;

    private Reservation reservation;
    
    private JLabel lblPayment;
    
    // indica si el usuario guardó o cerró sin guardar (lo usa quien abre el diálogo)
    private boolean saved = false;
    
    private final int FIELD_WIDTH = 300;
    
    public ReservationFormDialog(JFrame parent, Reservation reservation) {
        super(parent, true);
        this.reservation = reservation;

        try{
            users = new UserRepository().getUsers();

        }catch(Exception e){
            e.printStackTrace();
        }
        
        // Se incluyen las habitaciones activas + la habitación de la reservación que se está editando
       
        // Obtiene todas las habitaciones, las filtra
        rooms = new RoomRepository().getRooms().stream().filter(room -> {
        		
        	//si está editando una reservación y esta habitación es la que ya tiene asignada la reservación, la incluye aunque no esté activa
	        if(reservation != null && room.getRoomId() == reservation.getRoomId()){
	            return true;
	        }
	        
	        // Para todas las demás habitaciones solo incluir las que estén activas
	        return room.getStatus().equals(
	            RoomStatus.ACTIVE
	        );

	    })
	    .toList();
        
        setTitle(reservation == null ? "Agregar reservación" : "Editar reservación");
        setSize(450, 600);
        setLocationRelativeTo(parent);
        setLayout(new BorderLayout());
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);

        add(createTitlePanel(), BorderLayout.NORTH);
        add(createFormPanel());
        add(createButtonPanel(), BorderLayout.SOUTH);

        loadData();
    }

    private JPanel createTitlePanel() {
        JPanel panel = new JPanel();
        panel.setBackground(UIColors.CARD);
        panel.add(new JLabel("Formulario reservación"));
        return panel;
    }
    
    // Arma el formulario con todos sus campos
    // La sección de pago solo se agrega cuando es una reservación nueva, porque al editar el pago ya existe
    private JScrollPane createFormPanel() {
    	JPanel panel = new JPanel();
        panel.setBackground(UIColors.CARD);
        panel.setAlignmentX(Component.CENTER_ALIGNMENT);
		panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
		panel.setBorder(BorderFactory.createEmptyBorder(15, 20, 15, 20));

		JScrollPane scroll = new JScrollPane(panel);
		scroll.setBorder(null);
		scroll.setHorizontalScrollBar(null);
		scroll.getVerticalScrollBar().setUnitIncrement(14);

        // Construir el arreglo de nombres completos para el combo, índice 0 es el placeholder
        String[] userNames = new String[users.size()+1];
        userNames[0] = "Seleccione un usuario";

        for(int i = 0; i < users.size(); i++){
            userNames[i+1] = users.get(i).getName() + " " + users.get(i).getSurname();
        }       
        
        comboUser = FormUtils.createCombo(userNames);
        lblUserError = FormUtils.createErrorLabel();
        panel.add(FormUtils.createField("Usuario", comboUser, lblUserError, "", FIELD_WIDTH));

        // Mostrar número de habitación + nombre del tipo
        String[] roomNames = new String[rooms.size()+1];
        roomNames[0]="Seleccione una habitación";

        RoomTypeRepository typeRepo = new RoomTypeRepository();
        
        for(int i = 0; i < rooms.size(); i++){
            Room room = rooms.get(i);
            RoomType type = typeRepo.getById(room.getTypeId());
            
            roomNames[i+1] = "Hab. " + room.getRoomNumber() + " - " + type.getName();
        }         
        
        comboRoom = FormUtils.createCombo(roomNames);
        lblRoomError = FormUtils.createErrorLabel();
        panel.add(FormUtils.createField("Habitación", comboRoom, lblRoomError, "", FIELD_WIDTH));

        spCheckIn = FormUtils.createDateField();
        lblCheckInError = FormUtils.createErrorLabel();
        panel.add(FormUtils.createField("Fecha entrada", spCheckIn, lblCheckInError, "", FIELD_WIDTH));

        spCheckOut = FormUtils.createDateField();
        lblCheckOutError = FormUtils.createErrorLabel();
        panel.add(FormUtils.createField("Fecha salida", spCheckOut, lblCheckOutError, "", FIELD_WIDTH));

        spGuests = FormUtils.createNumberField(10);
        lblGuestsError = FormUtils.createErrorLabel();
        panel.add(FormUtils.createField("Huéspedes", spGuests, lblGuestsError, "", FIELD_WIDTH));

        String[] status = {"Seleccione estado", ReservationStatus.CONFIRMED, ReservationStatus.CANCELED, ReservationStatus.COMPLETED };
        comboStatus = FormUtils.createCombo(status);
        lblStatusError = FormUtils.createErrorLabel();
        panel.add(FormUtils.createField("Estado", comboStatus, lblStatusError, "", FIELD_WIDTH));
        		
        txtTotal = FormUtils.createTextField();
        txtTotal.setEditable(false);
        txtTotal.setBackground(new Color(245,245,245));
        lblTotalError = FormUtils.createErrorLabel();
        panel.add(FormUtils.createField("Total", txtTotal, lblTotalError, "Ingrese el total", FIELD_WIDTH));
        
        // La sección de pago solo aparece al crear una nueva reservación
        if(reservation == null) {
            lblPayment = new JLabel("Pago de la reservación");
            lblPayment.setFont(AppFont.big());
            lblPayment.setBorder(BorderFactory.createEmptyBorder(0, 0, 10, 0));
            lblPayment.setAlignmentX(CENTER_ALIGNMENT);
            lblPayment.setHorizontalAlignment(JLabel.CENTER);
            panel.add(lblPayment);

            String[] paymentMethods = {"Selecciona un método de pago", "Tarjeta de crédito", "Tarjeta de débito", "Efectivo", "Transferencia", "PayPal"};
            comboPaymentMethod = FormUtils.createCombo(paymentMethods);
            lblPaymentMethodError = FormUtils.createErrorLabel();
        	panel.add(FormUtils.createField("Método de pago:", comboPaymentMethod, lblPaymentMethodError, "", FIELD_WIDTH));

            chkTerms = new JCheckBox("Acepto los términos y condiciones");
        	chkTerms.setOpaque(false);
            chkTerms.setAlignmentX(Component.CENTER_ALIGNMENT);
            lblTermsError = FormUtils.createErrorLabel();
            panel.add(FormUtils.createField(null, chkTerms, lblTermsError, "", FIELD_WIDTH));

        	chkPolicies = new JCheckBox("Acepto políticas de cancelación");
        	chkPolicies.setOpaque(false);
        	lblPoliciesError = FormUtils.createErrorLabel();
        	chkPolicies.setAlignmentX(Component.CENTER_ALIGNMENT);
            panel.add(FormUtils.createField(null, chkPolicies, lblPoliciesError, "", FIELD_WIDTH));
        }

        return scroll;
    }

	private JPanel createButtonPanel() {
		JPanel panel = new JPanel();
		btnSave = ButtonFactory.createGoldButton("GUARDAR", "/assets/img/btn-icons/button-save-icon.png", "Guardar");
		btnCancel = ButtonFactory.createGoldButton("CANCELAR", "/assets/img/btn-icons/button-cancel-icon.png", "Cancelar");
		panel.add(btnSave);
		panel.add(btnCancel);
		return panel;
	}

    // Rellena los campos con los datos de la reservacion existente,solo se llama al editar
    private void loadData(){

        if(reservation == null){
            return;
        }

        comboUser.setSelectedIndex(findUserIndex(reservation.getUserId()));
        comboRoom.setSelectedIndex(findRoomIndex(reservation.getRoomId()));
        spCheckIn.setValue(Date.valueOf(reservation.getCheckInDate()));
        spCheckOut.setValue(Date.valueOf(reservation.getCheckOutDate()));
        spGuests.setValue(reservation.getGuests());
        comboStatus.setSelectedItem(reservation.getStatus());
        txtTotal.setText(String.valueOf(reservation.getTotal()));
    }
    
    public int confirmCancel(){
        return JOptionPane.showConfirmDialog(
            null,
            "¿Seguro que deseas cancelar?",
            "Confirmar",
            JOptionPane.YES_NO_OPTION
        );
    }

    // Busca la posicion del usuario en la lista para seleccionarlo en el combo (+ 1 por el placeholder)
    private int findUserIndex(int id) {
        for(int i = 0; i < users.size(); i++){
            if(users.get(i).getId() == id){
                return i + 1;
            }
        }
        return 0;
    }

    // Busca la posicion de la habitación en la lista para seleccionarla en el combo (+1 por el placeholder)
    private int findRoomIndex(int id) {
        for(int i = 0; i < rooms.size(); i++){
            if(rooms.get(i).getRoomId() == id){
                return i + 1;
            }
        }
        return 0;
    }

    // Se resta 1 al indice para compensar el placeholder en la posiciion 0
    public int getUserId() {
        return users.get(comboUser.getSelectedIndex()-1).getId();
    }

    public int getRoomId() {
        return rooms.get(comboRoom.getSelectedIndex()-1).getRoomId();
    }

    //getters y setters
    public JSpinner getSpCheckIn() { return spCheckIn; }
    public JSpinner getSpCheckOut() { return spCheckOut; }
    public JSpinner getSpGuests() { return spGuests; }
    public JTextField getTxtTotal() { return txtTotal; }

    public JComboBox<String> getComboStatus() { return comboStatus; }
    
    public JComboBox<String> getComboUser(){
        return comboUser;
    }

    public JComboBox<String> getComboRoom(){
        return comboRoom;
    }
    
    public JComboBox<String> getComboPaymentMethod() { return comboPaymentMethod; }
    public JCheckBox getChkTerms() { return chkTerms; }
    public JCheckBox getChkPolicies() { return chkPolicies; }

    public RoundedButton getBtnSave() { return btnSave; }
    public RoundedButton getBtnCancel() { return btnCancel; }

    public Reservation getReservation() { return reservation; }
    public void setReservation(Reservation reservation) { this.reservation = reservation; }

    public boolean isSaved() { return saved; }
    public void setSaved(boolean saved) { this.saved = saved; }
    
    public String getStatus(){
        return String.valueOf(comboStatus.getSelectedItem());
    }

    public int getGuests(){
        return (int)spGuests.getValue();
    }

    public double getTotal(){
        return Double.parseDouble(txtTotal.getText());
    }
    
    // Limpia todos los mensajes de error del formulario
    //los campos de pago solo se limpian si existen
	public void clearErrors() {
		clearUserError();
		clearRoomError();
		clearGuestsError();
		clearStatusError();
		clearCheckOutError();
		clearCheckInError();
		
		if(chkTerms != null) { clearTermsError(); }
		if(chkPolicies != null) { clearPoliciesError(); }
		if(comboPaymentMethod != null) { clearPaymentMethodError(); }
	}
    
	//setters y clears errores
	
    public void setGuestsError(String msg){
        lblGuestsError.setText(msg);
        spGuests.setBorder(FormUtils.redBorder);
    }

    public void clearGuestsError(){
        FormUtils.clearError(lblGuestsError,spGuests);
    }

    public void setCheckOutError(String msg){
        lblCheckOutError.setText(msg);
        spCheckOut.setBorder(FormUtils.redBorder);
    }

    public void clearCheckOutError(){
        FormUtils.clearError(lblCheckOutError,spCheckOut);
    }

    public void setCheckInError(String msg){
        lblCheckInError.setText(msg);
        spCheckIn.setBorder(FormUtils.redBorder);
    }

    public void clearCheckInError(){
        FormUtils.clearError(lblCheckInError,spCheckIn);
    }
    
    public void setUserError(String msg){
        lblUserError.setText(msg);
        comboUser.setBorder(FormUtils.redBorder);
    }

    public void clearUserError(){
        FormUtils.clearError(lblUserError,comboUser);
    }

    public void setRoomError(String msg){
        lblRoomError.setText(msg);
        comboRoom.setBorder(FormUtils.redBorder);
    }

    public void clearRoomError(){
        FormUtils.clearError(lblRoomError,comboRoom);
    }

    public void setStatusError(String msg){
        lblStatusError.setText(msg);
        comboStatus.setBorder(FormUtils.redBorder);
    }

    public void clearStatusError(){
        FormUtils.clearError(lblStatusError,comboStatus);
    }
    
    public void setPaymentMethodError(String msg){
        lblPaymentMethodError.setText(msg);
        comboPaymentMethod.setBorder(FormUtils.redBorder);
    }

    public void clearPaymentMethodError(){
        FormUtils.clearError(lblPaymentMethodError,comboPaymentMethod);
    }
    
	public void setTermsError(String msg) {
	    lblTermsError.setText(msg);
	}
	
	public void clearTermsError(){
	    lblTermsError.setText("");
	}
	
	public void setPoliciesError(String msg) {
	    lblPoliciesError.setText(msg);
	}

	public void clearPoliciesError(){
	    lblPoliciesError.setText("");
	}
}