package utils;

import java.awt.Component;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;

import javax.imageio.ImageIO;
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
import javax.swing.SpinnerNumberModel;
import javax.swing.border.Border;

import components.RoundedBorder;
import components.RoundedPasswordField;
import components.RoundedSpinner;
import components.RoundedTextField;

public class FormUtils {

    public static JPanel createField(String labelText, JComponent field, JLabel errorLabel, String placeholder, int width) {

        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setOpaque(false);

        panel.setPreferredSize(new Dimension(width, 90));
        panel.setMaximumSize(new Dimension(width, 90));
        panel.setMinimumSize(new Dimension(width, 90));

        if (labelText != null) {
            JLabel label = new JLabel(labelText);
            label.setAlignmentX(Component.CENTER_ALIGNMENT);
            panel.add(label);
        }

        field.setAlignmentX(Component.CENTER_ALIGNMENT);
        field.setPreferredSize(new Dimension(width, 45));
        field.setMaximumSize(new Dimension(width, 45));
        field.setMinimumSize(new Dimension(width, 45));

        if (field instanceof JTextField) {
            ((JTextField) field).putClientProperty(
                "JTextField.placeholderText",
                placeholder
            );
        }

        errorLabel.setForeground(UIColors.ERROR);
        errorLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        panel.add(field);
        panel.add(Box.createRigidArea(new Dimension(0, 5)));
        panel.add(errorLabel);

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
    	JTextField field = new RoundedTextField();
    	field.setFont(AppFont.normal());
        field.setBackground(UIColors.FIELD);
        field.setBorder(normalBorder);
        return field;
    }
    
    public static JPasswordField createPasswordField() {
    	JPasswordField field = new RoundedPasswordField();
        field.setFont(AppFont.normal());
        field.setBackground(UIColors.FIELD);
        field.setBorder(normalBorder);
        return field;
    }
    
    public static JSpinner createDateField() {
    	JSpinner date = new RoundedSpinner(new SpinnerDateModel());
    	JSpinner.DateEditor editor = new JSpinner.DateEditor(date, "dd/MM/yyyy");
    	date.setBackground(UIColors.FIELD);
    	date.setBorder(normalBorder);
        date.setEditor(editor);
		return date;
    }
    
    public static JSpinner createNumberField() {
    	JSpinner number = new RoundedSpinner(new SpinnerNumberModel(1, 1, 10, 1));
    	number.setBackground(UIColors.FIELD);
    	number.setBorder(normalBorder);
		return number;
    }
    
    public static JComboBox<String> createComboCountry() {
        String[] countryList = {"Seleccione el país", "Afganistán", "Albania", "Alemania", "Andorra", "Angola", "Antigua y Barbuda", "Arabia Saudita", "Argelia", "Argentina", "Armenia", "Australia", "Austria", "Azerbaiyán", "Bahamas", "Bangladés", "Baréin", "Bélgica", "Belice", "Benín", "Bielorrusia", "Birmania", "Bolivia", "Bosnia y Herzegovina", "Botsuana", "Brasil", "Brunéi", "Bulgaria", "Burkina Faso", "Burundi", "Bután", "Cabo Verde", "Camboya", "Camerún", "Canadá", "Catar", "Chad", "Chile", "China", "Chipre", "Colombia", "Comoras", "Corea del Norte", "Corea del Sur", "Costa de Marfil", "Costa Rica", "Croacia", "Cuba", "Dinamarca", "Dominica", "Ecuador", "Egipto", "El Salvador", "Emiratos Árabes Unidos", "Eritrea", "Eslovaquia", "Eslovenia", "España", "Estados Unidos", "Estonia", "Esuatini", "Etiopía", "Filipinas", "Finlandia", "Fiyi", "Francia", "Gabón", "Gambia", "Georgia", "Ghana", "Granada", "Grecia", "Guatemala", "Guinea", "Guinea-Bisáu", "Guinea Ecuatorial", "Guyana", "Haití", "Honduras", "Hungría", "India", "Indonesia", "Irak", "Irán", "Irlanda", "Islandia", "Islas Marshall", "Islas Salomón", "Israel", "Italia", "Jamaica", "Japón", "Jordania", "Kazajistán", "Kenia", "Kirguistán", "Kiribati", "Kuwait", "Laos", "Lesoto", "Letonia", "Líbano", "Liberia", "Libia", "Liechtenstein", "Lituania", "Luxemburgo", "Madagascar", "Malasia", "Malaui", "Maldivas", "Malí", "Malta", "Marruecos", "Mauricio", "Mauritania", "México", "Micronesia", "Moldavia", "Mónaco", "Mongolia", "Montenegro", "Mozambique", "Namibia", "Nauru", "Nepal", "Nicaragua", "Níger", "Nigeria", "Noruega", "Nueva Zelanda", "Omán", "Países Bajos", "Pakistán", "Palaos", "Palestina", "Panamá", "Papúa Nueva Guinea", "Paraguay", "Perú", "Polonia", "Portugal", "Reino Unido", "República Centroafricana", "República Checa", "República del Congo", "República Democrática del Congo", "República Dominicana", "Ruanda", "Rumania", "Rusia", "Samoa", "San Cristóbal y Nieves", "San Marino", "San Vicente y las Granadinas", "Santa Lucía", "Santo Tomé y Príncipe", "Senegal", "Serbia", "Seychelles", "Sierra Leona", "Singapur", "Siria", "Somalia", "Sri Lanka", "Sudáfrica", "Sudán", "Sudán del Sur", "Suecia", "Suiza", "Surinam", "Tailandia", "Taiwán", "Tanzania", "Tayikistán", "Timor Oriental", "Togo", "Tonga", "Trinidad y Tobago", "Túnez", "Turkmenistán", "Turquía", "Tuvalu", "Ucrania", "Uganda", "Uruguay", "Uzbekistán", "Vanuatu", "Vaticano", "Venezuela", "Vietnam", "Yemen", "Yibuti", "Zambia", "Zimbabue"};
    	JComboBox<String> combo = new JComboBox<>(countryList);
    	combo.setFont(AppFont.normal());
    	combo.setBackground(UIColors.FIELD);
    	combo.setBorder(normalBorder);
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
    public static Border normalBorder = new RoundedBorder(UIColors.FIELD_BORDER, 2, 20);

	public static Border redBorder = new RoundedBorder(UIColors.ERROR, 2, 20);

	public static Border focusBorder = new RoundedBorder(UIColors.BACKGROUND, 2, 20);
                
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
    
    public static ImageIcon loadRectangularIcon(String path, int width, int height) {
        try {
            ImageIcon icon = new ImageIcon(
                    FormUtils.class.getResource(path)
            );

            Image scaled = icon.getImage().getScaledInstance(
                    width,
                    height,
                    Image.SCALE_SMOOTH
            );

            return new ImageIcon(scaled);

        } catch (Exception e) {
            System.out.println("Error cargando imagen: " + path);
            return null;
        }
    }
    
    public static void addFocusEffect(JComponent field, JLabel errorLabel) {
        field.addFocusListener(new FocusAdapter() {

            @Override
            public void focusGained(FocusEvent e) {
                field.setBorder(FormUtils.focusBorder);
            }

            @Override
            public void focusLost(FocusEvent e) {

                if (errorLabel != null && !errorLabel.getText().isEmpty()) {
                    field.setBorder(FormUtils.redBorder);
                } else {
                    field.setBorder(FormUtils.normalBorder);
                }
            }
        });
    }	
    
	//manejar errores
    public static void showError(JLabel label, JComponent field, String message) {
        label.setText(message);
        field.setBorder(FormUtils.redBorder);
    }

    public static void clearError(JLabel label, JComponent field) {
        label.setText("");
        field.setBorder(FormUtils.normalBorder);
    }

    public static void clearLabel(JLabel label) {
        label.setText("");
    }

}