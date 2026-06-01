package components;

import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JLabel;
import javax.swing.JPanel;

import models.Amenity;
import models.RoomType;
import utils.AppFont;
import utils.ButtonFactory;
import utils.FormUtils;
import utils.UIColors;
import utils.VisualUtils;

@SuppressWarnings("serial")
public class RoomCard extends RoundedPanel {

	private RoundedButton btnDetails;
	private RoundedButton btnReserve;
	private RoomType room;

	public RoomCard(RoomType room) {
	    super(25);
	    this.room = room;
	    setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        setBackground(UIColors.CARD);
        setPreferredSize(new Dimension(340, 610));
        setMaximumSize(new Dimension(340, 610));
        setBorder(BorderFactory.createEmptyBorder(15,15,15,15));

        // imagen
        RoundedImagePanel imagePanel = new RoundedImagePanel(
            room.getImagePath(),
            280,
            180,
            20
        );
        imagePanel.setAlignmentX(Component.CENTER_ALIGNMENT);

        // info
        JPanel infoPanel = new JPanel();
        infoPanel.setLayout(new BoxLayout(infoPanel,BoxLayout.Y_AXIS));
        infoPanel.setOpaque(false);

        JLabel nameLabel = new JLabel(room.getName());
        nameLabel.setFont(AppFont.subtitle());
        nameLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        JLabel bedLabel = new JLabel(room.getBedType());
        bedLabel.setFont(AppFont.big());
        bedLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        
        infoPanel.add(Box.createRigidArea(new Dimension(0, 20)));
        infoPanel.add(nameLabel);
        infoPanel.add(Box.createRigidArea(new Dimension(0, 20)));
        infoPanel.add(bedLabel);
        infoPanel.add(Box.createRigidArea(new Dimension(0, 15)));

        // huéspedes
        JPanel guestsPanel = new JPanel(new FlowLayout(FlowLayout.CENTER,5,0));
        guestsPanel.setOpaque(false);

        JLabel guestIcon = new JLabel(FormUtils.loadIcon("/assets/img/icons/guest-icon.png", 25));
        JLabel guestLabel = new JLabel(room.getCapacity() + " huéspedes");

        guestsPanel.add(guestIcon);
        guestsPanel.add(guestLabel);

        infoPanel.add(guestsPanel);
        infoPanel.add(Box.createRigidArea(new Dimension(0, 15)));

        // features
        JPanel featuresPanel = new JPanel(new GridLayout(0, 2, 10, 10));
        featuresPanel.setOpaque(false);

        List<Amenity> amenities = room.getAmenities();

        for (int i = 0; i < Math.min(4, amenities.size()); i++) {

            JPanel featureItem = new JPanel(
                new FlowLayout(FlowLayout.LEFT, 5, 0)
            );
            featureItem.setOpaque(false);

            JLabel icon = new JLabel(
                FormUtils.loadIcon(
                    "/assets/img/icons/check-icon.png",
                    14
                )
            );

            String amenityName = amenities.get(i).getName();

            if (amenityName.length() > 15) {
                amenityName =
                    amenityName.substring(0, 12) + "...";
            }

            JLabel text = new JLabel(amenityName);

            featureItem.add(icon);
            featureItem.add(text);

            featuresPanel.add(featureItem);
        }

        infoPanel.add(featuresPanel);

        // acción
        JLabel priceLabel = new JLabel("$" + room.getPrice() + " por noche");
        priceLabel.setFont(AppFont.big());
        priceLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        btnReserve = ButtonFactory.createBlueButton(
            "Reservar",
            "/assets/img/btn-icons/button-reserve-icon.png",
            "Reservar habitación"
        );
        
        btnDetails = ButtonFactory.createGoldButton(
            "Ver detalles",
            "/assets/img/btn-icons/button-search-icon.png",
            "Ver detalles"
        );
        
        btnReserve.setAlignmentX(Component.CENTER_ALIGNMENT);
        btnDetails.setAlignmentX(Component.CENTER_ALIGNMENT);
        
	    Dimension btn = new Dimension(170,40);
	    btnReserve.setPreferredSize(btn);
	    btnReserve.setMinimumSize(btn);
	    btnReserve.setMaximumSize(btn);
        
	    btnDetails.setPreferredSize(btn);
	    btnDetails.setMinimumSize(btn);
	    btnDetails.setMaximumSize(btn);
        
        // action panel
        JPanel actionPanel = new JPanel();
        actionPanel.setOpaque(false);
        actionPanel.setLayout(new BoxLayout(actionPanel, BoxLayout.Y_AXIS));
        actionPanel.add(Box.createRigidArea(new Dimension(0, 10)));
        actionPanel.add(priceLabel);
        actionPanel.add(Box.createRigidArea(new Dimension(0, 20)));
        actionPanel.add(VisualUtils.createSmallDivider()); 
        actionPanel.add(Box.createRigidArea(new Dimension(0, 20)));
        actionPanel.add(btnReserve);
        actionPanel.add(Box.createRigidArea(new Dimension(0, 10)));
        actionPanel.add(btnDetails);
        
        add(Box.createRigidArea(new Dimension(0, 17)));

        add(imagePanel);
        add(Box.createVerticalGlue());
        add(infoPanel);
        add(Box.createVerticalGlue());
        add(actionPanel);
        add(Box.createRigidArea(new Dimension(0, 10)));
    }
    
    //getters
    public RoundedButton getBtnDetails() {
        return btnDetails;
    }

    public RoundedButton getBtnReserve() {
        return btnReserve;
    }

    public RoomType getRoom() {
        return room;
    }
}