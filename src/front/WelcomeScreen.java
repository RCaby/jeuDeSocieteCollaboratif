package front;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ResourceBundle;

public class WelcomeScreen {
    JPanel mainPanelContainer;
    MainFrame mainFrame;
    int nbPlayers;
    JComboBox<Integer> nbPlayerBox;
    ResourceBundle stringsBundle;
    private JTextField nameField;

    public WelcomeScreen(MainFrame mainFrame, ResourceBundle stringsBundle) {
        this.stringsBundle = stringsBundle;
        this.mainFrame = mainFrame;
        mainPanelContainer = new JPanel();
        mainPanelContainer.setLayout(new BorderLayout(0, 250));
        mainPanelContainer.add(new JPanel(), BorderLayout.NORTH);
        var mainPanel = new JPanel();
        mainPanelContainer.add(mainPanel, BorderLayout.CENTER);

        var validateButton = new JButton("Start");
        changeFont(validateButton, 20);
        var validateButtonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        validateButtonPanel.add(validateButton);
        validateButton.addActionListener(new ValidateListener());
        var namePanel = new JPanel();
        nameField = new JTextField("Player");
        nameField.setPreferredSize(new Dimension(100, 30));
        changeFont(nameField, 18);
        var nameLabel = new JLabel("Your name : ");
        changeFont(nameLabel, 18);
        namePanel.add(nameLabel);
        namePanel.add(nameField);

        var nbPlayerPanel = new JPanel();
        var nbPlayerLabel = new JLabel("Number of player : ");
        changeFont(nbPlayerLabel, 18);
        nbPlayerBox = new JComboBox<>();
        for (var i = 3; i < 13; i++) {
            nbPlayerBox.addItem(i);
        }
        nbPlayerPanel.add(nbPlayerLabel);
        nbPlayerPanel.add(nbPlayerBox);

        var wrapPanel = new JPanel();
        wrapPanel.setLayout(new BoxLayout(wrapPanel, BoxLayout.Y_AXIS));
        mainPanel.add(wrapPanel, BorderLayout.CENTER);
        wrapPanel.add(namePanel);
        wrapPanel.add(nbPlayerPanel);
        wrapPanel.add(validateButtonPanel);

    }

    public JPanel getMainPanelContainer() {
        return mainPanelContainer;
    }

    /**
     * Changes the font size of a given component.
     * 
     * @param component the component which will have its font changed
     * @param size      the new size of the font
     */
    private void changeFont(JComponent component, int size) {
        component.setFont(new Font(component.getFont().getName(), component.getFont().getStyle(), size));
    }

    private class ValidateListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            nbPlayers = (int) nbPlayerBox.getSelectedItem();
            var playerName = nameField.getText();
            mainFrame.buildMainScreen(nbPlayers, playerName);
        }

    }
}
