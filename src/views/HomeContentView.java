package views;

import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSpinner;
import javax.swing.JTextField;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.io.IOException;
import java.util.List;

import components.RoundedButton;
import components.RoundedImagePanel;
import components.RoundedPanel;
import models.Room;
import repository.RoomRepository;
import utils.AppFont;
import utils.ButtonFactory;
import utils.FormUtils;
import utils.UIColors;

@SuppressWarnings("serial")
public class HomeContentView extends JPanel{
	
	JSpinner spCheckInDate;
	JSpinner spCheckOutDate;
	JTextField txtNights;
	JSpinner spGuests;
	
	JLabel lblCheckInDateError;
	JLabel lblCheckOutDateError;
	JLabel lblNightsError;
	JLabel lblGuestsError;
	RoundedButton btnSearch;
	RoundedButton btnSeeRooms;

	public HomeContentView() {
	    
	    this.setBackground(Color.WHITE);
	    this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
	    
	    initializeComponents();
	    setVisible(true);
	}
	
	public void initializeComponents() {
	    add(wrapSection(createSearchBar()));
	    add(wrapSection(createRooms()));
	}
	
    public JPanel createSearchBar() {
    	
	    JPanel searchBar = new RoundedPanel(30);
	    searchBar.setLayout(new BoxLayout(searchBar, BoxLayout.X_AXIS));
	    searchBar.setBackground(UIColors.CARD);
	    searchBar.setBorder(BorderFactory.createEmptyBorder(25, 35, 25, 35));
	    searchBar.setAlignmentX(CENTER_ALIGNMENT);
	    searchBar.putClientProperty("FlatLaf.style", "arc:20");
	    searchBar.setPreferredSize(new Dimension(1100, 120));
	    searchBar.setMaximumSize(new Dimension(1100, 120));
	    
	    spCheckInDate = FormUtils.createDateField();
	    lblCheckInDateError = FormUtils.createErrorLabel();
	    searchBar.add(FormUtils.createField("Entrada", spCheckInDate, lblCheckInDateError, "Seleccione la fecha de entrada"));
	    searchBar.add(Box.createRigidArea(new Dimension(15, 0)));
	    
	    spCheckOutDate = FormUtils.createDateField();
	    lblCheckOutDateError = FormUtils.createErrorLabel();
	    searchBar.add(FormUtils.createField("Salida", spCheckOutDate, lblCheckOutDateError, "Seleccione la fecha de salida"));
	    searchBar.add(Box.createRigidArea(new Dimension(15, 0)));
	    
	    txtNights = FormUtils.createTextField();
	    lblNightsError = FormUtils.createErrorLabel();
	    searchBar.add(Box.createRigidArea(new Dimension(15, 0)));
	    searchBar.add(FormUtils.createField("Noches", txtNights, lblNightsError, ""));
	    
	    spGuests = FormUtils.createNumberField();
	    lblGuestsError = FormUtils.createErrorLabel();
	    searchBar.add(Box.createRigidArea(new Dimension(15, 0)));
	    searchBar.add(FormUtils.createField("Huéspedes", spGuests, lblGuestsError, "Ingrese los huéspedes"));

	    btnSearch = ButtonFactory.createNormalButton(
	            "Buscar",
	            "/img/button-search-icon.png",
	            "Haz click para buscar"
	    );
	    searchBar.add(Box.createRigidArea(new Dimension(15, 0)));
	    searchBar.add(btnSearch);	
	    searchBar.add(Box.createRigidArea(new Dimension(0, 10)));

		return searchBar;
    }
    
    public JPanel createRooms() {
        JPanel roomsPanel = new JPanel();
        roomsPanel.setLayout(new BoxLayout(roomsPanel, BoxLayout.Y_AXIS));
        roomsPanel.setOpaque(false);
        
        // HEADER SECTION
        JPanel headerPanel = new JPanel();
        headerPanel.setLayout(new BoxLayout(headerPanel, BoxLayout.Y_AXIS));
        headerPanel.setOpaque(false);

        JLabel titleLabel = new JLabel("Habitaciones destacadas");
        titleLabel.setFont(AppFont.subtitle());
        titleLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        JLabel subtitleLabel = new JLabel(
                "Descubre nuestras mejores habitaciones"
        );
        subtitleLabel.setFont(AppFont.big());
        subtitleLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        headerPanel.add(titleLabel);
        headerPanel.add(Box.createRigidArea(new Dimension(0, 8)));
        headerPanel.add(subtitleLabel);

        // contenedor horizontal de habitaciones
        JPanel roomsContainer = new JPanel(
                new FlowLayout(FlowLayout.CENTER, 20, 20)
        );
        roomsContainer.setOpaque(false);
        roomsContainer.setPreferredSize(new Dimension(1100, 550));

        RoomRepository roomRepository = new RoomRepository();

        try {
            List<Room> featuredRooms = roomRepository.getFeaturedRooms();

            for (Room room : featuredRooms) {
                roomsContainer.add(createRoomCard(room));
            }

        } catch (IOException e) {
            e.printStackTrace();
        }

        // botón ver más
        JPanel seeRooms = new JPanel();
        seeRooms.setOpaque(false);

        btnSeeRooms = ButtonFactory.createBigButton(
                "Ver más",
                "/img/button-add-icon.png",
                "Haz click para ver más habitaciones"
        );

        seeRooms.add(btnSeeRooms);

        // agregar todo
        roomsPanel.add(headerPanel);
        roomsPanel.add(Box.createRigidArea(new Dimension(0, 20)));
        roomsPanel.add(roomsContainer);
        roomsPanel.add(Box.createRigidArea(new Dimension(0, 20)));
        roomsPanel.add(seeRooms);

        return roomsPanel;
    }
    
    private JPanel createRoomCard(Room room) {

        JPanel roomCard = new RoundedPanel(25);
        roomCard.setLayout(new BoxLayout(roomCard, BoxLayout.Y_AXIS));
        roomCard.setBackground(UIColors.CARD);
        roomCard.setPreferredSize(new Dimension(320, 500));
        roomCard.setMaximumSize(new Dimension(320, 500));
        roomCard.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));

        // imagen
        RoundedImagePanel imagePanel = new RoundedImagePanel(
                room.getImagePath(),
                280,
                180,
                20
        );

        imagePanel.setAlignmentX(Component.CENTER_ALIGNMENT);
    	
        // info resumida
        JPanel infoPanel = new JPanel();
        infoPanel.setLayout(new BoxLayout(infoPanel, BoxLayout.Y_AXIS));
        infoPanel.setOpaque(false);

        JLabel nameLabel = new JLabel(room.getName());
        nameLabel.setFont(AppFont.subtitle());
        nameLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        JLabel bedLabel = new JLabel(room.getBedType());
        bedLabel.setFont(AppFont.big());
        bedLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        
        infoPanel.add(nameLabel);
        infoPanel.add(Box.createRigidArea(new Dimension(0, 8)));
        infoPanel.add(bedLabel);
        infoPanel.add(Box.createRigidArea(new Dimension(0, 10)));

        //guests
        JPanel guestsPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 5, 0));
        guestsPanel.setOpaque(false);
        guestsPanel.setAlignmentX(Component.CENTER_ALIGNMENT);
        
        JLabel guestIcon = new JLabel(
                FormUtils.loadIcon("/img/guest-icon.png", 25)
        );

        JLabel guestLabel = new JLabel(
                room.getCapacity() + " huéspedes"
        );

        guestsPanel.add(guestIcon);
        guestsPanel.add(guestLabel);

        infoPanel.add(guestsPanel);
        infoPanel.add(Box.createRigidArea(new Dimension(0, 15)));

        // solo mostrar primeras 4 features
        JPanel featuresPanel = new JPanel(new GridLayout(0, 2, 10, 10));
        featuresPanel.setOpaque(false);

        List<String> features = room.getFeatures();

        for (String feature : features) {
            JPanel featureItem = new JPanel(
                    new FlowLayout(FlowLayout.LEFT, 5, 0)
            );
            featureItem.setOpaque(false);

            JLabel icon = new JLabel(
                    FormUtils.loadIcon("/img/check-icon.png", 14)
            );

            JLabel text = new JLabel(feature);

            featureItem.add(icon);
            featureItem.add(text);

            featuresPanel.add(featureItem);
        }

        infoPanel.add(featuresPanel);

        // acción
        JLabel priceLabel = new JLabel(
                "$" + room.getPrice() + " por noche"
        );
        priceLabel.setFont(AppFont.big());
        priceLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        
        RoundedButton btnReserve = ButtonFactory.createNormalButton(
                "Reservar",
                "/img/button-search-icon.png",
                "Reservar habitación"
        );
        btnReserve.setAlignmentX(Component.CENTER_ALIGNMENT);

        JPanel actionPanel = new JPanel();
        actionPanel.setOpaque(false);
        actionPanel.setLayout(new BoxLayout(actionPanel, BoxLayout.Y_AXIS));

        actionPanel.add(priceLabel);
        actionPanel.add(Box.createRigidArea(new Dimension(0, 10)));
        actionPanel.add(btnReserve);
        
        roomCard.add(imagePanel);
        roomCard.add(Box.createVerticalGlue());

        roomCard.add(infoPanel);
        roomCard.add(Box.createVerticalGlue());
        roomCard.add(actionPanel);

        return roomCard;
    }
    
    //para envolver las secciones con un margen respecto al contentPanel
    private JPanel wrapSection(JPanel section) {
        JPanel wrapper = new JPanel(
                new FlowLayout(FlowLayout.CENTER, 20, 20)
        );

        wrapper.setOpaque(false);
        wrapper.add(section);

        return wrapper;
    }
    
	//SECCIONES DEL CONTENT HOME
	/* busqueda 
	 * habitaciones destacadas
	 * servicios
	 * promociones
	 * about us
	 * reviews
	 */
}
