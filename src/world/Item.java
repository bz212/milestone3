package world;

/**
 * Represents an item in the game that can be picked up by players and used during gameplay.
 */
public class Item {
    private String name;
    private int damage;
    private String description;

    /**
     * Initializes an Item with the given parameters.
     *
     * @param name The name of the item.
     * @param damage The damage value of the item.
     * @param description The description of the item.
     */
    public Item(String name, int damage, String description) {
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
     * Uses the item on a target player.
     *
     * @param target The player on which the item is used.
     */
    public void use(Player target) {
        if (target != null) {
            target.reduceHealth(damage);
            System.out.println(name + " used on " + target.getName() + " for " + damage + " damage.");
        }
    }
}
