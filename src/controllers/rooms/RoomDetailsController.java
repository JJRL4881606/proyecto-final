package controllers.rooms;

import java.util.List;

import views.main.MainView;
import views.rooms.RoomDetailsView;
import components.SearchBar;

public class RoomDetailsController {

    private RoomDetailsView view;
    private MainView mainView;

    public RoomDetailsController(RoomDetailsView view, MainView mainView){

        this.view = view;
        this.mainView = mainView;

        initListeners();
    }

    private void initListeners(){

        view.getBtnReserve().addActionListener(e -> {

            if(view.getRoom() == null){
                return;
            }

            SearchBar bookingSearch = mainView.bookingSearchPanel.getSearchBar();
            bookingSearch.setGuests(view.getRoom().getCapacity());

            mainView.bookingSearchPanel.setRooms(List.of(view.getRoom()));
            mainView.showView(MainView.BOOKING_SEARCH);
        });
    }
}