package back.cards.events;

import java.util.ResourceBundle;

public class CareerChange extends AEvent {
    public CareerChange(ResourceBundle stringBundle) {
        super(stringBundle);
        eventName = stringBundle.getString("careerChangeName");
    }
}
