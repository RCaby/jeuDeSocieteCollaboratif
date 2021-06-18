package back.cards.expansion;

import java.util.ResourceBundle;

import javax.swing.ImageIcon;

import back.ActionType;
import back.Board;
import back.Player;
import back.cards.Card;
import back.cards.CardType;

public class WhipExpansion extends Card {

    public static final int NUMBER_THIS_IN_DECK = 1;

    public WhipExpansion(Board board, ResourceBundle stringsBundle) {
        super(board, stringsBundle);
        isFromExpansion = true;
        revealedCardIcon = new ImageIcon("src/front/images/cards/expansion/WhipRevealed.png");
        cardName = stringsBundle.getString("Whip_name");
        cardDescription = stringsBundle.getString("Whip_description");
        cardType = CardType.THREAT;
    }

    @Override
    public void useCard(Player player1, Player player2, Player player3, ActionType action, Card card) {
        // TODO
        if (player1 != null && player1.equals(owner)) {
            board.getMainBoardFront().displayMessage(String.format(stringsBundle.getString("NoTarget"), owner, this));
            board.getMainBoardFront()
                    .displayMessage(String.format(stringsBundle.getString("Whip_smallDescription"), owner, player1));
            super.useCard(player1, player2, player3, action, card);
        }

    }

    @Override
    public int getCardImpactOnOpinion() {
        return IMPACT_WHIP;
    }

    @Override
    public boolean canBeUsed() {
        return board.getPlayerList().size() > 1;
    }

}
