package back.cards.expansion;

import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import javax.swing.ImageIcon;

import back.ActionType;
import back.Board;
import back.Player;
import back.cards.Card;
import back.cards.CardType;
import back.cards.Cartridge;
import back.cards.MetalSheet;

public class MetalDetectorExpansion extends Card {
    public static final int NUMBER_THIS_IN_DECK = 1;

    public MetalDetectorExpansion(Board board, ResourceBundle stringsBundle) {
        super(board, stringsBundle);
        isFromExpansion = true;
        revealedCardIcon = new ImageIcon("src/front/images/cards/expansion/MetalDetectorRevealed.png");
        cardName = stringsBundle.getString("MetalDetector_name");
        cardDescription = stringsBundle.getString("MetalDetector_description");
        cardType = CardType.HELP;
    }

    @Override
    public void useCard(Player player1, Player player2, Player player3, ActionType action) {
        List<Card> cardList = new ArrayList<>();
        if (!board.getDeck().isEmpty()) {
            cardList.add(board.getDeck().remove(0));
            if (board.getDeck().size() > 1) {
                cardList.add(board.getDeck().remove(0));
                if (board.getDeck().size() > 2) {
                    cardList.add(board.getDeck().remove(0));
                    if (board.getDeck().size() > 3) {
                        cardList.add(board.getDeck().remove(0));
                    }
                }
            }
        }
        var usefulCardsCount = 0;
        for (Card card : cardList) {
            if (card instanceof MetalSheet || card instanceof ConcaveMetalSheetExpansion || card instanceof Cartridge
                    || card instanceof ExpandingBulletExpansion) {
                owner.addCardToInventory(card);
                usefulCardsCount++;
            }
        }
        board.getMainBoardFront().displayMessage(String.format(stringsBundle.getString("NoTarget"), owner, this));
        board.getMainBoardFront().displayMessage(
                String.format(stringsBundle.getString("MetalDetector_smallDescription"), owner, usefulCardsCount));
        super.useCard(player1, player2, player3, action);

    }

    @Override
    public int getCardImpactOnOpinion() {
        return IMPACT_METAL_DETECTOR;
    }

    @Override
    public boolean canBeUsed() {
        return board.getWeather() == 0;
    }

}
