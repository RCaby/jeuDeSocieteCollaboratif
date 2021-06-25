package back.cards.characters;

import java.util.ResourceBundle;

public class WholesaleFisherman extends ACharacter {
    public WholesaleFisherman(ResourceBundle stringBundle) {
        super(stringBundle);
        characterName = stringBundle.getString("wholesaleFishermanName");
    }
}
