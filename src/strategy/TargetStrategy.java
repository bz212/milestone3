package strategy;

import world.World;
import world.Player;

/**
 * TargetStrategy interface defines a movement strategy for the target character in the game.
 */
public interface TargetStrategy {
    /**
     * Moves the target character based on the strategy implemented.
     *
     * @param target The target character to be moved.
     * @param world  The game world context.
     */
    void moveTarget(Player target, World world);
}
