package back;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.ResourceBundle;
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
    private Random random = new Random();
    private boolean hasPlankForDeparture;
    private int sickRound;
    private transient ResourceBundle stringsBundle;

    public Player(String name, ResourceBundle stringsBundle) {
        this.name = name;
        inventory = new ArrayList<>();
        inventoryHidden = new ArrayList<>();
        inventoryRevealed = new ArrayList<>();
        state = PlayerState.HEALTHY;
        this.stringsBundle = stringsBundle;
        buildDisplay();

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

        chiefLabel = new JLabel(stringsBundle.getString("not_chief_label"));
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
        card.setOwner(this);
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

    public void trade(Player target, Card cardToGive, Card cardToGet) {
        target.removeCard(cardToGet);
        this.removeCard(cardToGive);
        giveToPlayer(target, cardToGive);
        giveToPlayer(this, cardToGet);
    }

    public void giveToPlayer(Player target, Card card) {
        card.setOwner(target);
        target.addCardToInventory(card);
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

    public boolean wouldLikePlayCard() {
        // Here the code called each time someone can use a card
        boolean cardUsed = false;
        if (canUseCard()) {
            int odds = random.nextInt(10);
            int index = random.nextInt(inventory.size());
            Card card = inventory.get(index);
            if (inventory.get(index).canBeUsed() && odds == 0) {
                System.out.println(this + " uses the card " + card);
                card.useCard(this, null, null, "");
                cardUsed = true;
            }

            // System.out.println("- - - - - - - - - - - - - - - - - - - - - - - - - - - - -
            // - -");
        }
        return cardUsed;
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
        JLabel cardLabel = new JLabel(stringsBundle.getString("hidden_card_label"));
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
            chiefLabel.setText(stringsBundle.getString("chief_label"));
        } else {
            chiefLabel.setText(stringsBundle.getString("not_chief_label"));
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
