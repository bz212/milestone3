package world;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

/**
 * PlayerInventory manages the items carried by a player.
 */
public class PlayerInventory {

  private static final Logger LOGGER = Logger.getLogger(PlayerInventory.class.getName());
  private static final int DEFAULT_MAX_ITEMS = 10;

  private final List<Item> items;
  private final int maxItems;

  /**
   * Default constructor with a default maximum inventory size.
   */
  public PlayerInventory() {
    this(DEFAULT_MAX_ITEMS);
  }

  /**
   * Initializes an empty PlayerInventory with a specified capacity.
   *
   * @param maxItems The maximum number of items the inventory can hold.
   * @throws IllegalArgumentException If maxItems is zero or negative.
   */
  public PlayerInventory(int maxItems) {
    if (maxItems <= 0) {
      throw new IllegalArgumentException("Maximum items must be greater than zero.");
    }
    this.items = new ArrayList<>();
    this.maxItems = maxItems;
    LOGGER.info(() -> "PlayerInventory created with capacity: " + maxItems);
  }

  /**
   * Adds an item to the inventory if there is capacity.
   *
   * @param item The item to add.
   * @return True if the item was successfully added, false otherwise.
   * @throws IllegalArgumentException If the item is null.
   */
  public boolean addItem(Item item) {
    if (item == null) {
      throw new IllegalArgumentException("Item cannot be null.");
    }
    if (items.size() < maxItems) {
      items.add(item);
      LOGGER.info(() -> "Added item: " + item.getName());
      return true;
    }
    LOGGER.warning(() -> "Cannot add item: Inventory is full.");
    return false;
  }

  /**
   * Removes an item from the inventory.
   *
   * @param item The item to remove.
   * @return True if the item was successfully removed, false otherwise.
   * @throws IllegalArgumentException If the item is null.
   */
  public boolean removeItem(Item item) {
    if (item == null) {
      throw new IllegalArgumentException("Item cannot be null.");
    }
    boolean removed = items.remove(item);
    if (removed) {
      LOGGER.info(() -> "Removed item: " + item.getName());
    } else {
      LOGGER.warning(() -> "Failed to remove item: " + item.getName());
    }
    return removed;
  }

  /**
   * Gets a copy of the list of items in the inventory.
   *
   * @return A list of items.
   */
  public List<Item> getItems() {
    return new ArrayList<>(items);
  }

  /**
   * Checks if the inventory contains a specific item.
   *
   * @param item The item to check for.
   * @return True if the item is in the inventory, false otherwise.
   * @throws IllegalArgumentException If the item is null.
   */
  public boolean containsItem(Item item) {
    if (item == null) {
      throw new IllegalArgumentException("Item cannot be null.");
    }
    return items.contains(item);
  }

  /**
   * Gets the best item based on its damage value.
   *
   * @return The item with the highest damage, or null if the inventory is empty.
   */
  public Item getBestItem() {
    if (items.isEmpty()) {
      LOGGER.info("Inventory is empty. No best item available.");
      return null;
    }

    Item bestItem = items.get(0);
    for (Item item : items) {
      if (item.getDamage() > bestItem.getDamage()) {
        bestItem = item;
      }
    }

    String logMessage = "Best item found: " + bestItem.getName();
    LOGGER.info(logMessage);

    return bestItem;
  }


  /**
   * Checks if the inventory is full.
   *
   * @return True if the inventory is full, otherwise false.
   */
  public boolean isFull() {
    return items.size() >= maxItems;
  }

  /**
   * Clears all items from the inventory.
   */
  public void clearInventory() {
    items.clear();
    LOGGER.info(() -> "Inventory has been cleared.");
  }

  /**
   * Gets the current size of the inventory.
   *
   * @return The number of items in the inventory.
   */
  public int size() {
    return items.size();
  }

  /**
   * Provides a string representation of the inventory's contents.
   *
   * @return A string listing all items in the inventory.
   */
  @Override
  public String toString() {
    if (items.isEmpty()) {
      return "Inventory is empty.";
    }

    StringBuilder sb = new StringBuilder("Inventory: ");
    for (Item item : items) {
      sb.append(item.getName()).append(", ");
    }
    sb.setLength(sb.length() - 2); // Remove trailing comma and space
    return sb.toString();
  }

  /**
   * Checks if the inventory contains a specific item.
   *
   * @param item The item to check for.
   * @return True if the item is in the inventory, otherwise false.
   */
  public boolean contains(Item item) {
    if (item == null) {
      throw new IllegalArgumentException("Item cannot be null.");
    }
    return items.contains(item);
  }

}
