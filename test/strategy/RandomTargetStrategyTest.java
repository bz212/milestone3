package strategy;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;
import org.junit.Before;
import org.junit.Test;
import world.Player;
import world.Space;
import world.World;

/**
 * Test class for RandomTargetStrategy.
 */
public class RandomTargetStrategyTest {

  private RandomTargetStrategy strategy;
  private Player mockTarget;
  private Space mockSpace;
  private World mockWorld;
  private List<Space> neighbors;

  /**
   * Sets up the test environment before each test.
   */
  @Before
  public void setUp() {
    strategy = new RandomTargetStrategy();

    mockTarget = mock(Player.class);
    mockSpace = mock(Space.class);
    mockWorld = mock(World.class);

    // Set up neighboring spaces
    neighbors = new ArrayList<>();
    Space neighbor1 = mock(Space.class);
    Space neighbor2 = mock(Space.class);
    when(neighbor1.getName()).thenReturn("Neighbor 1");
    when(neighbor2.getName()).thenReturn("Neighbor 2");
    neighbors.add(neighbor1);
    neighbors.add(neighbor2);

    when(mockSpace.getNeighbors()).thenReturn(neighbors);
    when(mockTarget.getCurrentSpace()).thenReturn(mockSpace);
  }

  /**
   * Tests that the target moves to a random neighboring space.
   */
  @Test
  public void testTargetMovesToRandomSpace() {
    String result = strategy.moveTarget(mockTarget, mockWorld);

    verify(mockSpace, times(1)).getNeighbors();
    verify(mockTarget, times(1)).setCurrentSpace(any(Space.class));
    assertNotNull(result);
    assertTrue(result.contains("Target"));
    assertTrue(result.contains("moved to"));
  }

  /**
   * Tests the behavior when the target has no neighboring spaces.
   */
  @Test
  public void testNoNeighbors() {
    when(mockSpace.getNeighbors()).thenReturn(new ArrayList<>());

    String result = strategy.moveTarget(mockTarget, mockWorld);
    verify(mockTarget, never()).setCurrentSpace(any(Space.class));
    assertEquals("Error: Target has no neighboring spaces to move to.", result);
  }

  /**
   * Tests the behavior when the target is null.
   */
  @Test
  public void testNullTarget() {
    String result = strategy.moveTarget(null, mockWorld);
    assertEquals("Error: Target is null.", result);
  }

  /**
   * Tests the behavior when the target's current space is null.
   */
  @Test
  public void testNullSpace() {
    when(mockTarget.getCurrentSpace()).thenReturn(null);

    String result = strategy.moveTarget(mockTarget, mockWorld);
    verify(mockSpace, never()).getNeighbors();
    verify(mockTarget, never()).setCurrentSpace(any(Space.class));
    assertEquals("Error: Target has no neighboring spaces to move to.", result);
  }

  /**
   * Tests logging messages during strategy actions.
   */
  @Test
  public void testLoggingMessages() {
    strategy.moveTarget(mockTarget, mockWorld);

    // Ensure no exceptions occur; log messages can be checked manually if needed
  }
}
