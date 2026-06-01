package views.rooms;

import java.awt.Component;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Insets;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollBar;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JViewport;

import components.RoundedButton;
import components.RoundedImagePanel;
import components.RoundedPanel;
import models.Amenity;
import models.RoomImage;
import models.RoomType;
import utils.AppFont;
import utils.ButtonFactory;
import utils.FormUtils;
import utils.UIColors;
import utils.VisualUtils;

@SuppressWarnings("serial")
public class RoomDetailsView extends JPanel {

    private JPanel imageContainer;
    private JPanel featuresPanel;
    private RoundedImagePanel imagePanel;
    private JLabel lblName;
    private JLabel lblPrice;
    private JLabel lblBed;
    private JLabel lblCapacity;
    private JButton btnReserve;
    private JTextArea lblDescription;
    private RoomType room;
    
    //para que el panel de descripcion crezca segun necesite
    private RoundedPanel bookingLeftPanel;
    
    private JPanel carouselPanel;
    private JScrollPane carouselScroll;

    public RoomDetailsView() {
        setOpaque(false);
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));

        add(Box.createRigidArea(new Dimension(0,40)));
        
        add(createImageSection());

        add(Box.createRigidArea(new Dimension(0,40)));
	    add(VisualUtils.createDivider()); 
        add(Box.createRigidArea(new Dimension(0,50)));

        add(createTitleSection());

        add(Box.createRigidArea(new Dimension(0,40)));
        
        add(createBookingSection());

        add(Box.createRigidArea(new Dimension(0,50)));
	    add(VisualUtils.createDivider()); 
        add(Box.createRigidArea(new Dimension(0,50)));

        add(createFeaturesSection());

        add(Box.createRigidArea(new Dimension(0,60)));
	    add(VisualUtils.createDivider()); 
        add(Box.createRigidArea(new Dimension(0,40)));

        add(createCarouselSection());
        
        add(Box.createRigidArea(new Dimension(0,40)));
    }

    private JPanel createImageSection() {
        imageContainer = new JPanel();
        imageContainer.setOpaque(false);

        imagePanel = new RoundedImagePanel("", 900, 350, 30);
        imageContainer.add(imagePanel);

        return imageContainer;
    }

    private JLabel createTitleSection() {
        lblName = new JLabel();
        lblName.setFont(AppFont.title());
        lblName.setAlignmentX(Component.CENTER_ALIGNMENT);

        return lblName;
    }
    
    private JPanel createBookingSection() {

        JPanel wrapper = new JPanel(new FlowLayout(FlowLayout.CENTER,40,0));
        wrapper.setOpaque(false);

        // izquierda
        bookingLeftPanel = new RoundedPanel(30);
        
        RoundedPanel left = bookingLeftPanel;
        left.setBackground(UIColors.CARD);
        left.setLayout(new BoxLayout(left, BoxLayout.Y_AXIS));
        left.setPreferredSize(new Dimension(800,200));
        left.setMinimumSize(new Dimension(800,200));
        left.setMaximumSize(new Dimension(800,Integer.MAX_VALUE));
        
        JLabel title = new JLabel("Descripción");
        title.setFont(AppFont.subtitle2());
        title.setAlignmentX(Component.CENTER_ALIGNMENT);

        lblDescription = new JTextArea();
        lblDescription.setOpaque(false);
        lblDescription.setEditable(false);
        lblDescription.setFocusable(false);
        lblDescription.setLineWrap(true);
        lblDescription.setWrapStyleWord(true);
        lblDescription.setColumns(35);
        lblDescription.setFont(AppFont.subtitle());
        lblDescription.setMargin(new Insets(25, 30, 25, 30));

        left.add(Box.createRigidArea(new Dimension(0,50)));
        left.add(title);
        left.add(lblDescription);
        left.add(Box.createRigidArea(new Dimension(0,20)));

        // derecha
        RoundedPanel right = new RoundedPanel(30);
        right.setBackground(UIColors.CARD);
        right.setLayout(new BoxLayout(right, BoxLayout.Y_AXIS));

        Dimension rightSize = new Dimension(320,450);

        right.setPreferredSize(rightSize);
        right.setMinimumSize(rightSize);
        right.setMaximumSize(rightSize);

        lblPrice = new JLabel();
        lblBed = new JLabel();
        lblCapacity = new JLabel();

        JPanel info = new JPanel();
        info.setOpaque(false);
        info.setLayout(new BoxLayout(info,BoxLayout.Y_AXIS));
        
        info.add(createInfoItem("/assets/img/icons/price-icon.png", lblPrice));
        info.add(createInfoItem("/assets/img/icons/bed-icon.png", lblBed));
        info.add(createInfoItem("/assets/img/icons/guest-icon.png", lblCapacity));

        btnReserve = ButtonFactory.createBlueButton(
            "RESERVAR",
            "/assets/img/btn-icons/button-reserve-icon.png",
            "Haz clic para reservar"
        );
        btnReserve.setFont(AppFont.subtitle());
        btnReserve.setAlignmentX(Component.CENTER_ALIGNMENT);

        Dimension btn = new Dimension(220,55);
        btnReserve.setPreferredSize(btn);
        btnReserve.setMaximumSize(btn);

        right.add(Box.createRigidArea(new Dimension(0,50)));
        right.add(info);
        right.add(Box.createRigidArea(new Dimension(0,30)));
        right.add(btnReserve);
        right.add(Box.createRigidArea(new Dimension(0,20)));
        
        wrapper.add(left);
        wrapper.add(right);

        return wrapper;
    }
    
    private JPanel createInfoItem(String iconPath, JLabel label){
        RoundedPanel card = new RoundedPanel(30);

        card.setBackground(UIColors.CARD);
        card.setLayout(new GridBagLayout());

        Dimension size = new Dimension(250, 80);
        card.setPreferredSize(size);
        card.setMinimumSize(size);
        card.setMaximumSize(size);

        JPanel content = new JPanel(new FlowLayout(FlowLayout.CENTER,15,0));
        content.setOpaque(false);

        label.setFont(AppFont.subtitle());

        content.add(new JLabel(FormUtils.loadIcon(iconPath, 36)));
        content.add(label);

        card.add(content);

        return card;
    }

    private JPanel createFeaturesSection() {
        JPanel container = new JPanel();
        container.setOpaque(false);
        container.setLayout(new BoxLayout(container, BoxLayout.Y_AXIS));

        JLabel title = new JLabel("Amenidades");
        title.setFont(AppFont.subtitle2());
        title.setAlignmentX(Component.CENTER_ALIGNMENT);

        // WRAPPER
        JPanel wrapper = new JPanel(new FlowLayout(FlowLayout.CENTER, 0, 0));
        wrapper.setOpaque(false);

        featuresPanel = new JPanel(new GridLayout(0, 5, 20, 20));
        featuresPanel.setOpaque(false);
        featuresPanel.setPreferredSize(new Dimension(1200, 1));
        featuresPanel.setMinimumSize(new Dimension(1200, 1));
        featuresPanel.setMaximumSize(new Dimension(1200, Integer.MAX_VALUE));

        wrapper.add(featuresPanel);

        container.add(title);
        container.add(Box.createRigidArea(new Dimension(0, 40)));
        container.add(wrapper);

        return container;
    }
    
    private JPanel createCarouselSection() {
        JPanel container = new JPanel();
        container.setOpaque(false);
        container.setLayout(new BoxLayout(container, BoxLayout.Y_AXIS));

        JLabel title = new JLabel("Fotos adicionales");
        title.setFont(AppFont.subtitle2());
        title.setAlignmentX(Component.CENTER_ALIGNMENT);

        carouselPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 20, 0));
        carouselPanel.setOpaque(true);
        carouselPanel.setBackground(getBackground());
        
        carouselScroll = new JScrollPane(carouselPanel);
        carouselScroll.setBorder(null);
        carouselScroll.setOpaque(true);
        carouselScroll.setPreferredSize(new Dimension(1200,200));
        carouselScroll.setMaximumSize(new Dimension(1200,200));
        carouselScroll.setMinimumSize(new Dimension(1200,200));
        carouselScroll.setAlignmentX(Component.CENTER_ALIGNMENT);
        
        carouselScroll.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        carouselScroll.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_NEVER);
        carouselScroll.getViewport().setOpaque(true);
        carouselScroll.getViewport().setBackground(getBackground());
        carouselScroll.getViewport().setScrollMode(JViewport.BACKINGSTORE_SCROLL_MODE);
        carouselScroll.getHorizontalScrollBar().setUnitIncrement(18);
        carouselScroll.getHorizontalScrollBar().setBlockIncrement(350);
        
        //botones
        JPanel controls = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 10));
        controls.setOpaque(false);
        
        RoundedButton left = ButtonFactory.createBlueButton("", "/assets/img/btn-icons/button-left-icon.png", "");
        RoundedButton right = ButtonFactory.createBlueButton("", "/assets/img/btn-icons/button-right-icon.png", "");

        Dimension btnSize = new Dimension(60,60);

        left.setPreferredSize(btnSize);
        right.setPreferredSize(btnSize);

        left.setFont(AppFont.subtitle2());
        right.setFont(AppFont.subtitle2());

        left.setFocusPainted(false);
        right.setFocusPainted(false);

        left.setCursor(new Cursor(Cursor.HAND_CURSOR));
        right.setCursor(new Cursor(Cursor.HAND_CURSOR));

        left.addActionListener(e -> {
            JScrollBar bar = carouselScroll.getHorizontalScrollBar();
            bar.setValue(bar.getValue() - 320);
        });

        right.addActionListener(e -> {
            JScrollBar bar = carouselScroll.getHorizontalScrollBar();
            bar.setValue(bar.getValue() + 320);
        });

        controls.add(left);
        controls.add(right);

        container.add(title);
        container.add(Box.createRigidArea(new Dimension(0, 20)));
        
        //wrapper
        JPanel wrapper = new JPanel(new FlowLayout(FlowLayout.CENTER));
    	wrapper.setOpaque(false);
    	wrapper.add(carouselScroll);
    	
    	container.add(wrapper);
    	container.add(controls);

        return container;
    }
    
    private RoundedImagePanel createCarouselImage(String path) {

        if (path == null || path.trim().isEmpty()) {
            return null;
        }

        RoundedImagePanel panel = new RoundedImagePanel(path, 300, 170, 0);

        Dimension d = new Dimension(300,170);

        panel.setPreferredSize(d);
        panel.setMinimumSize(d);
        panel.setMaximumSize(d);
        
        return panel;
    }
    
    public void setRoom(RoomType room) {
        this.room = room;

        imageContainer.removeAll();
        
        imagePanel = new RoundedImagePanel(room.getImagePath(), 900, 500, 30);
        imageContainer.add(imagePanel);

        lblName.setText(room.getName());
        lblPrice.setText("$" + room.getPrice() + "/noche");
        lblBed.setText(room.getBedType());
        lblCapacity.setText(room.getCapacity() + " huéspedes");

        lblDescription.setText(room.getDescription());
        lblDescription.setSize(730, Short.MAX_VALUE);

        Dimension size = lblDescription.getPreferredSize();
        Dimension descSize = new Dimension(730, size.height);

        lblDescription.setPreferredSize(descSize);
        lblDescription.setMinimumSize(descSize);
        lblDescription.setMaximumSize(descSize);

        // altura total tarjeta
        int panelHeight = size.height + 110;

        bookingLeftPanel.setPreferredSize(new Dimension(800, panelHeight));
        bookingLeftPanel.setMinimumSize(new Dimension(800, panelHeight));
        bookingLeftPanel.revalidate();
        bookingLeftPanel.repaint();
         
        featuresPanel.removeAll();

        for(Amenity amenity : room.getAmenities()){

            RoundedPanel item = new RoundedPanel(25);
            item.setBackground(UIColors.CARD);
            item.setLayout(new GridBagLayout());

            Dimension dim = new Dimension(240, 150);
            
            item.setPreferredSize(dim);
            item.setMinimumSize(dim);
            item.setMaximumSize(dim);

            JPanel content = new JPanel();
            content.setOpaque(false);
            content.setLayout(new BoxLayout(content, BoxLayout.Y_AXIS));
            content.setAlignmentX(Component.CENTER_ALIGNMENT);

            JLabel icon = new JLabel(FormUtils.loadIcon(amenity.getIcon(), 60));
            icon.setAlignmentX(Component.CENTER_ALIGNMENT);

            JLabel text = new JLabel(amenity.getName());
            text.setFont(AppFont.subtitle());
            text.setAlignmentX(Component.CENTER_ALIGNMENT);

            content.add(icon);
            content.add(Box.createRigidArea(new Dimension(0, 6)));
            content.add(text);

            item.add(content);
            
            featuresPanel.add(item);
        }

        int rows = (int) Math.ceil(room.getAmenities().size() / 5.0);

        featuresPanel.setPreferredSize(new Dimension(1200, rows * 170));
        featuresPanel.revalidate();
        featuresPanel.repaint();
        
        //carrusel        
        carouselPanel.removeAll();
        carouselPanel.setSize(carouselPanel.getPreferredSize());

        if(room.getExtraImages() != null && !room.getExtraImages().isEmpty()){

        	for(RoomImage img : room.getExtraImages()){
        		
                if(img == null || img.getImagePath() == null ||
                    img.getImagePath().trim().isEmpty()){
                    continue;
                }

                RoundedImagePanel panel = createCarouselImage(
                    img.getImagePath()
                );

                if(panel != null){
                    carouselPanel.add(panel);
                }
            }
        }

        int count = room.getExtraImages() != null ? room.getExtraImages().size() : 0;

        int width = (count * 320) + 20;
        
        carouselPanel.setPreferredSize(new Dimension(width,170));
        carouselPanel.revalidate();
        carouselPanel.repaint();

        carouselScroll.revalidate();
        carouselScroll.repaint();
        carouselScroll.getViewport().revalidate();
        carouselScroll.getViewport().repaint();
        carouselScroll.getHorizontalScrollBar().revalidate();
        carouselScroll.getHorizontalScrollBar().repaint();
        
        revalidate();
        repaint();
        revalidate();
        repaint();        
    }
    
    public JButton getBtnReserve() {
        return btnReserve;
    }

    public RoomType getRoom() {
        return room;
    }
}