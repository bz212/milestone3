package world;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

import strategy.TargetStrategy;

/**
 * Represents the game world, containing spaces, items, and characters.
 */
public class World {
    private static final Logger LOGGER = Logger.getLogger(World.class.getName());
    private List<Space> spaces;
    private List<Item> items;
    private Player target;
    private Pet pet;
    private TargetStrategy strategy;

    /**
     * Initializes the World with spaces, items, a target, pet, and movement strategy.
     *
     * @param spaces   The list of spaces in the world.
     * @param items    The list of items in the world.
     * @param target   The target character.
     * @param pet      The pet associated with the target character.
     * @param strategy The strategy used for target movement.
     */
    public World(List<Space> spaces, List<Item> items, Player target, Pet pet, TargetStrategy strategy) {
        this.spaces = spaces != null ? spaces : new ArrayList<>();
        this.items = items != null ? items : new ArrayList<>();
        this.target = target;
        this.pet = pet;
        this.strategy = strategy;
    }

    /**
     * Gets the list of spaces in the world.
     *
     * @return The list of spaces.
     */
    public List<Space> getSpaces() {
        return spaces;
    }

    /**
     * Gets the list of items in the world.
     *
     * @return The list of items.
     */
    public List<Item> getItems() {
        return items;
    }

    /**
     * Adds an item to the world.
     *
     * @param item The item to be added.
     */
    public void addItem(Item item) {
        if (item == null) {
            LOGGER.warning("Attempted to add a null item to the world.");
            return;
        }

        if (!items.contains(item)) {
            items.add(item);
            LOGGER.info("Item added: " + item.getName());
        } else {
            LOGGER.warning("Attempted to add a duplicate item to the world: " + item.getName());
        }
    }

    /**
     * Removes an item from the world.
     *
     * @param item The item to be removed.
     */
    public void removeItem(Item item) {
        if (item == null) {
            LOGGER.warning("Attempted to remove a null item from the world.");
            return;
        }

        if (items.contains(item)) {
            items.remove(item);
            LOGGER.info("Item removed: " + item.getName());
        } else {
            LOGGER.warning("Attempted to remove an item that does not exist in the world: " + item.getName());
        }
    }

    /**
     * Gets the pet associated with the target character.
     *
     * @return The pet.
     */
    public Pet getPet() {
        return pet;
    }

    /**
     * Moves the pet according to its strategy.
     */
    public void movePet() {
        if (pet != null && pet.getStrategy() != null) {
            pet.getStrategy().movePet(pet, this);
        }
    }

    /**
     * Gets the list of all pets in the world.
     *
     * @return The list of pets.
     */
    public List<Pet> getPets() {
        List<Pet> pets = new ArrayList<>();
        if (pet != null) {
            pets.add(pet);
        }
        return pets;
    }

    /**
     * Moves the target character according to the strategy.
     */
    public void moveTarget() {
        if (strategy != null && target != null) {
            strategy.moveTarget(target, this);
        }
    }

    /**
     * Allows a player to pick up an item in the current space.
     *
     * @param player The player picking up the item.
     * @param item   The item to be picked up.
     */
    public void playerPickUpItem(Player player, Item item) {
        if (player == null || item == null) {
            LOGGER.warning("Attempted to pick up a null player or item.");
            return;
        }

        Space currentSpace = player.getCurrentSpace();
        if (currentSpace != null && currentSpace.getItems().contains(item) && items.contains(item)) {
            // Remove item from space and world only if both contain the item
            boolean removedFromSpace = currentSpace.getItems().remove(item);
            boolean removedFromWorld = items.remove(item);

            if (removedFromSpace && removedFromWorld) {
                if (!player.getInventory().contains(item)) {
                    player.pickUpItem(item);  // Player picks up item
                    LOGGER.info(player.getName() + " picked up item: " + item.getName());
                } else {
                    LOGGER.warning("Attempted to add a duplicate item to player's inventory: " + item.getName());
                }
            } else {
                LOGGER.warning("Failed to remove item from space or world.");
            }
        } else {
            LOGGER.warning("Item not available in the current space or the world: " + item.getName());
        }
    }

    /**
     * Gets the list of all players in the world by iterating over all spaces.
     *
     * @return The list of players.
     */
    public List<Player> getPlayers() {
        List<Player> players = new ArrayList<>();
        for (Space space : spaces) {
            players.addAll(space.getPlayers());
        }
        return players;
    }

    /**
     * Sets the players in the world. This method will place the players into spaces.
     *
     * @param players The list of players to set in the world.
     */
    public void setPlayers(List<Player> players) {
        // Clear current players from all spaces
        for (Space space : spaces) {
            space.getPlayers().clear();
        }

        // Distribute the players among the spaces or reset their current space
        for (Player player : players) {
            Space currentSpace = player.getCurrentSpace();
            if (currentSpace != null && spaces.contains(currentSpace)) {
                currentSpace.addPlayer(player);
            } else {
                // Assign a default space if the current space is null or invalid
                if (!spaces.isEmpty()) {
                    Space defaultSpace = spaces.get(0);
                    defaultSpace.addPlayer(player);
                    player.setCurrentSpace(defaultSpace);
                    LOGGER.info("Assigned default space to player: " + player.getName());
                }
            }
        }
    }

    /**
     * Determines if a space is visible to the player.
     *
     * @param space The space to check.
     * @return True if the space is visible, false otherwise.
     */
    public boolean isVisible(Space space) {
        // Placeholder logic: This should be updated with the actual visibility logic
        return space != null;
    }

    /**
     * Sets the spaces in the world.
     *
     * @param spaces The list of spaces to set.
     */
    public void setSpaces(List<Space> spaces) {
        this.spaces = spaces != null ? spaces : new ArrayList<>();
    }

    /**
     * Sets the strategy for target movement in the world.
     *
     * @param strategy The target movement strategy.
     */
    public void setStrategy(TargetStrategy strategy) {
        this.strategy = strategy;
    }

    /**
     * Gets the current strategy for target movement.
     *
     * @return The target movement strategy.
     */
    public TargetStrategy getStrategy() {
        return this.strategy;
    }

    /**
     * Sets the pet in the world.
     *
     * @param pet The pet to set.
     */
    public void setPet(Pet pet) {
        this.pet = pet;
    }
}
