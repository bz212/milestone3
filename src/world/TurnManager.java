package world;

import java.util.List;

/**
 * TurnManager handles the turn order and manages the sequence of players taking actions.
 */
public class TurnManager {
    private List<Player> players;
    private int currentTurn;
    private int maxTurns;

    /**
     * Initializes the TurnManager with the given players and the maximum number of turns allowed.
     *
     * @param players The list of players in the game.
     * @param maxTurns The maximum number of turns allowed in the game.
     */
    public TurnManager(List<Player> players, int maxTurns) {
        this.players = players;
        this.maxTurns = maxTurns;
        this.currentTurn = 0;
    }

    /**
     * Adds a player to the turn order.
     *
     * @param player The player to add.
     */
    public void addPlayer(Player player) {
        players.add(player);
    }

    /**
     * Advances to the next player's turn.
     *
     * @return The player whose turn is next.
     */
    public Player nextPlayer() {
        Player player = players.get(currentTurn);
        currentTurn = (currentTurn + 1) % players.size();
        return player;
    }

    /**
     * Checks if the game is over based on the maximum number of turns.
     *
     * @return True if the game is over, false otherwise.
     */
    public boolean isGameOver() {
        return currentTurn >= maxTurns;
    }
}
