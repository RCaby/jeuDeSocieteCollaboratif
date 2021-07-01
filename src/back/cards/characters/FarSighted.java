package back.cards.characters;

import java.util.ResourceBundle;

public class FarSighted extends ACharacter {
    public FarSighted(ResourceBundle stringBundle) {
        super(stringBundle);
        characterName = stringBundle.getString("farSightedName");
        linkedCharacterEnum = CharacterEnum.FARSIGHTED;

    }

    @Override
    public int updateWaterCollect(int waterRation, int weather) {
        if (weather == 0) {
            return 1;
        }
        if (weather == 3) {
            return 2;
        }
        return weather;
    }

}
