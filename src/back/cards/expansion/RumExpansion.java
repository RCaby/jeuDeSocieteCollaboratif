package back.cards.expansion;

import java.util.ResourceBundle;

import javax.swing.ImageIcon;

import back.ActionType;
import back.Board;
import back.Player;
import back.cards.Card;
import back.cards.CardType;

public class RumExpansion extends Card {
    public static final int NUMBER_THIS_IN_DECK = 1;

    public RumExpansion(Board board, ResourceBundle stringsBundle) {
        super(board, stringsBundle);
        isFromExpansion = true;
        revealedCardIcon = new ImageIcon("src/front/images/cards/expansion/RumRevealed.png");
        cardName = stringsBundle.getString("Rum_name");
        cardDescription = stringsBundle.getString("Rum_description");
        cardType = CardType.THREAT;
    }

    @Override
    public void useCard(Player player1, Player player2, Player player3, ActionType action, Card card) {
        board.getMainBoardFront().displayMessage(String.format(stringsBundle.getString("NoTarget"), owner, this));
        board.getMainBoardFront().displayMessage(stringsBundle.getString("Rum_smallDescription"));
        if (owner.equals(board.getThisPlayer())) {
            board.setIndexOfCurrentPlayer(board.getIndexCurrentPlayer() - 1);
        }
        super.useCard(player1, player2, player3, action, card);

        board.getMainBoardFront().getReadyForRum();
    }

    @Override
    public int getCardImpactOnOpinion() {
        return IMPACT_RUM;
    }

    @Override
    public boolean canBeUsed() {
        return board.getNbPlayersAlive() > 1;
    }

}
