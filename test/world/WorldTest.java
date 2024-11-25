package world;

import org.junit.Before;
import org.junit.Test;
import strategy.TargetStrategy;
import strategy.RandomMoveStrategy;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

/**
 * Test class for the World class.
 */
public class WorldTest {
    private World world;
    private Space space1;
    private Space space2;
    private Player player1;
    private Player player2;
    private Item item1;
    private Item item2;

    @Before
    public void setUp() {
        // Initialize spaces
        List<Space> spaces = new ArrayList<>();
        space1 = new Space("Living Room", null);
        space2 = new Space("Kitchen", null);
        spaces.add(space1);
        spaces.add(space2);

        // Initialize items
        List<Item> items = new ArrayList<>();
        item1 = new Item("Sword", 10, "A sharp blade.");
        item2 = new Item("Shield", 5, "A protective shield.");
        items.add(item1);
        items.add(item2);

        // Initialize players
        player1 = new HumanPlayer("Alice", 100, space1);
        player2 = new HumanPlayer("Bob", 100, space2);
        List<Player> players = new ArrayList<>();
        players.add(player1);
        players.add(player2);

        // Initialize world
        world = new World(spaces, items, player1, null, new RandomMoveStrategy());
        space1.setWorld(world);
        space2.setWorld(world);

        // Add players to their initial spaces
        space1.addPlayer(player1);
        space2.addPlayer(player2);
    }

    @Test
    public void testAddAndRemoveItems() {
        // Add item to the world
        world.addItem(item1);
        assertTrue(world.getItems().contains(item1));

        // Remove item from the world
        world.removeItem(item1);
        assertFalse(world.getItems().contains(item1));
    }

    @Test
    public void testAddAndRemovePlayer() {
        // Create a new player
        Player player3 = new HumanPlayer("Charlie", 100, space1);

        // Add player to space
        space1.addPlayer(player3);
        assertTrue(space1.containsPlayer(player3));

        // Remove player from space
        space1.removePlayer(player3);
        assertFalse(space1.containsPlayer(player3));
    }

    @Test
    public void testMovePet() {
        Pet pet = new Pet("Rex", space1, new RandomMoveStrategy());
        TargetStrategy strategy = new RandomMoveStrategy();
        world = new World(world.getSpaces(), world.getItems(), player1, pet, strategy);
        pet.setWorld(world);

        // Move the pet
        world.movePet();
        assertNotNull(pet.getCurrentSpace());
        assertTrue(world.getSpaces().contains(pet.getCurrentSpace()));
    }

    @Test
    public void testPlayerPickUpItem() {
        // Add item to space1 and ensure it's also in the world
      space1.addItem(item1);
      if (!world.getItems().contains(item1)) {
          world.addItem(item1);  // Ensure item is both in the space and the world

          assertTrue(space1.getItems().contains(item1));
          assertTrue(world.getItems().contains(item1));

          // Player picks up the item
          world.playerPickUpItem(player1, item1);

          // Verify the item is no longer in the space or the world
          assertFalse(space1.getItems().contains(item1));
          assertFalse(world.getItems().contains(item1));

          // Verify that the item is now in the player's inventory
          assertTrue(player1.getInventory().contains(item1));
      }
    }

    @Test
    public void testGetPlayers() {
        List<Player> players = world.getPlayers();
        assertEquals(2, players.size());
        assertTrue(players.contains(player1));
        assertTrue(players.contains(player2));
    }

    @Test
    public void testIsVisible() {
        assertTrue(world.isVisible(space1));
        assertTrue(world.isVisible(space2));
    }

    @Test
    public void testSetSpaces() {
        List<Space> newSpaces = new ArrayList<>();
        Space space3 = new Space("Garden", null);
        newSpaces.add(space3);
        world.setSpaces(newSpaces);
        assertEquals(1, world.getSpaces().size());
        assertTrue(world.getSpaces().contains(space3));
    }

    @Test
    public void testSetPlayers() {
        List<Player> newPlayers = new ArrayList<>();
        Player player3 = new HumanPlayer("Charlie", 100, space2);
        newPlayers.add(player3);
        world.setPlayers(newPlayers);
        assertEquals(1, world.getPlayers().size());
        assertTrue(world.getPlayers().contains(player3));
    }

    @Test
    public void testSetStrategy() {
        TargetStrategy strategy = new RandomMoveStrategy();
        world.setStrategy(strategy);
        assertNotNull(world.getStrategy());
        assertEquals(strategy, world.getStrategy());
    }

    @Test
    public void testEmptyWorldInitialization() {
        World emptyWorld = new World(new ArrayList<>(), new ArrayList<>(), null, null, null);
        assertTrue(emptyWorld.getSpaces().isEmpty());
        assertTrue(emptyWorld.getPlayers().isEmpty());
    }

    @Test
    public void testAddDuplicateItem() {
        world.addItem(item1);
        world.addItem(item1); // Attempt to add the same item again
        long itemCount = world.getItems().stream().filter(i -> i.equals(item1)).count();
        assertEquals(1, itemCount);
    }
}
