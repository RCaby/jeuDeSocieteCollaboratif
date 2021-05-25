package back.personalities;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.Random;

public enum PersonalitiesEnum {
    AGGRESSIVE_PERSONALITIES(PersonalityAggressive.class), COOPERATIVE_PERSONALITIES(PersonalityCooperative.class),
    MAD_PERSONALITIES(PersonalityMad.class);

    Class<?> linkedClass;
    static final Random random = new Random();
    static final PersonalitiesEnum[] personalitiesArray = new PersonalitiesEnum[] { AGGRESSIVE_PERSONALITIES,
            COOPERATIVE_PERSONALITIES, MAD_PERSONALITIES };

    PersonalitiesEnum(Class<?> linkedClass) {
        this.linkedClass = linkedClass;
    }

    public BasicPersonality getInstance() {
        BasicPersonality newInstanceBasicPersonality = null;
        try {

            Constructor<?> constructor = linkedClass.getConstructor();
            Object instance = constructor.newInstance();
            newInstanceBasicPersonality = (BasicPersonality) instance;
        } catch (NoSuchMethodException | SecurityException | InstantiationException | IllegalAccessException
                | IllegalArgumentException | InvocationTargetException e) {
            e.printStackTrace();
        }
        return newInstanceBasicPersonality;

    }

    public static BasicPersonality getRandomPersonality() {
        var pickedInt = PersonalitiesEnum.random.nextInt(personalitiesArray.length);
        return personalitiesArray[pickedInt].getInstance();
    }
}
