package controller;

import org.junit.Before;
import org.junit.Test;
import world.*;
import command.*;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

/**
 * Test class for the Controller class.
 */
public class ControllerTest {
    private World world;
    private Space space1;
    private Space space2;
    private Player player1;
    private Player player2;
    private Controller controller;

    /**
     * Sets up the initial game world, spaces, players, and controller before each test.
     */
    @Before
    public void setUp() {
        // Initialize world and spaces
        world = new World(new ArrayList<>(), new ArrayList<>(), null, null, null);
        space1 = new Space("Living Room", world);
        space2 = new Space("Kitchen", world);
        world.getSpaces().add(space1);
        world.getSpaces().add(space2);

        // Initialize players
        player1 = new HumanPlayer("Alice", 100, space1);
        player2 = new HumanPlayer("Bob", 100, space2);
        space1.addPlayer(player1);
        space2.addPlayer(player2);

        // Initialize controller with players and world
        List<Player> players = new ArrayList<>();
        players.add(player1);
        players.add(player2);
        controller = new Controller(players, world);  // Adding world parameter to the controller
    }

    /**
     * Tests adding a new player to the game.
     */
    @Test
    public void testAddPlayer() {
        // Add a new player to the game
        Player newPlayer = new HumanPlayer("Charlie", 100, space1);
        controller.addPlayer(newPlayer);

        // Verify the player was added
        assertEquals(3, controller.getPlayers().size());
        assertTrue(controller.getPlayers().contains(newPlayer));
    }

    /**
     * Tests moving a player to a new space using the controller.
     */
    @Test
    public void testMovePlayer() {
        // Move player1 to a new space using the controller
        MoveCommand moveCommand = new MoveCommand(player1, space2);
        assertTrue(moveCommand.isValid());  // Ensure command is valid before executing
        controller.executeCommand(moveCommand);

        // Verify player1 has moved to space2
        assertEquals(space2, player1.getCurrentSpace());
        assertFalse(space1.getPlayers().contains(player1));
        assertTrue(space2.getPlayers().contains(player1));
    }

    /**
     * Tests player picking up an item from the current space using the controller.
     */
    @Test
    public void testPlayerPicksUpItem() {
        // Add item to space1
        Item item = new Item("Shield", 15, "A protective shield.");
        space1.addItem(item);

        // Player1 picks up the item using the controller
        PickUpItemCommand pickUpItemCommand = new PickUpItemCommand(player1, item);
        assertTrue(pickUpItemCommand.isValid());  // Ensure command is valid before executing
        controller.executeCommand(pickUpItemCommand);

        // Verify player1 has the item in their inventory
        assertTrue(player1.getInventory().contains(item));
        assertFalse(space1.getItems().contains(item));
    }

    /**
     * Tests player attempting to attack another player using the controller.
     */
    @Test
    public void testPlayerAttemptsAttack() {
        // Player1 attempts to attack Player2
        Item weapon = new Item("Dagger", 10, "A sharp dagger.");
        player1.pickUpItem(weapon);
        AttemptAttackCommand attackCommand = new AttemptAttackCommand(player1, player2, weapon);
        assertTrue(attackCommand.isValid());  // Ensure command is valid before executing
        controller.executeCommand(attackCommand);

        // Verify player2's health is reduced
        assertEquals(90, player2.getHealth());
    }

    /**
     * Tests the look around command to verify the player's ability to observe their surroundings.
     */
    @Test
    public void testLookAroundCommand() {
        // Player1 looks around the current space
        LookAroundCommand lookAroundCommand = new LookAroundCommand(player1, world);
        assertTrue(lookAroundCommand.isValid());  // Ensure command is valid before executing
        controller.executeCommand(lookAroundCommand);

        // Verify the description includes relevant information
        String description = player1.getCurrentSpace().getDescription();
        assertTrue(description.contains("Living Room"));
        assertTrue(description.contains("Alice"));
    }

    /**
     * Tests invalid move attempts by trying to move to a non-adjacent space.
     */
    @Test
    public void testInvalidMovePlayer() {
        // Create a space that is not adjacent
        Space space3 = new Space("Bathroom", world);
        world.getSpaces().add(space3);

        // Attempt to move player1 to a non-adjacent space
        MoveCommand moveCommand = new MoveCommand(player1, space3);
        assertFalse(moveCommand.isValid());  // Ensure command is invalid
    }
}
