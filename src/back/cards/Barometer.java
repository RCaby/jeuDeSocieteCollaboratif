package back.cards;

import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import back.ActionType;
import back.Board;
import back.Player;

public class Barometer extends Card {
    private static final long serialVersionUID = -1846990989485437371L;

    public Barometer(Board board, ResourceBundle stringsBundle) {
        super(board, stringsBundle);
    }

    public String toString() {
        return stringsBundle.getString("Barometer_name");
    }

    @Override
    public void useCard(Player player1, Player player2, Player player3, ActionType action) {
        super.useCard(player1, player2, player3, action);
        List<Integer> toBeDisplayed = new ArrayList<>();
        int n = board.getRound();
        for (int index = n; index < n + 3; index++) {
            int weather = board.getWeather(index);
            toBeDisplayed.add(weather);
        }
        System.out.println(toBeDisplayed);
    }
}
