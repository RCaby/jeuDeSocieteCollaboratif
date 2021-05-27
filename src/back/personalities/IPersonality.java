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

    public Player chooseTarget(Card card, List<Player> playerList);

    public Player[] chooseThreeTargets(List<Player> playerList);

    public ActionType chooseActionForPendulum();

    public PersonalitiesEnum getLinkedPersonality();

}
