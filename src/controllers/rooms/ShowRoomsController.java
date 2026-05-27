package controllers.rooms;

import java.util.List;

import components.RoomCard;
import repository.RoomTypeRepository;
import views.main.MainView;
import views.rooms.ShowRoomsView;

public class ShowRoomsController {

    private ShowRoomsView view;
    private MainView mainView;
    private RoomTypeRepository repository;

    public ShowRoomsController(ShowRoomsView view, MainView mainView){

        this.view = view;
        this.mainView = mainView;

        repository = new RoomTypeRepository();

        loadRooms();
    }
    
    private void loadRooms(){
        view.setRooms(repository.getRoomTypes());
        loadRoomEvents();
    }
    
    private void loadRoomEvents(){
        for(RoomCard card : view.getRoomCards()){
            card.getBtnDetails().addActionListener(e -> {
                mainView.roomDetailsPanel.setRoom(card.getRoom());
                mainView.showView(MainView.ROOM_DETAILS);
            });

            card.getBtnReserve().addActionListener(e -> {
                mainView.bookingSearchPanel.setRooms(List.of(card.getRoom()));
                mainView.showView(MainView.BOOKING_SEARCH);
            });
        }
    }
}