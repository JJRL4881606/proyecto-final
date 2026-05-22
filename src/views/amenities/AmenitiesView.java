package views.amenities;

import java.awt.*;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.JTableHeader;

import components.RoundedButton;
import tablemodels.AmenityTableModel;
import utils.AppFont;
import utils.UIColors;

@SuppressWarnings("serial")
public class AmenitiesView extends JPanel {

    private JTable table;
    private RoundedButton btnAdd, btnEdit, btnDelete;

    public AmenitiesView() {

        setLayout(new BorderLayout());

        table = new JTable();
        styleTable();

        JScrollPane scroll = new JScrollPane(table);
        scroll.getVerticalScrollBar().setUnitIncrement(16);
        scroll.setPreferredSize(new Dimension(1000,400));

        JPanel center = new JPanel(new FlowLayout(FlowLayout.CENTER));
        center.setOpaque(false);
        center.setBorder(new EmptyBorder(10,0,30,0));
        center.add(scroll);

        add(center, BorderLayout.CENTER);

        JPanel top = new JPanel(new BorderLayout());
        top.setBorder(new EmptyBorder(20,0,10,0));

        top.add(createTitle(), BorderLayout.NORTH);

        JPanel buttons = new JPanel(new FlowLayout(FlowLayout.CENTER));
        buttons.setBorder(new EmptyBorder(10,0,0,0));

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

        buttons.add(btnAdd);
        buttons.add(btnEdit);
        buttons.add(btnDelete);

        top.add(buttons, BorderLayout.CENTER);
        add(top, BorderLayout.NORTH);
    }

    private JLabel createTitle() {

        JLabel lbl = new JLabel("Panel de administración de comodidades");

        lbl.setBorder(new EmptyBorder(20,20,0,20));
        lbl.setFont(AppFont.subtitle());
        lbl.setForeground(Color.BLACK);
        lbl.setHorizontalAlignment(JLabel.CENTER);

        return lbl;
    }

    private void styleTable() {

        table.setRowHeight(35);
        table.setShowGrid(true);
        table.setGridColor(new Color(230,230,230));

        table.setBackground(Color.WHITE);
        table.setForeground(Color.BLACK);
        table.setFont(AppFont.normal());

        table.setSelectionBackground(new Color(52,152,219));
        table.setSelectionForeground(Color.WHITE);
        table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

        JTableHeader header = table.getTableHeader();

        header.setBackground(UIColors.BACKGROUND);
        header.setForeground(Color.WHITE);
        header.setFont(AppFont.big());
        header.setPreferredSize(new Dimension(0,40));
        header.setReorderingAllowed(false);

        table.setDefaultRenderer(Object.class, new DefaultTableCellRenderer() {

            @Override
            public Component getTableCellRendererComponent(
                JTable table, Object value,
                boolean selected, boolean focus,
                int row, int col
            ) {

                Component c = super.getTableCellRendererComponent(
                    table, value, selected, focus, row, col
                );

                if(!selected){
                    c.setBackground(
                        row % 2 == 0
                            ? Color.WHITE
                            : new Color(245,245,245)
                    );
                    c.setForeground(Color.BLACK);
                }

                c.setFont(AppFont.normal());
                return c;
            }
        });
    }

    public void setTableModel(AmenityTableModel model) {

        table.setModel(model);

        DefaultTableCellRenderer center = new DefaultTableCellRenderer();
        center.setHorizontalAlignment(SwingConstants.CENTER);

        table.getColumnModel().getColumn(0).setPreferredWidth(250);
        table.getColumnModel().getColumn(1).setPreferredWidth(500);

        table.getColumnModel().getColumn(0).setCellRenderer(center);
        table.getColumnModel().getColumn(1).setCellRenderer(center);
    }

    public int getSelectedModelRow() {
        int row = table.getSelectedRow();
        return row == -1 ? -1 : table.convertRowIndexToModel(row);
    }

    public JTable getTable(){ return table; }

    public RoundedButton getBtnAdd(){ return btnAdd; }

    public RoundedButton getBtnEdit(){ return btnEdit; }

    public RoundedButton getBtnDelete(){ return btnDelete; }

    public int getSelectedRow(){ return table.getSelectedRow(); }
}