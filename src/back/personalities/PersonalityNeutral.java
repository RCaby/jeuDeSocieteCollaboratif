package back.personalities;

import java.util.ResourceBundle;

import back.Player;

public class PersonalityNeutral extends BasicPersonality {

    public PersonalityNeutral(ResourceBundle stringBundle, Player linkedPlayer, boolean publicPersonality) {
        super(stringBundle, linkedPlayer, publicPersonality);
    }

    @Override
    public String sayHello() {
        return "Hello, I'm Neutral";
    }

    @Override
    public PersonalitiesEnum getLinkedPersonality() {
        return PersonalitiesEnum.NEUTRAL_PERSONALITIES;
    }

    @Override
    public String toString() {
        return "Neutral";
    }

    @Override
    public int getLinkedStartingBonus() {
        return IPersonality.NEUTRAL_STARTING_BONUS;
    }

    @Override
    public boolean updatePersonality() {
        return false;
    }

}
