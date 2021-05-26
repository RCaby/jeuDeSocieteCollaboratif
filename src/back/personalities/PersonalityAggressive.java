package back.personalities;

import java.util.ResourceBundle;

import back.Player;

public class PersonalityAggressive extends BasicPersonality {

    public PersonalityAggressive(ResourceBundle stringBundle, Player player) {
        super(stringBundle, player);
    }

    @Override
    public String sayHello() {
        return "Hello, I'm Aggresive";
    }

}
