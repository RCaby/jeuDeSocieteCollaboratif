package back.cards.events;

import java.util.ResourceBundle;

public class SecretStash extends AEvent {
    public SecretStash(ResourceBundle stringBundle) {
        super(stringBundle);
        eventName = stringBundle.getString("secretStashName");
    }
}
