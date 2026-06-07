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
    
    // Carga las habitaciones destacadas mostradas en el inicio
    // y crea el controlador para cada tarjeta

    public void loadRooms() {
    	
    	//obtener las tipos de habitaciones destacados
        List<RoomType> rooms = repository.getFeaturedRoomTypes();

        view.setRooms(rooms);
        
        //crear los controllers de las roomcards
        for(int i = 0; i < rooms.size(); i++){

            new RoomCardController(
                view.getRoomCards().get(i),
                
                mainView, // referencia al mainview para permitir moverse entre vistas
                
                rooms.get(i), // tipo de habitacion asociado a esta card especifica

                view.getUser() // usuario actual, usado para el proceso de reservación
            );
        }
    }
    
    // Realiza una búsqueda usando los filtros seleccionados
    // y manda los resultados a la bookingsearchview
    
    private void handleSearch() {

        SearchBar homeSearch = view.getSearchBar(); //el searchbar de home
        
    	//obtener cant de huespedes que ingresó del usuario
        int guests = homeSearch.getGuests();

        // Buscar los tipos de habitación que tengan al menos una
        // habitación disponible para el rango de fechas pedido
        List<RoomType> rooms = repository.getAvailableRoomTypes(
            guests,
            homeSearch.getCheckIn(),
            homeSearch.getCheckOut()
        );

        // mandar los filtros usados y resultados encontrados
        // a la vista de búsqueda para que genere las nuevas cards
        mainView.getBookingSearchPanel().loadSearchData(
            homeSearch.getCheckInDate(),
            homeSearch.getCheckOutDate(),
            homeSearch.getGuests(),
            rooms
        );

        // Crear un controlador para cada roomcard generada en la vista
        for(int i = 0; i < rooms.size(); i++) {

            new RoomCardController(

            	// toma las cards que se acaban de crear en BookingSearchView
                // y que representa una habitacion encontrada
                mainView.getBookingSearchPanel().getRoomCards().get(i),
    
                mainView, // referencia al mainview para permitir moverse entre vistas
                
                rooms.get(i), // tipo de habitacion asociado a esta card especifica

                view.getUser() // usuario actual, usado para el proceso de reservación
            );
        }


        // Mostrar la vista de resultados
        mainView.showView(MainView.BOOKING_SEARCH);
        mainView.getBtnHome().setEnabled(true);
    }
    
    // Manda la vista de todos los tipos de habitaciones
    private void handleShowRooms() {
        mainView.showView(MainView.SHOW_ROOMS);
    }    

}