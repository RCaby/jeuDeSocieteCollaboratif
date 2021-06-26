package back.cards.events;

import java.util.ResourceBundle;

public class Mould extends AEvent {
    public Mould(ResourceBundle stringBundle) {
        super(stringBundle);
        eventName = stringBundle.getString("mouldName");
        linkedEventEnum = EventEnum.MOULD;
    }
}
