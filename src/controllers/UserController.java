package controllers;

import repository.UserRepository;
import tablemodels.UserTableModel;
import views.UserFormDialog;
import views.UsersView;

public class UserController {

	private UsersView view;
	private UserRepository repo;
	private UserTableModel model;
	
	public UserController(UsersView view) {
		this.view = view;
		repo = new UserRepository();
		
		view.getBtnAdd().addActionListener(e -> {
			UserFormDialog form = new UserFormDialog(null, null);
			form.setVisible(true);
		});
	}
	
}