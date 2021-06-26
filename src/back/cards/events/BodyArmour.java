package back.cards.events;

import java.util.ResourceBundle;

public class BodyArmour extends AEvent {
    public BodyArmour(ResourceBundle stringBundle) {
        super(stringBundle);
        eventName = stringBundle.getString("bodyArmourName");
        linkedEventEnum = EventEnum.BODY_ARMOUR;
    }
}
