package views.booking;

import java.awt.BorderLayout;
import java.awt.Color;
import java.sql.Date;
import java.util.List;
import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
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
import utils.ButtonFactory;
import utils.FormUtils;
import utils.UIColors;

@SuppressWarnings("serial")
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

    private RoundedButton btnSave;
    private RoundedButton btnCancel;

    private List<User> users;
    private List<Room> rooms;

    private Reservation reservation;
    private boolean saved = false;
    private int fieldWidth = 300;

    public ReservationFormDialog(JFrame parent, Reservation reservation) {
        super(parent, true);
        this.reservation = reservation;

        try{
            users=new UserRepository().getUsers();

        }catch(Exception e){
            e.printStackTrace();
        }
        
        rooms = new RoomRepository()
        	    .getRooms()
        	    .stream()
        	    .filter(room -> {

        	        if(reservation != null &&
        	           room.getRoomId() == reservation.getRoomId()){
        	            return true;
        	        }

        	        return room.getStatus().equals(
        	            RoomStatus.AVAILABLE
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

    private JPanel createButtonPanel() {
        JPanel panel = new JPanel();
        panel.setBackground(UIColors.CARD);

        btnSave = ButtonFactory.createGoldButton("GUARDAR", "/assets/img/btn-icons/button-save-icon.png", "");
        btnCancel = ButtonFactory.createGoldButton("CANCELAR", "/assets/img/btn-icons/button-cancel-icon.png", "");

        panel.add(btnSave);
        panel.add(btnCancel);
        return panel;
    }

    private JScrollPane createFormPanel() {
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel,BoxLayout.Y_AXIS));
        panel.setBorder(BorderFactory.createEmptyBorder(15,20,15,20));
        panel.setBackground(UIColors.CARD);

        JScrollPane scroll = new JScrollPane(panel);
        scroll.setBorder(null);
        scroll.setHorizontalScrollBar(null);
        scroll.getVerticalScrollBar().setUnitIncrement(14);

        String[] userNames = new String[users.size()+1];
        userNames[0] = "Seleccione un usuario";

        for(int i = 0; i < users.size(); i++){
            userNames[i+1] = users.get(i).getName() + " " + users.get(i).getSurname();
        }       
        
        comboUser = FormUtils.createCombo(userNames);
        lblUserError = FormUtils.createErrorLabel();
        panel.add(FormUtils.createField("Usuario", comboUser, lblUserError, "", fieldWidth));

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
        panel.add(FormUtils.createField("Habitación", comboRoom, lblRoomError, "", fieldWidth));

        spCheckIn = FormUtils.createDateField();
        lblCheckInError = FormUtils.createErrorLabel();
        panel.add(FormUtils.createField("Fecha entrada", spCheckIn, lblCheckInError, "", fieldWidth));

        spCheckOut = FormUtils.createDateField();
        lblCheckOutError = FormUtils.createErrorLabel();
        panel.add(FormUtils.createField("Fecha salida", spCheckOut, lblCheckOutError, "", fieldWidth));

        spGuests = FormUtils.createNumberField(10);
        lblGuestsError = FormUtils.createErrorLabel();
        panel.add(FormUtils.createField("Huéspedes", spGuests, lblGuestsError, "", fieldWidth));

        String[] status = {"Seleccione estado", ReservationStatus.PENDING, ReservationStatus.CONFIRMED, ReservationStatus.CANCELED, ReservationStatus.COMPLETED };
        comboStatus = FormUtils.createCombo(status);
        lblStatusError = FormUtils.createErrorLabel();
        panel.add(FormUtils.createField("Estado", comboStatus, lblStatusError, "", fieldWidth));
        		
        txtTotal = FormUtils.createTextField();
        txtTotal.setEditable(false);
        txtTotal.setBackground(new Color(245,245,245));
        lblTotalError = FormUtils.createErrorLabel();
        panel.add(FormUtils.createField("Total", txtTotal, lblTotalError, "Ingrese el total", fieldWidth));

        return scroll;
    }

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

    private int findUserIndex(int id) {
        for(int i = 0; i < users.size(); i++){
            if(users.get(i).getId() == id){
                return i + 1;
            }
        }
        return 0;
    }

    private int findRoomIndex(int id) {
        for(int i = 0; i < rooms.size(); i++){
            if(rooms.get(i).getRoomId() == id){
                return i + 1;
            }
        }
        return 0;
    }

    public int getUserId() {
        return users.get(comboUser.getSelectedIndex()-1).getId();
    }

    public int getRoomId() {
        return rooms.get(comboRoom.getSelectedIndex()-1).getRoomId();
    }

    public JSpinner getSpCheckIn() { return spCheckIn; }
    public JSpinner getSpCheckOut() { return spCheckOut; }
    public JSpinner getSpGuests() { return spGuests; }
    public JTextField getTxtTotal() { return txtTotal; }

    public JComboBox<String> getComboStatus() { return comboStatus; }

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

    //errores
    public JLabel getLblTotalError(){
        return lblTotalError;
    }
    
    public void setGuestsError(String msg){
        lblGuestsError.setText(msg);
        spGuests.setBorder(FormUtils.redBorder);
    }

    public void clearGuestsError(){
        FormUtils.clearError(lblGuestsError,spGuests);
    }

    public void setTotalError(String msg){
        lblTotalError.setText(msg);
        txtTotal.setBorder(FormUtils.redBorder);
    }

    public void clearTotalError(){
        FormUtils.clearError(lblTotalError,txtTotal);
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
    
    
    public int confirmCancel(){
        return JOptionPane.showConfirmDialog(
            null,
            "¿Seguro que deseas cancelar?",
            "Confirmar",
            JOptionPane.YES_NO_OPTION
        );
    }
    
    
    public JComboBox<String> getComboUser(){
        return comboUser;
    }

    public JComboBox<String> getComboRoom(){
        return comboRoom;
    }
    
    
}