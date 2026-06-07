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

        //document listener
		view.getTxtName().getDocument().addDocumentListener(new DocumentListener(){
			public void insertUpdate(DocumentEvent e){validateName();}
			public void removeUpdate(DocumentEvent e){validateName();}
			public void changedUpdate(DocumentEvent e){validateName();}
		});

        FormUtils.addFocusEffect(view.getTxtName(),view.getLblNameError());
    }

    // restricciones de campos
    private void initRestrictions(){
        Validator.onlyLetters(view.getTxtName());
    }

    // GUARDAR LA AMENIDAD
    private void handleSave(){
        if(!validateForm()) {
        	return;
        }

        Amenity amenity = view.getAmenity();

        if(amenity == null){
        	
        	// Crear nueva amenidad
            amenity = new Amenity(
                0,
                view.getName(),
                view.getIcon()
            );
        } else{
        	
        	// Actualizar datos de una amenidad existente
            amenity.setName(view.getName());
            amenity.setIcon(view.getIcon());
        }

        view.setAmenity(amenity);
        view.setSaved(true);

        view.dispose();
    }

    // CANCELAR Y CERRAR EL FORMULARIO
    private void handleCancel(){
        if(view.confirmCancel() == JOptionPane.YES_OPTION){
            view.dispose();
        }
    }

    // SELECCIONAR Y COPIAR UN ÍCONO AL PROYECTO
    private void selectIcon(){
    	
    	// Abrir el explorador en la ultima carpeta usada
    	String lastFolder = Config.get(
            "room.image.folder",
            System.getProperty("user.home")
        );

		JFileChooser chooser = new JFileChooser(lastFolder);
		
		//no permite la opcion de todos los archivos
	    chooser.setAcceptAllFileFilterUsed(false);
	    
	    // permitir solo imagenes
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
	        File selected = chooser.getSelectedFile(); //archivo selecionado
	        
	        // Guardar la carpeta seleccionada para proximas busquedas
	        Config.set(
        	    "room.image.folder",
        	    selected.getParent()
        	);
	        
	        String fileName = selected.getName();

            File srcFolder = new File("src/assets/img/icons");
            File binFolder = new File("bin/assets/img/icons");

            // Crear carpetas si todavía no existen
	        srcFolder.mkdirs();
	        binFolder.mkdirs();

	        File srcDestination = new File(srcFolder,fileName);
	        File binDestination = new File(binFolder,fileName);

	        // Copiar la imagen al proyecto
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

	        // Ruta que se almacenará en la base de datos
            String dbPath = "/assets/img/icons/" + fileName;
            view.getTxtIcon().setText(dbPath);

            // Mostrar vista previa del ícono seleccionado
            ImageIcon icon = new ImageIcon(srcDestination.getAbsolutePath());
	        Image image = icon.getImage().getScaledInstance(80, 80, Image.SCALE_SMOOTH);
	        view.getPreview().setIcon(new ImageIcon(image));
	        view.getPreview().setVisible(true);
            
        } catch(Exception ex){
            ex.printStackTrace();
        }
    }

    // VALIDAR TODOS LOS CAMPOS DEL FORMULARIO
    private boolean validateForm(){
    	view.clearErrors();
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