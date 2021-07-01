package back.cards.items;

import java.util.ResourceBundle;

import javax.swing.ImageIcon;

import back.ActionType;
import back.Board;
import back.Player;

/**
 * The {@code Spyglass} class represents the Spyglass Card.
 * 
 * <p>
 * The card Spyglass allows one player to see the cards of the other players. It
 * is a single-use card, discarded after utilization.
 * 
 * <p>
 * The class {@code Spyglass} extends the abstract class {@link Card}.
 * 
 */
public class Spyglass extends Card {
    private static final long serialVersionUID = -4985929183848934161L;
    public static final int NUMBER_THIS_IN_DECK = 1;

    /**
     * Generates a new {@code Spyglass} card.
     * 
     * @param board         the main board, not null
     * @param stringsBundle the strings used by the card such as its name or its
     *                      description, not null
     */
    public Spyglass(Board board, ResourceBundle stringsBundle) {
        super(board, stringsBundle);
        cardName = stringsBundle.getString("Spyglass_name");
        cardDescription = stringsBundle.getString("Spyglass_description");
        cardType = CardType.SPY;
        revealedCardIcon = new ImageIcon("src/front/images/cards/SpyglassRevealed.png");
    }

    /**
     * Simulates the utilization of the card, inherited from {@link Card}. Does not
     * need any parameter.
     * 
     * @param player1 not needed for this card
     * @param player2 not needed for this card
     * @param player3 not needed for this card
     * @param action  not needed for this card
     */
    @Override
    public void useCard(Player player1, Player player2, Player player3, ActionType action, Card card) {

        for (Player player : board.getPlayerList()) {
            if (!player.equals(owner)) {
                for (Card cardSeen : player.getInventory()) {
                    owner.getPersonality().seeCard(player, cardSeen, board.getDifficulty(), board.getMainBoardFront());
                }
            }
        }

        super.useCard(player1, player2, player3, action, card);
    }

    @Override
    public int getCardImpactOnOpinion() {
        return IMPACT_SPYGLASS;
    }

    @Override
    public int getCardImpactOnOpinionOnSee() {
        return IMPACT_SPYGLASS_SEE;
    }

}
