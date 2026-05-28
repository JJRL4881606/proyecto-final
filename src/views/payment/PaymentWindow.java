package views.payment;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Image;
import java.awt.Toolkit;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.SwingConstants;

import components.RoundedPanel;
import controllers.payment.PaymentController;
import models.RoomType;
import models.User;
import utils.UIColors;

@SuppressWarnings("serial")
public class PaymentWindow extends JFrame {

    private PaymentView paymentView;
    private JScrollPane scroll;
    private RoomType room;
    private User user;
    
    public PaymentWindow(RoomType room,User user) {
		this.room = room;
		this.user = user;
	    setTitle("ATLANTIS THE PALM, FORMA DE PAGO");
	    setExtendedState(JFrame.MAXIMIZED_BOTH);
	    setResizable(true);
        
		// setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
	     
        setLocationRelativeTo(null);

        // PANEL PRINCIPAL
        RoundedPanel background = new RoundedPanel(25);
        background.setLayout(new BorderLayout());
        background.setBackground(new Color(100, 149, 237));
        setContentPane(background);
        
        // ICONO
        Image icon = Toolkit.getDefaultToolkit().getImage(
                getClass().getResource("/assets/img/logos/hotel-icon.png"));
        setIconImage(icon);

        // PANEL CENTRAL
        JPanel centerPanel = new JPanel(new BorderLayout(20, 0));
        centerPanel.setBackground(UIColors.HEADER);
        centerPanel.setBorder(
                BorderFactory.createEmptyBorder(30, 30, 30, 30)
        );

        // PANEL IZQUIERDO FOTO
        JPanel leftImagePanel = createSideImagePanel(
                "/assets/img/payment/left-banner.jpg"
        );
        leftImagePanel.setBackground(Color.BLACK);
        leftImagePanel.setBorder(
                BorderFactory.createEmptyBorder(
                        3, 3, 3, 3)
        );

        // PANEL DERECHO FOTO
        JPanel rightImagePanel = createSideImagePanel(
                "/assets/img/payment/right-banner.jpg"
        );
        rightImagePanel.setBackground(Color.BLACK);
        rightImagePanel.setBorder(
                BorderFactory.createEmptyBorder(
                        3, 3, 3, 3)
        );

        // PANEL DEL FORMULARIO
        paymentView = new PaymentView(room,user);
        paymentView.setBackground(Color.WHITE);

        new PaymentController(this, paymentView, user);

        centerPanel.add(leftImagePanel, BorderLayout.WEST);
        centerPanel.add(paymentView, BorderLayout.CENTER);
        centerPanel.add(rightImagePanel, BorderLayout.EAST);

        Dimension screen = Toolkit.getDefaultToolkit().getScreenSize();

        centerPanel.setPreferredSize(
                new Dimension(screen.width - 20,
                              centerPanel.getPreferredSize().height)
        );

        // PANEL QUE CENTRA EL centerPanel
        JPanel wrapper = new JPanel(new GridBagLayout());
        wrapper.setBackground(UIColors.HEADER);

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.weightx = 1;
        gbc.weighty = 1;
        gbc.anchor = GridBagConstraints.CENTER;

        wrapper.add(centerPanel, gbc);

        // AGREGAR EL WRAPPER AL SCROLL
        background.add(
                createMainScroll(wrapper),
                BorderLayout.CENTER
        );

        setVisible(true);
    }
    
    private JPanel createSideImagePanel(String imagePath) {

    		JPanel panel = new JPanel(new BorderLayout());

        panel.setBackground(Color.WHITE);
        panel.setPreferredSize(
                new Dimension(260, 900));

        JLabel imageLabel = new JLabel();
        imageLabel.setHorizontalAlignment(
                SwingConstants.CENTER);

        java.net.URL imageURL =
                getClass().getResource(imagePath);

        if (imageURL != null) {

            ImageIcon icon =
                    new ImageIcon(imageURL);

            Image img =
                    icon.getImage().getScaledInstance(
                            560,
                            1200,
                            Image.SCALE_SMOOTH
                    );

            imageLabel.setIcon(
                    new ImageIcon(img));

        }

        panel.add(imageLabel,
                BorderLayout.CENTER);

        return panel;
    }

    private JScrollPane createMainScroll(JPanel panel) {

        scroll = new JScrollPane(panel);

        scroll.setBorder(null);

        scroll.getVerticalScrollBar()
                .setUnitIncrement(16);

        scroll.setHorizontalScrollBarPolicy(
                JScrollPane.HORIZONTAL_SCROLLBAR_NEVER
        );

        scroll.getViewport().setBackground(
                UIColors.HEADER
        );

        scroll.setBackground(UIColors.HEADER);

        return scroll;
    }

    public PaymentView getPaymentView() {
        return paymentView;
    }

    public JScrollPane getScroll() {
        return scroll;
    }
}