package back.cards;

import java.util.ResourceBundle;

import back.ActionType;
import back.Board;
import back.Player;

/**
 * The {@code LuxuryCarKey} class represents the Luxury Car Key Card.
 * 
 * <p>
 * The card Luxury Car Key is useless. It is a single-use card, discarded after
 * utilisation.
 * 
 * <p>
 * The class {@code Luxury Car Key} extends the abstract class {@link Card}.
 * 
 */
public class LuxuryCarKey extends Card {
    private static final long serialVersionUID = -2727390028133337201L;
    public static final int NUMBER_THIS_IN_DECK = 1;

    /**
     * Generates a new {@code LuxuryCarKey} card.
     * 
     * @param board         the main board, not null
     * @param stringsBundle the strings used by the card such as its name or its
     *                      description, not null
     */
    public LuxuryCarKey(Board board, ResourceBundle stringsBundle) {
        super(board, stringsBundle);
        cardName = stringsBundle.getString("LuxuryCarKey_name");
        cardDescription = stringsBundle.getString("LuxuryCarKey_description");
        cardType = CardType.USELESS;
    }

    @Override
    public void useCard(Player player1, Player player2, Player player3, ActionType action) {
        board.getMainBoardFront().displayMessage(String.format(stringsBundle.getString("UselessCard"), owner, this));
        super.useCard(player1, player2, player3, action);
    }

    @Override
    public int getCardImpactOnOpinion() {
        return IMPACT_LUXURY_CAR_KEY;
    }

}
