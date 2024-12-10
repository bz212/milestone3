package world;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import org.junit.Before;
import org.junit.Test;
import strategy.RandomMoveStrategy;

/**
 * Unit tests for the AiPlayer class to ensure proper behavior.
 */
public class AiPlayerTest {

  private AiPlayer aiPlayer;
  private Space startingSpace;
  private Item testItem;
  private World world;

  /**
   * Sets up the game world, AI player, and test items before each test.
   */
  @Before
  public void setUp() {
    world = new World(new ArrayList<>(), new ArrayList<>(), null, null, 10);
    startingSpace = new Space("Garden", 0, 0, world);
    world.getSpaces().add(startingSpace);

    aiPlayer = new AiPlayer("AIPlayer1", 100, startingSpace, new RandomMoveStrategy(world));
    testItem = new Item("Basketball", 5, "A standard basketball.");
  }

  /**
   * Tests that the AI player returns the correct name.
   */
  @Test
  public void testGetName() {
    assertEquals("AIPlayer1", aiPlayer.getName());
  }

  /**
   * Tests that the AI player returns the correct health value.
   */
  @Test
  public void testGetHealth() {
    assertEquals(100, aiPlayer.getHealth());
  }

  /**
   * Tests setting the health value of the AI player.
   */
  @Test
  public void testSetHealth() {
    aiPlayer.setHealth(50);
    assertEquals(50, aiPlayer.getHealth());

    aiPlayer.setHealth(0);
    assertEquals(0, aiPlayer.getHealth());
  }

  /**
   * Tests that setting health below zero throws an exception.
   */
  @Test(expected = IllegalArgumentException.class)
  public void testSetHealthBelowZero() {
    aiPlayer.setHealth(-10);
  }

  /**
   * Tests that setting health above the maximum limit throws an exception.
   */
  @Test(expected = IllegalArgumentException.class)
  public void testSetHealthAboveMax() {
    aiPlayer.setHealth(200);
  }

  /**
   * Tests reducing health of the AI player.
   */
  @Test
  public void testReduceHealth() {
    aiPlayer.reduceHealth(30);
    assertEquals(70, aiPlayer.getHealth());
  }

  /**
   * Tests reducing health with a negative value, expecting an exception.
   */
  @Test(expected = IllegalArgumentException.class)
  public void testReduceHealthNegative() {
    aiPlayer.reduceHealth(-10);
  }

  /**
   * Tests reducing health beyond current health, ensuring it does not go below zero.
   */
  @Test
  public void testReduceHealthExceedingHealth() {
    aiPlayer.reduceHealth(200);
    assertEquals(0, aiPlayer.getHealth());
  }

  /**
   * Tests moving the AI player to a new space.
   */
  @Test
  public void testMove() {
    Space newSpace = new Space("Library", 1, 1, world);
    world.getSpaces().add(newSpace);

    aiPlayer.move(newSpace);

    assertEquals(newSpace, aiPlayer.getCurrentSpace());
    assertFalse(startingSpace.getPlayers().contains(aiPlayer));
    assertTrue(newSpace.getPlayers().contains(aiPlayer));
  }

  /**
   * Tests the AI player picking up an item.
   */
  @Test
  public void testPickUpItem() {
    startingSpace.addItem(testItem);
    assertTrue(startingSpace.getItems().contains(testItem));

    aiPlayer.pickUpItem(testItem);

    assertFalse(startingSpace.getItems().contains(testItem));
    assertTrue(aiPlayer.getInventory().contains(testItem));
  }

  /**
   * Tests picking up an item that exceeds the inventory capacity.
   */
  @Test
  public void testPickUpItemExceedingCapacity() {
    for (int i = 0; i < 10; i++) {
      aiPlayer.getInventory().addItem(new Item("Item" + i, 1, "Test item."));
    }

    Item extraItem = new Item("Extra Item", 5, "An extra item.");
    boolean added = aiPlayer.getInventory().addItem(extraItem);
    assertFalse(added);
  }

  /**
   * Tests attacking a target player without a weapon.
   */
  @Test
  public void testAttemptAttack() {
    Player target = new HumanPlayer("Target", 100, startingSpace);
    startingSpace.addPlayer(target);

    aiPlayer.attemptAttack(target);

    assertEquals(90, target.getHealth());
  }

  /**
   * Tests attacking a target player with a weapon.
   */
  @Test
  public void testAttemptAttackWithWeapon() {
    Item weapon = new Item("Sword", 15, "A sharp blade.");
    aiPlayer.getInventory().addItem(weapon);

    Player target = new HumanPlayer("Target", 100, startingSpace);
    startingSpace.addPlayer(target);

    aiPlayer.attemptAttack(target);

    assertEquals(85, target.getHealth());
  }

  /**
   * Tests executing the AI player's strategy.
   */
  @Test
  public void testExecuteStrategy() {
    aiPlayer.executeStrategy();
    assertNotNull("AI should execute a valid strategy and remain in a valid space",
        aiPlayer.getCurrentSpace());
  }

  /**
   * Tests the visibility of a target player.
   */
  @Test
  public void testVisibility() {
    Player target = new HumanPlayer("Target", 100, startingSpace);

    assertFalse(aiPlayer.canSee(target));

    aiPlayer.setCanSee(target, true);
    assertTrue(aiPlayer.canSee(target));
  }
}
