package back;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Random;
import java.util.ResourceBundle;
import java.util.Scanner;
import java.util.Map.Entry;

import back.cards.Card;
import back.cards.Club;
import back.cards.CrystalBall;
import front.MainBoardFront;

/**
 * The main board of the game.
 * 
 * <p>
 * This board stores data for the game such as the number of rations or the card
 * list. The different steps of the game are implemented here, from food
 * gathering to player sacrifices.
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
    private List<Card> spyglassList;
    private List<Card> flashLightList;
    private List<Integer> barometerList;
    private Player conchOwner;
    private transient Scanner inputReader;
    private MainBoardFront mainBoardFront;
    private Player crystalBallClubOwner;
    private Player crystalBallOwner;
    private Map<Player, List<Player>> votes;
    private List<Player> pickablePlayers;
    private List<Player> votingPlayers;
    private Player designated;
    private boolean cardUsedVoteSession;
    private boolean killValidated;
    private boolean currentlyForDeparture;

    /**
     * Builds the game without launching it and without incorporating any
     * non-computer user.
     * 
     * @param nbPlayer the number of players in this game
     */
    public Board(MainBoardFront boardFront, int nbPlayers) {
        mainBoardFront = boardFront;
        currentPhase = GamePhase.INITIALISATION;
        Locale locale = new Locale("en", "US");
        stringsBundle = ResourceBundle.getBundle("Strings", locale);
        data = new Data(stringsBundle);
        deck = data.getDeck(this);
        gameOver = false;
        Integer[] rations = data.getInitialRations(nbPlayers);
        foodRations = rations[0];
        waterRations = rations[1];
        indexOfCurrentPlayer = -1;
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
    public Board(MainBoardFront boardFront, int nbPlayers, String namePlayer) {
        this(boardFront, nbPlayers);

        indexOfThisPlayer = random.nextInt(nbPlayers);
        thisPlayer = new Player(namePlayer, stringsBundle);
        playerList.remove(indexOfThisPlayer);
        playerList.add(indexOfThisPlayer, thisPlayer);

        cardsDistribution();
        inputReader = new Scanner(System.in);

        boardFront.displayMessage("End of initialisation. Good luck !");
        mainBoardFront.setBoard(this);
        updateDisplayResources();
        mainBoardFront.buildPlayersDisplay(indexOfThisPlayer);

        mainBoardFront.updateSouth();

        askPlayersForCards();
        currentPhase = GamePhase.GATHERING_RESSOURCES;
        setChief(playerList.get(0));

        // play(playerList.get(0));

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
     * Allows each computer player to play a card if they would like to.
     * 
     * <p>
     * To simulate the fact that a player is allowed by the rules to use several
     * cards at the same time, each player has to refuse to play a card for the game
     * to continue.
     * 
     * @return a boolean that indicates if a card was played.
     */
    public boolean askPlayersForCards() {
        boolean cardUsed = false;
        boolean allSaidNo = false;
        int index = 0;
        while (!allSaidNo) {

            Player player = playerList.get(index);
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
        } else if (player != null && player.getState() == PlayerState.SICK && player.getRoundSick() == round - 1) {
            player.setState(PlayerState.HEALTHY);
            mainBoardFront.displayMessage(player + " was sick and could not play, now cured");
            // play(nextPlayer());
        } else if (player != null && player.getState() == PlayerState.HEALTHY && !player.equals(thisPlayer)) {
            mainBoardFront.displayMessage(player + "'s turn !");
            playAsCPU(player);
        } else if (player != null && player.getState() == PlayerState.HEALTHY && player.equals(thisPlayer)) {
            mainBoardFront.displayMessage(player + "'s turn");
            mainBoardFront.makePlayerChooseAction();
        } else if (player == null) {
            switchToNextRound();
        } else if (player.getState() == PlayerState.DEAD) {
            mainBoardFront.displayMessage(player + " is dead and cannot play !\n");
            // play(nextPlayer());
        } else if (player.getState() == PlayerState.SICK) {
            mainBoardFront.displayMessage(player + " is sick and cannot play !\n");
            // play(nextPlayer());
        } else {
            mainBoardFront.displayMessage("Default Case ! Something is fishy ~");
        }
        updateDisplayResources();
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
            mainBoardFront.displayMessage("It is time to distribute the rations of the round !");
            roundEnd(false); // Goods distribution not for departure
        }
    }

    public void postDistributionRoundEnd() {
        mainBoardFront.displayMessage("Distribution ended !");
        boolean departure = weatherList[round] == -2 || isThereEnoughGoodsForAll(true);
        if (departure) {
            voluntaryDepartureStarted = true;
            roundEnd(true);

        }
        if (getNbPlayersAlive() == 0) {
            mainBoardFront.displayMessage("All players are dead :(");
            gameOver = true;
            endGame();
        } else if (weatherList[round] == -2) {
            mainBoardFront.displayMessage("Hurricane !");
            gameOver = true;
            endGame();
        } else if (voluntaryDepartureStarted) {
            mainBoardFront.displayMessage("Volontary Departure !");
            gameOver = true;
            endGame();
        } else {
            mainBoardFront.displayMessage("Going to the next round !");
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
            setChief(playerList.get(0));

            nextChief = null;
            twicePlayingPlayer = null;
            askPlayersForCards();

            currentPhase = GamePhase.GATHERING_RESSOURCES;
            indexOfCurrentPlayer = -1;
            Player nextToPlay = nextPlayer();
            play(nextToPlay);

        }

    }

    public void roundEnd(boolean forDeparture) {
        System.out.println("Round end " + forDeparture);
        currentlyForDeparture = forDeparture;
        boolean end = isThereEnoughGoodsForAll(forDeparture);
        if (end) {
            System.out.println("case 1");
            goodsDistributionForAlive();
            designated = null;
            cardUsedVoteSession = false;
            killValidated = false;
            postDistributionRoundEnd();

        } else if (designated == null) {
            System.out.println("case 2");
            cardUsedVoteSession = askPlayersForCards();
            if (cardUsedVoteSession) {
                System.out.println("Card used");
                cardUsedVoteSession = false;
                roundEnd(forDeparture);
            } else {
                System.out.println("No card used");
                mainBoardFront.allowPlayerToBeginVoteSession();
            }

        } else if (!killValidated) {
            System.out.println("case 3");
            cardUsedVoteSession = askPlayersForCards();
            if (cardUsedVoteSession) {
                cardUsedVoteSession = false;
                roundEnd(forDeparture);
            } else {
                mainBoardFront.allowPlayerToKillPlayerAfterVote(forDeparture);
            }
        } else {
            System.out.println("case 4");
            killPlayer(designated);
            designated = null;
            cardUsedVoteSession = false;
            killValidated = false;
            roundEnd(forDeparture);
        }

    }

    /**
     * Displays the end of the game
     */
    public void endGame() {
        currentPhase = GamePhase.END;
        inputReader.close();
        gameOver = true;
        mainBoardFront.displayMessage("End of the game!");
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
        int nbTries = 0;
        ActionType imposedAction = player.getImposedActionThisRound();
        if (imposedAction == ActionType.NONE) {
            System.out.println(
                    stringsBundle.getString("chooseActionList") + Arrays.toString(ActionType.getLActionTypes()));
            imposedAction = ActionType.getLActionTypes()[getUserIntChoice(0, 3)];
        }

        switch (imposedAction) {
            case FOOD:
                System.out.println(player + " is getting food !\n");
                player.playerSeeksFood(this);
                break;
            case WATER:
                System.out.println(player + " is getting water !\n");
                player.playerSeeksWater(this);
                break;
            case WOOD:
                System.out.println(player + " is getting wood !\n");
                System.out.println(stringsBundle.getString("howManyFragments"));
                nbTries = getUserIntChoice(0, 6);
                player.playerSeeksWood(this, nbTries);
                break;
            case CARD:
                System.out.println(player + " is getting a card !\n");
                player.playerSeeksCard(this);
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
                player.playerSeeksFood(this);
                break;
            case WATER:
                System.out.println(player + " is getting water !\n");
                player.playerSeeksWater(this);
                break;
            case WOOD:
                System.out.println(player + " is getting wood !\n");
                int nbTries = random.nextInt(6) + 1;
                player.playerSeeksWood(this, nbTries);
                break;
            case CARD:
                System.out.println(player + " is getting a card !\n");
                player.playerSeeksCard(this);
                break;
            default:
                break;
        }
        askPlayersForCards();
        if (twicePlayingPlayer != null && twicePlayingPlayer.equals(player)) {
            twicePlayingPlayer = null;
            System.out.println(player + " will play again !");
            playAsCPU(player);
        }
    }

    // Round end functions #####################################################
    /**
     * Initializes the voting session.
     * 
     * @param pickablePlayers the list of the players who can be "elected"
     * @param votingPlayers   the list of the players who can vote
     * @param votes           the results of the voting session
     */
    public void beginVotingSession() {
        for (Player player : playerList) {
            PlayerState playerState = player.getState();
            boolean isConchOwner = player.equals(conchOwner);
            Card cardClub = player.getCardType(Club.class);
            Card cardCrystalBall = player.getCardType(CrystalBall.class);

            if (playerState != PlayerState.DEAD && (!isConchOwner || getNbPlayersAlive() == 1)) {
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
     * @param crystalBallClubOwner the player who owns a crystal ball and a club
     * @param crystalBallOwner     the player who owns a crystal ball but no club
     * @param votingPlayers        the list of the voting players for this vote
     *                             session
     * @param votes                the votes results
     * @param player               the player currently analyzed
     * @param cardClub             the club card of the player
     */
    public void addVotingPlayer(List<Player> votingPlayers, Map<Player, List<Player>> votes, Player player,
            Card cardClub) {
        if (crystalBallOwner == null) {
            votingPlayers.add(player);
            votes.put(player, new ArrayList<>());
            if (crystalBallClubOwner == null && cardClub != null && cardClub.isCardRevealed()) {
                votingPlayers.add(player);
                mainBoardFront.displayMessage(player + " will be voting twice ! Thanks to his club");
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
    public void detectCrystalBallOrClub(Card cardClub, Card cardCrystalBall, Player player) {
        crystalBallClubOwner = null;
        crystalBallOwner = null;
        if (cardClub != null && cardClub.isCardRevealed() && cardCrystalBall != null
                && cardCrystalBall.isCardRevealed()) {
            crystalBallClubOwner = player;
            System.out.println("Club and crystal ball owner detected !");
        } else if (cardCrystalBall != null && cardCrystalBall.isCardRevealed()) {
            crystalBallOwner = player;
            System.out.println("Crystal ball owner detected !");
        }

    }

    /**
     * Distributes the right to vote after any other player to the owner of a
     * crystal ball.
     * 
     * @param crystalBallOwner     the player who owns a crystal ball but no club
     * @param crystalBallClubOwner the player who owns a crystal ball and a club
     * @param votingPlayers        the list of players who will vote during this
     *                             voting session
     * @param votes                the results of the votes
     */
    public void checkForCrystalBallOrClub() {
        if (crystalBallOwner != null) {
            votingPlayers.add(crystalBallOwner);
            votes.put(crystalBallOwner, new ArrayList<>());
        } else if (crystalBallClubOwner != null) {
            votes.put(crystalBallClubOwner, new ArrayList<>());
            votingPlayers.add(crystalBallClubOwner);
            votingPlayers.add(crystalBallClubOwner);

        }
    }

    /**
     * Handles the voting session and selects one player to be sacrificied.
     * 
     * @return the player who will be sacrificied
     */
    public void choosePlayerToDie() {

        votes = new HashMap<>();
        pickablePlayers = new ArrayList<>();
        votingPlayers = new ArrayList<>();
        crystalBallOwner = null;
        crystalBallClubOwner = null;
        mainBoardFront.displayMessage("Vote session ------------------");

        beginVotingSession();

        voteTimeForNonOwners();

    }

    public void endOfVote() {
        System.out.println("End of vote !");
        designated = voteResults();
        if (designated == null && !chief.equals(thisPlayer)) {
            System.out.println("CPU Chief");
            designated = chief.decideWhoDieAsCPU(pickablePlayers);
            roundEnd(currentlyForDeparture);
        } else if (designated == null) {
            System.out.println("Human chief");
            mainBoardFront.makePlayerChiefDesignates(pickablePlayers);
        } else {
            roundEnd(currentlyForDeparture);
        }
    }

    public void voteTimeForNonOwners() {
        System.out.println("Vote time for non owners");
        for (Player player : votingPlayers) {
            if (!player.equals(crystalBallOwner) && !player.equals(crystalBallClubOwner)
                    && votes.get(player).isEmpty()) {
                makePlayerVote(player);
            }
        }
        if (checkVoteNonOwnersOver()) {
            voteTimeForOwners();
        }
    }

    public void voteTimeForOwners() {
        System.out.println("voteTimeForOwners !");
        for (Player player : votingPlayers) {
            if (player.equals(crystalBallOwner)) {
                makePlayerVote(player);
            } else if (player.equals(crystalBallClubOwner)) {
                makePlayerVote(player);
                makePlayerVote(player);
            }
        }
        if (checkVoteOwnersOver()) {

            endOfVote();
        }

    }

    public boolean checkVoteOwnersOver() {
        for (Player player : votingPlayers) {
            if ((player.equals(crystalBallOwner) && votes.get(player).isEmpty())
                    || (player.equals(crystalBallClubOwner) && votes.get(player).size() != 2)) {
                System.out.println("Check owner false");
                return false;
            }
        }
        System.out.println("Check owner true");
        return true;
    }

    public boolean checkVoteNonOwnersOver() {
        for (Player player : votingPlayers) {
            if (!player.equals(crystalBallOwner) && !player.equals(crystalBallClubOwner)
                    && votes.get(player).isEmpty()) {
                System.out.println("Check non owner false");
                return false;
            }
        }
        System.out.println("Check non owner true");
        return true;
    }

    /**
     * Asks a player to vote for a player in a given list and saves the vote in a
     * map.
     * 
     * @param player          the voting player
     * @param votes           the map which contains the votes of every voting
     *                        player
     * @param pickablePlayers the list of the players who can be "elected"
     */
    public void makePlayerVote(Player player) {
        if (!player.equals(thisPlayer)) {
            Player designatedPlayer = player.voteAsCPU(pickablePlayers);
            votes.get(player).add(designatedPlayer);
        } else {
            player.vote(this, pickablePlayers);
        }

    }

    /**
     * Calculates the results of the voting session.
     * 
     * @param votes the map which contains the results of the vote
     * @return the Player designated by the votes
     */
    public Player voteResults() {
        displayVoteResult();
        Map<Player, Integer> results = new HashMap<>();
        for (Player player : playerList) {
            results.put(player, 0);
        }

        Integer max = 0;
        int nbOfMax = 0;
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
            Player player = maxPlayers.get(0);
            mainBoardFront.displayMessage(player + " is designated by the crew");
            return player;
        }
        mainBoardFront.displayMessage("The chief will decide who will be sacrificied");
        return null;
    }

    /**
     * Distributes the rations to the players still alive.
     */
    public void goodsDistributionForAlive() {
        foodRations -= getNbPlayersAlive();
        waterRations -= getNbPlayersAlive();
    }

    // Tools functions #####################################################

    // TODO javadoc
    public void updateDisplayResources() {
        mainBoardFront.updateNorth(foodRations, waterRations, nbWoodPlanksFragment, nbWoodPlanks, getWeather(),
                getNbPlayersAlive(), round);
    }

    // TODO Javadoc
    public List<Player> getSickPlayersList() {
        List<Player> sickPlayers = new ArrayList<>();
        for (Player player : playerList) {
            if (player.getState() == PlayerState.SICK) {
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
    public void displayVoteResult() {
        for (Entry<Player, List<Player>> entry : votes.entrySet()) {
            if (!entry.getValue().isEmpty()) {
                mainBoardFront.displayMessage(entry.getKey() + stringsBundle.getString("votesFor") + entry.getValue());
            }
        }
    }

    /**
     * Gets an user input.
     * 
     * @return the input
     */
    public String getUserInput() {
        String input = "";
        if (inputReader.hasNextLine()) {
            input = inputReader.nextLine();
        }
        return input;
    }

    /**
     * Gets a yes or no answer.
     * 
     * @return the answer, on a boolean form
     */
    public boolean getYesNoAnswer() {
        System.out.println(stringsBundle.getString("yesNoQuestion"));
        String answer = getUserInput();
        answer = answer.substring(0, 1).toLowerCase();
        while (!answer.equals("y") && !answer.equals("n")) {
            System.out.println(stringsBundle.getString("yesNoQuestion"));
            answer = getUserInput();
            answer = answer.substring(0, 1).toLowerCase();
        }
        return answer.equals("y");
    }

    /**
     * Gets a int input by the user, between two int, both inclusive.
     * 
     * @param minBound the min bound
     * @param maxBound the max bound
     * @return the input
     */
    public int getUserIntChoice(int minBound, int maxBound) {
        int choice = minBound - 1;

        while (choice < minBound || choice > maxBound) {
            try {
                choice = Integer.parseInt(inputReader.nextLine());
            } catch (NumberFormatException e) {
                choice = minBound - 1;
            }
        }
        return choice;

    }

    /**
     * Asks the user to choose several players in the players list.
     * 
     * @param nbPlayers the number of player that the user have to pick
     * @return the picked players
     */
    public List<Integer> getUserPlayerChoice(int nbPlayers) {
        List<Integer> pickedPlayers = new ArrayList<>();
        for (int index = 0; index < nbPlayers; index++) {
            int indexPickedPlayer = -1;
            while (indexPickedPlayer < 0 || indexPickedPlayer >= playerList.size()) {
                System.out.println(stringsBundle.getString("choosePlayerList") + playerList);
                indexPickedPlayer = getUserIntChoice(0, playerList.size() - 1);
            }
            pickedPlayers.add(indexPickedPlayer);
        }
        return pickedPlayers;
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
     * Tool function to calculate how much food a player got
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
        if (indexOfCurrentPlayer != playerList.size() - 1) {
            indexOfCurrentPlayer++;
            player = playerList.get(indexOfCurrentPlayer);
        } else {
            player = null;
        }
        mainBoardFront.updateCurrentPlayer();
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
        return departure ? nbWoodPlanks >= n && foodRations >= n && waterRations >= n
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
        mainBoardFront.displayMessage(player + "has been sacrificed for the sake of the crew :(");
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
                System.out.println("player.removeCard appelée");
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

    public void setMainBoardFront(MainBoardFront mainBoardFront) {
        this.mainBoardFront = mainBoardFront;
    }

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
    public void showSpyglassList(Player player) {
        if (player.equals(thisPlayer)) {
            mainBoardFront.displayMessage(stringsBundle.getString("cardsOfAllPlayers") + spyglassList);
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
     * TODO
     * 
     * @return
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

    public void setChief(Player chief) {
        this.chief = chief;
        for (Player player : playerList) {
            player.setPlayerChief(false);
        }
        chief.setPlayerChief(true);

    }

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

    // TODO javadoc
    public Player getThisPlayer() {
        return thisPlayer;
    }

    /**
     * TODO
     * 
     * @param killValidated
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
