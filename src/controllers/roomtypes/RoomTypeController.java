package controllers.roomtypes;

import java.util.List;

import javax.swing.JOptionPane;

import models.RoomType;
import repository.RoomRepository;
import repository.RoomTypeRepository;
import tablemodels.RoomTypeTableModel;
import views.main.MainView;
import views.roomtypes.RoomTypeFormDialog;
import views.roomtypes.RoomTypesView;

public class RoomTypeController {

	private RoomTypesView view;
	private RoomTypeRepository repo;
	private RoomTypeTableModel model;
	private MainView mainView;

	public RoomTypeController(RoomTypesView view, MainView mainView) {
	    this.view = view;
	    this.mainView = mainView;
	    this.repo = new RoomTypeRepository();

	    initListeners();
	}

	public void initListeners() {
		view.getBtnAdd().addActionListener(e -> { openForm(null); });
		view.getBtnEdit().addActionListener(e -> { handleEdit(); });
		view.getBtnDelete().addActionListener(e -> { handleDelete(); });
	}

	public void loadRoomTypes() {
		List<RoomType> roomTypes = repo.getRoomTypes();

		if(model == null){
			model = new RoomTypeTableModel(roomTypes);
			view.setTableModel(model);
			
		}else{
			model.setRoomTypes(roomTypes);
		}
	}

	private void openForm(RoomType roomType) {
		RoomTypeFormDialog dialog = new RoomTypeFormDialog(null, roomType);
		new RoomTypeFormController(dialog);
		dialog.setVisible(true);

		if(dialog.isSaved()) {
			RoomType savedRoomType = dialog.getRoomType();
			
			try {
				//Añadir nuevo
				if(roomType == null){

				    repo.save(savedRoomType);
				    loadRoomTypes();
				    mainView.refreshRoomViews();
				    
				}else{

				    int row=view.getSelectedModelRow();

				    RoomType original = model.getRoomTypeAt(row);

				    savedRoomType.setTypeId(
				        original.getTypeId()
				    );

				    boolean updated = repo.update(savedRoomType);

				    if(updated){
				        loadRoomTypes();
				        mainView.refreshRoomViews();
				    }
				}

				view.revalidate();
				view.repaint();
				
			}catch(Exception e) {
				e.printStackTrace();
				JOptionPane.showMessageDialog(view, e.getMessage());
			}
		}
	}

	private void handleEdit() {
		int row = view.getSelectedModelRow();
		
		if(row == -1) {
			JOptionPane.showMessageDialog(
			    null,
			    "Selecciona un tipo de habitación",
			    "Advertencia",
			    JOptionPane.WARNING_MESSAGE
			);
			return;
		}
		openForm(model.getRoomTypeAt(row));
	}

	private void handleDelete(){
		//advertencia si no seleccionaste ninguna fila
	    int row = view.getSelectedModelRow();

	    if(row == -1){
	        JOptionPane.showMessageDialog(
	            null,
	            "Selecciona un tipo de habitación",
	            "Advertencia",
	            JOptionPane.WARNING_MESSAGE
	        );
	        return;
	    }
	    
	    //Advertencia si hay habitaciones usando este tipo
	    RoomRepository roomRepo = new RoomRepository();
	    int typeId = model.getRoomTypeAt(view.getSelectedRow()).getTypeId();

	    if(roomRepo.existsByTypeId(typeId)){
	        JOptionPane.showMessageDialog(
	            null,
	            "No puedes eliminar este tipo porque hay habitaciones usándolo",
	            "Error",
	            JOptionPane.ERROR_MESSAGE
	        );
	        return;
	    }

	    boolean deleted = repo.delete(model.getRoomTypeAt(row).getTypeId());

	    if(deleted){
	        loadRoomTypes();
	        mainView.refreshRoomViews();
	    }
	}
}