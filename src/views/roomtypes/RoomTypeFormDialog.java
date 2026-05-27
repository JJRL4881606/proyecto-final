package views.roomtypes;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Dimension;
import java.util.ArrayList;

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
import javax.swing.JSpinner;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import java.util.List;

import components.RoundedButton;
import models.Amenity;
import models.RoomType;
import repository.AmenityRepository;
import utils.ButtonFactory;
import utils.FormUtils;
import utils.UIColors;

@SuppressWarnings("serial")
public class RoomTypeFormDialog extends JDialog {

    private JTextField txtName;
    private JComboBox<String> comboBedType;
    private JSpinner spCapacity;
    private JTextField txtPrice;
    private JCheckBox chkFeatured;
    
    private JTextField txtImagePath;
    private RoundedButton btnSelectImage;
    private JLabel lblPreview;
    private List<JCheckBox> amenitiesChecks = new ArrayList<>();
    private List<Amenity> amenities = new ArrayList<>();
    
    private JTextArea txtDescription;
    private JTextField txtExtraImages;
    private RoundedButton btnExtraImages;

    private JLabel lblNameError;
    private JLabel lblBedTypeError;
    private JLabel lblCapacityError;
    private JLabel lblPriceError;
    private JLabel lblImageError;
    private JLabel lblDescriptionError;
    private JLabel lblExtraImagesError;

    private RoundedButton btnSave;
    private RoundedButton btnCancel;

    private RoomType roomType;

    private boolean saved = false;
    private int fieldWidth = 300;

    public RoomTypeFormDialog(JFrame parent, RoomType roomType) {
        super(parent, true);
        this.roomType = roomType;

        setTitle(roomType == null ? "Agregar tipo de habitación" : "Editar tipo de habitación");
        setSize(450,600);
        setLocationRelativeTo(parent);
        setLayout(new BorderLayout());
        setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
        
        add(createTitlePanel(), BorderLayout.NORTH);
        add(createFormPanel());
        add(createButtonPanel(), BorderLayout.SOUTH);

        loadData();
    }

    private JPanel createTitlePanel(){
        JPanel panel = new JPanel();
        panel.setBackground(UIColors.CARD);
        panel.add(new JLabel("Formulario tipo de habitación"));
        return panel;
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

    private JScrollPane createFormPanel(){
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel,BoxLayout.Y_AXIS));
        panel.setBorder(BorderFactory.createEmptyBorder(15,20,15,20));
        panel.setBackground(UIColors.CARD);

		JScrollPane scroll = new JScrollPane(panel);
		scroll.setBorder(null);
		scroll.setHorizontalScrollBar(null);
		scroll.getVerticalScrollBar().setUnitIncrement(14);

		//NOMBRE
        txtName = FormUtils.createTextField();
        lblNameError = FormUtils.createErrorLabel();
        panel.add(FormUtils.createField("Nombre", txtName, lblNameError, "Ingrese el nombre", fieldWidth));

        //TIPO CAMA
        String[] options = {"Seleccione un tipo", "King Bed", "Queen Bed", "Single Bed" };
        comboBedType = FormUtils.createCombo(options);
        lblBedTypeError = FormUtils.createErrorLabel();
        panel.add(FormUtils.createField("Tipo de cama", comboBedType, lblBedTypeError, "Ingrese el tipo de cama", fieldWidth));
        
        //CAPACIDAD
        int max = 10;
        spCapacity = FormUtils.createNumberField(max);
        lblCapacityError = FormUtils.createErrorLabel();
        panel.add(FormUtils.createField("Capacidad", spCapacity, lblCapacityError, "Ingrese la capacidad", fieldWidth));

        //PRECIO
        txtPrice = FormUtils.createTextField();
        lblPriceError = FormUtils.createErrorLabel();
        panel.add(FormUtils.createField("Precio", txtPrice, lblPriceError, "Ingrese el precio", fieldWidth));
        
        //descripcion
        txtDescription = FormUtils.createTextArea();
        lblDescriptionError = FormUtils.createErrorLabel();
                
        panel.add(FormUtils.createField("Descripción", txtDescription, lblDescriptionError, "Ingrese la descripción", fieldWidth));

        //IMAGEN        
        JLabel lblImageTitle = new JLabel("Imagen");
        lblImageTitle.setAlignmentX(Component.CENTER_ALIGNMENT);

        txtImagePath = FormUtils.createImagePathField();
        lblImageError = FormUtils.createErrorLabel();

        lblPreview = new JLabel();
        lblPreview.setPreferredSize(new Dimension(220,120));
        lblPreview.setAlignmentX(Component.CENTER_ALIGNMENT);
        lblPreview.setVisible(false);

        JPanel imagePanel = new JPanel();
        imagePanel.setOpaque(false);
        imagePanel.setLayout(new BoxLayout(imagePanel, BoxLayout.Y_AXIS));

        btnSelectImage = new RoundedButton(
            "Seleccionar imagen",
            null
        );
        btnSelectImage.setAlignmentX(Component.CENTER_ALIGNMENT);
        
        imagePanel.add(btnSelectImage);
        imagePanel.add(Box.createRigidArea(new Dimension(0,10)));
        imagePanel.add(txtImagePath);
        imagePanel.add(Box.createRigidArea(new Dimension(0,10)));
        imagePanel.add(lblPreview);
        imagePanel.add(Box.createRigidArea(new Dimension(0,10)));
        imagePanel.add(lblImageError);
        
        panel.add(Box.createRigidArea(new Dimension(0,20)));
        panel.add(lblImageTitle);
        panel.add(Box.createRigidArea(new Dimension(0,10)));
        panel.add(imagePanel);
        panel.add(Box.createRigidArea(new Dimension(0,20)));
        
        //Imagenes adicionales
        JLabel lblExtraImagesTitle = new JLabel("Imagenes extra");
        lblExtraImagesTitle.setAlignmentX(Component.CENTER_ALIGNMENT);
        
        btnExtraImages = new RoundedButton(
		    "Seleccionar imágenes extras",
		    null
		);
        btnExtraImages.setAlignmentX(Component.CENTER_ALIGNMENT);

        txtExtraImages = FormUtils.createImagePathField();
        lblExtraImagesError = FormUtils.createErrorLabel();
        
        panel.add(lblExtraImagesTitle);
        panel.add(Box.createRigidArea(new Dimension(0,10)));
        panel.add(btnExtraImages);
        panel.add(Box.createRigidArea(new Dimension(0,10)));
        panel.add(txtExtraImages);
        panel.add(Box.createRigidArea(new Dimension(0,10)));
        panel.add(lblExtraImagesError);
        panel.add(Box.createRigidArea(new Dimension(0,20)));

        //AMENIDADES
        JPanel amenityPanel = new JPanel();
        amenityPanel.setOpaque(false);
        amenityPanel.setLayout(new BoxLayout(amenityPanel, BoxLayout.Y_AXIS));
        
        amenities = new AmenityRepository().getAmenities();

        for(Amenity a:amenities){

            JCheckBox chk = new JCheckBox(a.getName());
            chk.setOpaque(false);
            chk.setAlignmentX(Component.CENTER_ALIGNMENT);

            amenitiesChecks.add(chk);

            amenityPanel.add(chk);
            amenityPanel.add(Box.createRigidArea(new Dimension(0,5)));
        }

        JLabel lblAmenities = new JLabel("Amenidades");
        lblAmenities.setAlignmentX(Component.CENTER_ALIGNMENT);

        panel.add(lblAmenities);
        panel.add(Box.createRigidArea(new Dimension(0,10)));

        panel.add(amenityPanel);
        panel.add(Box.createRigidArea(new Dimension(0,10)));
        
        //destacada o no
        
        JLabel lblFeatured = new JLabel("Destacada");
        lblFeatured.setAlignmentX(Component.CENTER_ALIGNMENT);

        chkFeatured = FormUtils.createCheckBox();
        chkFeatured.setText("Habitación destacada");
        
        panel.add(Box.createRigidArea(new Dimension(0,20)));
        panel.add(lblFeatured);
        panel.add(Box.createRigidArea(new Dimension(0,10)));
        panel.add(chkFeatured);

        return scroll;
    }

    private void loadData(){
        if(roomType != null){
            txtName.setText(roomType.getName());
            comboBedType.setSelectedItem(roomType.getBedType());
            spCapacity.setValue(roomType.getCapacity());
            txtPrice.setText(String.valueOf(roomType.getPrice()));
            
            txtDescription.setText(
        	    roomType.getDescription()
        	);
            
            txtImagePath.setText(roomType.getImagePath());
            if(!roomType.getImagePath().isEmpty()){
                lblPreview.setVisible(true);
            }
            
            txtExtraImages.setText(
        	    roomType.extraImagesToString()
        	);
            
            for(JCheckBox chk:amenitiesChecks) {
                for(Amenity a:roomType.getAmenities()) {
                    if(chk.getText().equals(a.getName())) {
                        chk.setSelected(true);
                    }
                }
            }
            
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
        return (int) spCapacity.getValue();
    }

    public double getPrice(){
        return Double.parseDouble(txtPrice.getText());
    }
    
    public String getDescription(){
        return txtDescription.getText().trim();
    }

    public String getImagePath(){
        return txtImagePath.getText();
    }
    
    public RoundedButton getBtnSelectImage(){
        return btnSelectImage;
    }

    public JLabel getLblPreview(){
        return lblPreview;
    }

    public boolean isFeatured(){
        return chkFeatured.isSelected();
    }
    
    public List<Amenity> getSelectedAmenities(){

        List<Amenity> selected = new ArrayList<>();

        for(int i=0;i<amenitiesChecks.size();i++) {
            if(amenitiesChecks.get(i).isSelected()) {
                selected.add(amenities.get(i)); {
                }
            }
        }

        return selected;
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
    
    public JTextField getTxtName(){
    	return txtName;
    }
    
    public JTextArea getTxtDescription() {
		return txtDescription;
    }
    public JComboBox<String> getComboBedType(){return comboBedType;}
    
	public int getBedTypeIndex() {
	    return comboBedType.getSelectedIndex();
	}
	
	public JTextField getTxtExtraImages(){
	    return txtExtraImages;
	}

    public RoundedButton getBtnExtraImages(){
        return btnExtraImages;
    }

    public JSpinner getSpCapacity(){return spCapacity;}
    public JTextField getTxtPrice(){return txtPrice;}
    public JTextField getTxtImagePath(){return txtImagePath;}

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
    	FormUtils.clearError(lblCapacityError,spCapacity);
    }

    public void clearPriceError(){
    	FormUtils.clearError(lblPriceError,txtPrice);
    }    
    
    public void clearDescriptionError(){
        FormUtils.clearError(lblDescriptionError,txtDescription);
    }
    public void clearImageError(){
    	FormUtils.clearError(lblImageError,txtImagePath);
    }
    
    public void clearExtraImagesError(){
        FormUtils.clearError(lblExtraImagesError,txtExtraImages);
    }

    public void clearErrors(){
    	clearNameError();
    	clearBedTypeError();
    	clearCapacityError();
    	clearPriceError();
    	clearImageError();
        clearDescriptionError();
        clearExtraImagesError();
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
    	spCapacity.setBorder(FormUtils.redBorder);
    }

    public void setPriceError(String msg){
    	lblPriceError.setText(msg);
    	txtPrice.setBorder(FormUtils.redBorder);
    }

    public void setImageError(String msg){
    	lblImageError.setText(msg);
    	txtImagePath.setBorder(FormUtils.redBorder);
    }
    
    public void setDescriptionError(String msg){
        lblDescriptionError.setText(msg);
        txtDescription.setBorder(FormUtils.redBorder);
    }

    public void setExtraImagesError(String msg){
        lblExtraImagesError.setText(msg);
        txtExtraImages.setBorder(FormUtils.redBorder);
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
    
    public JLabel getLblDescriptionError(){
    	return lblDescriptionError;
    }
    
    public JLabel getLblExtraImagesError(){
    	return lblExtraImagesError;
    }
}