package views;

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
import models.RoomType;
import utils.AppFont;

@SuppressWarnings("serial")
public class ReservationView extends JPanel {

    private JPanel roomsContainer;
    private SearchBar searchBar;
    
    public ReservationView() {
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
        roomsContainer = new JPanel(
                new FlowLayout(
                        FlowLayout.CENTER,
                        20,
                        20
                )
        );
        roomsContainer.setOpaque(false);

        add(roomsContainer);
        add(Box.createRigidArea(new Dimension(0, 20)));
    }

    public void setRooms(List<RoomType> rooms) {
        roomsContainer.removeAll();
        
        for (RoomType room : rooms) {
        	roomsContainer.add(new RoomCard(room));
        }

        roomsContainer.revalidate();
        roomsContainer.repaint();
    }
    
    public SearchBar getSearchBar() {
        return searchBar;
    }
}