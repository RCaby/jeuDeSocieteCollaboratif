package src.old;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import src.back.cards.Card;
//import src.front.MainScreenGame;

public class MainBoard implements Serializable {
    /*
     * private static final long serialVersionUID = -7898882236996295239L; int
     * foodRation; int waterRation; int nbPlanks; int nbPlankFragments; private
     * List<Player> playersList; private List<Card> cardDeck; private List<Card>
     * discardDeck; Random random; int round; Data data; int playersAlive; int[]
     * weather; private boolean waterDistributed; private boolean foodDistributed;
     * 
     * public MainBoard(int players) {
     * 
     * random = new Random(); playersList = new ArrayList<>(); data = new Data();
     * 
     * Integer[] rations = data.getInitialRations(players); foodRation = rations[0];
     * waterRation = rations[1];
     * 
     * weather = data.getWeatherList();
     * 
     * nbPlanks = 0; nbPlankFragments = 0;
     * 
     * round = 0;
     * 
     * cardDeck = data.getDeck(); discardDeck = new ArrayList<>();
     * 
     * for (int i = 0; i < players; i++) { playersList.add(new Player()); }
     * playersAlive = players;
     * 
     * int nbCardsPerPlayer = players >= 9 ? 3 : 4; for (int index = 0; index <
     * nbCardsPerPlayer; index++) { for (Player player : playersList) { Card card =
     * cardDeck.remove(0); giveCardToPlayer(player, card);
     * 
     * } } }
     * 
     * public void setPlayersAlive(int alive) { playersAlive = alive; }
     * 
     * public int getPlayersAlive() { return playersAlive; }
     * 
     * public void giveCardToPlayer(Player player, Card card) {
     * card.setOwner(player); player.receiveCard(card); }
     * 
     * public void foodConsumption() { int foodAfterRound = foodRation -
     * playersAlive; System.out.println("Players Alive " + playersAlive); foodRation
     * = foodAfterRound; foodDistributed = true;
     * 
     * }
     * 
     * public void waterConsumption() { int waterAfterRound = waterRation -
     * playersAlive; System.out.println("Players Alive " + playersAlive);
     * waterRation = waterAfterRound; waterDistributed = true;
     * 
     * }
     * 
     * public void seekFood() { int amountFood = random.nextInt(6) + 1; if
     * (amountFood == 6) { amountFood = 3; } else if (amountFood <= 4) { amountFood
     * = 2; } else { amountFood = 1; } foodRation += amountFood;
     * System.out.println("got " + amountFood + " food ration"); }
     * 
     * public void addFood(int food) { foodRation += food; }
     * 
     * public int getFood() { return foodRation; }
     * 
     * public void seekWater() { waterRation += Math.abs(weather[round]);
     * System.out.println("got " + Math.abs(weather[round]) + " water rations !"); }
     * 
     * public int getWater() { return waterRation; }
     * 
     * public void addWater(int water) { waterRation += water; }
     * 
     * public int getNbPlanks() { return nbPlanks; }
     * 
     * public int getNbPlanksFragment() { return nbPlankFragments; }
     * 
     * public void addPlankFragment(int plankFragment) { nbPlankFragments +=
     * plankFragment; }
     * 
     * public int getWeather() { return weather[round]; }
     * 
     * public int getRound() { return round; }
     * 
     * public boolean thereIsEnoughFood() { return foodRation - playersAlive >= 0; }
     * 
     * public boolean thereIsEnoughWater() { return waterRation - playersAlive >= 0;
     * }
     * 
     * public boolean endRound(MainScreenGame mainScreenGame) { waterDistributed =
     * false; foodDistributed = false; if (thereIsEnoughWater() &&
     * thereIsEnoughFood()) { waterConsumption(); foodConsumption(); return true; }
     * else { mainScreenGame.switchToVote(); return false; } }
     * 
     * public boolean nextRound(MainScreenGame mainScreenGame) { if
     * (endRound(mainScreenGame)) { round++; return true; } return false; }
     * 
     * public void seekWood(Player player, int nb) { List<Integer> pickedNumbers =
     * new ArrayList<>(); while (pickedNumbers.size() < nb) { int picked =
     * random.nextInt(6); if (!pickedNumbers.contains(picked)) {
     * pickedNumbers.add(picked); } } if (pickedNumbers.contains(0)) {
     * player.setPlayerSick(round); System.out.println("sick ! :("); } else { for
     * (int index = 0; index < nb + 1; index++) { seekWoodenPlankFragment(); } } }
     * 
     * public void seekWoodenPlankFragment() { nbPlankFragments++; nbPlanks +=
     * nbPlankFragments / 6; nbPlankFragments %= 6; }
     * 
     * public Card getCard(Player player) { Card card; if (cardDeck.isEmpty()) {
     * card = null; } else { card = cardDeck.remove(0); giveCardToPlayer(player,
     * card); System.out.println("got card " + card); } return card; }
     * 
     * public List<Player> getPlayerList() { return playersList; }
     */
}
