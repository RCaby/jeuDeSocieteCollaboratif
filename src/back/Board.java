package back;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

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
    private boolean shouldGoodsBeRedistributed;
    private int waterDistributedThisRound;
    private int plankDistributedThisRound;
    private int foodDistributedThisRound;
    private int deadThisRound;

    public Board(int nbPlayers, String namePlayer, Boolean forTest) {
        currentPhase = GamePhase.INITIALISATION;
        data = new Data();
        deck = data.getDeck(this);
        gameOver = false;
        Integer[] rations = data.getInitialRations(nbPlayers);
        foodRations = rations[0];
        waterRations = rations[1];
        indexOfCurrentPlayer = 0;
        nbWoodPlanks = 0;
        nbWoodPlanksFragment = 0;
        random = new Random();
        weatherList = data.getWeatherList();
        indexOfThisPlayer = random.nextInt(nbPlayers);
        shouldGoodsBeRedistributed = false;
        foodDistributedThisRound = 0;
        plankDistributedThisRound = 0;
        waterDistributedThisRound = 0;
        voluntaryDepartureStarted = false;
        playerList = new ArrayList<>();
        discardDeck = new ArrayList<>();

        for (int index = 0; index < nbPlayers - 1; index++) {
            Player player = new Player("Player " + index);
            playerList.add(player);
        }
        playerList.add(indexOfThisPlayer, new Player(namePlayer));
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
            deadThisRound = 0;
            askPlayersForCards();
            currentPhase = GamePhase.GATHERING_RESSOURCES;
            play(playerList.get(0), forTest);
        }

    }

    public void askPlayersForCards() {
        for (Player player : playerList) {
            if (player.getState() != PlayerState.DEAD && player.getState() != PlayerState.SICK) {
                player.wouldLikePlayCard();
            }
        }

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
        System.out.println(player.toString() + " is playing !\n");
        int indexAction = random.nextInt(4);
        switch (indexAction) {
        case 0:
            player.playAsCPUFood(this);
            break;
        case 1:
            player.playAsCPUWater(this);
            break;
        case 2:
            player.playAsCPUWood(this);
            break;
        case 3:
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
            boolean departure = weatherList[round] == -2 || isThereEnoughGoods(true);
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

            foodDistributedThisRound = 0;
            waterDistributedThisRound = 0;
            plankDistributedThisRound = 0;

            System.out.println("Water " + waterRations + ", Food " + foodRations + ", Wood " + nbWoodPlanks);

            while (!shouldGoodsBeRedistributed) {

                shouldGoodsBeRedistributed = true;

                retakeAllGoods();
                foodRations += foodDistributedThisRound;
                waterRations += waterDistributedThisRound;
                nbWoodPlanks += plankDistributedThisRound;

                goodsAttribution(departure); // Determine who will be given food, water etc

                foodDistributedThisRound = 0;
                waterDistributedThisRound = 0;
                plankDistributedThisRound = 0;
                goodsDistribution(departure);

                askPlayersForCards(); // Modification of "shouldGoodsBeRedistributed"
            }

            shouldGoodsBeRedistributed = false;

            deathTime();

            askPlayersForCards();

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
                deadThisRound = 0;
                play(playerList.get(0), forTest);
            }
        }
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

    public void voluntaryDeparture() {
        voluntaryDepartureStarted = true;
    }

    public boolean isThereEnoughGoods(boolean forDeparture) {
        int nbAlive = getNbPlayersAlive();
        boolean departureCase = forDeparture && waterRations >= 2 * nbAlive && foodRations >= 2 * nbAlive
                && nbWoodPlanks >= nbAlive;
        boolean notDepartureCase = !forDeparture && waterRations >= nbAlive && foodRations >= nbAlive;

        return departureCase || notDepartureCase;
    }

    public void deathTime() {
        for (Player player : playerList) {
            if (player.getState() != PlayerState.DEAD && (!player.isNourished() || !player.isWatered())) {
                player.setState(PlayerState.DEAD);
                deadThisRound++;
                distributeCardsFromDeadPlayer(player);
            }
        }
    }

    public void goodsDistribution(boolean forDeparture) {
        int amountNeeded = forDeparture ? 2 : 1;
        for (Player player : playerList) {
            if (player.getState() != PlayerState.DEAD && player.isWatered()) {
                waterRations -= amountNeeded;
                waterDistributedThisRound += amountNeeded;
            }
            if (player.getState() != PlayerState.DEAD && player.isNourished()) {
                foodRations -= amountNeeded;
                foodDistributedThisRound += amountNeeded;
            }
            if (forDeparture && player.getState() != PlayerState.DEAD && player.hasPlank()) {
                nbWoodPlanks--;
                plankDistributedThisRound++;
            }
        }
    }

    public void goodsAttributionEnough(boolean forDeparture) {
        System.out.println("There are enough goods !");
        for (Player player : playerList) {
            if (player.getState() != PlayerState.DEAD) {
                player.setNourished(true);
                player.setWatered(true);
                if (forDeparture) {
                    player.setNourishedForDeparture(true);
                    player.setWateredForDeparture(true);
                    player.setPlankForDeparture(true);
                }
            }
        }
    }

    public void goodsAttributionNotEnough() {
        System.out.println("There are not enough goods ! And no boat");
        int foodToDistribute = foodRations;
        int waterToDistribute = waterRations;
        System.out.println("Someone will die, " + foodToDistribute + ", " + waterToDistribute);
        while (isSomeoneDying(false) && foodToDistribute > 0 && waterToDistribute > 0) {
            int pickedPlayer = random.nextInt(playerList.size());
            Player player = playerList.get(pickedPlayer);

            if (player.getState() != PlayerState.DEAD && !player.isNourished() && !player.isWatered()) {
                player.setNourished(true);
                player.setWatered(true);
                foodToDistribute--;
                waterToDistribute--;
            }
        }
    }

    public void goodsAttributionNotEnoughDeparture() {
        System.out.println("There are not enough goods ! And a boat");
        int foodToDistribute = foodRations;
        int waterToDistribute = waterRations;
        int plankToDistribute = nbWoodPlanks;
        System.out.println("Someone will die, " + foodToDistribute + ", " + waterToDistribute + ", " + nbWoodPlanks);

        while (isSomeoneDying(true) && foodToDistribute > 1 && waterToDistribute > 1 && plankToDistribute > 0) {
            int pickedPlayer = random.nextInt(playerList.size());
            Player player = playerList.get(pickedPlayer);
            boolean readyForDeparture = !player.isNourishedForDeparture() && !player.isWateredForDeparture();
            if (player.getState() != PlayerState.DEAD && !player.isNourished() && !player.isWatered()
                    && readyForDeparture) {
                player.setNourished(true);
                player.setWatered(true);
                player.setNourishedForDeparture(true);
                player.setWateredForDeparture(true);
                player.setPlankForDeparture(true);
                foodToDistribute -= 2;
                waterToDistribute -= 2;
                plankToDistribute--;
            }
        }
    }

    public void goodsAttribution(boolean forDeparture) {
        System.out.println("Goods attribution. Departure planned : " + forDeparture);
        if (isThereEnoughGoods(forDeparture)) {
            goodsAttributionEnough(forDeparture);
        } else if (forDeparture) {
            goodsAttributionNotEnoughDeparture();
        } else {
            goodsAttributionNotEnough();
        }
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

    public void retakeAllGoods() {
        for (Player player : playerList) {
            player.setNourished(false);
            player.setWatered(false);
            player.setNourishedForDeparture(false);
            player.setWateredForDeparture(false);
            player.setPlankForDeparture(false);
        }
    }

    public boolean isSomeoneDying(boolean forDeparture) {
        boolean someoneWillDie = false;

        for (Player player : playerList) {
            someoneWillDie = someoneWillDie || !player.isNourished() || !player.isWatered();
            if (forDeparture) {
                boolean readyForDeparture = !player.isNourishedForDeparture() || !player.isWateredForDeparture()
                        || !player.hasPlank();
                someoneWillDie = someoneWillDie || !readyForDeparture;
            }
        }
        return someoneWillDie;
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
        card.discard();
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

    public int getDeadThisRound() {
        return deadThisRound;
    }

    public void setDeadThisRound(int dead) {
        deadThisRound = dead;
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
        nbWoodPlanks = nbWoodPlanksFragment % 6;
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
