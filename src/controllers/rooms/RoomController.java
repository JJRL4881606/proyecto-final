package controllers.rooms;

import java.util.List;

import javax.swing.JOptionPane;

import models.Room;
import models.RoomStatus;
import models.RoomType;
import repository.ReservationRepository;
import repository.RoomRepository;
import repository.RoomTypeRepository;
import tablemodels.RoomTableModel;
import views.main.MainView;
import views.rooms.RoomFormDialog;
import views.rooms.RoomsView;

public class RoomController {

	private RoomsView view;
	private RoomRepository repo;
	private RoomTableModel model;
	
    // se necesita para refrescar otras partes del programa cuando cambian las habitaciones (showRooms)
	private MainView mainView;

	public RoomController(RoomsView view, MainView mainView){
		this.view = view;
		this.mainView = mainView;
		repo = new RoomRepository();

		initListeners();
	}

	public void initListeners(){
		view.getBtnAdd().addActionListener(e -> { openForm(null); });
		view.getBtnEdit().addActionListener(e -> { handleEdit(); });
		view.getBtnDelete().addActionListener(e -> { handleDelete(); });
	}

    // Si el modelo todavía no existe lo crea y lo asigna a la tabla,
    // si ya existe solo actualiza sus datos
    // También necesita la lista de roomTypes para mostrar el nombre del tipo en la tabla
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
	
    // Abre el formulario para crear o editar (room con datos)
    // Después de cerrar el dialog, si se guardó algo, aplica los cambios
    // en la bd, en la tabla y refresca las vistas relacionadas
	private void openForm(Room room){
		RoomFormDialog dialog = new RoomFormDialog(null, room);
		new RoomFormController(dialog);
		dialog.setVisible(true);

		if(dialog.isSaved()){
			Room savedRoom = dialog.getRoom();

			try{
				if(room == null){
                    // CREAR. guardar en la base de datos, agregar la fila a la tabla
					repo.save(savedRoom);
					model.addRow(savedRoom);
                    // refrescar otras vistas que muestran habitaciones
					mainView.refreshRoomViews();
					
				}else{
                    // EDITAR. recuperar el id original del modelo porque el dialog
                    // no lo guarda, y asignarselo al objeto modificado antes de actualizar

					int row = view.getSelectedModelRow();
					Room originalRoom = model.getRoomAt(row);
					
					savedRoom.setRoomId(originalRoom.getRoomId());

                    // Bloquear el cambio a out of service si la habitación tiene reservas activas,
                    // porque eso deja reservaciones confirmadas sin habitación disponible

					ReservationRepository reservationRepo = new ReservationRepository();

					if(savedRoom.getStatus().equals(RoomStatus.OUT_OF_SERVICE) && reservationRepo.hasActiveReservation(savedRoom.getRoomId())){
					    JOptionPane.showMessageDialog(
					        null,
					        "No puedes poner esta habitación fuera de servicio porque tiene reservas activas",
					        "Error",
					        JOptionPane.ERROR_MESSAGE
					    );

					    return;
					}

                    // Si la actualización salió bien, reflejar el cambio en la tabla
					boolean updated = repo.update(savedRoom);
					
					if(updated){
					    model.updateRow(row,savedRoom);
					    mainView.refreshRoomViews();
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

    // Elimina la habitación seleccionada, pero primero checa que no tenga
    // reservaciones asociadas (esten activas o solo en el historial) para no dejar datos huérfanos
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

	    Room room = model.getRoomAt(row);

        // No se puede eliminar si tiene reservaciones, aunque estén canceladas o completadas,
        // porque se rompería el historial de pagos vinculado a esas reservaciones

	    ReservationRepository reservationRepo = new ReservationRepository();

    	if(reservationRepo.hasReservationsByRoom(room.getRoomId())){
    	    JOptionPane.showMessageDialog(
    	        null,
    	        "No puedes eliminar esta habitación porque tiene reservaciones asociadas",
    	        "Error",
    	        JOptionPane.ERROR_MESSAGE
    	    );

    	    return;
    	}

	    boolean deleted = repo.delete(room.getRoomId());

	    if(deleted){
            // Quitar la fila de la tabla y refrescar las vistas que muestran habitaciones
	        model.removeRow(row);
	        mainView.refreshRoomViews();
	    }
	}
}