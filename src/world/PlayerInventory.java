package world;

import java.util.ArrayList;
import java.util.List;

/**
 * PlayerInventory manages the items carried by a player.
 */
public class PlayerInventory {
    private List<Item> items;
    private int maxItems;

    /**
     * Initializes an empty PlayerInventory with a specified capacity.
     *
     * @param maxItems The maximum number of items the inventory can hold.
     */
    public PlayerInventory(int maxItems) {
        this.items = new ArrayList<>();
        this.maxItems = maxItems;
    }

    /**
     * Adds an item to the inventory if there is capacity.
     *
     * @param item The item to add.
     * @return True if the item was successfully added, false otherwise.
     */
    public boolean addItem(Item item) {
        if (items.size() < maxItems) {
            items.add(item);
            return true;
        }
        System.out.println("Cannot add item: Inventory is full.");
        return false;
    }

    /**
     * Removes an item from the inventory.
     *
     * @param item The item to remove.
     */
    public void removeItem(Item item) {
        items.remove(item);
    }

    /**
     * Gets the list of items in the inventory.
     *
     * @return The list of items.
     */
    public List<Item> getItems() {
        return items;
    }

    /**
     * Checks if the inventory contains a specific item.
     *
     * @param item The item to check for.
     * @return True if the item is in the inventory, false otherwise.
     */
    public boolean containsItem(Item item) {
        return items.contains(item);
    }

    public Item getBestItem() {
      if (items.isEmpty()) {
          return null;
      }

      Item bestItem = items.get(0);
      for (Item item : items) {
          if (item.getDamage() > bestItem.getDamage()) {
              bestItem = item;
          }
      }
      return bestItem;
  }

    public boolean contains(Item item) {
      return items.contains(item);
  }

    /**
     * Removes an item from the inventory.
     *
     * @param item The item to remove.
     * @return True if the item was successfully removed, false otherwise.
     */
    public boolean remove(Item item) {
        return items.remove(item);
    }

}
