package controllers;

import java.awt.Desktop;
import java.io.File;
import java.io.IOException;
import java.util.List;

import javax.swing.JOptionPane;
import models.User;
import services.PDFExporter;
import repository.UserRepository;
import tablemodels.UserTableModel;
import views.UserFormDialog;
import views.UsersView;

public class UserController {

	private UsersView view;
	private UserRepository repo;
	private UserTableModel model;
	private PDFExporter pdfExporter;
	
	public UserController(UsersView view) {
		this.view = view;
		repo = new UserRepository();
		pdfExporter = new PDFExporter();
		
		this.view.getBtnAdd().addActionListener(e -> {
			openForm(null);
		});
		
		this.view.getBtnEdit().addActionListener(e -> {
			int row = view.getSelectedModelRow();
			
			if(row == -1) {
				JOptionPane.showMessageDialog(null, "Selecciona un usuario");
				return;
			}
			
			openForm(model.getUserAt(row));
		});
		
		this.view.getBtnDelete().addActionListener(e -> {
		    deleteUser();
		});		
		
		this.view.getBtnPdf().addActionListener(e -> generatePdf());
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
			JOptionPane.showMessageDialog(null, ex.getMessage());
		}
	}
	
	private void openForm(User user) {
		
		UserFormDialog dialog = new UserFormDialog(null, user);
		
		new UserFormController(dialog); 
		 
		dialog.setVisible(true);
		
		if(dialog.isSaved()) {
			User savedUser = dialog.getUser();
			
			try {
				int row = view.getSelectedModelRow();
				
				if(user == null) {
				    repo.save(savedUser);
				} else {
				    repo.update(row, savedUser);
				}
				
				loadUsers();
			}catch(Exception e) {
				e.printStackTrace();
				JOptionPane.showMessageDialog(null, e.getMessage());
			}
		}
	}
	
	private void deleteUser() {
		int row = view.getSelectedModelRow();
		
	    if (row == -1) {
	        JOptionPane.showMessageDialog(null, "Selecciona un usuario");
	        return;
	    }

	    int option = JOptionPane.showConfirmDialog(
	    	    null,
	    	    "¿Seguro que deseas eliminar este usuario?",
	    	    "Confirmar eliminación",
	    	    JOptionPane.YES_NO_OPTION
	    	);

	    if (option == JOptionPane.YES_OPTION) {
	        try {
	            repo.delete(row);
	            loadUsers();

	            JOptionPane.showMessageDialog(null, "Usuario eliminado correctamente");

	        } catch (IOException e) {
	            JOptionPane.showMessageDialog(null, e.getMessage());
	        }
	    }
	}
	
	public void generatePdf() {
		File file = view.selectPdfFile();
		
		if(file == null) {
			return;
		}
		
		try {
			pdfExporter.exportUsers(repo.getUsers(), file);
			if(Desktop.isDesktopSupported()) {
				Desktop.getDesktop().open(file);
			}
		}catch(Exception ex) {
			ex.printStackTrace();
			JOptionPane.showMessageDialog(null, "Error al exportar");
		}	
	}
}