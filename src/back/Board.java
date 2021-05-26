package back;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.ResourceBundle;
import java.util.Map.Entry;

import back.cards.Card;
import back.cards.Club;
import back.cards.CrystalBall;
import back.cards.WaterBottle;
import back.personalities.BasicPersonality;
import back.personalities.PersonalitiesEnum;
import front.MainBoardFront;

/**
 * The main board of the game.
 * 
 * <p>
 * This board stores data for the game such as the number of rations or the card
 * list. The different steps of the game are implemented here, from food
 * gathering to player sacrifices.
 * 
 * <p>
 * The board uses an instance of the class {@link MainBoardFront} to display
 * information and get user inputs. For instance the message displayed on the
 * main board are emitted here.
 */
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
    private Map<Player, List<Card>> spyglassMap;
    private List<Card> flashLightList;
    private List<Integer> barometerList;
    private Player conchOwner;
    private MainBoardFront mainBoardFront;
    private Player crystalBallClubOwner;
    private Player crystalBallOwner;
    private Map<Player, List<Player>> votes;
    private List<Player> pickablePlayers;
    private List<Player> votingPlayers;
    private Player designated;
    private boolean killValidated;
    private boolean currentlyForDeparture;
    private List<Player> designatedForFoodThisRound;
    private List<Player> designatedForWaterThisRound;
    private ActionType lackingResource;

    /**
     * Builds the game without launching it and without incorporating any
     * non-computer user.
     * 
     * @param nbPlayer the number of players in this game
     */
    public Board(MainBoardFront boardFront, int nbPlayers, ResourceBundle stringsBundle) {
        this.stringsBundle = stringsBundle;
        mainBoardFront = boardFront;
        currentPhase = GamePhase.INITIALISATION;
        data = new Data(stringsBundle);
        deck = data.getDeck(this);
        gameOver = false;
        Integer[] rations = data.getInitialRations(nbPlayers);
        foodRations = rations[0];
        waterRations = rations[1];
        indexOfCurrentPlayer = -2;
        nbWoodPlanks = 0;
        nbWoodPlanksFragment = 0;
        deadThisRound = new ArrayList<>();
        cardsPlayedThisRound = new ArrayList<>();
        designatedForFoodThisRound = new ArrayList<>();
        designatedForWaterThisRound = new ArrayList<>();
        lackingResource = ActionType.NONE;
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

        for (var index = 0; index < nbPlayers; index++) {
            var player = new Player(stringsBundle.getString("player_name") + index, stringsBundle);
            playerList.add(player);
        }
        indexOfThisPlayer = random.nextInt(nbPlayers);

    }

    /**
     * Builds the game and incorporates a non-computer user
     * 
     * @param boardFront the instance of the application interface
     * @param nbPlayers  the number of players in this game
     * @param namePlayer the name of the only non-computer user
     */
    public Board(MainBoardFront boardFront, int nbPlayers, ResourceBundle stringsBundle, String namePlayer) {
        this(boardFront, nbPlayers, stringsBundle);

        indexOfThisPlayer = random.nextInt(nbPlayers);
        thisPlayer = new Player(namePlayer, stringsBundle);
        playerList.remove(indexOfThisPlayer);
        playerList.add(indexOfThisPlayer, thisPlayer);

        associatePersonalities();
        cardsDistribution();

        boardFront.displayMessage("End of initialisation. Good luck !");
        sayHello();
        currentPhase = GamePhase.ROUND_BEGINNING;
        mainBoardFront.setBoard(this);
        updateDisplayResources();
        mainBoardFront.buildPlayersDisplay(indexOfThisPlayer);

        mainBoardFront.updateSouth();
        mainBoardFront.buildCardTargetPanel();

        askPlayersForCards();

        setChief(playerList.get(0));

    }

    // Round management functions ####################################

    /**
     * Distributes the required number of card for each player.
     */
    private void cardsDistribution() {
        int nbCardToGive = playerList.size() >= 9 ? 3 : 4;
        for (Player player : playerList) {
            for (var nbCard = 0; nbCard < nbCardToGive; nbCard++) {
                var card = deck.remove(0);
                giveCardToPlayer(player, card);
            }
        }
    }

    private void associatePersonalities() {
        for (Player player : playerList) {
            if (!player.equals(thisPlayer)) {
                BasicPersonality personality = PersonalitiesEnum.getRandomPersonality(stringsBundle, player);
                player.setPersonality(personality);
            }
        }
    }

    private void sayHello() {
        for (Player player : playerList) {
            if (!player.equals(thisPlayer)) {
                mainBoardFront.displayMessage(player.getPersonality().sayHello());
            }
        }
    }

    /**
     * Allows each computer player to play a card if they would like to.
     * 
     * <p>
     * To simulate the fact that a player is allowed by the rules to use several
     * cards at the same time, each computer player has to refuse to play a card for
     * the game to continue.
     * 
     * @return a boolean that indicates if a card was played.
     */
    private boolean askPlayersForCards() {
        var cardUsed = false;
        var allSaidNo = false;
        var index = 0;
        while (!allSaidNo) {

            var player = playerList.get(index);
            if (player.getState() != PlayerState.HEALTHY || player.equals(thisPlayer)) {
                index++;
            } else {
                boolean playerUsedCard = player.wouldLikePlayCardAsCpu(this);
                cardUsed = cardUsed || playerUsedCard;
                index = playerUsedCard ? 0 : index + 1;
            }

            if (index == playerList.size() - 1) {
                allSaidNo = true;
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
        } else if (player != null
                && (player.getState() == PlayerState.SICK_FROM_SNAKE || player.getState() == PlayerState.SICK_FROM_FOOD)
                && player.getSickRound() == round - 1) {
            curePlayer(player);
            mainBoardFront.displayMessage(player + stringsBundle.getString("sickNowCuredPlayer"));
            currentPhase = GamePhase.GATHERING_RESSOURCES;
        } else if (player != null && player.getState() == PlayerState.HEALTHY && !player.equals(thisPlayer)) {
            mainBoardFront.displayMessage(player + stringsBundle.getString("playerTurn"));
            currentPhase = GamePhase.GATHERING_RESSOURCES;
            playAsCPU(player);
        } else if (player != null && player.getState() == PlayerState.HEALTHY && player.equals(thisPlayer)) {
            mainBoardFront.displayMessage(player + stringsBundle.getString("playerTurn"));
            currentPhase = GamePhase.GATHERING_RESSOURCES;
            mainBoardFront.makePlayerChooseAction();
        } else if (player == null && indexOfCurrentPlayer == playerList.size()) {
            currentPhase = GamePhase.GOODS_DISTRIBUTION;
            switchToNextRound();
        } else if (player == null && indexOfCurrentPlayer == playerList.size() + 1) {
            currentPhase = GamePhase.GOODS_DISTRIBUTION;
            postDistributionRoundEnd();
        } else if (player == null) {
            currentPhase = GamePhase.ROUND_BEGINNING;
        } else if (player.getState() == PlayerState.DEAD) {
            mainBoardFront.displayMessage(player + stringsBundle.getString("isDeadPlayer"));
            currentPhase = GamePhase.GATHERING_RESSOURCES;
        } else if (player.getState() == PlayerState.SICK_FROM_SNAKE
                || player.getState() == PlayerState.SICK_FROM_FOOD) {
            mainBoardFront.displayMessage(player + stringsBundle.getString("isSickPlayer"));
            currentPhase = GamePhase.GATHERING_RESSOURCES;
        } else {
            mainBoardFront.displayMessage("Default Case ! Something is fishy ~");
        }
        updateDisplayResources();
    }

    /**
     * Handles the end of this round or the end of the game if players are all dead.
     */
    private void switchToNextRound() {
        if (gameOver || getNbPlayersAlive() == 0) {
            endGame();
        } else {
            currentPhase = GamePhase.GOODS_DISTRIBUTION;
            mainBoardFront.displayMessage(stringsBundle.getString("distributionTime"));
            roundEnd(false); // Goods distribution not for departure
        }
    }

    /**
     * Determines if the game shall start a new round or end because of a departure
     * or a game over.
     */
    private void postDistributionRoundEnd() {

        boolean departure = getNbPlayersAlive() > 0 && (weatherList[round] == -2 || isThereEnoughGoodsForAll(true));
        if (departure) {
            designatedForFoodThisRound.clear();
            designatedForWaterThisRound.clear();
            voluntaryDepartureStarted = true;
            roundEnd(true);
        }

        if (getNbPlayersAlive() == 0) {
            mainBoardFront.displayMessage(stringsBundle.getString("allPlayersDead"));
            gameOver = true;
            endGame();
        } else if (weatherList[round] == -2) {
            mainBoardFront.displayMessage(stringsBundle.getString("hurricane"));
            gameOver = true;
            endGame();
        } else if (voluntaryDepartureStarted) {
            mainBoardFront.displayMessage(stringsBundle.getString("volontaryDeparture"));
            gameOver = true;
            endGame();
        } else {
            mainBoardFront.displayMessage(stringsBundle.getString("newRoundInit"));
            round++;
            deadThisRound.clear();
            cardsPlayedThisRound.clear();
            flashLightList.clear();
            barometerList.clear();
            designatedForFoodThisRound.clear();
            designatedForWaterThisRound.clear();
            currentPhase = GamePhase.ROUND_BEGINNING;
            clearImposedDecisions();
            conchOwner = null;
            matchesUsedThisRound = false;
            nextRoundPlayerList();
            setChief(playerList.get(0));

            nextChief = null;
            twicePlayingPlayer = null;
            askPlayersForCards();

            indexOfCurrentPlayer = -1;
            // var nextToPlay = nextPlayer();
            // play(nextToPlay);

        }

    }

    /**
     * Eliminates players until each one of them has their rations, and a plank on
     * the raft if necessary.
     * 
     * @param forDeparture indicates if the function has to distributes only rations
     *                     or planks as well
     */
    public void roundEnd(boolean forDeparture) {
        currentlyForDeparture = forDeparture;
        boolean end = isThereEnoughGoodsForAll(forDeparture);
        boolean cardUsedVoteSession;
        if (end) {
            goodsDistributionForAlive();
            designated = null;
            killValidated = false;
            mainBoardFront.displayMessage(stringsBundle.getString("distributionEnd"));
            // postDistributionRoundEnd();

        } else if (designated == null) {
            cardUsedVoteSession = askPlayersForCards();
            if (cardUsedVoteSession) {
                roundEnd(forDeparture);
            } else {
                mainBoardFront.allowPlayerToBeginVoteSession();
            }

        } else if (!killValidated) {
            if (lackingResource == ActionType.FOOD) {
                designatedForFoodThisRound.add(designated);
                System.out.println(designated + "is designated for food !");
            } else if (lackingResource == ActionType.WATER) {
                designatedForWaterThisRound.add(designated);
                System.out.println(designated + "is designated for water !");
            } else {
                System.out.println("Something is fishy ~~~~~~~~~");
            }
            cardUsedVoteSession = askPlayersForCards();
            if (cardUsedVoteSession) {
                roundEnd(forDeparture);
            } else {
                mainBoardFront.allowPlayerToKillPlayerAfterVote(forDeparture);
            }
        } else {
            killPlayer(designated);
            designated = null;
            killValidated = false;
            roundEnd(forDeparture);
        }

    }

    /**
     * Displays the end of the game
     */
    private void endGame() {
        currentPhase = GamePhase.END;
        gameOver = true;
        mainBoardFront.displayMessage(stringsBundle.getString("endGame"));
        mainBoardFront.endGame();
    }

    // Ressources gathering function ##############################################

    /**
     * Asks a computer player to do an action (maybe imposed) for this round.
     * 
     * <p>
     * May be moved in the {@code Player} class in a future release.
     * 
     * @param player the player that will do an action
     */
    private void playAsCPU(Player player) {
        ActionType imposedAction = player.getImposedActionThisRound();
        if (imposedAction == ActionType.NONE) {
            imposedAction = player.getPersonality().chooseAction();
        }

        switch (imposedAction) {
            case FOOD:
                mainBoardFront.displayMessage(player + stringsBundle.getString("foodAction"));
                player.playerSeeksFood(this);
                break;
            case WATER:
                mainBoardFront.displayMessage(player + stringsBundle.getString("waterAction"));
                player.playerSeeksWater(this);
                break;
            case WOOD:
                mainBoardFront.displayMessage(player + stringsBundle.getString("woodAction"));
                int nbTries = random.nextInt(6) + 1;
                player.playerSeeksWood(this, nbTries);
                break;
            case CARD:
                mainBoardFront.displayMessage(player + stringsBundle.getString("cardAction"));
                player.playerSeeksCard(this);
                break;
            default:
                break;
        }
        askPlayersForCards();
        if (twicePlayingPlayer != null && twicePlayingPlayer.equals(player)) {
            playerWillPlayTwice(player);
        }
    }

    public void playerWillPlayTwice(Player player) {
        twicePlayingPlayer = null;
        mainBoardFront.displayMessage(player + stringsBundle.getString("playAgain"));
        indexOfCurrentPlayer--;
    }

    /**
     * TODO
     * 
     * @param resource
     * @return
     */
    private boolean everyPlayerHasBeenDesignated(ActionType resource) {
        List<Player> designatedList = resource == ActionType.FOOD ? designatedForFoodThisRound
                : designatedForWaterThisRound;
        for (Player player : playerList) {
            if (player.getState() != PlayerState.DEAD && !player.equals(conchOwner)
                    && !designatedList.contains(player)) {
                return false;
            }
        }
        return true;
    }

    // Round end functions #####################################################
    /**
     * Initializes the voting session by checking who can vote, for who and if some
     * players vote after the others.
     */
    private void beginVotingSession() {
        crystalBallClubOwner = null;
        crystalBallOwner = null;
        if (lackingResource != ActionType.NONE && everyPlayerHasBeenDesignated(lackingResource)) {
            System.out.println("Every player have been designated for this resource. Clearing the list.");
            (lackingResource == ActionType.WATER ? designatedForWaterThisRound : designatedForFoodThisRound).clear();
        }
        for (Player player : playerList) {
            var playerState = player.getState();
            boolean isConchOwner = player.equals(conchOwner);
            var cardClub = player.getCardType(Club.class);
            var cardCrystalBall = player.getCardType(CrystalBall.class);

            boolean alreadyDesignatedForFood = lackingResource == ActionType.FOOD
                    && designatedForFoodThisRound.contains(player);
            boolean alreadyDesignatedForWater = lackingResource == ActionType.WATER
                    && designatedForWaterThisRound.contains(player);
            if (playerState != PlayerState.DEAD && (!isConchOwner || getNbPlayersAlive() == 1)
                    && !alreadyDesignatedForFood && !alreadyDesignatedForWater) {
                pickablePlayers.add(player);

            }
            if (playerState == PlayerState.HEALTHY) {
                detectCrystalBallOrClub(cardClub, cardCrystalBall, player);
                addVotingPlayer(votingPlayers, votes, player, cardClub);

            }

        }
        checkForCrystalBallOrClub();

    }

    /**
     * Adds the voters who do not have a crystal ball to the list of voting players.
     * 
     * 
     * @param votingPlayers the list of the voting players for this vote session
     * @param votes         the votes results
     * @param player        the player currently analyzed
     * @param cardClub      the club card of the player
     */
    private void addVotingPlayer(List<Player> votingPlayers, Map<Player, List<Player>> votes, Player player,
            Card cardClub) {
        if (!player.equals(crystalBallOwner)) {
            votingPlayers.add(player);
            votes.put(player, new ArrayList<>());
            if (!player.equals(crystalBallClubOwner) && cardClub != null && cardClub.isCardRevealed()) {
                votingPlayers.add(player);
                mainBoardFront.displayMessage(player + stringsBundle.getString("votingTwice"));
            }
        }
    }

    /**
     * Checks if the player analysed is the owner of a crystal ball and/or a club.
     * 
     * @param cardClub        the club card of the player
     * @param cardCrystalBall the crystal ball card of the player
     * @param player          the player currently analysed
     * @return
     */
    private void detectCrystalBallOrClub(Card cardClub, Card cardCrystalBall, Player player) {

        if (cardClub != null && cardClub.isCardRevealed() && cardCrystalBall != null
                && cardCrystalBall.isCardRevealed()) {
            System.out.println("crystal ball club owner detected : " + player);
            crystalBallClubOwner = player;
        } else if (cardCrystalBall != null && cardCrystalBall.isCardRevealed()) {
            System.out.println("crystal ball owner detected ! : " + player);
            crystalBallOwner = player;
        }

    }

    /**
     * Distributes the right to vote after any other player to the owner of a
     * crystal ball.
     */
    private void checkForCrystalBallOrClub() {
        System.out.println("Adding owners to voting list... " + crystalBallOwner + " " + crystalBallClubOwner);
        if (crystalBallOwner != null) {
            System.out.println("Crystal ball owner added to voting list");
            votingPlayers.add(crystalBallOwner);
            votes.put(crystalBallOwner, new ArrayList<>());
        } else if (crystalBallClubOwner != null) {
            System.out.println("Crystal ball club owner added to voting list");
            votes.put(crystalBallClubOwner, new ArrayList<>());
            votingPlayers.add(crystalBallClubOwner);
            votingPlayers.add(crystalBallClubOwner);

        }
    }

    /**
     * Handles the voting session and selects one player to be sacrificied.
     */
    public void choosePlayerToDie() {
        votes = new HashMap<>();
        pickablePlayers = new ArrayList<>();
        votingPlayers = new ArrayList<>();
        crystalBallOwner = null;
        crystalBallClubOwner = null;
        mainBoardFront.displayMessage("Vote session ------------------");
        mainBoardFront.displayMessage(stringsBundle.getString("decideWhoVote"));
        beginVotingSession();

        voteTimeForNonOwners();

    }

    /**
     * Ends the vote session and if necessary asks the chief to designate one
     * player.
     */
    public void endOfVote() {
        designated = voteResults();
        if (designated == null && !chief.equals(thisPlayer)) {
            designated = chief.decideWhoDieAsCPU(pickablePlayers);
            roundEnd(currentlyForDeparture);
        } else if (designated == null) {
            mainBoardFront.makePlayerChiefDesignates(pickablePlayers);
        } else {
            roundEnd(currentlyForDeparture);
        }
    }

    /**
     * Asks each player who does not own a crystal ball to vote, if they are allowed
     * to.
     */
    public void voteTimeForNonOwners() {
        System.out.println("Vote Time for non owners " + votingPlayers);
        for (Player player : votingPlayers) {
            var club = player.getCardType(Club.class);
            if (!player.equals(crystalBallOwner) && !player.equals(crystalBallClubOwner) && votes.get(player).isEmpty()
                    || (club != null && votes.get(player).size() != 2)) {
                makePlayerVote(player);
            }
        }
        if (checkVoteNonOwnersOver()) {
            System.out.println("vote non owners over, time for owners");
            voteTimeForOwners();
        }
    }

    /**
     * Aks the player owning a crystal ball to vote if they are allowed to.
     */
    public void voteTimeForOwners() {
        System.out.println("Vote Time for owners");
        for (Player player : votingPlayers) {
            if (player.equals(crystalBallOwner)) {
                displayVoteResult();
                makePlayerVote(player);
            } else if (player.equals(crystalBallClubOwner)) {
                displayVoteResult();
                makePlayerVote(player);
                makePlayerVote(player);
            }
        }
        if (checkVoteOwnersOver()) {
            System.out.println("vote owners over, end of vote");
            endOfVote();
        }

    }

    /**
     * Checks if the player owning a crystal ball, has voted.
     * 
     * @return a boolean indicating if the player has voted
     */
    public boolean checkVoteOwnersOver() {
        System.out.println("Check Vote Owners");
        for (Player player : votingPlayers) {
            if ((player.equals(crystalBallOwner) && votes.get(player).isEmpty())
                    || (player.equals(crystalBallClubOwner) && votes.get(player).size() != 2)) {
                return false;
            }
        }
        return true;
    }

    /**
     * Checks if each player who does not own a crystal ball has voted.
     * 
     * @return a boolean indicating if the player has voted
     */
    public boolean checkVoteNonOwnersOver() {
        System.out.println("Check Vote Non Owners");
        for (Player player : votingPlayers) {
            var club = player.getCardType(Club.class);
            if ((!player.equals(crystalBallOwner) && !player.equals(crystalBallClubOwner)
                    && votes.get(player).isEmpty()) || (club != null && votes.get(player).size() != 2)) {
                return false;
            }
        }
        return true;
    }

    /**
     * Asks a player to vote for a player in the {@link Board#pickablePlayers} list
     * and saves the vote in the map {@link Board#votes}.
     * 
     * @param player the voting player
     */
    private void makePlayerVote(Player player) {
        if (!player.equals(thisPlayer)) {
            var designatedPlayer = player.voteAsCPU(pickablePlayers);
            votes.get(player).add(designatedPlayer);
        } else {
            player.vote(this, pickablePlayers);
        }

    }

    /**
     * Calculates the results of the voting session.
     * 
     * @return the {@code Player} designated by the votes
     */
    private Player voteResults() {
        mainBoardFront.displayMessage("~~~~~~~~~~~~~~~~ Final results : ");
        displayVoteResult();
        Map<Player, Integer> results = new HashMap<>();
        for (Player player : playerList) {
            results.put(player, 0);
        }

        Integer max = 0;
        var nbOfMax = 0;
        List<Player> maxPlayers = new ArrayList<>();
        for (Entry<Player, List<Player>> entry : votes.entrySet()) {
            for (Player designatedPlayer : entry.getValue()) {
                results.put(designatedPlayer, results.get(designatedPlayer) + 1);
            }
        }

        for (Entry<Player, Integer> entry : results.entrySet()) {

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
            var player = maxPlayers.get(0);
            mainBoardFront.displayMessage(player + stringsBundle.getString("designatedPlayer"));
            return player;
        }
        mainBoardFront.displayMessage(stringsBundle.getString("chiefWillDecide"));
        return null;
    }

    /**
     * Distributes the rations to the players still alive.
     */
    private void goodsDistributionForAlive() {
        foodRations -= getNbPlayersAlive();
        waterRations -= getNbPlayersAlive();
    }

    // Tools functions #####################################################

    /**
     * Updates the resources panel in the game interface.
     */
    public void updateDisplayResources() {
        mainBoardFront.updateNorth(foodRations, waterRations, nbWoodPlanksFragment, nbWoodPlanks, getWeather(),
                getNbPlayersAlive(), round);
    }

    /**
     * Gets the list of sick players.
     * 
     * @return the list of sick players
     */
    public List<Player> getSickPlayersList() {
        List<Player> sickPlayers = new ArrayList<>();
        for (Player player : playerList) {
            PlayerState state = player.getState();
            if (state == PlayerState.SICK_FROM_SNAKE || state == PlayerState.SICK_FROM_FOOD) {
                sickPlayers.add(player);
            }
        }
        return sickPlayers;
    }

    /**
     * Displays for the non computer player the results of a vote, which means the
     * designated player(s) for each voting player.
     * 
     * @param votes the map which contains the vote results.
     */
    private void displayVoteResult() {
        for (Entry<Player, List<Player>> entry : votes.entrySet()) {
            if (!entry.getValue().isEmpty()) {
                mainBoardFront.displayMessage(entry.getKey() + stringsBundle.getString("votesFor") + entry.getValue());
            }
        }
    }

    /**
     * Gives a card to a player.
     * 
     * @param player the player who will have a new card, not null
     * @param card   the new card, not null
     */
    public void giveCardToPlayer(Player player, Card card) {
        if (card != null && player != null) {
            player.addCardToInventory(card);
        }
    }

    /**
     * Tool function to calculate how much food a player got with the food action.
     * 
     * @param index the number picked by the player
     * @return the amount of food got by the player
     */
    public int howMuchFood(int index) {
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
        if (indexOfCurrentPlayer < playerList.size() - 1 && indexOfCurrentPlayer >= -1) {
            indexOfCurrentPlayer++;
            player = playerList.get(indexOfCurrentPlayer);

        } else {
            indexOfCurrentPlayer++;
            player = null;
        }

        for (Player oldCurrentPlayer : playerList) {
            oldCurrentPlayer.setCurrentPlayer(false);
        }
        if (player != null) {
            player.setCurrentPlayer(true);
        }
        mainBoardFront.updateCurrentPlayer();
        return player;
    }

    /**
     * Generates the player list for the next round. This list indicates the order
     * in which players will play.
     */
    public void nextRoundPlayerList() {
        if (getNbPlayersAlive() > 0) {

            var futureChief = playerList.remove(playerList.size() - 1);
            playerList.add(0, futureChief);
            while (playerList.get(0).getState() == PlayerState.DEAD
                    || (nextChief != null && !playerList.get(0).equals(nextChief))) {
                futureChief = playerList.remove(playerList.size() - 1);
                playerList.add(0, futureChief);
            }

        }
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
        var player = playerList.get(indexOfPlayer);
        var playerIsAlive = false;
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
        var alive = 0;
        for (var index = 0; index < playerList.size(); index++) {
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
     *                  rations and a plank on the raft.
     * @return a boolean which indicates if there are enough rations for everyone
     */
    public boolean isThereEnoughGoodsForAll(boolean departure) {
        int n = getNbPlayersAlive();
        boolean enoughResources = departure ? nbWoodPlanks >= n && foodRations >= n && waterRations >= n
                : waterRations >= n && foodRations >= n;
        if (!enoughResources && waterRations <= foodRations) {
            lackingResource = ActionType.WATER;
            System.out.println("lackingResource is now water");
        } else if (!enoughResources) {
            lackingResource = ActionType.FOOD;
            System.out.println("lackingResource is now food");
        } else {
            lackingResource = ActionType.NONE;
            System.out.println("lackingResource is now none");
        }
        return enoughResources;
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
        if (designated != null && player.equals(designated)) {
            mainBoardFront.displayMessage(player + stringsBundle.getString("sacrificedForCrew"));
        }
        if (player.equals(chief) && getNbPlayersAlive() > 0) {
            Player newChief = getPlayerAliveAfterBefore(playerList.indexOf(player), false);
            setChief(newChief);
            mainBoardFront.displayMessage(newChief + stringsBundle.getString("newChief"));
        }

        mainBoardFront.updateSouth();
    }

    /**
     * Makes a player sick and updates the game interface for the case it was a non
     * computer player.
     * 
     * @param player the player to make sick
     */
    public void sickPlayer(Player player, PlayerState stateOfSickness) {
        player.setState(stateOfSickness);
        if (thisPlayer.equals(player)) {
            mainBoardFront.setAllowedToPlayCard(false);
            mainBoardFront.updateSouth();
        }
    }

    /**
     * Cures a player from sickness and updates the game interface for the case it
     * was a non computer player.
     * 
     * @param player the player to cure
     */
    public void curePlayer(Player player) {
        player.setState(PlayerState.HEALTHY);
        if (thisPlayer.equals(player)) {
            mainBoardFront.setAllowedToPlayCard(true);
            mainBoardFront.updateSouth();
        }
    }

    /**
     * Redistributes the remaining cards of a dead player (such as a {@code Gun} for
     * instance).
     * 
     * @param player the dead player from who cards are taken
     */
    public void distributeCardsFromDeadPlayer(Player player) {
        player.deathPurgeCards();

        if (getNbPlayersAlive() > 0) {
            int indexOfPlayer = playerList.indexOf(player);
            var playerBefore = getPlayerAliveAfterBefore(indexOfPlayer, false);
            var playerAfter = getPlayerAliveAfterBefore(indexOfPlayer, true);

            int indexMax = player.getCardNumber();
            for (var index = 0; index < indexMax; index++) {
                var card = player.getCard(0);
                player.removeCard(0);

                if (index % 2 == 0) {
                    giveCardToPlayer(playerAfter, card);
                } else {
                    giveCardToPlayer(playerBefore, card);
                }

            }
        } else {
            for (var index = player.getCardNumber() - 1; index >= 0; index--) {
                player.removeCard(index);
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
     * The setter for the attribute {@link Board#mainBoardFront}.
     * 
     * @param mainBoardFront the interface of the game
     */
    public void setMainBoardFront(MainBoardFront mainBoardFront) {
        this.mainBoardFront = mainBoardFront;
    }

    /**
     * The getter for the attribute {@link Board#mainBoardFront}.
     * 
     * @return the interface of the game
     */
    public MainBoardFront getMainBoardFront() {
        return mainBoardFront;
    }

    /**
     * Shows the next meteo cards to the player. Currently only working for the non
     * computer player as the others are playing randomly.
     * 
     * @param player the player who owns the card
     */
    public void showBarometerList(Player player) {
        if (player.equals(thisPlayer)) {
            mainBoardFront.displayMessage(stringsBundle.getString("nextWeather") + barometerList);
        }
    }

    /**
     * Shows the next cards in the deck to the player. Currently only working for
     * the non computer player as the others are playing randomly.
     * 
     * @param player the player who owns the card
     */
    public void showFlashLightList(Player player) {
        if (player.equals(thisPlayer)) {
            mainBoardFront.displayMessage(stringsBundle.getString("nextCards") + flashLightList);
        }
    }

    /**
     * Shows cards of all other players to this player. Currently only working for
     * the non computer player as the others are playing randomly.
     * 
     * @param player the player who owns the card
     */
    public void showSpyglassMap(Player player) {
        mainBoardFront.displayMessage(stringsBundle.getString("cardsOfPlayersSpyglass"));
        for (Entry<Player, List<Card>> entry : spyglassMap.entrySet()) {
            mainBoardFront.displayMessage(entry.getKey() + stringsBundle.getString("hasCard") + entry.getValue());
        }
    }

    /**
     * A getter for the attribute {@link Board#weatherList}.
     * 
     * @return the list of the different weather
     */
    public int getWeather() {
        return weatherList[round];
    }

    /**
     * A getter for the attribute {@link Board#votes}.
     * 
     * @return the map of the votes
     */
    public Map<Player, List<Player>> getVotes() {
        return votes;
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

    /**
     * The setter for the attribute {@link Board#chief}, updates the {@code chief}
     * attribute of the player.
     * 
     * @param chief the new chief
     */
    public void setChief(Player chief) {
        this.chief = chief;
        for (Player player : playerList) {
            player.setPlayerChief(false);
        }
        chief.setPlayerChief(true);
        mainBoardFront.updateSouth();
    }

    /**
     * The setter for the attribute {@link Board#designated}.
     * 
     * @param designated the player designated to be sacrificed
     */
    public void setDesignated(Player designated) {
        this.designated = designated;
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
     * The setter for the attribute {@link Board#spyglassMap}.
     * 
     * @param spyglassMap the map of cards seen with spyglass
     */
    public void setSpyglassMap(Map<Player, List<Card>> spyglassMap) {
        this.spyglassMap = spyglassMap;
    }

    /**
     * The getter for the attribute {@link Board#spyglassMap}.
     * 
     * @return the map of cards seen with the spyglass
     */
    public Map<Player, List<Card>> getSpyglassMap() {
        return spyglassMap;
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
     * The setter for the attribute {@link Board#indexOfCurrentPlayer}.
     * 
     * @param indexOfCurrentPlayer the index of the player who is playing
     */
    public void setIndexOfCurrentPlayer(int indexOfCurrentPlayer) {
        this.indexOfCurrentPlayer = indexOfCurrentPlayer;
    }

    public boolean getCurrentlyForDeparture() {
        return this.currentlyForDeparture;
    }

    /**
     * The setter for the attribute {@link Board#thisPlayer}
     * 
     * @param thisPlayer the player controled by the user
     */
    public void setThisPlayer(Player thisPlayer) {
        this.thisPlayer = thisPlayer;
    }

    /**
     * The getter for the attribute {@link Board#thisPlayer}.
     * 
     * @return the non computer player
     */
    public Player getThisPlayer() {
        return thisPlayer;
    }

    /**
     * The setter for the attribute {@link killValidated}.
     * 
     * @param killValidated a boolean indicating whether the kill is validated by
     *                      the user
     */
    public void setKillValidated(boolean killValidated) {
        this.killValidated = killValidated;
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
