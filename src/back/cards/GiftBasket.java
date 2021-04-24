package back.cards;

import java.util.ResourceBundle;

import back.Board;
import back.GamePhase;
import back.Player;

public class GiftBasket extends Card {
    private static final long serialVersionUID = -925523317295840498L;

    public GiftBasket(Board board, ResourceBundle stringsBundle) {
        super(board, stringsBundle);
    }

    public String toString() {
        return stringsBundle.getString("GiftBasket_name");
    }
    // board

    @Override
    public void useCard(Player player1, Player player2, Player player3, String string) {
        super.useCard(player1, player2, player3, string);
        System.out.println("Panier garni !");
        if (board.getWaterRations() < board.getNbPlayersAlive()) {
            int waterToAdd = board.getNbPlayersAlive() - board.getWaterRations();
            board.addWater(waterToAdd);
        }
        if (board.getFoodRations() < board.getNbPlayersAlive()) {
            int foodToAdd = board.getNbPlayersAlive() - board.getFoodRations();
            board.addWater(foodToAdd);
        }
    }

    @Override
    public boolean canBeUsed() {
        return board.getPhase() == GamePhase.GOODS_DISTRIBUTION && (!board.isThereEnoughGoodsForAll(false));// To be
                                                                                                            // ended
    }
}
