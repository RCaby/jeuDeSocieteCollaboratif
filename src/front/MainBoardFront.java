package front;

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
import java.util.ResourceBundle;
import java.util.Map.Entry;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
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
import back.cards.items.Card;
import back.cards.items.expansion.BuoyExpansion;

public class MainBoardFront implements Serializable {
    private static final String CHOOSE_ACTION_PANEL = "CHOOSE_ACTION_PANEL";
    private static final String CHOOSE_PLAYER_PANEL = "CHOOSE_PLAYER_PANEL";
    private static final String CHOOSE_VOID_PANEL = "CHOOSE_VOID_PANEL";
    private static final String CHOOSE_WOOD_TRIES_PANEL = "CHOOSE_WOOD_TRIES_PANEL";
    private static final String CHOOSE_PLAYER_TARGET = "CHOOSE_PLAYER_TARGET";
    private static final String CHOOSE_CARD_BUOY_PANEL = "CHOOSE_CARD_BUOY_PANEL";
    private static final String CHOOSE_CARD_RUM_PANEL = "CHOOSE_CARD_RUM_PANEL";
    private static final int SOUTH_BUTTON_WIDTH = 65;
    private static final int SOUTH_BUTTON_HEIGHT = 65;
    private static final int ACTION_BUTTON_WIDTH = 161;
    private static final int ACTION_BUTTON_HEIGHT = 100;
    private static final int VOTE_BUTTON_WIDTH = 121;
    private static final int VOTE_BUTTON_HEIGHT = 75;

    JPanel mainPanel;
    Board board;
    int nbPlayers;
    int indexOfThisPlayer;
    private JPanel westPanel;
    private JPanel eastPanel;
    private JPanel southPanel;
    private JLabel foodQuantityLabel;
    private JLabel waterQuantityLabel;
    private JLabel woodFragmentsQuantityLabel;
    private JLabel weatherLabelNumber;
    private JLabel roundLabelNumber;
    private JLabel woodPlanksQuantityLabel;
    private JLabel nbAliveLabelNumber;
    private JPanel hiddenCardPanelPanel;
    private JPanel revealedCardPanelPanel;
    private CardLayout cardLayoutCentralPanel;

    private JPanel choosePlayerPanelPanel;
    private JTextPane notificationPanelTextPane;
    private JLabel notificationLabel;
    private JButton nextButton;
    private JPanel centerPanelCenterChoicePanel;
    private JButton beginVoteButton;
    private JButton allowKillNotForDeparture;
    private JButton allowKillForDeparture;
    private JPanel choosePlayerTargetPanelPanelPlayers;
    private JPanel choosePlayerTargetPanelPanelAction;
    private JButton actionValidate;
    private int nbTargetsRequired = 0;
    private Map<Player, JCheckBox> targetMap;
    private Map<ActionType, JCheckBox> actionCheckBoxMap;
    private Map<Card, JCheckBox> cardCheckBoxMap;
    private boolean allowedToPlayCard = true;
    private Card cardCurrentlyUsed;
    private int nbActionRequired = 0;
    private int nbCardRequired = 0;
    private String previousPanel = CHOOSE_VOID_PANEL;
    private String currentPanel = CHOOSE_VOID_PANEL;
    private JLabel chiefLabel;
    private JLabel stateLabel;
    private JTextPane cardDescription;
    private transient ResourceBundle stringsBundle;
    private String waterString = "water";
    private JLabel choosePlayerTargetLabel;
    private JPanel choosePlayerTargetPanelPanelCard;
    private JPanel cardChoiceBuoyPanel;
    private JPanel cardChoiceRumPanel;
    private String previousPanelRum;

    /**
     * Builds an interface for the game.
     * 
     * <p>
     * The main panel is divided in different parts, following a
     * {@code BorderLayout}.
     * 
     * @param nbPlayers the number of players, including one non computer player,
     *                  the user
     */
    public MainBoardFront(int nbPlayers, ResourceBundle stringsBundle) {
        this.stringsBundle = stringsBundle;
        mainPanel = new JPanel();
        mainPanel.setLayout(new BorderLayout());
        this.nbPlayers = nbPlayers;

        buildEastPanel();
        buildWestPanel();
        buildSouthPanel();
        buildCenterPanel();
        buildNorthPanel();

    }

    /**
     * Builds the western part of the main panel.
     */
    private void buildWestPanel() {
        westPanel = new JPanel(new GridLayout(nbPlayers / 2, 1));
        mainPanel.add(westPanel, BorderLayout.WEST);

    }

    /**
     * Builds the center part of the main panel.
     */
    private void buildCenterPanel() {
        var centerPanel = new JPanel(new BorderLayout());
        var centerPanelCenter = new JPanel(new GridLayout(2, 1));
        var centerPanelCenterNotificationPanel = new JPanel();
        centerPanelCenterChoicePanel = new JPanel();
        centerPanelCenter.add(centerPanelCenterNotificationPanel);
        centerPanelCenter.add(centerPanelCenterChoicePanel);

        var centerPanelSouth = new JPanel();
        centerPanel.add(centerPanelCenter, BorderLayout.CENTER);
        centerPanel.add(centerPanelSouth, BorderLayout.SOUTH);
        centerPanelSouth.setLayout(new FlowLayout(FlowLayout.CENTER, 10, 10));
        cardLayoutCentralPanel = new CardLayout();
        mainPanel.add(centerPanel, BorderLayout.CENTER);
        centerPanelCenterChoicePanel.setLayout(cardLayoutCentralPanel);
        var chooseActionPanel = new JPanel();
        var chooseWoodNbTriesPanel = new JPanel();
        var notificationPanel = new JPanel();
        var choosePlayerPanel = new JPanel();
        var choosePlayerTargetPanel = new JPanel();
        centerPanelCenterNotificationPanel.add(notificationPanel);
        var voidChoicePanel = new JPanel();
        cardChoiceBuoyPanel = new JPanel();
        cardChoiceRumPanel = new JPanel();
        centerPanelCenterChoicePanel.add(voidChoicePanel, CHOOSE_VOID_PANEL);
        centerPanelCenterChoicePanel.add(chooseActionPanel, CHOOSE_ACTION_PANEL);
        centerPanelCenterChoicePanel.add(choosePlayerPanel, CHOOSE_PLAYER_PANEL);
        centerPanelCenterChoicePanel.add(chooseWoodNbTriesPanel, CHOOSE_WOOD_TRIES_PANEL);
        centerPanelCenterChoicePanel.add(choosePlayerTargetPanel, CHOOSE_PLAYER_TARGET);
        centerPanelCenterChoicePanel.add(cardChoiceBuoyPanel, CHOOSE_CARD_BUOY_PANEL);
        centerPanelCenterChoicePanel.add(cardChoiceRumPanel, CHOOSE_CARD_RUM_PANEL);

        chooseActionPanel.setLayout(new BoxLayout(chooseActionPanel, BoxLayout.Y_AXIS));
        notificationPanel.setLayout(new BoxLayout(notificationPanel, BoxLayout.Y_AXIS));
        choosePlayerPanel.setLayout(new BoxLayout(choosePlayerPanel, BoxLayout.Y_AXIS));
        chooseWoodNbTriesPanel.setLayout(new BoxLayout(chooseWoodNbTriesPanel, BoxLayout.Y_AXIS));
        choosePlayerTargetPanel.setLayout(new BoxLayout(choosePlayerTargetPanel, BoxLayout.Y_AXIS));
        cardChoiceBuoyPanel.setLayout(new GridLayout(1, 2, 10, 10));

        var chooseActionLabelPanel = new JPanel();
        var chooseActionLabel = new JLabel(stringsBundle.getString("chooseAction"));
        changeFont(chooseActionLabel, 18);
        chooseActionLabelPanel.add(chooseActionLabel);
        var notificationLabelPanel = new JPanel();
        notificationLabel = new JLabel(stringsBundle.getString("notification"));
        changeFont(notificationLabel, 18);
        notificationLabelPanel.add(notificationLabel);
        var choosePlayerLabelPanel = new JPanel();
        var choosePlayerLabel = new JLabel(stringsBundle.getString("choosePlayer"));
        changeFont(choosePlayerLabel, 18);
        choosePlayerLabelPanel.add(choosePlayerLabel);
        var chooseWoodNbTriesLabelPanel = new JPanel();
        var chooseWoodNbTriesLabel = new JLabel(stringsBundle.getString("chooseNbTries"));
        changeFont(chooseWoodNbTriesLabel, 18);
        chooseWoodNbTriesLabelPanel.add(chooseWoodNbTriesLabel);
        var choosePlayerTargetLabelPanel = new JPanel();
        choosePlayerTargetLabel = new JLabel(stringsBundle.getString("cardDescription"));
        changeFont(choosePlayerTargetLabel, 18);
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

        chooseActionPanelPanel.setLayout(new GridLayout(2, 2, 25, 25));
        notificationPanelPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 10, 10));
        choosePlayerPanelPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 10, 10));
        chooseWoodNbTriesPanelPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 10, 10));
        choosePlayerTargetPanelPanel.setLayout(new BorderLayout());

        var foodButtonAction = new JButton(stringsBundle.getString("food"));
        foodButtonAction.setPreferredSize(new Dimension(ACTION_BUTTON_WIDTH, ACTION_BUTTON_HEIGHT));
        foodButtonAction.addActionListener(new FoodActionListener());
        var foodButtonPanel = new JPanel();
        var waterButtonAction = new JButton(stringsBundle.getString(waterString));
        waterButtonAction.setPreferredSize(new Dimension(ACTION_BUTTON_WIDTH, ACTION_BUTTON_HEIGHT));
        waterButtonAction.addActionListener(new WaterActionListener());
        var waterButtonPanel = new JPanel();
        var woodButtonAction = new JButton(stringsBundle.getString("wood"));
        woodButtonAction.setPreferredSize(new Dimension(ACTION_BUTTON_WIDTH, ACTION_BUTTON_HEIGHT));
        woodButtonAction.addActionListener(new WoodActionListener());
        var woodButtonPanel = new JPanel();
        var cardButtonAction = new JButton(stringsBundle.getString("card"));
        cardButtonAction.setPreferredSize(new Dimension(ACTION_BUTTON_WIDTH, ACTION_BUTTON_HEIGHT));
        cardButtonAction.addActionListener(new CardActionListener());
        var cardButtonPanel = new JPanel();
        foodButtonPanel.add(foodButtonAction);
        waterButtonPanel.add(waterButtonAction);
        woodButtonPanel.add(woodButtonAction);
        cardButtonPanel.add(cardButtonAction);
        chooseActionPanelPanel.add(foodButtonPanel);
        chooseActionPanelPanel.add(waterButtonPanel);
        chooseActionPanelPanel.add(woodButtonPanel);
        chooseActionPanelPanel.add(cardButtonPanel);
        changeFont(foodButtonAction, 16);
        changeFont(waterButtonAction, 16);
        changeFont(woodButtonAction, 16);
        changeFont(cardButtonAction, 16);

        notificationPanelTextPane = new JTextPane();
        changeFont(notificationPanelTextPane, 16);
        var notificationPanelTextPaneScrollable = new JScrollPane(notificationPanelTextPane);

        notificationPanelTextPaneScrollable.setHorizontalScrollBarPolicy(HORIZONTAL_SCROLLBAR_AS_NEEDED);
        notificationPanelTextPaneScrollable.setVerticalScrollBarPolicy(VERTICAL_SCROLLBAR_ALWAYS);

        notificationPanelTextPane.setEditable(false);
        notificationPanelTextPaneScrollable.setPreferredSize(new Dimension(800, 300));
        notificationPanelPanel.add(notificationPanelTextPaneScrollable);

        nextButton = new JButton(stringsBundle.getString("next"));
        changeFont(nextButton, 14);
        nextButton.setPreferredSize(new Dimension(100, 65));
        nextButton.addActionListener(new NextActionListener());
        centerPanelSouth.add(nextButton);
        beginVoteButton = new JButton(stringsBundle.getString("beginVote"));
        beginVoteButton.addActionListener(new BeginVoteListener());
        changeFont(beginVoteButton, 14);
        beginVoteButton.setPreferredSize(new Dimension(200, 65));
        centerPanelSouth.add(beginVoteButton);
        allowKillNotForDeparture = new JButton(stringsBundle.getString("confirmPlayer"));
        allowKillNotForDeparture.addActionListener(new AllowKillListener(false));
        changeFont(allowKillNotForDeparture, 14);
        allowKillNotForDeparture.setPreferredSize(new Dimension(250, 65));
        centerPanelSouth.add(allowKillNotForDeparture);
        allowKillForDeparture = new JButton(stringsBundle.getString("confirmPlayer"));
        allowKillForDeparture.addActionListener(new AllowKillListener(true));
        changeFont(allowKillForDeparture, 14);
        allowKillForDeparture.setPreferredSize(new Dimension(250, 65));
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
        var choosePlayerTargetPanelPanelActionCard = new JPanel(new GridLayout(2, 1));
        choosePlayerTargetPanelPanelCard = new JPanel();

        var choosePlayerTargetPanelPanelCenterCardDescription = new JPanel();
        cardDescription = new JTextPane();
        var cardDescriptionScrollPane = new JScrollPane(cardDescription);
        cardDescriptionScrollPane.setPreferredSize(new Dimension(800, 100));
        changeFont(cardDescription, 16);
        cardDescriptionScrollPane.setHorizontalScrollBarPolicy(HORIZONTAL_SCROLLBAR_AS_NEEDED);
        cardDescriptionScrollPane.setVerticalScrollBarPolicy(VERTICAL_SCROLLBAR_ALWAYS);
        cardDescription.setEditable(false);
        choosePlayerTargetPanelPanelCenterCardDescription.add(cardDescriptionScrollPane);

        var choosePlayerTargetPanelPanelCenterCardUse = new JPanel(new GridLayout(1, 2));
        var choosePlayerTargetPanelPanelSouth = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 10));
        choosePlayerTargetPanelPanel.add(choosePlayerTargetPanelPanelSouth, BorderLayout.SOUTH);
        choosePlayerTargetPanelPanelCenterContainer.add(choosePlayerTargetPanelPanelCenterCardDescription);
        choosePlayerTargetPanelPanelCenterContainer.add(choosePlayerTargetPanelPanelCenterCardUse);
        choosePlayerTargetPanelPanelCenterCardUse.add(choosePlayerTargetPanelPanelPlayers);
        choosePlayerTargetPanelPanelCenterCardUse.add(choosePlayerTargetPanelPanelActionCard);
        choosePlayerTargetPanelPanelActionCard.add(choosePlayerTargetPanelPanelAction);
        choosePlayerTargetPanelPanelActionCard.add(choosePlayerTargetPanelPanelCard);

        actionCheckBoxMap = new EnumMap<>(ActionType.class);
        var foodAction = new JCheckBox(stringsBundle.getString("food"));
        foodAction.addItemListener(new ActionChoiceItemListener());
        actionCheckBoxMap.put(ActionType.FOOD, foodAction);
        choosePlayerTargetPanelPanelAction.add(foodAction);
        var waterAction = new JCheckBox(stringsBundle.getString(waterString));
        waterAction.addItemListener(new ActionChoiceItemListener());
        actionCheckBoxMap.put(ActionType.WATER, waterAction);
        choosePlayerTargetPanelPanelAction.add(waterAction);
        var woodAction = new JCheckBox(stringsBundle.getString("wood"));
        foodAction.addItemListener(new ActionChoiceItemListener());
        actionCheckBoxMap.put(ActionType.WOOD, woodAction);
        choosePlayerTargetPanelPanelAction.add(woodAction);
        var cardAction = new JCheckBox(stringsBundle.getString("card"));
        cardAction.addItemListener(new ActionChoiceItemListener());
        actionCheckBoxMap.put(ActionType.CARD, cardAction);
        choosePlayerTargetPanelPanelAction.add(cardAction);

        cardCheckBoxMap = new HashMap<>();

        actionValidate = new JButton(stringsBundle.getString("useCard"));
        actionValidate.addActionListener(new ValidateCardUseListener());
        choosePlayerTargetPanelPanelSouth.add(actionValidate);

    }

    public void getReadyForRum() {
        nextButton.setEnabled(false);
        board.rumDistributionInitialization();
        buildCardChoiceRumPanel();
        updateSouth();
        allowedToPlayCard = false;
        previousPanelRum = previousPanel;
    }

    public void unbuildRum() {
        updateSouth();
        allowedToPlayCard = true;
        switchToPanel(previousPanelRum);

    }

    public void buildCardChoiceRumPanel() {
        cardChoiceRumPanel.removeAll();
        cardChoiceRumPanel.setLayout(new BorderLayout());
        var titlePanel = new JPanel();
        var target = board.getPlayerAliveAfterBefore(board.getPlayerList().indexOf(board.getThisPlayer()), true);
        var titleLabel = new JLabel(String.format(stringsBundle.getString("choiceCardRum"), target));
        titlePanel.add(titleLabel);
        changeFont(titleLabel, 16);
        cardChoiceRumPanel.add(titlePanel, BorderLayout.NORTH);
        var numberRows = (int) Math.ceil(Math.sqrt(1.0 * board.getThisPlayer().getCardNumber()));
        var cardsPanel = new JPanel(new GridLayout(numberRows, numberRows, 10, 10));
        cardChoiceRumPanel.add(cardsPanel, BorderLayout.CENTER);

        switchToPanel(CHOOSE_CARD_RUM_PANEL);
        for (Card card : board.getThisPlayer().getInventory()) {
            var img = ((ImageIcon) card.getRevealedCardIcon()).getImage();
            var newImg = img.getScaledInstance(SOUTH_BUTTON_WIDTH, SOUTH_BUTTON_HEIGHT, java.awt.Image.SCALE_SMOOTH);
            var icon = new ImageIcon(newImg);
            var buttonCard = new JButton(icon);
            buttonCard.setPreferredSize(new Dimension(SOUTH_BUTTON_WIDTH, SOUTH_BUTTON_HEIGHT));
            buttonCard.addActionListener(new ChoiceCardRumActionListener(card));
            var buttonCardPanel = new JPanel();
            buttonCardPanel.add(buttonCard);
            cardsPanel.add(buttonCardPanel);
        }
    }

    public void buildCardChoiceBuoyPanel(Card card1, Card card2) {
        cardChoiceBuoyPanel.removeAll();
        var panelCard1 = new JPanel(new FlowLayout(FlowLayout.CENTER));
        var panelCard2 = new JPanel(new FlowLayout(FlowLayout.CENTER)); // TODO améliorer affichage parce que là beurk
        var buttonCard1 = new JButton(card1.toString());
        buttonCard1.setToolTipText(card1.getCardDescription());
        var buttonCard2 = new JButton(card2.toString());
        buttonCard2.setToolTipText(card2.getCardDescription());
        buttonCard1.addActionListener(new ChooseCardListener(card1));
        buttonCard2.addActionListener(new ChooseCardListener(card2));
        panelCard1.add(buttonCard1);
        panelCard2.add(buttonCard2);
        cardChoiceBuoyPanel.add(panelCard1);
        cardChoiceBuoyPanel.add(panelCard2);
        switchToPanel(CHOOSE_CARD_BUOY_PANEL);

    }

    /**
     * Builds the panel used to choose a target for a card.
     */
    public void buildCardTargetPanel() {
        targetMap = new HashMap<>();
        for (Player player : board.getPlayerList()) {
            var box = new JCheckBox(player.getName());
            choosePlayerTargetPanelPanelPlayers.add(box);
            box.addItemListener(new ActionChoiceItemListener());
            targetMap.put(player, box);
        }
    }

    /**
     * Builds the eastern part of the main panel.
     */
    private void buildEastPanel() {
        eastPanel = new JPanel(new GridLayout(nbPlayers / 2, 1));
        mainPanel.add(eastPanel, BorderLayout.EAST);
    }

    /**
     * Builds the southern part of the main panel.
     */
    private void buildSouthPanel() {
        southPanel = new JPanel(new BorderLayout());
        mainPanel.add(southPanel, BorderLayout.SOUTH);
        var hiddenCardPanelContainer = new JPanel();
        var revealedCardPanelContainer = new JPanel();

        var playerStatePanel = new JPanel(new GridLayout(2, 1, 10, 0));
        var chiefPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        var statePanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        chiefLabel = new JLabel("");
        stateLabel = new JLabel(PlayerState.HEALTHY.toString());

        chiefPanel.add(chiefLabel);
        statePanel.add(stateLabel);
        playerStatePanel.add(statePanel);
        playerStatePanel.add(chiefPanel);

        var hiddenCardPanel = new JPanel();
        var revealedCardPanel = new JPanel();
        var hiddenCardPanelScrollable = new JScrollPane(hiddenCardPanel, VERTICAL_SCROLLBAR_NEVER,
                HORIZONTAL_SCROLLBAR_AS_NEEDED);
        var revealedCardPanelScrollable = new JScrollPane(revealedCardPanel, VERTICAL_SCROLLBAR_NEVER,
                HORIZONTAL_SCROLLBAR_AS_NEEDED);
        playerStatePanel.setPreferredSize(new Dimension(20, 20));
        hiddenCardPanelScrollable.setPreferredSize(new Dimension(900, 110));
        revealedCardPanelScrollable.setPreferredSize(new Dimension(900, 110));
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
        hiddenCardPanelPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 0));
        hiddenCardPanel.add(hiddenCardPanelPanel);
        revealedCardPanelPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 0));
        revealedCardPanel.add(revealedCardPanelPanel);

        var hiddenCardLabel = new JLabel(stringsBundle.getString("hiddenCards"));
        var revealedCardLabel = new JLabel(stringsBundle.getString("revealedCards"));
        hiddenCardLabelPanel.add(hiddenCardLabel);
        revealedCardLabelPanel.add(revealedCardLabel);

    }

    private JPanel buildNorthPanel() {
        var northPanel = new JPanel(new GridLayout(1, 2));
        var northWestPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        var northEastPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        northPanel.add(northWestPanel);
        northPanel.add(northEastPanel);
        mainPanel.add(northPanel, BorderLayout.NORTH);

        var resourcesPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 20, 0));
        northWestPanel.add(resourcesPanel);

        var foodPanel = new JPanel();
        var waterPanel = new JPanel();
        var woodFragmentsPanel = new JPanel();
        var woodPlanksPanel = new JPanel();

        resourcesPanel.add(foodPanel);
        resourcesPanel.add(waterPanel);
        resourcesPanel.add(woodFragmentsPanel);
        resourcesPanel.add(woodPlanksPanel);

        var foodLabel = new JLabel(stringsBundle.getString("food") + " : ");
        foodQuantityLabel = new JLabel("0");
        foodPanel.add(foodLabel);
        foodPanel.add(foodQuantityLabel);
        var waterLabel = new JLabel(stringsBundle.getString(waterString) + " : ");
        waterQuantityLabel = new JLabel("0");
        waterPanel.add(waterLabel);
        waterPanel.add(waterQuantityLabel);
        var woodFragmentsLabel = new JLabel(stringsBundle.getString("woodPlankFragments") + " : ");
        woodFragmentsQuantityLabel = new JLabel("0");
        woodFragmentsPanel.add(woodFragmentsLabel);
        woodFragmentsPanel.add(woodFragmentsQuantityLabel);
        var woodPlanksLabel = new JLabel(stringsBundle.getString("woodPlanks") + " : ");
        woodPlanksQuantityLabel = new JLabel("0");
        woodPlanksPanel.add(woodPlanksLabel);
        woodPlanksPanel.add(woodPlanksQuantityLabel);

        var roundDataPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT, 20, 0));
        northEastPanel.add(roundDataPanel);
        var weatherPanel = new JPanel();
        var nbAlivePanel = new JPanel();
        var roundNumberPanel = new JPanel();

        roundDataPanel.add(weatherPanel);
        roundDataPanel.add(nbAlivePanel);
        roundDataPanel.add(roundNumberPanel);
        var weatherLabel = new JLabel(stringsBundle.getString("weather") + " : ");
        weatherLabelNumber = new JLabel("0");
        var nbAliveLabel = new JLabel(stringsBundle.getString("alive") + " : ");
        nbAliveLabelNumber = new JLabel("0");
        var roundLabel = new JLabel(stringsBundle.getString("round") + " : ");
        roundLabelNumber = new JLabel("0");
        weatherPanel.add(weatherLabel);
        weatherPanel.add(weatherLabelNumber);
        nbAlivePanel.add(nbAliveLabel);
        nbAlivePanel.add(nbAliveLabelNumber);
        roundNumberPanel.add(roundLabel);
        roundNumberPanel.add(roundLabelNumber);

        return northPanel;

    }

    /**
     * Builds the displays of the computer players.
     */
    public void buildPlayersDisplay(int indexOfThisPlayer) {
        this.indexOfThisPlayer = indexOfThisPlayer;
        List<JPanel> listPlayerDisplays = new ArrayList<>();
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

    /**
     * Builds a panel for a card.
     * 
     * @param card the card for which the panel is built
     * @return the built panel
     */
    private JPanel buildCard(Card card) {
        var cardPanel = new JPanel();
        var img = ((ImageIcon) card.getRevealedCardIcon()).getImage();
        var newImg = img.getScaledInstance(SOUTH_BUTTON_WIDTH, SOUTH_BUTTON_HEIGHT, java.awt.Image.SCALE_SMOOTH);
        var icon = new ImageIcon(newImg);
        var cardButton = new JButton(icon);
        cardButton.setToolTipText(card.getCardName());
        cardButton.setPreferredSize(new Dimension(SOUTH_BUTTON_WIDTH, SOUTH_BUTTON_HEIGHT));
        cardButton.setForeground(Color.WHITE);
        cardButton.addActionListener(card.getActionListener());
        cardPanel.add(cardButton);
        return cardPanel;
    }

    /**
     * Adds a revealed card to the revealedCardPanel.
     * 
     * @param card the card to add
     */
    private void addRevealedCard(Card card) {
        JPanel cardPanel = buildCard(card);
        revealedCardPanelPanel.add(cardPanel);
    }

    /**
     * Adds a hidden card to the hiddenCardPanel.
     * 
     * @param card the card to add
     */
    private void addHiddenCard(Card card) {
        JPanel cardPanel = buildCard(card);
        hiddenCardPanelPanel.add(cardPanel);
    }

    /**
     * Updates the display of the user, the non computer player, which means their
     * cards, and state.
     */
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

    /**
     * Updates the name of the current player, in the main panel.
     */
    public void updateCurrentPlayer() {
        boolean playerNameBool = board.getIndexCurrentPlayer() >= 0
                && board.getIndexCurrentPlayer() < board.getPlayerList().size();
        String playerName;
        if (playerNameBool) {
            playerName = board.getPlayerList().get(board.getIndexCurrentPlayer()).toString();

            var isThisPlayerTurn = board.getPlayerList().get(board.getIndexCurrentPlayer())
                    .equals(board.getThisPlayer());
            southPanel.setBorder(BorderFactory.createLineBorder(isThisPlayerTurn ? Color.RED : Color.BLACK));
        } else {
            playerName = "";
        }
        notificationLabel.setText(stringsBundle.getString("currentPlayer") + " : " + playerName);
    }

    /**
     * Makes the user choose an action for this round.
     */
    public void makePlayerChooseAction() {
        nextButton.setEnabled(false);
        switchToPanel(CHOOSE_ACTION_PANEL);
    }

    /**
     * Asks the user to choose, as the chief, the player who will be sacrificed in a
     * given list.
     * 
     * @param pickablePlayers the list of player who can be designated
     */
    public void makePlayerChiefDesignates(List<Player> pickablePlayers) {
        choosePlayerPanelPanel.removeAll();
        switchToPanel(CHOOSE_PLAYER_PANEL);

        for (Player player : pickablePlayers) {
            var voteButton = new JButton(player + "");
            choosePlayerPanelPanel.add(voteButton);
            voteButton.addActionListener(new ChiefVoteListener(player));
        }
    }

    /**
     * Asks the user to choose a player in a given list.
     * 
     * @param pickablePlayers the list of pickable players
     */
    public void makePlayerVoteFor(List<Player> pickablePlayers) {
        choosePlayerPanelPanel.removeAll();
        var numberRows = (int) Math.ceil(Math.sqrt(1.0 * pickablePlayers.size()));
        choosePlayerPanelPanel.setLayout(new GridLayout(numberRows, numberRows, 5, 5));
        switchToPanel(CHOOSE_PLAYER_PANEL);

        for (Player player : pickablePlayers) {
            var voteButton = new JButton(player + "");
            var voteButtonPanel = new JPanel();
            voteButtonPanel.add(voteButton);
            changeFont(voteButton, 16);
            choosePlayerPanelPanel.add(voteButtonPanel);
            voteButton.addActionListener(new VoteListener(player));
            voteButton.setPreferredSize(new Dimension(VOTE_BUTTON_WIDTH, VOTE_BUTTON_HEIGHT));
        }
    }

    /**
     * Starts the vote session.
     */
    public void allowPlayerToBeginVoteSession() {
        nextButton.setEnabled(false);
        beginVoteButton.setVisible(true);
    }

    /**
     * Asks the player to validate a designated player, to be sacrificed.
     * 
     * @param forDeparture
     */
    public void allowPlayerToKillPlayerAfterVote(boolean forDeparture) {
        nextButton.setEnabled(false);
        if (forDeparture) {
            allowKillForDeparture.setVisible(true);
        } else {
            allowKillNotForDeparture.setVisible(true);
        }

    }

    /**
     * Displays a message on the interface.
     * 
     * @param text the message to be displayed
     */
    public void displayMessage(String text) {

        String newText = notificationPanelTextPane.getText() + text + "\n";
        notificationPanelTextPane.setText(newText);

    }

    /**
     * Updates the resources display in the interface.
     * 
     * @param foodRations   the new number of food rations
     * @param waterRations  the new number of water rations
     * @param woodFragments the new number of plank plank fragments
     * @param woodPlanks    the new number of planks
     * @param weather       the current weather
     * @param nbAlive       the number of players alive
     * @param round         the current round
     */
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

    /**
     * Triggers the end of the game on the interface.
     */
    public void endGame() {
        updateSouth();
        nextButton.setVisible(false);
        allowedToPlayCard = false;
    }

    /**
     * Changes the font size of a given component.
     * 
     * @param component the component which will have its font changed
     * @param size      the new size of the font
     */
    private void changeFont(JComponent component, int size) {
        component.setFont(new Font(component.getFont().getName(), component.getFont().getStyle(), size));
    }

    /**
     * Changes the central panel to another panel, with a cardLayout.
     * 
     * @param panelName the name of the new panel
     */
    private void switchToPanel(String panelName) {
        if (!previousPanel.equals(currentPanel) && !currentPanel.equals(CHOOSE_CARD_RUM_PANEL)
                && !currentPanel.equals(CHOOSE_PLAYER_TARGET)) {
            previousPanel = currentPanel;
        }

        cardLayoutCentralPanel.show(centerPanelCenterChoicePanel, panelName);
        currentPanel = panelName;
    }

    /**
     * The getter for the attribute {@link MainBoardFront#mainPanel}.
     * 
     * @return the main panel of the interface
     */
    public JPanel getMainPanel() {
        return mainPanel;
    }

    /**
     * The getter for the attribute {@link MainBoardFront#nextButton}.
     * 
     * @return the next button
     */
    public JButton getNextButton() {
        return nextButton;
    }

    /**
     * Sets the display of the number of alive players to a new value.
     * 
     * @param nbAlive the number of alive players
     */
    public void setNbAliveDisplay(int nbAlive) {
        nbAliveLabelNumber.setText(nbAlive + "");
    }

    /**
     * Sets the display of the number of food rations to a new value.
     * 
     * @param nbAlive the number of food rations
     */
    public void setFoodRationDisplay(int nbFoodRations) {
        foodQuantityLabel.setText(nbFoodRations + "");
    }

    /**
     * Sets the display of the number of water rations to a new value.
     * 
     * @param nbAlive the number of water rations
     */
    public void setWaterRationDisplay(int nbWaterRations) {
        waterQuantityLabel.setText(nbWaterRations + "");
    }

    /**
     * Sets the display of the number of planks fragments to a new value.
     * 
     * @param nbAlive the number of plank fragments
     */
    public void setFragmentsDisplay(int nbWoodFragments) {
        woodFragmentsQuantityLabel.setText(nbWoodFragments + "");
    }

    /**
     * Sets the display of the number of planks to a new value.
     * 
     * @param nbAlive the number of planks
     */
    public void setPlanksDisplay(int nbWoodPlanks) {
        woodPlanksQuantityLabel.setText(nbWoodPlanks + "");
    }

    /**
     * Sets the display of the current weather to a new value.
     * 
     * @param nbAlive the current weather
     */
    public void setWeatherDisplay(int weather) {
        weatherLabelNumber.setText(weather + "");
    }

    /**
     * Sets the display of the round to a new value.
     * 
     * @param nbAlive the current round
     */
    public void setRoundDisplay(int round) {
        roundLabelNumber.setText(round + "");
    }

    /**
     * The setter for the attribute {@link MainBoardFront#board}.
     * 
     * @param board the board represented by this interface
     */
    public void setBoard(Board board) {
        this.board = board;
    }

    /**
     * The setter for the attribute {@link MainBoardFront#allowedToPlayCard}.
     * 
     * @param board the board represented by this interface
     */
    public void setAllowedToPlayCard(boolean allowedToPlayCard) {
        this.allowedToPlayCard = allowedToPlayCard;
    }

    /**
     * The action listener used to switch to the next action in the game, such as an
     * action in a round or a vote.
     */
    private class NextActionListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            board.play(board.nextPlayer());
        }
    }

    /**
     * The action listener called when the user validates a choice of a player for a
     * sacrifice.
     */
    private class AllowKillListener implements ActionListener {
        boolean forDeparture;

        /**
         * Builds an action listener called when the user validates a choice of a
         * player.
         * 
         * @param forDeparture indicates whether the player is selected because of a
         *                     lack of a departure or a round end
         */
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

    /**
     * An action listener called when the user starts a voting session.
     */
    private class BeginVoteListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            board.choosePlayerToDie();
            ((JButton) e.getSource()).setVisible(false);
        }
    }

    /**
     * An action listener called when the user chooses the food action.
     */
    private class FoodActionListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            var player = board.getThisPlayer();
            player.playerSeeksFood(board);
            switchToPanel(CHOOSE_VOID_PANEL);
            for (Player watcher : board.getPlayerList()) {
                watcher.addOpinionOn(player, ActionType.FOOD.getImpactOnOpinion(), board.getDifficulty(),
                        MainBoardFront.this);
            }
            nextButton.setEnabled(true);
            if (player.equals(board.getTwicePlayingPlayer())) {
                board.playerWillPlayTwice(player);
            }
        }

    }

    /**
     * An action listener called when the user chooses the water action.
     */
    private class WaterActionListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            var player = board.getThisPlayer();
            player.playerSeeksWater(board);
            nextButton.setEnabled(true);
            switchToPanel(CHOOSE_VOID_PANEL);
            for (Player watcher : board.getPlayerList()) {
                watcher.addOpinionOn(player, ActionType.WATER.getImpactOnOpinion(), board.getDifficulty(),
                        MainBoardFront.this);
            }
            if (player.equals(board.getTwicePlayingPlayer())) {
                board.playerWillPlayTwice(player);
            }

        }

    }

    /**
     * An action listener called when the user chooses the wood action.
     */
    private class WoodActionListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            switchToPanel(CHOOSE_WOOD_TRIES_PANEL);
        }

    }

    /**
     * An action listener called when the user chooses how many fragments they will
     * pick in the wood action.
     */
    private class WoodTryListener implements ActionListener {
        int nbTries;

        /**
         * Builds an action listener called when a player chooses how many fragments
         * they are gathering for the wood action.
         * 
         * @param nbTries the number of fragments targeted
         */
        private WoodTryListener(int nbTries) {
            this.nbTries = nbTries;
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            var player = board.getThisPlayer();
            player.playerSeeksWood(board, nbTries, board.getWhipperPlayer(), board.getWhippedPlayer());
            nextButton.setEnabled(true);
            switchToPanel(CHOOSE_VOID_PANEL);
            for (Player watcher : board.getPlayerList()) {
                watcher.addOpinionOn(player, ActionType.WOOD.getImpactOnOpinion(), board.getDifficulty(),
                        MainBoardFront.this);
            }
            if (player.equals(board.getTwicePlayingPlayer())) {
                board.playerWillPlayTwice(player);
            }

        }
    }

    /**
     * An action listener called when the user chooses the card action.
     */
    private class CardActionListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            var player = board.getThisPlayer();

            var buoy = board.getThisPlayer().getCardType(BuoyExpansion.class);
            if (buoy != null && buoy.isCardRevealed()) {
                var card1 = board.getThisPlayer().pickCard(board);
                var card2 = board.getThisPlayer().pickCard(board);
                buildCardChoiceBuoyPanel(card1, card2);
            } else {
                player.playerSeeksCard(board);
                nextButton.setEnabled(true);
                updateSouth();
                switchToPanel(CHOOSE_VOID_PANEL);
                for (Player watcher : board.getPlayerList()) {
                    watcher.addOpinionOn(player, ActionType.CARD.getImpactOnOpinion(), board.getDifficulty(),
                            MainBoardFront.this);
                }
            }
            if (player.equals(board.getTwicePlayingPlayer())) {
                board.playerWillPlayTwice(player);
            }
        }
    }

    private class ChooseCardListener implements ActionListener {

        Card target;

        public ChooseCardListener(Card target) {
            this.target = target;
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            var player = board.getThisPlayer();
            board.getMainBoardFront().displayMessage(player + stringsBundle.getString("gotCard"));
            player.addCardToInventory(target);
            nextButton.setEnabled(true);
            updateSouth();
            switchToPanel(CHOOSE_VOID_PANEL);
            for (Player watcher : board.getPlayerList()) {
                watcher.addOpinionOn(player, ActionType.CARD.getImpactOnOpinion(), board.getDifficulty(),
                        MainBoardFront.this);
            }
        }

    }

    /**
     * An action listener called when the user chooses one player to be sacrificed.
     */
    private class VoteListener implements ActionListener {

        Player target;

        /**
         * Builds an action listener called to save the vote of the user for the target
         * player.
         * 
         * @param target the player targeted by the user
         */
        public VoteListener(Player target) {
            this.target = target;
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            switchToPanel(CHOOSE_VOID_PANEL);

            board.getVotes().get(board.getThisPlayer()).add(target);
            target.addOpinionOn(board.getThisPlayer(), Player.IMPACT_VOTE_ON_OPINION, board.getDifficulty(),
                    MainBoardFront.this);
            if (!board.checkVoteNonOwnersOver()) {
                board.voteTimeForNonOwners();
            } else if (!board.checkVoteOwnersOver()) {
                board.voteTimeForOwners();
            } else {
                board.endOfVote();
            }
        }

    }

    /**
     * An action listener called when the user, as the chief, designates a player
     * for a sacrifice.
     */
    private class ChiefVoteListener extends VoteListener {
        /**
         * Builds an action listener called when the user chief designates a player.
         * 
         * @param target the designated player
         */
        public ChiefVoteListener(Player target) {
            super(target);
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            switchToPanel(CHOOSE_VOID_PANEL);

            board.setDesignated(target);
            target.addOpinionOn(board.getThisPlayer(), Player.IMPACT_CHIEF_DESIGNATION_ON_OPINION,
                    board.getDifficulty(), MainBoardFront.this);
            board.roundEnd(board.getCurrentlyForDeparture());
        }
    }

    /**
     * An action listener called when the user clicks on one of their cards.
     */
    public class CardPlayerActionListener implements ActionListener {
        Card card;

        /**
         * Builds an action listener called when the user clicks on one of their cards,
         * which uses no parameter.
         * 
         * @param card the card clicked
         */
        public CardPlayerActionListener(Card card) {
            this.card = card;
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            if (card.equals(cardCurrentlyUsed)) {
                cardCurrentlyUsed = null;
                switchToPanel(board.isRumDistributionActive() ? CHOOSE_CARD_RUM_PANEL : previousPanel);
            } else {
                actionValidate.setEnabled(card.canBeUsed());
                cardCurrentlyUsed = card;
                nbTargetsRequired = 0;
                nbActionRequired = 0;
                nbCardRequired = 0;
                switchToPanel(CHOOSE_PLAYER_TARGET);
                choosePlayerTargetLabel.setText(stringsBundle.getString("cardDescription") + " : " + card.toString());
                choosePlayerTargetPanelPanelAction.setVisible(false);
                choosePlayerTargetPanelPanelPlayers.setVisible(false);
                choosePlayerTargetPanelPanelCard.setVisible(false);
                cardDescription.setText(card.getCardDescription());
            }
        }
    }

    /**
     * An action listener called when the user clicks on one of their card, which
     * uses one player as a parameter.
     */
    public class CardPlayerActionListenerOneTarget extends CardPlayerActionListener {

        /**
         * Builds an action listener called when the user clicks on one of their card,
         * which uses one player as a parameter.
         * 
         * @param card the card clicked
         */
        public CardPlayerActionListenerOneTarget(Card card) {
            super(card);
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            if (card.equals(cardCurrentlyUsed)) {
                cardCurrentlyUsed = null;
                switchToPanel(board.isRumDistributionActive() ? CHOOSE_CARD_RUM_PANEL : previousPanel);
            } else {
                super.actionPerformed(e);
                switchToPanel(CHOOSE_PLAYER_TARGET);
                nbTargetsRequired = 1;
                nbActionRequired = 0;
                nbCardRequired = 0;
                choosePlayerTargetPanelPanelAction.setVisible(false);
                choosePlayerTargetPanelPanelPlayers.setVisible(true);
                choosePlayerTargetPanelPanelCard.setVisible(false);
                for (Entry<Player, JCheckBox> entry : targetMap.entrySet()) {
                    var box = entry.getValue();
                    box.setEnabled(cardCurrentlyUsed.getRequiredState().contains(entry.getKey().getState()));
                    box.setSelected(false);
                }
            }
        }

    }

    /**
     * An action listener called when the user clicks on one of their card, which
     * uses one player and one action as parameters.
     */
    public class CardPlayerActionListenerOneTargetOneAction extends CardPlayerActionListener {

        /**
         * Builds an action listener called when the user clicks on one of their card,
         * which uses one player and one action as parameters.
         * 
         * @param card the card clicked
         */
        public CardPlayerActionListenerOneTargetOneAction(Card card) {
            super(card);
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            if (card.equals(cardCurrentlyUsed)) {
                cardCurrentlyUsed = null;
                switchToPanel(board.isRumDistributionActive() ? CHOOSE_CARD_RUM_PANEL : previousPanel);
            } else {
                super.actionPerformed(e);
                switchToPanel(CHOOSE_PLAYER_TARGET);
                nbTargetsRequired = 1;
                nbActionRequired = 1;
                nbCardRequired = 0;
                choosePlayerTargetPanelPanelAction.setVisible(true);
                choosePlayerTargetPanelPanelPlayers.setVisible(true);
                choosePlayerTargetPanelPanelCard.setVisible(false);
                for (Entry<Player, JCheckBox> entry : targetMap.entrySet()) {
                    var box = entry.getValue();
                    box.setEnabled(cardCurrentlyUsed.getRequiredState().contains(entry.getKey().getState()));
                    box.setSelected(false);
                }
            }
        }
    }

    /**
     * An action listener called when the user clicks on one of their card, which
     * uses up to three players as parameters.
     */
    public class CardPlayerActionListenerThreeTargets extends CardPlayerActionListener {

        /**
         * Builds an action listener called when the user clicks on one of their card,
         * which uses up to three players as parameters.
         * 
         * @param card the card clicked
         */
        public CardPlayerActionListenerThreeTargets(Card card) {
            super(card);
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            if (card.equals(cardCurrentlyUsed)) {
                cardCurrentlyUsed = null;
                switchToPanel(board.isRumDistributionActive() ? CHOOSE_CARD_RUM_PANEL : previousPanel);
            } else {
                super.actionPerformed(e);
                nbTargetsRequired = 3;
                nbActionRequired = 0;
                nbCardRequired = 0;
                switchToPanel(CHOOSE_PLAYER_TARGET);
                choosePlayerTargetPanelPanelAction.setVisible(false);
                choosePlayerTargetPanelPanelPlayers.setVisible(true);
                choosePlayerTargetPanelPanelCard.setVisible(false);
                for (Entry<Player, JCheckBox> entry : targetMap.entrySet()) {
                    var box = entry.getValue();
                    box.setEnabled(cardCurrentlyUsed.getRequiredState().contains(entry.getKey().getState()));
                    box.setSelected(false);
                }
            }
        }
    }

    public class CardPlayerActionListenerOneTargetOneCard extends CardPlayerActionListener {

        /**
         * Builds an action listener called when the user clicks on one of their card,
         * which uses up to three players as parameters.
         * 
         * @param card the card clicked
         */
        public CardPlayerActionListenerOneTargetOneCard(Card card) {
            super(card);
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            if (card.equals(cardCurrentlyUsed)) {
                cardCurrentlyUsed = null;
                switchToPanel(board.isRumDistributionActive() ? CHOOSE_CARD_RUM_PANEL : previousPanel);
            } else {
                super.actionPerformed(e);

                nbTargetsRequired = 1;
                nbActionRequired = 0;
                nbCardRequired = 1;
                switchToPanel(CHOOSE_PLAYER_TARGET);
                choosePlayerTargetPanelPanelCard.removeAll();
                choosePlayerTargetPanelPanelAction.setVisible(false);
                choosePlayerTargetPanelPanelCard.setVisible(true);
                choosePlayerTargetPanelPanelPlayers.setVisible(true);
                for (Entry<Player, JCheckBox> entry : targetMap.entrySet()) {
                    var box = entry.getValue();
                    box.setEnabled(cardCurrentlyUsed.getRequiredState().contains(entry.getKey().getState()));
                    box.setSelected(false);
                }
            }
        }
    }

    /**
     * An action listener called when the user validate the parameters of a card
     * previously clicked.
     */
    public class ValidateCardUseListener implements ActionListener {

        /**
         * Builds an array of players selected with checkboxes.
         * 
         * @return the array of selected players
         */
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
                Card card = null;

                for (Entry<ActionType, JCheckBox> entry : actionCheckBoxMap.entrySet()) {
                    if (entry.getValue().isSelected()) {
                        action = entry.getKey();
                    }
                }
                for (Entry<Card, JCheckBox> entry : cardCheckBoxMap.entrySet()) {
                    if (entry.getValue().isSelected()) {
                        card = entry.getKey();
                    }
                }

                cardCurrentlyUsed.useCard(targetsArray[0], targetsArray[1], targetsArray[2], action, card);
                cardCurrentlyUsed = null;
                nbTargetsRequired = 0;
                updateSouth();
                board.updateDisplayResources();

                switchToPanel(board.isRumDistributionActive() ? CHOOSE_CARD_RUM_PANEL : previousPanel);

            }
        }
    }

    /**
     * An item listener called when the user chooses the parameters for a previously
     * clicked card.
     */
    private class ActionChoiceItemListener implements ItemListener {

        /**
         * Displays cards card which are available to be robbed.
         * 
         * @param nbTargetsSelected the number of selected target players
         * @param nbCardSelected    the number of selected cards
         */
        private void buildCardChoiceTaser(int nbTargetsSelected, int nbCardSelected) {

            if (nbTargetsSelected == 1 && nbCardSelected == 0 && nbCardRequired == 1) {

                Player selectedPlayer = null;
                for (Entry<Player, JCheckBox> entry : targetMap.entrySet()) {
                    if (entry.getValue().isSelected()) {

                        selectedPlayer = entry.getKey();

                        cardCheckBoxMap.clear();
                        for (Card card : selectedPlayer.getInventoryRevealed()) {

                            var box = new JCheckBox(card.getCardName());
                            box.addItemListener(new ActionChoiceItemListener());

                            cardCheckBoxMap.put(card, box);
                            choosePlayerTargetPanelPanelCard.add(box);

                        }

                    }
                }
            }

        }

        @Override
        public void itemStateChanged(ItemEvent e) {
            var nbTargetsSelected = 0;
            var nbActionSelected = 0;
            var nbCardSelected = 0;

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
            for (Entry<Card, JCheckBox> entry : cardCheckBoxMap.entrySet()) {
                if (entry.getValue().isSelected()) {
                    nbCardSelected++;
                }
            }

            buildCardChoiceTaser(nbTargetsSelected, nbCardSelected);

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
            for (Entry<Card, JCheckBox> entry : cardCheckBoxMap.entrySet()) {
                if (!entry.getValue().isSelected()) {
                    entry.getValue().setEnabled(nbCardSelected != 1);
                }
            }
            actionValidate.setEnabled(
                    cardCurrentlyUsed.canBeUsed() && !board.isRumDistributionActive() && nbTargetsRequired > 0
                            && allowedToPlayCard && nbTargetsSelected >= 1 && nbTargetsSelected <= nbTargetsRequired
                            && nbActionSelected == nbActionRequired && nbCardSelected == nbCardRequired);

        }
    }

    private class ChoiceCardRumActionListener implements ActionListener {
        private Card selectedCard;

        public ChoiceCardRumActionListener(Card selectedCard) {
            this.selectedCard = selectedCard;
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            int indexThisPlayer = board.getPlayerList().indexOf(board.getThisPlayer());
            var target = board.getPlayerAliveAfterBefore(indexThisPlayer, true);
            board.getThisPlayer().removeCard(selectedCard);
            target.addCardToInventory(selectedCard);
            board.getRumDistributionList().remove(board.getThisPlayer());
            displayMessage(String.format(stringsBundle.getString("rumDistributionEvent"), board.getThisPlayer(),
                    selectedCard, target));
            nextButton.setEnabled(true);

            cardChoiceRumPanel.removeAll();
            board.rumDistributionEnd();
        }
    }
}
