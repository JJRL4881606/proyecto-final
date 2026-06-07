package controllers.amenities;

import java.util.List;
import javax.swing.JOptionPane;

import models.Amenity;
import repository.AmenityRepository;
import tablemodels.AmenityTableModel;
import views.amenities.AmenitiesFormDialog;
import views.amenities.AmenitiesView;

public class AmenityController {

    private AmenitiesView view;
    private AmenityRepository repo;
    private AmenityTableModel model;

    public AmenityController(AmenitiesView view){
        this.view = view;
        repo=new AmenityRepository();

        initListeners();
    }

    private void initListeners(){
        view.getBtnAdd().addActionListener(e->openForm(null));
        view.getBtnEdit().addActionListener(e->handleEdit());
        view.getBtnDelete().addActionListener(e->handleDelete());
    }

    //cargar amenities desde la bd
    public void loadAmenities(){

        List<Amenity> amenities=repo.getAmenities();

        if(model == null){
            // Crear el modelo y asignarlo a la tabla
            model = new AmenityTableModel(amenities);
            view.setTableModel(model);

        }else{
            // Actualizar los datos existentes sin recrear tabla
            model.setAmenities(amenities);
        }
    }

    // ABRIR FORMULARIO PARA AGREGAR O EDITAR
    private void openForm(Amenity amenity){
        AmenitiesFormDialog dialog = new AmenitiesFormDialog(null,amenity);
        new AmenityFormController(dialog);
        dialog.setVisible(true);

        if(dialog.isSaved()){
            Amenity saved = dialog.getAmenity();

            if(amenity == null){
                // Nueva amenidad
                repo.save(saved);
                model.addRow(saved);
                
            }else{
            	// Actualizar amenidad existente
            	
                int row = view.getSelectedModelRow();

                // Mantener el id original para actualizar
                saved.setAmenityId(
                    model.getAmenityAt(row)
                    .getAmenityId()
                );

                boolean updated = repo.update(saved);

                if(updated){
                    model.updateRow(row, saved);
                }
            }
        }
    }

    // EDITAR LA AMENIDAD SELECCIONADA
    private void handleEdit(){

        int row = view.getSelectedModelRow();

        if(row == -1){
            JOptionPane.showMessageDialog(
                null,
                "Selecciona una amenidad"
            );

            return;
        }

        openForm(model.getAmenityAt(row));
    }

    // ELIMINAR LA AMENIDAD SELECCIONADA
    private void handleDelete(){

        int row = view.getSelectedModelRow();

        if(row == -1){
            JOptionPane.showMessageDialog(
                null,
                "Selecciona una amenidad"
            );
            
            return;
        }
        
        Amenity amenity = model.getAmenityAt(row);

        // no permitir eliminar amenidades que estan asociadas a tipos de habitacion
    	if(repo.isUsed(amenity.getAmenityId())){
    	    JOptionPane.showMessageDialog(
    	        null,
    	        "No puedes eliminar esta amenidad porque está siendo utilizada por tipos de habitación",
    	        "Error",
    	        JOptionPane.ERROR_MESSAGE
    	    );

    	    return;
    	}

        boolean deleted = repo.delete(model.getAmenityAt(row).getAmenityId());

        // Eliminar de la base de datos y de la tabla
        if(deleted){
            model.removeRow(row);
        }
    }
}