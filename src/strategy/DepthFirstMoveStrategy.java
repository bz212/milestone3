package strategy;

import java.util.HashSet;
import java.util.Set;
import java.util.Stack;
import java.util.logging.Logger;
import world.Pet;
import world.Player;
import world.Space;
import world.World;

/**
 * DepthFirstMoveStrategy implements a depth-first traversal strategy
 * for moving the pet in the game.
 */
public class DepthFirstMoveStrategy implements TargetStrategy {

  private static final Logger logger = Logger.getLogger(DepthFirstMoveStrategy.class.getName());
  private final Set<Space> visited;
  private final Stack<Space> stack;

  /**
   * Initializes the DepthFirstMoveStrategy with necessary data structures.
   */
  public DepthFirstMoveStrategy() {
    this.visited = new HashSet<>();
    this.stack = new Stack<>();
  }

  /**
   * Moves the pet in a depth-first manner to traverse the game world.
   *
   * @param pet   The pet to be moved.
   * @param world The game world context.
   * @return A string describing the action taken.
   */
  
  @Override
  public String decideAction(Pet pet, World world) {
    return moveToNextSpace(pet);
  }

  /**
   * Decides the action for a player (not supported in this strategy).
   *
   * @param player The player making the decision.
   * @param world  The game world context.
   * @return A message indicating the action is not supported.
   */
  @Override
  public String decideAction(Player player, World world) {
    // TODO: Implement player-specific action if required.
    return "DecideAction for Player is not supported in DepthFirstMoveStrategy.";
  }
  
  /**
   * Depth-first traversal logic for moving the pet.
   *
   * @param pet The pet to move.
   * @return A string describing the move action.
   */
  private String moveToNextSpace(Pet pet) {
    Space currentSpace = pet.getCurrentSpace();

    if (stack.isEmpty()) {
      stack.push(currentSpace);
    }

    visited.add(currentSpace);

    Space nextSpace = getNextUnvisitedNeighbor(currentSpace);
    if (nextSpace != null) {
      pet.move(nextSpace);
      stack.push(nextSpace);
      String action = pet.getName() + " moved to " + nextSpace.getName()
          + " using depth-first traversal.";
      logger.info(action);
      return action;
    } else {
      stack.pop();
      if (!stack.isEmpty()) {
        Space backtrackSpace = stack.peek();
        pet.move(backtrackSpace);
        String action = pet.getName() + " backtracked to " + backtrackSpace.getName() + ".";
        logger.info(action);
        return action;
      } else {
        String action = pet.getName() + " has no more spaces to explore.";
        logger.info(action);
        return action;
      }
    }
  }

  /**
   * Gets the next unvisited neighbor of the given space.
   *
   * @param space The current space.
   * @return The next unvisited neighbor, or null if all neighbors are visited.
   */
  private Space getNextUnvisitedNeighbor(Space space) {
    for (Space neighbor : space.getNeighbors()) {
      if (!visited.contains(neighbor)) {
        return neighbor;
      }
    }
    return null;
  }

  /**
   * Handles the movement of a target player (unsupported in this strategy).
   *
   * @param player The player to move.
   * @param world  The game world context.
   * @return A warning message indicating unsupported action.
   */
  @Override
  public String moveTarget(Player player, World world) {
    String message = "DepthFirstMoveStrategy does not support player movement.";
    logger.warning(message);
    return message;
  }

  /**
   * Moves the pet using the depth-first strategy.
   *
   * @param pet   The pet to move.
   * @param world The game world context.
   * @return A string describing the move action.
   */
  @Override
  public String move(Pet pet, World world) {
    return moveToNextSpace(pet);
  }
  
}
