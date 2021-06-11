package back.cards;

import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import javax.swing.ImageIcon;

import back.ActionType;
import back.Board;
import back.Player;

/**
 * The {@code Flashlight} class represents the Flashlight Card.
 * 
 * <p>
 * The card Flashlight allows its owner to see the next three cards in the deck.
 * It is a single-use card, discarded after utilization.
 * 
 * <p>
 * The class {@code Flashlight} extends the abstract class {@link Card}.
 * 
 */
public class Flashlight extends Card {
    private static final long serialVersionUID = 669754759637594734L;
    public static final int NUMBER_THIS_IN_DECK = 1;

    /**
     * Generates a new {@code Flashlight} card.
     * 
     * @param board         the main board, not null
     * @param stringsBundle the strings used by the card such as its name or its
     *                      description, not null
     */
    public Flashlight(Board board, ResourceBundle stringsBundle) {
        super(board, stringsBundle);
        cardName = stringsBundle.getString("Flashlight_name");
        cardDescription = stringsBundle.getString("Flashlight_description");
        cardType = CardType.SPY;
        revealedCardIcon = new ImageIcon("src/front/images/cards/FlashlightRevealed.png");
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
    public void useCard(Player player1, Player player2, Player player3, ActionType action) {

        List<Card> toBeDisplayed = new ArrayList<>();
        if (!board.getDeck().isEmpty()) {
            toBeDisplayed.add(board.getDeck().get(0));
            if (board.getDeck().size() >= 2) {
                toBeDisplayed.add(board.getDeck().get(1));
                if (board.getDeck().size() >= 3) {
                    toBeDisplayed.add(board.getDeck().get(2));
                }
            }
        }
        board.setFlashLightList(toBeDisplayed);
        board.showFlashLightList(owner);
        board.getMainBoardFront().displayMessage(String.format(stringsBundle.getString("NoTarget"), owner, this));
        board.getMainBoardFront()
                .displayMessage(String.format(stringsBundle.getString("Flashlight_smallDescription"), owner));
        super.useCard(player1, player2, player3, action);
    }

    @Override
    public int getCardImpactOnOpinion() {
        return IMPACT_FLASHLIGHT;
    }
}
