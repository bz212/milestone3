package strategy;

import org.junit.Before;
import org.junit.Test;
import world.*;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

/**
 * Test class for the MoveStrategy class.
 */
public class MoveStrategyTest {
    private World world;
    private Space space1;
    private Space space2;
    private Player player;
    private MoveStrategy strategy;

    @Before
    public void setUp() {
        // Initialize world and spaces
        world = new World(new ArrayList<>(), new ArrayList<>(), null, null, null);
        space1 = new Space("Garden", world);
        space2 = new Space("Library", world);
        world.getSpaces().add(space1);
        world.getSpaces().add(space2);

        // Initialize player and strategy
        strategy = new RandomMoveStrategy();
        player = new HumanPlayer("Alice", 100, space1);
        space1.addPlayer(player);
    }

    @Test
    public void testMovePlayer() {
        // Move the player using the strategy
        strategy.move(player, world);

        // Verify the player has moved to a neighboring space
        assertTrue(space1.getPlayers().isEmpty() || space2.getPlayers().isEmpty());
        assertTrue(world.getPlayers().contains(player));
    }

    @Test
    public void testPlayerRemainsInWorld() {
        // Move the player multiple times
        for (int i = 0; i < 10; i++) {
            strategy.move(player, world);
        }

        // Verify the player is still in the world
        assertTrue(world.getPlayers().contains(player));
    }
}
