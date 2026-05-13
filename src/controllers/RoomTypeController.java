package controllers;

import java.io.IOException;
import java.util.List;

import javax.swing.JOptionPane;

import models.RoomType;
import repository.RoomTypeRepository;
import tablemodels.RoomTypeTableModel;
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

		view.getBtnAdd().addActionListener(e -> {

		});

		view.getBtnEdit().addActionListener(e -> {

		});

		view.getBtnDelete().addActionListener(e -> {

		});
	}

	public void loadRoomTypes() {
		try {
			List<RoomType> roomTypes = repo.getRoomTypes();

			if (model == null) {
				model = new RoomTypeTableModel(roomTypes);
				view.setTableModel(model);
			} else {
				model.setRoomTypes(roomTypes);
			}
		} catch (IOException ex) {
			JOptionPane.showMessageDialog(null, ex.getMessage());
		}
	}
}