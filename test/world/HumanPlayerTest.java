package world;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import org.junit.Before;
import org.junit.Test;

/**
 * Unit tests for the HumanPlayer class to ensure proper behavior.
 */
public class HumanPlayerTest {

  private HumanPlayer humanPlayer;
  private Space startingSpace;
  private Item testItem;
  private World world;

  /**
   * Sets up the game world, starting space, and test items before each test.
   */
  @Before
  public void setUp() {
    world = new World(new ArrayList<>(), new ArrayList<>(), null, new ArrayList<>(), null, 10);
    startingSpace = new Space("Living Room", 0, 0, world);
    world.getSpaces().add(startingSpace);

    humanPlayer = new HumanPlayer("HumanPlayer1", 100, startingSpace);
    testItem = new Item("Laptop", 5, "A high-end gaming laptop.");
  }

  /**
   * Tests if the player name is correctly retrieved.
   */
  @Test
  public void testGetName() {
    assertEquals("HumanPlayer1", humanPlayer.getName());
  }

  /**
   * Tests if the player's health is correctly retrieved.
   */
  @Test
  public void testGetHealth() {
    assertEquals(100, humanPlayer.getHealth());
  }

  /**
   * Tests reducing the player's health.
   */
  @Test
  public void testTakeDamage() {
    humanPlayer.reduceHealth(30);
    assertEquals(70, humanPlayer.getHealth());
  }

  /**
   * Tests reducing health with a negative value, which should have no effect.
   */
  @Test
  public void testTakeDamageNegative() {
    humanPlayer.reduceHealth(-10);
    assertEquals(100, humanPlayer.getHealth());
  }

  /**
   * Tests reducing health exceeding the player's current health.
   */
  @Test
  public void testTakeDamageExceedingHealth() {
    humanPlayer.reduceHealth(200);
    assertEquals(0, humanPlayer.getHealth());
  }

  /**
   * Tests moving the player to a new space.
   */
  @Test
  public void testMove() {
    Space newSpace = new Space("Kitchen", 1, 1, world);
    world.getSpaces().add(newSpace);

    humanPlayer.move(newSpace);

    assertEquals(newSpace, humanPlayer.getCurrentSpace());
    assertFalse(startingSpace.getPlayers().contains(humanPlayer));
    assertTrue(newSpace.getPlayers().contains(humanPlayer));
  }

  /**
   * Tests picking up an item.
   */
  @Test
  public void testPickUpItem() {
    startingSpace.addItem(testItem);
    assertTrue(startingSpace.getItems().contains(testItem));

    humanPlayer.pickUpItem(testItem);

    assertFalse(startingSpace.getItems().contains(testItem));
    assertTrue(humanPlayer.getInventory().getItems().contains(testItem));
  }

  /**
   * Tests picking up an item that exceeds the inventory capacity.
   */
  @Test
  public void testPickUpItemExceedingCapacity() {
    for (int i = 0; i < 10; i++) {
      humanPlayer.getInventory().addItem(new Item("Item" + i, 1, "Test item."));
    }
    Item extraItem = new Item("Extra Item", 5, "An extra item.");
    boolean added = humanPlayer.getInventory().addItem(extraItem);

    assertFalse(added);
  }

  /**
   * Tests the player's attempt to attack another player.
   */
  @Test
  public void testAttemptAttack() {
    Player target = new HumanPlayer("Target", 100, startingSpace);
    startingSpace.addPlayer(target);

    humanPlayer.attemptAttack(target);

    assertEquals(95, target.getHealth());
  }

  /**
   * Tests attacking a target player with a weapon.
   */
  @Test
  public void testAttemptAttackWithWeapon() {
    Item weapon = new Item("Sword", 15, "A sharp blade.");
    humanPlayer.getInventory().addItem(weapon);

    Player target = new HumanPlayer("Target", 100, startingSpace);
    startingSpace.addPlayer(target);

    humanPlayer.attemptAttack(target);

    assertEquals(85, target.getHealth());
  }

  /**
   * Tests retrieving the player's detailed information.
   */
  @Test
  public void testGetDetailedInfo() {
    String info = humanPlayer.getDetailedInfo();

    assertTrue(info.contains("HumanPlayer1"));
    assertTrue(info.contains("Health: 100"));
    assertTrue(info.contains("Living Room"));
  }

  /**
   * Tests setting and checking visibility of another player.
   */
  @Test
  public void testVisibility() {
    Player target = new HumanPlayer("Target", 100, startingSpace);

    assertFalse(humanPlayer.canSee(target));

    humanPlayer.setCanSee(target, true);
    assertTrue(humanPlayer.canSee(target));
  }
}
