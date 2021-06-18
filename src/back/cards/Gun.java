package back.cards;

import java.util.ResourceBundle;

import javax.swing.ImageIcon;

import back.ActionType;
import back.Board;
import back.Player;
import back.cards.expansion.ConcaveMetalSheetExpansion;
import back.cards.expansion.ExpandingBulletExpansion;

import java.awt.event.ActionListener;

/**
 * The {@code Gun} class represents the Gun Card.
 * 
 * <p>
 * The card Gun allows to kill one player. To use this card, the owner has to
 * have at least one {@link Cartridge} which will be discarded after one
 * utilization. The target player can survive the shot if they have one
 * {@link MetalSheet}, which is discarded after one use. It is not a single-use
 * card, and it is not discarded after death.
 * 
 * <p>
 * The class {@code Gun} extends the abstract class {@link Card}.
 * 
 */
public class Gun extends Card {
    private static final long serialVersionUID = 8554178826025991327L;
    public static final int NUMBER_THIS_IN_DECK = 3;

    /**
     * Generates a new {@code Gun} card.
     * 
     * @param board         the main board, not null
     * @param stringsBundle the strings used by the card such as its name or its
     *                      description, not null
     */
    public Gun(Board board, ResourceBundle stringsBundle) {
        super(board, stringsBundle);
        isSingleUse = false;
        cardName = stringsBundle.getString("Gun_name");
        cardDescription = stringsBundle.getString("Gun_description");
        cardType = CardType.WEAPON;
        revealedCardIcon = new ImageIcon("src/front/images/cards/GunRevealed.png");
    }

    /**
     * Simulates the utilization of the card, inherited from {@link Card}. Needs one
     * player as a target for the kill attempt.
     * 
     * @param player1 target of the kill attempt, not null, player has to be alive
     * @param player2 not needed for this card
     * @param player3 not needed for this card
     * @param action  not needed for this card
     */
    @Override
    public void useCard(Player player1, Player player2, Player player3, ActionType action, Card card) {
        if (player1 != null && owner != null) {
            var cardCartridge = owner.getCardType(Cartridge.class);
            var cardExpandingCartridge = owner.getCardType(ExpandingBulletExpansion.class);
            if (cardExpandingCartridge != null) {
                player1.addOpinionOn(owner, getCardImpactOnOpinionOnTarget(), board.getDifficulty(),
                        board.getMainBoardFront());
                board.getMainBoardFront()
                        .displayMessage(String.format(stringsBundle.getString("OneTarget"), owner, this, player1));
                board.getMainBoardFront()
                        .displayMessage(String.format(stringsBundle.getString("Gun_smallDescription"), owner, player1));
                cardCartridge.useCard(player1, player2, player3, action, card);
                board.killPlayer(player1);
                super.useCard(player1, player2, player3, action, card);

            } else if (cardCartridge != null) {
                player1.addOpinionOn(owner, getCardImpactOnOpinionOnTarget(), board.getDifficulty(),
                        board.getMainBoardFront());
                board.getMainBoardFront()
                        .displayMessage(String.format(stringsBundle.getString("OneTarget"), owner, this, player1));
                board.getMainBoardFront()
                        .displayMessage(String.format(stringsBundle.getString("Gun_smallDescription"), owner, player1));
                cardCartridge.useCard(player1, player2, player3, action, card);
                var metalSheet = player1.getCardType(MetalSheet.class);
                var concaveMetalSheet = player1.getCardType(ConcaveMetalSheetExpansion.class);
                if (metalSheet != null) {
                    metalSheet.useCard(player1, player2, player3, action, card);
                } else if (concaveMetalSheet != null) {
                    concaveMetalSheet.useCard(player1, player2, player3, action, card);
                    var newTarget = board
                            .getPlayerAliveAfterBefore(board.getPlayerList().indexOf(board.getThisPlayer()), true);
                    var cardMetalSheetBis = newTarget.getCardType(MetalSheet.class);
                    if (cardMetalSheetBis != null) {
                        cardMetalSheetBis.useCard(player1, player2, player3, action, card);
                    } else {
                        board.killPlayer(newTarget);
                    }

                } else {
                    board.killPlayer(player1);
                }
                super.useCard(player1, player2, player3, action, card);
            }
        }
    }

    /**
     * Indicates whether the card can be played. This card can be played only if the
     * owner has a card {@code Cartridge}.
     */
    @Override
    public boolean canBeUsed() {
        return owner != null && owner.getCardType(Cartridge.class) != null;
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
        return IMPACT_GUN;
    }

    @Override
    public int getCardImpactOnOpinionOnTarget() {
        return IMPACT_GUN_ON_TARGET;
    }
}
