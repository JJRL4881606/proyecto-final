package tablemodels;

import java.util.List;
import java.util.stream.Collectors;

import javax.swing.table.AbstractTableModel;

import models.Amenity;
import models.RoomType;

@SuppressWarnings("serial")
public class RoomTypeTableModel extends AbstractTableModel{

	private List<RoomType> roomTypes;
	
	private final String[] columns = {
	    "Nombre",
	    "Tipo cama",
	    "Capacidad",
	    "Precio",
	    "Imagen principal",
	    "Amenidades",
	    "Destacada",
	    "Descripción",
	    "Imágenes extra"
	};
	
	public RoomTypeTableModel(List<RoomType> roomTypes) {
		this.roomTypes = roomTypes;
	}
	
	@Override
	public int getRowCount() {
		return roomTypes.size();
	}

	@Override
	public int getColumnCount() {
		return columns.length;
	}
	
	@Override
	public String getColumnName(int column) {
		return columns[column];
	}

	@Override
	public Object getValueAt(int rowIndex, int columnIndex) {

	    RoomType roomType = roomTypes.get(rowIndex);

	    switch(columnIndex) {
		    case 0:
		        return roomType.getName();
		    case 1:
		        return roomType.getBedType();
		    case 2:
		        return roomType.getCapacity();
		    case 3:
		        return "$" + roomType.getPrice();
		    case 4:
		        return roomType.getImagePath();
		    case 5:
		        return roomType.getAmenities()
		            .stream()
		            .map(Amenity::getName)
		            .collect(Collectors.joining(", "));
		    case 6:
		    	if(roomType.isFeatured() == true) {
		    		return "Si";
		    	}
		        return "No";
		    case 7:
		        return shortenText(
		            roomType.getDescription(),
		            24
		        );
	
		    case 8:
		        return roomType.getExtraImages().size() + " imagen(es)";
		}

	    return null;
	}
	
	private String shortenText(String text, int maxLength){

	    if(text == null){
	        return "";
	    }

	    if(text.length() <= maxLength){
	        return text;
	    }

	    return text.substring(0, maxLength) + "...";
	}
	
	public RoomType getRoomTypeAt(int row) {
		return roomTypes.get(row);
	}
	
	public void setRoomTypes(List<RoomType> roomTypes) {
		this.roomTypes = roomTypes;
		fireTableDataChanged();
	}
	
	/* Estos métodos permiten modificar una sola fila al momento de añadir, editar o eliminar un usuario.
	 * Deberán llamarlos en el constructor cuando hacen cada operación. Ya les puse el ejemplo con editar y
	 * eliminar.
	 */
	public void removeRow(int row) {
		roomTypes.remove(row);
		fireTableRowsDeleted(row, row);
	}

	public void addRow(RoomType roomType) {
		int row = roomTypes.size();
		roomTypes.add(roomType);
		fireTableRowsInserted(row, row);
	}

	public void updateRow(int row, RoomType roomType) {
		roomTypes.set(row, roomType);
		fireTableRowsUpdated(row, row);
	}

}