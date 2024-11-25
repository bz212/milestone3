package world;

import strategy.AIStrategy;
import strategy.ChasePlayerStrategy;

import java.util.HashMap;
import java.util.Map;

/**
 * AIPlayer class represents a player controlled by the computer in the game.
 */
public class AIPlayer implements Player {
    private String name;
    private int health;
    private Space currentSpace;
    private PlayerInventory inventory;
    private AIStrategy strategy;
    private Map<Player, Boolean> visibilityMap; // Track visibility of other players

    /**
     * Initializes an AIPlayer with the given parameters.
     *
     * @param name          The name of the AI player.
     * @param health        The initial health of the AI player.
     * @param startingSpace The starting space of the AI player.
     * @param strategy      The strategy used by the AI player.
     */
    public AIPlayer(String name, int health, Space startingSpace, AIStrategy strategy) {
        this.name = name;
        this.health = health;
        this.currentSpace = startingSpace;
        this.inventory = new PlayerInventory(10); // Assume default inventory capacity is 10
        this.strategy = strategy;
        this.visibilityMap = new HashMap<>(); // Initialize the visibility map
        startingSpace.addPlayer(this); // Ensure player is added to the initial space
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
     * Sets the current space of the AI player.
     *
     * @param space The new space for the AI player.
     */
    @Override
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
        System.out.println(name + " attempts to attack " + target.getName() + " using AI strategy.");
        Item bestItem = inventory.getBestItem();
        if (bestItem != null) {
            bestItem.use(target);
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

    @Override
    public void reduceHealth(int damage) {
        this.health -= damage;
        if (this.health < 0) {
            this.health = 0; // Ensure health does not go below zero
        }
        System.out.println(name + " (AI) has " + health + " health remaining.");
    }

    @Override
    public PlayerInventory getInventory() {
        return inventory;
    }

    /**
     * Executes the AI player's strategy.
     */
    public void executeStrategy() {
        if (strategy != null) {
            strategy.decideAction(this, currentSpace.getWorld());
        }
    }
}
