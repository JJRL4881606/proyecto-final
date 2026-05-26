package views.amenities;

import java.awt.*;
import javax.swing.*;

import components.RoundedButton;
import models.Amenity;
import utils.*;

@SuppressWarnings("serial")
public class AmenitiesFormDialog extends JDialog{

    private JTextField txtName;
    private JTextField txtIcon;

    private JLabel lblNameError;
    private JLabel lblIconError;

    private JLabel preview;

    private RoundedButton btnSave;
    private RoundedButton btnCancel;
    private RoundedButton btnSelectIcon;

    private Amenity amenity;

    private boolean saved = false;
    private int fieldWidth = 300;

    public AmenitiesFormDialog(JFrame parent, Amenity amenity){
        super(parent,true);
        this.amenity = amenity;

        setTitle(amenity == null ? "Agregar amenidad" : "Editar amenidad");
        setSize(450,500);
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
        panel.add(new JLabel("Formulario amenidad"));
        return panel;
    }
    
    private JPanel createFormPanel(){
    	JPanel panel = new JPanel();
        panel.setBackground(UIColors.CARD);
        panel.setAlignmentX(Component.CENTER_ALIGNMENT);
		panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
		panel.setBorder(BorderFactory.createEmptyBorder(15, 20, 15, 20));

		JScrollPane scroll = new JScrollPane(panel);
		scroll.setBorder(null);
		scroll.setHorizontalScrollBar(null);
		scroll.getVerticalScrollBar().setUnitIncrement(14);
		
        //nombre
        txtName = FormUtils.createTextField();
        lblNameError = FormUtils.createErrorLabel();
        panel.add(FormUtils.createField("Nombre", txtName, lblNameError, "Ingrese el nombre", fieldWidth));

        //icono
        JLabel lblImageTitle = new JLabel("Imagen");
        lblImageTitle.setAlignmentX(Component.CENTER_ALIGNMENT);

        txtIcon = FormUtils.createImagePathField();
        lblIconError = FormUtils.createErrorLabel();

        btnSelectIcon = new RoundedButton("Seleccionar icono", null);
        btnSelectIcon.setAlignmentX(Component.CENTER_ALIGNMENT);

        preview = new JLabel();
        preview.setPreferredSize(new Dimension(220, 120));
        preview.setAlignmentX(Component.CENTER_ALIGNMENT);
        preview.setVisible(false);
                
        panel.add(Box.createRigidArea(new Dimension(0,10)));
        panel.add(lblImageTitle);
        panel.add(Box.createRigidArea(new Dimension(0,10)));
		panel.add(btnSelectIcon);
		panel.add(Box.createRigidArea(new Dimension(0,10)));
		panel.add(txtIcon);
        panel.add(Box.createRigidArea(new Dimension(0,10)));
        panel.add(preview);
        panel.add(Box.createRigidArea(new Dimension(0,10)));
        panel.add(lblIconError);

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

    private void loadData(){

        if(amenity == null) {
        	return;
        }

        txtName.setText(
            amenity.getName()
        );

        txtIcon.setText(
            amenity.getIcon()
        );
    }

    public int confirmCancel(){
        return JOptionPane.showConfirmDialog(
            null,
            "¿Cancelar?"
        );
    }

    public String getName(){
        return txtName.getText().trim();
    }

    public String getIcon(){
        return txtIcon.getText();
    }

    public JTextField getTxtName(){return txtName;}
    public JTextField getTxtIcon(){return txtIcon;}

    public JLabel getLblNameError(){return lblNameError;}

    public RoundedButton getBtnSave(){return btnSave;}
    public RoundedButton getBtnCancel(){return btnCancel;}
    public RoundedButton getBtnSelectIcon(){return btnSelectIcon;}

    public JLabel getPreview(){return preview;}

    public void setSaved(boolean b){saved=b;}
    public boolean isSaved(){return saved;}

    public Amenity getAmenity(){return amenity;}
    public void setAmenity(Amenity a){amenity=a;}

    public void setNameError(String s){
        lblNameError.setText(s);
    }

    public void clearNameError(){
        lblNameError.setText("");
    }

    public void setIconError(String s){
        lblIconError.setText(s);
    }

    public void clearIconError(){
        lblIconError.setText("");
    }
}