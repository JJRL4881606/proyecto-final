package components;

import java.awt.Dimension;
import java.awt.GridLayout;
import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JPanel;
import utils.AppFont;
import utils.ButtonFactory;
import utils.FormUtils;
import utils.UIColors;

@SuppressWarnings("serial")
public class ReservationCard extends RoundedPanel {

    private JLabel lblRoom,lblDates,lblGuests,lblStatus,lblTotal;
    private RoundedButton btnCancel;

    public ReservationCard() {

        super(50);

        setBackground(UIColors.CARD);
        setBorder(BorderFactory.createEmptyBorder(35,45,35,45));

        JPanel content = new JPanel(new GridLayout(2,3,40,25));
        content.setOpaque(false);

        lblRoom = createInfoLabel("/assets/img/icons/room-icon.png");
        lblGuests = createInfoLabel("/assets/img/icons/guest-icon.png");

        lblDates = createInfoLabel("/assets/img/icons/calendar-icon.png");
        lblTotal = createInfoLabel("/assets/img/icons/payment-icon-2.png");

        lblStatus = createInfoLabel("/assets/img/icons/status-icon.png");

        //boton cancelar
        JPanel btnPanel = new JPanel();
        btnPanel.setOpaque(false);

        btnCancel = ButtonFactory.createBlueButton(
            "Cancelar",
            "/assets/img/btn-icons/button-delete-white-icon.png",
            "Cancelar reservación"
        );

        btnCancel.setPreferredSize(new Dimension(150,45));
        btnCancel.setBackground(UIColors.DELETE);

        btnPanel.add(btnCancel);

        //agregar elementos
        content.add(lblRoom);
        content.add(lblDates);
        content.add(lblStatus);

        content.add(lblGuests);
        content.add(lblTotal);
        content.add(btnPanel);

        add(content);
    }

    private JLabel createInfoLabel(String iconPath){
        JLabel lbl = new JLabel(FormUtils.loadIcon(iconPath,30));
        lbl.setFont(AppFont.subtitle());
        return lbl;
    }

    public JLabel getLblRoom(){ return lblRoom; }
    public JLabel getLblDates(){ return lblDates; }
    public JLabel getLblGuests(){ return lblGuests; }
    public JLabel getLblStatus(){ return lblStatus; }
    public JLabel getLblTotal(){ return lblTotal; }
    public RoundedButton getBtnCancel(){ return btnCancel; }
}