package back.cards.events;

import java.util.ResourceBundle;

public class CrazyPsychic extends AEvent {
    public CrazyPsychic(ResourceBundle stringBundle) {
        super(stringBundle);
        eventName = stringBundle.getString("crazyPsychicName");
        linkedEventEnum = EventEnum.CRAZY_PSYCHIC;
    }
}
