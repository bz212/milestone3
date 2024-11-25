package strategy;

import world.Pet;
import world.Player;
import world.Space;
import world.World;

/**
 * MoveStrategy interface defines a movement strategy for the pet in the game.
 */
public interface MoveStrategy {
    /**
     * Moves the pet based on the strategy implemented.
     *
     * @param pet    The pet to be moved.
     * @param world  The game world context.
     */
    void movePet(Pet pet, World world);

    void move(Player player, World world);

    void move(Pet pet, World world);
}
