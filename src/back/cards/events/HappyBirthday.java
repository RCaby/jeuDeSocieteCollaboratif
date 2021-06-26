package back.cards.events;

import java.util.ResourceBundle;

public class HappyBirthday extends AEvent {
    public HappyBirthday(ResourceBundle stringBundle) {
        super(stringBundle);
        eventName = stringBundle.getString("happyBirthdayName");
        linkedEventEnum = EventEnum.HAPPY_BIRTHDAY;
    }
}
