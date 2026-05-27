package controllers.rooms;

import java.util.List;

import javax.swing.JOptionPane;

import models.Room;
import models.RoomType;
import repository.ReservationRepository;
import repository.RoomRepository;
import repository.RoomTypeRepository;
import tablemodels.RoomTableModel;
import views.rooms.RoomFormDialog;
import views.rooms.RoomsView;

public class RoomController {

	private RoomsView view;
	private RoomRepository repo;
	private RoomTableModel model;

	public RoomController(RoomsView view){
		this.view = view;
		repo = new RoomRepository();

		initListeners();
	}

	public void initListeners(){
		view.getBtnAdd().addActionListener(e -> { openForm(null); });
		view.getBtnEdit().addActionListener(e -> { handleEdit(); });
		view.getBtnDelete().addActionListener(e -> { handleDelete(); });
	}

	public void loadRooms() {
	    List<Room> rooms = repo.getRooms();
	    List<RoomType> roomTypes = new RoomTypeRepository().getRoomTypes();

	    if(model == null){
	        model = new RoomTableModel(rooms, roomTypes);
	        view.setTableModel(model);
	    }else{
	        model.setData(rooms, roomTypes);
	    }
	}
	
	private void openForm(Room room){
		RoomFormDialog dialog = new RoomFormDialog(null, room);
		new RoomFormController(dialog);
		dialog.setVisible(true);

		if(dialog.isSaved()){
			Room savedRoom = dialog.getRoom();

			try{
				if(room == null){
					repo.save(savedRoom);
					model.addRow(savedRoom);
				}else{
					int row = view.getSelectedModelRow();
					Room originalRoom = model.getRoomAt(row);
					savedRoom.setRoomId(originalRoom.getRoomId());

					boolean updated = repo.update(savedRoom);
					if(updated){
						model.updateRow(row,savedRoom);
					}
				}
			}catch(Exception e){
				e.printStackTrace();

				JOptionPane.showMessageDialog(
						null,
						e.getMessage()
				);
			}
		}
	}

	private void handleEdit() {
		int row = view.getSelectedModelRow();

		if(row == -1){
			JOptionPane.showMessageDialog(
				null,
				"Selecciona una habitación",
				"Advertencia",
				JOptionPane.WARNING_MESSAGE
			);
			return;
		}
		openForm(model.getRoomAt(row));
	}

	private void handleDelete(){

	    int row = view.getSelectedModelRow();

	    if(row==-1){

	        JOptionPane.showMessageDialog(
	            null,
	            "Selecciona una habitación",
	            "Advertencia",
	            JOptionPane.WARNING_MESSAGE
	        );

	        return;
	    }

	    Room room=model.getRoomAt(row);

	    ReservationRepository reservationRepo =
	        new ReservationRepository();

	    if(reservationRepo.hasActiveReservation(
	        room.getRoomId()
	    )){

	        JOptionPane.showMessageDialog(
	            null,
	            "No puedes eliminar esta habitación porque tiene reservas activas",
	            "Error",
	            JOptionPane.ERROR_MESSAGE
	        );

	        return;
	    }

	    boolean deleted=
	        repo.delete(room.getRoomId());

	    if(deleted){
	        model.removeRow(row);
	    }
	}
}