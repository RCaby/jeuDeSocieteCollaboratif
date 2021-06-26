package back.cards.events;

import java.util.ResourceBundle;

public class ArmedThreat extends AEvent {

    public ArmedThreat(ResourceBundle stringBundle) {
        super(stringBundle);
        eventName = stringBundle.getString("armedThreatName");
        linkedEventEnum = EventEnum.ARMED_THREAT;
    }
}
