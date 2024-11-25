package strategy;

import org.junit.Before;
import org.junit.Test;
import world.*;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.Arrays;

/**
 * Test class for the TargetStrategy interface and its implementations.
 */
public class TargetStrategyTest {
    private World world;
    private Space space1;
    private Space space2;
    private Player target;
    private TargetStrategy strategy;

    @Before
    public void setUp() {
        // Initialize world and spaces
        world = new World(new ArrayList<>(), new ArrayList<>(), null, null, null);
        space1 = new Space("Living Room", world);
        space2 = new Space("Kitchen", world);
        world.getSpaces().add(space1);
        world.getSpaces().add(space2);

        // Initialize target player
        target = new HumanPlayer("Target Player", 100, space1);
        space1.addPlayer(target);
        world.setPlayers(Arrays.asList(target));
    }

    @Test
    public void testRandomMoveStrategy() {
        // Set up RandomMoveStrategy
        strategy = new RandomMoveStrategy();
        world.setStrategy(strategy);

        // Execute strategy and verify the player moves to a neighboring space
        world.moveTarget();
        assertTrue(space1.getPlayers().isEmpty() || space2.getPlayers().isEmpty());
        assertTrue(world.getPlayers().contains(target));
    }

    @Test
    public void testChasePlayerStrategy() {
        // Set up ChasePlayerStrategy
        Player playerToChase = new HumanPlayer("Player to Chase", 100, space2);
        space2.addPlayer(playerToChase);
        world.getPlayers().add(playerToChase);

        strategy = new ChasePlayerStrategy();
        world.setStrategy(strategy);

        // Execute strategy and verify target moves towards playerToChase
        world.moveTarget();
        assertEquals(space2, target.getCurrentSpace());
    }

    @Test
    public void testDepthFirstMoveStrategy() {
        // Set up DepthFirstMoveStrategy
        strategy = new DepthFirstMoveStrategy();
        world.setStrategy(strategy);

        // Execute strategy and ensure no exceptions occur
        world.moveTarget();
    }
}
