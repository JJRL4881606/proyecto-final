package utils;

import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

import javax.imageio.ImageIO;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.ButtonGroup;
import javax.swing.ImageIcon;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JFormattedTextField;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JRadioButton;
import javax.swing.JSpinner;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.SpinnerDateModel;
import javax.swing.SpinnerNumberModel;
import javax.swing.border.Border;

import components.RoundedBorder;
import components.RoundedComboBox;
import components.RoundedPasswordField;
import components.RoundedSpinner;
import components.RoundedTextArea;
import components.RoundedTextField;

public class FormUtils {

    // Crea un campo de formulario con label de nombre, componente y label de error
    public static JPanel createField(String labelText, JComponent field, JLabel errorLabel, String placeholder, int width) {
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setOpaque(false);


        // poner tamaño fijo si el componente no es un textarea
        if (!(field instanceof JTextArea)) {
	        panel.setPreferredSize(new Dimension(width, 90));
	        panel.setMaximumSize(new Dimension(width, 90));
	        panel.setMinimumSize(new Dimension(width, 90));
        }
                
        if (!(field instanceof JTextArea)) {
        	field.setAlignmentX(Component.CENTER_ALIGNMENT);
         	field.setPreferredSize(new Dimension(width,45));
            field.setMaximumSize(new Dimension(width,45));
            field.setMinimumSize(new Dimension(width,45));
        }
        
        //crear label si de nombre si se ingresó un texto para eso
        if (labelText != null) {
            JLabel label = new JLabel(labelText);
            label.setAlignmentX(Component.CENTER_ALIGNMENT);
            panel.add(label);
        }

        //si es textfield agrega placeholder
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
    
    // crear labels para mensajes de error
    public static JLabel createErrorLabel() {
        JLabel label = new JLabel();
        label.setForeground(UIColors.ERROR);
        label.setFont(AppFont.small());
        label.setAlignmentX(Component.CENTER_ALIGNMENT);
        return label;
    }
    
    //crear un textfield
    public static JTextField createTextField() {
    	JTextField field = new RoundedTextField();
    	field.setFont(AppFont.normal());
        field.setBackground(UIColors.FIELD);
        field.setBorder(normalBorder);
        return field;
    }
    
    //crear textarea, usado en descripcion de roomtypes
    public static JTextArea createTextArea() {
    	JTextArea area = new RoundedTextArea();
        area.setLineWrap(true);
        area.setWrapStyleWord(true);
        area.setFont(AppFont.normal());
    	area.setBackground(UIColors.FIELD);
        area.setBorder(normalBorder);
        area.setMaximumSize(new Dimension(300, 200));
        area.setPreferredSize(new Dimension(300, 200));
		return area;
    }
    
    //crear campo donde va la ruta de imagenes selecciondas
    public static JTextField createImagePathField() {
    	JTextField field = new RoundedTextField();
    	field.setEditable(false);
        field.setAlignmentX(Component.CENTER_ALIGNMENT);
        field.setBackground(new Color(230,230,230));
        field.setForeground(Color.GRAY);
    	field.setFont(AppFont.normal());
        field.setBorder(normalBorder);
        field.setMaximumSize(new Dimension(300, 45));
        field.setPreferredSize(new Dimension(300, 45));
        return field;
    }
    
    //crear un campo de contraseña
    public static JPasswordField createPasswordField() {
    	JPasswordField field = new RoundedPasswordField();
        field.setFont(AppFont.normal());
        field.setBackground(UIColors.FIELD);
        field.setBorder(normalBorder);
        return field;
    }
    
    // crear un checbox para mostrar contraseña
    public static JCheckBox createCheckBox() {
    	JCheckBox checkbox = new JCheckBox();
    	checkbox.setOpaque(false);
	    checkbox.setFont(AppFont.small());
	    checkbox.setAlignmentX(Component.CENTER_ALIGNMENT);
	    checkbox.setText("Mostrar contraseña");
		return checkbox;
    }
    
    //crear selector de fecha
    public static JSpinner createDateField() {
    	JSpinner date = new RoundedSpinner(new SpinnerDateModel());
    	JSpinner.DateEditor editor = new JSpinner.DateEditor(date, "dd/MM/yyyy"); //formato dia mes año
    	date.setBackground(UIColors.FIELD);
    	date.setBorder(normalBorder);
        date.setEditor(editor);
		return date;
    }
    
    //crear campo de numero
    public static JSpinner createNumberField(int max) {
    	JSpinner number = new RoundedSpinner(new SpinnerNumberModel(1, 1, max, 1));
    	number.setBackground(UIColors.FIELD);
    	number.setBorder(normalBorder);
    	
        JSpinner.NumberEditor editor = new JSpinner.NumberEditor(number, "#"); //agregar #
        number.setEditor(editor);

        JFormattedTextField field = editor.getTextField(); //centrar numero
        field.setHorizontalAlignment(JTextField.CENTER);

		return number;
    }
    
    // crear combobox de países
    public static JComboBox<String> createComboCountry() {
        String[] countryList = {"Seleccione el país", "Afganistán", "Albania", "Alemania", "Andorra", "Angola", "Antigua y Barbuda", "Arabia Saudita", "Argelia", "Argentina", "Armenia", "Australia", "Austria", "Azerbaiyán", "Bahamas", "Bangladés", "Baréin", "Bélgica", "Belice", "Benín", "Bielorrusia", "Birmania", "Bolivia", "Bosnia y Herzegovina", "Botsuana", "Brasil", "Brunéi", "Bulgaria", "Burkina Faso", "Burundi", "Bután", "Cabo Verde", "Camboya", "Camerún", "Canadá", "Catar", "Chad", "Chile", "China", "Chipre", "Colombia", "Comoras", "Corea del Norte", "Corea del Sur", "Costa de Marfil", "Costa Rica", "Croacia", "Cuba", "Dinamarca", "Dominica", "Ecuador", "Egipto", "El Salvador", "Emiratos Árabes Unidos", "Eritrea", "Eslovaquia", "Eslovenia", "España", "Estados Unidos", "Estonia", "Esuatini", "Etiopía", "Filipinas", "Finlandia", "Fiyi", "Francia", "Gabón", "Gambia", "Georgia", "Ghana", "Granada", "Grecia", "Guatemala", "Guinea", "Guinea-Bisáu", "Guinea Ecuatorial", "Guyana", "Haití", "Honduras", "Hungría", "India", "Indonesia", "Irak", "Irán", "Irlanda", "Islandia", "Islas Marshall", "Islas Salomón", "Israel", "Italia", "Jamaica", "Japón", "Jordania", "Kazajistán", "Kenia", "Kirguistán", "Kiribati", "Kuwait", "Laos", "Lesoto", "Letonia", "Líbano", "Liberia", "Libia", "Liechtenstein", "Lituania", "Luxemburgo", "Madagascar", "Malasia", "Malaui", "Maldivas", "Malí", "Malta", "Marruecos", "Mauricio", "Mauritania", "México", "Micronesia", "Moldavia", "Mónaco", "Mongolia", "Montenegro", "Mozambique", "Namibia", "Nauru", "Nepal", "Nicaragua", "Níger", "Nigeria", "Noruega", "Nueva Zelanda", "Omán", "Países Bajos", "Pakistán", "Palaos", "Palestina", "Panamá", "Papúa Nueva Guinea", "Paraguay", "Perú", "Polonia", "Portugal", "Reino Unido", "República Centroafricana", "República Checa", "República del Congo", "República Democrática del Congo", "República Dominicana", "Ruanda", "Rumania", "Rusia", "Samoa", "San Cristóbal y Nieves", "San Marino", "San Vicente y las Granadinas", "Santa Lucía", "Santo Tomé y Príncipe", "Senegal", "Serbia", "Seychelles", "Sierra Leona", "Singapur", "Siria", "Somalia", "Sri Lanka", "Sudáfrica", "Sudán", "Sudán del Sur", "Suecia", "Suiza", "Surinam", "Tailandia", "Taiwán", "Tanzania", "Tayikistán", "Timor Oriental", "Togo", "Tonga", "Trinidad y Tobago", "Túnez", "Turkmenistán", "Turquía", "Tuvalu", "Ucrania", "Uganda", "Uruguay", "Uzbekistán", "Vanuatu", "Vaticano", "Venezuela", "Vietnam", "Yemen", "Yibuti", "Zambia", "Zimbabue"};
    	JComboBox<String> combo = new RoundedComboBox<>(countryList);
    	combo.setFont(AppFont.normal());
    	combo.setBackground(UIColors.FIELD);
    	combo.setBorder(normalBorder);
    	return combo;
    }
        
    // crear combobox, recibe un arreglo de opciones
    public static JComboBox<String> createCombo(String[] options) {
        JComboBox<String> combo = new RoundedComboBox<>(options);
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
    
    // permite solo numeros y separadores de fecha
    public static void onlyDateNumbers(JSpinner spinner) {

        JSpinner.DateEditor editor = (JSpinner.DateEditor) spinner.getEditor();
        JFormattedTextField textField = editor.getTextField();

        textField.addKeyListener(new KeyAdapter() {
        	
            @Override
            public void keyTyped(KeyEvent e) {

                char c = e.getKeyChar();

                // Bloquear cualquier caracter que no forme parte de una fecha
                if (!Character.isDigit(c) && c != '/' && c != '-') {
                    e.consume();
                }
            }
        });
    }
    
    //bordes de los campos
    public static Border normalBorder = new RoundedBorder(UIColors.FIELD_BORDER, 2, 20);
    
	public static Border redBorder = new RoundedBorder(UIColors.ERROR, 2, 20);
	
	public static Border focusBorder = new RoundedBorder(UIColors.BACKGROUND, 2, 20);
                
	// Carga un icono cuadrado y lo redimensiona
    public static ImageIcon loadIcon(String path, int size) {
        try {
            Image img = ImageIO.read(FormUtils.class.getResource(path)); //obtener imagen desde ruta indicada
            img = img.getScaledInstance(size, size, Image.SCALE_SMOOTH); // Ajusta la imagen al tamaño 
            
            return new ImageIcon(img);
            
        } catch (Exception e) {
            System.out.println("No está la imagen del ícono");
            return null;
        }
    }
    
    // Carga una imagen rectangular
    public static ImageIcon loadRectangularIcon(String path, int width, int height) {
        try {
            ImageIcon icon = new ImageIcon(FormUtils.class.getResource(path)); //obtener imagen
            Image scaled = icon.getImage().getScaledInstance(width, height,Image.SCALE_SMOOTH); //ajustar imagen

            return new ImageIcon(scaled);
            
        } catch (Exception e) {
            System.out.println("Error cargando imagen: " + path);
            return null;
        }
    }
    
    // Cambia el borde del componente cuando gana o pierde el foco
    public static void addFocusEffect(JComponent field, JLabel errorLabel) {
        field.addFocusListener(new FocusAdapter() {

            @Override
            public void focusGained(FocusEvent e) {
                field.setBorder(FormUtils.focusBorder);
            }

            @Override
            public void focusLost(FocusEvent e) {

                // Mantener borde rojo si existe un error de validación
                if (errorLabel != null && !errorLabel.getText().isEmpty()) {
                    field.setBorder(FormUtils.redBorder);
                } else {
                    field.setBorder(FormUtils.normalBorder);
                }
            }
        });
    }	
        
    // Muestra un mensaje de error y pone en rojo el borde del campo
    public static void showError(JLabel label, JComponent field, String message) {
        label.setText(message);
        field.setBorder(FormUtils.redBorder);
    }

    // limpia mensaje de error y pone el borde del campo normal
    public static void clearError(JLabel label, JComponent field) {
        label.setText("");
        field.setBorder(FormUtils.normalBorder);
    }

    //limpia el texto de una label
    public static void clearLabel(JLabel label) {
        label.setText("");
    }
}