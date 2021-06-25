package back.cards.events;

import java.util.ResourceBundle;

public class AttackByTheOthers extends AEvent {
    public AttackByTheOthers(ResourceBundle stringBundle) {
        super(stringBundle);
        eventName = stringBundle.getString("attackByTheOthersName");
    }
}
