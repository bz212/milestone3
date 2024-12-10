package strategy;

import java.util.List;
import java.util.Random;
import java.util.logging.Logger;
import world.Pet;
import world.Player;
import world.Space;
import world.World;

/**
 * RandomTargetStrategy implements a strategy for moving the target randomly.
 */
public class RandomTargetStrategy implements TargetStrategy {

  private static final Logger logger = Logger.getLogger(RandomTargetStrategy.class.getName());
  private final Random random;

  /**
   * Initializes the RandomTargetStrategy with a random number generator.
   */
  public RandomTargetStrategy() {
    this.random = new Random();
  }

  /**
   * Moves the target player to a random neighboring space.
   *
   * @param target The target player to move.
   * @param world  The game world context.
   * @return A string describing the move action.
   */
  @Override
  public String moveTarget(Player target, World world) {
    if (target == null) {
      logger.warning("Target is null.");
      return "Error: Target is null.";
    }

    Space currentSpace = target.getCurrentSpace();
    if (currentSpace == null || currentSpace.getNeighbors().isEmpty()) {
      logger.warning("Target has no neighboring spaces to move to.");
      return "Error: Target has no neighboring spaces to move to.";
    }

    List<Space> neighbors = currentSpace.getNeighbors();
    Space nextSpace = neighbors.get(random.nextInt(neighbors.size()));

    target.setCurrentSpace(nextSpace);
    String result = "Target " + target.getName() + " moved to " 
        + nextSpace.getName() + " randomly.";
    logger.info(result);
    return result;
  }

  /**
   * Decides the action for the given pet (not implemented in this strategy).
   *
   * @param pet   The pet making the decision.
   * @param world The game world context.
   * @return A message indicating the method is not implemented.
   */
  @Override
  public String decideAction(Pet pet, World world) {
    logger.warning("decideAction for Pet is not implemented in RandomTargetStrategy.");
    return "RandomTargetStrategy does not implement decideAction for Pet.";
  }

  /**
   * Decides the action for the given player (not implemented in this strategy).
   *
   * @param player The player making the decision.
   * @param world  The game world context.
   * @return A message indicating the method is not implemented.
   */
  @Override
  public String decideAction(Player player, World world) {
    logger.warning("decideAction for Player is not implemented in RandomTargetStrategy.");
    return "RandomTargetStrategy does not implement decideAction for Player.";
  }

  /**
   * Moves the pet to a random space (not implemented in this strategy).
   *
   * @param pet   The pet to move.
   * @param world The game world context.
   * @return A message indicating the method is not implemented.
   */
  @Override
  public String move(Pet pet, World world) {
    logger.warning("move for Pet is not implemented in RandomTargetStrategy.");
    return "RandomTargetStrategy does not implement move for Pet.";
  }

}
