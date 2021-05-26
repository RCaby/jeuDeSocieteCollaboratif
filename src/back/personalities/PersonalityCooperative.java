package back.personalities;

import java.util.ResourceBundle;

import back.Board;
import back.Player;

public class PersonalityCooperative extends BasicPersonality {

    public PersonalityCooperative(ResourceBundle stringBundle, Board board, Player player) {
        super(stringBundle, board, player);
    }

    @Override
    public String sayHello() {
        return "Hello, I'm Cooperative";
    }
}
