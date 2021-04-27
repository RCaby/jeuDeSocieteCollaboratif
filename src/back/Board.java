package back;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Random;
import java.util.ResourceBundle;
import java.util.Map.Entry;

import back.cards.Axe;
import back.cards.Card;
import back.cards.Club;
import back.cards.Conch;
import back.cards.CrystalBall;
import back.cards.FishingRod;
import back.cards.Gourd;

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
    private List<Card> cardsPlayedThisRound;
    private Player chief;
    private Player thisPlayer;
    private Player nextChief;
    private Player twicePlayingPlayer;

    public Board(int nbPlayers, String namePlayer) {
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
        deadThisRound = new ArrayList<>();
        cardsPlayedThisRound = new ArrayList<>();
        matchesUsedThisRound = false;
        random = new Random();
        weatherList = data.getWeatherList();
        indexOfThisPlayer = random.nextInt(nbPlayers);
        voluntaryDepartureStarted = false;
        playerList = new ArrayList<>();
        discardDeck = new ArrayList<>();
        nextChief = null;
        twicePlayingPlayer = null;

        for (int index = 0; index < nbPlayers - 1; index++) {
            Player player = new Player(stringsBundle.getString("player_name") + index, stringsBundle);
            playerList.add(player);
        }
        thisPlayer = new Player(namePlayer, stringsBundle);
        playerList.add(indexOfThisPlayer, thisPlayer);
        int nbCardToGive = nbPlayers >= 9 ? 3 : 4;
        for (Player player : playerList) {
            for (int nbCard = 0; nbCard < nbCardToGive; nbCard++) {
                Card card = deck.remove(0);
                giveCardToPlayer(player, card);
            }
        }

        System.out.println("End of initialisation");

        askPlayersForCards();
        currentPhase = GamePhase.GATHERING_RESSOURCES;
        chief = playerList.get(0);
        play(playerList.get(0));

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

    public void play(Player player) {
        if (gameOver) {
            endGame();
        } else if (player != null && player.getState() == PlayerState.SICK && player.getRoundSick() == round - 1) {
            player.setState(PlayerState.HEALTHY);
            System.out.println(player + " was sick and could not play, now cured");
            play(nextPlayer());
        } else if (player != null && player.getState() == PlayerState.HEALTHY && !player.equals(thisPlayer)) {
            System.out.println("CPU Play");
            playAsCPU(player);
        } else if (player != null && player.getState() == PlayerState.HEALTHY && player.equals(thisPlayer)) {
            System.out.println("Player play");
            playAsPlayer(player);
        } else if (player == null) {
            switchToNextRound();
            if (!gameOver) {
                play(playerList.get(0));
            }
        } else if (player.getState() == PlayerState.DEAD) {
            System.out.println("Player is dead !");
            play(nextPlayer());
        } else if (player.getState() == PlayerState.SICK) {
            // Case not expected
            System.out.println("Player is sick !");
            play(nextPlayer());
        } else {
            System.out.println("Default Case ! Something is fishy ~");
        }
    }

    public void clearImposedDecisions() {
        for (Player player : playerList) {
            player.setImposedActionThisRound(ActionType.NONE);
        }
    }

    public void playAsPlayer(Player player) {
        playAsCPU(player);
    }

    public void playAsCPU(Player player) {
        ActionType imposedAction = player.getImposedActionThisRound();
        if (imposedAction == ActionType.NONE) {
            imposedAction = ActionType.getRandomActionType();
        }

        switch (imposedAction) {
        case FOOD:
            System.out.println(player.toString() + " is getting food !\n");
            player.playAsCPUFood(this);

            break;
        case WATER:
            System.out.println(player.toString() + " is getting water !\n");
            player.playAsCPUWater(this);
            break;
        case WOOD:
            System.out.println(player.toString() + " is getting wood !\n");
            player.playAsCPUWood(this);
            break;
        case CARD:
            System.out.println(player.toString() + " is getting a card !\n");
            player.playAsCPUCard(this);
            break;
        default:
            break;
        }
        askPlayersForCards();
        if (twicePlayingPlayer != null && twicePlayingPlayer.equals(player)) {
            twicePlayingPlayer = null;
            System.out.println(player + " will play again !");
            playAsCPU(player);
        } else {
            play(nextPlayer());
        }
    }

    public void giveCardToPlayer(Player player, Card card) {
        player.addCardToInventory(card);
    }

    public void switchToNextRound() {
        if (gameOver || getNbPlayersAlive() == 0) {
            endGame();
        } else {

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
            } else if (weatherList[round] == -2) {
                System.out.println("Hurricane !");
                gameOver = true;
                endGame();
            } else if (voluntaryDepartureStarted) {
                System.out.println("Volontary Departure !");
                gameOver = true;
                endGame();
            } else {
                System.out.println("\nGoing to next round ! Bye bye -------------------------");
                round++; // !!! has been moved
                deadThisRound.clear();
                cardsPlayedThisRound.clear();
                currentPhase = GamePhase.ROUND_BEGINNING;
                clearImposedDecisions();
                matchesUsedThisRound = false;
                nextRoundPlayerList();
                chief = playerList.get(0);
                nextChief = null;
                twicePlayingPlayer = null;
                askPlayersForCards();

                currentPhase = GamePhase.GATHERING_RESSOURCES;
                indexOfCurrentPlayer = 0;
                // play(playerList.get(0));
            }

        }
    }

    public void nextRoundPlayerList() {
        if (getNbPlayersAlive() > 0) {

            Player futureChief = playerList.remove(playerList.size() - 1);
            playerList.add(0, futureChief);
            while (playerList.get(0).getState() == PlayerState.DEAD
                    || (nextChief != null && !playerList.get(0).equals(nextChief))) {
                futureChief = playerList.remove(playerList.size() - 1);
                playerList.add(0, futureChief);
            }

        }
        System.out.println("Next round playerList : " + playerList);
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
                    System.out.println(player + " has been sacrified for the sake of the crew :(");
                    if (player.equals(chief) && getNbPlayersAlive() > 0) {
                        chief = getPlayerAliveAfterBefore(playerList.indexOf(player), false);
                        System.out.println("The new chief is " + chief);

                    }
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

        Map<Player, Integer> votes = new HashMap<>();
        List<Player> pickablePlayers = new ArrayList<>();
        List<Player> votingPlayers = new ArrayList<>();
        System.out.println("Vote session ------------------");
        for (Player player : playerList) {
            Card conch = player.getCardType(Conch.class);
            if (player.getState() != PlayerState.DEAD && (conch == null || !conch.isCardRevealed()
                    || (conch.isCardRevealed() && getNbPlayersAlive() == 1))) {
                pickablePlayers.add(player);
                votes.put(player, 0);
            }
            if (player.getState() == PlayerState.HEALTHY) {
                votingPlayers.add(player);
                Card cardClub = player.getCardType(Club.class);
                if (cardClub != null && cardClub.isCardRevealed()) {
                    votingPlayers.add(player);
                    System.out.println(player + " will be voting twice ! Thanks to his club");
                }
            }
        }

        conchVote(votingPlayers);

        for (Player player : votingPlayers) {
            Player designated = player.vote(pickablePlayers);
            int prevValue = votes.get(designated);
            votes.put(designated, prevValue + 1);
        }
        Player resultPlayer = voteResults(votes);
        System.out.println("End of vote session ----------------");
        return resultPlayer;

    }

    public void conchVote(List<Player> listVotingPlayers) {
        int indexCrystalBall = -1;
        for (int index = 0; index < listVotingPlayers.size(); index++) {
            Card crystalBall = listVotingPlayers.get(index).getCardType(CrystalBall.class);
            if (crystalBall != null && crystalBall.isCardRevealed()) {
                indexCrystalBall = index;
            }
        }
        if (indexCrystalBall != -1) {
            Player player = listVotingPlayers.remove(indexCrystalBall);
            listVotingPlayers.add(player); // Last to vote
        }
    }

    public Player voteResults(Map<Player, Integer> votes) {
        Integer max = 0;
        int nbOfMax = 0;
        List<Player> maxPlayers = new ArrayList<>();
        for (Entry<Player, Integer> entry : votes.entrySet()) {
            Integer value = entry.getValue();
            Player key = entry.getKey();
            if (value > max) {
                max = value;
                maxPlayers.clear();
                maxPlayers.add(key);
                nbOfMax = 1;
            } else if (value.equals(max)) {
                maxPlayers.add(key);
                nbOfMax++;
            }
        }

        if (nbOfMax == 1) {
            return maxPlayers.get(0);
        }

        return chief.decideWhoDie(playerList);
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

    public int seekFood(Player player) {
        int pickedIndex = random.nextInt(6) + 1;

        int foodGot = howMuchFood(pickedIndex);

        Card fishingRod = player.getCardType(FishingRod.class);
        if (fishingRod != null && fishingRod.isCardRevealed()) {
            System.out.println("Fishing Rod is used !");
            int pickedIndex2 = random.nextInt(6) + 1;
            while (pickedIndex2 == pickedIndex) {
                pickedIndex2 = random.nextInt(6) + 1;
            }
            foodGot += howMuchFood(pickedIndex2);
        }

        return foodGot;

    }

    private int howMuchFood(int index) {
        int foodGot;
        if (index == 6) {
            foodGot = 3;
        } else if (index == 5 || index == 4) {
            foodGot = 2;
        } else {
            foodGot = 1;
        }
        return foodGot;
    }

    public int seekWater(Player player) {
        int waterGot = Math.abs(getWeather());
        Card gourd = player.getCardType(Gourd.class);
        if (gourd != null && gourd.isCardRevealed()) {
            waterGot *= 2;
        }
        return waterGot;
    }

    public int seekWood(int nbTries, Player player) {
        int wood = 1;
        List<Integer> diceList = new ArrayList<>();
        for (int index = 0; index < 6; index++) {
            diceList.add(index);
        }
        Collections.shuffle(diceList);

        List<Integer> randomSeries = diceList.subList(0, nbTries);
        if (!randomSeries.contains(0)) {
            wood += nbTries;
        }

        Card axe = player.getCardType(Axe.class);
        if (axe != null && axe.isCardRevealed()) {
            wood++;
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

    public void setChief(Player player) {
        chief = player;
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

    public void setNextChief(Player nextChief) {
        this.nextChief = nextChief;
    }

    public void setTwicePlayingPlayer(Player twicePlayingPlayer) {
        this.twicePlayingPlayer = twicePlayingPlayer;
    }

    public void removeWater(int water) {
        waterRations -= water;
    }

    public List<Card> getCardsPlayedThisRound() {
        return cardsPlayedThisRound;
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
