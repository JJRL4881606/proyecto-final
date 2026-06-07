package components;

import java.awt.Color;
import java.awt.Dimension;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.temporal.ChronoUnit;
import java.util.Calendar;
import java.util.Date;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JLabel;
import javax.swing.JSpinner;
import javax.swing.JTextField;
import javax.swing.SpinnerDateModel;

import utils.AppFont;
import utils.ButtonFactory;
import utils.DateUtils;
import utils.FormUtils;
import utils.UIColors;

@SuppressWarnings("serial")
public class SearchBar extends RoundedPanel {

    private JSpinner spCheckInDate;
    private JSpinner spCheckOutDate;
    private JTextField txtNights;
    private JSpinner spGuests;
    private JLabel lblCheckInDateError;
    private JLabel lblCheckOutDateError;
    private JLabel lblNightsError;
    private JLabel lblGuestsError;

    private RoundedButton btnSearch;

    public SearchBar() {
        super(30);

        initializeComponents();

        // configurar restricciones, validaciones y valores iniciales
        initInputRestrictions();
        initListeners();

        // Mostrar la cantidad de noches segun las fechas iniciales cargadas
        calculateNights();
    }
    
    private void initializeComponents() {
        setLayout(new BoxLayout(this, BoxLayout.X_AXIS));
	    setBackground(UIColors.CARD);
	    setBorder(BorderFactory.createEmptyBorder(25, 35, 25, 35));
	    setAlignmentX(CENTER_ALIGNMENT);
	    putClientProperty("FlatLaf.style", "arc:20");
	    
	    Dimension size = new Dimension(1000,120);
	    setPreferredSize(size);
	    setMinimumSize(size);
	    setMaximumSize(size);
	    
	    add(Box.createHorizontalGlue());
	    
	    spCheckInDate = FormUtils.createDateField();
	    lblCheckInDateError = FormUtils.createErrorLabel();
	    add(FormUtils.createField("Entrada", spCheckInDate, lblCheckInDateError, "", 180));
	    add(Box.createRigidArea(new Dimension(15, 0)));
	    	    
	    spCheckOutDate = FormUtils.createDateField();
	    lblCheckOutDateError = FormUtils.createErrorLabel();
	    add(FormUtils.createField("Salida", spCheckOutDate, lblCheckOutDateError, "", 180));
	    add(Box.createRigidArea(new Dimension(15, 0)));
	    		 
	    txtNights = FormUtils.createTextField();
	    lblNightsError = FormUtils.createErrorLabel();
	    txtNights.setEditable(false);
	    txtNights.setBackground(new Color(245,245,245));
	    add(Box.createRigidArea(new Dimension(15, 0)));
	    add(FormUtils.createField("Noches", txtNights, lblNightsError, "", 130));
	    
	    int max = 10;
		spGuests = FormUtils.createNumberField(max);
	    lblGuestsError = FormUtils.createErrorLabel();
	    add(Box.createRigidArea(new Dimension(15, 0)));
	    add(FormUtils.createField("Huéspedes", spGuests, lblGuestsError, "", 130));

	    btnSearch = ButtonFactory.createGoldButton(
	            "Buscar",
	            "/assets/img/btn-icons/button-search-icon.png",
	            "Haz click para buscar"
	    );
	    btnSearch.setFont(AppFont.subtitle());
	    
	    Dimension btn = new Dimension(160,50);
	    btnSearch.setPreferredSize(btn);
	    btnSearch.setMinimumSize(btn);
	    btnSearch.setMaximumSize(btn);
	    
	    add(Box.createRigidArea(new Dimension(30, 0)));
	    add(btnSearch);	
	    add(Box.createHorizontalGlue());
    }
    
    private void initListeners() {
        spCheckInDate.addChangeListener(e -> {
            validateDates();
        });

        spCheckOutDate.addChangeListener(e -> {
            validateDates();
        });

        addManualValidation();
    }
    
    //configura las restricciones de los campos de fecha y establece los valores minimos permitidos
    
	private void initInputRestrictions() {
		
		// Permitir unicamente la captura de números en los editores de fecha para evitar formatos inválidos
		FormUtils.onlyDateNumbers(spCheckInDate);
		FormUtils.onlyDateNumbers(spCheckOutDate);
		
		// La fecha minimaa de entrada es hoy
		Date today = DateUtils.normalize(new Date());

		// La salida inicia por default un dia después
		Date tomorrow = DateUtils.addDays(today, 1);
		
		// configurar los modelos para impedir seleccionar fechas antsriores a las permitidas
        SpinnerDateModel checkInModel = new SpinnerDateModel(
            today,
            today,
            null,
            Calendar.DAY_OF_MONTH
        );

        SpinnerDateModel checkOutModel = new SpinnerDateModel(
            tomorrow,   
            tomorrow,   
            null,
            Calendar.DAY_OF_MONTH
        );

        spCheckInDate.setModel(checkInModel);
        spCheckOutDate.setModel(checkOutModel);
        spCheckInDate.setValue(today);
        spCheckOutDate.setValue(tomorrow);
        //obtiene el editor, accede al txtfield y le pone el valor de tomorrow
        ((JSpinner.DefaultEditor) spCheckOutDate.getEditor()).getTextField().setValue(tomorrow); 
	}
	
	// Valida la coherencia entre las fechas seleccionadas
	// y corrige automaticamente valores inválidos
	
    private void validateDates() {
    	
    	// Obtener las fechas normalizadas sin horas para hacer comparaciones solo con el diia
    	Date today = DateUtils.normalize(new Date());

    	Date checkIn = DateUtils.normalize(
    	    (Date) spCheckInDate.getValue()
    	);

    	Date checkOut = DateUtils.normalize(
    	    (Date) spCheckOutDate.getValue()
    	);
    	
        //checar fechas nulas mientras el usuario aun modifica el valr
        if (checkIn == null || checkOut == null) return;

        // evitar fechas pasadas a hoy
        if (checkIn.before(today)) {
        	spCheckInDate.setValue(today);
            checkIn = today;
        }
        
        // limitar las estancias a un max de 30 noches
        long days = ChronoUnit.DAYS.between(getCheckIn(), getCheckOut());

    	if (days > 30) {
    	    spCheckOutDate.setValue(DateUtils.addDays(checkIn,30));
    	}
        
	    // La salida siempre debe ser minimo un día mas a la fecha de entrada
	    Date minCheckOut = DateUtils.addDays(checkIn, 1);

	    // Actualizar los limites de los spinners para impedir selecciones invalidas
        SpinnerDateModel modelOut = (SpinnerDateModel) spCheckOutDate.getModel();
        modelOut.setStart(minCheckOut);
        
        SpinnerDateModel modelIn = (SpinnerDateModel) spCheckInDate.getModel();
        modelIn.setStart(today);

        // corregir auomatico cuando la salida sea igual o antes de la entrada
        if(!checkOut.after(checkIn) && !checkOut.equals(minCheckOut)){
        	    spCheckOutDate.setValue(minCheckOut);
        }       
        
        //refrescar la cantidad de noches mostrada
        calculateNights();
    }
    
    // Agrega mas validacion cuando el usuario escribe manualmente una fecha en vez de usar los controles del spinner
    private void addManualValidation() {
        addSpinnerEditorListener(spCheckInDate);
        addSpinnerEditorListener(spCheckOutDate);
    }

    // Escucha cambios hechos directo sobre el editor de texto interno del spinner
    private void addSpinnerEditorListener(JSpinner spinner) {
    	//obtiene el editor del listener
        JSpinner.DateEditor editor = (JSpinner.DateEditor) spinner.getEditor();
        
        // Revalidar las fechas cuando el valor del editor cambie manualmente
        editor.getTextField().addPropertyChangeListener("value", e -> {
            validateDates();
        });
    }    
   
    // Calcula la cantidad de noches entre la fecha
    // de entrada y salida y la muestra en su campo
    private void calculateNights(){

    	// Obtener la diferencia de dias entre las dos fechas
    	long nights = ChronoUnit.DAYS.between(
    	    getCheckIn(),
    	    getCheckOut()
    	);
    	
    	// para que siempre se muestre al menos una noche
    	txtNights.setText(
    	    String.valueOf(Math.max(1, nights))
    	);
    }
    
    // GETTERS Y SETTERS
    public LocalDate getCheckIn(){
        return getCheckInDate()
            .toInstant()
            .atZone(ZoneId.systemDefault())
            .toLocalDate();
    }

    public LocalDate getCheckOut(){
        return getCheckOutDate()
            .toInstant()
            .atZone(ZoneId.systemDefault())
            .toLocalDate();
    }
    
    public JSpinner getSpCheckInDate() {
        return spCheckInDate;
    }

    public JSpinner getSpCheckOutDate() {
        return spCheckOutDate;
    }

    public JTextField getTxtNights() {
        return txtNights;
    }

    public JSpinner getSpGuests() {
        return spGuests;
    }

    public RoundedButton getBtnSearch() {
        return btnSearch;
    }
    
    public int getGuests() {
        return (int) spGuests.getValue();
    }

    public Date getCheckInDate() {
        return (Date) spCheckInDate.getValue();
    }

    public Date getCheckOutDate() {
        return (Date) spCheckOutDate.getValue();
    }
    
    public void setCheckInDate(Date date) {
        spCheckInDate.setValue(date);
    }

    public void setCheckOutDate(Date date) {
        spCheckOutDate.setValue(date);
    }

    public void setGuests(int guests) {
        spGuests.setValue(guests);
    }
}