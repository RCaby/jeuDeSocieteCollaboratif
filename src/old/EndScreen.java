package old;

import java.util.List;

import javax.swing.JPanel;
import javax.swing.JTextPane;

public class EndScreen extends JPanel {
    public static final String END_SCREEN = "END_SCREEN";
    private static final long serialVersionUID = 6672944288378081545L;
    private JTextPane endLabel;

    public EndScreen() {
        super();

        endLabel = new JTextPane();
        endLabel.setText("Game Over !\nAre alive : \n");
        endLabel.setEditable(false);
        add(endLabel);
    }

    public void alivePlayers(List<String> playersAliveName) {
        String string = endLabel.getText();
        StringBuilder builder = new StringBuilder();
        for (String playerName : playersAliveName) {
            builder.append(playerName + "\n");
        }
        endLabel.setText(string + builder.toString());
    }
}
