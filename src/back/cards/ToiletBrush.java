package back.cards;

import java.util.ResourceBundle;

import back.ActionType;
import back.Board;
import back.Player;

/**
 * The {@code ToiletBrush} class represents the Toilet Brush Card.
 * 
 * <p>
 * The card ToiletBrush is useless. It is a single-use card, discarded after
 * utilisation.
 * 
 * <p>
 * The class {@code ToiletBrush} extends the abstract class {@link Card}.
 * 
 */
public class ToiletBrush extends Card {
    private static final long serialVersionUID = -5505039183789857500L;
    public static final int NUMBER_THIS_IN_DECK = 1;

    /**
     * Generates a new {@code ToiletBrush} card.
     * 
     * @param board         the main board, not null
     * @param stringsBundle the strings used by the card such as its name or its
     *                      description, not null
     */
    public ToiletBrush(Board board, ResourceBundle stringsBundle) {
        super(board, stringsBundle);
        cardName = stringsBundle.getString("ToiletBrush_name");
        cardDescription = stringsBundle.getString("ToiletBrush_description");
        cardImpactOnOpinion = NEUTRAL_IMPACT;
    }

    @Override
    public void useCard(Player player1, Player player2, Player player3, ActionType action) {
        board.getMainBoardFront().displayMessage(String.format(stringsBundle.getString("UselessCard"), owner));
        super.useCard(player1, player2, player3, action);
    }

}
