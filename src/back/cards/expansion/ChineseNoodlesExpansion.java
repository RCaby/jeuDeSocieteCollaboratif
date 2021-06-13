package back.cards.expansion;

import java.util.ResourceBundle;

import javax.swing.ImageIcon;

import back.ActionType;
import back.Board;
import back.Player;
import back.cards.Card;
import back.cards.CardType;

public class ChineseNoodlesExpansion extends Card {
    public static final int NUMBER_THIS_IN_DECK = 1;

    public ChineseNoodlesExpansion(Board board, ResourceBundle stringsBundle) {
        super(board, stringsBundle);
        isFromExpansion = true;
        revealedCardIcon = new ImageIcon("src/front/images/cards/expansion/ChineseNoodlesRevealed.png");
        cardName = stringsBundle.getString("ChineseNoodles_name");
        cardDescription = stringsBundle.getString("ChineseNoodles_description");
        cardType = CardType.FOOD;
    }

    @Override
    public void useCard(Player player1, Player player2, Player player3, ActionType action) {
        board.removeWater(2);
        board.addFood(2);
        board.getMainBoardFront().displayMessage(String.format(stringsBundle.getString("NoTarget"), owner, this));
        board.getMainBoardFront()
                .displayMessage(String.format(stringsBundle.getString("ChineseNoodles_smallDescription"), owner));
        super.useCard(player1, player2, player3, action);

    }

    @Override
    public int getCardImpactOnOpinion() {
        return IMPACT_CHINESE_NOODLES;
    }

    @Override
    public boolean canBeUsed() {
        return board.getWaterRations() >= 2;
    }

}
