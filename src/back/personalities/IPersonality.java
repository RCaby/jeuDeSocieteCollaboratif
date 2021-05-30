package back.personalities;

import java.util.List;

import back.ActionType;
import back.Player;
import back.cards.Card;

public interface IPersonality {

    public static final int COOPERATIVE_STARTING_BONUS = 10;
    public static final int AGGRESIVE_STARTING_BONUS = -10;
    public static final int MAD_STARTING_BONUS = -50;
    public static final int NEUTRAL_STARTING_BONUS = 0;

    public String sayHello();

    public Card wouldLikePlayACard();

    public Player choosePlayerToVoteFor(List<Player> pickablePlayers);

    public Player chooseAsChief(List<Player> pickablePlayers);

    public ActionType chooseAction(int food, int water, int wood, int weather, int nbAlive);

    public ActionType getLackingResource(int food, int water, int wood, int weather, int nbAlive);

    public Player chooseTarget(Card card, List<Player> playerList);

    public Player[] chooseThreeTargets(List<Player> playerList);

    public ActionType chooseActionForPendulum();

    public PersonalitiesEnum getLinkedPersonality();

    public int getLinkedStartingBonus();

    public boolean isPersonalityPublic();

}
