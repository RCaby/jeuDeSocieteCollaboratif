package back.cards;

import java.util.ResourceBundle;

import javax.swing.ImageIcon;

import back.ActionType;
import back.Board;
import back.Player;

/**
 * The {@code Conch} class represents the Conch Card.
 * 
 * <p>
 * The card Conch makes its owner the chief and prevents any player from voting
 * against the owner. It is a single-use card, discarded after utilization.
 * 
 * <p>
 * The class {@code Conch} extends the abstract class {@link Card}.
 * 
 */
public class Conch extends Card {
    private static final long serialVersionUID = -2323509442041822515L;
    public static final int NUMBER_THIS_IN_DECK = 1;

    /**
     * Generates a new {@code Conch} card.
     * 
     * @param board         the main board, not null
     * @param stringsBundle the strings used by the card such as its name or its
     *                      description, not null
     */
    public Conch(Board board, ResourceBundle stringsBundle) {
        super(board, stringsBundle);
        cardName = stringsBundle.getString("Conch_name");
        cardDescription = stringsBundle.getString("Conch_description");
        cardType = CardType.PROTECTION;
        revealedCardIcon = new ImageIcon("src/front/images/cards/Conch.png");
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
        if (owner != null) {
            board.setConchOwner(owner);
            board.setChief(owner);
            board.getMainBoardFront().displayMessage(String.format(stringsBundle.getString("NoTarget"), owner, this));
            board.getMainBoardFront()
                    .displayMessage(String.format(stringsBundle.getString("Conch_smallDescription"), owner));
            super.useCard(player1, player2, player3, action);
        }

    }

    @Override
    public int getCardImpactOnOpinion() {
        return IMPACT_CONCH;
    }

}
