package back.cards;

import back.Board;

public class AlarmClock extends Card {

    private static final long serialVersionUID = -4056999539348867224L;
    public static final String NAME = "Alarm Clock";

    public AlarmClock(Board board) {
        super(board);
    }

    public String toString() {
        return NAME;
    }

    // board + Player

}
