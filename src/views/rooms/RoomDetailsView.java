package views.rooms;

import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Insets;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextArea;

import components.RoundedImagePanel;
import components.RoundedPanel;
import models.RoomType;
import utils.AppFont;
import utils.FormUtils;

@SuppressWarnings("serial")
public class RoomDetailsView extends JPanel {

    private JPanel imageContainer, featuresPanel;
    private RoundedImagePanel imagePanel;
    private JLabel lblName, lblPrice, lblBed, lblCapacity;
    private JTextArea lblDescription;

    public RoomDetailsView() {
        setOpaque(false);
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));

        add(Box.createRigidArea(new Dimension(0,40)));
        add(createImageSection());

        add(Box.createRigidArea(new Dimension(0,30)));
        add(createTitleSection());

        add(Box.createRigidArea(new Dimension(0,40)));
        add(createInfoSection());

        add(Box.createRigidArea(new Dimension(0,40)));
        add(createDescriptionSection());

        add(Box.createRigidArea(new Dimension(0,40)));
        add(createFeaturesSection());

        add(Box.createRigidArea(new Dimension(0,50)));
    }

    private JPanel createImageSection() {
        imageContainer = new JPanel();
        imageContainer.setOpaque(false);

        imagePanel = new RoundedImagePanel("",900,350,30);
        imageContainer.add(imagePanel);

        return imageContainer;
    }

    private JLabel createTitleSection() {
        lblName = new JLabel();
        lblName.setFont(AppFont.title());
        lblName.setAlignmentX(Component.CENTER_ALIGNMENT);

        return lblName;
    }

    private JPanel createInfoSection() {
        JPanel panel = new JPanel(new FlowLayout(FlowLayout.CENTER,30,0));
        panel.setOpaque(false);

        lblPrice = new JLabel();
        lblBed = new JLabel();
        lblCapacity = new JLabel();

        panel.add(createInfoItem("/assets/img/icons/price-icon.png",lblPrice));
        panel.add(createInfoItem("/assets/img/icons/bed-icon.png",lblBed));
        panel.add(createInfoItem("/assets/img/icons/guest-icon.png",lblCapacity));

        return panel;
    }

    private JPanel createInfoItem(String iconPath,JLabel label){
        RoundedPanel card = new RoundedPanel(30);

        card.setBackground(new Color(220,220,220));
        card.setLayout(new GridBagLayout());

        Dimension size = new Dimension(250,80);
        card.setPreferredSize(size);
        card.setMinimumSize(size);
        card.setMaximumSize(size);

        JPanel content = new JPanel(new FlowLayout(FlowLayout.CENTER,15,0));
        content.setOpaque(false);

        label.setFont(AppFont.subtitle());

        content.add(new JLabel(
            FormUtils.loadIcon(iconPath,36)
        ));

        content.add(label);

        card.add(content);

        return card;
    }
    
    private JPanel createDescriptionSection() {
        JPanel panel = new JPanel();
        panel.setOpaque(false);
        panel.setLayout(new BoxLayout(panel,BoxLayout.Y_AXIS));

        JLabel title = new JLabel("Descripción");
        title.setFont(AppFont.subtitle2());
        title.setAlignmentX(Component.CENTER_ALIGNMENT);

        lblDescription = new JTextArea();

        lblDescription.setFont(AppFont.subtitle());
        lblDescription.setOpaque(false);
        lblDescription.setEditable(false);
        lblDescription.setFocusable(false);
        lblDescription.setLineWrap(true);
        lblDescription.setWrapStyleWord(true);

        lblDescription.setColumns(50);
        lblDescription.setMargin(new Insets(30,0,30,0));
        lblDescription.setMaximumSize(
            new Dimension(700,Integer.MAX_VALUE)
        );

        panel.add(title);
        panel.add(Box.createRigidArea(new Dimension(0,20)));
        panel.add(lblDescription);

        return panel;
    }

    private JPanel createFeaturesSection() {
        JPanel container = new JPanel();
        container.setOpaque(false);
        container.setLayout(new BoxLayout(container,BoxLayout.Y_AXIS));

        JLabel title = new JLabel("Amenidades");
        title.setFont(AppFont.subtitle2());
        title.setAlignmentX(Component.CENTER_ALIGNMENT);

        featuresPanel = new JPanel(new GridLayout(0,4,20,20));
        featuresPanel.setOpaque(false);
        featuresPanel.setPreferredSize(new Dimension(850,200));
        featuresPanel.setMaximumSize(new Dimension(850,Integer.MAX_VALUE));
        featuresPanel.setAlignmentX(Component.CENTER_ALIGNMENT);

        container.add(title);
        container.add(Box.createRigidArea(new Dimension(0,40)));
        container.add(featuresPanel);

        return container;
    }

    public void setRoom(RoomType room) {
        imageContainer.removeAll();

        imagePanel = new RoundedImagePanel(
            room.getImagePath(), 900, 350, 30
        );

        imageContainer.add(imagePanel);

        lblName.setText(room.getName());
        lblPrice.setText("$" + room.getPrice() + "/noche");
        lblBed.setText(room.getBedType());
        lblCapacity.setText(room.getCapacity() + " huéspedes");

        lblDescription.setText(room.getDescription());
        lblDescription.setSize(700,Short.MAX_VALUE);

        Dimension size = lblDescription.getPreferredSize();
        lblDescription.setMaximumSize(new Dimension(900,size.height));

        featuresPanel.removeAll();

        for(String feature : room.getFeatures()){
            RoundedPanel item = new RoundedPanel(25);
            item.setBackground(new Color(220,220,220));
            item.setLayout(new GridBagLayout());

            Dimension dim = new Dimension(180,80);
            item.setPreferredSize(dim);
            item.setMinimumSize(dim);
            item.setMaximumSize(dim);

            JLabel text = new JLabel(feature);
            text.setFont(AppFont.subtitle());

            item.add(text);

            featuresPanel.add(item);
        }
        
        revalidate();
        repaint();
    }
}