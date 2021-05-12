package front;

import static javax.swing.ScrollPaneConstants.HORIZONTAL_SCROLLBAR_ALWAYS;
import static javax.swing.ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.CardLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextPane;

import back.Board;
import back.Player;
import back.cards.Card;

public class MainBoardFront implements Serializable {
    JPanel mainPanel;
    Board board;
    int nbPlayers;
    int indexOfThisPlayer;
    private JPanel northPanel;
    private JPanel westPanel;
    private JPanel centerPanel;
    private JPanel eastPanel;
    private JPanel southPanel;
    private JLabel foodQuantityLabel;
    private JLabel waterQuantityLabel;
    private JLabel woodFragmentsQuantityLabel;
    private JLabel weatherLabelNumber;
    private JLabel roundLabelNumber;
    private JLabel woodPlanksQuantityLabel;
    private JLabel nbAliveLabelNumber;
    private List<JPanel> listPlayerDisplays;
    private JPanel hiddenCardPanel;
    private JPanel revealedCardPanel;
    private JPanel hiddenCardPanelPanel;
    private JPanel revealedCardPanelPanel;
    private CardLayout cardLayoutCentralPanel;
    private JPanel chooseActionPanel;
    private JPanel notificationPanel;
    private JPanel choosePlayerPanel;
    private static final String CHOOSE_ACTION_PANEL = "CHOOSE_ACTION_PANEL";
    private static final String CHOOSE_PLAYER_PANEL = "CHOOSE_PLAYER_PANEL";
    private static final String CHOOSE_VOID_PANEL = "CHOOSE_VOID_PANEL";
    private static final String CHOOSE_WOOD_TRIES_PANEL = "CHOOSE_WOOD_TRIES_PANEL";
    private JButton foodButtonAction;
    private JButton waterButtonAction;
    private JButton woodButtonAction;
    private JButton cardButtonAction;
    private JPanel choosePlayerPanelPanel;
    private JTextPane notificationPanelTextPane;
    private JPanel centerPanelCenter;
    private JPanel centerPanelSouth;
    private JLabel notificationLabel;
    private JButton nextButton;
    private JScrollPane notificationPanelTextPaneScrollable;
    private JPanel centerPanelCenterNotificationPanel;
    private JPanel centerPanelCenterChoicePanel;
    private JButton beginVoteButton;
    private JButton allowKillNotForDeparture;
    private JButton allowKillForDeparture;
    private JPanel chooseWoodNbTriesPanel;

    public MainBoardFront(int nbPlayers) {
        mainPanel = new JPanel();
        mainPanel.setLayout(new BorderLayout());
        this.nbPlayers = nbPlayers;

        buildEastPanel();
        buildWestPanel();
        buildSouthPanel();
        buildCenterPanel();
        buildNorthPanel();

    }

    public void buildWestPanel() {
        westPanel = new JPanel(new GridLayout(nbPlayers / 2, 1));
        mainPanel.add(westPanel, BorderLayout.WEST);

    }

    public void buildCenterPanel() {
        centerPanel = new JPanel(new BorderLayout());
        centerPanelCenter = new JPanel(new GridLayout(2, 1));
        centerPanelCenterNotificationPanel = new JPanel();
        centerPanelCenterChoicePanel = new JPanel();
        centerPanelCenter.add(centerPanelCenterNotificationPanel);
        centerPanelCenter.add(centerPanelCenterChoicePanel);

        centerPanelSouth = new JPanel();
        centerPanel.add(centerPanelCenter, BorderLayout.CENTER);
        centerPanel.add(centerPanelSouth, BorderLayout.SOUTH);
        centerPanelSouth.setLayout(new FlowLayout(FlowLayout.CENTER, 10, 10));
        cardLayoutCentralPanel = new CardLayout();
        mainPanel.add(centerPanel, BorderLayout.CENTER);
        centerPanelCenterChoicePanel.setLayout(cardLayoutCentralPanel);
        chooseActionPanel = new JPanel();
        chooseWoodNbTriesPanel = new JPanel();
        notificationPanel = new JPanel();
        choosePlayerPanel = new JPanel();
        centerPanelCenterNotificationPanel.add(notificationPanel);
        JPanel voidChoicePanel = new JPanel();
        centerPanelCenterChoicePanel.add(voidChoicePanel, CHOOSE_VOID_PANEL);
        centerPanelCenterChoicePanel.add(chooseActionPanel, CHOOSE_ACTION_PANEL);
        centerPanelCenterChoicePanel.add(choosePlayerPanel, CHOOSE_PLAYER_PANEL);
        centerPanelCenterChoicePanel.add(chooseWoodNbTriesPanel, CHOOSE_WOOD_TRIES_PANEL);

        chooseActionPanel.setLayout(new BoxLayout(chooseActionPanel, BoxLayout.Y_AXIS));
        notificationPanel.setLayout(new BoxLayout(notificationPanel, BoxLayout.Y_AXIS));
        choosePlayerPanel.setLayout(new BoxLayout(choosePlayerPanel, BoxLayout.Y_AXIS));
        chooseWoodNbTriesPanel.setLayout(new BoxLayout(chooseWoodNbTriesPanel, BoxLayout.Y_AXIS));

        JPanel chooseActionLabelPanel = new JPanel();
        JLabel chooseActionLabel = new JLabel("Choose your action");
        chooseActionLabelPanel.add(chooseActionLabel);
        JPanel notificationLabelPanel = new JPanel();
        notificationLabel = new JLabel("Notification !");
        notificationLabelPanel.add(notificationLabel);
        JPanel choosePlayerLabelPanel = new JPanel();
        JLabel choosePlayerLabel = new JLabel("Choose a player");
        choosePlayerLabelPanel.add(choosePlayerLabel);
        JPanel chooseWoodNbTriesLabelPanel = new JPanel();
        JLabel chooseWoodNbTriesLabel = new JLabel("Choose your number of tries");
        chooseWoodNbTriesLabelPanel.add(chooseWoodNbTriesLabel);

        chooseActionPanel.add(chooseActionLabelPanel);
        notificationPanel.add(notificationLabelPanel);
        choosePlayerPanel.add(choosePlayerLabelPanel);
        chooseWoodNbTriesPanel.add(chooseWoodNbTriesLabelPanel);

        JPanel chooseActionPanelPanel = new JPanel();
        JPanel notificationPanelPanel = new JPanel();
        choosePlayerPanelPanel = new JPanel();
        JPanel chooseWoodNbTriesPanelPanel = new JPanel();
        chooseActionPanel.add(chooseActionPanelPanel);
        notificationPanel.add(notificationPanelPanel);
        choosePlayerPanel.add(choosePlayerPanelPanel);
        chooseWoodNbTriesPanel.add(chooseWoodNbTriesPanelPanel);

        chooseActionPanelPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 10, 10));
        notificationPanelPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 10, 10));
        choosePlayerPanelPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 10, 10));
        chooseWoodNbTriesPanelPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 10, 10));

        foodButtonAction = new JButton("Food");
        foodButtonAction.addActionListener(new FoodActionListener());
        waterButtonAction = new JButton("Water");
        waterButtonAction.addActionListener(new WaterActionListener());
        woodButtonAction = new JButton("Wood");
        woodButtonAction.addActionListener(new WoodActionListener());
        cardButtonAction = new JButton("Card");
        cardButtonAction.addActionListener(new CardActionListener());
        chooseActionPanelPanel.add(foodButtonAction);
        chooseActionPanelPanel.add(waterButtonAction);
        chooseActionPanelPanel.add(woodButtonAction);
        chooseActionPanelPanel.add(cardButtonAction);

        notificationPanelTextPane = new JTextPane();
        changePolice(notificationPanelTextPane, 16);
        notificationPanelTextPaneScrollable = new JScrollPane(notificationPanelTextPane);

        notificationPanelTextPaneScrollable.setHorizontalScrollBarPolicy(HORIZONTAL_SCROLLBAR_ALWAYS);
        notificationPanelTextPaneScrollable.setVerticalScrollBarPolicy(VERTICAL_SCROLLBAR_ALWAYS);

        notificationPanelTextPane.setEditable(false);
        notificationPanelTextPaneScrollable.setPreferredSize(new Dimension(800, 250));
        notificationPanelPanel.add(notificationPanelTextPaneScrollable);

        nextButton = new JButton("Next");
        nextButton.addActionListener(new NextActionListener());
        centerPanelSouth.add(nextButton);
        beginVoteButton = new JButton("Begin vote");
        beginVoteButton.addActionListener(new BeginVoteListener());
        centerPanelSouth.add(beginVoteButton);
        allowKillNotForDeparture = new JButton("Confirm designated player");
        allowKillNotForDeparture.addActionListener(new AllowKillListener(false));
        centerPanelSouth.add(allowKillNotForDeparture);
        allowKillForDeparture = new JButton("Confirm designated player");
        allowKillForDeparture.addActionListener(new AllowKillListener(true));
        centerPanelSouth.add(allowKillForDeparture);
        beginVoteButton.setVisible(false);
        allowKillNotForDeparture.setVisible(false);
        allowKillForDeparture.setVisible(false);

        for (int index = 0; index < 7; index++) {
            JButton woodButton = new JButton(index + "");
            chooseWoodNbTriesPanelPanel.add(woodButton);
            woodButton.addActionListener(new WoodTryListener(index));
        }

    }

    public void buildEastPanel() {
        eastPanel = new JPanel(new GridLayout(nbPlayers / 2, 1));
        mainPanel.add(eastPanel, BorderLayout.EAST);
    }

    public void buildSouthPanel() {
        southPanel = new JPanel(new GridLayout(1, 2));
        mainPanel.add(southPanel, BorderLayout.SOUTH);
        hiddenCardPanel = new JPanel();
        revealedCardPanel = new JPanel();
        southPanel.add(hiddenCardPanel);
        southPanel.add(revealedCardPanel);
        hiddenCardPanel.setLayout(new BoxLayout(hiddenCardPanel, BoxLayout.Y_AXIS));
        revealedCardPanel.setLayout(new BoxLayout(revealedCardPanel, BoxLayout.Y_AXIS));

        JPanel hiddenCardLabelPanel = new JPanel();
        hiddenCardPanel.add(hiddenCardLabelPanel);
        JPanel revealedCardLabelPanel = new JPanel();
        revealedCardPanel.add(revealedCardLabelPanel);
        hiddenCardPanelPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 10));
        hiddenCardPanel.add(hiddenCardPanelPanel);
        revealedCardPanelPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 10));
        revealedCardPanel.add(revealedCardPanelPanel);

        JLabel hiddenCardLabel = new JLabel("Hidden Cards");
        JLabel revealedCardLabel = new JLabel("Revealed Cards");
        hiddenCardLabelPanel.add(hiddenCardLabel);
        revealedCardLabelPanel.add(revealedCardLabel);

    }

    public void buildNorthPanel() {
        northPanel = new JPanel(new GridLayout(1, 2));
        JPanel northWestPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        JPanel northEastPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        northPanel.add(northWestPanel);
        northPanel.add(northEastPanel);
        mainPanel.add(northPanel, BorderLayout.NORTH);

        JPanel resourcesPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 20, 20));
        northWestPanel.add(resourcesPanel);

        JPanel foodPanel = new JPanel();
        JPanel waterPanel = new JPanel();
        JPanel woodFragmentsPanel = new JPanel();
        JPanel woodPlanksPanel = new JPanel();

        resourcesPanel.add(foodPanel);
        resourcesPanel.add(waterPanel);
        resourcesPanel.add(woodFragmentsPanel);
        resourcesPanel.add(woodPlanksPanel);

        JLabel foodLabel = new JLabel("Food : ");
        foodQuantityLabel = new JLabel("0");
        foodPanel.add(foodLabel);
        foodPanel.add(foodQuantityLabel);
        JLabel waterLabel = new JLabel("Water : ");
        waterQuantityLabel = new JLabel("0");
        waterPanel.add(waterLabel);
        waterPanel.add(waterQuantityLabel);
        JLabel woodFragmentsLabel = new JLabel("Wood Plank Fragments : ");
        woodFragmentsQuantityLabel = new JLabel("0");
        woodFragmentsPanel.add(woodFragmentsLabel);
        woodFragmentsPanel.add(woodFragmentsQuantityLabel);
        JLabel woodPlanksLabel = new JLabel("Wood Planks : ");
        woodPlanksQuantityLabel = new JLabel("0");
        woodPlanksPanel.add(woodPlanksLabel);
        woodPlanksPanel.add(woodPlanksQuantityLabel);

        JPanel roundDataPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT, 20, 20));
        northEastPanel.add(roundDataPanel);
        JPanel weatherPanel = new JPanel();
        JPanel nbAlivePanel = new JPanel();
        JPanel roundNumberPanel = new JPanel();

        roundDataPanel.add(weatherPanel);
        roundDataPanel.add(nbAlivePanel);
        roundDataPanel.add(roundNumberPanel);
        JLabel weatherLabel = new JLabel("Weather : ");
        weatherLabelNumber = new JLabel("0");
        JLabel nbAliveLabel = new JLabel("Alive : ");
        nbAliveLabelNumber = new JLabel("0");
        JLabel roundLabel = new JLabel("Round : ");
        roundLabelNumber = new JLabel("0");
        weatherPanel.add(weatherLabel);
        weatherPanel.add(weatherLabelNumber);
        nbAlivePanel.add(nbAliveLabel);
        nbAlivePanel.add(nbAliveLabelNumber);
        roundNumberPanel.add(roundLabel);
        roundNumberPanel.add(roundLabelNumber);

    }

    public void buildPlayersDisplay(int indexOfThisPlayer) {
        this.indexOfThisPlayer = indexOfThisPlayer;
        listPlayerDisplays = new ArrayList<>();
        for (int index = 0; index < board.getPlayerList().size(); index++) {
            Player player = board.getPlayerList().get(index);
            listPlayerDisplays.add(player.getPanelDisplay());

        }
        int newNbPlayers = nbPlayers;
        if (nbPlayers % 2 == 0) {
            JPanel voidPanel = new JPanel();

            listPlayerDisplays.add(voidPanel);
            newNbPlayers++;
        }
        int nbWest = nbPlayers / 2;

        for (int index = nbWest; index >= 1; index--) {

            westPanel.add(listPlayerDisplays.get((indexOfThisPlayer + index + newNbPlayers) % newNbPlayers));
            eastPanel.add(listPlayerDisplays.get((indexOfThisPlayer - index + newNbPlayers) % newNbPlayers));
        }

    }

    private JPanel buildCard(String title) {
        JPanel cardPanel = new JPanel();

        JLabel cardLabel = new JLabel(title);
        cardPanel.add(cardLabel);
        return cardPanel;
    }

    public void addRevealedCard(String title) {
        JPanel cardPanel = buildCard(title);
        revealedCardPanelPanel.add(cardPanel);
    }

    public void addHiddenCard(String title) {
        JPanel cardPanel = buildCard(title);
        hiddenCardPanelPanel.add(cardPanel);
    }

    public void updateSouth() {
        hiddenCardPanelPanel.removeAll();
        revealedCardPanelPanel.removeAll();
        Player thisPlayer = board.getThisPlayer();
        for (Card card : thisPlayer.getInventoryHidden()) {
            addHiddenCard(card + "");
        }
        for (Card card : thisPlayer.getInventoryRevealed()) {
            addRevealedCard(card + "");
        }
    }

    public void updateCurrentPlayer() {
        notificationLabel.setText("Current player : " + board.getPlayerList().get(board.getIndexCurrentPlayer()));
    }

    public void makePlayerChooseAction() {
        nextButton.setEnabled(false);
        cardLayoutCentralPanel.show(centerPanelCenterChoicePanel, CHOOSE_ACTION_PANEL);
    }

    public void makePlayerChiefDesignates(List<Player> pickablePlayers) {
        choosePlayerPanelPanel.removeAll();
        for (Player player : pickablePlayers) {
            JButton voteButton = new JButton(player + "");
            choosePlayerPanelPanel.add(voteButton);
            voteButton.addActionListener(new ChiefVoteListener(player));
        }
    }

    public void makePlayerVoteFor(List<Player> pickablePlayers) {
        choosePlayerPanelPanel.removeAll();
        cardLayoutCentralPanel.show(centerPanelCenterChoicePanel, CHOOSE_PLAYER_PANEL);
        for (Player player : pickablePlayers) {
            JButton voteButton = new JButton(player + "");
            choosePlayerPanelPanel.add(voteButton);
            voteButton.addActionListener(new VoteListener(player));
        }
    }

    public void allowPlayerToBeginVoteSession() {
        nextButton.setEnabled(false);
        beginVoteButton.setVisible(true);
    }

    public void allowPlayerToKillPlayerAfterVote(boolean forDeparture) {
        nextButton.setEnabled(false);
        if (forDeparture) {
            allowKillForDeparture.setVisible(true);
        } else {
            allowKillNotForDeparture.setVisible(true);
        }

    }

    public void displayMessage(String text) {

        String newText = notificationPanelTextPane.getText() + "\n" + text;
        notificationPanelTextPane.setText(newText);

    }

    public JPanel getMainPanel() {
        return mainPanel;
    }

    public void updateNorth(int foodRations, int waterRations, int woodFragments, int woodPlanks, int weather,
            int nbAlive, int round) {
        setFoodRationDisplay(foodRations);
        setWaterRationDisplay(waterRations);
        setFragmentsDisplay(woodFragments);
        setPlanksDisplay(woodPlanks);
        setWeatherDisplay(weather);
        setNbAliveDisplay(nbAlive);
        setRoundDisplay(round);
    }

    public void setNbAliveDisplay(int nbAlive) {
        nbAliveLabelNumber.setText(nbAlive + "");
    }

    public void setFoodRationDisplay(int nbFoodRations) {
        foodQuantityLabel.setText(nbFoodRations + "");
    }

    public void setWaterRationDisplay(int nbWaterRations) {
        waterQuantityLabel.setText(nbWaterRations + "");
    }

    public void setFragmentsDisplay(int nbWoodFragments) {
        woodFragmentsQuantityLabel.setText(nbWoodFragments + "");
    }

    public void setPlanksDisplay(int nbWoodPlanks) {
        woodPlanksQuantityLabel.setText(nbWoodPlanks + "");
    }

    public void setWeatherDisplay(int weather) {
        weatherLabelNumber.setText(weather + "");
    }

    public void setRoundDisplay(int round) {
        roundLabelNumber.setText(round + "");
    }

    public void setBoard(Board board) {
        this.board = board;
    }

    private void changePolice(JComponent component, int size) {
        component.setFont(new Font(component.getFont().getName(), component.getFont().getStyle(), size));

    }

    private class NextActionListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            board.play(board.nextPlayer());

        }
    }

    private class AllowKillListener implements ActionListener {
        boolean forDeparture;

        private AllowKillListener(boolean forDeparture) {
            this.forDeparture = forDeparture;
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            ((JButton) e.getSource()).setVisible(false);
            nextButton.setEnabled(true);
            board.setKillValidated(true);
            board.roundEnd(forDeparture);
        }
    }

    private class BeginVoteListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            board.choosePlayerToDie();
            ((JButton) e.getSource()).setVisible(false);
        }
    }

    private class FoodActionListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            Player player = board.getThisPlayer();
            player.playerSeeksFood(board);
            cardLayoutCentralPanel.show(centerPanelCenterChoicePanel, CHOOSE_VOID_PANEL);
            nextButton.setEnabled(true);
        }

    }

    private class WaterActionListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            Player player = board.getThisPlayer();
            player.playerSeeksWater(board);
            nextButton.setEnabled(true);
            cardLayoutCentralPanel.show(centerPanelCenterChoicePanel, CHOOSE_VOID_PANEL);
        }

    }

    private class WoodActionListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            cardLayoutCentralPanel.show(centerPanelCenterChoicePanel, CHOOSE_WOOD_TRIES_PANEL);
        }

    }

    private class WoodTryListener implements ActionListener {
        int nbTries;

        private WoodTryListener(int nbTries) {
            this.nbTries = nbTries;
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            Player player = board.getThisPlayer();
            player.playerSeeksWood(board, nbTries);
            nextButton.setEnabled(true);
            cardLayoutCentralPanel.show(centerPanelCenterChoicePanel, CHOOSE_VOID_PANEL);

        }
    }

    private class CardActionListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            Player player = board.getThisPlayer();
            player.playerSeeksCard(board);
            nextButton.setEnabled(true);
            updateSouth();
            cardLayoutCentralPanel.show(centerPanelCenterChoicePanel, CHOOSE_VOID_PANEL);

        }

    }

    private class VoteListener implements ActionListener {

        Player target;

        public VoteListener(Player target) {
            this.target = target;
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            cardLayoutCentralPanel.show(centerPanelCenterChoicePanel, CHOOSE_VOID_PANEL);
            board.getVotes().get(board.getThisPlayer()).add(target);
            if (!board.checkVoteNonOwnersOver()) {
                board.voteTimeForNonOwners();
            } else if (!board.checkVoteOwnersOver()) {
                board.voteTimeForOwners();
            } else {
                board.endOfVote();
            }
        }

    }

    private class ChiefVoteListener extends VoteListener {
        public ChiefVoteListener(Player target) {
            super(target);
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            cardLayoutCentralPanel.show(centerPanelCenterChoicePanel, CHOOSE_VOID_PANEL);
            board.setDesignated(target);
            board.roundEnd(board.getCurrentlyForDeparture());
        }
    }

}
