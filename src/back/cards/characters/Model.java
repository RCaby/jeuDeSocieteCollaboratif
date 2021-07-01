package back.cards.characters;

import java.util.ResourceBundle;

public class Model extends ACharacter {
    public Model(ResourceBundle stringBundle) {
        super(stringBundle);
        characterName = stringBundle.getString("modelName");
        linkedCharacterEnum = CharacterEnum.MODEL;
        foodConsumptionPerRound = 0;
        foodConsumptionOnRaft = 0;
    }

    @Override
    public int updateFoodCollect(int foodRation) {
        return Math.max(0, foodRation - 1);
    }

    @Override
    public int updateWaterCollect(int waterRation, int weather) {
        return Math.max(0, waterRation - 1);
    }

}
