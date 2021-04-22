package old;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.ScrollPaneConstants;

// import src.back.MainBoard;
import back.Player;
import back.cards.Card;
// import src.front.components.PlayerDisplay;

import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Random;
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import java.awt.Graphics;
import java.awt.CardLayout;
import java.awt.Image;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;

public class MainScreenGame extends JPanel {
    /*
     * private static final long serialVersionUID = -5327636955988161143L; public
     * static final String MAIN_SCREEN_GAME = "MAIN_SCREEN_GAME"; public static
     * final String EMPTY_CENTER = "EMPTY_CENTER"; public static final String
     * VOTE_CENTER = "VOTE_CENTER"; private int nbPlayer; private MainBoard board;
     * private int indexOfThisPlayer; private int indexCurrentPlayer; private int
     * indexChief; private JLabel roundDisplay; private JLabel weatherDisplay;
     * private JLabel foodStock; private JLabel waterStock; private JLabel
     * plankStock; private JLabel plankFragmentStock; private JPanel cardsGrid;
     * private JPanel westPanel; private JPanel southPanel; private JPanel
     * eastPanel; private JPanel centerPanel; private JPanel thisPlayerVotePanel;
     * private CardLayout cardLayoutCenter; private List<PlayerDisplay>
     * listPanelPlayers; private List<JButton> listActionButtonsPlayer; private
     * Random random; private MainFrame mainFrame; private JPanel summaryVotesPanel;
     * private HashMap<Integer, Integer> voteMap;
     * 
     * public MainScreenGame(MainFrame mainFrame) { super(); setLayout(new
     * BorderLayout()); setOpaque(false);
     * 
     * this.mainFrame = mainFrame;
     * 
     * // North JPanel northPanel = new JPanel(); northPanel.setLayout(new
     * GridLayout(1, 2)); add(northPanel, BorderLayout.NORTH);
     * northPanel.setOpaque(false); buildNorth(northPanel);
     * 
     * // Center centerPanel = new JPanel(); add(centerPanel, BorderLayout.CENTER);
     * centerPanel.setOpaque(false); buildCenter(centerPanel);
     * 
     * // South southPanel = new JPanel(); southPanel.setLayout(new GridLayout(1, 3,
     * 10, 10)); add(southPanel, BorderLayout.SOUTH); southPanel.setOpaque(false);
     * buildSouth(southPanel);
     * 
     * eastPanel = new JPanel(); eastPanel.setLayout(new GridLayout(0, 1, 10, 10));
     * add(eastPanel, BorderLayout.EAST); eastPanel.setOpaque(false);
     * 
     * westPanel = new JPanel(); westPanel.setLayout(new GridLayout(0, 1, 10, 10));
     * add(westPanel, BorderLayout.WEST); westPanel.setOpaque(false);
     * 
     * }
     * 
     * public void buildNorth(JPanel northPanel) {
     * 
     * JPanel stocksPanel = new JPanel(); stocksPanel.setOpaque(false);
     * stocksPanel.setLayout(new FlowLayout(FlowLayout.LEFT, 30, 20));
     * northPanel.add(stocksPanel);
     * 
     * JPanel foodPanel = new JPanel(); foodPanel.setOpaque(false);
     * foodPanel.setLayout(new FlowLayout(FlowLayout.LEFT, 10, 0)); JLabel
     * foodDisplay = new JLabel("Food : "); foodStock = new JLabel("0");
     * stocksPanel.add(foodPanel); foodPanel.add(foodDisplay);
     * foodPanel.add(foodStock); JPanel waterPanel = new JPanel();
     * waterPanel.setOpaque(false); waterPanel.setLayout(new
     * FlowLayout(FlowLayout.LEFT, 10, 0)); JLabel waterDisplay = new
     * JLabel("Water : "); waterStock = new JLabel("0");
     * stocksPanel.add(waterPanel); waterPanel.add(waterDisplay);
     * waterPanel.add(waterStock); JPanel plankPanel = new JPanel();
     * plankPanel.setOpaque(false); plankPanel.setLayout(new
     * FlowLayout(FlowLayout.LEFT, 10, 0)); JLabel plankDisplay = new
     * JLabel("Planks : "); plankStock = new JLabel("0");
     * stocksPanel.add(plankPanel); plankPanel.add(plankDisplay);
     * plankPanel.add(plankStock); JPanel plankFragmentPanel = new JPanel();
     * plankFragmentPanel.setOpaque(false); plankFragmentPanel.setLayout(new
     * FlowLayout(FlowLayout.LEFT, 10, 0)); JLabel plankFragmentDisplay = new
     * JLabel("Plank Fragments : "); plankFragmentStock = new JLabel("0");
     * stocksPanel.add(plankFragmentPanel);
     * plankFragmentPanel.add(plankFragmentDisplay);
     * plankFragmentPanel.add(plankFragmentStock);
     * 
     * JPanel otherDataPanel = new JPanel(); otherDataPanel.setOpaque(false);
     * otherDataPanel.setLayout(new FlowLayout(FlowLayout.RIGHT, 30, 20)); JPanel
     * roundPanel = new JPanel(); roundPanel.setOpaque(false);
     * roundPanel.setLayout(new FlowLayout(FlowLayout.RIGHT, 10, 0)); JLabel
     * roundTextDisplay = new JLabel("Round : "); roundDisplay = new JLabel("0");
     * 
     * JPanel weatherPanel = new JPanel(); weatherPanel.setOpaque(false);
     * weatherPanel.setLayout(new FlowLayout(FlowLayout.RIGHT, 10, 0)); JLabel
     * weatherTextDisplay = new JLabel("Weather : "); weatherDisplay = new
     * JLabel("-1");
     * 
     * northPanel.add(otherDataPanel); otherDataPanel.add(weatherPanel);
     * otherDataPanel.add(roundPanel); roundPanel.add(roundTextDisplay);
     * roundPanel.add(roundDisplay); weatherPanel.add(weatherTextDisplay);
     * weatherPanel.add(weatherDisplay); }
     * 
     * public void buildCenter(JPanel centerPanel) { JPanel votePanel = new
     * JPanel(); thisPlayerVotePanel = new JPanel();
     * thisPlayerVotePanel.setLayout(new GridLayout(2, 0, 10, 10));
     * summaryVotesPanel = new JPanel(); summaryVotesPanel.setLayout(new
     * GridLayout(2, 0, 10, 10)); votePanel.setLayout(new GridLayout(2, 1, 10, 10));
     * votePanel.add(summaryVotesPanel); votePanel.add(thisPlayerVotePanel);
     * 
     * JPanel emptyPanel = new JPanel();
     * 
     * cardLayoutCenter = new CardLayout(); centerPanel.setLayout(cardLayoutCenter);
     * centerPanel.add(emptyPanel, EMPTY_CENTER); centerPanel.add(votePanel,
     * VOTE_CENTER);
     * 
     * }
     * 
     * public void buildSouth(JPanel southPanel) { JPanel actionButtonsPanel = new
     * JPanel(); actionButtonsPanel.setOpaque(false);
     * actionButtonsPanel.setLayout(new FlowLayout(FlowLayout.LEFT, 30, 20));
     * southPanel.add(actionButtonsPanel); JButton foodButton = new JButton("Food");
     * foodButton.addActionListener(new FoodListener());
     * actionButtonsPanel.add(foodButton); JButton waterButton = new
     * JButton("Water"); waterButton.addActionListener(new WaterListener());
     * actionButtonsPanel.add(waterButton); JButton woodButton = new
     * JButton("Wood"); woodButton.addActionListener(new WoodListener());
     * actionButtonsPanel.add(woodButton); JButton cardButton = new JButton("Card");
     * cardButton.addActionListener(new CardListener());
     * actionButtonsPanel.add(cardButton); listActionButtonsPlayer = new
     * ArrayList<>(); listActionButtonsPlayer.add(foodButton);
     * listActionButtonsPlayer.add(waterButton);
     * listActionButtonsPlayer.add(woodButton);
     * listActionButtonsPlayer.add(cardButton);
     * 
     * PlayerDisplay otherPlayer = new PlayerDisplay();
     * otherPlayer.setOpaque(false); JPanel middleSouth = new JPanel();
     * middleSouth.setOpaque(false); middleSouth.add(otherPlayer);
     * southPanel.add(middleSouth);
     * 
     * JPanel cardsPanel = new JPanel(); cardsPanel.setOpaque(false);
     * cardsPanel.setLayout(new FlowLayout(FlowLayout.RIGHT, 30, 20));
     * southPanel.add(cardsPanel); JPanel cardsPanelPanel = new JPanel();
     * cardsPanelPanel.setOpaque(false); cardsPanelPanel.setLayout(new GridLayout(0,
     * 1, 0, 0)); cardsPanel.add(cardsPanelPanel); JLabel cardsLabel = new
     * JLabel("Cards"); JPanel cardsLabelPanel = new JPanel();
     * cardsLabelPanel.setOpaque(false); cardsLabelPanel.setLayout(new
     * FlowLayout(FlowLayout.CENTER, 0, 0)); cardsLabelPanel.add(cardsLabel); JPanel
     * cardsPanelScrollable = new JPanel(); cardsPanelScrollable.setOpaque(false);
     * JScrollPane scrollPane = new JScrollPane(cardsPanelScrollable,
     * ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED,
     * ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED);
     * scrollPane.setPreferredSize(new Dimension(500, 50));
     * scrollPane.setViewportView(cardsPanelScrollable); cardsGrid = new JPanel();
     * cardsGrid.setLayout(new GridLayout(1, 0, 10, 10));
     * cardsPanelScrollable.add(cardsGrid); cardsPanelPanel.add(cardsLabelPanel);
     * cardsPanelPanel.add(scrollPane);
     * 
     * }
     * 
     * public void buildEastWest(int nbWest, int nbEast) { for (int index = 0; index
     * < nbWest; index++) { PlayerDisplay pw1 = new PlayerDisplay();
     * pw1.setOpaque(false); westPanel.add(pw1); } for (int index = 0; index <
     * nbEast; index++) { PlayerDisplay pw2 = new PlayerDisplay();
     * pw2.setOpaque(false); eastPanel.add(pw2); } }
     * 
     * public void setNbPlayers(int nb) { nbPlayer = nb; }
     * 
     * public void setRound(int round) { roundDisplay.setText(round + ""); }
     * 
     * public void setWeather(int weather) { weatherDisplay.setText(weather + ""); }
     * 
     * public void initializeInterface(int food, int water, int weather) {
     * setWeather(weather); setWater(water); setFood(food); }
     * 
     * public void setWater(int water) { waterStock.setText(water + ""); }
     * 
     * public void setFood(int food) { foodStock.setText(food + ""); }
     * 
     * public void addCardToThisPlayer(Card card) { JPanel panel = new JPanel();
     * JLabel cardLabel = new JLabel(card.toString()); panel.add(cardLabel);
     * cardsGrid.add(panel); }
     * 
     * public void removeCard(int index) {
     * cardsGrid.remove(cardsGrid.getComponent(index)); }
     * 
     * public void setBoard(MainBoard mainBoard) { board = mainBoard; }
     * 
     * public void setOtherPlayers() { random = new Random(); int
     * positionOfThisPlayerInList = random.nextInt(nbPlayer); indexOfThisPlayer =
     * positionOfThisPlayerInList; int nbOfPlayersBeforeThisOne =
     * positionOfThisPlayerInList; int nbOfPlayersAfterThisOne = nbPlayer -
     * positionOfThisPlayerInList - 1;
     * 
     * listPanelPlayers = new ArrayList<>();
     * 
     * if (nbOfPlayersAfterThisOne <= 6 && nbOfPlayersBeforeThisOne <= 6) {
     * setPlayersBalanced(nbOfPlayersBeforeThisOne, nbOfPlayersAfterThisOne); } else
     * if (nbOfPlayersBeforeThisOne > 6) {
     * setPlayersBefore(nbOfPlayersBeforeThisOne, nbOfPlayersAfterThisOne); } else {
     * setPlayersAfter(nbOfPlayersBeforeThisOne, nbOfPlayersAfterThisOne); }
     * 
     * for (int index = 0; index < listPanelPlayers.size(); index++) { PlayerDisplay
     * panel = listPanelPlayers.get(index); panel.setNamePlayer("Player " + index);
     * } listPanelPlayers.get(indexOfThisPlayer).setNamePlayer("You");
     * listPanelPlayers.get(0).setChief(true);
     * listPanelPlayers.get(0).setCurrentPlayer(true); indexCurrentPlayer = 0;
     * showCards(); }
     * 
     * public void showCards() { for (int index = 0; index < nbPlayer; index++) {
     * Player player = board.getPlayerList().get(index); PlayerDisplay playerDisplay
     * = listPanelPlayers.get(index);
     * 
     * if (index == indexOfThisPlayer) { for (int indexCards = 0; indexCards <
     * player.getCardList().size(); indexCards++) {
     * addCardToThisPlayer(player.getCardList().get(indexCards)); } } else { for
     * (int indexCards = 0; indexCards < player.getCardList().size(); indexCards++)
     * { playerDisplay.addCardHidden(); } }
     * 
     * } playRound(); }
     * 
     * public void playRound() { Player currentPlayer =
     * board.getPlayerList().get(indexCurrentPlayer); if
     * (currentPlayer.isPlayerDead()) { System.out.println("Mmmh pas normal ça");
     * update(); nextPlayer(true);
     * 
     * } else if (currentPlayer.isPlayerSick() && currentPlayer.getRoundSickness()
     * == board.getRound() - 1) { currentPlayer.curePlayer(); update();
     * nextPlayer(true); } else if (indexCurrentPlayer != indexOfThisPlayer) { int
     * action = random.nextInt(4); switch (action) { case 0: foodAction(); break;
     * case 1: waterAction(); break; case 2: int nbTries = random.nextInt(6) + 1;
     * woodAction(currentPlayer, nbTries, indexCurrentPlayer); break; case 3:
     * cardAction(currentPlayer, indexCurrentPlayer); break; default: // Nothing }
     * 
     * update(); nextPlayer(true); } }
     * 
     * public void update() { foodStock.setText(board.getFood() + "");
     * waterStock.setText(board.getWater() + "");
     * plankStock.setText(board.getNbPlanks() + "");
     * plankFragmentStock.setText(board.getNbPlanksFragment() + ""); int nextIndex =
     * indexCurrentPlayer == nbPlayer - 1 ? 0 : indexCurrentPlayer + 1;
     * listPanelPlayers.get(nextIndex).setCurrentPlayer(true);
     * listPanelPlayers.get(indexCurrentPlayer).setCurrentPlayer(false); for (int
     * index = 0; index < listPanelPlayers.size(); index++) { PlayerDisplay
     * playerDisplay = listPanelPlayers.get(index); Player player =
     * board.getPlayerList().get(index);
     * playerDisplay.setState(player.isPlayerSick(), player.isPlayerDead()); } }
     * 
     * private boolean isRoundOverIncr() { boolean isOver = false; if
     * (indexCurrentPlayer == nbPlayer - 1) { indexCurrentPlayer =
     * (indexCurrentPlayer + 1) % nbPlayer; isOver = true;
     * 
     * } else { indexCurrentPlayer++; } return isOver; }
     * 
     * private boolean isRoundOver() { boolean isOver = false; if
     * (indexCurrentPlayer == nbPlayer - 1) { isOver = true; } return isOver; }
     * 
     * public void endGame() { List<String> playersAlive = new ArrayList<>(); for
     * (int index = 0; index < listPanelPlayers.size(); index++) { if
     * (!board.getPlayerList().get(index).isPlayerDead()) {
     * playersAlive.add(listPanelPlayers.get(index).getPlayerName()); } }
     * mainFrame.endGame(playersAlive); }
     * 
     * public void nextPlayer(boolean useIncrIndex) { boolean isOver = useIncrIndex
     * ? isRoundOverIncr() : isRoundOver(); update(); if (board.getPlayersAlive() ==
     * 0 || (board.getWeather() == -2 && isOver)) { endGame(); } else { Player
     * currentPlayer = board.getPlayerList().get(indexCurrentPlayer); if (isOver &&
     * !nextRound()) { update(); return; } update(); if (board.getPlayersAlive() ==
     * 0) { endGame(); } else if (currentPlayer.isPlayerDead()) { nextPlayer(true);
     * } else { playRound(); }
     * 
     * }
     * 
     * }
     * 
     * public boolean nextRound() { if (!board.nextRound(this)) { return false; }
     * indexChief++; indexChief %= nbPlayer;
     * weatherDisplay.setText(board.getWeather() + "");
     * roundDisplay.setText(board.getRound() + ""); for (int index = 0; index <
     * board.getPlayerList().size(); index++) {
     * board.getPlayerList().get(index).setPlayerChief(index == indexChief);
     * listPanelPlayers.get(index).setChief(index == indexChief); }
     * System.out.println("----------------------Round Over--------------------\n");
     * return true; }
     * 
     * public void foodAction() { board.seekFood(); }
     * 
     * public void waterAction() { board.seekWater(); }
     * 
     * public void woodAction(Player player, int nbTries, int indexPlayerDisplay) {
     * board.seekWood(player, nbTries); if (player.isPlayerSick()) {
     * listPanelPlayers.get(indexPlayerDisplay).setState(true, false); } }
     * 
     * public void cardAction(Player player, int playerIndex) { Card card =
     * board.getCard(player); if (playerIndex == indexOfThisPlayer && card != null)
     * { addCardToThisPlayer(card); } else if (card != null) {
     * listPanelPlayers.get(playerIndex).addCardHidden(); } }
     * 
     * public void switchToVote() { voteMap = new HashMap<>(); for (int index = 0;
     * index < board.getPlayerList().size(); index++) { if
     * (!board.getPlayerList().get(index).isPlayerDead()) { JButton button = new
     * JButton(listPanelPlayers.get(index).getPlayerName());
     * button.addActionListener(new VoteButton(index));
     * thisPlayerVotePanel.add(button); int votedInt =
     * random.nextInt(board.getPlayerList().size()); while
     * (board.getPlayerList().get(votedInt).isPlayerDead()) { votedInt =
     * random.nextInt(board.getPlayerList().size()); } voteMap.put(index, votedInt);
     * } } cardLayoutCenter.show(centerPanel, VOTE_CENTER); }
     * 
     * public void voteForKill() { switchToEmpty(); int[] votes = new
     * int[board.getPlayerList().size()]; for (Integer voteInt : voteMap.values()) {
     * votes[voteInt]++; }
     * 
     * int maxi = -1; int indexMaxi = -1; for (int index = 0; index < votes.length;
     * index++) { if (maxi < votes[index]) { maxi = votes[index]; indexMaxi = index;
     * } }
     * 
     * board.getPlayerList().get(indexMaxi).killPlayer(); nextPlayer(false); }
     * 
     * public void switchToEmpty() { thisPlayerVotePanel.removeAll();
     * cardLayoutCenter.show(centerPanel, EMPTY_CENTER); }
     * 
     * private void setPlayersBalanced(int nbOfPlayersBeforeThisOne, int
     * nbOfPlayersAfterThisOne) { for (int index = 0; index <
     * nbOfPlayersBeforeThisOne; index++) { PlayerDisplay player = new
     * PlayerDisplay(); eastPanel.add(player); listPanelPlayers.add(player); }
     * listPanelPlayers.add((PlayerDisplay) ((JPanel)
     * southPanel.getComponent(1)).getComponent(0)); for (int index = 0; index <
     * nbOfPlayersAfterThisOne; index++) { westPanel.add(new PlayerDisplay()); } for
     * (int index = westPanel.getComponentCount() - 1; index >= 0; index--) {
     * listPanelPlayers.add((PlayerDisplay) westPanel.getComponent(index)); } }
     * 
     * private void setPlayersBefore(int nbOfPlayersBeforeThisOne, int
     * nbOfPlayersAfterThisOne) { int nbBeforeFromWest = nbOfPlayersBeforeThisOne -
     * 6; for (int index = 0; index < nbBeforeFromWest; index++) { westPanel.add(new
     * PlayerDisplay()); } for (int index = nbBeforeFromWest - 1; index >= 0;
     * index--) { listPanelPlayers.add((PlayerDisplay)
     * westPanel.getComponent(index)); } westPanel.add(new JPanel()); for (int index
     * = 0; index < 6; index++) { PlayerDisplay player = new PlayerDisplay();
     * eastPanel.add(player); listPanelPlayers.add(player); }
     * listPanelPlayers.add((PlayerDisplay) ((JPanel)
     * southPanel.getComponent(1)).getComponent(0)); for (int index = 0; index <
     * nbOfPlayersAfterThisOne; index++) { westPanel.add(new PlayerDisplay()); } for
     * (int index = nbBeforeFromWest + 1; index < westPanel.getComponentCount();
     * index++) { listPanelPlayers.add((PlayerDisplay)
     * westPanel.getComponent(index)); } }
     * 
     * private void setPlayersAfter(int nbOfPlayersBeforeThisOne, int
     * nbOfPlayersAfterThisOne) { int nbFromEast = nbOfPlayersAfterThisOne - 6; for
     * (int index = 0; index < nbFromEast; index++) { eastPanel.add(new
     * PlayerDisplay()); } eastPanel.add(new JPanel()); for (int index = 0; index <
     * nbOfPlayersBeforeThisOne; index++) { eastPanel.add(new PlayerDisplay()); }
     * 
     * for (int index = 0; index < 6; index++) { westPanel.add(new PlayerDisplay());
     * }
     * 
     * for (int index = nbFromEast + 1; index < eastPanel.getComponentCount();
     * index++) { listPanelPlayers.add((PlayerDisplay)
     * eastPanel.getComponent(index)); } listPanelPlayers.add((PlayerDisplay)
     * ((JPanel) southPanel.getComponent(1)).getComponent(0)); for (int index = 5;
     * index >= 0; index--) { listPanelPlayers.add((PlayerDisplay)
     * westPanel.getComponent(index)); } for (int index = 0; index < nbFromEast;
     * index++) { listPanelPlayers.add((PlayerDisplay)
     * eastPanel.getComponent(index)); } }
     * 
     * private class FoodListener implements ActionListener {
     * 
     * @Override public void actionPerformed(ActionEvent e) { if (board.getWeather()
     * == -2) { for (JButton button : listActionButtonsPlayer) {
     * button.setEnabled(false); } } foodAction(); update(); nextPlayer(true); } }
     * 
     * private class WaterListener implements ActionListener {
     * 
     * @Override public void actionPerformed(ActionEvent e) { if (board.getWeather()
     * == -2) { for (JButton button : listActionButtonsPlayer) {
     * button.setEnabled(false); } } waterAction(); update(); nextPlayer(true); } }
     * 
     * private class WoodListener implements ActionListener {
     * 
     * @Override public void actionPerformed(ActionEvent e) { if (board.getWeather()
     * == -2) { for (JButton button : listActionButtonsPlayer) {
     * button.setEnabled(false); } }
     * woodAction(board.getPlayerList().get(indexOfThisPlayer), random.nextInt(6) +
     * 1, indexOfThisPlayer); update(); nextPlayer(true); } }
     * 
     * private class CardListener implements ActionListener {
     * 
     * @Override public void actionPerformed(ActionEvent e) { if (board.getWeather()
     * == -2) { for (JButton button : listActionButtonsPlayer) {
     * button.setEnabled(false); } }
     * cardAction(board.getPlayerList().get(indexOfThisPlayer), indexOfThisPlayer);
     * update(); nextPlayer(true); } }
     * 
     * private class VoteButton implements ActionListener { int indexButton;
     * 
     * public VoteButton(int index) { indexButton = index; }
     * 
     * @Override public void actionPerformed(ActionEvent e) {
     * voteMap.put(indexOfThisPlayer, indexButton); voteForKill(); } }
     * 
     * @Override public void paintComponent(Graphics g) { super.paintComponent(g);
     * Image icon = null; try { icon = ImageIO.read(new
     * File(System.getProperty("user.dir") + "/src/front/images/Peeper.png")); }
     * catch (IOException e) { e.printStackTrace(); } if (icon != null)
     * g.drawImage(icon, 0, 0, getWidth(), getHeight(), null); }
     */
}
