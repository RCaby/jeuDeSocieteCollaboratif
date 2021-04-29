package back.cards;

import java.util.ResourceBundle;

import back.ActionType;
import back.Board;
import back.Player;

/**
 * The {@code Sardines} class represents the Sardines Card.
 * 
 * <p>
 * The card Sardines adds three food rations. It is a single-use card, discarded
 * after utilisation.
 * 
 * <p>
 * The class {@code Sardines} extends the abstract class {@link Card}.
 * 
 */
public class Sardines extends Card {
    private static final long serialVersionUID = -1179600934965433180L;
    public static final int NUMBER_THIS_IN_DECK = 1;

    /**
     * Generates a new {@code Sardines} card.
     * 
     * @param board         the main board, not null
     * @param stringsBundle the strings used by the card such as its name or its
     *                      description, not null
     */
    public Sardines(Board board, ResourceBundle stringsBundle) {
        super(board, stringsBundle);
        cardName = stringsBundle.getString("Sardines_name");
        cardDescription = stringsBundle.getString("Sardines_description");
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
        board.addFood(3);
    }
}
