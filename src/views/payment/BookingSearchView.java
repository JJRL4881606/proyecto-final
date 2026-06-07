package views.payment;

import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import components.RoomCard;
import components.SearchBar;
import components.WrapLayout;
import models.RoomType;
import models.User;
import utils.AppFont;
import views.main.MainView;

@SuppressWarnings("serial")

// aqui el usuario busca habitaciones disponibles
// se muestra una barra de busqueda y las roomcards
public class BookingSearchView extends JPanel {

    private JPanel roomsContainer;
    private SearchBar searchBar;
    private User user;
    private List<RoomCard> roomCards = new ArrayList<>();
    private MainView mainView;
    
    public BookingSearchView(User user, MainView mainView) {
        this.user = user;
        this.mainView = mainView;

        setOpaque(false);
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));

        initializeComponents();
    }

    private void initializeComponents() {
    	add(Box.createRigidArea(new Dimension(0, 20)));
    	searchBar = new SearchBar();
    	add(searchBar);
    	add(Box.createRigidArea(new Dimension(0, 20)));
    	
    	createTitle();
    	createRooms();
    }
    
    private void createTitle() {
        add(Box.createRigidArea(new Dimension(0, 20)));

        JLabel title = new JLabel("Resultados de búsqueda");
        title.setFont(AppFont.title());
        title.setAlignmentX(Component.CENTER_ALIGNMENT);
        add(title);
        
        add(Box.createRigidArea(new Dimension(0, 20)));
    }
    
    // Crea el panel donde se van a poner las tarjetas de habitaciones
    // con WrapLayout para que se acomoden en filas
    private void createRooms() {
    	roomsContainer = new JPanel(new WrapLayout(FlowLayout.CENTER, 20, 20));
    	roomsContainer.setMaximumSize(new Dimension(1200, Integer.MAX_VALUE));
    	roomsContainer.setOpaque(false);

        add(roomsContainer);
        add(Box.createRigidArea(new Dimension(0, 20)));
    }

    // Reemplaza las tarjetas mostradas con la nueva lista de tipos de habitaciones
    // Si la lista viene vacia, muestra label de sin resultados
    
    public void setRooms(List<RoomType> rooms) {

        roomsContainer.removeAll();
        roomCards.clear();
        
        roomsContainer.setLayout(new WrapLayout( FlowLayout.CENTER, 20, 20));

        if (rooms.isEmpty()) {
            // Si no hay resultados, cambia el layout a uno simple y muestra el mensaje
            JLabel lblNoResults = new JLabel("Sin resultados");
            lblNoResults.setFont(AppFont.subtitle());
            lblNoResults.setAlignmentX(Component.CENTER_ALIGNMENT);

            roomsContainer.setLayout(new FlowLayout(FlowLayout.CENTER));
            roomsContainer.add(lblNoResults);

        } else {

            for (RoomType room : rooms) {
                RoomCard card = new RoomCard(room);
                roomCards.add(card);
                roomsContainer.add(card); //agrega el card al contenedor
            }
        }

        roomsContainer.revalidate();
        roomsContainer.repaint();
    }
    
    // Rellena la barra de búsqueda con datos ya ingresados por el usuario
    // y carga las habitaciones correspondientes
    
    public void loadSearchData(Date checkIn, Date checkOut, int guests, List<RoomType> rooms){
    	
        searchBar.setCheckInDate(checkIn);
        searchBar.setCheckOutDate(checkOut);
        searchBar.setGuests(guests);

        setRooms(rooms);
    }
    
    public void showError(String message){
        JOptionPane.showMessageDialog(
            null,
            message
        );
    }
    
    //getters
    public SearchBar getSearchBar() {
        return searchBar;
    }
    
    public List<RoomCard> getRoomCards(){
        return roomCards;
    }
    
    public User getUser() {
        return user;
    }
    
    public MainView getMainView() {
        return mainView;
    }
}