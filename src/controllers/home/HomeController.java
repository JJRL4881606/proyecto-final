package controllers.home;

import java.util.List;

import components.RoomCard;
import components.SearchBar;
import controllers.rooms.RoomCardController;
import models.RoomType;
import repository.RoomTypeRepository;
import views.home.HomeView;
import views.main.MainView;

public class HomeController {

    private HomeView view;
    private MainView mainView;
    private RoomTypeRepository repository;
    
	public HomeController(HomeView view, MainView mainView) {
        this.view = view;
        this.mainView = mainView;
        this.repository = new RoomTypeRepository();

        loadRooms();
        initListeners();
	}
	
    private void initListeners() {
    	view.getSearchBar().getBtnSearch().addActionListener(e -> { handleSearch(); });
    	view.getBtnShowRooms().addActionListener(e -> { handleShowRooms(); });
    }
    
    private void loadRooms() {
        List<RoomType> rooms = repository.getFeaturedRoomTypes();

        view.setRooms(rooms);

        for(RoomCard card:view.getRoomCards()){
            new RoomCardController(card, mainView);
        }
    }
    
    private void handleSearch() {
        try {
            SearchBar homeSearch = view.getSearchBar();
            int guests = homeSearch.getGuests();

            List<RoomType> rooms = repository.getAvailableRoomTypes(guests, homeSearch.getCheckIn(), homeSearch.getCheckOut());

            // pasar datos al booking searchbar
            SearchBar bookingSearch = mainView.bookingSearchPanel.getSearchBar();
            bookingSearch.setCheckInDate(homeSearch.getCheckInDate());
            bookingSearch.setCheckOutDate(homeSearch.getCheckOutDate());
            bookingSearch.setGuests(homeSearch.getGuests());

            mainView.bookingSearchPanel.setRooms(rooms);
            mainView.bookingSearchController.loadRoomControllers();
            mainView.showView(MainView.BOOKING_SEARCH);
            mainView.getBtnHome().setEnabled(true);
            
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    private void handleShowRooms() {
        mainView.showView(MainView.SHOW_ROOMS);
    }
}