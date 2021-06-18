package back.cards;

import java.util.ResourceBundle;

import javax.swing.ImageIcon;

import back.ActionType;
import back.Board;
import back.Player;

/**
 * The {@code BoardGameQuoridor} class represents the Board Game Quoridor Card.
 * 
 * <p>
 * The card BoardGameQuoridor is useless. It is a single-use card, discarded
 * after utilization.
 * 
 * <p>
 * The class {@code BoardGameQuoridor} extends the abstract class {@link Card}.
 * 
 */
public class BoardGameQuoridor extends Card {
    private static final long serialVersionUID = -5704752866503737116L;
    public static final int NUMBER_THIS_IN_DECK = 1;

    /**
     * Generates a new {@code BoardGameQuoridor} card.
     * 
     * @param board         the main board, not null
     * @param stringsBundle the strings used by the card such as its name or its
     *                      description, not null
     */
    public BoardGameQuoridor(Board board, ResourceBundle stringsBundle) {
        super(board, stringsBundle);
        cardName = stringsBundle.getString("BoardGameQuoridor_name");
        cardDescription = stringsBundle.getString("BoardGameQuoridor_description");
        cardType = CardType.USELESS;
        revealedCardIcon = new ImageIcon("src/front/images/cards/BoardGameQuoridorRevealed.png");

    }

    @Override
    public void useCard(Player player1, Player player2, Player player3, ActionType action, Card card) {
        board.getMainBoardFront().displayMessage(String.format(stringsBundle.getString("UselessCard"), owner));
        super.useCard(player1, player2, player3, action, card);
    }

    @Override
    public int getCardImpactOnOpinion() {
        return IMPACT_BOARD_GAME_QUORIDOR;
    }

}
