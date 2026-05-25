package views.booking;

import java.awt.*;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.*;
import components.RoundedButton;
import tablemodels.ReservationTableModel;
import utils.AppFont;
import utils.UIColors;

@SuppressWarnings("serial")
public class ReservationsView extends JPanel {

    private JTable table;
    private RoundedButton btnAdd, btnEdit, btnDelete;

    public ReservationsView() {
        setLayout(new BorderLayout());

        table = new JTable();
        styleTable();

        JScrollPane scroll = new JScrollPane(table);
        scroll.getVerticalScrollBar().setUnitIncrement(16);
        scroll.setPreferredSize(new Dimension(1200, 400));

        JPanel centerPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        centerPanel.setOpaque(false);
        centerPanel.add(scroll);
        centerPanel.setBorder(new EmptyBorder(10, 0, 30, 0));
        add(centerPanel, BorderLayout.CENTER);

        JPanel topPanel = new JPanel(new BorderLayout());
        topPanel.setBorder(new EmptyBorder(20, 0, 10, 0));
        topPanel.add(createTitle(), BorderLayout.NORTH);

        JPanel panelButtons = new JPanel(new FlowLayout(FlowLayout.CENTER));
        panelButtons.setBorder(new EmptyBorder(10, 0, 0, 0));

        btnAdd = new RoundedButton("Agregar", new ImageIcon(getClass().getResource("/assets/img/btn-icons/button-add-icon.png")));
        btnEdit = new RoundedButton("Editar", new ImageIcon(getClass().getResource("/assets/img/btn-icons/button-edit-icon.png")));
        btnDelete = new RoundedButton("Eliminar", new ImageIcon(getClass().getResource("/assets/img/btn-icons/button-delete-icon.png")));

        panelButtons.add(btnAdd);
        panelButtons.add(btnEdit);
        panelButtons.add(btnDelete);

        topPanel.add(panelButtons, BorderLayout.CENTER);
        add(topPanel, BorderLayout.NORTH);
    }

    public JLabel createTitle() {
        JLabel lblTitle = new JLabel("Panel de administración de reservaciones");
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
            public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
                Component c = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
                if (!isSelected) {
                    c.setBackground(row % 2 == 0 ? Color.WHITE : new Color(245, 245, 245));
                    c.setForeground(Color.BLACK);
                }
                c.setFont(AppFont.normal());
                return c;
            }
        });
    }

    public void setTableModel(ReservationTableModel model) {
        table.setModel(model);

        int[] widths = {80, 80, 120, 120, 90, 130, 100, 150};
        for (int i = 0; i < widths.length; i++) {
            table.getColumnModel().getColumn(i).setPreferredWidth(widths[i]);
        }

        DefaultTableCellRenderer center = new DefaultTableCellRenderer();
        center.setHorizontalAlignment(SwingConstants.CENTER);
        
        int[] centeredColumns = {0, 1, 4, 5, 6};
        for (int col : centeredColumns) {
            table.getColumnModel().getColumn(col).setCellRenderer(center);
        }
    }

    public int getSelectedModelRow() {
        int row = table.getSelectedRow();
        return row == -1 ? -1 : table.convertRowIndexToModel(row);
    }

    public JTable getTable() { return table; }
    public RoundedButton getBtnAdd() { return btnAdd; }
    public RoundedButton getBtnEdit() { return btnEdit; }
    public RoundedButton getBtnDelete() { return btnDelete; }
    public int getSelectedRow() { return table.getSelectedRow(); }
}