package strategy;

import world.AIPlayer;
import world.Pet;
import world.Player;
import world.Space;
import world.World;

import java.util.List;
import java.util.Random;

/**
 * RandomMoveStrategy implements a strategy where the target character moves to a random neighboring space.
 */
public class RandomMoveStrategy implements TargetStrategy, AIStrategy, MoveStrategy {
    private Random random;

    /**
     * Initializes the RandomMoveStrategy with a random number generator.
     */
    public RandomMoveStrategy() {
        this.random = new Random();
    }

    /**
     * Moves the target character to a random neighboring space.
     *
     * @param target The target character to be moved.
     * @param world  The game world context.
     */
    @Override
    public void moveTarget(Player target, World world) {
        Space currentSpace = target.getCurrentSpace();
        List<Space> neighbors = currentSpace.getNeighbors();

        if (neighbors.isEmpty()) {
            System.out.println(target.getName() + " has no neighboring spaces to move to.");
            return;
        }

        Space nextSpace = neighbors.get(random.nextInt(neighbors.size()));
        target.move(nextSpace);
        System.out.println(target.getName() + " moved to " + nextSpace.getName() + " randomly.");
    }

    @Override
    public void decideAction(AIPlayer player, World world) {
      // TODO Auto-generated method stub
      
    }

    @Override
    public void movePet(Pet pet, World world) {
      // TODO Auto-generated method stub
      
    }

    @Override
    public void move(Player player, World world) {
      // TODO Auto-generated method stub
      
    }

    @Override
    public void move(Pet pet, World world) {
      // TODO Auto-generated method stub
      
    }
}
