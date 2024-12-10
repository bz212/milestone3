package world;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.Test;

/**
 * Test class for the PlayerInventory class.
 */
public class PlayerInventoryTest {

  private PlayerInventory inventory;
  private Item sword;
  private Item shield;
  private Item potion;

  /**
   * Sets up the test environment by initializing the inventory and test items.
   */
  @Before
  public void setUp() {
    inventory = new PlayerInventory(10);
    sword = new Item("Sword", 15, "A sharp blade.");
    shield = new Item("Shield", 10, "A sturdy shield.");
    potion = new Item("Potion", 5, "A healing potion.");
  }

  /**
   * Tests adding an item successfully to the inventory.
   */
  @Test
  public void testAddItemSuccess() {
    assertTrue(inventory.addItem(sword));
    assertTrue(inventory.getItems().contains(sword));
  }

  /**
   * Tests failing to add an item when the inventory is full.
   */
  @Test
  public void testAddItemFailureWhenFull() {
    for (int i = 0; i < 10; i++) {
      inventory.addItem(new Item("Item " + i, 1, "Test item"));
    }
    assertFalse(inventory.addItem(sword));
  }

  /**
   * Tests removing an item successfully from the inventory.
   */
  @Test
  public void testRemoveItemSuccess() {
    inventory.addItem(sword);
    assertTrue(inventory.removeItem(sword));
    assertFalse(inventory.getItems().contains(sword));
  }

  /**
   * Tests failing to remove an item that does not exist in the inventory.
   */
  @Test
  public void testRemoveItemFailure() {
    assertFalse(inventory.removeItem(sword));
  }

  /**
   * Tests checking if the inventory contains a specific item.
   */
  @Test
  public void testContainsItem() {
    inventory.addItem(sword);
    assertTrue(inventory.containsItem(sword));
    assertFalse(inventory.containsItem(shield));
  }

  /**
   * Tests getting the best item from the inventory based on damage value.
   */
  @Test
  public void testGetBestItem() {
    inventory.addItem(sword);
    inventory.addItem(shield);
    inventory.addItem(potion);
    assertEquals(sword, inventory.getBestItem());
  }

  /**
   * Tests getting the best item from an empty inventory.
   */
  @Test
  public void testGetBestItemWhenEmpty() {
    assertNull(inventory.getBestItem());
  }

  /**
   * Tests checking if the inventory is full.
   */
  @Test
  public void testIsFull() {
    assertFalse(inventory.isFull());
    for (int i = 0; i < 10; i++) {
      inventory.addItem(new Item("Item " + i, 1, "Test item"));
    }
    assertTrue(inventory.isFull());
  }

  /**
   * Tests clearing all items from the inventory.
   */
  @Test
  public void testClearInventory() {
    inventory.addItem(sword);
    inventory.addItem(shield);
    inventory.clearInventory();
    assertTrue(inventory.getItems().isEmpty());
  }

  /**
   * Tests the toString method when the inventory is empty.
   */
  @Test
  public void testToStringWhenEmpty() {
    assertEquals("Inventory is empty.", inventory.toString());
  }

  /**
   * Tests the toString method when the inventory contains items.
   */
  @Test
  public void testToStringWithItems() {
    inventory.addItem(sword);
    inventory.addItem(shield);
    String expected = "Inventory: Sword, Shield";
    assertEquals(expected, inventory.toString());
  }

  /**
   * Tests adding a null item to the inventory, expecting an exception.
   */
  @Test(expected = IllegalArgumentException.class)
  public void testAddNullItem() {
    inventory.addItem(null);
  }

  /**
   * Tests removing a null item from the inventory, expecting an exception.
   */
  @Test(expected = IllegalArgumentException.class)
  public void testRemoveNullItem() {
    inventory.removeItem(null);
  }
}
