package back.cards.events;

import java.util.ResourceBundle;

public class TribalCemetery extends AEvent {
    public TribalCemetery(ResourceBundle stringBundle) {
        super(stringBundle);
        eventName = stringBundle.getString("tribalCemeteryName");
        linkedEventEnum = EventEnum.TRIBAL_CEMETERY;
    }
}
