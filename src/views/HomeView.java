package views;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollBar;
import javax.swing.JScrollPane;
import javax.swing.JSpinner;
import javax.swing.JTextField;
import javax.swing.JTextPane;
import javax.swing.Timer;
import javax.swing.border.EmptyBorder;
import javax.swing.text.SimpleAttributeSet;
import javax.swing.text.StyleConstants;
import javax.swing.text.StyledDocument;

import java.awt.FlowLayout;
import java.awt.Graphics;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.Insets;
import java.util.Date;
import java.util.List;

import components.RoundedButton;
import components.RoundedImageOverlayPanel;
import components.RoundedImagePanel;
import components.RoundedPanel;
import models.RoomType;
import utils.AppFont;
import utils.ButtonFactory;
import utils.FormUtils;
import utils.VisualUtils;
import utils.UIColors;

@SuppressWarnings("serial")
public class HomeView extends JPanel{
	
	private JSpinner spCheckInDate;
	private JSpinner spCheckOutDate;
	private JTextField txtNights;
	private JSpinner spGuests;
	
	private JLabel lblCheckInDateError;
	private JLabel lblCheckOutDateError;
	private JLabel lblNightsError;
	private JLabel lblGuestsError;
	private RoundedButton btnSearch;
	private RoundedButton btnSeeRooms;
	
	private JPanel roomsContainer;
	private int sectionWidth = 1100; 

	public HomeView() {
	    this.setBackground(Color.WHITE);
	    this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
	    
	    initializeComponents();
	    setVisible(true);
	}
	
	public void initializeComponents() {
		add(createSearchHero());
		
	    add(Box.createRigidArea(new Dimension(0, 60)));
	    add(VisualUtils.createDivider()); 
	    add(Box.createRigidArea(new Dimension(0, 20)));
	    
	    add(wrapSection(createPromosSection()));
	    
	    add(Box.createRigidArea(new Dimension(0, 30)));
	    add(VisualUtils.createDivider()); 
	    add(Box.createRigidArea(new Dimension(0, 20)));
	    
	    add(wrapSection(createRooms()));
	    
	    add(Box.createRigidArea(new Dimension(0, 5)));
	    add(VisualUtils.createDivider()); 
	    add(Box.createRigidArea(new Dimension(0, 30)));
	    
	    add(wrapSection(createServicesSection()));
        
	    add(Box.createRigidArea(new Dimension(0, 30)));
	    add(VisualUtils.createDivider()); 
	    add(Box.createRigidArea(new Dimension(0, 30)));
        
	    add(wrapSection(createAboutSection()));
	    
	    add(Box.createRigidArea(new Dimension(0, 30)));
	}
	
	public JPanel createSearchHero() {
	    JPanel container = new JPanel(new BorderLayout());
	    container.setPreferredSize(new Dimension(0, 300));
	    container.setMaximumSize(new Dimension(Integer.MAX_VALUE, 300));

	    RoundedImageOverlayPanel bg = new RoundedImageOverlayPanel(
	            "/assets/img/search/search-bg.png",
	            0,
	            new Color(0, 0, 0, 120)
	    );

	    bg.setLayout(new BorderLayout());

	    JPanel searchBar = createSearchBar();
	    searchBar.setPreferredSize(new Dimension(900, 120));

	    JPanel centerWrapper = new JPanel(new GridBagLayout());
	    centerWrapper.setOpaque(false);
	    centerWrapper.add(searchBar);

	    bg.add(centerWrapper, BorderLayout.CENTER);
	    container.add(bg, BorderLayout.CENTER);

	    return container;
	}
	
    public JPanel createSearchBar() {
	    JPanel searchBar = new RoundedPanel(30);
	    searchBar.setLayout(new BoxLayout(searchBar, BoxLayout.X_AXIS));
	    searchBar.setBackground(UIColors.CARD);
	    searchBar.setBorder(BorderFactory.createEmptyBorder(25, 35, 25, 35));
	    searchBar.setAlignmentX(CENTER_ALIGNMENT);
	    searchBar.putClientProperty("FlatLaf.style", "arc:20");
	    searchBar.setPreferredSize(new Dimension(900, 120));
	    searchBar.setMaximumSize(new Dimension(900, 120));
	    
	    searchBar.add(Box.createHorizontalGlue());
	    
	    spCheckInDate = FormUtils.createDateField();
	    lblCheckInDateError = FormUtils.createErrorLabel();
	    searchBar.add(FormUtils.createField("Entrada", spCheckInDate, lblCheckInDateError, "", 180));
	    searchBar.add(Box.createRigidArea(new Dimension(15, 0)));
	    	    
	    spCheckOutDate = FormUtils.createDateField();
	    lblCheckOutDateError = FormUtils.createErrorLabel();
	    searchBar.add(FormUtils.createField("Salida", spCheckOutDate, lblCheckOutDateError, "", 180));
	    searchBar.add(Box.createRigidArea(new Dimension(15, 0)));
	    		 
	    txtNights = FormUtils.createTextField();
	    lblNightsError = FormUtils.createErrorLabel();
	    txtNights.setEditable(false);
	    txtNights.setBackground(new Color(245,245,245));
	    searchBar.add(Box.createRigidArea(new Dimension(15, 0)));
	    searchBar.add(FormUtils.createField("Noches", txtNights, lblNightsError, "", 130));
	    
	    spGuests = FormUtils.createNumberField();
	    lblGuestsError = FormUtils.createErrorLabel();
	    searchBar.add(Box.createRigidArea(new Dimension(15, 0)));
	    searchBar.add(FormUtils.createField("Huéspedes", spGuests, lblGuestsError, "", 130));

	    btnSearch = ButtonFactory.createBigButton(
	            "Buscar",
	            "/assets/img/btn-icons/button-search-icon.png",
	            "Haz click para buscar"
	    );
	    searchBar.add(Box.createRigidArea(new Dimension(15, 0)));
	    searchBar.add(btnSearch);	
	    searchBar.add(Box.createRigidArea(new Dimension(0, 10)));
	    searchBar.add(Box.createHorizontalGlue()); // elementos al centro

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
        titleLabel.setFont(AppFont.title());
        titleLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        JLabel subtitleLabel = new JLabel("Descubre nuestras mejores habitaciones");
        subtitleLabel.setFont(AppFont.big());
        subtitleLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        headerPanel.add(titleLabel);
        headerPanel.add(Box.createRigidArea(new Dimension(0, 8)));
        headerPanel.add(subtitleLabel);

        // contenedor horizontal de habitaciones
        roomsContainer = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 20));
        roomsContainer.setOpaque(false);
        roomsContainer.setPreferredSize(new Dimension(sectionWidth, 550));

        // botón ver más
        JPanel seeRooms = new JPanel();
        seeRooms.setOpaque(false);

        btnSeeRooms = ButtonFactory.createBigButton(
                "Ver más",
                "/assets/img/btn-icons/button-add-icon.png",
                "Haz click para ver más habitaciones"
        );

        seeRooms.add(btnSeeRooms);

        roomsPanel.add(headerPanel);
        roomsPanel.add(Box.createRigidArea(new Dimension(0, 20)));
        roomsPanel.add(roomsContainer);
        roomsPanel.add(Box.createRigidArea(new Dimension(0, 5)));
        roomsPanel.add(seeRooms);
        roomsPanel.add(Box.createRigidArea(new Dimension(0, 20)));

        return roomsPanel;
    }
    
    public void setRooms(List<RoomType> rooms) {
        roomsContainer.removeAll();

        for (RoomType room : rooms) {
            roomsContainer.add(createRoomCard(room));
        }

        roomsContainer.revalidate();
        roomsContainer.repaint();
    }
    
    public RoundedButton getBtnSearch() {
        return btnSearch;
    }

    public RoundedButton getBtnSeeRooms() {
        return btnSeeRooms;
    }
    
    private JPanel createRoomCard(RoomType room) {

        JPanel roomCard = new RoundedPanel(25);
        roomCard.setLayout(new BoxLayout(roomCard, BoxLayout.Y_AXIS));
        roomCard.setBackground(UIColors.CARD);
        roomCard.setPreferredSize(new Dimension(320, 500));
        roomCard.setMaximumSize(new Dimension(320, 500));
        roomCard.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));

        // imagen
        RoundedImagePanel imagePanel = new RoundedImagePanel(room.getImagePath(), 280, 180, 20);
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
        
        JLabel guestIcon = new JLabel(FormUtils.loadIcon("/assets/img/icons/guest-icon.png", 25));
        JLabel guestLabel = new JLabel(room.getCapacity() + " huéspedes");

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

            JLabel icon = new JLabel(FormUtils.loadIcon("/assets/img/icons/check-icon.png", 14));
            JLabel text = new JLabel(feature);

            featureItem.add(icon);
            featureItem.add(text);

            featuresPanel.add(featureItem);
        }

        infoPanel.add(featuresPanel);

        // acción
        JLabel priceLabel = new JLabel("$" + room.getPrice() + " por noche");
        priceLabel.setFont(AppFont.big());
        priceLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        
        RoundedButton btnReserve = ButtonFactory.createBigButton(
                "Reservar",
                "/assets/img/btn-icons/button-search-icon.png",
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
    
    public JPanel createServicesSection() {

        JPanel section = new JPanel();
        section.setLayout(new BoxLayout(section, BoxLayout.Y_AXIS));
        section.setOpaque(false);

        // título
        JLabel title = new JLabel("Servicios");
        title.setFont(AppFont.title());
        title.setAlignmentX(Component.CENTER_ALIGNMENT);

        section.add(title);
        section.add(Box.createRigidArea(new Dimension(0, 30)));

        // grid 5x3
        JPanel grid = new JPanel(new GridLayout(3, 5, 20, 20));
        grid.setOpaque(false);

        // servicios
        grid.add(createServiceCard("WiFi", "/assets/img/serv/wifi-icon.png"));
        grid.add(createServiceCard("Piscina", "/assets/img/serv/pool-icon.png"));
        grid.add(createServiceCard("Gym", "/assets/img/serv/gym-icon.png"));
        grid.add(createServiceCard("Spa", "/assets/img/serv/spa-icon.png"));
        grid.add(createServiceCard("Parking", "/assets/img/serv/parking-icon.png"));

        grid.add(createServiceCard("Restaurante", "/assets/img/serv/restaurant-icon.png"));
        grid.add(createServiceCard("Bar", "/assets/img/serv/bar-icon.png"));
        grid.add(createServiceCard("Room Service", "/assets/img/serv/service-icon.png"));
        grid.add(createServiceCard("Acceso a la playa", "/assets/img/serv/beach-icon.png"));
        grid.add(createServiceCard("TV", "/assets/img/serv/tv-icon.png"));

        grid.add(createServiceCard("Lavandería", "/assets/img/serv/laundry-icon.png"));
        grid.add(createServiceCard("Seguridad", "/assets/img/serv/security-icon.png"));
        grid.add(createServiceCard("Recepción 24h", "/assets/img/serv/reception-icon.png"));
        grid.add(createServiceCard("Transporte", "/assets/img/serv/transport-icon.png"));
        grid.add(createServiceCard("Eventos", "/assets/img/serv/event-icon.png"));

        section.add(grid);

        return section;
    }
    
    private JPanel createServiceCard(String name, String iconPath) {

        RoundedPanel card = new RoundedPanel(25);
        card.setLayout(new BoxLayout(card, BoxLayout.Y_AXIS));
        card.setBackground(UIColors.CARD);
        card.setPreferredSize(new Dimension(160, 120));
        card.setMaximumSize(new Dimension(160, 120));
        card.setBorder(BorderFactory.createEmptyBorder(15, 10, 15, 10));

        // icono
        JLabel icon = new JLabel(FormUtils.loadIcon(iconPath, 60));
        icon.setAlignmentX(Component.CENTER_ALIGNMENT);

        // texto
        JLabel label = new JLabel(name);
        label.setFont(AppFont.normal());
        label.setAlignmentX(Component.CENTER_ALIGNMENT);

        card.add(icon);
        card.add(Box.createRigidArea(new Dimension(0, 10)));
        card.add(label);

        return card;
    }
    
    public JPanel createPromosSection() {
    	JPanel section = new JPanel();
    	section.setLayout(new BoxLayout(section, BoxLayout.Y_AXIS));
    	section.setOpaque(false);

    	// título
    	JLabel title = new JLabel("Promociones");
    	title.setFont(AppFont.title());
    	title.setAlignmentX(Component.CENTER_ALIGNMENT);

    	section.add(title);
    	section.add(Box.createRigidArea(new Dimension(0, 20)));

    	// promo grande
    	section.add(createMainPromo());
    	section.add(Box.createRigidArea(new Dimension(0, 30)));

    	//carrusel
    	section.add(createPromoCarousel());
    	
    	return section;
    }
    
    private JPanel createMainPromo() {

        RoundedPanel promo = new RoundedPanel(30);
        promo.setLayout(new BorderLayout());
        promo.setPreferredSize(new Dimension(sectionWidth, 350));
        promo.setMaximumSize(new Dimension(sectionWidth, 350));
        promo.setOpaque(false);

        // BACKGROUND
        RoundedImageOverlayPanel bg = new RoundedImageOverlayPanel(
                "/assets/img/promos/promo1.png",
                30,
                new Color(0, 0, 0, 100)
        );

        bg.setLayout(new BorderLayout());

        // CONTENIDO
        JPanel content = new JPanel();
        content.setOpaque(false);
        content.setLayout(new BoxLayout(content, BoxLayout.Y_AXIS));
        content.setBorder(new EmptyBorder(40, 40, 40, 40));

        JLabel title = new JLabel("10% OFF en verano");
        title.setFont(AppFont.title());
        title.setForeground(Color.WHITE);

        JLabel subtitle = new JLabel("Reserva ahora para estas vacaciones!");
        subtitle.setFont(AppFont.normal());
        subtitle.setForeground(Color.WHITE);

        RoundedButton reserveBtn = ButtonFactory.createBigButton(
                "Reservar ahora",
                "/assets/img/btn-icons/button-search-icon.png",
                "Ir a reservas"
        );

        content.add(title);
        content.add(Box.createRigidArea(new Dimension(0, 10)));
        content.add(subtitle);
        content.add(Box.createRigidArea(new Dimension(0, 15)));
        content.add(reserveBtn);

        bg.add(content, BorderLayout.WEST);

        promo.add(bg, BorderLayout.CENTER);

        return promo;
    }
    
    private JPanel createPromoCarousel() {

        JPanel container = new JPanel(new BorderLayout());
        container.setOpaque(false);
        container.setMaximumSize(new Dimension(sectionWidth, 220));

        // panel interno
        JPanel content = new JPanel();
        content.setLayout(new BoxLayout(content, BoxLayout.X_AXIS));
        content.setOpaque(false);

        // scroll
        JScrollPane scroll = new JScrollPane(content);
        scroll.addMouseWheelListener(e -> {
            scroll.getParent().dispatchEvent(e);
        });
        scroll.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        scroll.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_NEVER);
        scroll.setBorder(null);
        scroll.setOpaque(false);
        scroll.getViewport().setOpaque(false);
        scroll.setPreferredSize(new Dimension(sectionWidth, 200));
        scroll.setMaximumSize(new Dimension(sectionWidth, 200));

        // agregar las promos, doble para que sea infinito
        addPromos(content);
        addPromos(content);

        container.add(scroll, BorderLayout.CENTER);

        // animacion
        Timer timer = new Timer(20, null);

        timer.addActionListener(e -> {
            JScrollBar bar = scroll.getHorizontalScrollBar();
            bar.setValue(bar.getValue() + 1);

            // cuando llega a la mitad, vuelve al inicio
            if (bar.getValue() >= bar.getMaximum() / 2) {
                bar.setValue(0);
            }
        });

        timer.start();
        
        return container;
    }
    
    private void addPromos(JPanel content) {
        content.add(createSmallPromo("Tour privado por la ciudad", "/assets/img/promos/promo5.png"));
        content.add(Box.createRigidArea(new Dimension(15, 0)));

        content.add(createSmallPromo("5 noches, paga 4", "/assets/img/promos/promo6.png"));
        content.add(Box.createRigidArea(new Dimension(15, 0)));

        content.add(createSmallPromo("Aquaventure World", "/assets/img/promos/promo3.png"));
        content.add(Box.createRigidArea(new Dimension(15, 0)));

        content.add(createSmallPromo("Acuario Lost Chambers", "/assets/img/promos/promo2.png"));
        content.add(Box.createRigidArea(new Dimension(15, 0)));

        content.add(createSmallPromo("Spa & Wellness Retreat", "/assets/img/promos/promo4.png"));
        content.add(Box.createRigidArea(new Dimension(15, 0)));

        content.add(createSmallPromo("Experiencia playa VIP", "/assets/img/promos/promo8.png"));
        content.add(Box.createRigidArea(new Dimension(15, 0)));

        content.add(createSmallPromo("Vuelo en helicóptero sobre Dubai", "/assets/img/promos/promo9.png"));
        content.add(Box.createRigidArea(new Dimension(15, 0)));

        content.add(createSmallPromo("Yate privado de lujo", "/assets/img/promos/promo10.png"));
        content.add(Box.createRigidArea(new Dimension(15, 0)));

        content.add(createSmallPromo("Sky Pool infinity privada", "/assets/img/promos/promo7.png"));
        content.add(Box.createRigidArea(new Dimension(15, 0)));
    }    
    
    private JPanel createSmallPromo(String titleText, String imgPath) {

        RoundedPanel card = new RoundedPanel(20) {

            private Image image = new ImageIcon(
                getClass().getResource(imgPath)
            ).getImage();

            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);

                // dibujar imagen como fondo
                g.drawImage(image, 0, 0, getWidth(), getHeight(), this);

                // overlay oscuro
                g.setColor(new Color(0, 0, 0, 100));
                g.fillRoundRect(0, 0, getWidth(), getHeight(), 20, 20);
            }
        };

        card.setLayout(new BorderLayout());
        card.setPreferredSize(new Dimension(200, 180));
        card.setMaximumSize(new Dimension(200, 180));
        card.setOpaque(false);

        // TEXTO ENCIMA
        JLabel title = new JLabel(titleText);
        title.setForeground(Color.WHITE);
        title.setFont(AppFont.normal());
        title.setBorder(BorderFactory.createEmptyBorder(0, 10, 10, 10));

        JPanel textWrapper = new JPanel(new BorderLayout());
        textWrapper.setOpaque(false);
        textWrapper.add(title, BorderLayout.SOUTH);

        card.add(textWrapper, BorderLayout.CENTER);

        return card;
    }
    
    public JPanel createAboutSection() {
        JPanel section = new JPanel();
        section.setLayout(new BoxLayout(section, BoxLayout.Y_AXIS));
        section.setOpaque(false);
        section.add(createAboutHero());
        return section;
    }
    
    private JPanel createAboutHero() {

        RoundedPanel hero = new RoundedPanel(30);
        hero.setLayout(new BorderLayout());
        hero.setPreferredSize(new Dimension(sectionWidth, 250));
        hero.setMaximumSize(new Dimension(sectionWidth, 250));
        hero.setOpaque(false);

        // BACKGROUND + OVERLAY
        RoundedImageOverlayPanel bg = new RoundedImageOverlayPanel(
                "/assets/img/about/about1.png",
                30,
                new Color(0, 0, 0, 140)
        );

        bg.setLayout(new BorderLayout());

        // CONTENIDO
        JPanel content = new JPanel(new GridBagLayout());
        content.setOpaque(false);

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.anchor = GridBagConstraints.CENTER;
        gbc.insets = new Insets(5, 0, 5, 0);

        JLabel title = new JLabel("Sobre Atlantis Dubai");
        title.setFont(AppFont.title());
        title.setForeground(Color.WHITE);
        title.setAlignmentX(Component.CENTER_ALIGNMENT);

        JTextPane text = new JTextPane();
        text.setText(
            "Arropado por las calmadas aguas de color turquesa del golfo Pérsico y el skyline de Dubái,\n"
            + "Atlantis Dubái es la joya de la corona de la Isla de la Palmera.\n"
            + "Tanto si se queda en nuestro famoso resort, cena en nuestros galardonados restaurantes\n"
            + "como si siente la adrenalina en el parque acuático Aquaventure, en Atlantis Dubái\n"
            + "disfrutará de un mundo a años luz de la rutina diaria.\n"
        );

        text.setEditable(false);
        text.setOpaque(false);
        text.setBorder(null);
        text.setFont(AppFont.big());
        text.setForeground(Color.WHITE);

        StyledDocument doc = text.getStyledDocument();
        SimpleAttributeSet center = new SimpleAttributeSet();
        StyleConstants.setAlignment(center, StyleConstants.ALIGN_CENTER);
        doc.setParagraphAttributes(0, doc.getLength(), center, false);

        content.add(title, gbc);

        gbc.gridy++;
        content.add(text, gbc);

        content.setBorder(new EmptyBorder(20, 80, 20, 80));
        
        bg.add(content, BorderLayout.CENTER);
        hero.add(bg, BorderLayout.CENTER);

        return hero;
    }
    
	//para envolver las secciones con un margen respecto al contentPanel
    private JPanel wrapSection(JPanel section) {
        JPanel wrapper = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 30));

        wrapper.setOpaque(false);
        wrapper.add(section);

        return wrapper;
    }
    
    //getters
    public JSpinner getSpCheckInDate() {
        return spCheckInDate;
    }

    public JSpinner getSpCheckOutDate() {
        return spCheckOutDate;
    }
    
    public JTextField getTxtNights() {
        return txtNights;
    }
    
    public JSpinner getSpGuests() {
    	return spGuests;
    }
    
    public Date getCheckInDate() {
        return (Date) spCheckInDate.getValue();
    }

    public Date getCheckOutDate() {
        return (Date) spCheckOutDate.getValue();
    }
    
    public int getGuests() {
        return (int) spGuests.getValue();
    }
    
    public int getNights() {
        return Integer.parseInt(txtNights.getText());
    }
    
    public void setNights(String nights) {
        txtNights.setText(nights);
    }
}