package controllers;

import java.io.IOException;
import java.util.List;

import javax.swing.JOptionPane;
import models.User;
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
		
		this.view.getBtnAdd().addActionListener(e -> {
			openForm(null);
		});
		
		this.view.getBtnEdit().addActionListener(e -> {
			int row = view.getSelectedRow();
			if(row == -1) {
				JOptionPane.showMessageDialog(view, "Selecciona un usuario");
				return;
			}
			
			openForm(model.getUserAt(row));
		});
		
		this.view.getBtnDelete().addActionListener(e -> {
		    deleteUser();
		});
	}
	
	public void loadUsers() {	
		try {
			List<User> users = repo.getUsers();
			
			if(model == null) {
				model = new UserTableModel(users);
				view.setTableModel(model);
			}else {
				model.setUsers(users);
			}
			
		}catch (IOException ex) {
			JOptionPane.showMessageDialog(view, ex.getMessage());
		}
	}
	
	private void openForm(User user) {
		
		UserFormDialog dialog = new UserFormDialog(null, user);
		
		new UserFormController(dialog); 
		 
		dialog.setVisible(true);
		
		if(dialog.isSaved()) {
			User savedUser = dialog.getUser();
			
			try {
				if(user == null) {
					repo.save(savedUser);
				}else {
					int row = view.getSelectedRow();
					repo.update(row, savedUser);
				}
				
				loadUsers();
			}catch(Exception e) {
				e.printStackTrace();
				JOptionPane.showMessageDialog(view, e.getMessage());
			}
		}
	}
	
	private void deleteUser() {
	    int row = view.getSelectedRow();

	    if (row == -1) {
	        JOptionPane.showMessageDialog(view, "Selecciona un usuario");
	        return;
	    }

	    int option = JOptionPane.showConfirmDialog(
	        view,
	        "¿Seguro que deseas eliminar este usuario?",
	        "Confirmar eliminación",
	        JOptionPane.YES_NO_OPTION
	    );

	    if (option == JOptionPane.YES_OPTION) {
	        try {
	            repo.delete(row);
	            loadUsers();

	            JOptionPane.showMessageDialog(
	                view,
	                "Usuario eliminado correctamente"
	            );

	        } catch (IOException e) {
	            JOptionPane.showMessageDialog(view, e.getMessage());
	        }
	    }
	}
}