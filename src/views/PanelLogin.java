package views;

import javax.swing.BoxLayout;
import javax.swing.JPanel;

@SuppressWarnings("serial")
public class PanelLogin extends JPanel {

    public PanelLogin() {
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
       
        LoginView vistaLogin = new LoginView();
        add(vistaLogin);
    }
}