package strategy;

import org.junit.Before;
import org.junit.Test;
import world.*;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

/**
 * Test class for the DepthFirstMoveStrategy class.
 */
public class DepthFirstMoveStrategyTest {
    private World world;
    private Space space1;
    private Space space2;
    private Space space3;
    private Player player;
    private DepthFirstMoveStrategy strategy;

    @Before
    public void setUp() {
        // Initialize world and spaces
        world = new World(new ArrayList<>(), new ArrayList<>(), null, null, null);
        space1 = new Space("Entrance", world);
        space2 = new Space("Hallway", world);
        space3 = new Space("Chamber", world);
        world.getSpaces().add(space1);
        world.getSpaces().add(space2);
        world.getSpaces().add(space3);

        // Set neighbors for depth-first traversal
        space1.addNeighbor(space2);
        space2.addNeighbor(space3);

        // Initialize player and strategy
        strategy = new DepthFirstMoveStrategy();
        player = new HumanPlayer("Explorer", 100, space1);
        space1.addPlayer(player);
    }

    @Test
    public void testDepthFirstMove() {
        // Execute depth-first move
        strategy.move(player, world);

        // Verify the player has moved to the next space in depth-first order
        assertEquals(space2, player.getCurrentSpace());
        assertTrue(space1.getPlayers().isEmpty());
        assertTrue(space2.getPlayers().contains(player));
    }

    @Test
    public void testPlayerMovesThroughAllSpaces() {
        // Move the player through all the spaces in depth-first order
        strategy.move(player, world); // Move to space2
        strategy.move(player, world); // Move to space3

        // Verify player is now in the last space
        assertEquals(space3, player.getCurrentSpace());
        assertTrue(space3.getPlayers().contains(player));
    }

    @Test
    public void testPlayerRemainsInWorldAfterMultipleMoves() {
        // Move the player multiple times
        for (int i = 0; i < 5; i++) {
            strategy.move(player, world);
        }

        // Verify the player is still in the world
        assertTrue(world.getPlayers().contains(player));
    }
}
