package back.cards;

import java.util.ResourceBundle;

import back.ActionType;
import back.Board;
import back.Player;
import back.PlayerState;

import java.awt.event.ActionListener;

/**
 * The {@code RottenFish} class represents the Rotten Fish Card.
 * 
 * <p>
 * The card Rotten Fish adds one food ration but makes one of the player sick,
 * if no {@link Matches} were used this round. It is a single-use card,
 * discarded after utilisation.
 * 
 * <p>
 * The class {@code RottenFish} extends the abstract class {@link Card}.
 * 
 */
public class RottenFish extends Card {
    private static final long serialVersionUID = 4118478202921704382L;
    public static final int NUMBER_THIS_IN_DECK = 1;

    /**
     * Generates a new {@code RottenFish} card.
     * 
     * @param board         the main board, not null
     * @param stringsBundle the strings used by the card such as its name or its
     *                      description, not null
     */
    public RottenFish(Board board, ResourceBundle stringsBundle) {
        super(board, stringsBundle);
        cardName = stringsBundle.getString("RottenFish_name");
        cardDescription = stringsBundle.getString("RottenFish_description");
    }

    /**
     * Simulates the utilisation of the card, herited from {@link Card}. Needs one
     * player as a target for the sickness.
     * 
     * @param player1 target of the sickness, not null, player has to be alive
     * @param player2 not needed for this card
     * @param player3 not needed for this card
     * @param action  not needed for this card
     */
    @Override
    public void useCard(Player player1, Player player2, Player player3, ActionType action) {

        if (player1 != null) {
            board.getMainBoardFront()
                    .displayMessage(String.format(stringsBundle.getString("OneTarget"), owner, this, player1));
            board.getMainBoardFront().displayMessage(
                    String.format(stringsBundle.getString("RottenFish_smallDescription"), owner, player1));
            board.addFood(1);
            if (!board.getMatchesUsedThisRound()) {
                board.sickPlayer(player1, PlayerState.SICK_FROM_FOOD);
            } else {
                board.setMatchesUsedThisRound(false);
            }
            super.useCard(player1, player2, player3, action);
        }
    }

    @Override
    public boolean[] getNeededParameters() {
        return new boolean[] { true, false, false, false };
    }

    @Override
    public ActionListener getActionListener() {
        return board.getMainBoardFront().new CardPlayerActionListenerOneTarget(this);
    }
}
