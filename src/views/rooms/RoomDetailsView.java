package views.rooms;

import java.awt.Component;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Insets;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollBar;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JViewport;

import components.RoundedImagePanel;
import components.RoundedPanel;
import models.Amenity;
import models.RoomType;
import utils.AppFont;
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
    private JTextArea lblDescription;
    
    private JPanel carouselPanel;
    private JScrollPane carouselScroll;

    public RoomDetailsView() {
        setOpaque(false);
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));

        add(Box.createRigidArea(new Dimension(0,40)));
        
        add(createImageSection());

        add(Box.createRigidArea(new Dimension(0,40)));
	    add(VisualUtils.createDivider()); 
        add(Box.createRigidArea(new Dimension(0,40)));

        add(createTitleSection());

        add(Box.createRigidArea(new Dimension(0,40)));

        add(createInfoSection());

        add(Box.createRigidArea(new Dimension(0,40)));
	    add(VisualUtils.createDivider()); 
        add(Box.createRigidArea(new Dimension(0,40)));

        add(createDescriptionSection());

        add(Box.createRigidArea(new Dimension(0,10)));
	    add(VisualUtils.createDivider()); 
        add(Box.createRigidArea(new Dimension(0,40)));

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

    private JPanel createInfoSection() {
        JPanel panel = new JPanel(new FlowLayout(FlowLayout.CENTER,30,0));
        panel.setOpaque(false);

        lblPrice = new JLabel();
        lblBed = new JLabel();
        lblCapacity = new JLabel();

        panel.add(createInfoItem("/assets/img/icons/price-icon.png", lblPrice));
        panel.add(createInfoItem("/assets/img/icons/bed-icon.png", lblBed));
        panel.add(createInfoItem("/assets/img/icons/guest-icon.png", lblCapacity));

        return panel;
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
    
    private JPanel createDescriptionSection() {
        JPanel panel = new JPanel();
        panel.setOpaque(false);
        panel.setLayout(new BoxLayout(panel,BoxLayout.Y_AXIS));

        JLabel title = new JLabel("Descripción");
        title.setFont(AppFont.subtitle2());
        title.setAlignmentX(Component.CENTER_ALIGNMENT);

        lblDescription = new JTextArea();
        lblDescription.setOpaque(false);
        lblDescription.setFocusable(false);
        lblDescription.setMaximumSize(new Dimension(700,Integer.MAX_VALUE));
        lblDescription.setFont(AppFont.subtitle());
        lblDescription.setEditable(false);
        lblDescription.setLineWrap(true);
        lblDescription.setWrapStyleWord(true);
        lblDescription.setColumns(50);
        lblDescription.setMargin(new Insets(30, 0, 30, 0));

        panel.add(title);
        panel.add(Box.createRigidArea(new Dimension(0, 20)));
        panel.add(lblDescription);

        return panel;
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
        carouselScroll.setPreferredSize(new Dimension(1000,200));
        carouselScroll.setMaximumSize(new Dimension(1000,200));
        carouselScroll.setMinimumSize(new Dimension(1000,200));
        carouselScroll.setAlignmentX(Component.CENTER_ALIGNMENT);
        
        carouselScroll.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        carouselScroll.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_NEVER);
        carouselScroll.getViewport().setOpaque(true);
        carouselScroll.getViewport().setBackground(getBackground());
        carouselScroll.getViewport().setScrollMode(JViewport.BACKINGSTORE_SCROLL_MODE);
        carouselScroll.getHorizontalScrollBar().setUnitIncrement(18);
        carouselScroll.getHorizontalScrollBar().setBlockIncrement(350);
        
        JPanel controls = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 10));
        controls.setOpaque(false);

        JLabel left = new JLabel("<");
        JLabel right = new JLabel(">");
        
        left.setFont(AppFont.title());
        right.setFont(AppFont.title());

        left.setPreferredSize(new Dimension(60,60));
        right.setPreferredSize(new Dimension(60,60));
        
        left.setHorizontalAlignment(JLabel.CENTER);
        right.setHorizontalAlignment(JLabel.CENTER);

        left.setVerticalAlignment(JLabel.CENTER);
        right.setVerticalAlignment(JLabel.CENTER);
        
        left.setCursor(new Cursor(Cursor.HAND_CURSOR));
        right.setCursor(new Cursor(Cursor.HAND_CURSOR));

        left.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e){
                JScrollBar bar = carouselScroll.getHorizontalScrollBar();

                bar.setValue(bar.getValue() - 320);
            }
        });        
        
        right.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e){
                JScrollBar bar = carouselScroll.getHorizontalScrollBar();

                bar.setValue(bar.getValue() + 320);
            }
        });
        
        controls.add(left);
        controls.add(right);

        container.add(title);
        container.add(Box.createRigidArea(new Dimension(0, 20)));
        
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
        imageContainer.removeAll();

        imagePanel = new RoundedImagePanel(room.getImagePath(), 900, 500, 30);

        imageContainer.add(imagePanel);

        lblName.setText(room.getName());
        lblPrice.setText("$" + room.getPrice() + "/noche");
        lblBed.setText(room.getBedType());
        lblCapacity.setText(room.getCapacity() + " huéspedes");

        lblDescription.setText(room.getDescription());
        lblDescription.setSize(900, Short.MAX_VALUE);

        Dimension size = lblDescription.getPreferredSize();
        lblDescription.setMaximumSize(new Dimension(1000, size.height));

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

        if (room.getExtraImages() != null && !room.getExtraImages().isEmpty()) {
        	for (String img : room.getExtraImages()) {

        	    if (img == null || img.trim().isEmpty()) {
        	    	continue;
        	    }

        	    RoundedImagePanel panel = createCarouselImage(img);

        	    if (panel != null) {
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
}