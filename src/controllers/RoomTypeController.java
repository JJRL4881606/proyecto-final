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
		loadRoomTypes();
	}

	public void initListeners() {
		view.getBtnAdd().addActionListener(e -> { handleAdd(); });
		view.getBtnEdit().addActionListener(e -> { handleEdit(); });
		view.getBtnDelete().addActionListener(e -> { handleDelete(); });
	}

	public void loadRoomTypes() {
		List<RoomType> roomTypes = repo.getRoomTypes();

		if(model==null){
			model = new RoomTypeTableModel(roomTypes);
			view.setTableModel(model);
		}else{
			model.setRoomTypes(roomTypes);
		}
	}

	private void handleAdd() {
		RoomTypeFormDialog dialog = new RoomTypeFormDialog(null, null);

		dialog.getBtnSave().addActionListener(e->{
			RoomType roomType = new RoomType(
				0,
				dialog.getName(),
				dialog.getBedType(),
				dialog.getCapacity(),
				dialog.getPrice(),
				dialog.getImagePath(),
				dialog.getFeatures(),
				dialog.isFeatured()
			);

			repo.save(roomType);

			dialog.dispose();

			loadRoomTypes();
		});

		dialog.getBtnCancel().addActionListener(e->{ dialog.dispose(); });
		dialog.setVisible(true);
	}

	private void handleEdit() {
		int row = view.getSelectedModelRow();

		if(row==-1){
			JOptionPane.showMessageDialog(
					null,
					"Selecciona una habitación"
			);
			return;
		}

		RoomType roomType = model.getRoomTypeAt(row);

		RoomTypeFormDialog dialog = new RoomTypeFormDialog(null, roomType);

		dialog.getBtnSave().addActionListener(e->{
			RoomType updatedRoomType = new RoomType(
				roomType.getTypeId(),
				dialog.getName(),
				dialog.getBedType(),
				dialog.getCapacity(),
				dialog.getPrice(),
				dialog.getImagePath(),
				dialog.getFeatures(),
				dialog.isFeatured()
			);

			repo.update(updatedRoomType);

			dialog.dispose();

			loadRoomTypes();

		});

		dialog.getBtnCancel().addActionListener(e->{ dialog.dispose(); });
		dialog.setVisible(true);
	}

	private void handleDelete() {
		int row = view.getSelectedModelRow();

		if(row==-1){
			JOptionPane.showMessageDialog(
					null,
					"Selecciona una habitación"
			);
			return;
		}

		RoomType roomType = model.getRoomTypeAt(row);

		int option = JOptionPane.showConfirmDialog(
				null,
				"¿Eliminar habitación?",
				"Confirmar",
				JOptionPane.YES_NO_OPTION
		);

		if(option != JOptionPane.YES_OPTION){
			return;
		}

		repo.delete(roomType.getTypeId());

		loadRoomTypes();
	}
}