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
import back.cards.CrystalBall;
import back.cards.FishingRod;
import back.cards.Gourd;

/**
 * The main board of the game.
 * 
 * <p>
 * This board stores data for the game such as the number of rations or the card
 * list. The different steps of the game are implemented here, from food
 * gathering to player sacrifices.
 */
public class Board implements Serializable {
    /**
     * gae
     */
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
    private List<Card> spyglassList;
    private List<Card> flashLightList;
    private List<Integer> barometerList;
    private Player conchOwner;

    /**
     * Builds the game without launching it and without incorporating any
     * non-computer user.
     * 
     * @param nbPlayer the number of players in this game
     */
    public Board(int nbPlayers) {
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
        flashLightList = new ArrayList<>();
        barometerList = new ArrayList<>();
        matchesUsedThisRound = false;
        random = new Random();
        weatherList = data.getWeatherList();

        voluntaryDepartureStarted = false;
        playerList = new ArrayList<>();
        discardDeck = new ArrayList<>();
        nextChief = null;
        twicePlayingPlayer = null;

        for (int index = 0; index < nbPlayers; index++) {
            Player player = new Player(stringsBundle.getString("player_name") + index, stringsBundle);
            playerList.add(player);
        }
        indexOfThisPlayer = random.nextInt(nbPlayers);
    }

    /**
     * Builds the game and incorporates a non-computer user
     * 
     * @param nbPlayers  the number of players in this game
     * @param namePlayer the name of the only non-computer user
     */
    public Board(int nbPlayers, String namePlayer) {
        this(nbPlayers);
        indexOfThisPlayer = random.nextInt(nbPlayers);
        thisPlayer = new Player(namePlayer, stringsBundle);
        playerList.remove(indexOfThisPlayer);
        playerList.add(indexOfThisPlayer, thisPlayer);

        cardsDistribution();

        System.out.println("End of initialisation");

        askPlayersForCards();
        currentPhase = GamePhase.GATHERING_RESSOURCES;
        chief = playerList.get(0);
        play(playerList.get(0));

    }

    // Round management functions ####################################

    /**
     * Distributes the required number of card for each player.
     */
    public void cardsDistribution() {
        int nbCardToGive = playerList.size() >= 9 ? 3 : 4;
        for (Player player : playerList) {
            for (int nbCard = 0; nbCard < nbCardToGive; nbCard++) {
                Card card = deck.remove(0);
                giveCardToPlayer(player, card);
            }
        }
    }

    /**
     * Allows each player to play a card if they would like to.
     */
    public boolean askPlayersForCards() {
        boolean cardUsed = false;
        for (Player player : playerList) {
            if (player.getState() == PlayerState.HEALTHY) {
                cardUsed = cardUsed || player.wouldLikePlayCard();
            }
        }
        return cardUsed;

    }

    /**
     * Allows the next player to do their action for the round, if they are healthy.
     * 
     * @param player the player that would do an action
     */
    public void play(Player player) {
        if (gameOver) {
            endGame();
        } else if (player != null && player.getState() == PlayerState.SICK && player.getRoundSick() == round - 1) {
            player.setState(PlayerState.HEALTHY);
            System.out.println(player + " was sick and could not play, now cured");
            play(nextPlayer());
        } else if (player != null && player.getState() == PlayerState.HEALTHY && !player.equals(thisPlayer)) {
            System.out.println(player + "'s turn !");
            playAsCPU(player);
        } else if (player != null && player.getState() == PlayerState.HEALTHY && player.equals(thisPlayer)) {
            System.out.println(player + "'s turn");
            playAsPlayer(player);
        } else if (player == null) {
            switchToNextRound();
            if (!gameOver) {
                play(playerList.get(0));
            }
        } else if (player.getState() == PlayerState.DEAD) {
            System.out.println(player + " is dead and cannot play !\n");
            play(nextPlayer());
        } else if (player.getState() == PlayerState.SICK) {
            // Case not expected
            System.out.println(player + " is sick and cannot play !\n");
            play(nextPlayer());
        } else {
            System.out.println("Default Case ! Something is fishy ~");
        }
    }

    /**
     * Handles the end of this round.
     * 
     * <p>
     * The steps to end this round and to get to the next one are :
     * <ul>
     * <li>Rations distribution with a potential voting session
     * <li>Potential voluntary departure
     * <li>Next round initialization
     * </ul>
     */
    public void switchToNextRound() {
        if (gameOver || getNbPlayersAlive() == 0) {
            endGame();
        } else {

            currentPhase = GamePhase.GOODS_DISTRIBUTION;

            System.out.println(String.format(
                    "It is time to distribute the rations of the round %d. There are currently %d water rations, %d food rations. There are %d players alive and %d planks in the raft.",
                    round, waterRations, foodRations, getNbPlayersAlive(), nbWoodPlanks));

            roundEnd(false); // Goods distribution not for departure
            System.out.println("Distribution ended !");
            boolean departure = weatherList[round] == -2 || isThereEnoughGoodsForAll(true);
            if (departure) {
                roundEnd(true);
                voluntaryDepartureStarted = true;
            }

            if (getNbPlayersAlive() == 0) {
                System.out.println("All players are dead :(");
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
                System.out.println("\nGoing to the next round !");
                round++;
                deadThisRound.clear();
                cardsPlayedThisRound.clear();
                flashLightList.clear();
                barometerList.clear();
                currentPhase = GamePhase.ROUND_BEGINNING;
                clearImposedDecisions();
                conchOwner = null;
                matchesUsedThisRound = false;
                nextRoundPlayerList();
                chief = playerList.get(0);
                nextChief = null;
                twicePlayingPlayer = null;
                askPlayersForCards();

                currentPhase = GamePhase.GATHERING_RESSOURCES;
                indexOfCurrentPlayer = 0;
            }

        }
    }

    /**
     * Handles the rations distribution, and the potential sacrifices.
     * 
     * @param forDeparture indicates whether the players are trying to leave on the
     *                     raft
     */
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
                    killPlayer(player);
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

    /**
     * Displays the end of the game
     */
    public void endGame() {
        currentPhase = GamePhase.END;
        gameOver = true;
        System.out.println(String.format(
                "End of the game. There were %d rounds, %d players are still alive, the current weather is %d. The raft had %d planks",
                round, getNbPlayersAlive(), getWeather(), getWoodPlank()));
    }

    // Ressources gathering function ##############################################

    /**
     * Asks a non-computer player to do an action (maybe imposed) for this round.
     * 
     * <p>
     * Will be moved in the {@code Player} class in a future release.
     * 
     * @param player the player that will do an action
     */
    public void playAsPlayer(Player player) {
        playAsCPU(player);
    }

    /**
     * Asks a computer player to do an action (maybe imposed) for this round.
     * 
     * <p>
     * Will be moved in the {@code Player} class in a future release.
     * 
     * @param player the player that will do an action
     */
    public void playAsCPU(Player player) {
        ActionType imposedAction = player.getImposedActionThisRound();
        if (imposedAction == ActionType.NONE) {
            imposedAction = ActionType.getRandomActionType();
        }

        switch (imposedAction) {
            case FOOD:
                System.out.println(player + " is getting food !\n");
                player.playAsCPUFood(this);
                break;
            case WATER:
                System.out.println(player + " is getting water !\n");
                player.playAsCPUWater(this);
                break;
            case WOOD:
                System.out.println(player + " is getting wood !\n");
                player.playAsCPUWood(this);
                break;
            case CARD:
                System.out.println(player + " is getting a card !\n");
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

    /**
     * The food seeking action of a player.
     * 
     * @param player the player who seeks food
     * @return the amount of food rations gotten by the player
     */
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

    /**
     * The water seeking action of a player.
     * 
     * @param player the player who seeks water
     * @return the amount of water rations gotten by the player
     */
    public int seekWater(Player player) {
        int waterGot = Math.abs(getWeather());
        Card gourd = player.getCardType(Gourd.class);
        if (gourd != null && gourd.isCardRevealed()) {
            waterGot *= 2;
        }
        return waterGot;
    }

    /**
     * The wood seeking action of a player.
     * 
     * @param nbTries the number of plank fragments seeked by the player
     * @param player  the player who seeks wood
     * @return the number of fragments gotten by the player, may be negative which
     *         means the player is now sick and only got the absolute value of this
     *         negative int in plank fragments.
     */
    public int seekWood(int nbTries, Player player) {
        int wood = 1;

        Card axe = player.getCardType(Axe.class);
        if (axe != null && axe.isCardRevealed()) {
            wood++;
        }

        List<Integer> diceList = new ArrayList<>();
        for (int index = 0; index < 6; index++) {
            diceList.add(index);
        }
        Collections.shuffle(diceList);

        List<Integer> randomSeries = diceList.subList(0, nbTries);
        if (!randomSeries.contains(0)) {
            wood += nbTries;
        } else {
            wood *= -1;
        }

        return wood;
    }

    /**
     * The card seeking action of a player.
     * 
     * @param player the player who seeks a card
     * @return the card got by the player
     */
    public Card seekCard() {
        Card pickedCard;
        try {
            pickedCard = deck.remove(0);
        } catch (IndexOutOfBoundsException e) {
            return null;
        }
        return pickedCard;
    }

    // Round end functions #####################################################
    /**
     * Initializes the voting session.
     * 
     * @param pickablePlayers the list of the players who can be "elected"
     * @param votingPlayers   the list of the players who can vote
     * @param votes           the results of the voting session
     */
    public void beginVotingSession(List<Player> pickablePlayers, List<Player> votingPlayers,
            Map<Player, Integer> votes) {
        for (Player player : playerList) {

            if (player.getState() != PlayerState.DEAD
                    && (!player.equals(conchOwner) || (player.equals(conchOwner) && getNbPlayersAlive() == 1))) {
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

    }

    /**
     * Handles the voting session and selects one player to be sacrificied
     * 
     * @return the player who will be sacrificied
     */
    public Player choosePlayerToDie() {

        Map<Player, Integer> votes = new HashMap<>();
        List<Player> pickablePlayers = new ArrayList<>();
        List<Player> votingPlayers = new ArrayList<>();
        System.out.println("Vote session ------------------");

        beginVotingSession(pickablePlayers, votingPlayers, votes);
        crystalBallVote(votingPlayers);

        for (Player player : votingPlayers) {
            Player designated = player.vote(pickablePlayers);
            int prevValue = votes.get(designated);
            votes.put(designated, prevValue + 1);
        }
        Player resultPlayer = voteResults(votes);
        System.out.println("End of vote session ----------------");
        return resultPlayer;

    }

    /**
     * Determines whether someone as a revealed crystal ball and if it is the case,
     * makes this player vote after the other players.
     * 
     * @param listVotingPlayers the list of players who can vote
     */
    public void crystalBallVote(List<Player> listVotingPlayers) {
        int indexCrystalBall = -1;
        for (int index = 0; index < listVotingPlayers.size(); index++) {
            Card crystalBall = listVotingPlayers.get(index).getCardType(CrystalBall.class);
            if (crystalBall != null && crystalBall.isCardRevealed()) {
                indexCrystalBall = index;
                System.out.println("Oh, a crystalball");
            }
        }
        if (indexCrystalBall != -1) {
            Player player = listVotingPlayers.remove(indexCrystalBall);
            listVotingPlayers.add(player); // Last to vote
        }
    }

    /**
     * Calculates the results of the voting session.
     * 
     * @param votes the map which contains the results of the vote
     * @return the Player designated by the votes
     */
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

    /**
     * Distributes the rations to the players still alive.
     */
    public void goodsDistributionForAlive() {
        foodRations -= getNbPlayersAlive();
        waterRations -= getNbPlayersAlive();
    }

    // Tools functions #####################################################

    /**
     * Gives a card to a player.
     * 
     * @param player the player who will have a new card
     * @param card   the new card
     */
    public void giveCardToPlayer(Player player, Card card) {
        player.addCardToInventory(card);
    }

    /**
     * Tool function to calculate how much food a player got
     * 
     * @param index the number picked by the player
     * @return the amount of food got by the player
     */
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

    /**
     * Determines who is the next player to do some action (such as gathering wood)
     * during this round.
     * 
     * @return the next player to play in this round, or null if the round is over
     */
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

    /**
     * Generates the player list for the next round. This list indicates the order
     * in which player will play.
     */
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
        System.out.println("The player list of the next round : " + playerList);
    }

    /**
     * Gets the next player alive following the order of play or the reverse order
     * of play.
     * 
     * @param indexOfPlayer the player from who the research begins
     * @param after         the direction which should be followed by the function,
     *                      after or before the original player
     * @return the next alive player
     */
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

    /**
     * Gets the number of players alive.
     * 
     * @return the number of players alive
     */
    public int getNbPlayersAlive() {
        int alive = 0;
        for (int index = 0; index < playerList.size(); index++) {
            if (playerList.get(index).getState() != PlayerState.DEAD) {
                alive++;
            }
        }
        return alive;
    }

    /**
     * Indicates whether a player have to be sacrificed in order to feed everyone.
     * 
     * @param departure if a departure if planned, players will need twice as much
     *                  rations
     * @return a boolean which indicates if there are enough rations for everyone
     */
    public boolean isThereEnoughGoodsForAll(boolean departure) {
        int n = getNbPlayersAlive();
        return departure ? foodRations >= nbWoodPlanks && waterRations >= nbWoodPlanks && nbWoodPlanks >= n
                : waterRations >= n && foodRations >= n;
    }

    /**
     * Kills a player and distributes the remaining cards from the player's
     * inventory to the other players.
     * 
     * @param player the player to kill
     */
    public void killPlayer(Player player) {
        player.setState(PlayerState.DEAD);
        distributeCardsFromDeadPlayer(player);
        deadThisRound.add(player);
    }

    /**
     * Redistributes the remaining cards of a dead player (such as a {@code Gun} for
     * instance)
     * 
     * @param player the dead player from who cards are taken
     */
    public void distributeCardsFromDeadPlayer(Player player) {
        player.deathPurgeCards();

        if (getNbPlayersAlive() > 1) {
            int indexOfPlayer = playerList.indexOf(player);
            Player playerBefore = getPlayerAliveAfterBefore(indexOfPlayer, false);
            Player playerAfter = getPlayerAliveAfterBefore(indexOfPlayer, true);

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

    /**
     * Clear the imposed decision from the last round.
     */
    public void clearImposedDecisions() {
        for (Player player : playerList) {
            player.setImposedActionThisRound(ActionType.NONE);
        }
    }

    /**
     * Adds food to the food rations.
     * 
     * @param food the number of new rations
     */
    public void addFood(int food) {
        foodRations += food;
    }

    /**
     * Adds a plank to the raft.
     */
    public void addPlank() {
        nbWoodPlanks++;
    }

    /**
     * Adds water to the water rations.
     * 
     * @param water the number of new rations
     */
    public void addWater(int water) {
        waterRations += water;
    }

    /**
     * Adds plank fragments to the raft.
     * 
     * @param fragment the number of fragments to add
     */
    public void addFragmentPlank(int fragment) {
        nbWoodPlanksFragment += fragment;
        nbWoodPlanks += nbWoodPlanksFragment / 6;
        nbWoodPlanksFragment %= 6;
    }

    /**
     * Removes food from the rations.
     * 
     * @param food the number of rations to remove
     */
    public void removeFood(int food) {
        foodRations -= food;
    }

    // Getters and setters ######################################################

    /**
     * A getter for the attribute {@link Board#weatherList}.
     * 
     * @return the list of the different weather
     */
    public int getWeather() {
        return weatherList[round];
    }

    /**
     * A getter for the attribute {@link Board#weatherList}.
     * 
     * @param index a round index
     * @return the weather of the round requested
     */
    public int getWeather(int index) {
        if (0 <= index && index <= 11) {
            return weatherList[index];
        }
        return -1;
    }

    /**
     * The getter for the attribute {@link Board#deadThisRound}.
     * 
     * @return the list of the players dead this round
     */
    public List<Player> getDeadThisRound() {
        return deadThisRound;
    }

    /**
     * The getter for the attribute {@link Board#matchesUsedThisRound}.
     * 
     * @return a boolean indicating whether matches were used during this round
     */
    public boolean getMatchesUsedThisRound() {
        return matchesUsedThisRound;
    }

    /**
     * The setter for the attribute {@link Board#matchesUsedThisRound}.
     * 
     * @param matches a boolean indicating whether matches were used during this
     *                round
     */
    public void setMatchesUsedThisRound(boolean matches) {
        matchesUsedThisRound = matches;
    }

    /**
     * The getter for the attribute {@link Board#playerList}.
     * 
     * @return the current list of players
     */
    public List<Player> getPlayerList() {
        return playerList;
    }

    /**
     * The getter for the attribute {@link Board#deck}.
     * 
     * @return the deck of cards
     */
    public List<Card> getDeck() {
        return deck;
    }

    /**
     * The getter for the attribute {@link Board#discardDeck}.
     * 
     * @return the deck of the discarded cards
     */
    public List<Card> getDiscardDeck() {
        return discardDeck;
    }

    public void setChief(Player player) {
        chief = player;
    }

    /**
     * The setter for the attribute {@link Board#nextChief}.
     * 
     * @param nextChief the chief for the next round
     */
    public void setNextChief(Player nextChief) {
        this.nextChief = nextChief;
    }

    /**
     * The setter for the attribute {@link Board#twicePlayingPlayer}.
     * 
     * @param twicePlayingPlayer the player who will play twice this round
     */
    public void setTwicePlayingPlayer(Player twicePlayingPlayer) {
        this.twicePlayingPlayer = twicePlayingPlayer;
    }

    /**
     * The setter for the attribute {@link Board#currentPhase}.
     * 
     * @param currentPhase the new value of the game phase
     */
    public void setCurrentPhase(GamePhase currentPhase) {
        this.currentPhase = currentPhase;
    }

    /**
     * The getter for the attribute {@link Board#currentPhase}.
     * 
     * @return the current phase of the game
     */
    public GamePhase getCurrentPhase() {
        return currentPhase;
    }

    /**
     * The getter for the attribute {@link Board#cardsPlayedThisRound}.
     * 
     * @return the list of cards played this round
     */
    public List<Card> getCardsPlayedThisRound() {
        return cardsPlayedThisRound;
    }

    /**
     * The setter for the attribute {@link Board#spyglassList}.
     * 
     * @param spyglassList the list of cards seen by with spyglass
     */
    public void setSpyglassList(List<Card> spyglassList) {
        this.spyglassList = spyglassList;
    }

    /**
     * The getter for the attribute {@link Board#spyglassList}.
     * 
     * @return the list of cards seen with the spyglass
     */
    public List<Card> getSpyglassList() {
        return spyglassList;
    }

    /**
     * The getter for the attribute {@link Board#foodRations}.
     * 
     * @return the number of foods rations
     */
    public int getFoodRations() {
        return foodRations;
    }

    /**
     * The getter for the attribute {@link Board#waterRations}.
     * 
     * @return the number of water rations
     */
    public int getWaterRations() {
        return waterRations;
    }

    /**
     * The getter for the attribute {@link Board#nbWoodPlanksFragment}.
     * 
     * @return the number of plank fragments
     */
    public int getWoodPlankFragments() {
        return nbWoodPlanksFragment;
    }

    /**
     * The getter for the attribute {@link Board#nbWoodPlanks}.
     * 
     * @return the number of planks
     */
    public int getWoodPlank() {
        return nbWoodPlanks;
    }

    /**
     * The getter for the attribute {@link Board#indexOfCurrentPlayer}.
     * 
     * @return the index of the player who is now playing
     */
    public int getIndexCurrentPlayer() {
        return indexOfCurrentPlayer;
    }

    /**
     * The getter for the attribute {@link Board#round}.
     * 
     * @return the index of the current round
     */
    public int getRound() {
        return round;
    }

    /**
     * The setter for the attribute {@link Board#foodRations}.
     * 
     * @param foodRations the new value for the foods rations
     */
    public void setFoodRations(int foodRations) {
        this.foodRations = foodRations;
    }

    /**
     * The setter for the attribute {@link Board#waterRations}.
     * 
     * @param waterRations the number of water rations
     */
    public void setWaterRations(int waterRations) {
        this.waterRations = waterRations;
    }

    /**
     * The getter for the attribute {@link Board#barometerList}.
     * 
     * @return the list of the weathers seen with the barometer card
     */
    public List<Integer> getBarometerList() {
        return barometerList;
    }

    /**
     * The getter for the attribute {@link Board#flashLightList
     * 
     * @return the list of cards seen with the flashlight card
     */
    public List<Card> getFlashLightList() {
        return flashLightList;
    }

    /**
     * The setter for the attribute {@link Board#flashLightList}.
     * 
     * @param flashLightList the list of cards seen with the flashlight card
     */
    public void setFlashLightList(List<Card> flashLightList) {
        this.flashLightList = flashLightList;
    }

    /**
     * The setter for the attribute {@link Board#barometerList}.
     * 
     * @param barometerList the list of cards seen with the barometer card
     */
    public void setBarometerList(List<Integer> barometerList) {
        this.barometerList = barometerList;
    }

    /**
     * The setter for the attribute {@link Board#deck}.
     * 
     * @param deck the deck of cards
     */
    public void setDeck(List<Card> deck) {
        this.deck = deck;
    }

    /**
     * The getter for the attribute {@link Board#conchOwner}.
     * 
     * @return the player who owns a conch
     */
    public Player getConchOwner() {
        return conchOwner;
    }

    /**
     * The setter for the attribute {@link Board#conchOwner}.
     * 
     * @param conchOwner the player who owns a conch
     */
    public void setConchOwner(Player conchOwner) {
        this.conchOwner = conchOwner;
    }

    /**
     * The getter for the attribute {@link Board#twicePlayingPlayer}.
     * 
     * @return the player who will play twice this round
     */
    public Player getTwicePlayingPlayer() {
        return twicePlayingPlayer;
    }

    /**
     * The setter for the attribute {@link Board#round}.
     * 
     * @param round the index of the current round
     */
    public void setRound(int round) {
        this.round = round;
    }

    /**
     * The getter for the attribute {@link Board#nextChief}.
     * 
     * @return the player who will be the next chief
     */
    public Player getNextChief() {
        return nextChief;
    }

    /**
     * The getter for the attribute {@link Board#chief}.
     * 
     * @return the player who is currently chief
     */
    public Player getChief() {
        return chief;
    }

    /**
     * The getter for the attribute {@link Board#stringsBundle}.
     * 
     * @return the bundle of strings
     */
    public ResourceBundle getStringsBundle() {
        return stringsBundle;
    }
}
