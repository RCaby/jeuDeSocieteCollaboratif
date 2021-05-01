package back.cards;

import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import back.ActionType;
import back.Board;
import back.Player;

/**
 * The {@code Barometer} class represents the Barometer Card.
 * 
 * <p>
 * The card Barometer allows its owner to see the next two weather values. It is
 * a single-use card, discarded after utilisation.
 * 
 * <p>
 * The class {@code Barometer} extends the abstract class {@link Card}.
 * 
 */
public class Barometer extends Card {
    private static final long serialVersionUID = -1846990989485437371L;
    public static final int NUMBER_THIS_IN_DECK = 1;

    /**
     * Generates a new {@code Barometer} card.
     * 
     * @param board         the main board, not null
     * @param stringsBundle the strings used by the card such as its name or its
     *                      description, not null
     */
    public Barometer(Board board, ResourceBundle stringsBundle) {
        super(board, stringsBundle);
        cardName = stringsBundle.getString("Barometer_name");
        cardDescription = stringsBundle.getString("Barometer_description");
    }

    public String toString() {
        return cardName;
    }

    /**
     * Simulates the utilisation of the card, herited from {@link Card}. Does not
     * need any parameter.
     * 
     * @param player1 not needed for this card
     * @param player2 not needed for this card
     * @param player3 not needed for this card
     * @param action  not needed for this card
     */
    @Override
    public void useCard(Player player1, Player player2, Player player3, ActionType action) {
        super.useCard(player1, player2, player3, action);
        List<Integer> toBeDisplayed = new ArrayList<>();
        int n = board.getRound();
        if (n <= 10) {
            int weather = board.getWeather(11);
            toBeDisplayed.add(weather);
            if (n <= 9) {
                weather = board.getWeather(10);
                toBeDisplayed.add(0, weather);
            }
        }
        board.setBarometerList(toBeDisplayed);
        System.out.println(toBeDisplayed);
    }
}
