package strategy;

import org.junit.Before;
import org.junit.Test;
import world.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import static org.junit.Assert.*;

/**
 * Test class for the ChasePlayerStrategy class.
 */
public class ChasePlayerStrategyTest {
    private World world;
    private Space space1;
    private Space space2;
    private Space space3;
    private Player targetPlayer;
    private Player chaser;
    private ChasePlayerStrategy strategy;

    @Before
    public void setUp() {
        // Initialize world and spaces
        world = new World(new ArrayList<>(), new ArrayList<>(), null, null, null);
        space1 = new Space("Garden", world);
        space2 = new Space("Kitchen", world);
        space3 = new Space("Living Room", world);
        world.getSpaces().add(space1);
        world.getSpaces().add(space2);
        world.getSpaces().add(space3);

        // Set neighbors
        space1.addNeighbor(space2);
        space2.addNeighbor(space1);
        space2.addNeighbor(space3);
        space3.addNeighbor(space2);

        // Initialize strategy with a fixed random seed for consistent testing
        Random random = new Random(42);
        strategy = new ChasePlayerStrategy(random);

        // Initialize players with strategy
        targetPlayer = new HumanPlayer("Target Player", 100, space1);
        chaser = new AIPlayer("Chaser", 100, space2, strategy);
        space1.addPlayer(targetPlayer);
        space2.addPlayer(chaser);
    }

    @Test
    public void testChaseTargetPlayer() {
        // Execute chase strategy
        strategy.move(chaser, world);

        // Verify the chaser has moved to a neighboring space
        Space currentSpace = chaser.getCurrentSpace();
        List<Space> neighbors = space2.getNeighbors();

        assertTrue(neighbors.contains(currentSpace));
        assertTrue(currentSpace.getPlayers().contains(chaser));
    }

    @Test
    public void testChaserRemainsInWorldAfterMultipleMoves() {
        // Move the chaser multiple times
        for (int i = 0; i < 5; i++) {
            strategy.move(chaser, world);
        }

        // Verify the chaser is still in the world
        assertTrue(world.getPlayers().contains(chaser));
    }

    @Test
    public void testChaserDoesNotMoveIfTargetIsInSameSpace() {
        // Move target player to the same space as the chaser
        targetPlayer.move(space2);

        // Execute chase strategy
        strategy.move(chaser, world);

        // Verify the chaser did not move since it is already in the same space as the target
        assertEquals(space2, chaser.getCurrentSpace());
    }

    @Test
    public void testChaserMovesRandomlyWhenMultipleOptions() {
        // Add additional neighbors to test random movement
        space1.addNeighbor(space3);
        space3.addNeighbor(space1);

        // Execute chase strategy multiple times
        for (int i = 0; i < 5; i++) {
            strategy.move(chaser, world);

            // Verify that the chaser remains in a valid space (either kitchen, garden, or living room)
            assertTrue(world.getSpaces().contains(chaser.getCurrentSpace()));
        }
    }
}
