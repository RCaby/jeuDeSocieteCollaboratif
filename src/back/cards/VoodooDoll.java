package back.cards;

import java.util.ResourceBundle;

import back.ActionType;
import back.Board;
import back.GamePhase;
import back.Player;
import back.PlayerState;

/**
 * The {@code VoodooDoll} class represents the Voodoo Doll Card.
 * 
 * <p>
 * The card Voodoo Doll resurrects one dead player. It is a single-use card,
 * discarded after utilisation.
 * 
 * <p>
 * The class {@code VoodooDoll} extends the abstract class {@link Card}.
 * 
 */
public class VoodooDoll extends Card {
    private static final long serialVersionUID = 8543007868222050206L;
    public static final int NUMBER_THIS_IN_DECK = 1;

    /**
     * Generates a new {@code VoodooDoll} card.
     * 
     * @param board         the main board, not null
     * @param stringsBundle the strings used by the card such as its name or its
     *                      description, not null
     */
    public VoodooDoll(Board board, ResourceBundle stringsBundle) {
        super(board, stringsBundle);
        cardName = stringsBundle.getString("VoodooDoll_name");
        cardDescription = stringsBundle.getString("VoodooDoll_description");
    }

    public String toString() {
        return cardName;
    }

    /**
     * Simulates the utilisation of the card, herited from {@link Card}. Needs one
     * player as a target for the resurrection.
     * 
     * @param player1 target of the resurrection, not null, player has to be dead
     * @param player2 not needed for this card
     * @param player3 not needed for this card
     * @param action  not needed for this card
     */
    @Override
    public void useCard(Player player1, Player player2, Player player3, ActionType action) {
        if (player1 != null && player1.getState() == PlayerState.DEAD) {
            super.useCard(player1, player2, player3, action);
            player1.setState(PlayerState.HEALTHY);
            board.getDeadThisRound().remove(player1);
        }
    }

    /**
     * Indicates whether the card can be played. This card can be played only at the
     * beginning of a round and if someone is already dead.
     */
    @Override
    public boolean canBeUsed() {
        return board.getPhase() == GamePhase.ROUND_BEGINNING && !board.getDeadThisRound().isEmpty();
    }
}
