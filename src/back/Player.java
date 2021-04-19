package back;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.awt.FlowLayout;
import java.io.Serializable;

import javax.swing.JLabel;
import javax.swing.JPanel;

import back.cards.Card;
import back.cards.Cartridge;
import back.cards.Matches;
import back.cards.MetalSheet;

public class Player implements Serializable {

    private static final long serialVersionUID = 2874539910717357461L;
    private static final String NOT_CHIEF = "     ";
    private static final String CHIEF = "Chief";
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
    private boolean hasBeenNourished;
    private boolean hasBeenWatered;
    private boolean hasBeenNourishedForDeparture;
    private boolean hasBeenWateredForDeparture;
    private Random random = new Random();
    private boolean hasPlankForDeparture;
    private int sickRound;

    public Player(String name) {
        this.name = name;
        inventory = new ArrayList<>();
        inventoryHidden = new ArrayList<>();
        inventoryRevealed = new ArrayList<>();
        state = PlayerState.HEALTHY;
        buildDisplay();
        hasBeenNourished = false;
        hasBeenWatered = false;

    }

    @Override
    public String toString() {
        return name;
    }

    private void buildDisplay() {
        display = new JPanel();
        display.setLayout(new FlowLayout(FlowLayout.CENTER, 10, 10));
        JLabel nameLabel = new JLabel(name);
        display.add(nameLabel);

        stateLabel = new JLabel(state.toString());
        display.add(stateLabel);

        chiefLabel = new JLabel(NOT_CHIEF);
        display.add(chiefLabel);

        cardRevealedPanel = new JPanel();
        display.add(cardRevealedPanel);

        cardHiddenPanel = new JPanel();
        display.add(cardHiddenPanel);
    }

    public int playAsCPUFood(Board board) {
        int food = board.seekFood();
        board.addFood(food);
        return food;
    }

    public int playAsCPUWater(Board board) {
        int water = board.seekWater();
        board.addWater(water);
        return water;
    }

    public int playAsCPUWood(Board board) {
        int nbTries = random.nextInt(6) + 1;
        int wood = board.seekWood(nbTries);
        if (wood == 0) {
            setState(PlayerState.SICK);
            sickRound = board.getRound();
        }
        board.addFragmentPlank(wood);
        return wood;
    }

    public Card playAsCPUCard(Board board) {
        Card card = board.seekCard();
        addCardToInventory(card);
        return card;
    }

    public void addCardToInventory(Card card) {
        inventory.add(card);
        if (card.isCardRevealed()) {
            addCardToRevealedPanel(card);
            inventoryRevealed.add(card);
        } else {
            addCardHiddenPanel();
            inventoryHidden.add(card);
        }
    }

    public Card removeCard(Card card) {
        int index = inventory.indexOf(card);
        return removeCard(index);
    }

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

    public void trade(Player playerTarget, Card cardToGive) {
        // TODO
    }

    public void giveToPlayer(Player target, Card card) {
        // TODO
    }

    public void discardCard(Card card) {
        int index = inventory.indexOf(card);
        card.discard();
        removeCard(index);
    }

    public boolean canUseCard() {
        boolean cardUsable = !inventory.isEmpty() && this.getState() == PlayerState.HEALTHY;
        for (Card card : inventory) {
            cardUsable = cardUsable || card.canBeUsed();
        }
        return cardUsable;
    }

    public void wouldLikePlayCard() {
        // Here the code called each time someone can use a card
        if (canUseCard()) {
            for (Card card : inventory) {
                if (card.canBeUsed()) {
                    // System.out.println(card + " can be used !");
                }
                // System.out.println("");
            }
            // System.out.println("- - - - - - - - - - - - - - - - - - - - - - - - - - - - -
            // - -");
        }
    }

    public Card getMetalSheet() {
        Card card = null;
        for (Card cardInInventory : inventory) {
            if (cardInInventory instanceof MetalSheet) {
                card = cardInInventory;
            }
        }
        return card;
    }

    public Card getMatches() {
        Card card = null;
        for (Card cardInInventory : inventory) {
            if (cardInInventory instanceof Matches) {
                card = cardInInventory;
            }
        }
        return card;
    }

    public Card getCartridge() {
        Card card = null;
        for (Card cardInInventory : inventory) {
            if (cardInInventory instanceof Cartridge) {
                card = cardInInventory;
            }
        }
        return card;
    }

    public Card robRandomCard() {
        int index = random.nextInt(inventory.size());
        Card card = inventory.get(index);
        removeCard(index);
        return card;
    }

    private void addCardHiddenPanel() {
        JLabel cardLabel = new JLabel("Card !");
        cardHiddenPanel.add(cardLabel);
    }

    private void addCardToRevealedPanel(Card card) {
        JLabel cardLabel = new JLabel(card.toString());
        cardRevealedPanel.add(cardLabel);
    }

    public void revealCard(Card card) {
        if (!inventoryRevealed.contains(card) && inventory.contains(card) && inventoryHidden.contains(card)) {
            inventoryHidden.remove(card);
            inventoryRevealed.add(card);
            addCardToRevealedPanel(card);
        }
    }

    public void deathPurge() {
        for (int index = inventory.size() - 1; index > 0; index--) {
            if (inventory.get(index).discardOnDeath()) {
                inventory.get(index).discard();
            }
        }
    }

    public boolean isPlayerChief() {
        return isChief;
    }

    public void setPlayerChief(boolean playerChief) {
        isChief = playerChief;
        if (isChief) {
            chiefLabel.setText(CHIEF);
        } else {
            chiefLabel.setText(NOT_CHIEF);
        }
    }

    public PlayerState getState() {
        return state;
    }

    public void setState(PlayerState playerState) {
        state = playerState;
        stateLabel.setText(state.toString());
    }

    public String getName() {
        return name;
    }

    public JPanel getPanelDisplay() {
        return display;
    }

    public Card getCard(int index) {
        return inventory.get(index);
    }

    public int getCardNumber() {
        return inventory.size();
    }

    public boolean hasCard(Card card) {
        return inventory.contains(card);
    }

    public boolean isNourished() {
        return hasBeenNourished;
    }

    public void setNourished(boolean food) {
        hasBeenNourished = food;
    }

    public boolean isWatered() {
        return hasBeenWatered;
    }

    public void setWatered(boolean water) {
        hasBeenWatered = water;
    }

    public boolean isNourishedForDeparture() {
        return hasBeenNourishedForDeparture;
    }

    public void setNourishedForDeparture(boolean food) {
        hasBeenNourishedForDeparture = food;
    }

    public boolean isWateredForDeparture() {
        return hasBeenWateredForDeparture;
    }

    public void setWateredForDeparture(boolean water) {
        hasBeenWateredForDeparture = water;
    }

    public boolean hasPlank() {
        return hasPlankForDeparture;
    }

    public void setPlankForDeparture(boolean plank) {
        hasPlankForDeparture = plank;
    }

    public int getRoundSick() {
        return sickRound;
    }

}
