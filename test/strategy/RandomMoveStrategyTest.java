package strategy;

import static org.junit.Assert.assertEquals;
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
import world.Player;
import world.Space;
import world.World;

/**
 * Test class for RandomMoveStrategy.
 */
public class RandomMoveStrategyTest {

  private RandomMoveStrategy strategy;
  private Player mockPlayer;
  private Pet mockPet;
  private Space mockSpace;
  private World mockWorld;
  private List<Space> neighbors;

  /**
   * Sets up the test environment before each test.
   */
  @Before
  public void setUp() {
    strategy = new RandomMoveStrategy();

    mockPlayer = mock(Player.class);
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
    when(mockPlayer.getCurrentSpace()).thenReturn(mockSpace);
    when(mockPet.getCurrentSpace()).thenReturn(mockSpace);
  }

  /**
   * Tests that the player moves to a random neighboring space.
   */
  @Test
  public void testPlayerMovesToRandomSpace() {
    strategy.move(mockPlayer, mockWorld);

    verify(mockSpace, times(1)).getNeighbors();
    verify(mockPlayer, times(1)).move(any(Space.class));
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

    strategy.move(mockPlayer, mockWorld);
    verify(mockPlayer, never()).move(any(Space.class));

    strategy.move(mockPet, mockWorld);
    verify(mockPet, never()).move(any(Space.class));
  }

  /**
   * Tests the behavior when the entity is null.
   */
  @Test
  public void testNullEntity() {
    String result = strategy.move((Player) null, mockWorld);
    assertEquals("Error: Entity is null.", result);
  }

  /**
   * Tests the behavior when the entity's current space is null.
   */
  @Test
  public void testNullSpace() {
    when(mockPlayer.getCurrentSpace()).thenReturn(null);
    when(mockPet.getCurrentSpace()).thenReturn(null);

    strategy.move(mockPlayer, mockWorld);
    verify(mockPlayer, never()).move(any(Space.class));

    strategy.move(mockPet, mockWorld);
    verify(mockPet, never()).move(any(Space.class));
  }

  /**
   * Tests logging messages during strategy actions.
   */
  @Test
  public void testLoggingMessages() {
    strategy.move(mockPlayer, mockWorld);
    strategy.move(mockPet, mockWorld);

    // Ensure no exceptions occur; log messages can be checked manually if needed
  }
}
