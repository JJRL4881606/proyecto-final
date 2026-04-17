package tablemodels;

import java.text.SimpleDateFormat;
import java.util.List;

import javax.swing.table.AbstractTableModel;

import models.User;

@SuppressWarnings("serial")
public class UserTableModel extends AbstractTableModel{

	private List<User> users;
	private final SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
	
	private final String[] columns = {
		"Nombre",
		"Apellido",
		"Email",
		"Teléfono",
		"País",
		"Fecha de nacimiento",
		"Género",
	};
	
	public UserTableModel(List<User> users) {
		this.users = users;
	}
	
	@Override
	public int getRowCount() {
		return users.size();
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
		
		User user = users.get(rowIndex);
		
		switch(columnIndex) {
			case 0:
				return user.getName();
			case 1:
				return user.getSurname();
			case 2:
				return user.getEmail();
			case 3:
				return user.getPhone();
			case 4:
				return user.getCountry();
			case 5:
			    return formatter.format(user.getBirthDate());			
			case 6:
				return user.getGender();
		}	
		return null;	
	}	
}