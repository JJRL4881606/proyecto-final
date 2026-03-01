package views;

import java.awt.BorderLayout;
import java.awt.GridBagLayout;

import javax.swing.JPanel;

@SuppressWarnings("serial")
public abstract class BaseFormView extends JPanel {

    protected JPanel contenedorFormulario;

    public BaseFormView() {
        setLayout(new BorderLayout());

        contenedorFormulario = crearContenedorFormulario();
        add(contenedorFormulario, BorderLayout.CENTER);
    }

    private JPanel crearContenedorFormulario() {
        JPanel panel = new JPanel();
        panel.setLayout(new GridBagLayout());
        panel.setOpaque(false);
        return panel;
    }
}