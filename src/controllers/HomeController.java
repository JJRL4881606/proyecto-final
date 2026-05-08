package controllers;

import java.io.IOException;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.swing.JSpinner;
import javax.swing.SpinnerDateModel;

import models.RoomType;
import repository.RoomTypeRepository;
import utils.DateUtils;
import utils.FormUtils;
import views.HomeView;

public class HomeController {

    private HomeView view;
    private RoomTypeRepository repository;

    public HomeController(HomeView view) {
        this.view = view;
        this.repository = new RoomTypeRepository();
        
        loadRooms();
        initListeners();
    }

    private void initListeners() {
        view.getBtnSearch().addActionListener(e -> { handleSearch(); });
        view.getBtnSeeRooms().addActionListener(e -> { handleSeeRooms(); });
        view.getSpCheckInDate().addChangeListener(e -> { validateDates(); });
        view.getSpCheckOutDate().addChangeListener(e -> { validateDates(); });

        addManualValidation();
        calculateNights();
		initInputRestrictions();
    }
    
    private void loadRooms() {
        try {
            List<RoomType> rooms = repository.getFeaturedRoomTypes();
            view.setRooms(rooms);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    private void handleSearch() {
        System.out.println("Presionó botón buscar");
    }
    
    private void handleSeeRooms(){
        System.out.println("Presionó botón ver más");
    }

	private void initInputRestrictions() {
		FormUtils.onlyDateNumbers(view.getSpCheckInDate());
		FormUtils.onlyDateNumbers(view.getSpCheckOutDate());
		
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

        view.getSpCheckInDate().setModel(checkInModel);
        view.getSpCheckOutDate().setModel(checkOutModel);
        view.getSpCheckInDate().setValue(today);
        view.getSpCheckOutDate().setValue(tomorrow);
        ((JSpinner.DefaultEditor) view.getSpCheckOutDate().getEditor()).getTextField().setValue(tomorrow);
	}
	
	//	FECHAS
    private void validateDates() {
        Date today = DateUtils.normalize(new Date());

        Date checkIn = DateUtils.normalize((Date) view.getSpCheckInDate().getValue());
        Date checkOut = DateUtils.normalize((Date) view.getSpCheckOutDate().getValue());

        // evitar fechas pasadas (si permite hoy)
        if (checkIn.before(today)) {
            view.getSpCheckInDate().setValue(today);
            checkIn = today;
        }

        // actualizar mínimo checkout = checkIn + 1 día
        Date minCheckOut = DateUtils.addDays(checkIn, 1);

        SpinnerDateModel modelOut = (SpinnerDateModel) view.getSpCheckOutDate().getModel();
        modelOut.setStart(minCheckOut);
        
        SpinnerDateModel modelIn = (SpinnerDateModel) view.getSpCheckInDate().getModel();
        modelIn.setStart(today);

        // evitar salida < entrada
        if (!checkOut.after(checkIn)) {
            checkOut = minCheckOut;
            view.getSpCheckOutDate().setValue(checkOut);
        }
        
        // calcular noches
        calculateNights();
    }
    
    private void addManualValidation() {
        addSpinnerEditorListener(view.getSpCheckInDate());
        addSpinnerEditorListener(view.getSpCheckOutDate());
    }

    private void addSpinnerEditorListener(JSpinner spinner) {
        JSpinner.DateEditor editor = (JSpinner.DateEditor) spinner.getEditor();
        editor.getTextField().addPropertyChangeListener("value", e -> {
            validateDates();
        });
    }
        
    private void calculateNights() {
        Date checkIn = (Date) view.getSpCheckInDate().getValue();
        Date checkOut = (Date) view.getSpCheckOutDate().getValue();

        long diff = checkOut.getTime() - checkIn.getTime();

        long nights = diff / (1000 * 60 * 60 * 24);

        // evitar negativos
        if (nights <= 0) {
            view.getTxtNights().setText("1");
        } else {
            view.getTxtNights().setText(String.valueOf(nights));
        }
    }
}