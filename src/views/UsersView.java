package views;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.border.EmptyBorder;

import components.RoundButton;
import tablemodels.UserTableModel;
import utils.AppFont;

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

	    JPanel topPanel = new JPanel();
	    topPanel.setLayout(new BorderLayout());

	    JLabel lblTitle = createTitle();
	    topPanel.add(lblTitle, BorderLayout.NORTH);

	    JPanel panelButtons = new JPanel(new FlowLayout(FlowLayout.CENTER));
	    
	    btnAdd = new RoundButton("Agregar", 
	        new ImageIcon(getClass().getResource("/img/button-add-icon.png")));
	    btnEdit = new RoundButton("Editar", 
	        new ImageIcon(getClass().getResource("/img/button-edit-icon.png")));
	    btnDelete = new RoundButton("Eliminar", 
	        new ImageIcon(getClass().getResource("/img/button-delete-icon.png")));

	    panelButtons.add(btnAdd);
	    panelButtons.add(btnEdit);
	    panelButtons.add(btnDelete);

	    topPanel.add(panelButtons, BorderLayout.CENTER);

	    add(topPanel, BorderLayout.NORTH);
	}
	
	public JLabel createTitle() {
	    JLabel lblTitle = new JLabel("Panel de administración de usuarios");
	    lblTitle.setBorder(new EmptyBorder(20, 20, 0, 20));
	    lblTitle.setFont(AppFont.subtitle());
	    lblTitle.setForeground(Color.BLACK);
	    lblTitle.setHorizontalAlignment(JLabel.CENTER);

	    return lblTitle;
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