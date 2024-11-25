package world;

import strategy.MoveStrategy;

/**
 * Pet represents the target character's pet, which affects visibility of spaces in the game.
 */
public class Pet {
    private String name;
    private Space currentSpace;
    private MoveStrategy strategy;
    private World world;

    /**
     * Initializes a Pet with a name, its starting space, and movement strategy.
     *
     * @param name          The name of the pet.
     * @param startingSpace The starting space of the pet.
     * @param strategy      The movement strategy for the pet.
     */
    public Pet(String name, Space startingSpace, MoveStrategy strategy) {
        this.name = name;
        this.currentSpace = startingSpace;
        this.strategy = strategy;
        if (startingSpace != null) {
            startingSpace.addPet(this);  // Add the pet to the initial space
        }
    }

    /**
     * Sets the world for the pet.
     *
     * @param world The world instance to set.
     */
    public void setWorld(World world) {
        this.world = world;
    }

    /**
     * Moves the pet to a new space.
     *
     * @param newSpace The space to which the pet is moving.
     */
    public void moveTo(Space newSpace) {
        if (this.currentSpace != null) {
            this.currentSpace.removePet(this);  // Remove the pet from the current space
        }
        this.currentSpace = newSpace;
        if (newSpace != null) {
            newSpace.addPet(this);  // Add the pet to the new space
        }
    }

    /**
     * Moves the pet based on its movement strategy.
     */
    public void move() {
        if (strategy != null && world != null) {
            strategy.move(this, world);  // Use the strategy to move the pet
        }
    }

    /**
     * Gets the current space of the pet.
     *
     * @return The current space of the pet.
     */
    public Space getCurrentSpace() {
        return currentSpace;
    }

    /**
     * Gets the movement strategy of the pet.
     *
     * @return The movement strategy.
     */
    public MoveStrategy getStrategy() {
        return strategy;
    }

    @Override
    public String toString() {
        return "Pet[name=" + name + ", currentSpace=" + currentSpace.getName() + "]";
    }

    /**
     * Gets the name of the pet.
     *
     * @return The name of the pet.
     */
    public String getName() {
        return this.name;
    }
}
