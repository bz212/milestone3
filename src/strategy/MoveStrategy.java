package strategy;

import world.Pet;
import world.Player;
import world.World;

/**
 * MoveStrategy interface defines a movement strategy for the entities in the game.
 */
public interface MoveStrategy {

  /**
   * Moves the player based on the strategy.
   *
   * @param player The player to move.
   * @param world  The game world context.
   * @return A string describing the movement action taken by the player.
   */
  String move(Player player, World world);

  /**
   * Moves the pet based on the strategy.
   *
   * @param pet   The pet to move.
   * @param world The game world context.
   * @return A string describing the movement action taken by the pet.
   */
  String move(Pet pet, World world);
}
