package world;

import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

/**
 * Test class for the Space class.
 */
public class SpaceTest {
    private Space space;
    private World world;
    private Player player;
    private Item item;
    private Space neighborSpace;

    @Before
    public void setUp() {
        // Initialize world
        List<Space> spaces = new ArrayList<>();
        world = new World(spaces, new ArrayList<>(), null, null, null);

        // Initialize space
        space = new Space("Living Room", world);
        spaces.add(space);
        neighborSpace = new Space("Kitchen", world);
        spaces.add(neighborSpace);

        // Initialize player and item
        player = new HumanPlayer("Alice", 100, space);
        item = new Item("Sword", 10, "A sharp blade.");
    }

    @Test
    public void testAddAndRemovePlayer() {
        // Add player to space
        space.addPlayer(player);
        assertTrue(space.containsPlayer(player));

        // Remove player from space
        space.removePlayer(player);
        assertFalse(space.containsPlayer(player));
    }

    @Test
    public void testAddAndRemoveItem() {
        // Add item to space
        space.addItem(item);
        assertTrue(space.getItems().contains(item));

        // Remove item from space
        space.removeItem(item);
        assertFalse(space.getItems().contains(item));
    }

    @Test
    public void testAddNeighbor() {
        // Add neighbor space
        space.addNeighbor(neighborSpace);
        assertTrue(space.getNeighbors().contains(neighborSpace));
    }

    @Test
    public void testGetDescription() {
        // Add player and item to space
        space.addPlayer(player);
        space.addItem(item);

        // Get description
        String description = space.getDescription();

        // Verify the description contains expected information
        assertTrue(description.contains("Space: Living Room"));
        assertTrue(description.contains("Items: Sword"));
        assertTrue(description.contains("Players: Alice"));
        assertTrue(description.contains("Pets: None"));
    }


    @Test
    public void testGetWorld() {
        assertEquals(world, space.getWorld());
    }
}
