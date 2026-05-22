package views.payment;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.KeyEvent;

import javax.swing.*;
import javax.swing.border.EmptyBorder;

import components.RoundedPanel;
import components.UnderlineMenu;
import controllers.main.MainController;
import controllers.payment.PaymentController;
import utils.AppFont;
import utils.UIColors;
import views.main.MainView;

import java.awt.*;
import javax.swing.*;
import javax.swing.border.EmptyBorder;

public class PaymentWindow extends JFrame {

    private PaymentView paymentView;
    private JScrollPane scroll;

    public PaymentWindow() {

        setTitle("ATLANTIS THE PALM, FORMA DE PAGO");
        setExtendedState(JFrame.MAXIMIZED_BOTH);
        setResizable(true);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
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

        // VIEW
        paymentView = new PaymentView();
        new PaymentController(this, paymentView);

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
        leftImagePanel.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(
                		Color.BLACK, 1),
                BorderFactory.createEmptyBorder(
                        3, 3, 3, 3)
        ));

        // PANEL DERECHO FOTO
        JPanel rightImagePanel = createSideImagePanel(
                "/assets/img/payment/right-banner.jpg"
        );
        rightImagePanel.setBackground(Color.BLACK);
        rightImagePanel.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(
                		Color.BLACK, 1),
                BorderFactory.createEmptyBorder(
                        3, 3, 3, 3)
        ));

        // PANEL DEL FORMULARIO
        paymentView = new PaymentView();
        paymentView.setBackground(Color.WHITE);

        new PaymentController(this, paymentView);

        centerPanel.add(leftImagePanel, BorderLayout.WEST);
        centerPanel.add(paymentView, BorderLayout.CENTER);
        centerPanel.add(rightImagePanel, BorderLayout.EAST);

        background.add(
                createMainScroll(centerPanel),
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

        // Bordes redondeados
        panel.setBorder(
            BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(
                    new Color(220,220,220),
                    1,
                    true
                ),
                BorderFactory.createEmptyBorder(
                    0,0,0,0
                )
            )
        );

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

        // Fondo azul detrás
        scroll.getViewport().setBackground(
                UIColors.HEADER
        );

        // Opcional: quitar fondo gris feo
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