package views.account;

import java.awt.Color;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import utils.AppFont;

@SuppressWarnings("serial")
public class MyReservationsView extends JPanel {

    private JPanel cardsContainer;
    private JLabel lblNoReservations;
    
    private JLabel lblInfo;
    private JLabel lblPolicy;
    private JLabel lblOrder;

    public MyReservationsView() {

        setBackground(Color.WHITE);
        setLayout(new GridBagLayout());
        setBorder(new EmptyBorder(60, 40, 60, 40));
        
        JPanel card = new JPanel();
        card.setBackground(Color.WHITE);
        card.setLayout(new BoxLayout(card, BoxLayout.Y_AXIS));
        card.setBorder(new EmptyBorder(35, 45, 35, 45));
        
        JLabel title = new JLabel("Mis reservaciones");
        title.setFont(AppFont.title());
        title.setAlignmentX(CENTER_ALIGNMENT);
        title.setHorizontalAlignment(JLabel.CENTER);
        title.setMaximumSize(title.getPreferredSize());

        lblOrder = new JLabel("<html><center>En orden de más recientes a más antiguas.</center></html>");
        lblOrder.setFont(AppFont.normal());
    	lblOrder.setForeground(new Color(80,80,80));
    	lblOrder.setAlignmentX(CENTER_ALIGNMENT);
    	lblOrder.setHorizontalAlignment(JLabel.CENTER);
    	lblOrder.setMaximumSize(lblOrder.getPreferredSize());
    	
        lblInfo = new JLabel("<html><center>Para modificar fechas, huéspedes o detalles de una reservación,<br>comunícate con recepción al +971 4 426 0000.</center></html>");
    	lblInfo.setFont(AppFont.normal());
    	lblInfo.setForeground(new Color(80,80,80));
    	lblInfo.setAlignmentX(CENTER_ALIGNMENT);
    	lblInfo.setHorizontalAlignment(JLabel.CENTER);
    	lblInfo.setMaximumSize(lblInfo.getPreferredSize());

    	lblPolicy = new JLabel("Las cancelaciones están sujetas a las políticas del hotel.");
    	lblPolicy.setFont(AppFont.normal());
    	lblPolicy.setForeground(new Color(120,120,120));
    	lblPolicy.setAlignmentX(CENTER_ALIGNMENT);
    	lblPolicy.setHorizontalAlignment(JLabel.CENTER);
    	lblPolicy.setMaximumSize(lblPolicy.getPreferredSize());

    	card.add(title);
    	card.add(Box.createVerticalStrut(15));
    	card.add(lblOrder);
    	card.add(Box.createVerticalStrut(15));
    	card.add(lblInfo);
    	card.add(Box.createVerticalStrut(10));
    	card.add(lblPolicy);
    	card.add(Box.createVerticalStrut(25));

        lblNoReservations = new JLabel("No tienes reservaciones registradas");
        lblNoReservations.setFont(AppFont.subtitle());
        lblNoReservations.setAlignmentX(CENTER_ALIGNMENT);
        lblNoReservations.setVisible(false);
        lblNoReservations.setHorizontalAlignment(JLabel.CENTER);

        cardsContainer = new JPanel();
        cardsContainer.setOpaque(false);
        cardsContainer.setLayout(new BoxLayout(cardsContainer, BoxLayout.Y_AXIS));

        card.add(lblNoReservations);
        card.add(cardsContainer);

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.weightx = 1;

        add(card, gbc);
    }

    public JPanel getCardsContainer() {
        return cardsContainer;
    }

    public JLabel getLblNoReservations() {
        return lblNoReservations;
    }
}