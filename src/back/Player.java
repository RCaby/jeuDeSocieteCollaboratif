package back;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;
import java.util.ResourceBundle;
import java.awt.FlowLayout;
import java.io.Serializable;

import javax.swing.JLabel;
import javax.swing.JPanel;

import back.cards.Card;

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
    private JLabel chiefLabel;
    private JLabel stateLabel;
    private JPanel cardRevealedPanel;
    private JPanel cardHiddenPanel;
    private ActionType imposedActionThisRound;
    private Random random = new Random();
    private boolean hasPlankForDeparture;
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
        display = new JPanel();
        display.setLayout(new FlowLayout(FlowLayout.CENTER, 10, 10));
        JLabel nameLabel = new JLabel(name);
        display.add(nameLabel);

        stateLabel = new JLabel(state.toString());
        display.add(stateLabel);

        chiefLabel = new JLabel(stringsBundle.getString("not_chief_label"));
        display.add(chiefLabel);

        cardRevealedPanel = new JPanel();
        display.add(cardRevealedPanel);

        cardHiddenPanel = new JPanel();
        display.add(cardHiddenPanel);
    }

    /**
     * The food gathering action of the player.
     * 
     * @param board The main board, used to store the new rations.
     */
    public void playAsCPUFood(Board board) {
        int food = board.seekFood(this);
        board.addFood(food);
    }

    /**
     * The water gathering action of the player.
     * 
     * @param board The main board, used to store the new rations.
     */
    public void playAsCPUWater(Board board) {
        int water = board.seekWater(this);
        board.addWater(water);
    }

    /**
     * The wood gathering action of the player.
     * 
     * @param board The main board, used to store the new rations.
     */
    public void playAsCPUWood(Board board) {
        int nbTries = random.nextInt(6) + 1;
        int wood = board.seekWood(nbTries, this);
        if (wood < 0) {
            setState(PlayerState.SICK);
            sickRound = board.getRound();
        }
        board.addFragmentPlank(Math.abs(wood));
    }

    /**
     * The card gathering action of the player.
     * 
     * @param board The main board, used to store the new rations.
     */
    public Card playAsCPUCard(Board board) {
        Card card = board.seekCard();
        addCardToInventory(card);
        return card;
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
        Card card = inventory.get(index);
        inventory.remove(index);
        if (card.isCardRevealed()) {
            for (int indexInPanel = 0; indexInPanel < cardRevealedPanel.getComponentCount(); indexInPanel++) {
                if (((JLabel) cardRevealedPanel.getComponent(indexInPanel)).getText().equals(card.getCardName())) {
                    cardRevealedPanel.remove(indexInPanel);
                    inventoryRevealed.remove(card);
                }
            }
        } else {
            for (int indexInPanel = 0; indexInPanel < cardHiddenPanel.getComponentCount(); indexInPanel++) {
                if (((JLabel) cardHiddenPanel.getComponent(indexInPanel)).getText().equals(card.getCardName())) {
                    cardHiddenPanel.remove(indexInPanel);
                    inventoryHidden.remove(card);
                }
            }
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
        card.discard();
        removeCard(index);
    }

    /**
     * Determines whether this player can use at least one card of their inventory.
     * 
     * @return a boolean indicating if one card can be used.
     */
    public boolean canUseCard() {
        boolean cardUsable = false;
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
     * @return a boolean indicating if one card has been used.
     */
    public boolean wouldLikePlayCardAsCpu(Board board) {

        boolean cardUsed = false;
        if (canUseCard()) {
            int odds = random.nextInt(10);
            int index = random.nextInt(inventory.size());
            Card card = inventory.get(index);
            if (inventory.get(index).canBeUsed() && odds == 0) {
                boolean[] neededParameters = card.getNeededParameters();
                if (Arrays.equals(neededParameters, new boolean[] { true, true, true, false })) {
                    List<Integer> pickedPlayers = new ArrayList<>();
                    int pickedIndex = -1;
                    for (int anotherIndex = 0; anotherIndex < 3; anotherIndex++) {
                        pickedIndex = random.nextInt(board.getPlayerList().size());
                        pickedPlayers.add(pickedIndex);
                    }
                    Player player0 = board.getPlayerList().get(pickedPlayers.get(0));
                    Player player1 = board.getPlayerList().get(pickedPlayers.get(1));
                    Player player2 = board.getPlayerList().get(pickedPlayers.get(2));
                    card.useCard(player0, player1, player2, ActionType.NONE);
                } else if (Arrays.equals(neededParameters, new boolean[] { true, false, false, true })) {
                    Player pickedPlayer = board.getPlayerList().get(random.nextInt(board.getPlayerList().size()));
                    int pickedActionIndex = random.nextInt(4);
                    ActionType pickedAction = ActionType.getLActionTypes()[pickedActionIndex];
                    card.useCard(pickedPlayer, null, null, pickedAction);
                } else if (Arrays.equals(neededParameters, new boolean[] { true, false, false, false })) {
                    Player pickedPlayer = board.getPlayerList().get(random.nextInt(board.getPlayerList().size()));
                    card.useCard(pickedPlayer, null, null, ActionType.NONE);
                } else {
                    card.useCard(null, null, null, ActionType.NONE);
                }
                cardUsed = true;
            }
        }
        return cardUsed;
    }

    public boolean wouldLikePlayCard(Board board) {
        System.out.println(stringsBundle.getString("cardsDisplay") + inventory);
        System.out.println(stringsBundle.getString("wouldLikePlayCard?"));
        boolean wouldPlayerPlayACard = board.getYesNoAnswer();
        if (wouldPlayerPlayACard) {
            System.out.println(stringsBundle.getString("chooseACard"));
            int pickedCardIndex = board.getUserIntChoice(0, inventory.size() - 1);
            Card cardChoosed = inventory.get(pickedCardIndex);
            System.out.println(stringsBundle.getString("choiceResult") + cardChoosed);
            if (cardChoosed.canBeUsed()) {
                boolean[] neededParameters = cardChoosed.getNeededParameters();
                if (Arrays.equals(neededParameters, new boolean[] { true, true, true, false })) {
                    List<Integer> pickedPlayers = board.getUserPlayerChoice(3);
                    Player player0 = board.getPlayerList().get(pickedPlayers.get(0));
                    Player player1 = board.getPlayerList().get(pickedPlayers.get(1));
                    Player player2 = board.getPlayerList().get(pickedPlayers.get(2));
                    cardChoosed.useCard(player0, player1, player2, ActionType.NONE);
                } else if (Arrays.equals(neededParameters, new boolean[] { true, false, false, true })) {
                    List<Integer> pickedPlayers = board.getUserPlayerChoice(1);
                    System.out.println(stringsBundle.getString("chooseActionList") + ActionType.getLActionTypes());
                    int pickedActionIndex = board.getUserIntChoice(0, 3);
                    ActionType pickedAction = ActionType.getLActionTypes()[pickedActionIndex];
                    Player player0 = board.getPlayerList().get(pickedPlayers.get(0));
                    cardChoosed.useCard(player0, null, null, pickedAction);
                } else if (Arrays.equals(neededParameters, new boolean[] { true, false, false, false })) {
                    List<Integer> pickedPlayers = board.getUserPlayerChoice(1);
                    Player player0 = board.getPlayerList().get(pickedPlayers.get(0));
                    cardChoosed.useCard(player0, null, null, ActionType.NONE);
                } else {
                    cardChoosed.useCard(null, null, null, ActionType.NONE);
                }

            } else {
                System.out.println(stringsBundle.getString("cannotPlayCard"));
            }

        }
        return false;
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
     * Robs a random card from this player.
     * 
     * @return the robbed card and removes it from the inventory of the player
     */
    public Card robRandomCard() {
        int index = random.nextInt(inventory.size());
        Card card = inventory.get(index);
        removeCard(index);
        return card;
    }

    /**
     * Adds a card to the hidden panel in the data displayer, to show to the other
     * players that this player has a card but has not revealed it yet.
     */
    private void addCardHiddenPanel() {
        JLabel cardLabel = new JLabel(stringsBundle.getString("hidden_card_label"));
        cardHiddenPanel.add(cardLabel);
    }

    /**
     * Adds a card to the revealed panel in the data displayer, to show to the other
     * players that this player has a visible card.
     *
     * @param card the card revealed to add to the panel
     */
    private void addCardToRevealedPanel(Card card) {
        JLabel cardLabel = new JLabel(card.toString());
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
            inventoryHidden.remove(card);
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
            if (inventory.get(index).discardOnDeath()) {
                discardCard(inventory.get(index));
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
    public Player vote(List<Player> pickablePlayers) {
        int pickedIndex = random.nextInt(pickablePlayers.size());
        System.out.println(this + " votes for " + pickablePlayers.get(pickedIndex));
        return pickablePlayers.get(pickedIndex);
    }

    /**
     * Asks this player to choose one player to be sacrificed without any discussion
     * or vote.
     * 
     * @param playerList the list of player who can be sacrificed
     * @return the player selected to be sacrificed
     */
    public Player decideWhoDie(List<Player> playerList) {
        Player player = playerList.get(random.nextInt(playerList.size()));
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

    /**
     * The setter for the attribute {@link Player#isChief}.
     * 
     * @param playerChief a boolean indicating whether this player is the current
     *                    chief
     */
    public void setPlayerChief(boolean playerChief) {
        isChief = playerChief;
        if (isChief) {
            chiefLabel.setText(stringsBundle.getString("chief_label"));
        } else {
            chiefLabel.setText(stringsBundle.getString("not_chief_label"));
        }
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
    public int getRoundSick() {
        return sickRound;
    }

}
