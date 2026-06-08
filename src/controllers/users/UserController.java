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

//Controla el panel de admin de usuarios. cargar datos, abrir formulario, editar, eliminar y exportar pdf
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
	
    // Si el modelo todavía no existe lo crea y lo asigna a la tabla,
    // si ya existe solo actualiza sus datos
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
	
    // Abre el formulario para crear o editar
    // Después de cerrar el dialog, si se guardo algo, aplica los cambios
    // en la bd y refresca la tabla y las vistas relacionadas
	private void openForm(User user) {
		UserFormDialog dialog = new UserFormDialog(null, user);
		new UserFormController(dialog);
		dialog.setVisible(true);
		
		if(dialog.isSaved()) {
			User savedUser = dialog.getUser();
			
			try {
				//Añadir nuevo
				if(user == null) {
                    // CREAR. guardar en la bd y agregar solo esa fila
					repo.save(savedUser);
					model.addRow(savedUser); //Agrega el registro a la tabla
				}else {
					//Editar. recuperar el id original del modelo porque el diálogo no lo guarda,
                    // asignarselo al objeto modificado y actualizar solo esa fila

					int row = view.getSelectedModelRow();
					User originalUser = model.getUserAt(row);
					savedUser.setId(originalUser.getId());

					boolean updated = repo.update(savedUser);					
					if(updated) {
						model.updateRow(row, savedUser); //Actualiza el registro de la tabla
					}
				}
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
	
    // crea el pdf con la lista de usuarios y lo abre automaticamente si el sistema lo soporta
    // El archivo de destino lo elige el usuario desde la vista con un selector de archivos
	public void generatePdf() {
		File file = view.selectPdfFile();
		
		if(file == null) {
			return;
		}
		
		try {
			pdfExporter.exportUsers(repo.getUsers(), file);
			
            // Abrir el pdf automaticamente con el visor predeterminado del sistema
			if(Desktop.isDesktopSupported()) {
				Desktop.getDesktop().open(file);
			}
		}catch(Exception ex) {
			ex.printStackTrace();
			JOptionPane.showMessageDialog(null, "Error al exportar");
		}	
	}
	
    // Elimina el usuario seleccionado, pero primero revisa que no tenga reservaciones registradas
    // para no dejar reservaciones sin usuario valido en la bd
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

        // Bloquear el eliminar si el usuario tiene reservaciones, aunque esten canceladas o completadas,
        // para no romper el historial de pagos conectadoss a esas reservaciones

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
            // Quitar solo la fila eliminada del modelo sin recargar toda la tabla
	        model.removeRow(row);
	    }
	}
}