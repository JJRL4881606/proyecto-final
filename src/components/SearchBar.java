package components;

import java.awt.Color;
import java.awt.Dimension;
import java.util.Calendar;
import java.util.Date;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JLabel;
import javax.swing.JSpinner;
import javax.swing.JTextField;
import javax.swing.SpinnerDateModel;

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

        initInputRestrictions();
        initListeners();
        calculateNights();
    }
    
    private void initializeComponents() {
        setLayout(new BoxLayout(this, BoxLayout.X_AXIS));
	    setBackground(UIColors.CARD);
	    setBorder(BorderFactory.createEmptyBorder(25, 35, 25, 35));
	    setAlignmentX(CENTER_ALIGNMENT);
	    putClientProperty("FlatLaf.style", "arc:20");
	    setPreferredSize(new Dimension(900, 120));
	    setMaximumSize(new Dimension(900, 120));
	    
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
	    
	    spGuests = FormUtils.createNumberField();
	    lblGuestsError = FormUtils.createErrorLabel();
	    add(Box.createRigidArea(new Dimension(15, 0)));
	    add(FormUtils.createField("Huéspedes", spGuests, lblGuestsError, "", 130));

	    btnSearch = ButtonFactory.createBigButton(
	            "Buscar",
	            "/assets/img/btn-icons/button-search-icon.png",
	            "Haz click para buscar"
	    );
	    add(Box.createRigidArea(new Dimension(15, 0)));
	    add(btnSearch);	
	    add(Box.createRigidArea(new Dimension(0, 10)));
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
    
	private void initInputRestrictions() {
		FormUtils.onlyDateNumbers(spCheckInDate);
		FormUtils.onlyDateNumbers(spCheckOutDate);
		
        Date today = DateUtils.normalize(new Date());
        Date tomorrow = DateUtils.addDays(today, 1);

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
        ((JSpinner.DefaultEditor) spCheckOutDate.getEditor()).getTextField().setValue(tomorrow);
	}
	
	//	FECHAS
    private void validateDates() {
        Date today = DateUtils.normalize(new Date());

        Date checkIn = DateUtils.normalize((Date) spCheckInDate.getValue());
        Date checkOut = DateUtils.normalize((Date) spCheckOutDate.getValue());

        // evitar fechas pasadas (si permite hoy)
        if (checkIn.before(today)) {
        	spCheckInDate.setValue(today);
            checkIn = today;
        }

        // actualizar mínimo checkout = checkIn + 1 día
        Date minCheckOut = DateUtils.addDays(checkIn, 1);

        SpinnerDateModel modelOut = (SpinnerDateModel) spCheckOutDate.getModel();
        modelOut.setStart(minCheckOut);
        
        SpinnerDateModel modelIn = (SpinnerDateModel) spCheckInDate.getModel();
        modelIn.setStart(today);

        // evitar salida < entrada
        if (!checkOut.after(checkIn)) {
            checkOut = minCheckOut;
            spCheckOutDate.setValue(checkOut);
        }
        
        // calcular noches
        calculateNights();
    }
    
    private void addManualValidation() {
        addSpinnerEditorListener(spCheckInDate);
        addSpinnerEditorListener(spCheckOutDate);
    }

    private void addSpinnerEditorListener(JSpinner spinner) {
        JSpinner.DateEditor editor = (JSpinner.DateEditor) spinner.getEditor();
        editor.getTextField().addPropertyChangeListener("value", e -> {
            validateDates();
        });
    }
        
    private void calculateNights() {
        Date checkIn = (Date) spCheckInDate.getValue();
        Date checkOut = (Date) spCheckOutDate.getValue();

        long diff = checkOut.getTime() - checkIn.getTime();
        long nights = diff / (1000 * 60 * 60 * 24);

        // evitar negativos
        if (nights <= 0) {
        	txtNights.setText("1");
        } else {
        	txtNights.setText(String.valueOf(nights));
        }
    }

    // GETTERS
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

    public int getNights() {
        return Integer.parseInt(txtNights.getText());
    }

    public void setNights(String nights) {
        txtNights.setText(nights);
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