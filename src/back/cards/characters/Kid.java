package back.cards.characters;

import java.util.ResourceBundle;

public class Kid extends ACharacter {
    public Kid(ResourceBundle stringBundle) {
        super(stringBundle);
        characterName = stringBundle.getString("kidName");
        linkedCharacterEnum = CharacterEnum.KID;
        nbPlacesTakenOnRaft = 0;
        canFleeAlone = false;
    }

    @Override
    public int updateFoodCollect(int foodRation) {
        return Math.min(foodRation, 2);
    }

    @Override
    public int updateWaterCollect(int waterRation, int weather) {
        return Math.min(waterRation, 2);
    }

    @Override
    public int updateWoodCollect(int woodRation) {
        return Math.min(woodRation, 2);
    }
}
