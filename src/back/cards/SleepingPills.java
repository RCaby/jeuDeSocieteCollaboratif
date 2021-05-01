package back.cards;

import java.util.ResourceBundle;

import back.ActionType;
import back.Board;
import back.Player;

/**
 * The {@code SleepingPills} class represents the Sleeping Pills Card.
 * 
 * <p>
 * The card Sleeping Pills allows its owner to rob three random cards to other
 * players. It is a single-use card, discarded after utilisation.
 * 
 * <p>
 * The class {@code SleepingPills} extends the abstract class {@link Card}.
 * 
 */
public class SleepingPills extends Card {
    private static final long serialVersionUID = -722036692167231970L;
    public static final int NUMBER_THIS_IN_DECK = 1;

    /**
     * Generates a new {@code SleepingPills} card.
     * 
     * @param board         the main board, not null
     * @param stringsBundle the strings used by the card such as its name or its
     * 
     */
    public SleepingPills(Board board, ResourceBundle stringsBundle) {
        super(board, stringsBundle);
        cardName = stringsBundle.getString("SleepingPills_name");
        cardDescription = stringsBundle.getString("SleepingPills_description");
    }

    public String toString() {
        return cardName;
    }

    /**
     * Simulates the utilisation of the card, herited from {@link Card}. Needs three
     * players as target for the robbery.
     * 
     * @param player1 target of the robbery, not null, player has to be alive
     * @param player2 target of the robbery, not null, player has to be alive
     * @param player3 target of the robbery, not null, player has to be alive
     * @param action  not needed for this card
     */
    @Override
    public void useCard(Player player1, Player player2, Player player3, ActionType action) {
        if (owner != null && player1 != null && player2 != null && player3 != null) {
            board.giveCardToPlayer(owner, player1.robRandomCard());
            board.giveCardToPlayer(owner, player2.robRandomCard());
            board.giveCardToPlayer(owner, player3.robRandomCard());
            super.useCard(player1, player2, player3, action);
        }
    }
}
