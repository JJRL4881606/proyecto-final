package views;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.util.Arrays;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import java.util.List;

import components.RoundedButton;
import models.RoomType;
import utils.ButtonFactory;
import utils.FormUtils;
import utils.UIColors;

@SuppressWarnings("serial")
public class RoomTypeFormDialog extends JDialog {

    private JTextField txtName;
    private JComboBox<String> comboBedType;
    private JTextField txtCapacity;
    private JTextField txtPrice;
    private JTextField txtImagePath;
    private JTextField txtFeatures;

    private JCheckBox chkFeatured;

    private RoundedButton btnSave;
    private RoundedButton btnCancel;

    private RoomType roomType;
    private boolean saved = false;

    private JLabel lblNameError;
    private JLabel lblBedTypeError;
    private JLabel lblCapacityError;
    private JLabel lblPriceError;
    private JLabel lblImageError;
    private JLabel lblFeaturesError;
    
    int fieldWidth = 300;

    public RoomTypeFormDialog(JFrame parent, RoomType roomType) {
        super(parent, true);
        this.roomType = roomType;

        setTitle(roomType == null ? "Agregar tipo de habitación" : "Editar tipo de habitación");
        setSize(450,600);
        setLocationRelativeTo(parent);
        setLayout(new BorderLayout());
        add(createTitlePanel(),BorderLayout.NORTH);
        add(createFormPanel());
        add(createButtonPanel(),BorderLayout.SOUTH);

        loadData();
    }

    private JPanel createTitlePanel(){
        JPanel panel=new JPanel();
        panel.setBackground(UIColors.CARD);
        panel.add(new JLabel("Formulario tipo de habitación"));
        return panel;
    }

    private JPanel createButtonPanel(){
        JPanel panel=new JPanel();
        panel.setBackground(UIColors.CARD);

        btnSave = ButtonFactory.createBigButton(
            "GUARDAR",
            "/assets/img/btn-icons/button-save-icon.png",
            ""
        );

        btnCancel = ButtonFactory.createBigButton(
            "CANCELAR",
            "/assets/img/btn-icons/button-cancel-icon.png",
            ""
        );

        panel.add(btnSave);
        panel.add(btnCancel);

        return panel;
    }

    private JScrollPane createFormPanel(){
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel,BoxLayout.Y_AXIS));
        panel.setBorder(BorderFactory.createEmptyBorder(15,20,15,20));
        panel.setBackground(UIColors.CARD);

        JScrollPane scroll = new JScrollPane(panel);
        scroll.setBorder(null);

        //NOMBRE
        txtName = FormUtils.createTextField();
        lblNameError = FormUtils.createErrorLabel();
        panel.add(FormUtils.createField("Nombre", txtName, lblNameError, "Ingrese el nombre", fieldWidth));

        //TIPO CAMA
        comboBedType = new JComboBox<>(new String[]{"Seleccione un tipo", "King Bed", "Queen Bed", "Single Bed" });
        lblBedTypeError = FormUtils.createErrorLabel();
        panel.add(FormUtils.createField("Tipo de cama", comboBedType, lblBedTypeError, "Ingrese el ipo de cama", fieldWidth));
        
        //CAPACIDAD
        txtCapacity = FormUtils.createTextField();
        lblCapacityError = FormUtils.createErrorLabel();
        panel.add(FormUtils.createField("Capacidad", txtCapacity, lblCapacityError, "Ingrese la capacidad", fieldWidth));

        //PRECIO
        txtPrice = FormUtils.createTextField();
        lblPriceError = FormUtils.createErrorLabel();
        panel.add(FormUtils.createField("Precio", txtPrice, lblPriceError, "Ingrese el precio", fieldWidth));

        //IMAGEN
        txtImagePath = FormUtils.createTextField();
        lblImageError = FormUtils.createErrorLabel();
        panel.add(FormUtils.createField("Ruta imagen", txtImagePath, lblImageError, "Ingrese la ruta de imagen", fieldWidth));

        //FEATURES
        txtFeatures = FormUtils.createTextField();
        lblFeaturesError = FormUtils.createErrorLabel();
        panel.add(FormUtils.createField("Comodidades", txtFeatures, lblFeaturesError, "Ingrese comodidades (Wifi|TV|Jacuzzi|etc)", fieldWidth));

        chkFeatured = FormUtils.createCheckBox();
        chkFeatured.setText("Habitación destacada");

        panel.add(chkFeatured);
        panel.add(Box.createRigidArea(new Dimension(0,20)));

        return scroll;
    }

    private void loadData(){
        if(roomType!=null){
            txtName.setText(roomType.getName());
            comboBedType.setSelectedItem(roomType.getBedType());
            txtCapacity.setText(String.valueOf(roomType.getCapacity()));
            txtPrice.setText(String.valueOf(roomType.getPrice()));
            txtImagePath.setText(roomType.getImagePath());
            txtFeatures.setText(roomType.featuresToString());
            chkFeatured.setSelected(roomType.isFeatured());
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
    public String getName(){
        return txtName.getText().trim();
    }

    public String getBedType(){
        return String.valueOf(comboBedType.getSelectedItem());
    }

    public int getCapacity(){
        return Integer.parseInt(txtCapacity.getText());
    }

    public double getPrice(){
        return Double.parseDouble(txtPrice.getText());
    }

    public String getImagePath(){
        return txtImagePath.getText();
    }

    public boolean isFeatured(){
        return chkFeatured.isSelected();
    }

    public List<String> getFeatures(){
        return Arrays.asList(
                txtFeatures
                .getText()
                .split("\\|")
        );
    }

    public RoundedButton getBtnSave() {
        return btnSave;
    }

    public RoundedButton getBtnCancel() {
        return btnCancel;
    }

    public boolean isSaved() {
        return saved;
    }

    public void setSaved(boolean saved) {
        this.saved = saved;
    }

    public RoomType getRoomType() {
        return roomType;
    }
    
    public JTextField getTxtName(){return txtName;}
    public JComboBox<String> getComboBedType(){return comboBedType;}
    
	public int getBedTypeIndex() {
	    return comboBedType.getSelectedIndex();
	}

    public JTextField getTxtCapacity(){return txtCapacity;}
    public JTextField getTxtPrice(){return txtPrice;}
    public JTextField getTxtImagePath(){return txtImagePath;}
    public JTextField getTxtFeatures(){return txtFeatures;}

    public void setRoomType(RoomType roomType){
    	this.roomType=roomType;
    }
    
  //LIMPIAR ERRORES
    public void clearNameError(){
    	FormUtils.clearError(lblNameError,txtName);
    }

    public void clearBedTypeError(){
    	FormUtils.clearError(lblBedTypeError,comboBedType);
    }

    public void clearCapacityError(){
    	FormUtils.clearError(lblCapacityError,txtCapacity);
    }

    public void clearPriceError(){
    	FormUtils.clearError(lblPriceError,txtPrice);
    }

    public void clearImageError(){
    	FormUtils.clearError(lblImageError,txtImagePath);
    }

    public void clearFeaturesError(){
    	FormUtils.clearError(lblFeaturesError,txtFeatures);
    }

    public void clearErrors(){
    	clearNameError();
    	clearBedTypeError();
    	clearCapacityError();
    	clearPriceError();
    	clearImageError();
    	clearFeaturesError();
    }
    
  //SETTERS ERRORES
    public void setNameError(String msg){
    	lblNameError.setText(msg);
    	txtName.setBorder(FormUtils.redBorder);
    }

    public void setBedTypeError(String msg){
    	lblBedTypeError.setText(msg);
    	comboBedType.setBorder(FormUtils.redBorder);
    }

    public void setCapacityError(String msg){
    	lblCapacityError.setText(msg);
    	txtCapacity.setBorder(FormUtils.redBorder);
    }

    public void setPriceError(String msg){
    	lblPriceError.setText(msg);
    	txtPrice.setBorder(FormUtils.redBorder);
    }

    public void setImageError(String msg){
    	lblImageError.setText(msg);
    	txtImagePath.setBorder(FormUtils.redBorder);
    }

    public void setFeaturesError(String msg){
    	lblFeaturesError.setText(msg);
    	txtFeatures.setBorder(FormUtils.redBorder);
    }
    
    public JLabel getLblNameError(){
    	return lblNameError;
    }

    public JLabel getLblCapacityError(){
    	return lblCapacityError;
    }

    public JLabel getLblPriceError(){
    	return lblPriceError;
    }

    public JLabel getLblImageError(){
    	return lblImageError;
    }

    public JLabel getLblFeaturesError(){
    	return lblFeaturesError;
    }
}