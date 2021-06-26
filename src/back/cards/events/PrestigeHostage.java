package back.cards.events;

import java.util.ResourceBundle;

public class PrestigeHostage extends AEvent {
    public PrestigeHostage(ResourceBundle stringBundle) {
        super(stringBundle);
        eventName = stringBundle.getString("prestigeHostageName");
        linkedEventEnum = EventEnum.PRESTIGE_HOSTAGE;
    }
}
