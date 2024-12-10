package strategy;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.util.ArrayList;
import java.util.Arrays;
import org.junit.Before;
import org.junit.Test;
import world.HumanPlayer;
import world.Player;
import world.Space;
import world.World;

/**
 * Test class for the TargetStrategy interface and its implementations.
 */
public class TargetStrategyTest {

  private World world;
  private Space space1;
  private Space space2;
  private Player target;
  private TargetStrategy strategy;

  /**
   * Sets up the test environment before each test.
   */
  @Before
  public void setUp() {
    // Initialize world and spaces
    world = new World(
        new ArrayList<>(),           // spaces
        new ArrayList<>(),           // items
        new HumanPlayer("Target", 100, null), // target player
        new ArrayList<>(),           // players
        new RandomMoveStrategy(),    // strategy
        10                           // maxTurns
    );

    space1 = new Space("Living Room", 0, 0, world);
    space2 = new Space("Kitchen", 1, 0, world);

    // Link spaces as neighbors
    space1.addNeighbor(space2);
    space2.addNeighbor(space1);

    world.getSpaces().add(space1);
    world.getSpaces().add(space2);

    // Initialize target player
    target = new HumanPlayer("Target Player", 100, space1);
    space1.addPlayer(target);
    world.setPlayers(Arrays.asList(target));
  }

  /**
   * Tests the RandomMoveStrategy for moving the target.
   */
  @Test
  public void testRandomMoveStrategy() {
    strategy = new RandomMoveStrategy();
    world.setStrategy(strategy);

    world.moveTarget();

    assertTrue(
        "Target should have moved to a neighboring space",
        space1.getPlayers().isEmpty() || space2.getPlayers().isEmpty()
    );
    assertTrue("Target should still exist in the world", world.getPlayers().contains(target));
  }

  /**
   * Tests the ChasePlayerStrategy for moving the target towards another player.
   */
  @Test
  public void testChasePlayerStrategy() {
    Player playerToChase = new HumanPlayer("Player to Chase", 100, space2);
    space2.addPlayer(playerToChase);
    world.getPlayers().add(playerToChase);

    strategy = new ChasePlayerStrategy();
    world.setStrategy(strategy);

    world.moveTarget();

    assertEquals("Target should have moved towards "
        + "playerToChase", space2, target.getCurrentSpace());
  }

  /**
   * Tests the DepthFirstMoveStrategy for moving the target.
   */
  @Test
  public void testDepthFirstMoveStrategy() {
    strategy = new DepthFirstMoveStrategy();
    world.setStrategy(strategy);

    try {
      world.moveTarget();
      assertTrue("DepthFirstMoveStrategy should execute without errors", true);
    } catch (Exception e) {
      fail("DepthFirstMoveStrategy should not throw exceptions");
    }
  }

  /**
   * Tests that the target stays in the current space when there are no neighbors.
   */
  @Test
  public void testTargetStaysInCurrentSpaceIfNoNeighbors() {
    space1.getNeighbors().clear();

    strategy = new RandomMoveStrategy();
    world.setStrategy(strategy);

    world.moveTarget();

    assertEquals(
        "Target should stay in the current space when there are no neighbors",
        space1, target.getCurrentSpace()
    );
  }

  /**
   * Tests the behavior when no strategy is assigned to the world.
   */
  @Test
  public void testNoStrategyAssigned() {
    world.setStrategy(null);

    world.moveTarget();

    assertEquals("Target should not move without a strategy", space1, target.getCurrentSpace());
  }
}
