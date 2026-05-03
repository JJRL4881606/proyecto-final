package controllers;

import java.io.IOException;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.swing.JSpinner;
import javax.swing.SpinnerDateModel;

import models.RoomType;
import repository.RoomTypeRepository;
import views.HomeView;

public class HomeController {

    private HomeView view;
    private RoomTypeRepository repository;

    public HomeController(HomeView view) {
        this.view = view;
        this.repository = new RoomTypeRepository();

        init();
    }

    private void init() {
        loadRooms();
        initEvents();
        initDateConstraints();
        addManualValidation();
        calculateNights();
    }
    
    private void loadRooms() {
        try {
            List<RoomType> rooms = repository.getFeaturedRoomTypes();
            view.setRooms(rooms);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void initEvents() {
        view.getBtnSearch().addActionListener(e -> {
            System.out.println("Buscar...");
        });

        view.getBtnSeeRooms().addActionListener(e -> {
            System.out.println("Ver más...");
        });
        
        
        view.getSpCheckInDate().addChangeListener(e -> {
            validateDates();
        });

        view.getSpCheckOutDate().addChangeListener(e -> {
            validateDates();
        });
    }
    
    private Date normalize(Date date) { //normalizar fechas (quita las horas)
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        cal.set(Calendar.HOUR_OF_DAY, 0);
        cal.set(Calendar.MINUTE, 0);
        cal.set(Calendar.SECOND, 0);
        cal.set(Calendar.MILLISECOND, 0);
        return cal.getTime();
    }
    
    private Date addDays(Date date, int days) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        cal.add(Calendar.DAY_OF_MONTH, days);
        return cal.getTime();
    }
    
    
    private void validateDates() {
        Date today = normalize(new Date());

        Date checkIn = normalize((Date) view.getSpCheckInDate().getValue());
        Date checkOut = normalize((Date) view.getSpCheckOutDate().getValue());

        // evitar fechas pasadas (si permite hoy)
        if (checkIn.before(today)) {
            view.getSpCheckInDate().setValue(today);
            checkIn = today;
        }

        // actualizar mínimo checkout = checkIn + 1 día
        Date minCheckOut = addDays(checkIn, 1);

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
    
    private void initDateConstraints() {
        Date today = normalize(new Date());
        Date tomorrow = addDays(today, 1);

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
    
    private void calculateNights() {
        Date checkIn = (Date) view.getSpCheckInDate().getValue();
        Date checkOut = (Date) view.getSpCheckOutDate().getValue();

        long diff = checkOut.getTime() - checkIn.getTime();

        long nights = diff / (1000 * 60 * 60 * 24);

        // evitar negativos
        if (nights <= 0) {
            view.getTxtNights().setText("1 noche(s)");
        } else {
            view.getTxtNights().setText(String.valueOf(nights) + " noche(s)");
        }
    }
}