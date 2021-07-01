package back.cards.characters;

import java.util.ResourceBundle;

public class WholesaleFisherman extends ACharacter {
    public WholesaleFisherman(ResourceBundle stringBundle) {
        super(stringBundle);
        characterName = stringBundle.getString("wholesaleFishermanName");
        linkedCharacterEnum = CharacterEnum.WHOLESALE_FISHERMAN;
    }

    @Override
    public int updateFoodCollect(int foodRation) {
        if (foodRation == 1) {
            return 0;
        } else {
            return foodRation + 1;
        }
    }
}
