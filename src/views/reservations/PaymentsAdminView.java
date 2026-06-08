package views.reservations;

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
import tablemodels.PaymentTableModel;
import utils.AppFont;
import utils.UIColors;

@SuppressWarnings("serial")

//Pantalla del panel admin que muestra todos los pagos en una tabla
public class PaymentsAdminView extends JPanel {

    private JTable table;

    public PaymentsAdminView() {
        setLayout(new BorderLayout());

        table = new JTable();
        styleTable();

        JScrollPane scroll = new JScrollPane(table);
        scroll.getVerticalScrollBar().setUnitIncrement(16);
        scroll.setPreferredSize(new Dimension(1200, 400));

        // Panel que centra el scroll horizontalmente
        JPanel centerPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        centerPanel.setOpaque(false);
        centerPanel.add(scroll);
        centerPanel.setBorder(new EmptyBorder(10, 0, 30, 0));
        add(centerPanel, BorderLayout.CENTER);

        JPanel topPanel = new JPanel(new BorderLayout());
        topPanel.setBorder(new EmptyBorder(20, 0, 10, 0));
        topPanel.add(createTitle(), BorderLayout.NORTH);
        add(topPanel, BorderLayout.NORTH);
    }

    public JLabel createTitle() {
        JLabel lblTitle = new JLabel("Panel de administración de pagos");
        lblTitle.setBorder(new EmptyBorder(20, 20, 0, 20));
        lblTitle.setFont(AppFont.title());
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
            public Component getTableCellRendererComponent(JTable table, Object value,
                    boolean isSelected, boolean hasFocus, int row, int column) {

                Component c = super.getTableCellRendererComponent(
                        table, value, isSelected, hasFocus, row, column);

                if (!isSelected) {
                    c.setBackground(row % 2 == 0 ? Color.WHITE : new Color(245, 245, 245));
                    c.setForeground(Color.BLACK);
                }
                c.setFont(AppFont.normal());
                return c;
            }
        });
    }

    public void setTableModel(PaymentTableModel model) {
        table.setModel(model);

        int[] widths = {80, 120, 100, 150, 120};
        for (int i = 0; i < widths.length; i++) {
            table.getColumnModel().getColumn(i).setPreferredWidth(widths[i]);
        }

        DefaultTableCellRenderer center = new DefaultTableCellRenderer();
        center.setHorizontalAlignment(SwingConstants.CENTER);

        // centrar id pago, id reservación, monto, fecha
        int[] centeredColumns = {0, 1, 2, 4};
        for (int col : centeredColumns) {
            table.getColumnModel().getColumn(col).setCellRenderer(center);
        }
    }

    public JTable getTable() { return table; }
}