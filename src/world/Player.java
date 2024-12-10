package world;

import java.util.List;

/**
 * The Player interface defines common actions and attributes of a player in the game.
 * This abstraction ensures that all player types (human or AI) implement core 
 * functionalities to interact with the game world.
 */
public interface Player {

  // ------------------- Player Basic Information -------------------

  /**
   * Gets the name of the player.
   *
   * @return The player's name.
   */
  String getName();

  /**
   * Gets the current health of the player.
   *
   * @return The player's health.
   */
  int getHealth();

  /**
   * Sets the health of the player.
   *
   * @param health The new health value for the player.
   * @throws IllegalArgumentException If the health value is negative.
   */
  void setHealth(int health);

  // ------------------- Player Inventory Management -------------------

  /**
   * Allows the player to pick up an item in their current space.
   *
   * @param item The item to pick up.
   * @throws IllegalArgumentException If the item is null.
   * @throws IllegalStateException If the player's inventory is full.
   */
  void pickUpItem(Item item);

  /**
   * Gets the list of items the player currently possesses.
   *
   * @return A list of items in the player's inventory.
   */
  List<Item> getItems();

  /**
   * Gets the inventory object for the player.
   *
   * @return The player's inventory.
   */
  PlayerInventory getInventory();

  // ------------------- Player Movement -------------------

  /**
   * Gets the current space where the player is located.
   *
   * @return The current space of the player.
   */
  Space getCurrentSpace();

  /**
   * Sets the current space of the player.
   *
   * @param space The new space for the player.
   * @throws IllegalArgumentException If the space is null.
   */
  void setCurrentSpace(Space space);

  /**
   * Moves the player to a new space.
   *
   * @param newSpace The target space to move to.
   * @throws IllegalArgumentException If the new space is null.
   */
  void move(Space newSpace);

  // ------------------- Player Combat Actions -------------------

  /**
   * Attempts to attack another player.
   *
   * @param target The target player to attack.
   * @throws IllegalArgumentException If the target is null.
   * @throws IllegalStateException If the target is not in range.
   */
  void attemptAttack(Player target);

  /**
   * Reduces the health of the player by a specified amount of damage.
   *
   * @param damage The amount of health to reduce.
   * @throws IllegalArgumentException If the damage value is negative.
   */
  void reduceHealth(int damage);

  // ------------------- Player Turn Management -------------------

  /**
   * Checks if it is currently this player's turn.
   *
   * @return True if it is this player's turn, otherwise false.
   */
  boolean isCurrentTurn();

  /**
   * Sets whether it is currently this player's turn.
   *
   * @param isTurn True if it is this player's turn, otherwise false.
   */
  void setCurrentTurn(boolean isTurn);

  // ------------------- Player Visibility Management -------------------

  /**
   * Checks if this player can see another player.
   *
   * @param player The player to check visibility for.
   * @return True if the specified player is visible, otherwise false.
   * @throws IllegalArgumentException If the player is null.
   */
  boolean canSee(Player player);

  /**
   * Sets the visibility of another player for this player.
   *
   * @param player The player whose visibility is being set.
   * @param canSee True if the player is visible, otherwise false.
   * @throws IllegalArgumentException If the player is null.
   */
  void setCanSee(Player player, boolean canSee);
}
