package views.booking;

import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.util.List;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JLabel;
import javax.swing.JPanel;

import components.RoomCard;
import components.SearchBar;
import components.WrapLayout;
import controllers.rooms.RoomCardController;
import models.RoomType;
import models.User;
import utils.AppFont;

@SuppressWarnings("serial")
public class BookingSearchView extends JPanel {

    private JPanel roomsContainer;
    private SearchBar searchBar;
    private User user;
    
    public BookingSearchView(User user) {
    		this.user = user;
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
    
    public void createTitle() {
        add(Box.createRigidArea(new Dimension(0, 20)));

        JLabel title = new JLabel("Resultados de búsqueda");
        title.setFont(AppFont.title());
        title.setAlignmentX(Component.CENTER_ALIGNMENT);
        add(title);
        
        add(Box.createRigidArea(new Dimension(0, 20)));
    }
    
    public void createRooms() {
	    	roomsContainer = new JPanel(new WrapLayout(FlowLayout.CENTER, 20, 20));
	    	roomsContainer.setMaximumSize(new Dimension(1200, Integer.MAX_VALUE));
	    	roomsContainer.setOpaque(false);

        add(roomsContainer);
        add(Box.createRigidArea(new Dimension(0, 20)));
    }

    public void setRooms(List<RoomType> rooms) {
        roomsContainer.removeAll();
        
        if (rooms.isEmpty()) {
            JLabel lblNoResults = new JLabel("Sin resultados");
            lblNoResults.setFont(AppFont.subtitle());
            lblNoResults.setAlignmentX(Component.CENTER_ALIGNMENT);

            roomsContainer.setLayout(new FlowLayout(FlowLayout.CENTER));
            roomsContainer.add(lblNoResults);
        } else {
            for (RoomType room : rooms) {
            		RoomCard card = new RoomCard(room);
                roomsContainer.add(card);
                new RoomCardController(card, room, user);
            }
        }

        roomsContainer.revalidate();
        roomsContainer.repaint();
    }
    
    public SearchBar getSearchBar() {
        return searchBar;
    }
}