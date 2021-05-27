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
    private JComboBox<String> difficultyChoice;

    public WelcomeScreen(MainFrame mainFrame, ResourceBundle stringsBundle) {
        this.stringsBundle = stringsBundle;
        this.mainFrame = mainFrame;
        mainPanelContainer = new JPanel();
        mainPanelContainer.setLayout(new BorderLayout(0, 250));
        mainPanelContainer.add(new JPanel(), BorderLayout.NORTH);
        var mainPanel = new JPanel();
        mainPanelContainer.add(mainPanel, BorderLayout.CENTER);

        var validateButton = new JButton(stringsBundle.getString("startButton"));

        changeFont(validateButton, 20);
        var validateButtonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        validateButtonPanel.add(validateButton);
        validateButton.addActionListener(new ValidateListener());
        var namePanel = new JPanel();
        nameField = new JTextField(stringsBundle.getString("defaultName"));
        nameField.setPreferredSize(new Dimension(100, 30));
        changeFont(nameField, 18);
        var nameLabel = new JLabel(stringsBundle.getString("yourNameLabel"));
        changeFont(nameLabel, 18);
        namePanel.add(nameLabel);
        namePanel.add(nameField);

        var difficultyPanel = new JPanel();
        var difficultyLabel = new JLabel(stringsBundle.getString("difficulty"));
        changeFont(difficultyLabel, 18);
        difficultyChoice = new JComboBox<>();
        changeFont(difficultyChoice, 16);
        difficultyChoice.addItem(stringsBundle.getString("difficultyEasy"));
        difficultyChoice.addItem(stringsBundle.getString("difficultyMedium"));
        difficultyChoice.addItem(stringsBundle.getString("difficultyHard"));
        difficultyChoice.setSelectedIndex(1);
        difficultyPanel.add(difficultyLabel);
        difficultyPanel.add(difficultyChoice);

        var nbPlayerPanel = new JPanel();
        var nbPlayerLabel = new JLabel(stringsBundle.getString("nbPlayerLabel"));
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
        wrapPanel.add(difficultyPanel);
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
            int difficulty = difficultyChoice.getSelectedIndex();
            mainFrame.buildMainScreen(nbPlayers, playerName, difficulty);
        }

    }
}
