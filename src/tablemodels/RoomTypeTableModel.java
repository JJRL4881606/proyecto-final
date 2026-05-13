package tablemodels;

import java.util.List;

import javax.swing.table.AbstractTableModel;

import models.RoomType;

@SuppressWarnings("serial")
public class RoomTypeTableModel extends AbstractTableModel{

	private List<RoomType> roomTypes;
	
	private final String[] columns = {
		"Id",
		"Nombre",
		"Tipo cama",
		"Capacidad",
		"Precio",
		"Ruta imagen",
		"Comodidades",
		"Destacada",
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
				return roomType.getTypeId();
			case 1:
				return roomType.getName();
			case 2:
				return roomType.getBedType();
			case 3:
				return roomType.getCapacity();
			case 4:
				return roomType.getPrice();
			case 5:
				return roomType.getImagePath();
			case 6:
				return roomType.getFeatures();
			case 7:
				return roomType.isFeatured();
		}	
		return null;	
	}	
	
	public RoomType getRoomTypeAt(int row) {
		return roomTypes.get(row);
	}
	
	public void setRoomTypes(List<RoomType> roomTypes) {
		this.roomTypes = roomTypes;
		fireTableDataChanged();
	}
}