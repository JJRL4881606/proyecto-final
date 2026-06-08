package controllers.roomtypes;

import java.awt.Window;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.SwingUtilities;

import components.RoomCard;
import repository.RoomTypeRepository;
import utils.Session;
import views.main.MainView;
import views.payment.PaymentWindow;
import views.roomtypes.ShowRoomsView;

//Controla la pantalla de habitaciones. carga los tipos disponibles y maneja los botones de cada card
public class ShowRoomsController {

    private ShowRoomsView view;
    private MainView mainView;
    private RoomTypeRepository repository;

    public ShowRoomsController(ShowRoomsView view, MainView mainView){

        this.view = view;
        this.mainView = mainView;

        repository = new RoomTypeRepository();

        // Cargar habitaciones primero para que las cards existan antes de asignarles eventos
        reloadRooms();
        loadRoomEvents();
    }
    
    // Recorre todas las cards y les asigna los listeners de sus botones
    // Hay que llamarlo cada vez que se recarguen las habitaciones porque el setRooms()
    // destruye y recrea las cards, asi que los listeners viejos se pierden
    private void loadRoomEvents(){
        for(RoomCard card : view.getRoomCards()){
        	
            // Botón ver detalles carga la info en el panel de detalles y manda a esa vista
            card.getBtnDetails().addActionListener(e -> {
                mainView.getRoomDetailsPanel().setRoomType(card.getRoomType());
                mainView.showView(MainView.ROOM_DETAILS);
            });
            
            // Botón reservar cierra la ventana actual y abre directamente la ventana de pago
            card.getBtnReserve().addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    // Cerrar ventana actual
                    Window window = SwingUtilities.getWindowAncestor(view);
                    if (window != null) {
                        window.dispose();
                    }

                    // Abrir nueva ventana de pago
                    PaymentWindow paymentWindow = new PaymentWindow(card.getRoomType(), Session.getCurrentUser());
                    paymentWindow.setVisible(true);
                }
            });
        }
    }
    
    // Consulta solo los tipos de habitación visibles (los que el admin no ha ocultado)
    // y los pasa a la vista para que los muestre como cards
    public void reloadRooms(){
        view.setRooms(
            repository.getVisibleRoomTypes()
        );
        loadRoomEvents();
    }
}