package back;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Locale;
import java.util.Random;
import java.util.ResourceBundle;

import back.cards.Card;

public class Board implements Serializable {
    private int foodRations;
    private int waterRations;
    private List<Player> playerList;
    private int round;
    private int nbWoodPlanks;
    private int nbWoodPlanksFragment;
    private List<Card> deck;
    private List<Card> discardDeck;
    private int indexOfThisPlayer;
    private int indexOfCurrentPlayer;
    private int[] weatherList;
    private Data data;
    private Random random;
    private boolean gameOver;
    private GamePhase currentPhase;
    private boolean voluntaryDepartureStarted;
    private boolean matchesUsedThisRound;
    private List<Player> deadThisRound;
    private transient ResourceBundle stringsBundle;

    public Board(int nbPlayers, String namePlayer, Boolean forTest) {
        currentPhase = GamePhase.INITIALISATION;
        Locale locale = new Locale("en", "US");
        stringsBundle = ResourceBundle.getBundle("Strings", locale);
        data = new Data(stringsBundle);
        deck = data.getDeck(this);
        gameOver = false;
        Integer[] rations = data.getInitialRations(nbPlayers);
        foodRations = rations[0];
        waterRations = rations[1];
        indexOfCurrentPlayer = 0;
        nbWoodPlanks = 0;
        nbWoodPlanksFragment = 0;
        deadThisRound = new ArrayList<Player>();
        matchesUsedThisRound = false;
        random = new Random();
        weatherList = data.getWeatherList();
        indexOfThisPlayer = random.nextInt(nbPlayers);
        voluntaryDepartureStarted = false;
        playerList = new ArrayList<>();
        discardDeck = new ArrayList<>();

        for (int index = 0; index < nbPlayers - 1; index++) {
            Player player = new Player(stringsBundle.getString("player_name") + index, stringsBundle);
            playerList.add(player);
        }
        playerList.add(indexOfThisPlayer, new Player(namePlayer, stringsBundle));
        int nbCardToGive = nbPlayers >= 9 ? 3 : 4;
        for (Player player : playerList) {
            for (int nbCard = 0; nbCard < nbCardToGive; nbCard++) {
                Card card = deck.remove(0);
                giveCardToPlayer(player, card);
            }
        }

        System.out.println("End of initialisation");
        if (Boolean.FALSE.equals(forTest)) {
            System.out.println("Ce n'est pas un test !");
            askPlayersForCards();
            currentPhase = GamePhase.GATHERING_RESSOURCES;
            play(playerList.get(0), forTest);
        }

    }

    public boolean askPlayersForCards() {
        boolean cardUsed = false;
        for (Player player : playerList) {
            if (player.getState() != PlayerState.DEAD && player.getState() != PlayerState.SICK) {
                cardUsed = cardUsed || player.wouldLikePlayCard();
            }
        }
        return cardUsed;

    }

    public Player nextPlayer() {
        Player player;
        if (indexOfCurrentPlayer != playerList.size() - 1) {
            indexOfCurrentPlayer++;
            player = playerList.get(indexOfCurrentPlayer);
        } else {
            player = null;
        }
        return player;
    }

    public void play(Player player, boolean forTest) {
        if (gameOver) {
            endGame();
        } else if (player != null && player.getState() == PlayerState.SICK && player.getRoundSick() == round - 1) {
            player.setState(PlayerState.HEALTHY);
            System.out.println(player + " was sick and could not play, now cured");
            play(nextPlayer(), forTest);
        } else if (player != null && player.getState() == PlayerState.HEALTHY
                && player != playerList.get(indexOfThisPlayer)) {
            System.out.println("CPU Play");
            playAsCPU(player, forTest);
        } else if (player != null && player.getState() == PlayerState.HEALTHY
                && player == playerList.get(indexOfThisPlayer)) {
            System.out.println("Player play");
            playAsPlayer(player, forTest);
        } else if (player == null) {
            switchToNextRound(forTest);
            if (!gameOver) {
                play(playerList.get(0), forTest);
            }
        } else if (player.getState() == PlayerState.DEAD) {
            System.out.println("Player is dead !");
            play(nextPlayer(), forTest);
        } else if (player.getState() == PlayerState.SICK) {
            // Case not expected
            System.out.println("Player is sick !");
            play(nextPlayer(), forTest);
        }
    }

    public void playAsPlayer(Player player, boolean forTest) {
        playAsCPU(player, forTest);
    }

    public void playAsCPU(Player player, boolean forTest) {

        int indexAction = random.nextInt(4);
        switch (indexAction) {
        case 0:
            System.out.println(player.toString() + " is getting food !\n");
            player.playAsCPUFood(this);

            break;
        case 1:
            System.out.println(player.toString() + " is getting water !\n");
            player.playAsCPUWater(this);
            break;
        case 2:
            System.out.println(player.toString() + " is getting wood !\n");
            player.playAsCPUWood(this);
            break;
        case 3:
            System.out.println(player.toString() + " is getting a card !\n");
            player.playAsCPUCard(this);
            break;
        default:
            break;
        }
        askPlayersForCards();
        play(nextPlayer(), forTest);
    }

    public void giveCardToPlayer(Player player, Card card) {
        player.addCardToInventory(card);
    }

    public void switchToNextRound(boolean forTest) {
        if (gameOver || getNbPlayersAlive() == 0) {
            endGame();
        } else {

            round++;
            indexOfCurrentPlayer = 0;
            if (indexOfThisPlayer == 0) {
                indexOfThisPlayer = playerList.size() - 1;
            } else {
                indexOfThisPlayer--;
            }
            Player chief = playerList.remove(0);
            playerList.add(chief);

            currentPhase = GamePhase.GOODS_DISTRIBUTION;

            System.out.println("Water " + waterRations + ", Food " + foodRations + ", Wood " + nbWoodPlanks);

            roundEnd(false); // Goods distribution not for departure
            System.out.println("Distribution ended !");
            boolean departure = weatherList[round] == -2 || isThereEnoughGoodsForAll(true);
            if (departure) {
                roundEnd(true);
                voluntaryDepartureStarted = true;
            }

            if (getNbPlayersAlive() == 0) {
                System.out.println("All dead :(");
                gameOver = true;
                endGame();
            } else if (weatherList[round - 1] == -2) {
                System.out.println("Hurricane !");
                gameOver = true;
                endGame();
            } else if (voluntaryDepartureStarted) {
                System.out.println("Volontary Departure !");
                gameOver = true;
                endGame();
            } else if (!forTest) {
                System.out.println("\nGoing to next round ! Bye bye -------------------------");
                deadThisRound.clear();
                currentPhase = GamePhase.ROUND_BEGINNING;
                matchesUsedThisRound = false;
                askPlayersForCards();
                currentPhase = GamePhase.GATHERING_RESSOURCES;
                play(playerList.get(0), forTest);
            }

        }
    }

    public boolean isThereEnoughGoodsForAll(boolean departure) {
        int n = getNbPlayersAlive();
        return departure ? foodRations >= nbWoodPlanks && waterRations >= nbWoodPlanks && nbWoodPlanks >= n
                : waterRations >= n && foodRations >= n;
    }

    public void roundEnd(boolean forDeparture) {
        Player player = null;
        boolean end = isThereEnoughGoodsForAll(forDeparture);
        while (!end && getNbPlayersAlive() > 0) {
            boolean cardUsed = askPlayersForCards();
            if (!cardUsed) {
                if (player == null) {
                    player = choosePlayerToDie();
                }
                boolean cardUsedAgain = askPlayersForCards();
                if (!cardUsedAgain) {
                    player.setState(PlayerState.DEAD);
                    player = null;
                }
            }
            end = isThereEnoughGoodsForAll(forDeparture);
        }
        goodsDistributionForAlive();
    }

    public void goodsDistributionForAlive() {
        foodRations -= getNbPlayersAlive();
        waterRations -= getNbPlayersAlive();
    }

    public Player choosePlayerToDie() {
        int pickedPlayerIndex = random.nextInt(playerList.size());
        while (playerList.get(pickedPlayerIndex).getState() == PlayerState.DEAD) {
            pickedPlayerIndex = random.nextInt(playerList.size());
        }
        Player player = playerList.get(pickedPlayerIndex);
        System.out.println("Oh " + player + " has been doomed by fate !");
        return player;
    }

    public void distributeCardsFromDeadPlayer(Player player) {
        if (getNbPlayersAlive() > 1) {
            int indexOfPlayer = playerList.indexOf(player);
            Player playerBefore = getPlayerAliveAfterBefore(indexOfPlayer, false);
            Player playerAfter = getPlayerAliveAfterBefore(indexOfPlayer, true);

            player.deathPurge();

            int indexMax = player.getCardNumber();
            for (int index = 0; index < indexMax; index++) {
                Card card = player.getCard(0);
                player.removeCard(0);

                if (index % 2 == 0) {
                    giveCardToPlayer(playerAfter, card);
                } else {
                    giveCardToPlayer(playerBefore, card);
                }

            }
        }
    }

    public Player getPlayerAliveAfterBefore(int indexOfPlayer, boolean after) {
        Player player = playerList.get(indexOfPlayer);
        boolean playerIsAlive = false;
        int index = indexOfPlayer;
        while (!playerIsAlive) {
            index = index + (after ? 1 : -1);
            if (index < 0) {
                index = playerList.size() - 1;
            }
            if (index >= playerList.size()) {
                index = 0;
            }
            player = playerList.get(index);
            if (playerList.get(index).getState() != PlayerState.DEAD) {
                playerIsAlive = true;
            }
        }
        return player;
    }

    public int getNbPlayersAlive() {
        int alive = 0;
        for (int index = 0; index < playerList.size(); index++) {
            if (playerList.get(index).getState() != PlayerState.DEAD) {
                alive++;
            }
        }
        return alive;
    }

    public void endGame() {
        currentPhase = GamePhase.END;
        gameOver = true;
        System.out.println("End game, Round : " + round + ", NbAlive : " + getNbPlayersAlive() + ", Weather : "
                + getWeather() + ", Planks : " + nbWoodPlanks);
    }

    public void discardCard(Player player, Card card) {
        player.discardCard(card);
        discardDeck.add(card);
    }

    public int seekFood() {
        int pickedIndex = random.nextInt(6) + 1;
        int foodGot;
        if (pickedIndex == 6) {
            foodGot = 3;
        } else if (pickedIndex == 5 || pickedIndex == 4) {
            foodGot = 2;
        } else {
            foodGot = 1;
        }
        return foodGot;

    }

    public int seekWater() {
        return Math.abs(getWeather());
    }

    public int seekWood(int nbTries) {
        int wood = 0;
        List<Integer> givenList = new ArrayList<>();
        for (int index = 0; index < 6; index++) {
            givenList.add(index);
        }
        Collections.shuffle(givenList);

        List<Integer> randomSeries = givenList.subList(0, nbTries);
        if (!randomSeries.contains(0)) {
            wood += nbTries + 1;
        }
        return wood;
    }

    public Card seekCard() {
        Card pickedCard;
        try {
            pickedCard = deck.remove(0);
        } catch (IndexOutOfBoundsException e) {
            return null;
        }
        return pickedCard;
    }

    public int getWeather() {
        return weatherList[round];
    }

    public int getWeather(int index) {
        if (0 <= index && index <= 11) {
            return weatherList[index];
        }
        return -1;
    }

    public List<Player> getDeadThisRound() {
        return deadThisRound;
    }

    public boolean getMatchesUsedThisRound() {
        return matchesUsedThisRound;
    }

    public void setMatchesUsedThisRound(boolean matches) {
        matchesUsedThisRound = matches;
    }

    public List<Player> getPlayerList() {
        return playerList;
    }

    public List<Card> getDeck() {
        return deck;
    }

    public List<Card> getDiscardDeck() {
        return discardDeck;
    }

    public void addFood(int food) {
        foodRations += food;
    }

    public void addPlank() {
        nbWoodPlanks++;
    }

    public void addWater(int water) {
        waterRations += water;
    }

    public void addFragmentPlank(int fragment) {
        nbWoodPlanksFragment += fragment;
        nbWoodPlanks += nbWoodPlanksFragment / 6;
        nbWoodPlanksFragment %= 6;
    }

    public void removeFood(int food) {
        foodRations -= food;
    }

    public void removeWater(int water) {
        waterRations -= water;
    }

    public int getFoodRations() {
        return foodRations;
    }

    public int getWaterRations() {
        return waterRations;
    }

    public int getWoodPlankFragments() {
        return nbWoodPlanksFragment;
    }

    public int getWoodPlank() {
        return nbWoodPlanks;
    }

    public int getIndexCurrentPlayer() {
        return indexOfCurrentPlayer;
    }

    public int getIndexThisPlayer() {
        return indexOfThisPlayer;
    }

    public int getRound() {
        return round;
    }

    public GamePhase getPhase() {
        return currentPhase;
    }

}
