package world;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;

/**
 * HumanPlayer class represents a player controlled by a human in the game.
 */
public class HumanPlayer implements Player {

  private static final Logger logger = Logger.getLogger(HumanPlayer.class.getName());

  private String name;
  private int health;
  private Space currentSpace;
  private final PlayerInventory inventory;
  private final Map<Player, Boolean> visibilityMap; // Tracks visibility of other players
  private boolean isCurrentTurn; // Indicates if it's this player's turn

  /**
   * Initializes a HumanPlayer with the given parameters.
   *
   * @param name          The name of the player.
   * @param health        The initial health of the player.
   * @param startingSpace The starting space of the player.
   */
  public HumanPlayer(String name, int health, Space startingSpace) {
    if (name == null || name.isEmpty()) {
      throw new IllegalArgumentException("Player name cannot be null or empty.");
    }
    if (health <= 0) {
      throw new IllegalArgumentException("Health must be greater than zero.");
    }

    this.name = name;
    this.health = health;
    this.currentSpace = startingSpace;
    this.inventory = new PlayerInventory(10); // Default inventory capacity of 10
    this.visibilityMap = new HashMap<>();
    this.isCurrentTurn = false;

    if (startingSpace != null) {
      startingSpace.addPlayer(this);
    }
  }

  @Override
  public String getName() {
    return name;
  }

  @Override
  public int getHealth() {
    return health;
  }

  @Override
  public void setHealth(int health) {
    if (health < 0) {
      throw new IllegalArgumentException("Health cannot be negative.");
    }
    this.health = health;
  }

  @Override
  public Space getCurrentSpace() {
    return currentSpace;
  }

  @Override
  public void setCurrentSpace(Space space) {
    if (space == null) {
      throw new IllegalArgumentException("Space cannot be null.");
    }
    if (currentSpace != null) {
      currentSpace.removePlayer(this); // Remove from the old space
    }
    currentSpace = space;
    currentSpace.addPlayer(this); // Add to the new space
  }

  @Override
  public void move(Space newSpace) {
    if (newSpace == null) {
      throw new IllegalArgumentException("Invalid move: The target space is null.");
    }
    setCurrentSpace(newSpace);
    logger.info(() -> name + " moved to " + newSpace.getName());
  }

  @Override
  public void pickUpItem(Item item) {
    if (item == null) {
      throw new IllegalArgumentException("Item cannot be null.");
    }
    if (currentSpace != null && currentSpace.getItems().contains(item)) {
      if (inventory.addItem(item)) {
        currentSpace.removeItem(item);
        logger.info(() -> name + " picked up " + item.getName());
      } else {
        throw new IllegalStateException("Cannot pick up item: Inventory is full.");
      }
    } else {
      throw new IllegalStateException(
          "Item " + item.getName() + " is not available in the current space.");
    }
  }

  @Override
  public void attemptAttack(Player target) {
    if (target == null) {
      throw new IllegalArgumentException("Target player cannot be null.");
    }
    logger.info(() -> name + " attempts to attack " + target.getName());

    // Use the best item for attack, if available
    Item weapon = inventory.getBestItem();
    if (weapon != null) {
      logger.info(() -> name + " uses " + weapon.getName() + " to attack " + target.getName());
      weapon.use(target);
    } else {
      logger.info(() -> name + " has no items to attack with.");
      target.reduceHealth(5); // Default attack without weapon deals 5 damage
    }
  }

  @Override
  public boolean canSee(Player player) {
    return visibilityMap.getOrDefault(player, false);
  }

  @Override
  public void setCanSee(Player player, boolean canSee) {
    if (player == null) {
      throw new IllegalArgumentException("Player cannot be null.");
    }
    visibilityMap.put(player, canSee);
  }

  @Override
  public void reduceHealth(int damage) {
    if (damage < 0) {
      throw new IllegalArgumentException("Damage cannot be negative.");
    }
    health -= damage;
    if (health < 0) {
      health = 0;
    }
    logger.info(() -> name + " now has " + health + " health remaining.");
  }

  @Override
  public PlayerInventory getInventory() {
    return inventory;
  }

  @Override
  public List<Item> getItems() {
    return new ArrayList<>(inventory.getItems());
  }

  @Override
  public boolean isCurrentTurn() {
    return isCurrentTurn;
  }

  @Override
  public void setCurrentTurn(boolean isTurn) {
    this.isCurrentTurn = isTurn;
  }

  /**
   * Provides detailed information about the player.
   *
   * @return A string with the player's name, health, inventory, and current space.
   */
  public String getDetailedInfo() {
    StringBuilder info = new StringBuilder("Player: ").append(name)
        .append(", Health: ").append(health)
        .append(", Current Space: ")
        .append(currentSpace != null ? currentSpace.getName() : "None")
        .append(", Inventory: ");

    if (inventory.getItems().isEmpty()) {
      info.append("Empty");
    } else {
      for (Item item : inventory.getItems()) {
        info.append(item.getName()).append(", ");
      }
      info.setLength(info.length() - 2); // Remove trailing comma and space
    }
    return info.toString();
  }
}
