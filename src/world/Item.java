package world;

import java.util.logging.Logger;

/**
 * Represents an item in the game that can be picked up by players and used during gameplay.
 */
public class Item {

  private static final Logger logger = Logger.getLogger(Item.class.getName());

  private final String name;
  private final int damage;
  private final String description;

  /**
   * Initializes an Item with the given parameters.
   *
   * @param name        The name of the item.
   * @param damage      The damage value of the item.
   * @param description The description of the item.
   * @throws IllegalArgumentException If name, description are null/empty, or damage is negative.
   */
  public Item(String name, int damage, String description) {
    if (name == null || name.isEmpty()) {
      throw new IllegalArgumentException("Item name cannot be null or empty.");
    }
    if (damage < 0) {
      throw new IllegalArgumentException("Damage value cannot be negative.");
    }
    if (description == null || description.isEmpty()) {
      throw new IllegalArgumentException("Description cannot be null or empty.");
    }

    this.name = name;
    this.damage = damage;
    this.description = description;
  }

  /**
   * Gets the name of the item.
   *
   * @return The name of the item.
   */
  public String getName() {
    return name;
  }

  /**
   * Gets the damage value of the item.
   *
   * @return The damage value of the item.
   */
  public int getDamage() {
    return damage;
  }

  /**
   * Gets the description of the item.
   *
   * @return The description of the item.
   */
  public String getDescription() {
    return description;
  }

  /**
   * Uses the item on a target player, reducing the target's health.
   *
   * @param target The player on which the item is used.
   * @throws IllegalArgumentException If the target player is null.
   */
  public void use(Player target) {
    if (target == null) {
      throw new IllegalArgumentException("Target player cannot be null.");
    }

    target.reduceHealth(damage);
    logger.info(() -> name + " used on " + target.getName()
        + " for " + damage + " damage. Remaining health: " + target.getHealth());
  }

  /**
   * Provides a string representation of the item.
   *
   * @return A string describing the item.
   */
  @Override
  public String toString() {
    return "Item{name='" + name + "', damage=" + damage 
        + ", description='" + description + "'}";
  }
}
