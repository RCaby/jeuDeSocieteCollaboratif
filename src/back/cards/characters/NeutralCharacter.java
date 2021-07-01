package back.cards.characters;

import java.util.ResourceBundle;

public class NeutralCharacter extends ACharacter {

    protected NeutralCharacter(ResourceBundle stringBundle) {
        super(stringBundle);
        characterName = stringBundle.getString("neutralName");
        characterDescription = stringBundle.getString("neutralDescription");
    }

}
