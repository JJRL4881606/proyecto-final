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

    public void loadAmenities(){

        List<Amenity> amenities=repo.getAmenities();

        if(model == null){
            model = new AmenityTableModel(amenities);
            view.setTableModel(model);
        }else{
            model.setAmenities(amenities);
        }
    }

    private void openForm(Amenity amenity){
        AmenitiesFormDialog dialog = new AmenitiesFormDialog(null,amenity);
        new AmenityFormController(dialog);
        dialog.setVisible(true);

        if(dialog.isSaved()){
            Amenity saved = dialog.getAmenity();

            if(amenity == null){
                repo.save(saved);
                model.addRow(saved);
                
            }else{
                int row = view.getSelectedModelRow();

                saved.setAmenityId(
                    model.getAmenityAt(row)
                    .getAmenityId()
                );

                boolean updated = repo.update(saved);

                if(updated){
                    model.updateRow(
                        row,
                        saved
                    );
                }
            }
        }
    }

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

        if(deleted){
            model.removeRow(row);
        }
    }
}