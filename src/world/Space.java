package world;

import java.util.ArrayList;
import java.util.List;

/**
 * Represents a space in the game world, containing items, players, and pets.
 */
public class Space {
    private String name;
    private List<Item> items;
    private List<Player> players;
    private List<Pet> pets; // add pets list
    private List<Space> neighbors;
    private World world;
    private boolean isVisited;

    /**
     * Initializes a Space with the given name.
     *
     * @param name The name of the space.
     * @param world The world to which this space belongs.
     */
    public Space(String name, World world) {
        this.name = name;
        this.items = new ArrayList<>();
        this.players = new ArrayList<>();
        this.pets = new ArrayList<>(); // Initializes pets list
        this.neighbors = new ArrayList<>();
        this.world = world;
        this.isVisited = false;
    }

    /**
     * Gets the name of the space.
     *
     * @return The name of the space.
     */
    public String getName() {
        return name;
    }

    /**
     * Adds a player to this space.
     *
     * @param player The player to add.
     */
    public void addPlayer(Player player) {
        players.add(player);
    }

    /**
     * Removes a player from this space.
     *
     * @param player The player to remove.
     */
    public void removePlayer(Player player) {
        players.remove(player);
    }

    /**
     * Gets the list of players in this space.
     *
     * @return The list of players.
     */
    public List<Player> getPlayers() {
        return players;
    }

    /**
     * Adds a pet to this space.
     *
     * @param pet The pet to add.
     */
    public void addPet(Pet pet) {
        pets.add(pet);
    }

    /**
     * Removes a pet from this space.
     *
     * @param pet The pet to remove.
     */
    public void removePet(Pet pet) {
        pets.remove(pet);
    }

    /**
     * Gets the list of pets in this space.
     *
     * @return The list of pets.
     */
    public List<Pet> getPets() {
        return pets;
    }

    /**
     * Adds an item to this space.
     *
     * @param item The item to add.
     */
    public void addItem(Item item) {
        items.add(item);
    }

    /**
     * Removes an item from this space.
     *
     * @param item The item to remove.
     */
    public void removeItem(Item item) {
        items.remove(item);
    }

    /**
     * Gets the list of items in this space.
     *
     * @return The list of items.
     */
    public List<Item> getItems() {
        return items;
    }

    /**
     * Adds a neighboring space to this space.
     *
     * @param neighbor The neighboring space to add.
     */
    public void addNeighbor(Space neighbor) {
        neighbors.add(neighbor);
    }

    /**
     * Gets the list of neighboring spaces.
     *
     * @return The list of neighboring spaces.
     */
    public List<Space> getNeighbors() {
        return neighbors;
    }

    /**
     * Gets the world that this space belongs to.
     *
     * @return The world instance.
     */
    public World getWorld() {
        return world;
    }

    /**
     * Gets a description of the space, including players, pets, and items present.
     *
     * @return The description of the space.
     */
    public String getDescription() {
        StringBuilder description = new StringBuilder();
        description.append("Space: ").append(name).append("\n");
        
        description.append("Items: ");
        if (items.isEmpty()) {
            description.append("None\n");
        } else {
            for (Item item : items) {
                description.append(item.getName()).append(", ");
            }
            description.delete(description.length() - 2, description.length()); // 删除最后的逗号和空格
            description.append("\n");
        }

        description.append("Players: ");
        if (players.isEmpty()) {
            description.append("None\n");
        } else {
            for (Player player : players) {
                description.append(player.getName()).append(", ");
            }
            description.delete(description.length() - 2, description.length()); // 删除最后的逗号和空格
            description.append("\n");
        }

        description.append("Pets: ");
        if (pets.isEmpty()) {
            description.append("None\n");
        } else {
            for (Pet pet : pets) {
                description.append(pet.getName()).append(", ");
            }
            description.delete(description.length() - 2, description.length()); // 删除最后的逗号和空格
            description.append("\n");
        }

        return description.toString();
    }


    /**
     * Checks if the given player is present in this space.
     *
     * @param player The player to check.
     * @return True if the player is present, false otherwise.
     */
    public boolean containsPlayer(Player player) {
        return players.contains(player);
    }

    /**
     * Checks if the given pet is present in this space.
     *
     * @param pet The pet to check.
     * @return True if the pet is present, false otherwise.
     */
    public boolean containsPet(Pet pet) {
        return pets.contains(pet);
    }

    /**
     * Marks the space as visited.
     */
    public void markVisited() {
        this.isVisited = true;
    }

    /**
     * Checks if the space has been visited.
     *
     * @return True if the space has been visited, false otherwise.
     */
    public boolean isVisited() {
        return isVisited;
    }

    /**
     * Sets the world to which this space belongs.
     *
     * @param world The world instance.
     */
    public void setWorld(World world) {
        if (world == null) {
            throw new IllegalArgumentException("World cannot be null.");
        }
        this.world = world;
    }
}
