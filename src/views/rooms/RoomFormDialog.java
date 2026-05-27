package views.rooms;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Dimension;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSpinner;

import components.RoundedButton;
import models.Room;
import models.RoomStatus;
import utils.ButtonFactory;
import utils.FormUtils;
import utils.UIColors;

@SuppressWarnings("serial")
public class RoomFormDialog extends JDialog {

    private JSpinner spRoomNumber;
    private JSpinner spFloor;
    private JComboBox<String> comboRoomType;
    private JComboBox<String> comboStatus;
    
    private JLabel lblRoomNumberError;
    private JLabel lblFloorError;
    private JLabel lblRoomTypeError;
    private JLabel lblStatusError;

    private RoundedButton btnSave;
    private RoundedButton btnCancel;

    private Room room;

    private boolean saved = false;
    private int fieldWidth = 300;

    public RoomFormDialog(JFrame parent, Room room) {

        super(parent,true);
        this.room = room;

        setTitle(room == null ? "Agregar habitación" : "Editar habitación");
        setSize(450,450);
        setLocationRelativeTo(parent);
        setLayout(new BorderLayout());
        setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);

        add(createTitlePanel(),BorderLayout.NORTH);
        add(createFormPanel());
        add(createButtonPanel(),BorderLayout.SOUTH);

        loadData();
    }

    private JPanel createTitlePanel(){
        JPanel panel = new JPanel();
        panel.setBackground(UIColors.CARD);
        panel.add(new JLabel("Formulario habitación"));
        return panel;
    }

    private JScrollPane createFormPanel(){
    	JPanel panel = new JPanel();
        panel.setBackground(UIColors.CARD);
        panel.setAlignmentX(Component.CENTER_ALIGNMENT);
		panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
		panel.setBorder(BorderFactory.createEmptyBorder(15, 20, 15, 20));

		JScrollPane scroll = new JScrollPane(panel);
		scroll.setBorder(null);
		scroll.setHorizontalScrollBar(null);
		scroll.getVerticalScrollBar().setUnitIncrement(14);

        //NUMERO HABITACION
        spRoomNumber = FormUtils.createNumberField(9999);
        lblRoomNumberError = FormUtils.createErrorLabel();
        panel.add(FormUtils.createField("Número habitación", spRoomNumber, lblRoomNumberError, "Ingrese número", fieldWidth));

        //PISO
        spFloor = FormUtils.createNumberField(50);
        lblFloorError = FormUtils.createErrorLabel();
        panel.add(FormUtils.createField("Piso", spFloor, lblFloorError, "Ingrese piso", fieldWidth));

        //TIPO HABITACION
        String[] options = {"Seleccione tipo"};
        comboRoomType = FormUtils.createCombo(options);
        lblRoomTypeError = FormUtils.createErrorLabel();
        panel.add(FormUtils.createField("Tipo habitación", comboRoomType, lblRoomTypeError, "Seleccione tipo", fieldWidth));

        //DISPONIBLE
        String[] status = {"Seleccione estado", RoomStatus.AVAILABLE, RoomStatus.OCCUPIED, RoomStatus.OUT_OF_SERVICE};
        comboStatus = FormUtils.createCombo(status);
        lblStatusError = FormUtils.createErrorLabel();
        panel.add(FormUtils.createField( "Estado", comboStatus, lblStatusError, "Seleccione estado", fieldWidth));      
        
        panel.add(Box.createRigidArea(new Dimension(0,20)));

        return scroll;
    }
    
    private JPanel createButtonPanel(){
        JPanel panel = new JPanel();
        panel.setBackground(UIColors.CARD);

        btnSave = ButtonFactory.createGoldButton(
            "GUARDAR",
            "/assets/img/btn-icons/button-save-icon.png",
            "Haz clic para guardar"
        );

        btnCancel = ButtonFactory.createGoldButton(
            "CANCELAR",
            "/assets/img/btn-icons/button-cancel-icon.png",
            "Haz clic para cancelar"
        );

        panel.add(btnSave);
        panel.add(btnCancel);

        return panel;
    }

    private void loadData(){
        if(room != null){
            spRoomNumber.setValue(room.getRoomNumber());
            spFloor.setValue(room.getFloor());
            comboStatus.setSelectedItem(room.getStatus());
        }
    }

    public int confirmCancel(){
        return JOptionPane.showConfirmDialog(
                null,
                "¿Seguro que deseas cancelar?",
                "Confirmar",
                JOptionPane.YES_NO_OPTION
        );
    }

    //GETTERS
    public int getRoomNumber(){
        return (int)spRoomNumber.getValue();
    }

    public int getFloor(){
        return (int)spFloor.getValue();
    }

    public int getTypeId(){
        return comboRoomType.getSelectedIndex();
    }

    public String getStatus(){
        return comboStatus.getSelectedItem().toString();
    }
    
    public JComboBox<String> getComboStatus(){
        return comboStatus;
    }
    
    public RoundedButton getBtnSave(){
        return btnSave;
    }

    public RoundedButton getBtnCancel(){
        return btnCancel;
    }

    public boolean isSaved(){
        return saved;
    }

    public void setSaved(boolean saved){
        this.saved = saved;
    }

    public Room getRoom(){
        return room;
    }

    public void setRoom(Room room){
        this.room = room;
    }

    public JComboBox<String> getComboRoomType(){
        return comboRoomType;
    }

    public JSpinner getSpRoomNumber(){
        return spRoomNumber;
    }

    public JSpinner getSpFloor(){
        return spFloor;
    }

    //ERRORES
    public void clearRoomNumberError(){
        FormUtils.clearError(lblRoomNumberError,spRoomNumber);
    }

    public void clearFloorError(){
        FormUtils.clearError(lblFloorError,spFloor);
    }

    public void clearRoomTypeError(){
        FormUtils.clearError(lblRoomTypeError,comboRoomType);
    }
    
    public void clearStatusError(){
        FormUtils.clearError(lblStatusError,comboStatus);
    }

    public void clearErrors(){
        clearRoomNumberError();
        clearFloorError();
        clearRoomTypeError();
        clearStatusError();
    }

    public void setRoomNumberError(String msg){
        lblRoomNumberError.setText(msg);
        spRoomNumber.setBorder(FormUtils.redBorder);
    }

    public void setFloorError(String msg){
        lblFloorError.setText(msg);
        spFloor.setBorder(FormUtils.redBorder);
    }

    public void setRoomTypeError(String msg){
        lblRoomTypeError.setText(msg);
        comboRoomType.setBorder(FormUtils.redBorder);
    }
    
    public void setStatusError(String msg){
    	lblStatusError.setText(msg);
    	comboStatus.setBorder(FormUtils.redBorder);
    }
}