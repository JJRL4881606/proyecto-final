package views;

import java.awt.BorderLayout;

import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;

import tablemodels.UserTableModel;

@SuppressWarnings("serial")
public class UsersView extends JPanel{

	private JTable table;
	
	public UsersView() {
		setLayout(new BorderLayout());
		table = new JTable();
		
		add(new JScrollPane(table), BorderLayout.CENTER);
	}
	
	public void setTableModel(UserTableModel model) {
		table.setModel(model);
	}
	
	public JTable getTable() {
		return table;
	}
	
}