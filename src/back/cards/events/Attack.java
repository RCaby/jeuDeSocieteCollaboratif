package back.cards.events;

import java.util.ResourceBundle;

public class Attack extends AEvent {
    public Attack(ResourceBundle stringBundle) {
        super(stringBundle);
        eventName = stringBundle.getString("attackName");
    }
}
