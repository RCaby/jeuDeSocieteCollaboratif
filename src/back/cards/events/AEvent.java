package back.cards.events;

import java.util.ResourceBundle;

public abstract class AEvent implements IEvent {

    protected ResourceBundle stringBundle;
    protected String eventName = "";
    protected EventEnum linkedEventEnum = null; // TODO

    protected AEvent(ResourceBundle stringBundle) {
        this.stringBundle = stringBundle;
    }

    @Override
    public String toString() {
        return eventName;
    }
}
