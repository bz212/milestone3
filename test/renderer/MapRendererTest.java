package renderer;

import org.junit.Before;
import org.junit.Test;
import world.*;
import renderer.MapRenderer;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

/**
 * Test class for the MapRenderer class.
 */
public class MapRendererTest {
    private World world;
    private Space space1;
    private Space space2;
    private Space space3;
    private Player player1;
    private Player player2;
    private MapRenderer mapRenderer;

    @Before
    public void setUp() {
        // Initialize world and spaces
        world = new World(new ArrayList<>(), new ArrayList<>(), null, null, null);
        space1 = new Space("Living Room", world);
        space2 = new Space("Kitchen", world);
        space3 = new Space("Garden", world);
        world.getSpaces().add(space1);
        world.getSpaces().add(space2);
        world.getSpaces().add(space3);

        // Set neighbors
        space1.addNeighbor(space2);
        space2.addNeighbor(space3);

        // Initialize players
        player1 = new HumanPlayer("Alice", 100, space1);
        player2 = new HumanPlayer("Bob", 100, space2);
        space1.addPlayer(player1);
        space2.addPlayer(player2);

        // Initialize MapRenderer
        mapRenderer = new MapRenderer(world);
    }

    @Test
    public void testRenderMap() {
        // Render the map and verify the output contains details of all spaces and players
        String map = mapRenderer.renderMap();
        assertTrue(map.contains("Living Room"));
        assertTrue(map.contains("Kitchen"));
        assertTrue(map.contains("Garden"));
        assertTrue(map.contains("Alice"));
        assertTrue(map.contains("Bob"));
    }

    @Test
    public void testRenderMapAfterPlayerMove() {
        // Move player1 to a new space
        player1.move(space2);

        // Render the map and verify the updated positions
        String map = mapRenderer.renderMap();
        assertTrue(map.contains("Kitchen"));
        assertTrue(map.contains("Alice"));
        assertFalse(map.contains("Living Room"));
    }

    @Test
    public void testRenderMapWithNoPlayers() {
        // Remove all players from spaces
        space1.removePlayer(player1);
        space2.removePlayer(player2);

        // Render the map and verify that no players are shown
        String map = mapRenderer.renderMap();
        assertFalse(map.contains("Alice"));
        assertFalse(map.contains("Bob"));
    }

    @Test
    public void testRenderMapWithItems() {
        // Add items to spaces
        Item item1 = new Item("Shield", 10, "A protective shield.");
        Item item2 = new Item("Potion", 5, "A healing potion.");
        space1.addItem(item1);
        space2.addItem(item2);

        // Render the map and verify items are included in the output
        String map = mapRenderer.renderMap();
        assertTrue(map.contains("Shield"));
        assertTrue(map.contains("Potion"));
    }
}
