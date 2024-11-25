package world;

import java.util.HashMap;
import java.util.Map;

/**
 * HumanPlayer class represents a player controlled by a human in the game.
 */
public class HumanPlayer implements Player {
    private String name;
    private int health;
    private Space currentSpace;
    private PlayerInventory inventory;
    private Map<Player, Boolean> visibilityMap; // Track visibility of other players

    /**
     * Initializes a HumanPlayer with the given parameters.
     *
     * @param name The name of the player.
     * @param health The initial health of the player.
     * @param startingSpace The starting space of the player.
     */
    public HumanPlayer(String name, int health, Space startingSpace) {
        this.name = name;
        this.health = health;
        this.currentSpace = startingSpace;
        this.inventory = new PlayerInventory(10); // Assuming a default capacity of 10 for PlayerInventory
        this.visibilityMap = new HashMap<>(); // Initialize the visibility map
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
    public Space getCurrentSpace() {
        return currentSpace;
    }

    /**
     * Sets the current space of the player.
     *
     * @param space The new space to set as the current space of the player.
     */
    public void setCurrentSpace(Space space) {
        if (currentSpace != null) {
            currentSpace.removePlayer(this); // Remove from old space
        }
        currentSpace = space;
        if (currentSpace != null) {
            currentSpace.addPlayer(this); // Add to the new space
        }
    }

    @Override
    public void move(Space newSpace) {
        if (newSpace != null) {
            setCurrentSpace(newSpace);
        }
    }

    @Override
    public void pickUpItem(Item item) {
        if (currentSpace.getItems().contains(item)) {
            inventory.addItem(item);
            currentSpace.removeItem(item);
        }
    }

    @Override
    public void attemptAttack(Player target) {
        System.out.println(name + " attempts to attack " + target.getName());
        // Pick one item to attack
        Item weapon = inventory.getBestItem(); // Pick the best item
        if (weapon != null) {
            System.out.println(name + " uses " + weapon.getName() + " on " + target.getName());
            weapon.use(target); // Attack using the weapon
        } else {
            System.out.println(name + " has no items to use for attack.");
        }
    }

    @Override
    public boolean canSee(Player player) {
        return visibilityMap.getOrDefault(player, false);
    }

    @Override
    public void setCanSee(Player player, boolean canSee) {
        visibilityMap.put(player, canSee);
    }

    /**
     * Gets detailed information about the player.
     *
     * @return The detailed information.
     */
    public String getDetailedInfo() {
        return "Player: " + name + ", Health: " + health + ", Current Space: " + currentSpace.getName();
    }

    @Override
    public void reduceHealth(int damage) {
        this.health -= damage;
        if (this.health < 0) {
            this.health = 0; // Ensure health does not go below zero
        }
        System.out.println(name + " (Human) has " + health + " health remaining.");
    }

    @Override
    public PlayerInventory getInventory() {
        return inventory;
    }
}
