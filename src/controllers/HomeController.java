package controllers;

import java.util.List;

import components.SearchBar;
import models.RoomType;
import repository.RoomTypeRepository;
import views.HomeView;
import views.MainView;

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
    	view.getBtnSeeRooms().addActionListener(e -> { handleSeeRooms(); });
    }
    
    private void loadRooms() {
        List<RoomType> rooms = repository.getFeaturedRoomTypes();
		view.setRooms(rooms);
    }
    
    private void handleSearch() {
        try {
            SearchBar homeSearch = view.getSearchBar();
            int guests = homeSearch.getGuests();

            List<RoomType> rooms =
                    repository.getAvailableRoomTypes(
                            guests
                    );

            // pasar datos al reservation searchbar
            SearchBar reservationSearch = mainView.reservationPanel.getSearchBar();
            reservationSearch.setCheckInDate(homeSearch.getCheckInDate());
            reservationSearch.setCheckOutDate(homeSearch.getCheckOutDate());
            reservationSearch.setGuests(homeSearch.getGuests());

            mainView.reservationPanel.setRooms(rooms);
            mainView.showView(MainView.RESERVATIONS);
            mainView.btnHome.setEnabled(true);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    private void handleSeeRooms(){
        System.out.println("Presionó botón ver más");
    }
}