package controllers.rooms;

import java.util.List;

import components.RoomCard;
import components.SearchBar;
import views.main.MainView;

public class RoomCardController {

    private RoomCard roomCard;
    private MainView mainView;

    public RoomCardController(RoomCard roomCard, MainView mainView) {
        this.roomCard = roomCard;
        this.mainView = mainView;

        initListeners();
    }

    private void initListeners() {

        // detalles
        roomCard.getBtnDetails().addActionListener(e -> {
            mainView.roomDetailsPanel.setRoom(
                roomCard.getRoom()
            );

            mainView.showView(
                MainView.ROOM_DETAILS
            );
        });

        // reservar
        roomCard.getBtnReserve().addActionListener(e -> {

            SearchBar bookingSearch =
                mainView.bookingSearchPanel.getSearchBar();

            bookingSearch.setGuests(
                roomCard.getRoom().getCapacity()
            );

            mainView.bookingSearchPanel.setRooms(
                List.of(roomCard.getRoom())
            );

            mainView.showView(
                MainView.BOOKING_SEARCH
            );
        });
    }
}