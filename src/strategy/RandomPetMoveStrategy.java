package strategy;

import world.Pet;
import world.Player;
import world.Space;
import world.World;

import java.util.List;
import java.util.Random;

/**
 * RandomPetMoveStrategy implements a strategy where the pet moves to a random neighboring space.
 */
public class RandomPetMoveStrategy implements MoveStrategy {
    private Random random;

    /**
     * Initializes the RandomPetMoveStrategy with a random number generator.
     */
    public RandomPetMoveStrategy() {
        this.random = new Random();
    }

    /**
     * Moves the pet to a random neighboring space.
     *
     * @param pet   The pet to be moved.
     * @param world The game world context.
     */
    @Override
    public void movePet(Pet pet, World world) {
        Space currentSpace = pet.getCurrentSpace();
        List<Space> neighbors = currentSpace.getNeighbors();

        if (neighbors.isEmpty()) {
            System.out.println(pet.toString() + " has no neighboring spaces to move to.");
            return;
        }

        Space nextSpace = neighbors.get(random.nextInt(neighbors.size()));
        pet.moveTo(nextSpace);
        System.out.println(pet.toString() + " moved to " + nextSpace.getName() + " randomly.");
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
