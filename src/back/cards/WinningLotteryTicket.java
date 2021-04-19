package back.cards;

import back.Board;

public class WinningLotteryTicket extends Card {
    private static final long serialVersionUID = -3278254230727430534L;
    public static final String NAME = "Winning Lottery Ticket";

    public WinningLotteryTicket(Board board) {
        super(board);
        isSingleUse = false;
        discardOnDeath = true;
    }

    public String toString() {
        return NAME;
    }

}
