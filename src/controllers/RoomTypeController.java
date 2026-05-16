package controllers;

import java.util.List;

import javax.swing.JOptionPane;

import models.RoomType;
import repository.RoomTypeRepository;
import tablemodels.RoomTypeTableModel;
import views.RoomTypeFormDialog;
import views.RoomTypesView;

public class RoomTypeController {

	private RoomTypesView view;
	private RoomTypeRepository repo;
	private RoomTypeTableModel model;

	public RoomTypeController(RoomTypesView view) {
		this.view = view;
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
				if(roomType == null) {
					repo.save(savedRoomType);
					model.addRow(savedRoomType); //Agrega el registro a la tabla
				}else {
					//Editar existente
					int row = view.getSelectedModelRow();
					RoomType originalRoomType = model.getRoomTypeAt(row);
					savedRoomType.setTypeId(originalRoomType.getTypeId());

					boolean updated = repo.update(savedRoomType);					
					if(updated) {
						model.updateRow(row, savedRoomType); //Actualiza el registro de la tabla
					}
				}
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
			    "Selecciona una habitación",
			    "Advertencia",
			    JOptionPane.WARNING_MESSAGE
			);
			return;
		}
		openForm(model.getRoomTypeAt(row));
	}

	private void handleDelete() {
		boolean deleted = repo.delete(model.getRoomTypeAt(view.getSelectedRow()).getTypeId());
		if(deleted) {
			model.removeRow(view.getSelectedRow());
		}
	}
}