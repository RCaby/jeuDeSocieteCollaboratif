package front;

import static javax.swing.WindowConstants.EXIT_ON_CLOSE;

import java.io.Serializable;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class MainBoardFront implements Serializable {
    JPanel mainPanel;
    JFrame mainFrame;

    public MainBoardFront() {
        mainPanel = new JPanel();
        mainFrame = new JFrame("Game");
        mainFrame.setSize(1280, 720);
        mainFrame.setDefaultCloseOperation(EXIT_ON_CLOSE);
        mainFrame.setVisible(true);

        mainFrame.add(mainPanel);

        JLabel coucouLabel = new JLabel("Coucou");
        mainPanel.add(coucouLabel);
    }

}
