package views.users;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.io.File;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.JTableHeader;
import javax.swing.JFileChooser;
import javax.swing.filechooser.FileNameExtensionFilter;

import components.RoundedButton;
import config.Config;
import tablemodels.UserTableModel;
import utils.AppFont;
import utils.ButtonFactory;
import utils.UIColors;

@SuppressWarnings("serial")
public class UsersView extends JPanel{

	private JTable table;
	private RoundedButton btnEdit;
	private RoundedButton btnAdd;
	private RoundedButton btnDelete;
	private RoundedButton btnPdf;
	
	public UsersView() {
	    setLayout(new BorderLayout());
	    table = new JTable();
		styleTable();
		
		//scroll
		JScrollPane scroll = new JScrollPane(table);
		scroll.getVerticalScrollBar().setUnitIncrement(16);
		scroll.setPreferredSize(new Dimension(1200, 400));

		JPanel centerPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
		centerPanel.setOpaque(false);
		centerPanel.add(scroll);
		centerPanel.setBorder(new EmptyBorder(10, 0, 30, 0));

		add(centerPanel, BorderLayout.CENTER);
		
		//top panel
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
            "Agregar un usuario"
        );

	    btnEdit = ButtonFactory.createGoldButton(
            "Editar",
            "/assets/img/btn-icons/button-edit-icon.png",
            "Editar un usuario"
        );

	    btnDelete = ButtonFactory.createGoldButton(
            "Eliminar",
            "/assets/img/btn-icons/button-delete-icon.png",
            "Eliminar un usuario"
        );

	    btnPdf = ButtonFactory.createGoldButton(
            "Exportar a PDF",
            "/assets/img/btn-icons/button-pdf-icon.png",
            "Exportar usuarios a PDF"
        );
	    
	    panelButtons.add(btnAdd);
	    panelButtons.add(btnEdit);
	    panelButtons.add(btnDelete);
	    panelButtons.add(btnPdf);

	    topPanel.add(panelButtons, BorderLayout.CENTER);

	    add(topPanel, BorderLayout.NORTH);
	}
	
	public JLabel createTitle() {
	    JLabel lblTitle = new JLabel("Panel de administración de usuarios");
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
	
	public File selectPdfFile() {
		
		String path = Config.get("users.export.pdf", System.getProperty("user.home"));
		JFileChooser chooser = new JFileChooser(path);
		
		chooser.setSelectedFile(new File("reporte-usuarios.pdf"));
		
		chooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
		chooser.setAcceptAllFileFilterUsed(false);
		
		FileNameExtensionFilter filter = new FileNameExtensionFilter("Documentos PDF",  "pdf");
		chooser.addChoosableFileFilter(filter);
		chooser.setFileFilter(filter);
		
		int option = chooser.showDialog(null, "Exportar PDF de usuarios");
		
		if(option != JFileChooser.APPROVE_OPTION) {
			return null;
		}
		
		File file = chooser.getSelectedFile();
		Config.set("users.export.pdf", file.getParent());
		
		if(!file.getName().toLowerCase().endsWith(".pdf")) {
			file = new File(file.getAbsolutePath() + ".pdf");
		}
		
		return file;
	}
	
	public void setTableModel(UserTableModel model) {
		table.setModel(model);
		
		if(table.getColumnCount() >= 1) {
			table.getColumnModel().getColumn(0).setPreferredWidth(40);
		}
		
		if(table.getColumnCount() >= 2) {
			table.getColumnModel().getColumn(1).setPreferredWidth(40);
		}
		
		if(table.getColumnCount() >= 3) {
			table.getColumnModel().getColumn(2).setPreferredWidth(150);
		}
		
		if(table.getColumnCount() >= 4) {
			table.getColumnModel().getColumn(3).setPreferredWidth(40);
		}

		if(table.getColumnCount() >= 5) {
			table.getColumnModel().getColumn(4).setPreferredWidth(40);
		}

		if(table.getColumnCount() >= 6) {
			table.getColumnModel().getColumn(5).setPreferredWidth(70);
		}
		
		if(table.getColumnCount() >= 7) {
			table.getColumnModel().getColumn(6).setPreferredWidth(30);
		}
		
		DefaultTableCellRenderer center = new DefaultTableCellRenderer();
		center.setHorizontalAlignment(SwingConstants.CENTER);
		
		if(table.getColumnCount() >= 1) {
			table.getColumnModel().getColumn(0).setCellRenderer(center);
		}
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
    
    public RoundedButton getBtnPdf() {
    	return btnPdf;
    }
	
    public int getSelectedRow() {
    	return table.getSelectedRow();
    }
}