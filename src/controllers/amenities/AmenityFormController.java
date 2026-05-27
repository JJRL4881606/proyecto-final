package controllers.amenities;

import javax.swing.*;
import javax.swing.event.*;
import javax.swing.filechooser.FileNameExtensionFilter;

import config.Config;
import models.Amenity;
import utils.FormUtils;
import utils.Validator;
import views.amenities.AmenitiesFormDialog;

import java.awt.Image;
import java.io.File;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;

public class AmenityFormController {

    private AmenitiesFormDialog view;

    public AmenityFormController(AmenitiesFormDialog view){
        this.view = view;

        initListeners();
        initRestrictions();
    }

    private void initListeners(){
        view.getBtnSave().addActionListener(e->handleSave());
        view.getBtnCancel().addActionListener(e->handleCancel());
        view.getBtnSelectIcon().addActionListener(e->selectIcon());

		view.getTxtName().getDocument().addDocumentListener(new DocumentListener(){
			public void insertUpdate(DocumentEvent e){validateName();}
			public void removeUpdate(DocumentEvent e){validateName();}
			public void changedUpdate(DocumentEvent e){validateName();}
		});

        FormUtils.addFocusEffect(view.getTxtName(),view.getLblNameError());
    }

    private void initRestrictions(){
        Validator.onlyLetters(view.getTxtName());
    }

    private void handleSave(){
        if(!validateForm()) {
        	return;
        }

        Amenity amenity = view.getAmenity();

        if(amenity == null){
            amenity = new Amenity(
                0,
                view.getName(),
                view.getIcon()
            );
        } else{
            amenity.setName(view.getName());
            amenity.setIcon(view.getIcon());
        }

        view.setAmenity(amenity);
        view.setSaved(true);

        view.dispose();
    }

    private void handleCancel(){
        if(view.confirmCancel() == JOptionPane.YES_OPTION){
            view.dispose();
        }
    }

    private void selectIcon(){
		String lastFolder = Config.get(
            "room.image.folder",
            System.getProperty("user.home")
        );

		JFileChooser chooser = new JFileChooser(lastFolder);
		
	    chooser.setAcceptAllFileFilterUsed(false);
	    chooser.setFileFilter(
	        new FileNameExtensionFilter(
	            "Imágenes (*.png, *.jpg, *.jpeg)",
	            "png",
	            "jpg",
	            "jpeg"
	        )
	    );

	    int option = chooser.showOpenDialog(null);

	    if(option != JFileChooser.APPROVE_OPTION){
	        return;
	    }

        try{
	        File selected = chooser.getSelectedFile();
	        
	        Config.set(
        	    "room.image.folder",
        	    selected.getParent()
        	);
	        
	        String fileName = selected.getName() + "_" + System.currentTimeMillis();

            File srcFolder = new File("src/assets/img/icons");
            File binFolder = new File("bin/assets/img/icons");

	        srcFolder.mkdirs();
	        binFolder.mkdirs();

	        File srcDestination = new File(srcFolder,fileName);
	        File binDestination = new File(binFolder,fileName);

	        Files.copy(
	                selected.toPath(),
	                srcDestination.toPath(),
	                StandardCopyOption.REPLACE_EXISTING
	        );

	        Files.copy(
	                selected.toPath(),
	                binDestination.toPath(),
	                StandardCopyOption.REPLACE_EXISTING
	        );

            String dbPath = "/assets/img/icons/" + fileName;
            view.getTxtIcon().setText(dbPath);

	        ImageIcon icon = new ImageIcon(srcDestination.getAbsolutePath());
	        Image image = icon.getImage().getScaledInstance(80, 80, Image.SCALE_SMOOTH);
	        view.getPreview().setIcon(new ImageIcon(image));
	        view.getPreview().setVisible(true);
            
        } catch(Exception ex){
            ex.printStackTrace();
        }
    }

    private boolean validateForm(){
        boolean valid = true;

        if(!validateName()) valid = false;
        if(!validateIcon()) valid = false;

        return valid;
    }

    private boolean validateName(){
        String name = view.getName();

        if(name.isEmpty()){
            view.setNameError("Obligatorio");
            return false;
        }

        if(name.length() < 3){
            view.setNameError("Mínimo 3 letras");
            return false;
        }

        view.clearNameError();

        return true;
    }

    private boolean validateIcon(){
        if(view.getIcon().isEmpty()){
            view.setIconError("Seleccione ícono");
            return false;
        }

        view.clearIconError();

        return true;
    }
}