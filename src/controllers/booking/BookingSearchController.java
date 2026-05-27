package controllers.booking;

import java.util.List;

import models.RoomType;
import repository.RoomTypeRepository;
import views.booking.BookingSearchView;

public class BookingSearchController {

    private BookingSearchView view;
    private RoomTypeRepository repository;

    public BookingSearchController(BookingSearchView view) {
        this.view = view;
        repository = new RoomTypeRepository();
        initListeners();
    }

    private void initListeners() {
        view.getSearchBar().getBtnSearch().addActionListener(e -> { handleSearch(); });
    }

    private void handleSearch() {
        int guests = view.getSearchBar().getGuests();
		
		List<RoomType> rooms = repository.getAvailableRoomTypes(guests);
		view.setRooms(rooms);
    }
}