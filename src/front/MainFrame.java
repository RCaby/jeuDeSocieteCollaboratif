package front;

import static javax.swing.WindowConstants.EXIT_ON_CLOSE;

import javax.swing.JFrame;
import javax.swing.JPanel;

import back.Board;

import java.awt.CardLayout;
import java.awt.Color;

public class MainFrame {
    JFrame frame;
    JPanel mainPanel;
    CardLayout mainPanelLayout;
    Board board;

    public static final String WELCOME_SCREEN = "Welcome_Screen";
    public static final String MAIN_SCREEN = "Main_Screen";

    public MainFrame() {

        frame = new JFrame("Game");
        frame.setSize(1280, 720);
        frame.setDefaultCloseOperation(EXIT_ON_CLOSE);

        mainPanelLayout = new CardLayout();
        mainPanel = new JPanel(mainPanelLayout);
        frame.add(mainPanel);

        WelcomeScreen welcomeScreen = new WelcomeScreen(this);

        mainPanel.add(welcomeScreen.getMainPanel(), WELCOME_SCREEN);
        mainPanelLayout.show(mainPanel, WELCOME_SCREEN);

        frame.setVisible(true);
    }

    public void buildMainScreen(int nbPlayers) {
        MainBoardFront mainBoardFront = new MainBoardFront(nbPlayers);
        mainPanel.add(mainBoardFront.getMainPanel(), MAIN_SCREEN);
        mainPanelLayout.show(mainPanel, MainFrame.MAIN_SCREEN);
        board = new Board(mainBoardFront, nbPlayers, "Raph");

    }

    public JPanel getMainPanel() {
        return mainPanel;
    }

    public CardLayout getMainPanelLayout() {
        return mainPanelLayout;
    }

}
