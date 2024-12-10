package world;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;
import strategy.RandomTargetStrategy;
import strategy.TargetStrategy;

/**
 * Represents the game world, containing spaces, items, players, and pets.
 */
public class World implements WorldModel {
  private static final Logger LOGGER = Logger.getLogger(World.class.getName());
  private List<Space> spaces;       // List of all spaces in the world
  private List<Item> items;         // List of all items in the world
  private List<Player> players;     // List of all players in the world
  private Player target;            // The target player in the game
  private Pet pet;                  // Pet associated with the target or world
  private TargetStrategy strategy;  // Movement strategy for the target
  private int maxTurns;             // Maximum number of turns allowed in the game

  /**
   * Constructor with all required parameters.
   *
   * @param spaces   The list of spaces in the world.
   * @param items    The list of items in the world.
   * @param target   The target player in the game.
   * @param players  The list of all players in the world.
   * @param strategy The movement strategy for the target.
   * @param maxTurns The maximum number of turns.
   */
  public World(List<Space> spaces, List<Item> items, Player target, 
      List<Player> players, TargetStrategy strategy, int maxTurns) {
    if (spaces == null || items == null || target == null 
        || players == null || strategy == null) {
      throw new IllegalArgumentException(
          "Spaces, items, target, players, and strategy cannot be null.");
    }
    if (maxTurns <= 0) {
      throw new IllegalArgumentException("Max turns must be positive.");
    }
    this.spaces = new ArrayList<>(spaces);
    this.items = new ArrayList<>(items);
    this.target = target;
    this.players = new ArrayList<>(players);
    this.strategy = strategy;
    this.maxTurns = maxTurns;
  }

  /**
   * Simplified constructor without a strategy (uses default RandomTargetStrategy).
   *
   * @param spaces   The list of spaces in the world.
   * @param items    The list of items in the world.
   * @param target   The target player in the game.
   * @param players  The list of all players in the world.
   * @param maxTurns The maximum number of turns.
   */
  public World(List<Space> spaces, List<Item> items, Player target, 
      List<Player> players, int maxTurns) {
    this(spaces, items, target, players, new RandomTargetStrategy(), maxTurns);
  }

  /**
   * Constructor for initializing the world with configuration file and max turns.
   *
   * @param worldConfigFile The configuration file path.
   * @param maxTurns        The maximum number of turns.
   */
  public World(String worldConfigFile, int maxTurns) {
    if (worldConfigFile == null || worldConfigFile.isEmpty()) {
      throw new IllegalArgumentException("World configuration file cannot be null or empty.");
    }
    if (maxTurns <= 0) {
      throw new IllegalArgumentException("Max turns must be positive.");
    }

    this.spaces = new ArrayList<>();
    this.items = new ArrayList<>();
    this.players = new ArrayList<>();
    this.strategy = new RandomTargetStrategy();
    this.maxTurns = maxTurns;

    LOGGER.info("Initializing world with file: " + worldConfigFile 
        + " and max turns: " + maxTurns);
    initializeWorld(worldConfigFile, maxTurns);
  }

  /**
   * Returns the list of spaces in the world.
   *
   * @return A list of spaces.
   */
  public List<Space> getSpaces() {
    return new ArrayList<>(spaces);
  }

  /**
   * Returns the target player in the game.
   *
   * @return The target player.
   */
  public Player getTarget() {
    return target;
  }

  @Override
  public void initializeWorld(String worldConfigFile, int maxTurns) {
    LOGGER.info("Parsing configuration file: " + worldConfigFile);
    try (BufferedReader reader = new BufferedReader(new FileReader(worldConfigFile))) {
      String line;
      while ((line = reader.readLine()) != null) {
        String[] parts = line.split(",");
        switch (parts[0]) {
          case "SPACE":
            String name = parts[1];
            int x = Integer.parseInt(parts[2]);
            int y = Integer.parseInt(parts[3]);
            spaces.add(new Space(name, x, y, this));
            break;
          case "ITEM":
            String itemName = parts[1];
            String spaceName = parts[2];
            String description = parts.length > 3 ? parts[3] : "No description";
            int damage = parts.length > 4 ? Integer.parseInt(parts[4]) : 0;

            Item item = new Item(itemName, damage, description);
            spaces.stream()
                .filter(space -> space.getName().equals(spaceName))
                .findFirst()
                .ifPresent(space -> {
                  space.addItem(item);
                  items.add(item);
                });
            break;
          case "PLAYER":
            String playerName = parts[1];
            int health = Integer.parseInt(parts[2]);
            String startSpaceName = parts[3];
            spaces.stream()
                .filter(space -> space.getName().equals(startSpaceName))
                .findFirst()
                .ifPresent(space -> {
                  Player player = new HumanPlayer(playerName, health, space);
                  space.addPlayer(player);
                  players.add(player);
                });
            break;
          default:
            LOGGER.warning("Unknown entity type: " + parts[0]);
        }
      }
    } catch (IOException e) {
      LOGGER.severe("Error loading world configuration: " + e.getMessage());
      throw new IllegalStateException("Failed to initialize world.");
    }
  }

  @Override
  public Object getWorld() {
    return this;
  }

  @Override
  public boolean movePlayer(String spaceName) {
    if (target == null || target.getCurrentSpace() == null) {
      LOGGER.warning("Target player or current space is not defined.");
      return false;
    }

    return spaces.stream()
        .filter(space -> space.getName().equals(spaceName))
        .findFirst()
        .filter(space -> target.getCurrentSpace().getNeighbors().contains(space))
        .map(space -> {
          target.setCurrentSpace(space);
          LOGGER.info("Player moved to space: " + spaceName);
          return true;
        })
        .orElseGet(() -> {
          LOGGER.warning("Invalid move. Either space not found or not accessible.");
          return false;
        });
  }

  @Override
  public boolean pickUpItem(String itemName) {
    if (target == null || target.getCurrentSpace() == null) {
      LOGGER.warning("Target player or current space is not defined.");
      return false;
    }

    Space currentSpace = target.getCurrentSpace();
    return currentSpace.getItems().stream()
        .filter(item -> item.getName().equals(itemName))
        .findFirst()
        .map(item -> {
          currentSpace.removeItem(item);
          target.pickUpItem(item);
          LOGGER.info(
              "Item \"" + itemName + "\" picked up by player \"" + target.getName() + "\".");
          return true;
        })
        .orElseGet(() -> {
          LOGGER.warning("Item \"" + itemName + "\" not found in the current space.");
          return false;
        });
  }

  @Override
  public String attackPlayer(String playerName) {
    for (Player player : players) {
      if (player.getName().equals(playerName)) {
        player.reduceHealth(10);
        LOGGER.info(playerName + " was attacked and lost 10 health points.");
        return playerName + " attacked successfully!";
      }
    }
    LOGGER.warning("Player \"" + playerName + "\" not found.");
    return "Attack failed: Player not found.";
  }

  @Override
  public boolean isGameOver() {
    boolean allPlayersDead = players.stream().allMatch(player -> player.getHealth() <= 0);
    if (allPlayersDead) {
      LOGGER.info("Game over: All players are dead.");
    }
    return allPlayersDead;
  }

  @Override
  public void setStrategy(TargetStrategy strategy) {
    if (strategy == null) {
      throw new IllegalArgumentException("Strategy cannot be null.");
    }
    this.strategy = strategy;
  }

  @Override
  public TargetStrategy getStrategy() {
    return strategy;
  }

  @Override
  public List<Player> getPlayers() {
    return new ArrayList<>(players);
  }

  @Override
  public boolean isVisible(Space space) {
    if (space == null) {
      throw new IllegalArgumentException("Space cannot be null.");
    }
    return spaces.contains(space);
  }

  @Override
  public String lookAround() {
    StringBuilder result = new StringBuilder("World State:\n");
    spaces.forEach(space -> result.append(space.getDescription()).append("\n"));
    return result.toString();
  }

  @Override
  public String getSpaceNameAt(int x, int y) {
    return spaces.stream()
        .filter(space -> space.getX() == x && space.getY() == y)
        .findFirst()
        .map(Space::getName)
        .orElseGet(() -> {
          LOGGER.warning("No space found at coordinates: (" + x + ", " + y + ")");
          return null;
        });
  }

  @Override
  public String getPlayerStatus() {
    if (target == null) {
      return "No player status available.";
    }

    StringBuilder status = new StringBuilder();
    status.append("Player: ").append(target.getName()).append("\n")
        .append("Health: ").append(target.getHealth()).append("\n")
        .append("Current Space: ").append(target.getCurrentSpace().getName()).append("\n")
        .append("Inventory: ");

    if (target.getItems().isEmpty()) {
      status.append("None");
    } else {
      target.getItems().forEach(item -> status.append(item.getName()).append(", "));
      status.delete(status.length() - 2, status.length());
    }

    return status.toString();
  }

  @Override
  public String getCurrentPlayerStatus() {
    return getPlayerStatus();
  }

  /**
   * Sets the list of players in the world.
   *
   * @param players The list of players to set. Cannot be null.
   * @throws IllegalArgumentException if the provided players list is null.
   */
  public void setPlayers(List<Player> players) {
    if (players == null) {
      throw new IllegalArgumentException("Players list cannot be null.");
    }
    this.players = new ArrayList<>(players);
  }

  /**
   * Moves the target player based on the current movement strategy.
   * Logs a warning if the target or strategy is not defined.
   */
  public void moveTarget() {
    if (target == null) {
      LOGGER.warning("No target player to move.");
      return;
    }
    if (strategy == null) {
      LOGGER.warning("No movement strategy assigned.");
      return;
    }
    strategy.decideAction(target, this);
    LOGGER.info("Target moved using strategy: " + strategy.getClass().getSimpleName());
  }

  /**
   * Adds an item to the world if it is not already present.
   *
   * @param item The item to be added. Cannot be null.
   * @throws IllegalArgumentException if the provided item is null.
   */
  public void addItem(Item item) {
    if (item == null) {
      throw new IllegalArgumentException("Item cannot be null.");
    }
    if (!items.contains(item)) {
      items.add(item);
      LOGGER.info("Item added to the world: " + item.getName());
    }
  }

  @Override
  public Player getCurrentPlayer() {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public Space getSpaceAt(int x, int y) {
    // TODO Auto-generated method stub
    return null;
  }

  /**
   * Adds a space to the world.
   *
   * @param space The space to add.
   */
  public void addSpace(Space space) {
    if (space == null) {
      throw new IllegalArgumentException("Space cannot be null.");
    }
    if (!spaces.contains(space)) {
      spaces.add(space);
      space.setWorld(this);
      LOGGER.info("Space added to the world: " + space.getName());
    }
  }

  /**
   * Adds a player to the world.
   *
   * @param player The player to add.
   */
  public void addPlayer(Player player) {
    if (player == null) {
      throw new IllegalArgumentException("Player cannot be null.");
    }
    if (!players.contains(player)) {
      players.add(player);
      LOGGER.info("Player added to the world: " + player.getName());
    }
  }
}
