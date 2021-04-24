package back.cards;

import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import back.Board;
import back.Player;

public class Flashlight extends Card {
    private static final long serialVersionUID = 669754759637594734L;

    public Flashlight(Board board, ResourceBundle stringsBundle) {
        super(board, stringsBundle);
    }

    public String toString() {
        return stringsBundle.getString("Flashlight_name");
    }

    @Override
    public void useCard(Player player1, Player player2, Player player3, String string) {
        super.useCard(player1, player2, player3, string);
        List<Card> toBeDisplayed = new ArrayList<>();
        toBeDisplayed.add(board.getDeck().get(0));
        toBeDisplayed.add(board.getDeck().get(1));
        toBeDisplayed.add(board.getDeck().get(2));
        System.out.println(toBeDisplayed);
    }
}
