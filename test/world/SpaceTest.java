package world;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.List;
import org.junit.Before;
import org.junit.Test;
import strategy.TargetStrategy;

/**
 * Test class for the Space class.
 */
public class SpaceTest {

  private Space space;
  private Space neighbor;
  private Item sword;
  private Item shield;
  private Player player;
  private Player anotherPlayer;
  private Pet pet;
  private World world;

  /**
   * Sets up the test environment by initializing spaces, items, players, pets, and the world.
   */
  @Before
  public void setUp() {
    world = new World(new ArrayList<>(), new ArrayList<>(), null, new ArrayList<>(), null, 10);
    space = new Space("Living Room", 0, 0, world);
    neighbor = new Space("Kitchen", 1, 0, world);

    sword = new Item("Sword", 15, "A sharp blade.");
    shield = new Item("Shield", 10, "A sturdy shield.");

    TargetStrategy dummyStrategy = new TargetStrategy() {
      @Override
      public String decideAction(Pet pet, World world) {
        return "Action performed by dummy strategy.";
      }
      
      @Override
      public String decideAction(Player player, World world) {
        return null;
      }

      @Override
      public String moveTarget(Player player, World world) {
        return null;
      }

      @Override
      public String move(Pet pet, World world) {
        return null;
      }

    };

    player = new HumanPlayer("Alice", 100, space);
    anotherPlayer = new HumanPlayer("Bob", 90, neighbor);
    pet = new Pet("Dog", space, dummyStrategy);

    world.getSpaces().add(space);
    world.getSpaces().add(neighbor);
  }

  /**
   * Tests adding and removing items from a space.
   */
  @Test
  public void testAddAndRemoveItem() {
    space.addItem(sword);
    space.addItem(shield);

    List<Item> items = space.getItems();
    assertTrue(items.contains(sword));
    assertTrue(items.contains(shield));

    space.removeItem(sword);
    assertFalse(space.getItems().contains(sword));
  }

  /**
   * Tests adding and removing players from a space.
   */
  @Test
  public void testAddAndRemovePlayer() {
    space.addPlayer(player);
    space.addPlayer(anotherPlayer);

    List<Player> players = space.getPlayers();
    assertTrue(players.contains(player));
    assertTrue(players.contains(anotherPlayer));

    space.removePlayer(player);
    assertFalse(space.getPlayers().contains(player));
  }

  /**
   * Tests adding a neighboring space.
   */
  @Test
  public void testAddNeighbor() {
    space.addNeighbor(neighbor);
    List<Space> neighbors = space.getNeighbors();
    assertTrue(neighbors.contains(neighbor));
    assertTrue(neighbor.getNeighbors().contains(space));
  }

  /**
   * Tests generating the description of a space.
   */
  @Test
  public void testGetDescription() {
    space.addItem(sword);
    space.addPlayer(player);
    space.addNeighbor(neighbor);

    String description = space.getDescription();
    assertTrue(description.contains("Living Room"));
    assertTrue(description.contains("Sword"));
    assertTrue(description.contains("Alice"));
    assertTrue(description.contains("Kitchen"));
  }

  /**
   * Tests adding a null neighbor, expecting an exception.
   */
  @Test(expected = IllegalArgumentException.class)
  public void testAddNullNeighbor() {
    space.addNeighbor(null);
  }

  /**
   * Tests adding a pet to a space.
   */
  @Test
  public void testAddPet() {
    space.addPlayer(player);
    space.addPet(pet);
    assertTrue(space.getPets().contains(pet));
  }

  /**
   * Tests the equals and hashCode methods for spaces.
   */
  @Test
  public void testEqualsAndHashCode() {
    Space duplicateSpace = new Space("Living Room", 0, 0, world);
    assertTrue(space.equals(duplicateSpace));
    assertEquals(space.hashCode(), duplicateSpace.hashCode());

    Space differentSpace = new Space("Garage", 2, 0, world);
    assertFalse(space.equals(differentSpace));
  }
}
