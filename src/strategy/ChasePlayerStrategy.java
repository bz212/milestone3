package strategy;

import java.util.List;
import java.util.Random;
import java.util.logging.Logger;
import world.AiPlayer;
import world.Pet;
import world.Player;
import world.Space;
import world.World;

/**
 * ChasePlayerStrategy implements a strategy 
 * where the target character moves towards the nearest player.
 */
public class ChasePlayerStrategy implements TargetStrategy, AiStrategy {

  private static final Logger logger = Logger.getLogger(ChasePlayerStrategy.class.getName());
  private final Random random;

  /**
   * Constructs a ChasePlayerStrategy with a custom random generator.
   *
   * @param random The random generator for selecting moves.
   */
  public ChasePlayerStrategy(Random random) {
    this.random = random;
  }

  /**
   * Constructs a ChasePlayerStrategy with a default random generator.
   */
  public ChasePlayerStrategy() {
    this.random = new Random();
  }

  @Override
  public String decideAction(Player player, World world) {
    if (!(player instanceof AiPlayer)) {
      throw new IllegalArgumentException("Player must be of type AIPlayer");
    }
    return move((AiPlayer) player, world);
  }

  @Override
  public String decideAction(Pet pet, World world) {
    logger.info("ChasePlayerStrategy: Pet is moving to chase the player.");
    return move(pet, world);
  }

  /**
   * Moves the AI player towards the closest player in the world.
   *
   * @param chaser The AI player performing the move.
   * @param world  The game world where the move happens.
   * @return A string describing the move action.
   */
  public String move(Player chaser, World world) {
    Space currentSpace = chaser.getCurrentSpace();
    List<Space> neighbors = currentSpace.getNeighbors();
    Player closestPlayer = findClosestPlayer(chaser, world);

    if (closestPlayer == null) {
      String action = chaser.getName() + " has no players to chase.";
      logger.info(action);
      return action;
    }

    Space targetSpace = closestPlayer.getCurrentSpace();
    Space nextSpace = findBestNeighbor(neighbors, targetSpace);

    if (nextSpace == null && !neighbors.isEmpty()) {
      nextSpace = neighbors.get(random.nextInt(neighbors.size()));
    }

    if (nextSpace != null) {
      chaser.move(nextSpace);
      String action = chaser.getName() + " moved towards " + closestPlayer.getName() 
          + " and entered " + nextSpace.getName() + ".";
      logger.info(action);
      return action;
    } else {
      String action = chaser.getName() + " has no valid moves.";
      logger.info(action);
      return action;
    }
  }

  @Override
  public String move(Pet pet, World world) {
    Space currentSpace = pet.getCurrentSpace();
    List<Space> neighbors = currentSpace.getNeighbors();
    Player closestPlayer = findClosestPlayer(pet, world);

    if (closestPlayer == null) {
      String action = pet.getName() + " has no players to chase.";
      logger.info(action);
      return action;
    }

    Space targetSpace = closestPlayer.getCurrentSpace();
    Space nextSpace = findBestNeighbor(neighbors, targetSpace);

    if (nextSpace == null && !neighbors.isEmpty()) {
      nextSpace = neighbors.get(random.nextInt(neighbors.size()));
    }

    if (nextSpace != null) {
      pet.move(nextSpace);
      String action = pet.getName() + " moved towards " + closestPlayer.getName() 
          + " and entered " + nextSpace.getName() + ".";
      logger.info(action);
      return action;
    } else {
      String action = pet.getName() + " has no valid moves.";
      logger.info(action);
      return action;
    }
  }

  /**
   * Finds the closest player to the given chaser.
   */
  private Player findClosestPlayer(Player chaser, World world) {
    List<Player> players = world.getPlayers();
    Player closestPlayer = null;
    double closestDistance = Double.MAX_VALUE;

    for (Player player : players) {
      if (!player.equals(chaser)) {
        double distance = calculateDistance(chaser.getCurrentSpace(), player.getCurrentSpace());
        if (distance < closestDistance) {
          closestDistance = distance;
          closestPlayer = player;
        }
      }
    }
    return closestPlayer;
  }

  private Player findClosestPlayer(Pet pet, World world) {
    List<Player> players = world.getPlayers();
    Player closestPlayer = null;
    double closestDistance = Double.MAX_VALUE;

    for (Player player : players) {
      double distance = calculateDistance(pet.getCurrentSpace(), player.getCurrentSpace());
      if (distance < closestDistance) {
        closestDistance = distance;
        closestPlayer = player;
      }
    }
    return closestPlayer;
  }

  private Space findBestNeighbor(List<Space> neighbors, Space targetSpace) {
    Space bestNeighbor = null;
    double closestDistance = Double.MAX_VALUE;

    for (Space neighbor : neighbors) {
      double distance = calculateDistance(neighbor, targetSpace);
      if (distance < closestDistance) {
        closestDistance = distance;
        bestNeighbor = neighbor;
      }
    }
    return bestNeighbor;
  }

  /**
   * Calculates the Euclidean distance between two spaces.
   */
  double calculateDistance(Space space1, Space space2) {
    int dx = space1.getX() - space2.getX();
    int dy = space1.getY() - space2.getY();
    return Math.sqrt(dx * dx + dy * dy);
  }

  @Override
  public String moveTarget(Player player, World world) {
    // TODO: Implementation required for moveTarget
    return null;
  }
}
