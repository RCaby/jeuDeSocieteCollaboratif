package back.cards;

import java.util.ResourceBundle;

import back.ActionType;
import back.Board;
import back.Player;

/**
 * The {@code Club} class represents the Club Card.
 * 
 * <p>
 * The card Club allows its owner to vote twice at each voting session. It is
 * not a single-use card, discarded after the death of its owner.
 * 
 * <p>
 * The class {@code Club} extends the abstract class {@link Card}.
 * 
 */
public class Club extends Card {
    private static final long serialVersionUID = -8286765403755107392L;
    public static final int NUMBER_THIS_IN_DECK = 1;

    /**
     * Generates a new {@code Club} card.
     * 
     * @param board         the main board, not null
     * @param stringsBundle the strings used by the card such as its name or its
     *                      description, not null
     */
    public Club(Board board, ResourceBundle stringsBundle) {
        super(board, stringsBundle);
        isSingleUse = false;
        discardOnDeath = true;
        cardName = stringsBundle.getString("Club_name");
        cardDescription = stringsBundle.getString("Club_description");
        cardImpactOnOpinion = NEGATIVE_IMPACT;
    }

    /**
     * Indicates whether the card can be played. This card can be played only to
     * reveal it to the other player.
     */
    @Override
    public boolean canBeUsed() {
        return !isRevealed;
    }

    @Override
    public void useCard(Player player1, Player player2, Player player3, ActionType action) {
        board.getMainBoardFront().displayMessage(String.format(stringsBundle.getString("RevealsCard"), owner, this));
        board.getMainBoardFront()
                .displayMessage(String.format(stringsBundle.getString("Club_smallDescription"), owner));
        super.useCard(player1, player2, player3, action);
    }

}
