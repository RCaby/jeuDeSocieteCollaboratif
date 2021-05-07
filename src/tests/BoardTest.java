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
    public void beginVotingSessionTest() {
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
        Map<Player, List<Player>> votes = new HashMap<>();
        board.beginVotingSession(pickablePlayers, votingPlayers, votes);
        List<Player> expectedVotingPlayers = new ArrayList<>();
        List<Player> expectedPickablePlayers = new ArrayList<>();
        Map<Player, List<Player>> expectedVotes = new HashMap<>();
        expectedPickablePlayers.add(player1);
        expectedPickablePlayers.add(player2);
        expectedPickablePlayers.add(player4);
        expectedVotingPlayers.add(player0);
        expectedVotingPlayers.add(player1);
        expectedVotingPlayers.add(player1);
        expectedVotingPlayers.add(player4);
        List<Player> voteForP0 = new ArrayList<>();
        List<Player> voteForP1 = new ArrayList<>();
        List<Player> voteForP4 = new ArrayList<>();

        expectedVotes.put(player0, voteForP0);
        expectedVotes.put(player1, voteForP1);
        expectedVotes.put(player4, voteForP4);

        assertEquals(expectedPickablePlayers, pickablePlayers);
        assertEquals(expectedVotes, votes);
        assertEquals(expectedVotingPlayers, votingPlayers);
    }

    @Test
    public void voteResultsTest() {
        Map<Player, List<Player>> results = new HashMap<>();
        Board board = new Board(5);
        Player player0 = board.getPlayerList().get(0);
        Player player1 = board.getPlayerList().get(1);
        Player player2 = board.getPlayerList().get(2);
        Player player3 = board.getPlayerList().get(3);
        Player player4 = board.getPlayerList().get(4);
        List<Player> votesForP0 = new ArrayList<>();
        List<Player> votesForP1 = new ArrayList<>();
        List<Player> votesForP2 = new ArrayList<>();
        List<Player> votesForP3 = new ArrayList<>();
        List<Player> votesForP4 = new ArrayList<>();
        votesForP0.add(player1);
        votesForP0.add(player2);
        votesForP0.add(player3);
        votesForP3.add(player4);
        votesForP3.add(player0);
        results.put(player0, votesForP0);
        results.put(player1, votesForP1);
        results.put(player2, votesForP2);
        results.put(player3, votesForP3);
        results.put(player4, votesForP4);
        board.setThisPlayer(player4);
        board.setChief(player3);
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