package world;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

import java.util.ArrayList;

/**
 * Test class for the HumanPlayer class.
 */
public class HumanPlayerTest {
    private HumanPlayer player;
    private Space startingSpace;
    private Item item;
    private World world;

    @Before
    public void setUp() {
        // Initialize world and spaces
        world = new World(new ArrayList<>(), new ArrayList<>(), null, null, null);
        startingSpace = new Space("Living Room", world);
        world.getSpaces().add(startingSpace);

        // Initialize player and item
        player = new HumanPlayer("Alice", 100, startingSpace);
        item = new Item("Sword", 10, "A sharp blade.");

        // Add item to the player's inventory for testing attack
        player.getInventory().addItem(item);
    }

    @Test
    public void testGetName() {
        assertEquals("Alice", player.getName());
    }

    @Test
    public void testGetHealth() {
        assertEquals(100, player.getHealth());
    }

    @Test
    public void testMove() {
        // Create a new space and move the player there
        Space newSpace = new Space("Kitchen", world);
        world.getSpaces().add(newSpace);
        player.move(newSpace);
        
        assertEquals(newSpace, player.getCurrentSpace());
        assertFalse(startingSpace.getPlayers().contains(player));
        assertTrue(newSpace.getPlayers().contains(player));
    }

    @Test
    public void testPickUpItem() {
        // Add item to the starting space
        startingSpace.addItem(item);
        assertTrue(startingSpace.getItems().contains(item));

        // Player picks up the item
        player.pickUpItem(item);
        assertFalse(startingSpace.getItems().contains(item));
        assertTrue(player.getInventory().getItems().contains(item)); // Ensure item is in player's inventory
    }

    @Test
    public void testAttemptAttack() {
        // Create another player to attack
        Player target = new HumanPlayer("Bob", 100, startingSpace);
        startingSpace.addPlayer(target);

        // Player attacks the target
        player.attemptAttack(target);

        // Check if the target's health has decreased by the damage of the weapon
        assertEquals(90, target.getHealth());
    }

    @Test
    public void testReduceHealth() {
        // Reduce health of the player
        player.reduceHealth(20);
        assertEquals(80, player.getHealth());

        // Reduce health below zero
        player.reduceHealth(100);
        assertEquals(0, player.getHealth());
    }

    @Test
    public void testGetDetailedInfo() {
        String info = player.getDetailedInfo();
        assertTrue(info.contains("Alice"));
        assertTrue(info.contains("Health: 100"));
        assertTrue(info.contains("Living Room"));
    }
}
