package front;

import static javax.swing.ScrollPaneConstants.HORIZONTAL_SCROLLBAR_ALWAYS;
import static javax.swing.ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED;
import static javax.swing.ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS;
import static javax.swing.ScrollPaneConstants.VERTICAL_SCROLLBAR_NEVER;

import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.EnumMap;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextPane;

import back.ActionType;
import back.Board;
import back.Player;
import back.PlayerState;
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
    private static final String CHOOSE_PLAYER_TARGET = "CHOOSE_PLAYER_TARGET";
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
    private JPanel choosePlayerTargetPanel;
    private JPanel choosePlayerTargetPanelPanelPlayers;
    private JPanel choosePlayerTargetPanelPanelAction;
    private JButton actionValidate;
    private int nbTargetsRequired = 0;
    private Map<Player, JCheckBox> targetMap;
    private Map<ActionType, JCheckBox> actionCheckBoxMap;
    private boolean allowedToPlayCard = true;
    public Card cardCurrentlyUsed;
    private int nbActionRequired = 0;
    private String previousPanel = CHOOSE_VOID_PANEL;
    private String currentPanel = CHOOSE_VOID_PANEL;
    private JLabel chiefLabel;
    private JLabel stateLabel;
    private JTextPane cardDescription;

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
        choosePlayerTargetPanel = new JPanel();
        centerPanelCenterNotificationPanel.add(notificationPanel);
        var voidChoicePanel = new JPanel();
        centerPanelCenterChoicePanel.add(voidChoicePanel, CHOOSE_VOID_PANEL);
        centerPanelCenterChoicePanel.add(chooseActionPanel, CHOOSE_ACTION_PANEL);
        centerPanelCenterChoicePanel.add(choosePlayerPanel, CHOOSE_PLAYER_PANEL);
        centerPanelCenterChoicePanel.add(chooseWoodNbTriesPanel, CHOOSE_WOOD_TRIES_PANEL);
        centerPanelCenterChoicePanel.add(choosePlayerTargetPanel, CHOOSE_PLAYER_TARGET);

        chooseActionPanel.setLayout(new BoxLayout(chooseActionPanel, BoxLayout.Y_AXIS));
        notificationPanel.setLayout(new BoxLayout(notificationPanel, BoxLayout.Y_AXIS));
        choosePlayerPanel.setLayout(new BoxLayout(choosePlayerPanel, BoxLayout.Y_AXIS));
        chooseWoodNbTriesPanel.setLayout(new BoxLayout(chooseWoodNbTriesPanel, BoxLayout.Y_AXIS));
        choosePlayerTargetPanel.setLayout(new BoxLayout(choosePlayerTargetPanel, BoxLayout.Y_AXIS));

        var chooseActionLabelPanel = new JPanel();
        var chooseActionLabel = new JLabel("Choose your action");
        chooseActionLabelPanel.add(chooseActionLabel);
        var notificationLabelPanel = new JPanel();
        notificationLabel = new JLabel("Notification !");
        notificationLabelPanel.add(notificationLabel);
        var choosePlayerLabelPanel = new JPanel();
        var choosePlayerLabel = new JLabel("Choose a player");
        choosePlayerLabelPanel.add(choosePlayerLabel);
        var chooseWoodNbTriesLabelPanel = new JPanel();
        var chooseWoodNbTriesLabel = new JLabel("Choose your number of tries");
        chooseWoodNbTriesLabelPanel.add(chooseWoodNbTriesLabel);
        var choosePlayerTargetLabelPanel = new JPanel();
        var choosePlayerTargetLabel = new JLabel("Card Validation");
        choosePlayerTargetLabelPanel.add(choosePlayerTargetLabel);

        chooseActionPanel.add(chooseActionLabelPanel);
        notificationPanel.add(notificationLabelPanel);
        choosePlayerPanel.add(choosePlayerLabelPanel);
        chooseWoodNbTriesPanel.add(chooseWoodNbTriesLabelPanel);
        choosePlayerTargetPanel.add(choosePlayerTargetLabelPanel);

        var chooseActionPanelPanel = new JPanel();
        var notificationPanelPanel = new JPanel();
        choosePlayerPanelPanel = new JPanel();
        var chooseWoodNbTriesPanelPanel = new JPanel();
        var choosePlayerTargetPanelPanel = new JPanel();
        chooseActionPanel.add(chooseActionPanelPanel);
        notificationPanel.add(notificationPanelPanel);
        choosePlayerPanel.add(choosePlayerPanelPanel);
        chooseWoodNbTriesPanel.add(chooseWoodNbTriesPanelPanel);
        choosePlayerTargetPanel.add(choosePlayerTargetPanelPanel);

        chooseActionPanelPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 10, 10));
        notificationPanelPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 10, 10));
        choosePlayerPanelPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 10, 10));
        chooseWoodNbTriesPanelPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 10, 10));
        choosePlayerTargetPanelPanel.setLayout(new BorderLayout());

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

        notificationPanelTextPaneScrollable.setHorizontalScrollBarPolicy(HORIZONTAL_SCROLLBAR_AS_NEEDED);
        notificationPanelTextPaneScrollable.setVerticalScrollBarPolicy(VERTICAL_SCROLLBAR_ALWAYS);

        notificationPanelTextPane.setEditable(false);
        notificationPanelTextPaneScrollable.setPreferredSize(new Dimension(800, 400));
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

        for (var index = 0; index < 7; index++) {
            var woodButton = new JButton(index + "");
            chooseWoodNbTriesPanelPanel.add(woodButton);
            woodButton.addActionListener(new WoodTryListener(index));
        }

        var choosePlayerTargetPanelPanelCenterContainer = new JPanel(new GridLayout(2, 1));
        choosePlayerTargetPanelPanel.add(choosePlayerTargetPanelPanelCenterContainer, BorderLayout.CENTER);
        choosePlayerTargetPanelPanelPlayers = new JPanel();
        choosePlayerTargetPanelPanelAction = new JPanel();

        var choosePlayerTargetPanelPanelCenterCardDescription = new JPanel();
        cardDescription = new JTextPane();
        var cardDescriptionScrollPane = new JScrollPane(cardDescription);
        choosePlayerTargetPanelPanelCenterCardDescription.add(cardDescriptionScrollPane);

        var choosePlayerTargetPanelPanelCenterCardUse = new JPanel(new GridLayout(1, 2));
        var choosePlayerTargetPanelPanelSouth = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 10));
        choosePlayerTargetPanelPanel.add(choosePlayerTargetPanelPanelSouth, BorderLayout.SOUTH);
        choosePlayerTargetPanelPanelCenterContainer.add(choosePlayerTargetPanelPanelCenterCardDescription);
        choosePlayerTargetPanelPanelCenterContainer.add(choosePlayerTargetPanelPanelCenterCardUse);
        choosePlayerTargetPanelPanelCenterCardUse.add(choosePlayerTargetPanelPanelPlayers);
        choosePlayerTargetPanelPanelCenterCardUse.add(choosePlayerTargetPanelPanelAction);

        actionCheckBoxMap = new EnumMap<>(ActionType.class);
        var foodAction = new JCheckBox("Food");
        foodAction.addItemListener(new ActionChoiceItemListener());
        actionCheckBoxMap.put(ActionType.FOOD, foodAction);
        choosePlayerTargetPanelPanelAction.add(foodAction);
        var waterAction = new JCheckBox("Water");
        waterAction.addItemListener(new ActionChoiceItemListener());
        actionCheckBoxMap.put(ActionType.WATER, waterAction);
        choosePlayerTargetPanelPanelAction.add(waterAction);
        var woodAction = new JCheckBox("Wood");
        foodAction.addItemListener(new ActionChoiceItemListener());
        actionCheckBoxMap.put(ActionType.WOOD, woodAction);
        choosePlayerTargetPanelPanelAction.add(woodAction);
        var cardAction = new JCheckBox("Card");
        cardAction.addItemListener(new ActionChoiceItemListener());
        actionCheckBoxMap.put(ActionType.CARD, cardAction);
        choosePlayerTargetPanelPanelAction.add(cardAction);

        actionValidate = new JButton("Use the card");
        actionValidate.addActionListener(new ValidateCardUseListener());
        choosePlayerTargetPanelPanelSouth.add(actionValidate);

    }

    public void buildCardTargetPanel() {
        targetMap = new HashMap<>();
        for (Player player : board.getPlayerList()) {
            var box = new JCheckBox(player.getName());
            choosePlayerTargetPanelPanelPlayers.add(box);
            box.addItemListener(new ActionChoiceItemListener());
            targetMap.put(player, box);
        }
    }

    public void buildEastPanel() {
        eastPanel = new JPanel(new GridLayout(nbPlayers / 2, 1));
        mainPanel.add(eastPanel, BorderLayout.EAST);
    }

    public void buildSouthPanel() {
        southPanel = new JPanel(new BorderLayout());
        mainPanel.add(southPanel, BorderLayout.SOUTH);
        var hiddenCardPanelContainer = new JPanel();
        var revealedCardPanelContainer = new JPanel();

        var playerStatePanel = new JPanel(new GridLayout(2, 1, 10, 10));
        var chiefPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        var statePanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        chiefLabel = new JLabel("");
        stateLabel = new JLabel(PlayerState.HEALTHY.toString());

        chiefPanel.add(chiefLabel);
        statePanel.add(stateLabel);
        playerStatePanel.add(statePanel);
        playerStatePanel.add(chiefPanel);

        hiddenCardPanel = new JPanel();
        revealedCardPanel = new JPanel();
        var hiddenCardPanelScrollable = new JScrollPane(hiddenCardPanel, VERTICAL_SCROLLBAR_NEVER,
                HORIZONTAL_SCROLLBAR_AS_NEEDED);
        var revealedCardPanelScrollable = new JScrollPane(revealedCardPanel, VERTICAL_SCROLLBAR_NEVER,
                HORIZONTAL_SCROLLBAR_AS_NEEDED);
        playerStatePanel.setPreferredSize(new Dimension(20, 20));
        hiddenCardPanelScrollable.setPreferredSize(new Dimension(900, 100));
        revealedCardPanelScrollable.setPreferredSize(new Dimension(900, 100));
        hiddenCardPanelContainer.add(hiddenCardPanelScrollable);
        revealedCardPanelContainer.add(revealedCardPanelScrollable);
        southPanel.add(hiddenCardPanelContainer, BorderLayout.WEST);
        southPanel.add(playerStatePanel, BorderLayout.CENTER);
        southPanel.add(revealedCardPanelContainer, BorderLayout.EAST);
        hiddenCardPanel.setLayout(new BoxLayout(hiddenCardPanel, BoxLayout.Y_AXIS));
        revealedCardPanel.setLayout(new BoxLayout(revealedCardPanel, BoxLayout.Y_AXIS));

        var hiddenCardLabelPanel = new JPanel();
        hiddenCardPanel.add(hiddenCardLabelPanel);
        var revealedCardLabelPanel = new JPanel();
        revealedCardPanel.add(revealedCardLabelPanel);
        hiddenCardPanelPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 10));
        hiddenCardPanel.add(hiddenCardPanelPanel);
        revealedCardPanelPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 10));
        revealedCardPanel.add(revealedCardPanelPanel);

        var hiddenCardLabel = new JLabel("Hidden Cards");
        var revealedCardLabel = new JLabel("Revealed Cards");
        hiddenCardLabelPanel.add(hiddenCardLabel);
        revealedCardLabelPanel.add(revealedCardLabel);

    }

    public void buildNorthPanel() {
        northPanel = new JPanel(new GridLayout(1, 2));
        var northWestPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        var northEastPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        northPanel.add(northWestPanel);
        northPanel.add(northEastPanel);
        mainPanel.add(northPanel, BorderLayout.NORTH);

        var resourcesPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 20, 20));
        northWestPanel.add(resourcesPanel);

        var foodPanel = new JPanel();
        var waterPanel = new JPanel();
        var woodFragmentsPanel = new JPanel();
        var woodPlanksPanel = new JPanel();

        resourcesPanel.add(foodPanel);
        resourcesPanel.add(waterPanel);
        resourcesPanel.add(woodFragmentsPanel);
        resourcesPanel.add(woodPlanksPanel);

        var foodLabel = new JLabel("Food : ");
        foodQuantityLabel = new JLabel("0");
        foodPanel.add(foodLabel);
        foodPanel.add(foodQuantityLabel);
        var waterLabel = new JLabel("Water : ");
        waterQuantityLabel = new JLabel("0");
        waterPanel.add(waterLabel);
        waterPanel.add(waterQuantityLabel);
        var woodFragmentsLabel = new JLabel("Wood Plank Fragments : ");
        woodFragmentsQuantityLabel = new JLabel("0");
        woodFragmentsPanel.add(woodFragmentsLabel);
        woodFragmentsPanel.add(woodFragmentsQuantityLabel);
        var woodPlanksLabel = new JLabel("Wood Planks : ");
        woodPlanksQuantityLabel = new JLabel("0");
        woodPlanksPanel.add(woodPlanksLabel);
        woodPlanksPanel.add(woodPlanksQuantityLabel);

        var roundDataPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT, 20, 20));
        northEastPanel.add(roundDataPanel);
        var weatherPanel = new JPanel();
        var nbAlivePanel = new JPanel();
        var roundNumberPanel = new JPanel();

        roundDataPanel.add(weatherPanel);
        roundDataPanel.add(nbAlivePanel);
        roundDataPanel.add(roundNumberPanel);
        var weatherLabel = new JLabel("Weather : ");
        weatherLabelNumber = new JLabel("0");
        var nbAliveLabel = new JLabel("Alive : ");
        nbAliveLabelNumber = new JLabel("0");
        var roundLabel = new JLabel("Round : ");
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
        for (var index = 0; index < board.getPlayerList().size(); index++) {
            var player = board.getPlayerList().get(index);
            listPlayerDisplays.add(player.getPanelDisplay());

        }
        int newNbPlayers = nbPlayers;
        if (nbPlayers % 2 == 0) {
            var voidPanel = new JPanel();

            listPlayerDisplays.add(voidPanel);
            newNbPlayers++;
        }
        int nbWest = nbPlayers / 2;

        for (int index = nbWest; index >= 1; index--) {
            var panelForWest = new JPanel(new FlowLayout(FlowLayout.CENTER));
            var panelForEast = new JPanel(new FlowLayout(FlowLayout.CENTER));
            panelForWest.add(listPlayerDisplays.get((indexOfThisPlayer + index + newNbPlayers) % newNbPlayers));
            panelForEast.add(listPlayerDisplays.get((indexOfThisPlayer - index + newNbPlayers) % newNbPlayers));
            westPanel.add(panelForWest);
            eastPanel.add(panelForEast);
        }

    }

    private JPanel buildCard(Card card) {
        var cardPanel = new JPanel();

        var cardButton = new JButton(card + "");
        cardButton.addActionListener(card.getActionListener());
        cardPanel.add(cardButton);
        return cardPanel;
    }

    public void addRevealedCard(Card card) {
        JPanel cardPanel = buildCard(card);
        revealedCardPanelPanel.add(cardPanel);
    }

    public void addHiddenCard(Card card) {
        JPanel cardPanel = buildCard(card);
        hiddenCardPanelPanel.add(cardPanel);
    }

    public void updateSouth() {
        hiddenCardPanelPanel.removeAll();
        revealedCardPanelPanel.removeAll();
        var thisPlayer = board.getThisPlayer();
        for (Card card : thisPlayer.getInventoryHidden()) {
            addHiddenCard(card);
        }
        for (Card card : thisPlayer.getInventoryRevealed()) {
            addRevealedCard(card);
        }
        stateLabel.setText(board.getThisPlayer().getState().toString());
        String chiefString = board.getThisPlayer().equals(board.getChief())
                ? board.getStringsBundle().getString("chief_label")
                : board.getStringsBundle().getString("not_chief_label");
        chiefLabel.setText(chiefString);
        southPanel.update(southPanel.getGraphics());

    }

    public void updateCurrentPlayer() {
        notificationLabel.setText("Current player : " + board.getPlayerList().get(board.getIndexCurrentPlayer()));
        var isThisPlayerTurn = board.getPlayerList().get(board.getIndexCurrentPlayer()).equals(board.getThisPlayer());
        southPanel.setBorder(BorderFactory.createLineBorder(isThisPlayerTurn ? Color.RED : Color.BLACK));
    }

    public void makePlayerChooseAction() {
        nextButton.setEnabled(false);
        switchToPanel(CHOOSE_ACTION_PANEL);
    }

    public void makePlayerChiefDesignates(List<Player> pickablePlayers) {
        choosePlayerPanelPanel.removeAll();
        switchToPanel(CHOOSE_PLAYER_PANEL);

        for (Player player : pickablePlayers) {
            var voteButton = new JButton(player + "");
            choosePlayerPanelPanel.add(voteButton);
            voteButton.addActionListener(new ChiefVoteListener(player));
        }
    }

    public void makePlayerVoteFor(List<Player> pickablePlayers) {
        choosePlayerPanelPanel.removeAll();
        switchToPanel(CHOOSE_PLAYER_PANEL);

        for (Player player : pickablePlayers) {
            var voteButton = new JButton(player + "");
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

    public void endGame() {
        updateSouth();
        nextButton.setVisible(false);
        allowedToPlayCard = false;
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

    public void setAllowedToPlayCard(boolean allowedToPlayCard) {
        this.allowedToPlayCard = allowedToPlayCard;
    }

    private void changePolice(JComponent component, int size) {
        component.setFont(new Font(component.getFont().getName(), component.getFont().getStyle(), size));
    }

    private void switchToPanel(String panelName) {
        if (!previousPanel.equals(currentPanel) && !currentPanel.equals(CHOOSE_PLAYER_TARGET)) {
            previousPanel = currentPanel;
        }

        cardLayoutCentralPanel.show(centerPanelCenterChoicePanel, panelName);
        currentPanel = panelName;
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
            var player = board.getThisPlayer();
            player.playerSeeksFood(board);
            switchToPanel(CHOOSE_VOID_PANEL);
            nextButton.setEnabled(true);
        }

    }

    private class WaterActionListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            var player = board.getThisPlayer();
            player.playerSeeksWater(board);
            nextButton.setEnabled(true);
            switchToPanel(CHOOSE_VOID_PANEL);

        }

    }

    private class WoodActionListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            switchToPanel(CHOOSE_WOOD_TRIES_PANEL);
        }

    }

    private class WoodTryListener implements ActionListener {
        int nbTries;

        private WoodTryListener(int nbTries) {
            this.nbTries = nbTries;
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            var player = board.getThisPlayer();
            player.playerSeeksWood(board, nbTries);
            nextButton.setEnabled(true);
            switchToPanel(CHOOSE_VOID_PANEL);

        }
    }

    private class CardActionListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            var player = board.getThisPlayer();
            player.playerSeeksCard(board);
            nextButton.setEnabled(true);
            updateSouth();
            switchToPanel(CHOOSE_VOID_PANEL);

        }

    }

    private class VoteListener implements ActionListener {

        Player target;

        public VoteListener(Player target) {
            this.target = target;
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            switchToPanel(CHOOSE_VOID_PANEL);

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
            switchToPanel(CHOOSE_VOID_PANEL);

            board.setDesignated(target);
            board.roundEnd(board.getCurrentlyForDeparture());
        }
    }

    public class CardPlayerActionListener implements ActionListener {
        Card card;

        public CardPlayerActionListener(Card card) {
            this.card = card;
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            if (card.equals(cardCurrentlyUsed)) {
                cardCurrentlyUsed = null;
                switchToPanel(previousPanel);
            } else if (card.canBeUsed()) {
                cardCurrentlyUsed = card;
                nbTargetsRequired = 0;
                nbActionRequired = 0;
                switchToPanel(CHOOSE_PLAYER_TARGET);
                choosePlayerTargetPanelPanelAction.setVisible(false);
                choosePlayerTargetPanelPanelPlayers.setVisible(false);
            }
        }
    }

    public class CardPlayerActionListenerOneTarget extends CardPlayerActionListener {

        public CardPlayerActionListenerOneTarget(Card card) {
            super(card);
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            if (card.equals(cardCurrentlyUsed)) {
                cardCurrentlyUsed = null;
                switchToPanel(previousPanel);
            } else if (card.canBeUsed()) {
                super.actionPerformed(e);
                switchToPanel(CHOOSE_PLAYER_TARGET);
                nbTargetsRequired = 1;
                nbActionRequired = 0;
                choosePlayerTargetPanelPanelAction.setVisible(false);
                choosePlayerTargetPanelPanelPlayers.setVisible(true);
                for (var box : targetMap.values()) {
                    box.setEnabled(true);
                    box.setSelected(false);
                }
            }
        }

    }

    public class CardPlayerActionListenerOneTargetOneAction extends CardPlayerActionListener {

        public CardPlayerActionListenerOneTargetOneAction(Card card) {
            super(card);
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            if (card.equals(cardCurrentlyUsed)) {
                cardCurrentlyUsed = null;
                switchToPanel(previousPanel);
            } else if (card.canBeUsed()) {
                super.actionPerformed(e);
                switchToPanel(CHOOSE_PLAYER_TARGET);
                nbTargetsRequired = 1;
                nbActionRequired = 1;
                choosePlayerTargetPanelPanelAction.setVisible(true);
                choosePlayerTargetPanelPanelPlayers.setVisible(true);
                for (var box : targetMap.values()) {
                    box.setEnabled(true);
                    box.setSelected(false);
                }
            }
        }
    }

    public class CardPlayerActionListenerThreeTargets extends CardPlayerActionListener {

        public CardPlayerActionListenerThreeTargets(Card card) {
            super(card);
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            if (card.equals(cardCurrentlyUsed)) {
                cardCurrentlyUsed = null;
                switchToPanel(previousPanel);
            } else if (card.canBeUsed()) {
                super.actionPerformed(e);
                nbTargetsRequired = 3;
                nbActionRequired = 0;
                switchToPanel(CHOOSE_PLAYER_TARGET);
                choosePlayerTargetPanelPanelAction.setVisible(false);
                choosePlayerTargetPanelPanelPlayers.setVisible(true);
                for (var box : targetMap.values()) {
                    box.setEnabled(true);
                    box.setSelected(false);
                }
            }
        }
    }

    public class ValidateCardUseListener implements ActionListener {

        private Player[] fillTargetsArray() {
            var targetsArray = new Player[] { null, null, null };
            for (var index = 0; index < nbTargetsRequired; index++) {
                for (Entry<Player, JCheckBox> entry : targetMap.entrySet()) {
                    if (entry.getValue().isSelected()) {
                        var selectedPlayer = entry.getKey();
                        if (targetsArray[0] == null) {
                            targetsArray[0] = selectedPlayer;
                        } else if (targetsArray[1] == null && !selectedPlayer.equals(targetsArray[0])) {
                            targetsArray[1] = selectedPlayer;
                        } else if (targetsArray[2] == null && !selectedPlayer.equals(targetsArray[1])
                                && !selectedPlayer.equals(targetsArray[0])) {
                            targetsArray[2] = selectedPlayer;
                        }
                    }
                }
            }
            return targetsArray;
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            if (allowedToPlayCard) {
                var targetsArray = fillTargetsArray();
                ActionType action = ActionType.NONE;

                for (Entry<ActionType, JCheckBox> entry : actionCheckBoxMap.entrySet()) {
                    if (entry.getValue().isSelected()) {
                        action = entry.getKey();
                    }
                }
                cardCurrentlyUsed.useCard(targetsArray[0], targetsArray[1], targetsArray[2], action);
                cardCurrentlyUsed = null;
                nbTargetsRequired = 0;
                updateSouth();
                board.updateDisplayResources();
                switchToPanel(previousPanel);
            }
        }
    }

    private class ActionChoiceItemListener implements ItemListener {

        @Override
        public void itemStateChanged(ItemEvent e) {
            var nbTargetsSelected = 0;
            var nbActionSelected = 0;
            for (Entry<Player, JCheckBox> entry : targetMap.entrySet()) {
                if (entry.getValue().isSelected()) {
                    nbTargetsSelected++;
                }
            }
            for (Entry<ActionType, JCheckBox> entry : actionCheckBoxMap.entrySet()) {
                if (entry.getValue().isSelected()) {
                    nbActionSelected++;
                }
            }

            for (Entry<Player, JCheckBox> entry : targetMap.entrySet()) {
                if (!entry.getValue().isSelected()) {
                    entry.getValue().setEnabled(nbTargetsSelected < nbTargetsRequired);
                }
            }
            for (Entry<ActionType, JCheckBox> entry : actionCheckBoxMap.entrySet()) {
                if (!entry.getValue().isSelected()) {
                    entry.getValue().setEnabled(nbActionSelected != 1);
                }
            }
            actionValidate.setEnabled(nbTargetsRequired > 0 && allowedToPlayCard && nbTargetsSelected >= 1
                    && nbTargetsSelected <= nbTargetsRequired && nbActionSelected == nbActionRequired);

        }
    }
}
