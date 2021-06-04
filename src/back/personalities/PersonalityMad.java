package back.personalities;

import java.util.ResourceBundle;

import back.Player;

/**
 * A mad personality is unpredictable. Every choice is made randomly.
 */
public class PersonalityMad extends BasicPersonality {

    /**
     * Builds a mad personality.
     * 
     * @param stringBundle      the resource bundle used to store strings used by
     *                          the class
     * @param player            the player linked to this personality
     * @param publicPersonality a boolean indicating whether this personality should
     *                          be known by other players
     */
    public PersonalityMad(ResourceBundle stringBundle, Player player, boolean publicPersonality) {
        super(stringBundle, player, publicPersonality);
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

    @Override
    public boolean updatePersonality() {
        return false;
    }
}
