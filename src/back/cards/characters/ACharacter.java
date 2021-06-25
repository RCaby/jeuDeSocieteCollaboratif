package back.cards.characters;

import java.util.ResourceBundle;

public abstract class ACharacter implements ICharacter {

    protected ResourceBundle stringBundle;
    protected String characterName = "";

    protected ACharacter(ResourceBundle stringBundle) {
        this.stringBundle = stringBundle;
    }

    @Override
    public String toString() {
        return characterName;
    }
}
