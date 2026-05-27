package controllers.users;

import java.awt.Desktop;
import java.io.File;
import java.io.IOException;
import java.util.List;

import javax.swing.JOptionPane;
import models.User;
import services.PDFExporter;
import repository.ReservationRepository;
import repository.UserRepository;
import tablemodels.UserTableModel;
import views.users.UserFormDialog;
import views.users.UsersView;

public class UserController {

	private UsersView view;
	private UserRepository repo;
	private UserTableModel model;
	private PDFExporter pdfExporter;
	
	public UserController(UsersView view) {
		this.view = view;
		this.repo = new UserRepository();
		this.pdfExporter = new PDFExporter();
		
		initListeners();
	}
	
	public void initListeners() {
		view.getBtnAdd().addActionListener(e -> { openForm(null); });
		view.getBtnEdit().addActionListener(e -> { editUser(); });
		view.getBtnDelete().addActionListener(e -> { deleteUser(); });
		view.getBtnPdf().addActionListener(e -> generatePdf());
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
				//Añadir nuevo
				if(user == null) {
					repo.save(savedUser);
					model.addRow(savedUser); //Agrega el registro a la tabla
				}else {
					//Editar existente
					int row = view.getSelectedModelRow();
					User originalUser = model.getUserAt(row);
					savedUser.setId(originalUser.getId());

					boolean updated = repo.update(savedUser);					
					if(updated) {
						model.updateRow(row, savedUser); //Actualiza el registro de la tabla
					}
				}
				//Ya no actualizamos toda la tabla
				//loadUsers();
			}catch(Exception e) {
				e.printStackTrace();
				JOptionPane.showMessageDialog(view, e.getMessage());
			}
		}
	}
	
	private void editUser() {
		int row = view.getSelectedModelRow();
		
		if(row == -1) {
			JOptionPane.showMessageDialog(
			    null,
			    "Selecciona un usuario",
			    "Advertencia",
			    JOptionPane.WARNING_MESSAGE
			);
			return;
		}
		openForm(model.getUserAt(row));
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
	
	private void deleteUser(){

	    int row = view.getSelectedModelRow();

	    if(row == -1){
	        JOptionPane.showMessageDialog(
	            null,
	            "Selecciona un usuario",
	            "Advertencia",
	            JOptionPane.WARNING_MESSAGE
	        );
	        return;
	    }
	    
	    User user = model.getUserAt(row);

	    ReservationRepository reservationRepo = new ReservationRepository();

	    if(reservationRepo.hasReservationsByUser(user.getId())){
	        JOptionPane.showMessageDialog(
	            null,
	            "No puedes eliminar este usuario porque tiene reservaciones registradas",
	            "Error",
	            JOptionPane.ERROR_MESSAGE
	        );

	        return;
	    }
	    boolean deleted = repo.delete(model.getUserAt(row).getId());

	    if(deleted){
	        model.removeRow(row);
	    }
	}
}