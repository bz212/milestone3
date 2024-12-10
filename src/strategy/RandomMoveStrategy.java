package strategy;

import java.util.List;
import java.util.Random;
import java.util.logging.Logger;
import world.Pet;
import world.Player;
import world.Space;
import world.World;

/**
 * RandomMoveStrategy implements a strategy where the player or pet
 * moves to a random neighboring space.
 */
public class RandomMoveStrategy implements TargetStrategy, AiStrategy, MoveStrategy {

  private static final Logger logger = Logger.getLogger(RandomMoveStrategy.class.getName());
  private final Random random;
  private World world;

  /**
   * Constructs a RandomMoveStrategy with a specified world.
   *
   * @param world The game world context.
   */
  public RandomMoveStrategy(World world) {
    if (world == null) {
      throw new IllegalArgumentException("World cannot be null.");
    }
    this.world = world;
    this.random = new Random();
  }

  /**
   * Constructs a RandomMoveStrategy with a default random generator.
   */
  public RandomMoveStrategy() {
    this.random = new Random();
  }

  @Override
  public String decideAction(Player player, World world) {
    if (player == null || world == null) {
      logger.warning("Player or world is null. Cannot decide action.");
      return "Action failed: Player or world is null.";
    }

    String result = moveToRandomSpace(player);
    logger.info(result);
    return result;
  }

  @Override
  public String decideAction(Pet pet, World world) {
    if (pet == null || world == null) {
      logger.warning("Pet or world is null. Cannot decide action.");
      return "Action failed: Pet or world is null.";
    }

    String result = moveToRandomSpace(pet);
    logger.info(result);
    return result;
  }

  @Override
  public String move(Pet pet, World world) {
    if (pet == null || world == null) {
      logger.warning("Pet or world is null. Cannot execute random move strategy.");
      return "Action failed: Pet or world is null.";
    }

    String result = moveToRandomSpace(pet);
    logger.info(result);
    return result;
  }

  @Override
  public String move(Player player, World world) {
    if (player == null || world == null) {
      logger.warning("Player or world is null. Cannot move player.");
      return "Error: Player or world is null.";
    }

    String result = moveToRandomSpace(player);
    logger.info(result);
    return result;
  }

  @Override
  public String moveTarget(Player player, World world) {
    // TODO: Implement logic for moving the target player.
    logger.info("RandomMoveStrategy: moveTarget is not yet implemented.");
    return "RandomMoveStrategy: moveTarget not implemented.";
  }

  /**
   * Moves an entity (Player or Pet) to a random neighboring space.
   *
   * @param entity The entity to move (Player or Pet).
   * @return A string describing the move action.
   */
  private <T> String moveToRandomSpace(T entity) {
    if (entity == null) {
      return "Error: Entity is null.";
    }

    Space currentSpace = null;
    if (entity instanceof Player) {
      currentSpace = ((Player) entity).getCurrentSpace();
    } else if (entity instanceof Pet) {
      currentSpace = ((Pet) entity).getCurrentSpace();
    }

    if (currentSpace == null || currentSpace.getNeighbors().isEmpty()) {
      return (entity instanceof Player ? "Player" : "Pet")
          + " has no neighboring spaces to move to.";
    }

    List<Space> neighbors = currentSpace.getNeighbors();
    Space nextSpace = neighbors.get(random.nextInt(neighbors.size()));

    if (entity instanceof Player) {
      ((Player) entity).move(nextSpace);
      return "Player " + ((Player) entity).getName()
          + " moved to " + nextSpace.getName() + " randomly.";
    } else if (entity instanceof Pet) {
      ((Pet) entity).move(nextSpace);
      return "Pet " + ((Pet) entity).getName()
          + " moved to " + nextSpace.getName() + " randomly.";
    }

    return "Unknown entity type.";
  }
}
