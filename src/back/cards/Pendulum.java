package back.cards;

import java.util.ResourceBundle;

import back.ActionType;
import back.Board;
import back.Player;

/**
 * The {@code Pendulum} class represents the Pendulum Card.
 * 
 * <p>
 * The card Pendulum imposes the next action of the target player. It is a
 * single-use card, discarded after utilisation.
 * 
 * <p>
 * The class {@code Pendulum} extends the abstract class {@link Card}.
 * 
 */
public class Pendulum extends Card {
    private static final long serialVersionUID = 9023151678122174623L;
    public static final int NUMBER_THIS_IN_DECK = 1;

    /**
     * Generates a new {@code Pendulum} card.
     * 
     * @param board         the main board, not null
     * @param stringsBundle the strings used by the card such as its name or its
     *                      description, not null
     */
    public Pendulum(Board board, ResourceBundle stringsBundle) {
        super(board, stringsBundle);
        cardName = stringsBundle.getString("Pendulum_name");
        cardDescription = stringsBundle.getString("Pendulum_description");
    }

    /**
     * Simulates the utilisation of the card, herited from {@link Card}. Needs one
     * player as a target for the action imposition.
     * 
     * @param player1 target of the imposition, not null, player has to be alive
     * @param player2 not needed for this card
     * @param player3 not needed for this card
     * @param action  not needed for this card
     */
    @Override
    public void useCard(Player player1, Player player2, Player player3, ActionType action) {
        super.useCard(player1, player2, player3, action);
        if (player1 != null) {
            player1.setImposedActionThisRound(action);
        }
    }

    /**
     * Indicates whether the card can be played. This card can be played only if
     * there is at least another player alive.
     */
    @Override
    public boolean canBeUsed() {
        return board.getNbPlayersAlive() > 1;
    }
}
