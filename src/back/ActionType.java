package back;

import java.util.Random;

public enum ActionType {
    FOOD, WATER, WOOD, CARD, NONE;

    private static final Random RANDOM = new Random();
    private static final ActionType[] L_ACTION_TYPES = new ActionType[] { FOOD, WATER, WOOD, CARD };

    public static final ActionType getRandomActionType() {
        int pickedAction = RANDOM.nextInt(4);
        return L_ACTION_TYPES[pickedAction];
    }

}
