package controllers.rooms;

import java.util.List;

import javax.swing.JOptionPane;
import models.Room;
import models.RoomType;
import repository.RoomRepository;
import repository.RoomTypeRepository;
import views.rooms.RoomFormDialog;

public class RoomFormController {

	private RoomFormDialog view;
	private RoomRepository roomRepository;
	private RoomTypeRepository roomTypeRepository;

	private List<RoomType> roomTypes;

	public RoomFormController(RoomFormDialog view){
		this.view = view;
		
	    roomRepository = new RoomRepository();
		roomTypeRepository = new RoomTypeRepository();

		loadRoomTypes();
		initListeners();
	}

    // Llena el combo de tipos de habitación con los datos de la bd
    // Si es modo editar, selecciona el tipo que ya tenía la habitación buscandolo por typeId
	private void loadRoomTypes(){
		roomTypes = roomTypeRepository.getRoomTypes();

		for(RoomType roomType:roomTypes){
			view.getComboRoomType().addItem(roomType.getName());
		}

		//en modo de editar
		if(view.getRoom() != null){
			
			for(int i = 0; i < roomTypes.size(); i++){
				
				//busca el tipo actual por typeId, y seleccionarlo en el combo (+1 por el placeholder)
				if(roomTypes.get(i).getTypeId() == view.getRoom().getTypeId()){
					view.getComboRoomType().setSelectedIndex(i+1);
					break;
				}
			}
		}
	}

	private void initListeners(){
		view.getBtnSave().addActionListener(e->{handleSave();});
		view.getBtnCancel().addActionListener(e->{handleCancel();});

		view.getSpRoomNumber().addChangeListener(e->validateRoomNumber());
		view.getSpFloor().addChangeListener(e->validateFloor());
		view.getComboRoomType().addActionListener(e->validateRoomType());
		view.getComboStatus().addActionListener(e->validateStatus());
	}

    // Guarda la habitación nueva o actualiza la existente
    // Primero valida el formulario, luego verifica que el numero de habitacion no esta duplicado,
    // y después crea o modifica la Room

	private void handleSave(){
	    if(!validateForm()){
	        return;
	    }

	    int roomNumber = view.getRoomNumber();
	    
        // Verificar numero duplicado antes de crear el objeto
	    if(view.getRoom() == null){
	    	
            // Al crear. el número no puede existir en ninguna otra habitación
	        if(roomRepository.existsRoomNumber(roomNumber)){
	            JOptionPane.showMessageDialog(
	                null,
	                "Ya existe una habitación con ese número",
	                "Error",
	                JOptionPane.ERROR_MESSAGE
	            );

	            return;
	        }
	    }else{
            // Al editar, el número puede ser el mismo del registro actual,
            // pero no puede coincidir con el de otra habitación diferente
	        Room current = view.getRoom();

	        if(current.getRoomNumber() != roomNumber && roomRepository.existsRoomNumber(roomNumber)){
	            JOptionPane.showMessageDialog(
	                null,
	                "Ese número ya está en uso",
	                "Error",
	                JOptionPane.ERROR_MESSAGE
	            );

	            return;
	        }
	    }

        // Se resta 1 al índice del combo, por el placeholder en la posición 0
	    int index = view.getComboRoomType().getSelectedIndex()-1;

	    RoomType roomType = roomTypes.get(index);

	    int typeId = roomType.getTypeId();

	    Room room = view.getRoom();
	    
	    if(room == null){
            // Crear nueva habitación con id 0 (la bd asigna el definitivo)
	        room = new Room(
	            0,
	            roomNumber,
	            view.getFloor(),
	            typeId,
	            view.getStatus()
	        );
	    }else{
            // Actualizar los campos de la habitación existente
	        room.setRoomNumber(roomNumber);
	        room.setFloor(view.getFloor());
	        room.setTypeId(typeId);
	        room.setStatus(view.getStatus());
	    }

        // Pasar el objeto actualizado a la vista y cerrar el dialog
	    view.setSaved(true);
	    view.setRoom(room);
	    view.dispose();
	}
	
	private void handleCancel(){
		int option = view.confirmCancel();

		if (option == JOptionPane.YES_OPTION){
			view.dispose();
		}
	}

	private boolean validateForm(){
		view.clearErrors();

		boolean valid = true;

		if(!validateRoomNumber()) valid = false;
		if(!validateFloor()) valid = false;
		if(!validateRoomType()) valid = false;
		if(!validateStatus()) valid = false;

		return valid;
	}

	private boolean validateRoomNumber(){
		int value = view.getRoomNumber();

		if(value <= 0){
			view.setRoomNumberError("Número inválido");
			return false;
		}

		view.clearRoomNumberError();

		return true;
	}

	private boolean validateFloor(){
		int value = view.getFloor();

		if(value < 1){
			view.setFloorError("Piso inválido");
			return false;
		}

		view.clearFloorError();

		return true;
	}

	private boolean validateRoomType(){
		if(view.getComboRoomType().getSelectedIndex() == 0){
			view.setRoomTypeError("Seleccione un tipo");
			return false;
		}

		view.clearRoomTypeError();

		return true;
	}	
	
	private boolean validateStatus(){
		if(view.getComboStatus().getSelectedIndex() == 0){
			view.setStatusError("Seleccione el estado");
			return false;
		}

		view.clearStatusError();

		return true;
	}
}