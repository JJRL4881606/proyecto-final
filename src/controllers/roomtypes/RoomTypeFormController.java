package controllers.roomtypes;

import javax.swing.JOptionPane;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.filechooser.FileNameExtensionFilter;

import config.Config;

import javax.swing.JFileChooser;
import javax.swing.ImageIcon;
import java.awt.Image;
import java.io.File;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;

import models.RoomType;
import utils.FormUtils;
import utils.Validator;
import views.roomtypes.RoomTypeFormDialog;

public class RoomTypeFormController {

	private RoomTypeFormDialog view;
	
	public RoomTypeFormController(RoomTypeFormDialog view){
		this.view = view;		
		initListeners();
		initInputRestrictions();
	}

	private void initListeners(){
		view.getBtnSave().addActionListener(e->{ handleSave(); });
		view.getBtnCancel().addActionListener(e->{ handleCancel(); });
	    view.getBtnSelectImage().addActionListener(e->{selectImage();});
		view.getSpCapacity().addChangeListener(e -> validateCapacity());
        view.getComboBedType().addActionListener(e -> validateBedType());

		view.getTxtName().getDocument().addDocumentListener(new DocumentListener(){
			public void insertUpdate(DocumentEvent e){validateName();}
			public void removeUpdate(DocumentEvent e){validateName();}
			public void changedUpdate(DocumentEvent e){validateName();}
		});
		
		view.getTxtPrice().getDocument().addDocumentListener(new DocumentListener(){
			public void insertUpdate(DocumentEvent e){validatePrice();}
			public void removeUpdate(DocumentEvent e){validatePrice();}
			public void changedUpdate(DocumentEvent e){validatePrice();}
		});
		
		view.getTxtDescription().getDocument().addDocumentListener(new DocumentListener(){
			public void insertUpdate(DocumentEvent e){validateDescription();}
			public void removeUpdate(DocumentEvent e){validateDescription();}
			public void changedUpdate(DocumentEvent e){validateDescription();}
		});
		
		view.getBtnExtraImages().addActionListener( e->selectExtraImages() );
		
		FormUtils.addFocusEffect(view.getTxtName(),view.getLblNameError());
		FormUtils.addFocusEffect(view.getTxtPrice(),view.getLblPriceError());
		FormUtils.addFocusEffect(view.getTxtDescription(),view.getLblDescriptionError());
		FormUtils.addFocusEffect(view.getTxtImagePath(),view.getLblImageError());
		FormUtils.addFocusEffect(view.getTxtExtraImages(),view.getLblExtraImagesError());
	}

	private void initInputRestrictions(){
		Validator.onlyLetters(view.getTxtName());
		Validator.onlyDecimalNumbers(view.getTxtPrice());
	}
	
	private void handleSave(){
		if(!validateForm()){
			return;
		}

		RoomType roomType = view.getRoomType();

		if(roomType == null){

			roomType = new RoomType(
			    0,
			    view.getName(),
			    view.getBedType(),
			    view.getCapacity(),
			    view.getPrice(),
			    view.getImagePath(),
			    view.getSelectedAmenities(),
			    view.isFeatured(),

			    view.getDescription(),
			    RoomType.stringToImages(
			        view.getTxtExtraImages().getText()
			    )
			);
		}else{
			roomType.setName(view.getName());
			roomType.setBedType(view.getBedType());
			roomType.setCapacity(view.getCapacity());
			roomType.setPrice(view.getPrice());
			roomType.setImagePath(view.getImagePath());
			roomType.setAmenities(view.getSelectedAmenities());
			roomType.setFeatured(view.isFeatured());	
			roomType.setDescription(view.getDescription());
			roomType.setExtraImages(RoomType.stringToImages(
				view.getTxtExtraImages().getText()
			));
		}

		view.setSaved(true);
		view.setRoomType(roomType);
		view.dispose();
	}

	private void handleCancel(){
		int option = view.confirmCancel();

		if(option == JOptionPane.YES_OPTION){
			view.dispose();
		}
	}

	private void selectImage(){
		String lastFolder = Config.get(
            "room.image.folder",
            System.getProperty("user.home")
        );

		JFileChooser chooser =new JFileChooser(lastFolder);
		
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
	        
	        String fileName = selected.getName();

	        File srcFolder =new File("src/assets/img/rooms");
	        File binFolder =new File("bin/assets/img/rooms");

	        srcFolder.mkdirs();
	        binFolder.mkdirs();

	        File srcDestination =new File(srcFolder,fileName);
	        File binDestination =new File(binFolder,fileName);

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
	        
	        String dbPath = "/assets/img/rooms/" + fileName;
	        view.getTxtImagePath().setText(dbPath);
	        
	        validateImage();

	        ImageIcon icon = new ImageIcon(srcDestination.getAbsolutePath());
	        Image image = icon.getImage().getScaledInstance(220, 120, Image.SCALE_SMOOTH);
	        view.getLblPreview().setIcon(new ImageIcon(image));
	        view.getLblPreview().setVisible(true);
	        
	    }catch(Exception ex){
	        ex.printStackTrace();
	    }
	}
	
	private void selectExtraImages(){

	    String lastFolder = Config.get(
	        "room.image.folder",
	        System.getProperty("user.home")
	    );

	    JFileChooser chooser = new JFileChooser(lastFolder);
	    chooser.setMultiSelectionEnabled(true);
	    chooser.setAcceptAllFileFilterUsed(false);

	    chooser.setFileFilter(new FileNameExtensionFilter(
	        "Imágenes (*.png, *.jpg, *.jpeg)",
	        "png","jpg","jpeg"
	    ));

	    int option = chooser.showOpenDialog(null);
	    if(option != JFileChooser.APPROVE_OPTION) return;

	    try{

	        File[] files = chooser.getSelectedFiles();

	        File srcFolder = new File("src/assets/img/rooms");
	        File binFolder = new File("bin/assets/img/rooms");

	        srcFolder.mkdirs();
	        binFolder.mkdirs();

	        StringBuilder paths = new StringBuilder();

	        for(File selected : files){

	            String fileName = selected.getName();

	            File srcDest = new File(srcFolder, fileName);
	            File binDest = new File(binFolder, fileName);

	            Files.copy(selected.toPath(), srcDest.toPath(), StandardCopyOption.REPLACE_EXISTING);
	            Files.copy(selected.toPath(), binDest.toPath(), StandardCopyOption.REPLACE_EXISTING);

	            String dbPath = "/assets/img/rooms/" + fileName;

	            if(paths.length() > 0) {
	                paths.append("|");
	            }

	            paths.append(dbPath);
	        }
	        
	        view.getTxtExtraImages().setText(paths.toString());
	        validateExtraImages();

	    }catch(Exception ex){
	        ex.printStackTrace();
	    }
	}
	
	private boolean validateForm(){
		view.clearErrors();
		boolean valid = true;

		if(!validateName()) valid = false;
		if(!validateCapacity()) valid = false;
		if(!validateBedType()) valid = false;
		if(!validatePrice()) valid = false;
		if(!validateImage()) valid = false;
		
		if(!validateDescription()) valid = false;
		if(!validateExtraImages()) valid = false;

		return valid;
	}
	
	private boolean validateName(){
	    String name = view.getName();
	    
	    if (name.isEmpty()) {
	        view.setNameError("El nombre es obligatorio");
	        return false;
	    } else if (!Validator.isValidName(name)) {
	        view.setNameError("Solo se permiten letras");
	        return false;
	    }
	    
	    view.clearNameError();
		return true;
	}

	public boolean validateCapacity(){
	    int value = view.getCapacity();

	    if(value < 1){
	        view.setCapacityError("Capacidad mínima de 1");
	        return false;
	    }

	    if(value > 10){
	        view.setCapacityError("Capacidad máxima de 10");
	        return false;
	    }

	    view.clearCapacityError();
	    return true;
	}
	
	public boolean validatePrice(){
	    String price = view.getTxtPrice().getText().trim();

	    if(price.isEmpty()){
	        view.setPriceError("El precio es obligatorio");
	        return false;
	    }

	    try{
	        double value = Double.parseDouble(price);

	        if(value <= 0){
	            view.setPriceError("Debe ser mayor a 0");
	            return false;
	        }
	    }catch(Exception e){
	        view.setPriceError("Formato inválido");
	        return false;
	    }

	    view.clearPriceError();
	    return true;
	}
	
	public boolean validateBedType(){
	    if(view.getBedTypeIndex() == 0){
	        view.setBedTypeError("Seleccione un tipo de cama");
	        return false;
	    }

	    view.clearBedTypeError();
	    return true;
	}
	
	public boolean validateImage(){
	    if (view.getImagePath().isEmpty()){
	        view.setImageError("Seleccione una imagen");
	        return false;
	    }
	    
	    view.clearImageError();
	    return true;
	}
		
	private boolean validateDescription(){
	    String description = view.getDescription();
	    
	    if (description.isEmpty()) {
	        view.setDescriptionError("La descripción es obligatoria");
	        return false;
	    } 
	    
	    if(description.length() < 100){
	        view.setDescriptionError("Mínimo 100 caracteres");
	        return false;
	    }

	    
	    view.clearDescriptionError();
		return true;
	}
	
	private boolean validateExtraImages(){
	    String images = view.getTxtExtraImages().getText().trim();

	    if(images.isEmpty()) {
	        view.setExtraImagesError(
	            "Selecciona imágenes extras"
	        );

	        return false;
	    }

	    view.clearExtraImagesError();
	    return true;
	}
}