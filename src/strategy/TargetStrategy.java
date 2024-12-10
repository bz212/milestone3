package strategy;

import world.Pet;
import world.Player;
import world.World;

/**
 * TargetStrategy interface defines movement strategies for target characters.
 */
public interface TargetStrategy {

  /**
   * Decides the action for a pet.
   *
   * @param pet   The pet taking the action.
   * @param world The game world in which the action takes place.
   * @return A string describing the action taken.
   */
  String decideAction(Pet pet, World world);
  
  /**
   * Decides the action for a player.
   *
   * @param player The player taking the action.
   * @param world  The game world in which the action takes place.
   * @return A string describing the action taken.
   */
  String decideAction(Player player, World world);

  /**
   * Moves the pet to a new location in the game world.
   *
   * @param pet   The pet to move.
   * @param world The game world context.
   * @return A string describing the move action.
   */
  String move(Pet pet, World world);

  /**
   * Moves the target player in the game world.
   *
   * @param player The target player to move.
   * @param world  The game world context.
   * @return A string describing the move action.
   */
  String moveTarget(Player player, World world);
}
