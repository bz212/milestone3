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
import world.Pet;
import world.Space;
import world.World;

/**
 * Test class for DepthFirstMoveStrategy.
 */
public class DepthFirstMoveStrategyTest {

  private DepthFirstMoveStrategy strategy;
  private Pet mockPet;
  private Space mockCurrentSpace;
  private Space mockNeighborSpace;
  private World mockWorld;

  /**
   * Sets up the test environment before each test.
   */
  @Before
  public void setUp() {
    strategy = new DepthFirstMoveStrategy();
    mockPet = mock(Pet.class);
    mockCurrentSpace = mock(Space.class);
    mockNeighborSpace = mock(Space.class);
    mockWorld = mock(World.class);

    // Set up mock neighbors
    List<Space> neighbors = new ArrayList<>();
    neighbors.add(mockNeighborSpace);
    when(mockCurrentSpace.getNeighbors()).thenReturn(neighbors);

    // Set up initial pet space
    when(mockPet.getCurrentSpace()).thenReturn(mockCurrentSpace);
    when(mockNeighborSpace.getName()).thenReturn("Neighbor Space");
  }

  /**
   * Tests moving the pet to the next space.
   */
  @Test
  public void testMoveToNextSpace() {
    String result = strategy.decideAction(mockPet, mockWorld);

    verify(mockCurrentSpace, times(1)).getNeighbors();
    verify(mockPet, times(1)).move(mockNeighborSpace);
    assertNotNull(result);
    assertTrue(result.contains("moved to Neighbor Space"));
  }

  /**
   * Tests the behavior of backtracking when all neighbors are visited.
   */
  @Test
  public void testBacktrackBehavior() {
    // Simulate all neighbors being visited
    when(mockCurrentSpace.getNeighbors()).thenReturn(new ArrayList<>());

    String result = strategy.decideAction(mockPet, mockWorld);
    verify(mockPet, never()).move(any(Space.class));
    assertTrue(result.contains("backtracked"));
  }

  /**
   * Tests the scenario where there are no spaces left to explore.
   */
  @Test
  public void testNoSpacesToExplore() {
    when(mockCurrentSpace.getNeighbors()).thenReturn(new ArrayList<>());
    when(mockPet.getCurrentSpace()).thenReturn(null);

    String result = strategy.decideAction(mockPet, mockWorld);
    verify(mockPet, never()).move(any(Space.class));
    assertTrue(result.contains("has no more spaces to explore"));
  }

  /**
   * Tests the moveTarget method which is not supported by this strategy.
   */
  @Test
  public void testMoveTargetNotSupported() {
    String result = strategy.moveTarget(null, mockWorld);
    assertEquals("DepthFirstMoveStrategy does not support player movement.", result);
  }

  /**
   * Tests logging messages during strategy actions.
   */
  @Test
  public void testLoggingMessages() {
    strategy.decideAction(mockPet, mockWorld);

    // Ensure no exceptions occur; log messages can be checked manually if needed
  }
}
