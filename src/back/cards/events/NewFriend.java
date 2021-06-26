package back.cards.events;

import java.util.ResourceBundle;

public class NewFriend extends AEvent {
    public NewFriend(ResourceBundle stringBundle) {
        super(stringBundle);
        eventName = stringBundle.getString("newFriendName");
        linkedEventEnum = EventEnum.NEW_FRIEND;
    }
}
