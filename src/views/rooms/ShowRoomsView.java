package views.rooms;

import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.util.ArrayList;
import java.util.List;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JLabel;
import javax.swing.JPanel;

import components.RoomCard;
import components.WrapLayout;
import models.RoomType;
import utils.AppFont;

@SuppressWarnings("serial")
public class ShowRoomsView extends JPanel {

    private JPanel roomsContainer;
    private List<RoomCard> roomCards = new ArrayList<>();

    public ShowRoomsView() {
        setOpaque(false);
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));

        initializeComponents();
    }

    private void initializeComponents() {
        add(Box.createRigidArea(new Dimension(0,40)));

        JLabel title = new JLabel("Todas nuestras habitaciones");
        title.setFont(AppFont.title());
        title.setAlignmentX(Component.CENTER_ALIGNMENT);
        add(title);
        
        add(Box.createRigidArea(new Dimension(0,20)));

        roomsContainer = new JPanel(new WrapLayout(FlowLayout.CENTER, 20, 20));
        roomsContainer.setOpaque(false);
        roomsContainer.setMaximumSize( new Dimension(1200, Integer.MAX_VALUE));

        add(roomsContainer);
        add(Box.createRigidArea(new Dimension(0,20)));
    }

    public void setRooms(List<RoomType> rooms){
        roomsContainer.removeAll();

        if(rooms.isEmpty()){
        	
            JLabel lblNoRooms = new JLabel("No hay habitaciones disponibles");
            lblNoRooms.setFont(AppFont.subtitle());
            lblNoRooms.setAlignmentX(Component.CENTER_ALIGNMENT);

            roomsContainer.add(lblNoRooms);
            
        }else{
            for(RoomType room : rooms){
            	RoomCard card = new RoomCard(room);

            	roomCards.add(card);

            	roomsContainer.add(card);
            }
        }

        roomsContainer.revalidate();
        roomsContainer.repaint();
    }
    
    public List<RoomCard> getRoomCards(){
        return roomCards;
    }
}