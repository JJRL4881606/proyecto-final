package controllers;

import java.io.IOException;
import java.util.List;

import models.Room;
import repository.RoomRepository;
import views.HomeView;

public class HomeController {

    private HomeView view;
    private RoomRepository repository;

    public HomeController(HomeView view) {
        this.view = view;
        this.repository = new RoomRepository();

        init();
    }

    private void init() {
        loadRooms();
        initEvents();
    }

    private void loadRooms() {
        try {
            List<Room> rooms = repository.getFeaturedRooms();
            view.setRooms(rooms);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void initEvents() {
        view.getBtnSearch().addActionListener(e -> {
            System.out.println("Buscar...");
        });

        view.getBtnSeeRooms().addActionListener(e -> {
            System.out.println("Ver más...");
        });
    }
}