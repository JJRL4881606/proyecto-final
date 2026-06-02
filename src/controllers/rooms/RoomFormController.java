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

	private void loadRoomTypes(){
		roomTypes = roomTypeRepository.getRoomTypes();

		for(RoomType roomType:roomTypes){
			view.getComboRoomType().addItem(roomType.getName());
		}

		if(view.getRoom() != null){
			
			for(int i = 0; i < roomTypes.size(); i++){
				
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

	private void handleSave(){
	    if(!validateForm()){
	        return;
	    }

	    int roomNumber = view.getRoomNumber();
	    
	    //VALIDACIÓN ANTES DE CREAR EL OBJETO
	    if(view.getRoom() == null){
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
	        // Al editar, permitir mismo número si es el mismo registro
	        Room current = view.getRoom();

	        if(current.getRoomNumber() != roomNumber &&
	           roomRepository.existsRoomNumber(roomNumber)){
	            JOptionPane.showMessageDialog(
	                null,
	                "Ese número ya está en uso",
	                "Error",
	                JOptionPane.ERROR_MESSAGE
	            );

	            return;
	        }
	    }

	    // DESPUÉS DE VALIDAR, CREAR EL ROOM
	    int index = view.getComboRoomType().getSelectedIndex()-1;

	    RoomType roomType = roomTypes.get(index);

	    int typeId = roomType.getTypeId();

	    Room room = view.getRoom();
	    
	    /*
	    ReservationRepository reservationRepo = new ReservationRepository();

	    if (room != null) {

	        boolean hasActiveReservation =
	            reservationRepo.hasActiveReservation(room.getRoomId());

	        String status = view.getStatus();

	        if (hasActiveReservation && status.equals(RoomStatus.OUT_OF_SERVICE)) {
	            JOptionPane.showMessageDialog(
	                null,
	                "No puedes poner fuera de servicio una habitación con reservas activas"
	            );
	            return;
	        }
	    }*/
	    
	    if(room == null){
	        room = new Room(
	            0,
	            roomNumber,
	            view.getFloor(),
	            typeId,
	            view.getStatus()
	        );
	    }else{
	        room.setRoomNumber(roomNumber);
	        room.setFloor(view.getFloor());
	        room.setTypeId(typeId);
	        room.setStatus(view.getStatus());
	    }

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