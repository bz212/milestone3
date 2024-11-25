package world;

import org.junit.Before;
import org.junit.Test;

import strategy.AIStrategy;
import strategy.RandomMoveStrategy;

import static org.junit.Assert.*;

import java.util.ArrayList;

/**
 * Test class for the AIPlayer class.
 */
public class AIPlayerTest {
    private AIPlayer aiPlayer;
    private Space startingSpace;
    private Item weapon;
    private World world;
    private AIStrategy strategy;

    @Before
    public void setUp() {
        // Initialize world and spaces
        world = new World(new ArrayList<>(), new ArrayList<>(), null, null, null);
        startingSpace = new Space("Garden", world);
        world.getSpaces().add(startingSpace);

        // Initialize strategy (mock or simple implementation)
        strategy = new RandomMoveStrategy();

        // Initialize AI player and item
        aiPlayer = new AIPlayer("AI Bot", 100, startingSpace, strategy);
        weapon = new Item("Sword", 15, "A sharp sword.");
    }

    @Test
    public void testGetName() {
        assertEquals("AI Bot", aiPlayer.getName());
    }

    @Test
    public void testGetHealth() {
        assertEquals(100, aiPlayer.getHealth());
    }

    @Test
    public void testMove() {
        // Create a new space and move the AI player there
        Space newSpace = new Space("Library", world);
        world.getSpaces().add(newSpace);
        aiPlayer.move(newSpace);

        assertEquals(newSpace, aiPlayer.getCurrentSpace());
        assertFalse(startingSpace.getPlayers().contains(aiPlayer));
        assertTrue(newSpace.getPlayers().contains(aiPlayer));
    }

    @Test
    public void testPickUpItem() {
        // Add item to the starting space
        startingSpace.addItem(weapon);
        assertTrue(startingSpace.getItems().contains(weapon));

        // AI player picks up the item
        aiPlayer.pickUpItem(weapon);
        assertFalse(startingSpace.getItems().contains(weapon));
        assertTrue(aiPlayer.getInventory().contains(weapon));
    }

    @Test
    public void testAttemptAttack() {
        // Create another player to attack
        Player target = new HumanPlayer("Charlie", 100, startingSpace);
        startingSpace.addPlayer(target);

        // Add a weapon to AI player's inventory
        aiPlayer.getInventory().addItem(weapon);

        // AI player attacks the target
        aiPlayer.attemptAttack(target);
        
        // Assuming the sword does 15 damage
        assertEquals(85, target.getHealth());
    }

    @Test
    public void testReduceHealth() {
        // Reduce health of the AI player
        aiPlayer.reduceHealth(30);
        assertEquals(70, aiPlayer.getHealth());

        // Reduce health below zero
        aiPlayer.reduceHealth(100);
        assertEquals(0, aiPlayer.getHealth());
    }

    @Test
    public void testExecuteStrategy() {
        // Assuming the strategy makes a decision, ensure no exceptions occur
        aiPlayer.executeStrategy();
    }
}
