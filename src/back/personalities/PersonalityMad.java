package back.personalities;

import java.util.ResourceBundle;

import back.Player;

public class PersonalityMad extends BasicPersonality {

    public PersonalityMad(ResourceBundle stringBundle, Player player, boolean publicPersonality) {
        super(stringBundle, player, publicPersonality);
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

    @Override
    public int getLinkedStartingBonus() {
        return IPersonality.MAD_STARTING_BONUS;
    }
}
