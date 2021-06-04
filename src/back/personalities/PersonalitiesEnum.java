package back.personalities;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.Random;
import java.util.ResourceBundle;

import back.Player;

/**
 * The different personalities which can be used in the game.
 */
public enum PersonalitiesEnum {
    NEUTRAL_PERSONALITIES(PersonalityNeutral.class, 0., "Neutral"),
    AGGRESSIVE_PERSONALITIES(PersonalityAggressive.class, 0.45, "Aggressive"),
    COOPERATIVE_PERSONALITIES(PersonalityCooperative.class, 0.45, "Cooperative"),
    MAD_PERSONALITIES(PersonalityMad.class, 0.1, "Mad");

    Class<?> linkedClass;
    double probabilityPersonality;
    String personalityTypeName;
    static final Random random = new Random();
    static final PersonalitiesEnum[] personalitiesArray = new PersonalitiesEnum[] { NEUTRAL_PERSONALITIES,
            AGGRESSIVE_PERSONALITIES, COOPERATIVE_PERSONALITIES, MAD_PERSONALITIES };

    /**
     * Builds a personality.
     * 
     * @param linkedClass            the class used to handle the personality after
     *                               its creation
     * @param probabilityPersonality the probability to get this personality from a
     *                               random choice
     * @param name                   the name of this personality
     */
    PersonalitiesEnum(Class<?> linkedClass, double probabilityPersonality, String name) {
        this.linkedClass = linkedClass;

        this.probabilityPersonality = probabilityPersonality;
        this.personalityTypeName = name;
    }

    /**
     * Builds and return an instance of a class which inherits from
     * {@code BasicPersonality}.
     * 
     * @param stringBundle        the resource bundle which contains the strings
     *                            used by the class
     * @param player              the player linked to this personality to be
     * @param isPersonalityPublic a boolean indicating whether the personality
     *                            should be known by the other players
     * @return the new instance
     */
    public BasicPersonality getInstance(ResourceBundle stringBundle, Player player, boolean isPersonalityPublic) {
        BasicPersonality newInstanceBasicPersonality = null;
        try {
            Constructor<?> constructor = linkedClass.getConstructor(ResourceBundle.class, Player.class, boolean.class);
            Object instance = constructor.newInstance(stringBundle, player, isPersonalityPublic);
            newInstanceBasicPersonality = (BasicPersonality) instance;
        } catch (NoSuchMethodException | SecurityException | InstantiationException | IllegalAccessException
                | IllegalArgumentException | InvocationTargetException e) {
            e.printStackTrace();
        }
        return newInstanceBasicPersonality;

    }

    @Override
    public String toString() {
        return personalityTypeName;
    }

    /**
     * Returns the probability to get this type of personality with a random choice.
     * 
     * @return the probability
     */
    private double getProbability() {
        return this.probabilityPersonality;
    }

    /**
     * Returns the array used to store personalities types in a determined order.
     * 
     * @return the array of personalities
     */
    public static PersonalitiesEnum[] getPersonalitiesarray() {
        return personalitiesArray;
    }

    /**
     * Determines, given an array of probabilities whose sum is 1, which index
     * corresponds to the picked, real value.
     * 
     * @param pickedValue the value to be associated to an index
     * @return the index linked to the picked value
     */
    private static int whichIndex(double pickedValue) {
        var probabilityArrays = new double[personalitiesArray.length];
        for (var index = 0; index < personalitiesArray.length; index++) {
            probabilityArrays[index] = personalitiesArray[index].getProbability();
        }
        var previousIndex = 1;
        double previousStep = 0;
        double nextStep = probabilityArrays[1];
        while (nextStep < 1.0 && (pickedValue > nextStep || pickedValue < previousStep)) {
            previousIndex++;
            previousStep = nextStep;
            if (previousIndex < probabilityArrays.length - 1) {
                nextStep += probabilityArrays[previousIndex];
            } else {
                nextStep = 1.0;
            }
        }

        assert previousStep <= pickedValue && nextStep >= pickedValue;
        return previousIndex;

    }

    /**
     * Builds and returns a neutral personality, only used for the user.
     * 
     * @param stringsBundle       the resource bundle used to store strings used by
     *                            the class
     * @param player              the user
     * @param isPersonalityPublic a boolean indicating if the personality should be
     *                            known by other players.
     * @return an instance of neutral personality
     */
    public static BasicPersonality getNeutralPersonality(ResourceBundle stringsBundle, Player player,
            boolean isPersonalityPublic) {
        return personalitiesArray[0].getInstance(stringsBundle, player, isPersonalityPublic);
    }

    /**
     * Builds and returns an aggressive personality, used when a personality is
     * modified.
     * 
     * @param stringsBundle       the resource bundle used to store strings used by
     *                            the class
     * @param player              the user
     * @param isPersonalityPublic a boolean indicating if the personality should be
     *                            known by other players.
     * @return an instance of aggressive personality
     */
    public static BasicPersonality getAggressivePersonality(ResourceBundle stringsBundle, Player player,
            boolean isPersonalityPublic) {
        return personalitiesArray[1].getInstance(stringsBundle, player, isPersonalityPublic);
    }

    /**
     * Builds and returns a cooperative personality, used when a personality is
     * modified.
     * 
     * @param stringsBundle       the resource bundle used to store strings used by
     *                            the class
     * @param player              the user
     * @param isPersonalityPublic a boolean indicating if the personality should be
     *                            known by other players.
     * @return an instance of cooperative personality
     */
    public static BasicPersonality getCooperativePersonality(ResourceBundle stringsBundle, Player player,
            boolean isPersonalityPublic) {
        return personalitiesArray[2].getInstance(stringsBundle, player, isPersonalityPublic);
    }

    /**
     * Builds and returns a mad personality, used when a personality is modified.
     * 
     * @param stringsBundle       the resource bundle used to store strings used by
     *                            the class
     * @param player              the user
     * @param isPersonalityPublic a boolean indicating if the personality should be
     *                            known by other players.
     * @return an instance of mad personality
     */
    public static BasicPersonality getMadPersonality(ResourceBundle stringsBundle, Player player,
            boolean isPersonalityPublic) {
        return personalitiesArray[3].getInstance(stringsBundle, player, isPersonalityPublic);
    }

    /**
     * Gets and returns an instance of a random personality type.
     * 
     * @param stringsBundle       the resource bundle used to store strings used by
     *                            the class
     * @param player              the user
     * @param isPersonalityPublic a boolean indicating if the personality should be
     *                            known by other players.
     * @return the new instance
     */
    public static BasicPersonality getRandomPersonality(ResourceBundle stringsBundle, Player player,
            boolean isPersonalityPublic) {
        var randomDouble = Math.random();
        int index = whichIndex(randomDouble);
        return personalitiesArray[index].getInstance(stringsBundle, player, isPersonalityPublic);
    }
}
