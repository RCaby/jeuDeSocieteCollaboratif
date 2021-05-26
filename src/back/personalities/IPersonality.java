package back.personalities;

import java.util.List;

import back.ActionType;
import back.Player;
import back.cards.Card;

public interface IPersonality {
    public String sayHello();

    public Card wouldLikePlayACard();

    public Player choosePlayerToVoteFor(List<Player> pickablePlayers);

    public Player chooseAsChief(List<Player> pickablePlayers);

    public ActionType chooseAction();

    public Player chooseTarget();

    public ActionType chooseActionForPendulum();

    // In a next release : a "chooseTarget" method for each card that needs it such
    // as "chooseTargetForVoodooDoll" for instance.
}
