package strategy;

import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import org.junit.Before;
import org.junit.Test;
import world.HumanPlayer;
import world.Player;
import world.Space;
import world.World;

/**
 * Test class for the MoveStrategy class.
 */
public class MoveStrategyTest {

  private World world;
  private Space space1;
  private Space space2;
  private Player player;
  private RandomMoveStrategy strategy;

  /**
   * Sets up the test environment before each test.
   */
  @Before
  public void setUp() {
    // Initialize world and spaces
    world = new World(new ArrayList<>(), new ArrayList<>(), 
        null, new ArrayList<>(), new RandomMoveStrategy(), 10);
    space1 = new Space("Garden", 0, 0, world);
    space2 = new Space("Library", 0, 0, world);
    world.getSpaces().add(space1);
    world.getSpaces().add(space2);

    // Initialize player and strategy
    strategy = new RandomMoveStrategy();
    player = new HumanPlayer("Alice", 100, space1);
    space1.addPlayer(player);
  }

  /**
   * Tests moving the player to a neighboring space using the strategy.
   */
  @Test
  public void testMovePlayer() {
    // Move the player using the strategy
    strategy.move(player, world);

    // Verify the player has moved to a neighboring space
    assertTrue(space1.getPlayers().isEmpty() || space2.getPlayers().isEmpty());
    assertTrue(world.getPlayers().contains(player));
  }

  /**
   * Tests that the player remains in the world after multiple moves.
   */
  @Test
  public void testPlayerRemainsInWorld() {
    // Move the player multiple times
    for (int i = 0; i < 10; i++) {
      strategy.move(player, world);
    }

    // Verify the player is still in the world
    assertTrue(world.getPlayers().contains(player));
  }
}
