package utils;

import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;

import javax.imageio.ImageIO;
import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.ButtonGroup;
import javax.swing.ImageIcon;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JRadioButton;
import javax.swing.JSpinner;
import javax.swing.JTextField;
import javax.swing.SpinnerDateModel;
import javax.swing.border.Border;

public class FormUtils {

    public static JPanel createField(String labelText, JComponent field, JLabel errorLabel, String placeholder) {

        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setOpaque(false);
        panel.setAlignmentX(Component.CENTER_ALIGNMENT);

        if (labelText != null) {
            JLabel label = new JLabel(labelText);
            label.setAlignmentX(Component.CENTER_ALIGNMENT);
            panel.add(label);
        }

        if (field instanceof JTextField) {
            ((JTextField) field).putClientProperty("JTextField.placeholderText", placeholder);
        }

        errorLabel.setForeground(UIColors.ERROR);
        errorLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        panel.add(field);
        panel.add(Box.createRigidArea(new Dimension(0, 5)));
        panel.add(errorLabel);
        panel.add(Box.createRigidArea(new Dimension(0, 15)));

        return panel;
    }
    
    public static JLabel createErrorLabel() {
        JLabel label = new JLabel();
        label.setForeground(UIColors.ERROR);
        label.setFont(AppFont.small());
        label.setAlignmentX(Component.CENTER_ALIGNMENT);
        return label;
    }
    
    public static JTextField createTextField() {
        JTextField field = new JTextField();
        field.setBorder(BorderFactory.createEmptyBorder(8,10,8,10));
        field.setFont(AppFont.normal());
        field.setMaximumSize(new Dimension(Integer.MAX_VALUE, 35));
        return field;
    }
    
    public static JPasswordField createPasswordField() {
    	JPasswordField field = new JPasswordField();
        field.setBorder(BorderFactory.createEmptyBorder(8,10,8,10));
        field.setFont(AppFont.normal());
        field.setMaximumSize(new Dimension(Integer.MAX_VALUE, 35));
        return field;
    }
    
    public static JSpinner createSpinner() {
    	JSpinner spinner = new JSpinner(new SpinnerDateModel());
    	spinner.setBorder(BorderFactory.createEmptyBorder(6,0,6,0));
    	spinner.setMaximumSize(new Dimension(Integer.MAX_VALUE, 35));
    	JSpinner.DateEditor editor = new JSpinner.DateEditor(spinner, "dd/MM/yyyy");
    	spinner.setEditor(editor);
		return spinner;
    }
    
    public static JComboBox<String> createComboCountry() {
        String[] countryList = {"Seleccione el país", "Afganistán", "Albania", "Alemania", "Andorra", "Angola", "Antigua y Barbuda", "Arabia Saudita", "Argelia", "Argentina", "Armenia", "Australia", "Austria", "Azerbaiyán", "Bahamas", "Bangladés", "Baréin", "Bélgica", "Belice", "Benín", "Bielorrusia", "Birmania", "Bolivia", "Bosnia y Herzegovina", "Botsuana", "Brasil", "Brunéi", "Bulgaria", "Burkina Faso", "Burundi", "Bután", "Cabo Verde", "Camboya", "Camerún", "Canadá", "Catar", "Chad", "Chile", "China", "Chipre", "Colombia", "Comoras", "Corea del Norte", "Corea del Sur", "Costa de Marfil", "Costa Rica", "Croacia", "Cuba", "Dinamarca", "Dominica", "Ecuador", "Egipto", "El Salvador", "Emiratos Árabes Unidos", "Eritrea", "Eslovaquia", "Eslovenia", "España", "Estados Unidos", "Estonia", "Esuatini", "Etiopía", "Filipinas", "Finlandia", "Fiyi", "Francia", "Gabón", "Gambia", "Georgia", "Ghana", "Granada", "Grecia", "Guatemala", "Guinea", "Guinea-Bisáu", "Guinea Ecuatorial", "Guyana", "Haití", "Honduras", "Hungría", "India", "Indonesia", "Irak", "Irán", "Irlanda", "Islandia", "Islas Marshall", "Islas Salomón", "Israel", "Italia", "Jamaica", "Japón", "Jordania", "Kazajistán", "Kenia", "Kirguistán", "Kiribati", "Kuwait", "Laos", "Lesoto", "Letonia", "Líbano", "Liberia", "Libia", "Liechtenstein", "Lituania", "Luxemburgo", "Madagascar", "Malasia", "Malaui", "Maldivas", "Malí", "Malta", "Marruecos", "Mauricio", "Mauritania", "México", "Micronesia", "Moldavia", "Mónaco", "Mongolia", "Montenegro", "Mozambique", "Namibia", "Nauru", "Nepal", "Nicaragua", "Níger", "Nigeria", "Noruega", "Nueva Zelanda", "Omán", "Países Bajos", "Pakistán", "Palaos", "Palestina", "Panamá", "Papúa Nueva Guinea", "Paraguay", "Perú", "Polonia", "Portugal", "Reino Unido", "República Centroafricana", "República Checa", "República del Congo", "República Democrática del Congo", "República Dominicana", "Ruanda", "Rumania", "Rusia", "Samoa", "San Cristóbal y Nieves", "San Marino", "San Vicente y las Granadinas", "Santa Lucía", "Santo Tomé y Príncipe", "Senegal", "Serbia", "Seychelles", "Sierra Leona", "Singapur", "Siria", "Somalia", "Sri Lanka", "Sudáfrica", "Sudán", "Sudán del Sur", "Suecia", "Suiza", "Surinam", "Tailandia", "Taiwán", "Tanzania", "Tayikistán", "Timor Oriental", "Togo", "Tonga", "Trinidad y Tobago", "Túnez", "Turkmenistán", "Turquía", "Tuvalu", "Ucrania", "Uganda", "Uruguay", "Uzbekistán", "Vanuatu", "Vaticano", "Venezuela", "Vietnam", "Yemen", "Yibuti", "Zambia", "Zimbabue"};
    	JComboBox<String> combo = new JComboBox<>(countryList);
    	combo.setBorder(BorderFactory.createEmptyBorder(6,0,6,0));
    	combo.setFont(AppFont.normal());
    	combo.setMaximumSize(new Dimension(Integer.MAX_VALUE, 35));
    	return combo;
    }
    
    // CREAR RADIOBUTTON CON ESTILO
    public static JRadioButton createRadioButton(String text) {
        JRadioButton btn = new JRadioButton(text);
        btn.setOpaque(false);
        btn.setFont(AppFont.normal());
        return btn;
    }

    // CREAR GRUPO DE RADIOBUTTONS
    public static ButtonGroup createRadioGroup(JRadioButton... buttons) {
        ButtonGroup group = new ButtonGroup();
        for (JRadioButton btn : buttons) {
            group.add(btn);
        }
        return group;
    }

    // CREAR PANEL PARA RADIOBUTTONS
    public static JPanel createRadioPanel(JRadioButton... buttons) {
        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(0, 1));
        panel.setOpaque(false);
        panel.setAlignmentX(Component.CENTER_ALIGNMENT);
        panel.setMaximumSize(new Dimension(Integer.MAX_VALUE, 70));

        for (JRadioButton btn : buttons) {
            panel.add(btn);
        }

        return panel;
    }
    
    //bordes
    
    public static Border redBorder = BorderFactory.createCompoundBorder(
        BorderFactory.createLineBorder(UIColors.ERROR, 2),
        BorderFactory.createEmptyBorder(8, 10, 8, 10)
    );
        
    public static Border normalBorder = BorderFactory.createEmptyBorder(8, 10, 8, 10);
    
    //image icon
    public static ImageIcon loadIcon(String path, int size) {
        try {
            Image img = ImageIO.read(FormUtils.class.getResource(path));
            img = img.getScaledInstance(size, size, Image.SCALE_SMOOTH);
            return new ImageIcon(img);
        } catch (Exception e) {
            System.out.println("No está la imagen del ícono");
            return null;
        }
    }
    
	public static void addFocusEffect(JComponent field) {
	    field.addFocusListener(new FocusAdapter() {
	        @Override
	        public void focusGained(FocusEvent e) {
	            field.setBorder(BorderFactory.createCompoundBorder(
	                BorderFactory.createLineBorder(new Color(30,144,255), 2),
	                BorderFactory.createEmptyBorder(8,10,8,10)
	            ));
	        }
	        @Override
	        public void focusLost(FocusEvent e) {
	            field.setBorder(FormUtils.normalBorder);
	        }
	    });
	}
}