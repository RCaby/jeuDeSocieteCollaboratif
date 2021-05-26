package back.personalities;

import java.io.Serializable;
import java.util.List;
import java.util.Random;
import java.util.ResourceBundle;

import back.ActionType;
import back.Board;
import back.Player;
import back.cards.Card;

public abstract class BasicPersonality implements IPersonality, Serializable {

    protected transient ResourceBundle stringsBundle;
    protected Random random;
    protected Player linkedPlayer;
    protected Board board;

    protected BasicPersonality(ResourceBundle stringBundle, Board board, Player linkedPlayer) {
        this.stringsBundle = stringBundle;
        this.linkedPlayer = linkedPlayer;
        this.board = board;
        this.random = new Random();
    }

    @Override
    public Card wouldLikePlayACard() {
        var pickedInt = random.nextInt(2);
        Card pickedCard = null;
        if (pickedInt % 2 == 0 && linkedPlayer.canUseCard()) {
            var pickedIndexCard = random.nextInt(linkedPlayer.getInventory().size());

            while (!linkedPlayer.getInventory().get(pickedIndexCard).canBeUsed()) {
                pickedIndexCard = random.nextInt(linkedPlayer.getInventory().size());
            }
            pickedCard = linkedPlayer.getInventory().get(pickedIndexCard);
        }
        return pickedCard;
    }

    @Override
    public Player choosePlayerToVoteFor(List<Player> pickablePlayers) {
        var pickedIndex = random.nextInt(pickablePlayers.size());
        if (pickablePlayers.size() > 1) {
            while (pickablePlayers.get(pickedIndex).equals(linkedPlayer)) {
                pickedIndex = random.nextInt(pickablePlayers.size());
            }
        }

        return pickablePlayers.get(pickedIndex);
    }

    @Override
    public Player chooseAsChief(List<Player> pickablePlayers) {
        var pickedIndex = random.nextInt(pickablePlayers.size());
        if (pickablePlayers.size() > 1) {
            while (pickablePlayers.get(pickedIndex).equals(linkedPlayer)) {
                pickedIndex = random.nextInt(pickablePlayers.size());
            }
        }

        return pickablePlayers.get(pickedIndex);
    }

    @Override
    public ActionType chooseAction() {
        return ActionType.getRandomActionType();
    }

    @Override
    public Player chooseTarget() {
        return board.getPlayerList().get(random.nextInt(board.getPlayerList().size()));
    }

    @Override
    public ActionType chooseActionForPendulum() {
        // But different
        var pickedInt = random.nextInt(4);
        return ActionType.getLActionTypes()[pickedInt];
    }

    @Override
    public String sayHello() {
        return "Hello, I'm Basic";
    }
}
