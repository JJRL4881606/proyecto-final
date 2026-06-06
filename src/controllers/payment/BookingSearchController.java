package controllers.payment;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;

import components.RoomCard;
import controllers.roomtypes.RoomCardController;
import models.RoomType;
import repository.RoomTypeRepository;
import views.payment.BookingSearchView;

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
        	
        	view.showError("Número de huéspedes inválido");
        	
        	return;
        }
        
        LocalDate checkIn = view.getSearchBar().getCheckIn();
        LocalDate checkOut = view.getSearchBar().getCheckOut();
        
        //validar fechas nulas o inválidas
        if (checkIn == null || checkOut == null || !checkOut.isAfter(checkIn)) {

        	view.showError("Seleccione fechas válidas");
        	view.setRooms(new ArrayList<>());
        	
        	return;
        }
        
        //evitar estancias absurdas
        if (ChronoUnit.DAYS.between(checkIn, checkOut) > 30) {

        	view.showError("Máximo 30 días");
        	view.setRooms(new ArrayList<>());
        	
            return;
        }
                
        List<RoomType> rooms = repository.getAvailableRoomTypes(guests, checkIn, checkOut);

        view.setRooms(rooms);
        attachCardControllers(rooms);
    }
    
    private void attachCardControllers(List<RoomType> rooms) {

        List<RoomCard> cards = view.getRoomCards();

        for(int i = 0; i < rooms.size(); i++) {

            new RoomCardController(
                cards.get(i),
                view.getMainView(),
                rooms.get(i),
                view.getUser()
            );
        }
    }
    
    public void reloadRooms(){

        int guests = view.getSearchBar().getGuests();

        List<RoomType> rooms = repository.getAvailableRoomTypes(
            guests,
            view.getSearchBar().getCheckIn(),
            view.getSearchBar().getCheckOut()
        );
        
        if(view.getSearchBar().getCheckIn() == null || view.getSearchBar().getCheckOut() == null){
		    return;
		}

        view.setRooms(rooms);
        attachCardControllers(rooms);
    }
}