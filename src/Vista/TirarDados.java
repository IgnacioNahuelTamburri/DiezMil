package Vista;

import javax.swing.*;

public class TirarDados extends JDialog {
    private JPanel contentPane;
    private JButton buttonTirar;
    private JLabel texto;

    public TirarDados() {
        setContentPane(contentPane);
        setModal(true);
        getRootPane().setDefaultButton(buttonTirar);
        setIconImage(new ImageIcon("src/Images/Logo.png").getImage());

        buttonTirar.addActionListener(e -> onOK());
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
    }

    private void onOK() {
        // add your code here
        dispose();
    }

}
