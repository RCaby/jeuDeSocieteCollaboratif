package front;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JPanel;
import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ResourceBundle;

public class WelcomeScreen {
    JPanel mainPanel;
    MainFrame mainFrame;
    int nbPlayers;
    JComboBox<Integer> nbPlayerBox;
    ResourceBundle stringsBundle;

    public WelcomeScreen(MainFrame mainFrame, ResourceBundle stringsBundle) {
        this.stringsBundle = stringsBundle;
        this.mainFrame = mainFrame;
        mainPanel = new JPanel();
        mainPanel.setLayout(new BorderLayout());

        JButton validateButton = new JButton("Validate");// FIXME
        validateButton.addActionListener(new ValidateListener());
        nbPlayerBox = new JComboBox<>();
        for (int i = 3; i < 13; i++) {
            nbPlayerBox.addItem(i);
        }

        JPanel chooseNbPlayer = new JPanel();
        chooseNbPlayer.add(nbPlayerBox);
        chooseNbPlayer.add(validateButton);
        mainPanel.add(chooseNbPlayer, BorderLayout.CENTER);

    }

    public JPanel getMainPanel() {
        return mainPanel;
    }

    private class ValidateListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            nbPlayers = (int) nbPlayerBox.getSelectedItem();
            mainFrame.buildMainScreen(nbPlayers);
        }

    }
}
