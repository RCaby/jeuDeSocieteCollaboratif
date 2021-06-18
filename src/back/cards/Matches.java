package back.cards;

import java.util.ResourceBundle;

import javax.swing.ImageIcon;

import back.ActionType;
import back.Board;
import back.Player;

/**
 * The {@code Matches} class represents the Matches Card.
 * 
 * <p>
 * The card Matches allows the utilization of {@link RottenFish} or
 * {@link StagnantWater} without anyone getting sick. It is a single-use card,
 * discarded after utilization.
 * 
 * <p>
 * The class {@code Matches} extends the abstract class {@link Card}.
 * 
 */
public class Matches extends Card {
    private static final long serialVersionUID = -5512438010000301669L;
    public static final int NUMBER_THIS_IN_DECK = 1;

    /**
     * Generates a new {@code Matches} card.
     * 
     * @param board         the main board, not null
     * @param stringsBundle the strings used by the card such as its name or its
     *                      description, not null
     */
    public Matches(Board board, ResourceBundle stringsBundle) {
        super(board, stringsBundle);
        cardName = stringsBundle.getString("Matches_name");
        cardDescription = stringsBundle.getString("Matches_description");
        cardType = CardType.HELP;
        revealedCardIcon = new ImageIcon("src/front/images/cards/MatchesRevealed.png");
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
        board.getMainBoardFront().displayMessage(String.format(stringsBundle.getString("NoTarget"), owner, this));
        board.getMainBoardFront()
                .displayMessage(String.format(stringsBundle.getString("Matches_smallDescription"), owner));
        board.setMatchesUsedThisRound(true);
        super.useCard(player1, player2, player3, action, card);
    }

    @Override
    public int getCardImpactOnOpinion() {
        return IMPACT_MATCHES;
    }

}
