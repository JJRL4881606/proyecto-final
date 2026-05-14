package controllers;

import javax.swing.JOptionPane;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

import models.RoomType;
import repository.RoomTypeRepository;
import utils.FormUtils;
import utils.Validator;
import views.RoomTypeFormDialog;

public class RoomTypeFormController {

	private RoomTypeFormDialog view;
	
	public RoomTypeFormController(RoomTypeFormDialog view){
		this.view=view;
		new RoomTypeRepository();
		
		initListeners();
		initInputRestrictions();
	}

	private void initListeners(){
		view.getBtnSave().addActionListener(e->{handleSave();});
		view.getBtnCancel().addActionListener(e->{handleCancel();});
		
        view.getComboBedType().addActionListener(e -> validateBedType());


		view.getTxtName().getDocument().addDocumentListener(new DocumentListener(){
			public void insertUpdate(DocumentEvent e){validateName();}
			public void removeUpdate(DocumentEvent e){validateName();}
			public void changedUpdate(DocumentEvent e){validateName();}
		});

		view.getTxtCapacity().getDocument().addDocumentListener(new DocumentListener(){
			public void insertUpdate(DocumentEvent e){validateCapacity();}
			public void removeUpdate(DocumentEvent e){validateCapacity();}
			public void changedUpdate(DocumentEvent e){validateCapacity();}
		});

		view.getTxtPrice().getDocument().addDocumentListener(new DocumentListener(){
			public void insertUpdate(DocumentEvent e){validatePrice();}
			public void removeUpdate(DocumentEvent e){validatePrice();}
			public void changedUpdate(DocumentEvent e){validatePrice();}
		});

		view.getTxtImagePath().getDocument().addDocumentListener(new DocumentListener(){
			public void insertUpdate(DocumentEvent e){validateImage();}
			public void removeUpdate(DocumentEvent e){validateImage();}
			public void changedUpdate(DocumentEvent e){validateImage();}
		});

		view.getTxtFeatures().getDocument().addDocumentListener(new DocumentListener(){
			public void insertUpdate(DocumentEvent e){validateFeatures();}
			public void removeUpdate(DocumentEvent e){validateFeatures();}
			public void changedUpdate(DocumentEvent e){validateFeatures();}
		});
		
		FormUtils.addFocusEffect(view.getTxtName(),view.getLblNameError());
		FormUtils.addFocusEffect(view.getTxtCapacity(),view.getLblCapacityError());
		FormUtils.addFocusEffect(view.getTxtPrice(),view.getLblPriceError());
		FormUtils.addFocusEffect(view.getTxtImagePath(),view.getLblImageError());
		FormUtils.addFocusEffect(view.getTxtFeatures(),view.getLblFeaturesError());
	}

	private void initInputRestrictions(){
		Validator.onlyLetters(view.getTxtName());
		Validator.onlyNumbers(view.getTxtCapacity());
		Validator.onlyDecimalNumbers(view.getTxtPrice());
		Validator.noSpaces(view.getTxtImagePath());
	}
	
	private void handleSave(){
		if(!validateForm()){
			return;
		}

		RoomType roomType=view.getRoomType();

		if(roomType==null){

			roomType=new RoomType(
				0,
				view.getName(),
				view.getBedType(),
				view.getCapacity(),
				view.getPrice(),
				view.getImagePath(),
				view.getFeatures(),
				view.isFeatured()
			);

		}else{
			roomType.setName(view.getName());
			roomType.setBedType(view.getBedType());
			roomType.setCapacity(view.getCapacity());
			roomType.setPrice(view.getPrice());
			roomType.setImagePath(view.getImagePath());
			roomType.setFeatures(view.getFeatures());
			roomType.setFeatured(view.isFeatured());
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

	private boolean validateForm(){
		view.clearErrors();
		boolean valid=true;

		if(!validateName()) valid=false;
		if(!validateCapacity()) valid=false;
		if(!validateBedType()) valid=false;
		if(!validatePrice()) valid=false;
		if(!validateImage()) valid=false;
		if(!validateFeatures()) valid=false;

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

	public boolean validateCapacity() {
	    String capacity=view.getTxtCapacity().getText().trim();

	    if(capacity.isEmpty()){
	        view.setCapacityError("La capacidad es obligatoria");
	        return false;
	    }

	    try{
	        int value=Integer.parseInt(capacity);

	        if(value<=0){
	            view.setCapacityError("Debe ser mayor a 0");
	            return false;
	        }
	    }catch(Exception e){
	        view.setCapacityError("Solo números");
	        return false;
	    }

	    view.clearCapacityError();
	    return true;
	}
	
	public boolean validatePrice(){
	    String price=view.getTxtPrice().getText().trim();

	    if(price.isEmpty()){
	        view.setPriceError("El precio es obligatorio");
	        return false;
	    }

	    try{
	        double value=Double.parseDouble(price);

	        if(value<=0){
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
	    if(view.getBedTypeIndex()==0){
	        view.setBedTypeError("Seleccione un tipo de cama");
	        return false;
	    }

	    view.clearBedTypeError();
	    return true;
	}
	
	public boolean validateImage(){
	    String image = view.getImagePath();

	    if(image.isEmpty()){
	        view.setImageError("La ruta es obligatoria");
	        return false;
	    }

	    if(!image.endsWith(".jpg") && !image.endsWith(".png") && !image.endsWith(".jpeg")){
	        view.setImageError("Formato inválido");
	        return false;
	    }

	    view.clearImageError();
	    return true;
	}
	
	public boolean validateFeatures(){
	    String features=view.getTxtFeatures().getText().trim();

	    if(features.isEmpty()){
	        view.setFeaturesError("Campo obligatorio");
	        return false;
	    }

	    if(features.startsWith("|") || features.endsWith("|")){
	        view.setFeaturesError("Formato inválido");
	        return false;
	    }

	    view.clearFeaturesError();
	    return true;
	}
}