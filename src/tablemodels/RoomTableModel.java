package tablemodels;

import java.util.List;

import javax.swing.table.AbstractTableModel;

import models.Room;
import models.RoomType;

@SuppressWarnings("serial")
public class RoomTableModel extends AbstractTableModel{

	private List<Room> rooms;
	
	//Lista de los tipos de habitaciones
	private List<RoomType> roomTypes;

	private final String[] columns = {
		"Número",
		"Piso",
		"Tipo habitación",
		"Estado"
	};

	public RoomTableModel(List<Room> rooms, List<RoomType> roomTypes) {
	    this.rooms = rooms;
	    this.roomTypes = roomTypes;
	}
	
	@Override
	public int getRowCount() {
		return rooms.size();
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

		Room room = rooms.get(rowIndex);

		switch(columnIndex) {

			case 0:
				return room.getRoomNumber();
			case 1:
				return room.getFloor();
			case 2:
			    return getRoomTypeName(room.getTypeId());
			case 3:				
				return room.getStatus();
		}

		return null;
	}

	public Room getRoomAt(int row) {
		return rooms.get(row);
	}

	public void setData(List<Room> rooms, List<RoomType> roomTypes) {
	    this.rooms = rooms;
	    this.roomTypes = roomTypes;
	    fireTableDataChanged();
	}
	
	private String getRoomTypeName(int typeId){

	    for(RoomType rt : roomTypes){
	        if(rt.getTypeId() == typeId){
	            return rt.getName();
	        }
	    }

	    return "Desconocido";
	}

	/* Estos métodos permiten modificar una sola fila al momento de añadir, editar o eliminar un usuario.
	 * Deberán llamarlos en el constructor cuando hacen cada operación. Ya les puse el ejemplo con editar y
	 * eliminar.
	 */
	public void removeRow(int row) {
		rooms.remove(row);
		fireTableRowsDeleted(row,row);
	}

	public void addRow(Room room) {
		int row = rooms.size();
		rooms.add(room);
		fireTableRowsInserted(row,row);
	}

	public void updateRow(int row, Room room) {
		rooms.set(row,room);
		fireTableRowsUpdated(row,row);
	}
}