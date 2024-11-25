package world;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Test class for the Item class.
 */
public class ItemTest {
    private Item item;

    @Before
    public void setUp() {
        // Initialize item
        item = new Item("Sword", 10, "A sharp blade.");
    }

    @Test
    public void testGetName() {
        assertEquals("Sword", item.getName());
    }

    @Test
    public void testGetDamage() {
        assertEquals(10, item.getDamage());
    }

    @Test
    public void testGetDescription() {
        assertEquals("A sharp blade.", item.getDescription());
    }

    @Test
    public void testUse() {
        // Create a player and reduce their health using the item
        Player player = new HumanPlayer("Alice", 100, new Space("Living Room", null));
        item.use(player);
        assertEquals(90, player.getHealth());
    }
}
