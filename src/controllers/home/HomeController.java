package controllers.home;

import java.util.List;

import components.SearchBar;
import controllers.roomtypes.RoomCardController;
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
    	view.getBtnReserve().addActionListener(e -> { handleSearch(); });
    }
    
    public void loadRooms() {
        List<RoomType> rooms = repository.getFeaturedRoomTypes();

        view.setRooms(rooms);
        
        //crear los controllers de las roomcards
        for(int i = 0; i < rooms.size(); i++){

            new RoomCardController(
                view.getRoomCards().get(i),
                mainView,
                rooms.get(i),
                view.getUser()
            );
        }
    }
    
    private void handleSearch() {

        SearchBar homeSearch = view.getSearchBar();

        int guests = homeSearch.getGuests();

        List<RoomType> rooms =
            repository.getAvailableRoomTypes(
                guests,
                homeSearch.getCheckIn(),
                homeSearch.getCheckOut()
            );

        mainView.getBookingSearchPanel().loadSearchData(
            homeSearch.getCheckInDate(),
            homeSearch.getCheckOutDate(),
            homeSearch.getGuests(),
            rooms
        );

        for(int i = 0; i < rooms.size(); i++) {

            new RoomCardController(
                mainView.getBookingSearchPanel().getRoomCards().get(i),
                mainView,
                rooms.get(i),
                view.getUser()
            );
        }

        mainView.showView(MainView.BOOKING_SEARCH);
        mainView.getBtnHome().setEnabled(true);
    }
    
    private void handleShowRooms() {
        mainView.showView(MainView.SHOW_ROOMS);
    }    

}