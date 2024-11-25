package strategy;

import world.AIPlayer;
import world.World;

/**
 * AIStrategy interface represents the decision-making strategy of AI players in the game.
 */
public interface AIStrategy {
    /**
     * Decides the action that the AI player should take.
     *
     * @param player The AI player taking the action.
     * @param world The game world in which the action takes place.
     */
    void decideAction(AIPlayer player, World world);
}
