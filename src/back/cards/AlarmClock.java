package back.cards;

import java.util.ResourceBundle;

import back.Board;

public class AlarmClock extends Card {

    private static final long serialVersionUID = -4056999539348867224L;

    public AlarmClock(Board board, ResourceBundle stringsBundle) {
        super(board, stringsBundle);
    }

    public String toString() {
        return stringsBundle.getString("AlarmClock_name");
    }

    // board + Player

}
