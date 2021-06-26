package back.cards.events;

import java.util.ResourceBundle;

public class Charity extends AEvent {
    public Charity(ResourceBundle stringBundle) {
        super(stringBundle);
        eventName = stringBundle.getString("charityName");
        linkedEventEnum = EventEnum.CHARITY;
    }
}
