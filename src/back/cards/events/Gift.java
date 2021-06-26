package back.cards.events;

import java.util.ResourceBundle;

public class Gift extends AEvent {
    public Gift(ResourceBundle stringBundle) {
        super(stringBundle);
        eventName = stringBundle.getString("giftName");
        linkedEventEnum = EventEnum.GIFT;
    }
}
