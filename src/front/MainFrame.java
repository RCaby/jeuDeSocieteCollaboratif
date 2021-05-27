package front;

import static javax.swing.WindowConstants.EXIT_ON_CLOSE;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.UIManager;

import back.Board;

import java.awt.CardLayout;
import java.util.Locale;
import java.util.ResourceBundle;

import javax.swing.UIManager.*;

public class MainFrame {
    JFrame frame;
    JPanel mainPanel;
    CardLayout mainPanelLayout;
    Board board;
    private ResourceBundle stringsBundle;

    public static final String WELCOME_SCREEN = "Welcome_Screen";
    public static final String MAIN_SCREEN = "Main_Screen";

    public MainFrame() {
        try {
            for (LookAndFeelInfo info : UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (Exception e) {
            // If Nimbus is not available, you can set the GUI to another look and feel.
        }

        frame = new JFrame("Game");
        frame.setSize(1280, 720);
        frame.setDefaultCloseOperation(EXIT_ON_CLOSE);

        var locale = new Locale("en", "US");
        stringsBundle = ResourceBundle.getBundle("Strings", locale);

        mainPanelLayout = new CardLayout();
        mainPanel = new JPanel(mainPanelLayout);
        frame.add(mainPanel);

        var welcomeScreen = new WelcomeScreen(this, stringsBundle);

        mainPanel.add(welcomeScreen.getMainPanelContainer(), WELCOME_SCREEN);
        mainPanelLayout.show(mainPanel, WELCOME_SCREEN);

        frame.setVisible(true);
    }

    public void buildMainScreen(int nbPlayers, String namePlayer, int difficulty) {
        var mainBoardFront = new MainBoardFront(nbPlayers, stringsBundle);
        mainPanel.add(mainBoardFront.getMainPanel(), MAIN_SCREEN);
        mainPanelLayout.show(mainPanel, MainFrame.MAIN_SCREEN);
        board = new Board(mainBoardFront, nbPlayers, stringsBundle, namePlayer, difficulty);

    }

    /**
     * The getter for the attribute {@link MainFrame#mainPanel}.
     * 
     * @return the main panel
     */
    public JPanel getMainPanel() {
        return mainPanel;
    }

    /**
     * The getter for the attribute {@link MainFrame#mainPanelLayout}.
     * 
     * @return the cardLayout of the main panel
     */
    public CardLayout getMainPanelLayout() {
        return mainPanelLayout;
    }

}
