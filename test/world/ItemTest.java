package world;

import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;

/**
 * Test class for the Item class.
 */
public class ItemTest {

  private Item sword;
  private Item shield;
  private Item potion;
  private Player mockPlayer;

  /**
   * Sets up the test environment by initializing items and a mock player.
   */
  @Before
  public void setUp() {
    sword = new Item("Sword", 15, "A sharp blade.");
    shield = new Item("Shield", 10, "A sturdy shield.");
    potion = new Item("Potion", 0, "A healing potion.");
    mockPlayer = new HumanPlayer("MockPlayer", 100, null);
  }

  /**
   * Tests the getName method for all items.
   */
  @Test
  public void testGetName() {
    assertEquals("Sword", sword.getName());
    assertEquals("Shield", shield.getName());
    assertEquals("Potion", potion.getName());
  }

  /**
   * Tests the getDamage method for all items.
   */
  @Test
  public void testGetDamage() {
    assertEquals(15, sword.getDamage());
    assertEquals(10, shield.getDamage());
    assertEquals(0, potion.getDamage());
  }

  /**
   * Tests the getDescription method for all items.
   */
  @Test
  public void testGetDescription() {
    assertEquals("A sharp blade.", sword.getDescription());
    assertEquals("A sturdy shield.", shield.getDescription());
    assertEquals("A healing potion.", potion.getDescription());
  }

  /**
   * Tests using items on a player.
   */
  @Test
  public void testUse() {
    sword.use(mockPlayer);
    assertEquals(85, mockPlayer.getHealth());

    shield.use(mockPlayer);
    assertEquals(75, mockPlayer.getHealth());

    potion.use(mockPlayer);
    assertEquals(75, mockPlayer.getHealth());
  }

  /**
   * Tests using an item with a null target, expecting an exception.
   */
  @Test(expected = IllegalArgumentException.class)
  public void testUseWithNullTarget() {
    sword.use(null);
  }

  /**
   * Tests the toString method for all items.
   */
  @Test
  public void testToString() {
    assertEquals("Item{name='Sword', damage=15, description='A sharp blade.'}", sword.toString());
    assertEquals("Item{name='Shield', damage=10, description='A sturdy shield.'}", 
        shield.toString());
    assertEquals("Item{name='Potion', damage=0, description='A healing potion.'}", 
        potion.toString());
  }

  /**
   * Tests the constructor with a null name, expecting an exception.
   */
  @Test(expected = IllegalArgumentException.class)
  public void testConstructorWithNullName() {
    new Item(null, 10, "Invalid item");
  }

  /**
   * Tests the constructor with an empty name, expecting an exception.
   */
  @Test(expected = IllegalArgumentException.class)
  public void testConstructorWithEmptyName() {
    new Item("", 10, "Invalid item");
  }

  /**
   * Tests the constructor with negative damage, expecting an exception.
   */
  @Test(expected = IllegalArgumentException.class)
  public void testConstructorWithNegativeDamage() {
    new Item("Invalid", -5, "Invalid item");
  }

  /**
   * Tests the constructor with a null description, expecting an exception.
   */
  @Test(expected = IllegalArgumentException.class)
  public void testConstructorWithNullDescription() {
    new Item("Invalid", 10, null);
  }

  /**
   * Tests the constructor with an empty description, expecting an exception.
   */
  @Test(expected = IllegalArgumentException.class)
  public void testConstructorWithEmptyDescription() {
    new Item("Invalid", 10, "");
  }

  /**
   * Tests creating an item and retrieving its attributes.
   */
  @Test
  public void testCreateItem() {
    Item sword = new Item("Sword", 10, "A sharp blade");
    assertEquals("Sword", sword.getName());
    assertEquals(10, sword.getDamage());
    assertEquals("A sharp blade", sword.getDescription());
  }
}
