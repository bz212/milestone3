package world;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

/**
 * TurnManager handles the turn order and manages the sequence of players taking actions.
 */
public class TurnManager {

  private static final Logger LOGGER = Logger.getLogger(TurnManager.class.getName());

  private List<Player> players;
  private int currentTurn;
  private int maxTurns;
  private int turnCount; // Tracks the total number of turns taken

  /**
   * Initializes the TurnManager with the given players and the maximum number of turns allowed.
   *
   * @param players  The list of players in the game.
   * @param maxTurns The maximum number of turns allowed in the game.
   */
  public TurnManager(List<Player> players, int maxTurns) {
    if (players == null || players.isEmpty()) {
      throw new IllegalArgumentException("Player list cannot be null or empty.");
    }
    if (maxTurns <= 0) {
      throw new IllegalArgumentException("Maximum turns must be greater than zero.");
    }

    this.players = new ArrayList<>(players); // Create a copy to prevent external modification
    this.maxTurns = maxTurns;
    this.currentTurn = 0;
    this.turnCount = 0;

    LOGGER.info(
        "TurnManager initialized with " + players.size() + " players and max turns: " + maxTurns);
  }

  /**
   * Adds a player to the turn order.
   *
   * @param player The player to add.
   */
  public void addPlayer(Player player) {
    if (player == null) {
      throw new IllegalArgumentException("Player cannot be null.");
    }

    players.add(player);
    LOGGER.info("Player added: " + player.getName());
  }

  /**
   * Removes a player from the turn order.
   *
   * @param player The player to remove.
   * @return True if the player was successfully removed, false otherwise.
   */
  public boolean removePlayer(Player player) {
    if (player == null) {
      throw new IllegalArgumentException("Player cannot be null.");
    }

    boolean removed = players.remove(player);
    if (removed) {
      LOGGER.info("Player removed: " + player.getName());
    } else {
      LOGGER.warning("Player not found: " + player.getName());
    }
    return removed;
  }

  /**
   * Advances to the next player's turn.
   *
   * @return The player whose turn is next, or null if the game is over.
   */
  public Player nextPlayer() {
    if (isGameOver()) {
      LOGGER.warning("Cannot advance turn: The game is over.");
      return null;
    }

    Player player = players.get(currentTurn);
    LOGGER.info("Current turn: " + player.getName());

    currentTurn = (currentTurn + 1) % players.size();
    turnCount++;

    return player;
  }

  /**
   * Checks if the game is over based on the maximum number of turns or other conditions.
   *
   * @return True if the game is over, false otherwise.
   */
  public boolean isGameOver() {
    if (turnCount >= maxTurns) {
      LOGGER.info("Game over: Maximum number of turns reached.");
      return true;
    }
    if (players.isEmpty()) {
      LOGGER.info("Game over: No players remaining.");
      return true;
    }
    return false;
  }

  /**
   * Gets the current turn count.
   *
   * @return The current turn count.
   */
  public int getTurnCount() {
    return turnCount;
  }

  /**
   * Provides the list of players currently in the game.
   *
   * @return A copy of the list of players.
   */
  public List<Player> getPlayers() {
    return new ArrayList<>(players);
  }
}
