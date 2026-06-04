package controllers.rooms;

import java.awt.Window;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.SwingUtilities;

import components.RoomCard;
import repository.RoomTypeRepository;
import utils.Session;
import views.main.MainView;
import views.payment.PaymentWindow;
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
                mainView.roomDetailsPanel.setRoomType(card.getRoomType());
                mainView.showView(MainView.ROOM_DETAILS);
            });
            
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
}