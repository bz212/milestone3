package strategy;

import java.util.List;
import java.util.Random;
import java.util.logging.Logger;
import world.Pet;
import world.Player;
import world.Space;
import world.World;

/**
 * RandomPetMoveStrategy implements a strategy where the pet moves to a random neighboring space.
 */
public class RandomPetMoveStrategy implements MoveStrategy {

  private static final Logger logger = Logger.getLogger(RandomPetMoveStrategy.class.getName());
  private final Random random;

  /**
   * Constructs a RandomPetMoveStrategy with a default random generator.
   */
  public RandomPetMoveStrategy() {
    this.random = new Random();
  }

  /**
   * Random movement is not supported for players in this strategy.
   *
   * @param player The player attempting to move.
   * @param world  The game world context.
   * @return A warning message indicating the action is unsupported.
   */
  @Override
  public String move(Player player, World world) {
    logger.warning("RandomPetMoveStrategy does not support player movement.");
    return "Player movement is not supported in RandomPetMoveStrategy.";
  }

  /**
   * Moves the pet to a random neighboring space.
   *
   * @param pet   The pet to move.
   * @param world The game world context.
   * @return A string describing the pet's move action.
   */
  @Override
  public String move(Pet pet, World world) {
    if (pet == null) {
      logger.warning("Pet is null.");
      return "Error: Pet is null.";
    }

    Space currentSpace = pet.getCurrentSpace();
    if (currentSpace == null || currentSpace.getNeighbors().isEmpty()) {
      logger.warning("Pet has no neighboring spaces to move to.");
      return "Error: No neighboring spaces for the pet to move to.";
    }

    List<Space> neighbors = currentSpace.getNeighbors();
    Space nextSpace = neighbors.get(random.nextInt(neighbors.size()));

    pet.move(nextSpace);
    String result = "Pet " + pet.getName() + " moved to " + nextSpace.getName() + " randomly.";
    logger.info(result);
    return result;
  }
}
