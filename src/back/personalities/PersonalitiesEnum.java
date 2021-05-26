package back.personalities;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.Random;
import java.util.ResourceBundle;

import back.Board;
import back.Player;

public enum PersonalitiesEnum {
    AGGRESSIVE_PERSONALITIES(PersonalityAggressive.class, 0.45),
    COOPERATIVE_PERSONALITIES(PersonalityCooperative.class, 0.45), MAD_PERSONALITIES(PersonalityMad.class, 0.1);

    Class<?> linkedClass;
    double probabilityPersonality;
    static final Random random = new Random();
    static final PersonalitiesEnum[] personalitiesArray = new PersonalitiesEnum[] { AGGRESSIVE_PERSONALITIES,
            COOPERATIVE_PERSONALITIES, MAD_PERSONALITIES };

    PersonalitiesEnum(Class<?> linkedClass, double probabilityPersonality) {
        this.linkedClass = linkedClass;
        this.probabilityPersonality = probabilityPersonality;
    }

    public BasicPersonality getInstance(ResourceBundle stringBundle, Board board, Player player) {
        BasicPersonality newInstanceBasicPersonality = null;
        try {

            Constructor<?> constructor = linkedClass.getConstructor(ResourceBundle.class, Board.class, Player.class);
            Object instance = constructor.newInstance(stringBundle, board, player);
            newInstanceBasicPersonality = (BasicPersonality) instance;
        } catch (NoSuchMethodException | SecurityException | InstantiationException | IllegalAccessException
                | IllegalArgumentException | InvocationTargetException e) {
            e.printStackTrace();
        }
        return newInstanceBasicPersonality;

    }

    private double getProbability() {
        return this.probabilityPersonality;
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
                nextStep += probabilityArrays[previousIndex + 1];
            } else {
                nextStep = 1.0;
            }
        }
        assert previousStep <= pickedValue && nextStep >= pickedValue;
        return previousIndex;

    }

    public static BasicPersonality getRandomPersonality(ResourceBundle stringBundle, Board board, Player player) {
        var randomDouble = Math.random();
        int index = whichIndex(randomDouble);
        return personalitiesArray[index].getInstance(stringBundle, board, player);
    }
}
