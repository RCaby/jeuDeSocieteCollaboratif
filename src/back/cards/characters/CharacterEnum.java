package back.cards.characters;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.ResourceBundle;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;

public enum CharacterEnum {
    NATURIST(0, Naturist.class), SNAKE_CHARMER(0, SnakeCharmer.class), SHARP_EYE(0, SharpEye.class),
    STRAPPING(0, Strapping.class), HYPNOTIST(0, Hypnotist.class), SCAVENGER(0, Scavenger.class),
    FOOD_ALL(0, FoodAll.class), SURVIVALIST(0, Survivalist.class), WHOLESALE_FISHERMAN(0, WholesaleFisherman.class),
    DOWSER(0, Dowser.class), TIME_TRAVELLER(0, TimeTraveller.class), MAGICIAN(0, Magician.class),
    UPTIGHT(0, Uptight.class), FARSIGHTED(0, FarSighted.class), SUMO(0, Sumo.class),

    SALESMAN(5, Salesman.class), MODEL(5, Model.class), CAPTAIN(5, Captain.class), KID(5, Kid.class),

    BODYGUARD(6, Bodyguard.class),

    ;

    int numberMinPlayers;
    Class<?> linkedClass;

    CharacterEnum(int nbMinPlayers, Class<?> linkedClass) {
        numberMinPlayers = nbMinPlayers;
        this.linkedClass = linkedClass;
    }

    static CharacterEnum[] lessThanFivePlayers = new CharacterEnum[] { NATURIST, SNAKE_CHARMER, SHARP_EYE, STRAPPING,
            HYPNOTIST, SCAVENGER, FOOD_ALL, SURVIVALIST, WHOLESALE_FISHERMAN, DOWSER, TIME_TRAVELLER, MAGICIAN, UPTIGHT,
            FARSIGHTED, SUMO };
    static CharacterEnum[] fivePlayers = new CharacterEnum[] { NATURIST, SNAKE_CHARMER, SHARP_EYE, STRAPPING, HYPNOTIST,
            SCAVENGER, FOOD_ALL, SURVIVALIST, WHOLESALE_FISHERMAN, DOWSER, TIME_TRAVELLER, MAGICIAN, UPTIGHT,
            FARSIGHTED, SUMO, SALESMAN, MODEL, CAPTAIN, KID };
    static CharacterEnum[] moreThanSixPlayers = new CharacterEnum[] { NATURIST, SNAKE_CHARMER, SHARP_EYE, STRAPPING,
            HYPNOTIST, SCAVENGER, FOOD_ALL, SURVIVALIST, WHOLESALE_FISHERMAN, DOWSER, TIME_TRAVELLER, MAGICIAN, UPTIGHT,
            FARSIGHTED, SUMO, BODYGUARD, SALESMAN, MODEL, CAPTAIN, KID };
    static Random random = new Random();

    public static List<ACharacter> getCharacterEnumList(boolean isExpansionUsed, int nbPlayers,
            ResourceBundle stringBundle) {
        if (!isExpansionUsed) {
            List<ACharacter> charactersListNoExpansion = new ArrayList<>();
            for (var index = 0; index < nbPlayers; index++) {
                charactersListNoExpansion.add(new NeutralCharacter(stringBundle));
            }
            return charactersListNoExpansion;
        }
        CharacterEnum[] availableCharacters;
        if (nbPlayers < 5) {
            availableCharacters = lessThanFivePlayers;
        } else if (nbPlayers == 5) {
            availableCharacters = fivePlayers;
        } else {
            availableCharacters = moreThanSixPlayers;
        }

        List<Integer> pickedIndex = new ArrayList<>();
        while (pickedIndex.size() != nbPlayers) {
            var index = random.nextInt(availableCharacters.length);
            if (!pickedIndex.contains(index)) {
                pickedIndex.add(index);
            }
        }

        List<ACharacter> pickedCharacters = new ArrayList<>();
        for (var index = 0; index < nbPlayers; index++) {
            pickedCharacters.add(availableCharacters[pickedIndex.get(index)].getInstance(stringBundle));
        }

        return pickedCharacters;
    }

    public ACharacter getInstance(ResourceBundle stringBundle) {
        ACharacter newInstanceACharacter = null;
        try {
            Constructor<?> constructor = linkedClass.getConstructor(ResourceBundle.class);
            Object instance = constructor.newInstance(stringBundle);
            newInstanceACharacter = (ACharacter) instance;
        } catch (NoSuchMethodException | SecurityException | InstantiationException | IllegalAccessException
                | IllegalArgumentException | InvocationTargetException e) {
            e.printStackTrace();
        }
        return newInstanceACharacter;
    }
}
