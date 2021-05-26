package back.cards;

import java.util.ResourceBundle;

import back.ActionType;
import back.Board;
import back.Player;
import back.PlayerState;

/**
 * The {@code StagnantWater} class represents the Stagnant Water Card.
 * 
 * <p>
 * The card Stagnant Water adds one water ration but makes one of the player
 * sick, if no {@link Matches} were used this round. It is a single-use card,
 * discarded after utilisation.
 * 
 * <p>
 * The class {@code StagnantWater} extends the abstract class {@link Card}.
 * 
 */
public class StagnantWater extends Card {
    private static final long serialVersionUID = 206603853100641733L;
    public static final int NUMBER_THIS_IN_DECK = 1;

    /**
     * Generates a new {@code StagnantWater} card.
     * 
     * @param board         the main board, not null
     * @param stringsBundle the strings used by the card such as its name or its
     *                      description, not null
     */
    public StagnantWater(Board board, ResourceBundle stringsBundle) {
        super(board, stringsBundle);
        cardName = stringsBundle.getString("StagnantWater_name");
        cardDescription = stringsBundle.getString("StagnantWater_description");
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
        if (owner != null) {

            board.getMainBoardFront().displayMessage(String.format(stringsBundle.getString("NoTarget"), owner, this));
            board.getMainBoardFront()
                    .displayMessage(String.format(stringsBundle.getString("StagnantWater_smallDescription"), owner));
            board.addWater(1);
            if (!board.getMatchesUsedThisRound()) {
                board.sickPlayer(owner, PlayerState.SICK_FROM_FOOD);
            } else {
                board.setMatchesUsedThisRound(false);
            }
            super.useCard(player1, player2, player3, action);
        }
    }

}
