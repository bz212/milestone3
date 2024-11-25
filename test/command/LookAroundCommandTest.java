package command;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import world.*;

import static org.junit.Assert.*;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.ArrayList;

/**
 * Test class for the LookAroundCommand class.
 */
public class LookAroundCommandTest {
    private World world;
    private Space space1;
    private Space space2;
    private Player player;
    private Item item;
    private LookAroundCommand command;

    private final ByteArrayOutputStream outContent = new ByteArrayOutputStream();
    private final PrintStream originalOut = System.out;

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

        // Initialize player and item
        player = new HumanPlayer("Alice", 100, space1);
        item = new Item("Book", 0, "An old dusty book.");
        space1.addPlayer(player);
        space1.addItem(item);

        // Initialize command
        command = new LookAroundCommand(player, world);

        // Redirect System.out to capture output
        System.setOut(new PrintStream(outContent));
    }

    @After
    public void restoreStreams() {
        System.setOut(originalOut);
    }

    @Test
    public void testExecuteLookAroundCurrentSpace() {
        // Execute the command to look around the current space
        command.execute();

        // Verify the output contains information about the current space, players, and items
        String result = outContent.toString();
        assertTrue(result.contains("You are currently in: Living Room"));
        assertTrue(result.contains("Items in this space: Book"));
        assertTrue(result.contains("There are no other players in this space."));
    }

    @Test
    public void testExecuteLookAroundWithNeighboringSpaces() {
        // Add another player to the neighboring space
        Player neighborPlayer = new HumanPlayer("Bob", 100, space2);
        space2.addPlayer(neighborPlayer);

        // Execute the command to look around the current space
        command.execute();

        // Verify the output contains information about neighboring spaces and players
        String result = outContent.toString();
        assertTrue(result.contains("From here, you can see:"));
        assertTrue(result.contains("Kitchen with no items and Bob"));
    }

    @Test
    public void testExecuteLookAroundEmptySpace() {
        // Move player to an empty space
        player.move(space2);

        // Execute the command to look around the current space
        command.execute();

        // Verify the output contains information about the current space with no players or items
        String result = outContent.toString();
        assertTrue(result.contains("You are currently in: Kitchen"));
        assertTrue(result.contains("There are no items in this space."));
        assertTrue(result.contains("There are no other players in this space."));
    }

    @Test
    public void testExecuteLookAroundNeighborWithoutItemsOrPlayers() {
        // Execute the command to look around the current space
        command.execute();

        // Verify the output contains information about neighboring spaces with no items or players
        String result = outContent.toString();
        assertTrue(result.contains("From here, you can see:"));
        assertTrue(result.contains("Kitchen with no items and no players"));
    }
}
