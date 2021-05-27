package back.cards;

import java.util.ResourceBundle;

import back.ActionType;
import back.Board;
import back.Player;

/**
 * The {@code Axe} class represents the Axe Card.
 * 
 * <p>
 * The card Axe allows its owner to get two plank fragments for each wood
 * action. It is not a single-use card, discarded after the death of its owner.
 * 
 * <p>
 * The class {@code Axe} extends the abstract class {@link Card}.
 * 
 */
public class Axe extends Card {
    private static final long serialVersionUID = -20152998682170229L;
    public static final int NUMBER_THIS_IN_DECK = 1;

    /**
     * Generates a new {@code Axe} card.
     * 
     * @param board         the main board, not null
     * @param stringsBundle the strings used by the card such as its name or its
     *                      description, not null
     */
    public Axe(Board board, ResourceBundle stringsBundle) {
        super(board, stringsBundle);
        isSingleUse = false;
        discardOnDeath = true;
        cardName = stringsBundle.getString("Axe_name");
        cardDescription = stringsBundle.getString("Axe_description");
        cardImpactOnOpinion = POSITIVE_IMPACT;
    }

    @Override
    public void useCard(Player player1, Player player2, Player player3, ActionType action) {

        board.getMainBoardFront().displayMessage(String.format(stringsBundle.getString("RevealsCard"), owner, this));
        board.getMainBoardFront().displayMessage(String.format(stringsBundle.getString("Axe_smallDescription"), owner));
        super.useCard(player1, player2, player3, action);
    }

    /**
     * Indicates whether the card can be played. This card can be played only to
     * reveal it to the other player.
     */
    @Override
    public boolean canBeUsed() {
        return !isRevealed;
    }
}
