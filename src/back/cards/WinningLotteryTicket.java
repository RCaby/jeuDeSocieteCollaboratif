package back.cards;

import java.util.ResourceBundle;

import javax.swing.ImageIcon;

import back.ActionType;
import back.Board;
import back.Player;

/**
 * The {@code WinningLotteryTicket} class represents the Winning Lottery Ticket
 * Card.
 * 
 * <p>
 * The Winning Lottery Ticket is useless. It is a single-use card, discarded
 * after utilization.
 * 
 * <p>
 * The class {@code WinningLotteryTicket} extends the abstract class
 * {@link Card}.
 * 
 */
public class WinningLotteryTicket extends Card {
    private static final long serialVersionUID = -3278254230727430534L;
    public static final int NUMBER_THIS_IN_DECK = 1;

    /**
     * Generates a new {@code WinningLotteryTicket} card.
     * 
     * @param board         the main board, not null
     * @param stringsBundle the strings used by the card such as its name or its
     *                      description, not null
     */
    public WinningLotteryTicket(Board board, ResourceBundle stringsBundle) {
        super(board, stringsBundle);
        cardName = stringsBundle.getString("WinningLotteryTicket_name");
        cardDescription = stringsBundle.getString("WinningLotteryTicket_description");
        cardType = CardType.USELESS;
        revealedCardIcon = new ImageIcon("src/front/images/cards/WinningLotteryTicketRevealed.png");
    }

    @Override
    public void useCard(Player player1, Player player2, Player player3, ActionType action, Card card) {
        board.getMainBoardFront().displayMessage(String.format(stringsBundle.getString("UselessCard"), owner));
        super.useCard(player1, player2, player3, action, card);
    }

    @Override
    public int getCardImpactOnOpinion() {
        return IMPACT_WINNING_LOTTERY_TICKET;
    }

}
