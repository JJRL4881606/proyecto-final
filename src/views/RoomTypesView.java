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
import tablemodels.RoomTypeTableModel;
import utils.AppFont;
import utils.UIColors;

@SuppressWarnings("serial")
public class RoomTypesView extends JPanel {

	private JTable table;
	
	private RoundedButton btnAdd;
	private RoundedButton btnEdit;
	private RoundedButton btnDelete;

	public RoomTypesView() {
		
		setLayout(new BorderLayout());

		table = new JTable();
		styleTable();

		// scroll
		JScrollPane scroll = new JScrollPane(table);
		scroll.getVerticalScrollBar().setUnitIncrement(16);
		scroll.setPreferredSize(new Dimension(1200, 400));

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

		btnAdd = new RoundedButton(
			"Agregar",
			new ImageIcon(getClass().getResource("/assets/img/btn-icons/button-add-icon.png"))
		);

		btnEdit = new RoundedButton(
			"Editar",
			new ImageIcon(getClass().getResource("/assets/img/btn-icons/button-edit-icon.png"))
		);

		btnDelete = new RoundedButton(
			"Eliminar",
			new ImageIcon(getClass().getResource("/assets/img/btn-icons/button-delete-icon.png"))
		);

		panelButtons.add(btnAdd);
		panelButtons.add(btnEdit);
		panelButtons.add(btnDelete);

		topPanel.add(panelButtons, BorderLayout.CENTER);

		add(topPanel, BorderLayout.NORTH);
	}

	public JLabel createTitle() {
		JLabel lblTitle = new JLabel("Panel de administración de habitaciones");
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

				c.setFont(AppFont.normal());

				return c;
			}
		});
	}

	public void setTableModel(RoomTypeTableModel model) {
		table.setModel(model);

		if (table.getColumnCount() >= 1) {
			table.getColumnModel().getColumn(0).setPreferredWidth(50);
		}

		if (table.getColumnCount() >= 2) {
			table.getColumnModel().getColumn(1).setPreferredWidth(150);
		}

		if (table.getColumnCount() >= 3) {
			table.getColumnModel().getColumn(2).setPreferredWidth(120);
		}

		if (table.getColumnCount() >= 4) {
			table.getColumnModel().getColumn(3).setPreferredWidth(80);
		}

		if (table.getColumnCount() >= 5) {
			table.getColumnModel().getColumn(4).setPreferredWidth(100);
		}

		if (table.getColumnCount() >= 6) {
			table.getColumnModel().getColumn(5).setPreferredWidth(250);
		}

		if (table.getColumnCount() >= 7) {
			table.getColumnModel().getColumn(6).setPreferredWidth(300);
		}

		if (table.getColumnCount() >= 8) {
			table.getColumnModel().getColumn(7).setPreferredWidth(80);
		}

		DefaultTableCellRenderer center = new DefaultTableCellRenderer();
		center.setHorizontalAlignment(SwingConstants.CENTER);

		table.getColumnModel().getColumn(0).setCellRenderer(center);
		table.getColumnModel().getColumn(3).setCellRenderer(center);
		table.getColumnModel().getColumn(4).setCellRenderer(center);
		table.getColumnModel().getColumn(7).setCellRenderer(center);
	}

	public int getSelectedModelRow() {
		int row = table.getSelectedRow();

		if (row == -1) {
			return -1;
		}

		return table.convertRowIndexToModel(row);
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