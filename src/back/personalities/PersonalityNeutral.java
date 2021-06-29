package back.personalities;

import java.util.ResourceBundle;

import back.Player;
import back.cards.items.Card;
import front.MainBoardFront;

/**
 * A neutral personality does not need any interaction with any player, the user
 * will make every choice.
 */
public class PersonalityNeutral extends BasicPersonality {

    /**
     * Builds a neutral personality.
     * 
     * @param stringBundle      the resource bundle used to store strings used by
     *                          the class
     * @param player            the player linked to this personality
     * @param publicPersonality a boolean indicating whether this personality should
     *                          be known by other players
     */
    public PersonalityNeutral(ResourceBundle stringBundle, Player linkedPlayer, boolean publicPersonality) {
        super(stringBundle, linkedPlayer, publicPersonality);
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

    @Override
    public void seeCard(Player picker, Card card, int difficulty, MainBoardFront mainBoardFront) {
        mainBoardFront.displayMessage(String.format(stringsBundle.getString("seePickedCard"), picker, card));
    }

}
