package back.cards.characters;

import java.util.ResourceBundle;

public class FoodAll extends ACharacter {
    public FoodAll(ResourceBundle stringBundle) {
        super(stringBundle);
        characterName = stringBundle.getString("foodAllName");
        linkedCharacterEnum = CharacterEnum.FOOD_ALL;
    }
}
