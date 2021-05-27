package back.personalities;

import java.util.ResourceBundle;

import back.Player;

public class PersonalityMad extends BasicPersonality {

    public PersonalityMad(ResourceBundle stringBundle, Player player) {
        super(stringBundle, player);
    }

    @Override
    public String sayHello() {
        return "Hello, I'm Mad :o";
    }

    @Override
    public PersonalitiesEnum getLinkedPersonality() {
        return PersonalitiesEnum.MAD_PERSONALITIES;
    }

    @Override
    public String toString() {
        return "Mad";
    }
}
