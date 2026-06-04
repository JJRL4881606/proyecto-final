package controllers.booking;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JOptionPane;

import models.RoomType;
import repository.RoomTypeRepository;
import views.booking.BookingSearchView;

public class BookingSearchController {

    private BookingSearchView view;
    private RoomTypeRepository repository;

    public BookingSearchController(BookingSearchView view){
        this.view = view;
        repository = new RoomTypeRepository();
        initListeners();
    }
    
    private void initListeners(){
        view.getSearchBar().getBtnSearch().addActionListener(e->handleSearch());
    }

    private void handleSearch(){

        int guests = view.getSearchBar().getGuests();
        
        if (guests <= 0 || guests > 10) {
        	JOptionPane.showMessageDialog(
    		    null,
    		    "Número de huéspedes inválido"
    		);
        	
        	return;
        }
        
        LocalDate checkIn = view.getSearchBar().getCheckIn();
        LocalDate checkOut = view.getSearchBar().getCheckOut();
        
        //validar fechas nulas o inválidas
        if (checkIn == null || checkOut == null || !checkOut.isAfter(checkIn)) {

        	JOptionPane.showMessageDialog(
    		    null,
    		    "Seleccione fechas válidas"
    		);
        	
        	view.setRooms(new ArrayList<>());
        	return;
        }
        
        //evitar estancias absurdas (falta mostrar al usuario que no se puede)
        if (ChronoUnit.DAYS.between(checkIn, checkOut) > 30) {

        	JOptionPane.showMessageDialog(
    		    null,
    		    "Máximo 30 días"
    		);
        	
        	view.setRooms(new ArrayList<>());
            return;
        }
                
        List<RoomType> rooms = repository.getAvailableRoomTypes(guests, checkIn, checkOut);

        view.setRooms(rooms);
    }
}