package strategy;

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
 * Test class for RandomPetMoveStrategy.
 */
public class RandomPetMoveStrategyTest {

  private RandomPetMoveStrategy strategy;
  private Pet mockPet;
  private Space mockSpace;
  private World mockWorld;
  private List<Space> neighbors;

  /**
   * Sets up the test environment before each test.
   */
  @Before
  public void setUp() {
    strategy = new RandomPetMoveStrategy();

    mockPet = mock(Pet.class);
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
    when(mockPet.getCurrentSpace()).thenReturn(mockSpace);
  }

  /**
   * Tests that the pet moves to a random neighboring space.
   */
  @Test
  public void testPetMovesToRandomSpace() {
    strategy.move(mockPet, mockWorld);

    verify(mockSpace, times(1)).getNeighbors();
    verify(mockPet, times(1)).move(any(Space.class));
  }

  /**
   * Tests that no movement occurs when there are no neighboring spaces.
   */
  @Test
  public void testNoNeighbors() {
    when(mockSpace.getNeighbors()).thenReturn(new ArrayList<>());

    strategy.move(mockPet, mockWorld);
    verify(mockPet, never()).move(any(Space.class));
  }

  /**
   * Tests the behavior when the pet is null.
   */
  @Test
  public void testNullPet() {
    strategy.move((Pet) null, mockWorld);

    // No exceptions should occur; ensure no interaction with world or space
    verify(mockSpace, never()).getNeighbors();
    verify(mockPet, never()).move(any(Space.class));
  }

  /**
   * Tests the behavior when the pet's current space is null.
   */
  @Test
  public void testNullSpace() {
    when(mockPet.getCurrentSpace()).thenReturn(null);

    strategy.move(mockPet, mockWorld);
    verify(mockPet, never()).move(any(Space.class));
  }

  /**
   * Tests logging messages during strategy actions.
   */
  @Test
  public void testLoggingMessages() {
    strategy.move(mockPet, mockWorld);

    // Ensure no exceptions occur; log messages can be checked manually if needed
  }
}
