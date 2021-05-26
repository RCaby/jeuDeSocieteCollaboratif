package back.personalities;

import java.util.ResourceBundle;

import back.Player;

public class PersonalityCooperative extends BasicPersonality {

    public PersonalityCooperative(ResourceBundle stringBundle, Player player) {
        super(stringBundle, player);
    }

    @Override
    public String sayHello() {
        return "Hello, I'm Cooperative";
    }
}
