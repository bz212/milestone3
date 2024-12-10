package world;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

/**
 * Represents a space in the game world, containing items, players, pets, and neighbors.
 */
public class Space {

  private static final Logger LOGGER = Logger.getLogger(Space.class.getName());

  private final String name;       // Name of the space
  private final int x;             // X-coordinate
  private final int y;             // Y-coordinate
  private List<Item> items;        // Items in the space
  private List<Player> players;    // Players in the space
  private List<Pet> pets;          // Pets in the space
  private List<Space> neighbors;   // Adjacent spaces
  private World world;             // The world this space belongs to

  /**
   * Initializes a Space with the given name, coordinates, and world context.
   *
   * @param name  The name of the space.
   * @param x     The X-coordinate of the space.
   * @param y     The Y-coordinate of the space.
   * @param world The world to which this space belongs.
   */
  public Space(String name, int x, int y, World world) {
    if (name == null || name.isEmpty()) {
      throw new IllegalArgumentException("Space name cannot be null or empty.");
    }

    this.name = name;
    this.x = x;
    this.y = y;
    this.world = world;

    this.items = new ArrayList<>();
    this.players = new ArrayList<>();
    this.neighbors = new ArrayList<>();
    this.pets = new ArrayList<>();

    LOGGER.info("Space created: " + name + " at coordinates (" + x + ", " + y + ")");
  }

  /**
   * Gets the list of pets in the space.
   *
   * @return A list of pets.
   */
  public List<Pet> getPets() {
    return new ArrayList<>(pets);
  }

  /**
   * Adds a pet to this space.
   *
   * @param pet The pet to add.
   */
  public void addPet(Pet pet) {
    if (pet == null) {
      throw new IllegalArgumentException("Pet cannot be null.");
    }
    if (!pets.contains(pet)) {
      pets.add(pet);
    }
  }

  /**
   * Gets the name of the space.
   *
   * @return The space name.
   */
  public String getName() {
    return name;
  }

  /**
   * Gets the X-coordinate of the space.
   *
   * @return The X-coordinate.
   */
  public int getX() {
    return x;
  }

  /**
   * Gets the Y-coordinate of the space.
   *
   * @return The Y-coordinate.
   */
  public int getY() {
    return y;
  }

  /**
   * Sets the world context for this space.
   *
   * @param world The world to set.
   */
  public void setWorld(World world) {
    if (world == null) {
      throw new IllegalArgumentException("World cannot be null.");
    }
    this.world = world;
  }

  /**
   * Gets the world context of the space.
   *
   * @return The world instance.
   */
  public World getWorld() {
    return world;
  }

  /**
   * Adds a player to this space.
   *
   * @param player The player to add.
   */
  public void addPlayer(Player player) {
    if (player != null && !players.contains(player)) {
      players.add(player);
      LOGGER.info("Player added: " + player.getName() + " to space: " + name);
    }
  }

  /**
   * Removes a player from this space.
   *
   * @param player The player to remove.
   */
  public void removePlayer(Player player) {
    if (players.remove(player)) {
      LOGGER.info("Player removed: " + player.getName() + " from space: " + name);
    }
  }

  /**
   * Gets the list of players in the space.
   *
   * @return A list of players.
   */
  public List<Player> getPlayers() {
    return new ArrayList<>(players);
  }

  /**
   * Adds an item to this space.
   *
   * @param item The item to add.
   */
  public void addItem(Item item) {
    if (item != null && !items.contains(item)) {
      items.add(item);
      LOGGER.info("Item added: " + item.getName() + " to space: " + name);
    }
  }

  /**
   * Removes an item from this space.
   *
   * @param item The item to remove.
   */
  public void removeItem(Item item) {
    if (items.remove(item)) {
      LOGGER.info("Item removed: " + item.getName() + " from space: " + name);
    }
  }

  /**
   * Gets the list of items in the space.
   *
   * @return A list of items.
   */
  public List<Item> getItems() {
    return new ArrayList<>(items);
  }

  /**
   * Adds a neighboring space to this space.
   *
   * @param neighbor The neighboring space to add.
   */
  public void addNeighbor(Space neighbor) {
    if (neighbor == null || this.equals(neighbor)) {
      LOGGER.warning("Invalid neighbor: Cannot add self or null as neighbor.");
      return;
    }

    if (!neighbors.contains(neighbor)) {
      neighbors.add(neighbor);
      if (!neighbor.getNeighbors().contains(this)) {
        neighbor.addNeighbor(this); // Ensure bidirectional relationship
      }
      LOGGER.info("Neighbor added between: " + this.name + " and " + neighbor.getName());
    }
  }

  /**
   * Gets the list of neighboring spaces.
   *
   * @return A list of neighbors.
   */
  public List<Space> getNeighbors() {
    return new ArrayList<>(neighbors);
  }

  /**
   * Gets a detailed description of the space.
   *
   * @return A string describing the space, its items, players, pets, and neighbors.
   */
  public String getDescription() {
    StringBuilder description = new StringBuilder();
    description.append("Space: ")
    .append(name).append(" (").append(x).append(", ").append(y).append(")\n");
    description.append("Items: ").append(items.isEmpty() ? "None" : formatList(items)).append("\n");
    description.append("Players: ")
    .append(players.isEmpty() ? "None" : formatList(players)).append("\n");
    description.append("Pets: ").append(pets.isEmpty() ? "None" : formatList(pets)).append("\n");
    description.append("Exits: ").append(neighbors.isEmpty() ? "None" : formatList(neighbors));
    return description.toString();
  }

  private <T> String formatList(List<T> list) {
    return list.stream()
        .map(Object::toString)
        .reduce((a, b) -> a + ", " + b)
        .orElse("");
  }

  @Override
  public boolean equals(Object obj) {
    if (this == obj) {
      return true;
    }
    if (obj == null || getClass() != obj.getClass()) {
      return false;
    }
    Space space = (Space) obj;
    return x == space.x && y == space.y && name.equals(space.name);
  }

  @Override
  public int hashCode() {
    return name.hashCode() + x * 31 + y * 31;
  }
}
