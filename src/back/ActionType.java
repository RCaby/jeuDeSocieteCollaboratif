package back;

import java.util.Random;

/**
 * The different actions a {@link Player} can do during a turn.
 */
public enum ActionType {
    FOOD(1), WATER(1), WOOD(1), CARD(-1), NONE(0);

    private int impactOnOpinion;

    ActionType(int impactOnOpinion) {
        this.impactOnOpinion = impactOnOpinion;
    }

    private static final Random RANDOM = new Random();
    private static final ActionType[] L_ACTION_TYPES = new ActionType[] { FOOD, WATER, WOOD, CARD };

    public static final ActionType getRandomActionType(ActionType forbiddenActionType) {
        var pickedAction = getRandomActionType();
        while (pickedAction == forbiddenActionType) {
            pickedAction = getRandomActionType();
        }
        return pickedAction;
    }

    public static final ActionType getRandomActionType() {
        var pickedAction = RANDOM.nextInt(4);
        return L_ACTION_TYPES[pickedAction];
    }

    public static final ActionType[] getLActionTypes() {
        return L_ACTION_TYPES;
    }

    public int getImpactOnOpinion() {
        return impactOnOpinion;
    }

}
