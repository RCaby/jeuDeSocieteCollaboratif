package back;

import java.util.Random;

/**
 * The different actions a {@link Player} can do during a turn.
 */
public enum ActionType {
    FOOD, WATER, WOOD, CARD, NONE;

    private static final Random RANDOM = new Random();
    private static final ActionType[] L_ACTION_TYPES = new ActionType[] { FOOD, WATER, WOOD, CARD };

    public static final ActionType getRandomActionType() {
        int pickedAction = RANDOM.nextInt(4);
        return L_ACTION_TYPES[pickedAction];
    }

    public static final ActionType[] getLActionTypes() {
        return L_ACTION_TYPES;
    }

}
