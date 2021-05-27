package back.personalities;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.List;
import java.util.Random;
import java.util.ResourceBundle;

import back.Player;

public enum PersonalitiesEnum {
    AGGRESSIVE_PERSONALITIES(PersonalityAggressive.class, 0.45, "Aggressive"),
    COOPERATIVE_PERSONALITIES(PersonalityCooperative.class, 0.45, "Cooperative"),
    MAD_PERSONALITIES(PersonalityMad.class, 0.1, "Mad");

    Class<?> linkedClass;
    double probabilityPersonality;
    String personalityTypeName;
    static final Random random = new Random();
    static final PersonalitiesEnum[] personalitiesArray = new PersonalitiesEnum[] { AGGRESSIVE_PERSONALITIES,
            COOPERATIVE_PERSONALITIES, MAD_PERSONALITIES };

    PersonalitiesEnum(Class<?> linkedClass, double probabilityPersonality, String name) {
        this.linkedClass = linkedClass;
        this.probabilityPersonality = probabilityPersonality;
        this.personalityTypeName = name;
    }

    public BasicPersonality getInstance(ResourceBundle stringBundle, Player player) {
        BasicPersonality newInstanceBasicPersonality = null;
        try {

            Constructor<?> constructor = linkedClass.getConstructor(ResourceBundle.class, Player.class);
            Object instance = constructor.newInstance(stringBundle, player);
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

    private double getProbability() {
        return this.probabilityPersonality;
    }

    public static PersonalitiesEnum[] getPersonalitiesarray() {
        return personalitiesArray;
    }

    private static int whichIndex(double pickedValue) {
        var probabilityArrays = new double[personalitiesArray.length];
        for (var index = 0; index < personalitiesArray.length; index++) {
            probabilityArrays[index] = personalitiesArray[index].getProbability();
        }
        var previousIndex = 0;
        double previousStep = 0;
        double nextStep = probabilityArrays[0];
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

    public static BasicPersonality getRandomPersonality(ResourceBundle stringBundle, Player player) {
        var randomDouble = Math.random();
        int index = whichIndex(randomDouble);
        return personalitiesArray[index].getInstance(stringBundle, player);
    }
}
