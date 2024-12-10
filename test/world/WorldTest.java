package world;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.Arrays;
import org.junit.Before;
import org.junit.Test;
import strategy.RandomMoveStrategy;
import strategy.RandomTargetStrategy;
import strategy.TargetStrategy;

/**
 * Test class for the World class.
 */
public class WorldTest {

  private World world;
  private Space space1;
  private Space space2;
  private Item sword;
  private Item shield;
  private Player player1;
  private Player player2;
  private Pet pet;

  /**
   * Sets up the test environment by initializing the world, spaces, items, players, and pets.
   */
  @Before
  public void setUp() {
    // Step 1: Initialize spaces
    space1 = new Space("Living Room", 0, 0, null);
    space2 = new Space("Kitchen", 1, 0, null);

    // Step 2: Initialize items
    sword = new Item("Sword", 15, "A sharp blade.");
    shield = new Item("Shield", 10, "A sturdy shield.");
    space1.addItem(sword);
    space2.addItem(shield);

    // Step 3: Initialize players
    player1 = new HumanPlayer("Alice", 100, space1);
    player2 = new HumanPlayer("Bob", 90, space2);

    // Step 4: Initialize pet
    pet = new Pet("Dog", space1, new RandomTargetStrategy());

    // Step 5: Initialize world
    world = new World(
        new ArrayList<>(Arrays.asList(space1, space2)),
        new ArrayList<>(Arrays.asList(sword, shield)),
        player1,
        new ArrayList<>(Arrays.asList(player1, player2)),
        new RandomTargetStrategy(),
        20
    );

    // Step 6: Set world references
    space1.setWorld(world);
    space2.setWorld(world);

    // Step 7: Add objects to world
    world.addSpace(space1);
    world.addSpace(space2);
    world.addPlayer(player1);
    world.addPlayer(player2);
  }

  /**
   * Tests initializing the world.
   */
  @Test
  public void testInitializeWorld() {
    assertNotNull(world.getSpaces());
    assertNotNull(world.getPlayers());
    assertNotNull(world.getTarget());
    assertEquals(2, world.getSpaces().size());
    assertEquals(2, world.getPlayers().size());
  }

  /**
   * Tests moving a player to a valid space.
   */
  @Test
  public void testMovePlayer() {
    boolean moved = world.movePlayer("Kitchen");
    assertTrue(moved);
    assertEquals(space2, player1.getCurrentSpace());
  }

  /**
   * Tests attempting to move a player to an invalid space.
   */
  @Test
  public void testMovePlayerInvalidSpace() {
    boolean moved = world.movePlayer("Garage");
    assertFalse(moved);
  }

  /**
   * Tests picking up an item from a space.
   */
  @Test
  public void testPickUpItem() {
    space1.addItem(sword);
    boolean pickedUp = world.pickUpItem("Sword");
    assertTrue(pickedUp);
    assertTrue(player1.getInventory().contains(sword));
    assertFalse(space1.getItems().contains(sword));
  }

  /**
   * Tests attempting to pick up an item that does not exist.
   */
  @Test
  public void testPickUpItemNotFound() {
    boolean pickedUp = world.pickUpItem("Bow");
    assertFalse(pickedUp);
  }

  /**
   * Tests attacking another player.
   */
  @Test
  public void testAttackPlayer() {
    String result = world.attackPlayer("Bob");
    assertEquals("Bob attacked successfully!", result);
    assertEquals(80, player2.getHealth());
  }

  /**
   * Tests attempting to attack a player that does not exist.
   */
  @Test
  public void testAttackPlayerNotFound() {
    String result = world.attackPlayer("Charlie");
    assertEquals("Attack failed: Player not found.", result);
  }

  /**
   * Tests checking if the game is over.
   */
  @Test
  public void testIsGameOver() {
    player1.reduceHealth(100);
    player2.reduceHealth(90);
    assertTrue(world.isGameOver());
  }

  /**
   * Tests checking if the game is not over.
   */
  @Test
  public void testIsGameOverNotOver() {
    assertFalse(world.isGameOver());
  }

  /**
   * Tests looking around the current space and its surroundings.
   */
  @Test
  public void testLookAround() {
    space1.addItem(sword);
    space2.addItem(shield);
    String worldState = world.lookAround();
    assertTrue(worldState.contains("Living Room"));
    assertTrue(worldState.contains("Sword"));
    assertTrue(worldState.contains("Kitchen"));
    assertTrue(worldState.contains("Shield"));
  }

  /**
   * Tests getting the name of a space at specific coordinates.
   */
  @Test
  public void testGetSpaceNameAt() {
    assertEquals("Living Room", world.getSpaceNameAt(0, 0));
    assertEquals("Kitchen", world.getSpaceNameAt(1, 0));
  }

  /**
   * Tests getting the name of a space at invalid coordinates.
   */
  @Test
  public void testGetSpaceNameAtInvalidCoordinates() {
    assertNull(world.getSpaceNameAt(2, 2));
  }

  /**
   * Tests getting the status of all players in the world.
   */
  @Test
  public void testGetPlayerStatus() {
    String status = world.getPlayerStatus();
    assertTrue(status.contains("Alice"));
    assertTrue(status.contains("Health: 100"));
  }

  /**
   * Tests setting and getting the strategy for the world.
   */
  @Test
  public void testSetAndGetStrategy() {
    TargetStrategy newStrategy = new RandomMoveStrategy();
    world.setStrategy(newStrategy);
    assertEquals(newStrategy, world.getStrategy());
  }
}
