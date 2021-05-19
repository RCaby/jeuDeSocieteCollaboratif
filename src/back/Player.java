package back;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Random;
import java.util.ResourceBundle;

import static javax.swing.ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED;
import static javax.swing.ScrollPaneConstants.VERTICAL_SCROLLBAR_NEVER;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.io.Serializable;

import javax.swing.BorderFactory;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

import back.cards.Axe;
import back.cards.Card;
import back.cards.FishingRod;
import back.cards.Gourd;

/**
 * The {@code Player} class represents a Player.
 * 
 * <p>
 * In this class are different methods simulating the actions of a player
 * (discarding cards for instance). This class should not contain any AI related
 * method, only implements function to interact with the board or card but not
 * how to interact (for instance, this class implements card trading between
 * Players but does not describe when such a trade is needed).
 */
public class Player implements Serializable {

    private static final long serialVersionUID = 2874539910717357461L;
    private List<Card> inventory;
    private List<Card> inventoryRevealed;
    private List<Card> inventoryHidden;
    private JPanel display;
    private String name;
    private PlayerState state;
    private boolean isChief;
    private boolean currentPlayer;
    private JLabel chiefLabel;
    private JLabel stateLabel;
    private JPanel cardRevealedPanel;
    private JPanel cardHiddenPanel;
    private ActionType imposedActionThisRound;
    private Random random = new Random();
    private int sickRound;
    private transient ResourceBundle stringsBundle;

    /**
     * Generates a Player.
     * 
     * @param name          The name of the player
     * @param stringsBundle The bundle which contains the strings of the projet, in
     *                      the current language of the application.
     */
    public Player(String name, ResourceBundle stringsBundle) {
        this.name = name;
        inventory = new ArrayList<>();
        inventoryHidden = new ArrayList<>();
        inventoryRevealed = new ArrayList<>();
        imposedActionThisRound = ActionType.NONE;
        state = PlayerState.HEALTHY;
        this.stringsBundle = stringsBundle;
        buildDisplay();

    }

    @Override
    public String toString() {
        return name;
    }

    /**
     * The data displayer of the Player.
     */
    private void buildDisplay() {
        display = new JPanel(new BorderLayout());

        var displayCenter = new JPanel();
        display.add(displayCenter, BorderLayout.CENTER);
        var displayNorth = new JPanel();
        display.add(displayNorth, BorderLayout.NORTH);
        var voidPanelSouth = new JPanel();
        display.add(voidPanelSouth, BorderLayout.SOUTH);

        display.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        display.setMaximumSize(new Dimension(600, 200));
        displayCenter.setLayout(new GridLayout(1, 0, 10, 10));

        var nameLabel = new JLabel(name);
        displayNorth.add(nameLabel);

        var statePanelContainer = new JPanel(new GridLayout(1, 1));

        var statePanel = new JPanel(new BorderLayout());
        statePanelContainer.add(statePanel);
        stateLabel = new JLabel(state.toString());

        displayCenter.add(statePanelContainer);
        var statePanelNorth = new JPanel(new FlowLayout(FlowLayout.CENTER));
        var statePanelCenter = new JPanel(new FlowLayout(FlowLayout.CENTER));
        statePanel.add(statePanelNorth, BorderLayout.NORTH);
        statePanel.add(statePanelCenter, BorderLayout.CENTER);
        statePanelNorth.add(stateLabel);
        statePanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        chiefLabel = new JLabel(stringsBundle.getString("not_chief_label"));

        statePanelCenter.add(chiefLabel);

        cardRevealedPanel = new JPanel(new GridLayout(1, 0, 10, 10));

        var cardRevealedScrollableContener = new JPanel(new GridLayout(1, 1));

        displayCenter.add(cardRevealedScrollableContener);

        var scrollPaneRevealed = new JScrollPane(cardRevealedPanel);
        cardRevealedScrollableContener.add(scrollPaneRevealed);
        scrollPaneRevealed.setHorizontalScrollBarPolicy(HORIZONTAL_SCROLLBAR_AS_NEEDED);
        scrollPaneRevealed.setVerticalScrollBarPolicy(VERTICAL_SCROLLBAR_NEVER);
        scrollPaneRevealed.setPreferredSize(new Dimension(170, 100));

        cardHiddenPanel = new JPanel(new GridLayout(1, 0, 10, 10));
        var cardHiddenScrollableContener = new JPanel(new GridLayout(1, 1));

        displayCenter.add(cardHiddenScrollableContener);
        var scrollPaneHidden = new JScrollPane(cardHiddenPanel);
        cardHiddenScrollableContener.add(scrollPaneHidden);
        scrollPaneHidden.setHorizontalScrollBarPolicy(HORIZONTAL_SCROLLBAR_AS_NEEDED);
        scrollPaneHidden.setVerticalScrollBarPolicy(VERTICAL_SCROLLBAR_NEVER);
        scrollPaneHidden.setPreferredSize(new Dimension(170, 100));
    }

    /**
     * The food gathering action of the player.
     * 
     * @param board The main board, used to store the new rations.
     */
    public void playerSeeksFood(Board board) {
        int pickedIndex = random.nextInt(6) + 1;

        int foodGot = board.howMuchFood(pickedIndex);

        var fishingRod = getCardType(FishingRod.class);
        if (fishingRod != null && fishingRod.isCardRevealed()) {
            int pickedIndex2 = random.nextInt(6) + 1;
            while (pickedIndex2 == pickedIndex) {
                pickedIndex2 = random.nextInt(6) + 1;
            }
            foodGot += board.howMuchFood(pickedIndex2);
        }
        board.getMainBoardFront().displayMessage(stringsBundle.getString("gotFood") + foodGot);
        board.addFood(foodGot);
        board.updateDisplayResources();
    }

    /**
     * The water gathering action of the player.
     * 
     * @param board The main board, used to store the new rations.
     */
    public void playerSeeksWater(Board board) {
        int waterGot = Math.abs(board.getWeather());
        var gourd = getCardType(Gourd.class);
        if (gourd != null && gourd.isCardRevealed()) {
            waterGot *= 2;
        }
        board.getMainBoardFront().displayMessage(stringsBundle.getString("gotWater") + waterGot);
        board.addWater(waterGot);
        board.updateDisplayResources();
    }

    /**
     * The wood gathering action of the player.
     * 
     * @param board The main board, used to store the new rations.
     */
    public void playerSeeksWood(Board board, int nbTries) {

        var wood = 1;

        var axe = getCardType(Axe.class);
        if (axe != null && axe.isCardRevealed()) {
            wood++;
        }

        List<Integer> diceList = new ArrayList<>();
        for (var index = 0; index < 6; index++) {
            diceList.add(index);
        }
        Collections.shuffle(diceList);

        List<Integer> randomSeries = diceList.subList(0, nbTries);
        if (!randomSeries.contains(0)) {
            wood += nbTries;
        } else {
            board.getMainBoardFront().displayMessage(this + stringsBundle.getString("playerGotSick"));

            board.sickPlayer(this);
            sickRound = board.getRound();
        }
        board.getMainBoardFront().displayMessage(stringsBundle.getString("gotWood") + Math.abs(wood));

        board.addFragmentPlank(wood);
        board.updateDisplayResources();
    }

    /**
     * The card gathering action of the player.
     * 
     * @param board The main board, used to store the new rations.
     */
    public Card playerSeeksCard(Board board) {
        Card pickedCard;
        try {
            pickedCard = board.getDeck().remove(0);
        } catch (IndexOutOfBoundsException e) {
            return null;
        }
        board.getMainBoardFront().displayMessage(this + stringsBundle.getString("gotCard"));
        addCardToInventory(pickedCard);
        return pickedCard;
    }

    /**
     * Adds a card to the inventory and places it in the data displayer of the
     * player.
     * 
     * @param card The card to add
     */
    public void addCardToInventory(Card card) {
        inventory.add(card);
        if (card.isCardRevealed()) {
            addCardToRevealedPanel(card);
            inventoryRevealed.add(card);
        } else {
            addCardHiddenPanel();
            inventoryHidden.add(card);
        }
        card.setOwner(this);
    }

    /**
     * Removes a card from the inventory and from the data displayer.
     * 
     * @param card the card to remove
     * @return the card removed
     */
    public Card removeCard(Card card) {
        int index = inventory.indexOf(card);
        Card cardToReturn = null;
        if (index != -1) {
            cardToReturn = removeCard(index);
        }
        return cardToReturn;
    }

    /**
     * Removes a card from the inventory and from the data displayer.
     * 
     * @param index the index of the card to remove
     * @return the card removed
     */
    public Card removeCard(int index) {
        var card = inventory.get(index);
        inventory.remove(index);
        if (card.isCardRevealed()) {
            for (var indexInPanel = 0; indexInPanel < cardRevealedPanel.getComponentCount(); indexInPanel++) {
                var label = (JLabel) cardRevealedPanel.getComponent(indexInPanel);
                if (label.getText().equals(card.getCardName())) {
                    cardRevealedPanel.remove(indexInPanel);
                    inventoryRevealed.remove(card);
                }
            }
        } else if (cardHiddenPanel.getComponentCount() > 0) {
            cardHiddenPanel.remove(0);
            inventoryHidden.remove(card);
        }

        return card;
    }

    /**
     * Trades two cards between this player and an other one.
     * 
     * @param target     the other player concerned by this trade
     * @param cardToGive the card gived by this player to the other player
     * @param cardToGet  the card gived by the other player to this player
     */
    public void trade(Player target, Card cardToGive, Card cardToGet) {
        if (hasCard(cardToGive) && target != null && target.hasCard(cardToGet)) {
            target.removeCard(cardToGet);
            this.removeCard(cardToGive);
            target.addCardToInventory(cardToGive);
            addCardToInventory(cardToGet);
        }
    }

    /**
     * Discards a card, by sending it to the discard deck of the board and removing
     * this player from the owner attribute of the card.
     * 
     * @param card the card to discard.
     */
    public void discardCard(Card card) {
        int index = inventory.indexOf(card);
        removeCard(index);
        card.discard();

    }

    /**
     * Determines whether this player can use at least one card of their inventory.
     * 
     * @return a boolean indicating if one card can be used.
     */
    public boolean canUseCard() {
        var cardUsable = false;
        if (!inventory.isEmpty() && this.getState() == PlayerState.HEALTHY) {
            for (Card card : inventory) {
                cardUsable = cardUsable || card.canBeUsed();
            }

        }
        return cardUsable;
    }

    /**
     * Determines whether this player would like to use a card. If one card can be
     * used, the player may decide to use it.
     * 
     * @param board the main board
     * @return a boolean indicating if one card has been used.
     */
    public boolean wouldLikePlayCardAsCpu(Board board) {
        var cardUsed = false;
        if (canUseCard()) {
            var odds = random.nextInt(10);
            var index = random.nextInt(inventory.size());
            var card = inventory.get(index);
            if (inventory.get(index).canBeUsed() && odds == 0) {
                boolean[] neededParameters = card.getNeededParameters();
                if (Arrays.equals(neededParameters, new boolean[] { true, true, true, false })) {
                    List<Integer> pickedPlayers = new ArrayList<>();
                    int pickedIndex = -1;
                    for (var anotherIndex = 0; anotherIndex < 3; anotherIndex++) {
                        pickedIndex = random.nextInt(board.getPlayerList().size());
                        pickedPlayers.add(pickedIndex);
                    }
                    var player0 = board.getPlayerList().get(pickedPlayers.get(0));
                    var player1 = board.getPlayerList().get(pickedPlayers.get(1));
                    var player2 = board.getPlayerList().get(pickedPlayers.get(2));
                    card.useCard(player0, player1, player2, ActionType.NONE);
                } else if (Arrays.equals(neededParameters, new boolean[] { true, false, false, true })) {
                    var pickedPlayer = board.getPlayerList().get(random.nextInt(board.getPlayerList().size()));
                    var pickedActionIndex = random.nextInt(4);
                    ActionType pickedAction = ActionType.getLActionTypes()[pickedActionIndex];
                    card.useCard(pickedPlayer, null, null, pickedAction);
                } else if (Arrays.equals(neededParameters, new boolean[] { true, false, false, false })) {
                    var pickedPlayer = board.getPlayerList().get(random.nextInt(board.getPlayerList().size()));
                    card.useCard(pickedPlayer, null, null, ActionType.NONE);
                } else {
                    card.useCard(null, null, null, ActionType.NONE);
                }
                cardUsed = true;
            }
        }
        return cardUsed;
    }

    /**
     * Determines whether this player has an instance of one of the card class in
     * their inventory.
     * 
     * @param cardClass the class of the wanted card.
     * @return a boolean indicating if the player has a card of the wanted class.
     */
    public boolean hasCardType(Class<?> cardClass) {
        for (Card cardInInventory : inventory) {
            if (cardClass.isInstance(cardInInventory)) {
                return true;
            }
        }
        return false;
    }

    /**
     * Provides a card of a wanted class from this player's inventory.
     * 
     * @param cardClass the class of the wanted card
     * @return the card of the wanted class, will return if this player does not
     *         have any card of this class
     */
    public Card getCardType(Class<?> cardClass) {
        Card card = null;
        for (Card cardInInventory : inventory) {
            if (cardClass.isInstance(cardInInventory)) {
                card = cardInInventory;
            }
        }
        return card;
    }

    /**
     * Robs a random hidden card from this player.
     * 
     * @return the robbed card and removes it from the inventory of the player
     */
    public Card robRandomCard() {
        if (!inventoryHidden.isEmpty()) {
            var index = random.nextInt(inventory.size());
            var card = inventory.get(index);
            while (card.isCardRevealed()) {
                index = random.nextInt(inventory.size());
                card = inventory.get(index);
            }
            removeCard(index);
            return card;
        }
        return null;
    }

    /**
     * Adds a card to the hidden panel in the data displayer, to show to the other
     * players that this player has a card but has not revealed it yet.
     */
    private void addCardHiddenPanel() {
        var cardLabel = new JLabel(stringsBundle.getString("hidden_card_label"));
        cardHiddenPanel.add(cardLabel);
    }

    /**
     * Adds a card to the revealed panel in the data displayer, to show to the other
     * players that this player has a visible card.
     *
     * @param card the card revealed to add to the panel
     */
    private void addCardToRevealedPanel(Card card) {
        var cardLabel = new JLabel(card.toString());
        cardRevealedPanel.add(cardLabel);
    }

    /**
     * Reveals a card of the player. The card is removed from the hidden panel and
     * added to the revealed panel.
     * 
     * @param card the card to reveal
     */
    public void revealCard(Card card) {
        if (!inventoryRevealed.contains(card) && inventory.contains(card) && inventoryHidden.contains(card)) {

            if (cardHiddenPanel.getComponentCount() > 0) {
                cardHiddenPanel.remove(0);
                inventoryHidden.remove(card);
            }

            inventoryRevealed.add(card);
            addCardToRevealedPanel(card);

        }
    }

    /**
     * Removes the cards that have to be discarded after the death of the owner such
     * as {@link Club} for instance.
     */
    public void deathPurgeCards() {
        for (int index = inventory.size() - 1; index >= 0; index--) {
            var card = inventory.get(index);
            if (card.discardOnDeath()) {
                discardCard(card);
            }
        }
    }

    /**
     * Asks this player to vote for one of the player in the list given in
     * parameter.
     * 
     * @param pickablePlayers the list of the players whose can be selected by this
     *                        player
     * @return the selected player designated by this player
     */
    public Player voteAsCPU(List<Player> pickablePlayers) {
        var pickedIndex = random.nextInt(pickablePlayers.size());
        return pickablePlayers.get(pickedIndex);

    }

    /**
     * Asks this player to vote for one of the player in the list given in
     * parameter.
     * 
     * @param board           the main board
     * @param pickablePlayers the list of the players whose can be selected by this
     *                        player
     * @return the selected player designated by this player
     */
    public void vote(Board board, List<Player> pickablePlayers) {
        board.getMainBoardFront().displayMessage(stringsBundle.getString("decideWhoVote"));
        board.getMainBoardFront().makePlayerVoteFor(pickablePlayers);

    }

    /**
     * Asks this player to choose one player to be sacrificed without any discussion
     * or vote.
     * 
     * @param playerList the list of player who can be sacrificed
     * @return the player selected to be sacrificed
     */
    public Player decideWhoDieAsCPU(List<Player> playerList) {
        var player = playerList.get(random.nextInt(playerList.size()));
        while (player.getState() == PlayerState.DEAD) {
            player = playerList.get(random.nextInt(playerList.size()));
        }
        return player;
    }

    /**
     * The getter for the attribute {@link Player#isChief}.
     * 
     * @return a boolean indicating whether this player is the current chief
     */
    public boolean isPlayerChief() {
        return isChief;
    }

    public void setCurrentPlayer(boolean currentPlayer) {
        this.currentPlayer = currentPlayer;
        Color color = currentPlayer ? Color.RED : Color.BLACK;
        display.setBorder(BorderFactory.createLineBorder(color));

    }

    public boolean isCurrentPlayer() {
        return currentPlayer;
    }

    /**
     * The setter for the attribute {@link Player#isChief}.
     * 
     * @param playerChief a boolean indicating whether this player is the current
     *                    chief
     */
    public void setPlayerChief(boolean playerChief) {
        String text = playerChief ? stringsBundle.getString("chief_label") : stringsBundle.getString("not_chief_label");
        chiefLabel.setText(text);

    }

    /**
     * The getter for the attribute {@link Player#state}.
     * 
     * @return the state (healthy, sick or dead) of the player
     */
    public PlayerState getState() {
        return state;
    }

    /**
     * The setter for the attribute {@link Player#state}.
     * 
     * @param playerState the state (healthy, sick or dead) of the player
     */
    public void setState(PlayerState playerState) {
        state = playerState;
        stateLabel.setText(state.toString());
    }

    /**
     * The getter for the attribute {@link Player#name}.
     * 
     * @return the name of the player
     */
    public String getName() {
        return name;
    }

    /**
     * The getter for the attribute {@link Player#inventoryHidden}.
     * 
     * @return the list of hidden cards of the player
     */
    public List<Card> getInventoryHidden() {
        return inventoryHidden;
    }

    /**
     * The getter for the attribute {@link Player#inventoryRevealed}.
     * 
     * @return the list of revealed cards of the player
     */
    public List<Card> getInventoryRevealed() {
        return inventoryRevealed;
    }

    /**
     * The getter for the attribute {@link Player#inventory}.
     * 
     * @return the inventory of the player
     */
    public List<Card> getInventory() {
        return inventory;
    }

    /**
     * The getter for the attribute {@link Player#display}.
     * 
     * @return the data display panel
     */
    public JPanel getPanelDisplay() {
        return display;
    }

    /**
     * The getter for the attribute {@link Player#imposedActionThisRound}.
     * 
     * @return the action imposed this round
     */
    public ActionType getImposedActionThisRound() {
        return imposedActionThisRound;
    }

    /**
     * The setter for the attribute {@link Player#imposedActionThisRound}.
     * 
     * @param imposedActionThisRound the action imposed this round
     */
    public void setImposedActionThisRound(ActionType imposedActionThisRound) {
        this.imposedActionThisRound = imposedActionThisRound;
    }

    /**
     * Gives the card of a determined index from this player's inventory
     * 
     * @param index the index of the requested card
     * @return the requested card
     */
    public Card getCard(int index) {
        return inventory.get(index);
    }

    /**
     * Gives the number of card in the player's inventory
     * 
     * @return the number of cards
     */
    public int getCardNumber() {
        return inventory.size();
    }

    /**
     * Indicates whether this player as a card in their inventory.
     * 
     * @param card the card requested
     * @return a boolean indicating whether the player has the requested card
     */
    public boolean hasCard(Card card) {
        return inventory.contains(card);
    }

    /**
     * The getter for the attribute {@link Player#sickRound}.
     * 
     * @return the round during which the player got sick
     */
    public int getSickRound() {
        return sickRound;
    }

    /**
     * The setter for the attribute {@link Player#sickRound}.
     * 
     * @param roundSick the round during which the player got sick
     */
    public void setSickRound(int roundSick) {
        this.sickRound = roundSick;
    }

}
