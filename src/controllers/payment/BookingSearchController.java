package controllers.payment;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;

import components.RoomCard;
import controllers.roomtypes.RoomCardController;
import models.RoomType;
import repository.RoomTypeRepository;
import views.payment.BookingSearchView;

//controla la lógica de la vista de buscador
//valida los datos que ingresa el usuario y consulta las habitaciones disponibles
public class BookingSearchController {

    private BookingSearchView view;
    private RoomTypeRepository repository;

    public BookingSearchController(BookingSearchView view){
        this.view = view;
        repository = new RoomTypeRepository();
        initListeners();
    }
    
    private void initListeners(){
        view.getSearchBar().getBtnSearch().addActionListener(e->handleSearch());
    }

    // Valida los datos ingresados (huéspedes, fechas, duración) antes de consultar
    // la base de datos. Si algo está mal, muestra un error y no busca

    private void handleSearch(){

        int guests = view.getSearchBar().getGuests();
        
        // El numero de huespedes tiene que ser entre 1 y 10
        if (guests <= 0 || guests > 10) {
        	view.showError("Número de huéspedes inválido");
        	return;
        }
        
        LocalDate checkIn = view.getSearchBar().getCheckIn();
        LocalDate checkOut = view.getSearchBar().getCheckOut();
        
        // Las fechas no pueden ser nulas y el checkout tiene que ser después del checkin
        if (checkIn == null || checkOut == null || !checkOut.isAfter(checkIn)) {

        	view.showError("Seleccione fechas válidas");
        	view.setRooms(new ArrayList<>());
        	
        	return;
        }
        
        // No se permiten estancias de más de 30 días
        if (ChronoUnit.DAYS.between(checkIn, checkOut) > 30) {

        	view.showError("Máximo 30 días");
        	view.setRooms(new ArrayList<>());
        	
            return;
        }
                
        // Si todo esta bien, busca los tipos de habitaciones disponibles y los muestra
        List<RoomType> rooms = repository.getAvailableRoomTypes(guests, checkIn, checkOut);

        view.setRooms(rooms);
        attachCardControllers(rooms);
    }
    
    // Le pone a roomCard su propio controlador después de cargar las habitaciones
    // i sirve para emparejar cada RoomType con su RoomCard
    private void attachCardControllers(List<RoomType> rooms) {

        List<RoomCard> cards = view.getRoomCards();

        for(int i = 0; i < rooms.size(); i++) {

            new RoomCardController(
                cards.get(i),
                view.getMainView(),
                rooms.get(i),
                view.getUser()
            );
        }
    }
    
	 // Vuelve a buscar y mostrar habitaciones con los mismos datos que ya están en la barra de búsqueda
	 // Se usa cuando hay que actualizar la vista sin que el usuario presione buscar de nuevo
	 // Si las fechas están vacías, no hace nada para evitar errores
    public void reloadRooms(){
        
        // primero checa que haya fechas antes de consultar
        if(view.getSearchBar().getCheckIn() == null || view.getSearchBar().getCheckOut() == null){
		    return;
		}

        int guests = view.getSearchBar().getGuests();

        List<RoomType> rooms = repository.getAvailableRoomTypes(
            guests,
            view.getSearchBar().getCheckIn(),
            view.getSearchBar().getCheckOut()
        );

        view.setRooms(rooms);
        attachCardControllers(rooms);
    }
}