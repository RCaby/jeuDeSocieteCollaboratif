package back.cards;

import java.util.ResourceBundle;

import back.ActionType;
import back.Board;
import back.Player;
import back.PlayerState;

/**
 * The {@code Antivenom} class represents the Antivenom Card.
 * 
 * <p>
 * The card Antivenom cures a sick player. It is a single-use card, discarded
 * after utilisation.
 * 
 * <p>
 * The class {@code Antivenom} extends the abstract class {@link Card}.
 * 
 */
public class Antivenom extends Card {

    private static final long serialVersionUID = 6436526438360639610L;
    public static final int NUMBER_THIS_IN_DECK = 1;

    /**
     * Generates a new {@code Antivenom} card.
     * 
     * @param board         the main board, not null
     * @param stringsBundle the strings used by the card such as its name or its
     *                      description, not null
     */
    public Antivenom(Board board, ResourceBundle stringsBundle) {
        super(board, stringsBundle);
        cardName = stringsBundle.getString("Antivenom_name");
        cardDescription = stringsBundle.getString("Antivenom_description");
    }

    /**
     * Simulates the utilisation of the card, herited from {@link Card}. Needs one
     * player as a target for the cure action.
     * 
     * @param player1 target of the cure action, not null, player has to be sick
     * @param player2 not needed for this card
     * @param player3 not needed for this card
     * @param action  not needed for this card
     */
    @Override
    public void useCard(Player player1, Player player2, Player player3, ActionType action) {
        if (player1 != null && player1.getState() == PlayerState.SICK) {

            player1.setState(PlayerState.HEALTHY);
            board.getMainBoardFront().displayMessage(owner + " uses the card " + this + " on " + player1 + ".");
            board.getMainBoardFront().displayMessage(player1 + " is now cured !");
            super.useCard(player1, player2, player3, action);
        }
    }

    /**
     * Indicates whether the card can be played. This card can be played only if
     * there is at least another player sick.
     */
    @Override
    public boolean canBeUsed() {
        boolean flag = false;
        for (Player player : board.getPlayerList()) {
            flag = flag || player.getState() == PlayerState.SICK;
        }
        return flag;
    }

    @Override
    public boolean[] getNeededParameters() {
        return new boolean[] { true, false, false, false };
    }
}
