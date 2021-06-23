package back.cards.items;

import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import javax.swing.ImageIcon;

import back.ActionType;
import back.Board;
import back.Player;
import back.PlayerState;
import java.awt.event.ActionListener;

/**
 * The {@code Antivenom} class represents the Antivenom Card.
 * 
 * <p>
 * The card Antivenom cures a sick player. It is a single-use card, discarded
 * after utilization.
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
        cardType = CardType.HELP;
        revealedCardIcon = new ImageIcon("src/front/images/cards/AntivenomRevealed.png");
    }

    /**
     * Simulates the utilization of the card, inherited from {@link Card}. Needs one
     * player as a target for the cure action.
     * 
     * @param player1 target of the cure action, not null, player has to be sick
     * @param player2 not needed for this card
     * @param player3 not needed for this card
     * @param action  not needed for this card
     */
    @Override
    public void useCard(Player player1, Player player2, Player player3, ActionType action, Card card) {
        if (player1 != null && player1.getState() == PlayerState.SICK_FROM_SNAKE) {

            board.curePlayer(player1);
            player1.addOpinionOn(owner, getCardImpactOnOpinionOnTarget(), board.getDifficulty(),
                    board.getMainBoardFront());
            board.getMainBoardFront()
                    .displayMessage(String.format(stringsBundle.getString("OneTarget"), owner, this, player1));
            board.getMainBoardFront()
                    .displayMessage(String.format(stringsBundle.getString("Antivenom_smallDescription"), player1));

            super.useCard(player1, player2, player3, action, card);
        }
    }

    /**
     * Indicates whether the card can be played. This card can be played only if
     * there is at least another player sick.
     */
    @Override
    public boolean canBeUsed() {
        var usableCard = !board.getSickPlayersList().isEmpty();
        if (usableCard) {
            usableCard = false;
            for (Player sickPlayer : board.getSickPlayersList()) {
                usableCard = usableCard || sickPlayer.getState() == PlayerState.SICK_FROM_SNAKE;
            }

        }
        return usableCard;
    }

    @Override
    public boolean[] getNeededParameters() {
        return new boolean[] { true, false, false, false, false };
    }

    @Override
    public ActionListener getActionListener() {
        return board.getMainBoardFront().new CardPlayerActionListenerOneTarget(this);
    }

    @Override
    public int getCardImpactOnOpinion() {
        return IMPACT_ANTIVENOM;
    }

    @Override
    public int getCardImpactOnOpinionOnTarget() {
        return IMPACT_ANTIVENOM_ON_TARGET;
    }

    @Override
    public List<PlayerState> getRequiredState() {
        List<PlayerState> allowedStates = new ArrayList<>();
        allowedStates.add(PlayerState.SICK_FROM_SNAKE);
        return allowedStates;
    }
}
