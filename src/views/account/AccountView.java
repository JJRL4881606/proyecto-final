package views.account;

import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;
import components.RoundedButton;
import components.RoundedPanel;
import utils.AppFont;
import utils.ButtonFactory;
import utils.UIColors;

@SuppressWarnings("serial")
public class AccountView extends JPanel {

	private JLabel lblName;
	private JLabel lblEmail;
	private JLabel lblPhone;
	private JLabel lblCountry; 
	private JLabel lblBirthDate;
	private JLabel lblGender;
	private RoundedButton btnEdit, btnPassword, btnLogout;
	
	private JLabel lblNoSession;
	private RoundedButton btnLogin;

	public AccountView() {
		setBackground(Color.white);
		setLayout(new GridBagLayout());
		setBorder(new EmptyBorder(40, 0, 40, 0));
		
		JPanel card = new RoundedPanel(50);
		card.setBackground(UIColors.CARD);
		card.setLayout(new BoxLayout(card, BoxLayout.Y_AXIS));
		card.setBorder(new EmptyBorder(35, 45, 35, 45));
		card.setMaximumSize(new Dimension(650, Integer.MAX_VALUE));
		card.putClientProperty("FlatLaf.style", "arc:20");

		JLabel title = new JLabel("Mi cuenta");
		title.setFont(AppFont.title());
		title.setAlignmentX(CENTER_ALIGNMENT);
		card.add(title);
		card.add(Box.createRigidArea(new Dimension(0, 30)));
		
		lblNoSession = new JLabel("Inicia sesión para ver los datos de tu cuenta");
		lblNoSession.setFont(AppFont.subtitle());
		lblNoSession.setAlignmentX(CENTER_ALIGNMENT);
		lblNoSession.setVisible(false);
		card.add(lblNoSession);
		card.add(Box.createRigidArea(new Dimension(0,20)));
		
		btnLogin = ButtonFactory.createGoldButton(
		    "INICIAR SESIÓN",
		    "/assets/img/btn-icons/button-login-icon.png",
		    "Ir a iniciar sesión"
		);
		btnLogin.setAlignmentX(CENTER_ALIGNMENT);
		btnLogin.setVisible(false);
		card.add(Box.createRigidArea(new Dimension(0,20)));
		card.add(btnLogin);

		card.add(lblName = createLabel());
		card.add(Box.createVerticalStrut(15));
		card.add(lblEmail = createLabel());
		card.add(Box.createVerticalStrut(15));
		card.add(lblPhone = createLabel());
		card.add(Box.createVerticalStrut(15));
		card.add(lblCountry = createLabel());
		card.add(Box.createVerticalStrut(15));
		card.add(lblBirthDate = createLabel());
		card.add(Box.createVerticalStrut(15));
		card.add(lblGender = createLabel());
		card.add(Box.createRigidArea(new Dimension(0, 35)));

		JPanel buttons = new JPanel(new GridLayout(3, 1, 0, 12));
		buttons.setOpaque(false);
		Dimension btnSize = new Dimension(350, 160);
		buttons.setMaximumSize(btnSize);
		buttons.setPreferredSize(btnSize);

		btnEdit = ButtonFactory.createGoldButton(
			"EDITAR PERFIL", 
			"/assets/img/btn-icons/button-edit-icon.png", 
			"Editar"
		);
		
		btnPassword = ButtonFactory.createGoldButton(
			"CAMBIAR CONTRASEÑA",
			"/assets/img/btn-icons/button-password-icon.png", 
			"Cambiar"
		);
		
		btnLogout = ButtonFactory.createGoldButton(
			"CERRAR SESIÓN",
			"/assets/img/btn-icons/button-logout-icon.png",
			"Salir"
		);

		buttons.add(btnEdit);
		buttons.add(btnPassword);
		buttons.add(btnLogout);
		card.add(buttons);

		GridBagConstraints gbc = new GridBagConstraints();
		gbc.gridx = 0;
		gbc.gridy = 0;
		add(card, gbc);
	}

	private JLabel createLabel() {
		JLabel lbl = new JLabel();
		lbl.setFont(AppFont.subtitle());
		lbl.setAlignmentX(Component.CENTER_ALIGNMENT);
		lbl.setHorizontalAlignment(SwingConstants.CENTER);
		return lbl;
	}

	public JLabel getLblName() { return lblName; }
	public JLabel getLblEmail() { return lblEmail; }
	public JLabel getLblPhone() { return lblPhone; }
	public JLabel getLblCountry() { return lblCountry; }
	public JLabel getLblBirthDate() { return lblBirthDate; }
	public JLabel getLblGender() { return lblGender; }
	public RoundedButton getBtnEdit() { return btnEdit; }
	public RoundedButton getBtnPassword() { return btnPassword; }
	public RoundedButton getBtnLogout() { return btnLogout; }
	
	public JLabel getLblNoSession() {
	    return lblNoSession;
	}
	
	public RoundedButton getBtnLogin() {
	    return btnLogin;
	}
}