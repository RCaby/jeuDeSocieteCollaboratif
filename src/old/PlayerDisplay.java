package src.old;

import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JPanel;

import src.back.cards.Card;

import java.awt.Color;
import java.awt.FlowLayout;

public class PlayerDisplay extends JPanel {
    /*
     * JLabel name; JPanel cardsRevealed; JPanel cardsHidden; JPanel state; JPanel
     * isChief;
     * 
     * public PlayerDisplay() { super(); setLayout(new FlowLayout(FlowLayout.LEFT,
     * 10, 10)); setOpaque(false); name = new JLabel(""); add(name); cardsRevealed =
     * new JPanel(); cardsRevealed.setOpaque(false); add(cardsRevealed); cardsHidden
     * = new JPanel(); cardsHidden.setOpaque(false); add(cardsHidden); state = new
     * JPanel(); state.setOpaque(false); add(state); isChief = new JPanel();
     * isChief.setOpaque(false); add(isChief);
     * 
     * }
     * 
     * public void setNamePlayer(String playerName) { name.setText(playerName); }
     * 
     * public String getPlayerName() { return name.getText(); }
     * 
     * public void addCardRevealed(Card card) { JLabel cardLabel = new
     * JLabel(card.toString()); cardsRevealed.add(cardLabel); }
     * 
     * public void removeCardRevealed(int index) {
     * cardsRevealed.remove(cardsRevealed.getComponent(index)); }
     * 
     * public void addCardHidden() { JLabel cardLabel = new JLabel("Card" +
     * cardsHidden.getComponentCount()); cardsHidden.add(cardLabel); }
     * 
     * public void removeCardHidden(int index) {
     * cardsHidden.remove(cardsHidden.getComponent(index)); }
     * 
     * public void setState(boolean isSick, boolean isDead) { if
     * (state.getComponentCount() == 0 && isDead) { state.add(new JLabel("Dead !"));
     * } else if (state.getComponentCount() > 0 && !isSick && !isDead) {
     * state.removeAll(); } else if (state.getComponentCount() == 0 && isSick) {
     * state.add(new JLabel("Sick !")); } }
     * 
     * public void setChief(boolean isTheChief) { if (isTheChief &&
     * isChief.getComponentCount() == 0) { isChief.add(new JLabel("Chief !")); }
     * else if (!isTheChief && isChief.getComponentCount() != 0) {
     * isChief.removeAll(); }
     * 
     * }
     * 
     * public void setCurrentPlayer(boolean isCurrentPlayer) { if (isCurrentPlayer)
     * { setBorder(BorderFactory.createLineBorder(Color.RED)); } else {
     * setBorder(null); } }
     */
}
