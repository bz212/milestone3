package controller;

/**
 * The Features interface defines the actions that can be performed by the user
 * through the game view. This includes moving players, picking up items,
 * looking around, and attacking targets.
 */
public interface Features {

  /**
   * Moves the player to the specified coordinates in the game world.
   *
   * @param x The X-coordinate where the player should move.
   * @param y The Y-coordinate where the player should move.
   */
  void movePlayerTo(int x, int y);

  /**
   * Moves the player to the specified space in the game world.
   *
   * @param spaceName The name of the space where the player should move.
   */
  void movePlayerTo(String spaceName);

  /**
   * Allows the player to pick up an item in their current space.
   *
   * @param itemName The name of the item to pick up.
   */
  void pickUpItem(String itemName);

  /**
   * Allows the player to look around their current space to discover nearby spaces.
   */
  void lookAround();

  /**
   * Allows the player to attack the specified target character.
   *
   * @param targetName The name of the target character to attack.
   */
  void attackPlayer(String targetName);

  /**
   * Starts a new game with the provided world configuration file.
   *
   * @param worldConfigFile The path to the world configuration file.
   * @param maxTurns        The maximum number of turns for the game.
   */
  void startNewGame(String worldConfigFile, int maxTurns);

  /**
   * Quits the current game session.
   */
  void quitGame();

  /**
   * Starts the game session.
   */
  void start();

  /**
   * Checks whether the game is over.
   *
   * @return True if the game is over, false otherwise.
   */
  boolean isGameOver();
}
