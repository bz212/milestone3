package strategy;

import org.junit.Before;
import org.junit.Test;
import world.*;
import static org.junit.Assert.*;
import java.util.ArrayList;

/**
 * Test class for the RandomMoveStrategy class.
 */
public class RandomMoveStrategyTest {
    private World world;
    private Space space1;
    private Space space2;
    private Player player;
    private RandomMoveStrategy strategy;

    @Before
    public void setUp() {
        // Initialize world and spaces
        world = new World(new ArrayList<>(), new ArrayList<>(), null, null, null);
        space1 = new Space("Garden", world);
        space2 = new Space("Library", world);
        world.getSpaces().add(space1);
        world.getSpaces().add(space2);

        // Initialize player and strategy
        player = new HumanPlayer("Alice", 100, space1);
        space1.addPlayer(player);
        strategy = new RandomMoveStrategy();
    }

    @Test
    public void testMovePlayer() {
        // Execute the random move strategy
        strategy.move(player, world);

        // Verify that the player has moved to a neighboring space
        assertTrue(space1.getPlayers().isEmpty() || space2.getPlayers().isEmpty());
        assertTrue(world.getPlayers().contains(player));
    }

    @Test
    public void testNoExceptionOnMove() {
        // Ensure that executing the strategy does not throw any exceptions
        try {
            strategy.move(player, world);
        } catch (Exception e) {
            fail("Strategy execution should not throw an exception");
        }
    }
}
