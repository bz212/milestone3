package command;

import org.junit.Before;
import org.junit.Test;
import world.*;

import static org.junit.Assert.*;

import java.util.ArrayList;

/**
 * Test class for the MoveCommand class.
 */
public class MoveCommandTest {
    private World world;
    private Space space1;
    private Space space2;
    private Player player;
    private MoveCommand command;

    @Before
    public void setUp() {
        // Initialize world and spaces
        world = new World(new ArrayList<>(), new ArrayList<>(), null, null, null);
        space1 = new Space("Living Room", world);
        space2 = new Space("Kitchen", world);
        world.getSpaces().add(space1);
        world.getSpaces().add(space2);

        // Set neighbors
        space1.addNeighbor(space2);

        // Initialize player
        player = new HumanPlayer("Alice", 100, space1);
        space1.addPlayer(player);

        // Initialize command
        command = new MoveCommand(player, space2);
    }

    @Test
    public void testExecutePlayerMoved() {
        // Execute command to move the player
        command.execute();

        // Verify the player has moved to the new space
        assertEquals(space2, player.getCurrentSpace());
        assertFalse(space1.getPlayers().contains(player));
        assertTrue(space2.getPlayers().contains(player));
    }

    @Test
    public void testExecutePlayerCannotMoveToNonNeighborSpace() {
        // Create a non-neighbor space
        Space space3 = new Space("Garden", world);
        world.getSpaces().add(space3);

        // Attempt to move player to a non-neighbor space
        command = new MoveCommand(player, space3);
        command.execute();

        // Verify the player did not move
        assertEquals(space1, player.getCurrentSpace());
        assertFalse(space3.getPlayers().contains(player));
    }

    @Test
    public void testExecutePlayerAlreadyInTargetSpace() {
        // Attempt to move player to the same space they are already in
        command = new MoveCommand(player, space1);
        command.execute();

        // Verify the player is still in the same space
        assertEquals(space1, player.getCurrentSpace());
        assertTrue(space1.getPlayers().contains(player));
    }
}
