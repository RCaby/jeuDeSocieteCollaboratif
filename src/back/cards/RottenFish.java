package back.cards;

import java.util.ResourceBundle;

import javax.swing.ImageIcon;

import back.ActionType;
import back.Board;
import back.Player;
import back.PlayerState;

/**
 * The {@code RottenFish} class represents the Rotten Fish Card.
 * 
 * <p>
 * The card Rotten Fish adds one food ration but makes one of the player sick,
 * if no {@link Matches} were used this round. It is a single-use card,
 * discarded after utilization.
 * 
 * <p>
 * The class {@code RottenFish} extends the abstract class {@link Card}.
 * 
 */
public class RottenFish extends Card {
    private static final long serialVersionUID = 4118478202921704382L;
    public static final int NUMBER_THIS_IN_DECK = 1;

    /**
     * Generates a new {@code RottenFish} card.
     * 
     * @param board         the main board, not null
     * @param stringsBundle the strings used by the card such as its name or its
     *                      description, not null
     */
    public RottenFish(Board board, ResourceBundle stringsBundle) {
        super(board, stringsBundle);
        cardName = stringsBundle.getString("RottenFish_name");
        cardDescription = stringsBundle.getString("RottenFish_description");
        cardType = CardType.FOOD;
        revealedCardIcon = new ImageIcon("src/front/images/cards/RottenFishRevealed.png");
    }

    /**
     * Simulates the utilization of the card, inherited from {@link Card}. Needs one
     * player as a target for the sickness.
     * 
     * @param player1 target of the sickness, not null, player has to be alive
     * @param player2 not needed for this card
     * @param player3 not needed for this card
     * @param action  not needed for this card
     */
    @Override
    public void useCard(Player player1, Player player2, Player player3, ActionType action) {

        if (owner != null) {
            board.getMainBoardFront().displayMessage(String.format(stringsBundle.getString("NoTarget"), owner, this));
            board.getMainBoardFront()
                    .displayMessage(String.format(stringsBundle.getString("RottenFish_smallDescription"), owner));
            board.addFood(1);
            if (!board.getMatchesUsedThisRound()) {
                board.sickPlayer(owner, PlayerState.SICK_FROM_FOOD);
            } else {
                board.setMatchesUsedThisRound(false);
            }
            super.useCard(player1, player2, player3, action);
        }
    }

    @Override
    public int getCardImpactOnOpinion() {
        return IMPACT_ROTTEN_FISH;
    }

}
