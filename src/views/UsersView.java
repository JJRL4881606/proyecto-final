package views;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.JTableHeader;

import components.RoundedButton;
import tablemodels.UserTableModel;
import utils.AppFont;

@SuppressWarnings("serial")
public class UsersView extends JPanel{

	private JTable table;
	private RoundedButton btnEdit;
	private RoundedButton btnAdd;
	private RoundedButton btnDelete;
	
	public UsersView() {
	    setLayout(new BorderLayout());
	    table = new JTable();
		styleTable();

	    add(new JScrollPane(table), BorderLayout.CENTER);

	    JPanel topPanel = new JPanel();
	    topPanel.setLayout(new BorderLayout());

	    JLabel lblTitle = createTitle();
	    topPanel.add(lblTitle, BorderLayout.NORTH);

	    JPanel panelButtons = new JPanel(new FlowLayout(FlowLayout.CENTER));
	    
	    btnAdd = new RoundedButton("Agregar", 
	        new ImageIcon(getClass().getResource("/img/button-add-icon.png")));
	    btnEdit = new RoundedButton("Editar", 
	        new ImageIcon(getClass().getResource("/img/button-edit-icon.png")));
	    btnDelete = new RoundedButton("Eliminar", 
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
	
	public void styleTable() {
		table.setRowHeight(35);
		table.setShowGrid(true);
		table.setGridColor(new Color(230, 230, 230));
		table.setBackground(Color.WHITE);
		table.setForeground(Color.BLACK);
		table.setFont(AppFont.normal());
		
		table.setSelectionBackground(new Color(52, 152, 219));
		table.setSelectionForeground(Color.WHITE);
		
		table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		
		JTableHeader header = table.getTableHeader();
		header.setBackground(new Color(44, 62, 80));
		header.setForeground(Color.WHITE);
		header.setFont(AppFont.big());
		header.setPreferredSize(new Dimension(0, 40));
		header.setReorderingAllowed(false);
		
		table.setDefaultRenderer(Object.class, new DefaultTableCellRenderer() {

            @Override
            public Component getTableCellRendererComponent(
                    JTable table,
                    Object value,
                    boolean isSelected,
                    boolean hasFocus,
                    int row,
                    int column) {

                Component c = super.getTableCellRendererComponent(
                        table,
                        value,
                        isSelected,
                        hasFocus,
                        row,
                        column);
                
                if (!isSelected) {
                    if (row % 2 == 0) {
                        c.setBackground(Color.WHITE);
                    } else {
                        c.setBackground(new Color(245, 245, 245));
                    }
                    c.setForeground(Color.BLACK);
                }
				
				if(column == 2) {
					if(!isSelected) {
						c.setForeground(new Color(41, 128, 185));
					}
				} else {
					c.setFont(AppFont.normal());
				}
			
				return c;
			}
		});
	}
	
	public void setTableModel(UserTableModel model) {
		table.setModel(model);
		
		if(table.getColumnCount() >= 1) {
			table.getColumnModel().getColumn(0).setPreferredWidth(80);
		}
		
		if(table.getColumnCount() >= 2) {
			table.getColumnModel().getColumn(1).setPreferredWidth(80);
		}
		
		if(table.getColumnCount() >= 3) {
			table.getColumnModel().getColumn(2).setPreferredWidth(200);
		}
		
		if(table.getColumnCount() >= 4) {
			table.getColumnModel().getColumn(3).setPreferredWidth(90);
		}

		if(table.getColumnCount() >= 5) {
			table.getColumnModel().getColumn(4).setPreferredWidth(90);
		}

		if(table.getColumnCount() >= 6) {
			table.getColumnModel().getColumn(5).setPreferredWidth(100);
		}
		
		if(table.getColumnCount() >= 7) {
			table.getColumnModel().getColumn(6).setPreferredWidth(40);
		}
		
		DefaultTableCellRenderer center = new DefaultTableCellRenderer();
		center.setHorizontalAlignment(SwingConstants.CENTER);
		
		if(table.getColumnCount() >= 1) {
			table.getColumnModel().getColumn(0).setCellRenderer(center);
		}
	}	
	public JTable getTable() {
		return table;
	}
	
	public RoundedButton getBtnAdd() {
        return btnAdd;
    }

    public RoundedButton getBtnEdit() {
        return btnEdit;
    }

    public RoundedButton getBtnDelete() {
        return btnDelete;
    }
	
    public int getSelectedRow() {
    	return table.getSelectedRow();
    }
}