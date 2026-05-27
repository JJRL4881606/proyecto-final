package tablemodels;

import java.util.List;

import javax.swing.table.AbstractTableModel;

import models.Amenity;

@SuppressWarnings("serial")
public class AmenityTableModel extends AbstractTableModel {

    private List<Amenity> amenities;

    private final String[] columns = {
        "Nombre",
        "Ícono"
    };

    public AmenityTableModel(List<Amenity> amenities){
        this.amenities = amenities;
    }

    @Override
    public int getRowCount() {
        return amenities.size();
    }

    @Override
    public int getColumnCount() {
        return columns.length;
    }

    @Override
    public String getColumnName(int column){
        return columns[column];
    }

    @Override
    public Object getValueAt(int rowIndex,int columnIndex){

        Amenity amenity = amenities.get(rowIndex);

        switch(columnIndex){
            case 0: return amenity.getName();
            case 1: return amenity.getIcon();
        }

        return null;
    }

    public Amenity getAmenityAt(int row){
        return amenities.get(row);
    }

    public void setAmenities(List<Amenity> amenities){
        this.amenities=amenities;
        fireTableDataChanged();
    }

    public void removeRow(int row){
        amenities.remove(row);
        fireTableRowsDeleted(row,row);
    }

    public void addRow(Amenity amenity){
        int row=amenities.size();
        amenities.add(amenity);
        fireTableRowsInserted(row,row);
    }

    public void updateRow(int row,Amenity amenity){
        amenities.set(row,amenity);
        fireTableRowsUpdated(row,row);
    }
}