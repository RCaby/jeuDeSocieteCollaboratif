package tests;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.ResourceBundle;

import org.junit.Test;

import back.ActionType;
import back.Board;
import back.Player;
import back.PlayerState;
import back.cards.Card;
import back.cards.Club;
import back.cards.Conch;
import back.cards.CrystalBall;
import back.cards.Sandwich;
import back.cards.WaterBottle;

public class BoardTest {

    private Locale locale = new Locale("en", "US");
    private ResourceBundle stringsBundle = ResourceBundle.getBundle("Strings", locale);

    @Test
    public void beginVotingSessionTest() {//
        Board board = new Board(5);
        Player player0 = board.getPlayerList().get(0);
        Player player1 = board.getPlayerList().get(1);
        Player player2 = board.getPlayerList().get(2);
        Player player3 = board.getPlayerList().get(3);
        Player player4 = board.getPlayerList().get(4);
        Card conch = new Conch(board, stringsBundle);
        Card club = new Club(board, stringsBundle);
        board.giveCardToPlayer(player0, conch);
        board.giveCardToPlayer(player1, club);

        conch.useCard(null, null, null, ActionType.NONE);
        club.useCard(null, null, null, ActionType.NONE);

        player2.setState(PlayerState.SICK);
        player3.setState(PlayerState.DEAD);
        List<Player> votingPlayers = new ArrayList<>();
        List<Player> pickablePlayers = new ArrayList<>();
        Map<Player, Integer> votes = new HashMap<>();
        board.beginVotingSession(pickablePlayers, votingPlayers, votes);
        List<Player> expectedVotingPlayers = new ArrayList<>();
        List<Player> expectedPickablePlayers = new ArrayList<>();
        Map<Player, Integer> expectedVotes = new HashMap<>();
        expectedPickablePlayers.add(player1);
        expectedPickablePlayers.add(player2);
        expectedPickablePlayers.add(player4);
        expectedVotingPlayers.add(player0);
        expectedVotingPlayers.add(player1);
        expectedVotingPlayers.add(player1);
        expectedVotingPlayers.add(player4);
        expectedVotes.put(player1, 0);
        expectedVotes.put(player2, 0);
        expectedVotes.put(player4, 0);

        assertEquals(expectedPickablePlayers, pickablePlayers);
        assertEquals(expectedVotes, votes);
        assertEquals(expectedVotingPlayers, votingPlayers);
    }

    @Test
    public void crystalBallVoteTest() {
        Board board = new Board(5);
        Card crystalBall = new CrystalBall(board, stringsBundle);
        Player target = board.getPlayerList().get(0);
        crystalBall.useCard(null, null, null, ActionType.NONE);
        board.giveCardToPlayer(target, crystalBall);

        board.crystalBallVote(board.getPlayerList());
        assertEquals(target, board.getPlayerList().get(board.getPlayerList().size() - 1));

    }

    @Test
    public void voteResultsTest() {
        Map<Player, Integer> results = new HashMap<>();
        Board board = new Board(5);
        Player player0 = board.getPlayerList().get(0);
        Player player3 = board.getPlayerList().get(3);
        results.put(player0, 3);
        results.put(player3, 2);
        assertEquals(player0, board.voteResults(results));
    }

    @Test
    public void distributeCardsFromDeadPlayerTest() {
        Board board = new Board(5);
        Card waterBottle = new WaterBottle(board, stringsBundle);
        Card sandwich = new Sandwich(board, stringsBundle);
        Player target = board.getPlayerList().get(2);
        board.giveCardToPlayer(target, waterBottle);
        board.giveCardToPlayer(target, sandwich);
        board.getPlayerList().get(3).setState(PlayerState.DEAD);
        target.setState(PlayerState.DEAD);
        board.distributeCardsFromDeadPlayer(target);
        boolean beforeGotWater = board.getPlayerList().get(1).hasCard(waterBottle)
                && board.getPlayerList().get(4).hasCard(sandwich);
        boolean beforeGotFood = board.getPlayerList().get(1).hasCard(sandwich)
                && board.getPlayerList().get(4).hasCard(waterBottle);
        assertTrue(beforeGotFood || beforeGotWater);
    }

}