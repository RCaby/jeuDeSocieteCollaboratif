package back.cards.events;

import java.util.ResourceBundle;

public class PotatoCrop extends AEvent {
    public PotatoCrop(ResourceBundle stringBundle) {
        super(stringBundle);
        eventName = stringBundle.getString("potatoCropName");
    }
}
