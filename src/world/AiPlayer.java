package world;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;
import strategy.AiStrategy;

/**
 * AIPlayer class represents a player controlled by the computer in the game.
 */
public class AiPlayer implements Player {

  private static final Logger logger = Logger.getLogger(AiPlayer.class.getName());

  private final String name;
  private int health;
  private Space currentSpace;
  private final PlayerInventory inventory;
  private AiStrategy strategy;
  private final Map<Player, Boolean> visibilityMap; // Tracks visibility of other players
  private boolean isCurrentTurn; // Indicates if it is currently this player's turn

  /**
   * Constructs an AIPlayer with the given parameters.
   *
   * @param name          The name of the AI player.
   * @param health        The initial health of the AI player.
   * @param startingSpace The starting space of the AI player.
   */
  public AiPlayer(String name, int health, Space startingSpace) {
    if (name == null || name.isEmpty()) {
      throw new IllegalArgumentException("Name cannot be null or empty.");
    }
    if (health <= 0) {
      throw new IllegalArgumentException("Health must be greater than zero.");
    }

    this.name = name;
    this.health = health;
    this.currentSpace = startingSpace;
    this.inventory = new PlayerInventory(10); // Default inventory size = 10
    this.visibilityMap = new HashMap<>();
    this.isCurrentTurn = false;

    if (startingSpace != null) {
      startingSpace.addPlayer(this);
    }
  }

  /**
   * Constructs an AIPlayer with a strategy.
   *
   * @param name          The name of the AI player.
   * @param health        The initial health of the AI player.
   * @param startingSpace The starting space.
   * @param strategy      The AI strategy for actions.
   */
  public AiPlayer(String name, int health, Space startingSpace, AiStrategy strategy) {
    this(name, health, startingSpace);
    if (strategy == null) {
      throw new IllegalArgumentException("Strategy cannot be null.");
    }
    this.strategy = strategy;
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
      currentSpace.removePlayer(this); // Remove AI player from current space
    }

    currentSpace = space;
    currentSpace.addPlayer(this); // Add AI player to the new space
  }

  @Override
  public void move(Space newSpace) {
    if (newSpace != null) {
      setCurrentSpace(newSpace);
    }
  }

  @Override
  public void pickUpItem(Item item) {
    if (item == null) {
      throw new IllegalArgumentException("Invalid item: Cannot be null.");
    }
    if (currentSpace == null || !currentSpace.getItems().contains(item)) {
      throw new IllegalStateException(
          name + " cannot pick up item: " + item.getName() + ". Item not found in current space."
      );
    }

    if (inventory.addItem(item)) {
      currentSpace.removeItem(item);
      logger.info(name + " picked up " + item.getName());
    } else {
      throw new IllegalStateException(
          name + " cannot pick up " + item.getName() + ". Inventory is full."
      );
    }
  }

  @Override
  public void attemptAttack(Player target) {
    if (target == null) {
      throw new IllegalArgumentException("Invalid target: Cannot be null.");
    }
    logger.info(name + " attempts to attack " + target.getName());
    target.reduceHealth(10); // Default attack causes 10 damage
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
    if (damage <= 0) {
      throw new IllegalArgumentException("Invalid damage value: Must be greater than zero.");
    }
    this.health -= damage;
    if (this.health < 0) {
      this.health = 0;
    }
    logger.info(name + " (AI) now has " + health + " health remaining.");
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
   * Executes the AI strategy.
   */
  public void executeStrategy() {
    if (strategy != null) {
      strategy.decideAction(this, currentSpace.getWorld());
    }
  }

  /**
   * Sets the strategy for the AI player.
   *
   * @param strategy The AIStrategy to set.
   */
  public void setStrategy(AiStrategy strategy) {
    if (strategy == null) {
      throw new IllegalArgumentException("Strategy cannot be null.");
    }
    this.strategy = strategy;
  }

  /**
   * Gets the strategy of the AI player.
   *
   * @return The AIStrategy of the player.
   */
  public AiStrategy getStrategy() {
    return strategy;
  }
}
