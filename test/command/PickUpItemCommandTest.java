package command;

import org.junit.Before;
import org.junit.Test;
import world.*;

import static org.junit.Assert.*;

import java.util.ArrayList;

/**
 * Test class for the PickUpItemCommand class.
 */
public class PickUpItemCommandTest {
    private World world;
    private Space space;
    private Player player;
    private Item item;
    private PickUpItemCommand command;

    @Before
    public void setUp() {
        // Initialize world and spaces
        world = new World(new ArrayList<>(), new ArrayList<>(), null, null, null);
        space = new Space("Living Room", world);
        world.getSpaces().add(space);

        // Initialize player and item
        player = new HumanPlayer("Alice", 100, space);
        item = new Item("Magic Wand", 5, "A wand with magical powers.");
        space.addItem(item);
        space.addPlayer(player);

        // Initialize command
        command = new PickUpItemCommand(player, item);
    }

    @Test
    public void testExecuteItemPickedUp() {
        // Execute command to pick up the item
        command.execute();

        // Verify the item is now in the player's inventory and no longer in the space
        assertTrue(player.getInventory().contains(item));
        assertFalse(space.getItems().contains(item));
    }

    @Test
    public void testExecuteItemNotInSpace() {
        // Remove the item from the space
        space.removeItem(item);

        // Execute command to pick up the item
        command.execute();

        // Verify the item was not added to the player's inventory
        assertFalse(player.getInventory().contains(item));
    }

    @Test
    public void testExecutePlayerNotInSpace() {
        // Move player to a different space
        Space otherSpace = new Space("Kitchen", world);
        world.getSpaces().add(otherSpace);
        player.move(otherSpace);

        // Execute command to pick up the item
        command.execute();

        // Verify the item is still in the original space and not in the player's inventory
        assertTrue(space.getItems().contains(item));
        assertFalse(player.getInventory().contains(item));
    }
}
