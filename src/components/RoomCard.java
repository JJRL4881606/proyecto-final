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

import models.RoomType;
import utils.AppFont;
import utils.ButtonFactory;
import utils.FormUtils;
import utils.UIColors;

@SuppressWarnings("serial")
public class RoomCard extends RoundedPanel {

    private RoundedButton btnReserve;
    private RoundedButton btnDetails;

    public RoundedButton getBtnReserve() { return btnReserve; }
    public RoundedButton getBtnDetails() { return btnDetails; }

    public RoomCard(RoomType room) {
        super(25);
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        setBackground(UIColors.CARD);
        setPreferredSize(new Dimension(320, 550));
        setMaximumSize(new Dimension(320, 550));
        setBorder(BorderFactory.createEmptyBorder(15,15,15,15));

        // imagen
        RoundedImagePanel imagePanel =
                new RoundedImagePanel(
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

        infoPanel.add(nameLabel);
        infoPanel.add(Box.createRigidArea(new Dimension(0, 20)));
        infoPanel.add(bedLabel);
        infoPanel.add(Box.createRigidArea(new Dimension(0, 15)));

        // huéspedes
        JPanel guestsPanel = new JPanel(new FlowLayout(FlowLayout.CENTER,5,0));
        guestsPanel.setOpaque(false);

        JLabel guestIcon = new JLabel(
                FormUtils.loadIcon(
                        "/assets/img/icons/guest-icon.png",
                        25
                )
        );

        JLabel guestLabel = new JLabel(room.getCapacity() + " huéspedes");

        guestsPanel.add(guestIcon);
        guestsPanel.add(guestLabel);

        infoPanel.add(guestsPanel);
        infoPanel.add(Box.createRigidArea(new Dimension(0, 15)));

        // features
        JPanel featuresPanel = new JPanel(new GridLayout(0, 2, 10, 10));
        featuresPanel.setOpaque(false);

        List<String> features = room.getFeatures();
        for (String feature : features) {
            JPanel featureItem = new JPanel(new FlowLayout(FlowLayout.LEFT,5,0));
            featureItem.setOpaque(false);

            JLabel icon = new JLabel(
                    FormUtils.loadIcon(
                            "/assets/img/icons/check-icon.png",
                            14
                    )
            );
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

        btnReserve = ButtonFactory.createBigButton(
                "Reservar",
                "/assets/img/btn-icons/button-search-icon.png",
                "Reservar habitación"
        );

        btnDetails = ButtonFactory.createBigButton(
                "Ver detalles",
                "/assets/img/btn-icons/button-search-icon.png",
                "Ver detalles"
        );

        btnReserve.setAlignmentX(Component.CENTER_ALIGNMENT);
        btnDetails.setAlignmentX(Component.CENTER_ALIGNMENT);
        btnReserve.setMaximumSize(new Dimension(260, 45));
        btnDetails.setMaximumSize(new Dimension(260, 45));

        JPanel actionPanel = new JPanel();
        actionPanel.setOpaque(false);
        actionPanel.setLayout(new BoxLayout(actionPanel, BoxLayout.Y_AXIS));
        actionPanel.add(priceLabel);
        actionPanel.add(Box.createRigidArea(new Dimension(0, 20)));
        actionPanel.add(btnReserve);
        actionPanel.add(Box.createRigidArea(new Dimension(0, 10)));
        actionPanel.add(btnDetails);

        add(imagePanel);
        add(Box.createVerticalGlue());
        add(infoPanel);
        add(Box.createVerticalGlue());
        add(actionPanel);
    }
}