package world;

/**
 * Player interface represents common actions and attributes of a player in the game.
 */
public interface Player {
    String getName();
    int getHealth();
    Space getCurrentSpace();

    /**
     * Sets the current space of the player.
     *
     * @param space The new space of the player.
     */
    void setCurrentSpace(Space space);

    void move(Space newSpace);
    void pickUpItem(Item item);
    void attemptAttack(Player target);
    boolean canSee(Player player);
    void reduceHealth(int damage);
    PlayerInventory getInventory();

    /**
     * Sets the visibility of another player for this player.
     *
     * @param player The player whose visibility is being set.
     * @param canSee True if the player is visible, otherwise false.
     */
    void setCanSee(Player player, boolean canSee);
}
