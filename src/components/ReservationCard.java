package components;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSeparator;

import utils.AppFont;
import utils.ButtonFactory;
import utils.FormUtils;
import utils.UIColors;

@SuppressWarnings("serial")
public class ReservationCard extends RoundedPanel {

    private JLabel lblRoom;
    private JLabel lblGuests;
    private JLabel lblCheckIn;
    private JLabel lblCheckOut;
    private JLabel lblStatus;
    private JLabel lblTotal;
    private JLabel lblCreatedAt;
    
    private RoundedButton btnCancel;

    public ReservationCard() {

        super(50);

        setBackground(UIColors.CARD);
        setBorder(BorderFactory.createEmptyBorder(22,35,22,35));
        
        // Labels
        lblRoom = createInfoLabel("/assets/img/icons/room-icon.png");
        lblGuests = createInfoLabel("/assets/img/icons/guest-icon.png");

        lblCheckIn = createInfoLabel("/assets/img/icons/calendar-icon.png");
        lblCheckOut = createInfoLabel("/assets/img/icons/calendar-icon.png");

        lblTotal = createInfoLabel("/assets/img/icons/payment-icon-2.png");
        
        lblStatus = createInfoLabel("/assets/img/icons/status-icon.png");
        lblStatus.setHorizontalAlignment(JLabel.CENTER);

        lblCreatedAt = createInfoLabel("/assets/img/icons/calendar-icon.png");

        // Botón cancelar
        JPanel btnPanel = new JPanel();
        btnPanel.setOpaque(false);
        btnPanel.setLayout(new FlowLayout(FlowLayout.CENTER));

        btnCancel = ButtonFactory.createBlueButton(
            "Cancelar",
            "/assets/img/btn-icons/button-delete-white-icon.png",
            "Cancelar reservación"
        );

        btnCancel.setPreferredSize(new Dimension(150,45));
        btnCancel.setBackground(UIColors.DELETE);

        btnPanel.add(btnCancel);

        // panel izquierdo 2x 3
        JPanel leftPanel = new JPanel(new GridBagLayout());
        leftPanel.setOpaque(false);

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.anchor = GridBagConstraints.WEST;
        gbc.insets = new Insets(0, 0, 25, 25);

        // fila 1
        gbc.gridx = 0; gbc.gridy = 0; gbc.weightx = 0.25;
        leftPanel.add(lblRoom, gbc);

        gbc.gridx = 1; gbc.weightx = 0.25;
        leftPanel.add(lblCheckIn, gbc);

        gbc.gridx = 2; gbc.weightx = 0.50;
        leftPanel.add(lblCreatedAt, gbc);

        // fila 2
        gbc.gridx = 0; gbc.gridy = 1;
        gbc.weightx = 0.25;
        leftPanel.add(lblGuests, gbc);

        gbc.gridx = 1;
        gbc.weightx = 0.25;
        leftPanel.add(lblCheckOut, gbc);

        gbc.gridx = 2;
        gbc.weightx = 0.50;
        leftPanel.add(lblTotal, gbc);

        // separador vertical
        JSeparator separator = new JSeparator(JSeparator.VERTICAL);
        separator.setForeground(new Color(180,180,180));
        
        JPanel separatorPanel = new JPanel(new BorderLayout());
        separatorPanel.setOpaque(false);
        separatorPanel.setBorder(BorderFactory.createEmptyBorder(0,0,0,25));
        separatorPanel.add(separator, BorderLayout.CENTER);

        // panel derecho
        JPanel rightPanel = new JPanel();
        rightPanel.setOpaque(false);
        rightPanel.setLayout(new BoxLayout(rightPanel, BoxLayout.Y_AXIS));

        lblStatus.setAlignmentX(CENTER_ALIGNMENT);
        btnPanel.setAlignmentX(CENTER_ALIGNMENT);

        rightPanel.add(Box.createVerticalGlue());
        rightPanel.add(lblStatus);
        rightPanel.add(Box.createVerticalStrut(8));
        rightPanel.add(btnPanel);
        rightPanel.add(Box.createVerticalGlue());

        // contenedor derecho
        JPanel rightContainer = new JPanel(new BorderLayout());
        rightContainer.setOpaque(false);
        rightContainer.setPreferredSize(new Dimension(180, 80));
        
        rightContainer.add(separatorPanel, BorderLayout.WEST);
        rightContainer.add(rightPanel, BorderLayout.CENTER);

        // contenedor principal
        JPanel content = new JPanel(new BorderLayout(15,0));
        content.setOpaque(false);

        content.add(leftPanel, BorderLayout.CENTER);
        content.add(rightContainer, BorderLayout.EAST);

        setLayout(new BorderLayout());
        add(content, BorderLayout.CENTER);
    }

    //crear una label de informacion
    private JLabel createInfoLabel(String iconPath){
        JLabel lbl = new JLabel(FormUtils.loadIcon(iconPath,30));
        lbl.setFont(AppFont.subtitle());
        return lbl;
    }

    //getters
    public JLabel getLblRoom(){ return lblRoom; }
    public JLabel getLblCheckIn(){ return lblCheckIn; }
    public JLabel getLblCheckOut(){ return lblCheckOut; }
    public JLabel getLblGuests(){ return lblGuests; }
    public JLabel getLblStatus(){ return lblStatus; }
    public JLabel getLblTotal(){ return lblTotal; }
    public JLabel getLblCreatedAt(){ return lblCreatedAt; }
    
    public RoundedButton getBtnCancel(){ return btnCancel; }
}