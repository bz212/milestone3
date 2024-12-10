package strategy;

import world.Pet;
import world.Player;
import world.World;

/**
 * AIStrategy interface represents the decision-making strategy of AI players in the game.
 */
public interface AiStrategy {

  /**
   * Decides the action that the AI player should take based on the current state of the world.
   *
   * @param player The AI player taking the action.
   * @param world  The game world in which the action takes place.
   * @return A string describing the action taken by the AI player.
   */
  String decideAction(Player player, World world);

  /**
   * Moves the pet to a new space in the game world, based on the AI strategy.
   *
   * @param pet   The pet that needs to be moved.
   * @param world The game world in which the pet will be moved.
   * @return A string describing the move action performed for the pet.
   */
  String move(Pet pet, World world);
}
