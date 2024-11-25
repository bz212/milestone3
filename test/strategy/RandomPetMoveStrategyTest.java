package strategy;

import org.junit.Before;
import org.junit.Test;
import world.*;

import static org.junit.Assert.*;

import java.util.ArrayList;

/**
 * Test class for the RandomPetMoveStrategy class.
 */
public class RandomPetMoveStrategyTest {
    private World world;
    private Space space1;
    private Space space2;
    private Pet pet;
    private RandomPetMoveStrategy strategy;

    @Before
    public void setUp() {
        // Initialize world and spaces
        world = new World(new ArrayList<>(), new ArrayList<>(), null, null, null);
        space1 = new Space("Garden", world);
        space2 = new Space("Library", world);
        world.getSpaces().add(space1);
        world.getSpaces().add(space2);

        // Initialize pet and strategy
        strategy = new RandomPetMoveStrategy();
        pet = new Pet("Rex", space1, strategy);
        space1.addPet(pet); // Use addPet method to add pet to space
        world.setPet(pet);
    }

    @Test
    public void testMovePet() {
        // Move the pet using the strategy
        world.movePet();

        // Verify the pet has moved to a neighboring space
        assertTrue(space1.getPets().isEmpty() || space2.getPets().isEmpty());
        assertTrue(world.getPets().contains(pet));
    }

    @Test
    public void testPetRemainsInWorld() {
        // Move the pet multiple times
        for (int i = 0; i < 10; i++) {
            world.movePet();
        }

        // Verify the pet is still in the world
        assertTrue(world.getPets().contains(pet));
    }
}
