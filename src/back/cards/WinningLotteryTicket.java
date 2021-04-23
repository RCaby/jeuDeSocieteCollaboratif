package back.cards;

import java.util.ResourceBundle;

import back.Board;

public class WinningLotteryTicket extends Card {
    private static final long serialVersionUID = -3278254230727430534L;

    public WinningLotteryTicket(Board board, ResourceBundle stringsBundle) {
        super(board, stringsBundle);
    }

    public String toString() {
        return stringsBundle.getString("WinningLotteryTicket_name");
    }

}
