package views;

import java.awt.BorderLayout;
import java.awt.FlowLayout;

import javax.swing.ImageIcon;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;

import components.RoundButton;
import tablemodels.UserTableModel;

@SuppressWarnings("serial")
public class UsersView extends JPanel{

	private JTable table;
	private RoundButton btnEdit;
	private RoundButton btnAdd;
	private RoundButton btnDelete;
	
	public UsersView() {
		setLayout(new BorderLayout());
		table = new JTable();
		
		add(new JScrollPane(table), BorderLayout.CENTER);
		
		JPanel panelButtons = new JPanel(new FlowLayout(FlowLayout.LEFT));

        btnAdd = new RoundButton("Agregar", new ImageIcon(getClass().getResource("/img/button-add-icon.png")));
        btnEdit = new RoundButton("Editar", new ImageIcon(getClass().getResource("/img/button-edit-icon.png")));
        btnDelete = new RoundButton("Eliminar", new ImageIcon(getClass().getResource("/img/button-delete-icon.png")));

        panelButtons.add(btnAdd);
        panelButtons.add(btnEdit);
        panelButtons.add(btnDelete);
        
        add(panelButtons, BorderLayout.NORTH);
	}
	
	public void setTableModel(UserTableModel model) {
		table.setModel(model);
	}
	
	public JTable getTable() {
		return table;
	}
	
	public RoundButton getBtnAdd() {
        return btnAdd;
    }

    public RoundButton getBtnEdit() {
        return btnEdit;
    }

    public RoundButton getBtnDelete() {
        return btnDelete;
    }
	
    public int getSelectedRow() {
    	return table.getSelectedRow();
    }

}