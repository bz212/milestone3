package world;

import java.util.logging.Logger;
import strategy.TargetStrategy;

/**
 * Represents a pet in the game world.
 */
public class Pet {

  private static final Logger LOGGER = Logger.getLogger(Pet.class.getName());

  private final String name;
  private Space currentSpace;
  private World world; // Reference to the World
  private TargetStrategy strategy;

  /**
   * Common validation logic for constructors.
   *
   * @param name       The name of the pet.
   * @param startSpace The starting space of the pet.
   */
  private void validatePet(String name, Space startSpace) {
    if (name == null || name.isEmpty()) {
      throw new IllegalArgumentException("Pet name cannot be null or empty.");
    }
    if (startSpace == null) {
      throw new IllegalArgumentException("Starting space cannot be null.");
    }
  }

  /**
   * Initializes a Pet with a name, starting space, and movement strategy.
   *
   * @param name        The name of the pet.
   * @param startSpace  The starting space of the pet.
   * @param strategy    The movement strategy of the pet.
   */
  public Pet(String name, Space startSpace, TargetStrategy strategy) {
    validatePet(name, startSpace);
    if (strategy == null) {
      throw new IllegalArgumentException("Strategy cannot be null.");
    }

    this.name = name;
    this.currentSpace = startSpace;
    this.strategy = strategy;

    LOGGER.info(() -> "Pet " + name + " initialized at space: " + startSpace.getName());
  }

  /**
   * Simplified constructor for Pet without a strategy.
   *
   * @param name        The name of the pet.
   * @param startSpace  The starting space of the pet.
   */
  public Pet(String name, Space startSpace) {
    validatePet(name, startSpace);

    this.name = name;
    this.currentSpace = startSpace;
    this.strategy = null; // No strategy provided

    LOGGER.info(() -> "Pet " + name + " initialized at space: " + startSpace.getName());
  }

  /**
   * Moves the pet to a new space.
   *
   * @param newSpace The new space to move to.
   */
  public void move(Space newSpace) {
    if (newSpace == null) {
      throw new IllegalArgumentException("New space cannot be null.");
    }
    LOGGER.info(() -> "Pet " + name + " moving from " + currentSpace.getName() 
        + " to " + newSpace.getName());
    this.currentSpace = newSpace;
  }

  /**
   * Gets the current space of the pet.
   *
   * @return The current space.
   */
  public Space getCurrentSpace() {
    return currentSpace;
  }

  /**
   * Sets the world context for the pet.
   *
   * @param world The world to set.
   */
  public void setWorld(World world) {
    if (world == null) {
      throw new IllegalArgumentException("World cannot be null.");
    }
    this.world = world;
    LOGGER.info(() -> "World context set for pet: " + name);
  }

  /**
   * Gets the name of the pet.
   *
   * @return The pet's name.
   */
  public String getName() {
    return name;
  }

  /**
   * Decides the action for the pet based on its strategy.
   */
  public void decideAction() {
    if (strategy == null) {
      LOGGER.warning(() -> "Strategy is not set for pet: " + name);
      return;
    }
    if (world == null) {
      LOGGER.warning(() -> "World context is not set for pet: " + name);
      return;
    }
    LOGGER.info(() -> "Pet " + name + " executing strategy.");
    strategy.decideAction(this, world);
  }

  /**
   * Provides detailed information about the pet.
   *
   * @return A string with the pet's name, current space, and strategy.
   */
  public String getDetails() {
    String spaceName = currentSpace != null ? currentSpace.getName() : "Unknown";
    String strategyName = strategy != null ? strategy.getClass().getSimpleName() : "None";
    return "Pet Details: [Name: " + name 
        + ", Current Space: " + spaceName 
        + ", Strategy: " + strategyName + "]";
  }
}
