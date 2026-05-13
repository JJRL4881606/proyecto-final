package controllers;

import java.io.IOException;
import java.util.List;

import models.RoomType;
import repository.RoomTypeRepository;
import views.ReservationView;

public class ReservationController {

    private ReservationView view;
    private RoomTypeRepository repository;

    public ReservationController(ReservationView view) {
        this.view = view;
        repository = new RoomTypeRepository();
        initListeners();
    }

    private void initListeners() {
        view.getSearchBar().getBtnSearch().addActionListener(e -> { handleSearch(); });
    }

    private void handleSearch() {
        try {
            int guests = view.getSearchBar().getGuests();
            
            List<RoomType> rooms =
                repository.getAvailableRoomTypes(
                        guests
                );
            
            view.setRooms(rooms);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}