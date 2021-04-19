package back.cards;

import java.io.Serializable;

import back.Board;
import back.Player;

public abstract class Card implements Serializable {

    private static final long serialVersionUID = -3585116799691315922L;
    boolean isRevealed;
    Player owner;
    String cardName;
    protected boolean isSingleUse;
    protected boolean discardOnDeath;
    protected Board board;

    protected Card(Board board) {
        isRevealed = false;
        owner = null;
        isSingleUse = true;
        discardOnDeath = false;
        this.board = board;
    }

    public void useCard(Player player1, Player player2, Player player3, String string) {
        if (isSingleUse) {
            discard();
        }
    }

    public void discard() {
        owner = null;
    }

    public void setOwner(Player player) {
        owner = player;
    }

    public Player getOwner() {
        return owner;
    }

    public boolean isCardRevealed() {
        return isRevealed;
    }

    public void setCardRevealed(boolean cardRevealed) {
        isRevealed = cardRevealed;
        if (cardRevealed && owner != null) {
            owner.revealCard(this);
        }
    }

    public String getCardName() {
        return cardName;
    }

    public boolean canBeUsed() {
        return true;
    }

    public boolean discardOnDeath() {
        return discardOnDeath;
    }
}
