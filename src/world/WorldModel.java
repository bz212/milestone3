package world;

import java.util.List;
import strategy.TargetStrategy;

/**
 * The WorldModel interface defines the operations for managing
 * the game world and its entities.
 */
public interface WorldModel {

  /**
   * Retrieves the current player.
   *
   * @return The current player in the game.
   */
  Player getCurrentPlayer();

  /**
   * Retrieves the space at the given grid coordinates.
   *
   * @param x The X-coordinate of the space.
   * @param y The Y-coordinate of the space.
   * @return The Space object at the given coordinates, or null if no space exists.
   */
  Space getSpaceAt(int x, int y);

  /**
   * Initializes the world with the given configuration file and maximum turns.
   *
   * @param worldConfigFile The path to the world configuration file.
   * @param maxTurns        The maximum number of turns for the game.
   */
  void initializeWorld(String worldConfigFile, int maxTurns);

  /**
   * Gets the current state of the world.
   *
   * @return The world object representing the current state.
   */
  Object getWorld();

  /**
   * Moves a player to the specified space.
   *
   * @param spaceName The name of the target space.
   * @return True if the move is successful, false otherwise.
   */
  boolean movePlayer(String spaceName);

  /**
   * Allows a player to pick up an item in the world.
   *
   * @param itemName The name of the item to be picked up.
   * @return True if the item is successfully picked up, false otherwise.
   */
  boolean pickUpItem(String itemName);

  /**
   * Performs an attack on another player.
   *
   * @param playerName The name of the player to attack.
   * @return A message describing the result of the attack.
   */
  String attackPlayer(String playerName);

  /**
   * Checks if the game is over.
   *
   * @return True if the game is over, false otherwise.
   */
  boolean isGameOver();

  /**
   * Sets the strategy for the target's movement.
   *
   * @param strategy The target movement strategy.
   */
  void setStrategy(TargetStrategy strategy);

  /**
   * Gets the current strategy for the target's movement.
   *
   * @return The target movement strategy.
   */
  TargetStrategy getStrategy();

  /**
   * Gets the list of all players in the world.
   *
   * @return The list of players.
   */
  List<Player> getPlayers();

  /**
   * Determines if a space is visible to a player in the world.
   *
   * @param space The space to check.
   * @return True if the space is visible, false otherwise.
   */
  boolean isVisible(Space space);

  /**
   * Provides information about the player's current space and its neighbors.
   *
   * @return A string describing the player's surroundings, including spaces,
   *         items, and other players or entities.
   */
  String lookAround();

  /**
   * Retrieves the name of the space at the specified coordinates.
   *
   * @param x The X-coordinate of the space.
   * @param y The Y-coordinate of the space.
   * @return The name of the space at the given coordinates, or null if no space exists.
   */
  String getSpaceNameAt(int x, int y);

  /**
   * Retrieves the current player's status, including health, inventory, and location.
   *
   * @return A string describing the current player's status.
   */
  String getPlayerStatus();

  /**
   * Retrieves the current player's status.
   *
   * @return A string representing the player's status.
   */
  String getCurrentPlayerStatus();
}
