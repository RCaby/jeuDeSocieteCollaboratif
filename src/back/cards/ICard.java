package back.cards;

import back.ActionType;
import back.Player;

import java.awt.event.ActionListener;

import javax.swing.Icon;

public interface ICard {

    public void useCard(Player player1, Player player2, Player player3, ActionType action);

    public int getCardImpactOnOpinion();

    public int getCardImpactOnOpinionOnTarget();

    public void setCardRevealed(boolean cardRevealed);

    public void discard();

    public ActionListener getActionListener();

    public boolean canBeUsed();

    public Icon getRevealedCardIcon();

    public Icon getHiddenCardIcon();

    public boolean isFromExpansion();

    public boolean[] getNeededParameters();

    public void setOwner(Player player);

    public Player getOwner();

    public boolean isCardRevealed();

    public String getCardDescription();

    public CardType getCardType();

    public String getCardName();

    public boolean discardOnDeath();

    public String toString();
}
