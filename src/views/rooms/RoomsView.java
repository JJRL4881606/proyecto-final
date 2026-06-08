package views.rooms;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;

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
import tablemodels.RoomTableModel;
import utils.AppFont;
import utils.ButtonFactory;
import utils.UIColors;

@SuppressWarnings("serial")

//Pantalla del panel de admin de habitaciones con tabla y botones de agregar, editar y eliminar
public class RoomsView extends JPanel{

	private JTable table;

	private RoundedButton btnAdd;
	private RoundedButton btnEdit;
	private RoundedButton btnDelete;

	public RoomsView(){

		setLayout(new BorderLayout());

		table = new JTable();
		styleTable();

		// scroll
		JScrollPane scroll = new JScrollPane(table);
		scroll.getVerticalScrollBar().setUnitIncrement(16);
		scroll.setPreferredSize(new Dimension(1000, 400));

        // Panel que centra el scroll horizontalmente
		JPanel centerPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
		centerPanel.setOpaque(false);
		centerPanel.add(scroll);
		centerPanel.setBorder(new EmptyBorder(10, 0, 30, 0));

		add(centerPanel, BorderLayout.CENTER);

		// top panel
		JPanel topPanel = new JPanel();
		topPanel.setLayout(new BorderLayout());
		topPanel.setBorder(new EmptyBorder(20, 0, 10, 0));

		JLabel lblTitle = createTitle();
		topPanel.add(lblTitle, BorderLayout.NORTH);

		JPanel panelButtons = new JPanel(new FlowLayout(FlowLayout.CENTER));
		panelButtons.setBorder(new EmptyBorder(10, 0, 0, 0));

	    btnAdd = ButtonFactory.createGoldButton(
            "Agregar",
            "/assets/img/btn-icons/button-add-icon.png",
            "Agregar una habitación"
        );

	    btnEdit = ButtonFactory.createGoldButton(
            "Editar",
            "/assets/img/btn-icons/button-edit-icon.png",
            "Editar una habitación"
        );

	    btnDelete = ButtonFactory.createGoldButton(
            "Eliminar",
            "/assets/img/btn-icons/button-delete-icon.png",
            "Eliminar una habitación"
        );

		panelButtons.add(btnAdd);
		panelButtons.add(btnEdit);
		panelButtons.add(btnDelete);

		topPanel.add(panelButtons,BorderLayout.CENTER);

		add(topPanel, BorderLayout.NORTH);
	}

	public JLabel createTitle() {
		JLabel lblTitle = new JLabel("Panel de administración de habitaciones");
		lblTitle.setBorder(new EmptyBorder(20, 20, 0, 20));
		lblTitle.setFont(AppFont.title());
		lblTitle.setForeground(Color.BLACK);
		lblTitle.setHorizontalAlignment(JLabel.CENTER);

		return lblTitle;
	}

	public void styleTable(){
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
		header.setBackground(UIColors.BACKGROUND);
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

	public void setTableModel(RoomTableModel model){
		table.setModel(model);

		if (table.getColumnCount() >= 1) {
			table.getColumnModel().getColumn(0).setPreferredWidth(50);
		}
		
		if (table.getColumnCount() >= 2) {
			table.getColumnModel().getColumn(1).setPreferredWidth(50);
		}
		
		if (table.getColumnCount() >= 3) {
			table.getColumnModel().getColumn(2).setPreferredWidth(100);
		}
		
		if (table.getColumnCount() >= 4) {
			table.getColumnModel().getColumn(3).setPreferredWidth(50);
		}
		
		DefaultTableCellRenderer center = new DefaultTableCellRenderer();
		center.setHorizontalAlignment(SwingConstants.CENTER);

		table.getColumnModel().getColumn(0).setCellRenderer(center);
		table.getColumnModel().getColumn(1).setCellRenderer(center);
		table.getColumnModel().getColumn(3).setCellRenderer(center);
	}

    // Obtiene la fila seleccionada del modelo
    public int getSelectedModelRow() {
        int row = table.getSelectedRow();

        // Si no hay una fila seleccionada
        if(row == -1) {
            return -1;
        }

        // Devuelve el índice real de la fila seleccionada
        return table.convertRowIndexToModel(row);
    }

	public JTable getTable(){
		return table;
	}
	
	//getters

	public RoundedButton getBtnAdd(){
		return btnAdd;
	}

	public RoundedButton getBtnEdit(){
		return btnEdit;
	}

	public RoundedButton getBtnDelete(){
		return btnDelete;
	}

	public int getSelectedRow(){
		return table.getSelectedRow();
	}
}