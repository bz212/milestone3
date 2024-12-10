package strategy;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.atLeastOnce;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;
import org.junit.Before;
import org.junit.Test;
import world.AiPlayer;
import world.Pet;
import world.Player;
import world.Space;
import world.World;

/**
 * Test class for ChasePlayerStrategy.
 */
public class ChasePlayerStrategyTest {

  private ChasePlayerStrategy strategy;
  private AiPlayer mockAiPlayer;
  private Pet mockPet;
  private Space mockSpace;
  private World mockWorld;
  private List<Player> mockPlayers;

  /**
   * Sets up the test environment before each test.
   */
  @Before
  public void setUp() {
    strategy = new ChasePlayerStrategy();
    mockAiPlayer = mock(AiPlayer.class);
    mockPet = mock(Pet.class);
    mockSpace = mock(Space.class);
    mockWorld = mock(World.class);

    // Mock players in the world
    mockPlayers = new ArrayList<>();
    Player player1 = mock(Player.class);
    Player player2 = mock(Player.class);
    when(player1.getName()).thenReturn("Player1");
    when(player2.getName()).thenReturn("Player2");
    when(player1.getCurrentSpace()).thenReturn(mock(Space.class));
    when(player2.getCurrentSpace()).thenReturn(mock(Space.class));
    mockPlayers.add(player1);
    mockPlayers.add(player2);

    when(mockWorld.getPlayers()).thenReturn(mockPlayers);
    when(mockAiPlayer.getCurrentSpace()).thenReturn(mockSpace);
    when(mockPet.getCurrentSpace()).thenReturn(mockSpace);
  }

  /**
   * Tests the strategy's decision-making for an AI player.
   */
  @Test
  public void testDecideActionForAiPlayer() {
    String result = strategy.decideAction(mockAiPlayer, mockWorld);

    assertNotNull(result);
    verify(mockWorld, times(1)).getPlayers();
    verify(mockAiPlayer, atLeastOnce()).move(any(Space.class));
    assertTrue(result.contains("moved towards"));
  }

  /**
   * Tests the strategy's decision-making for a pet.
   */
  @Test
  public void testDecideActionForPet() {
    String result = strategy.decideAction(mockPet, mockWorld);

    assertNotNull(result);
    verify(mockWorld, times(1)).getPlayers();
    verify(mockPet, atLeastOnce()).move(any(Space.class));
    assertTrue(result.contains("moved towards"));
  }

  /**
   * Tests the scenario where there are no players to chase.
   */
  @Test
  public void testNoPlayersToChase() {
    when(mockWorld.getPlayers()).thenReturn(new ArrayList<>());

    String result = strategy.decideAction(mockAiPlayer, mockWorld);
    assertEquals(mockAiPlayer.getName() + " has no players to chase.", result);
  }

  /**
   * Tests the scenario where there are no neighbors for the space.
   */
  @Test
  public void testNoNeighbors() {
    when(mockSpace.getNeighbors()).thenReturn(new ArrayList<>());

    String result = strategy.decideAction(mockAiPlayer, mockWorld);
    assertTrue(result.contains("has no valid moves"));
  }

  /**
   * Tests the calculation of distance between two spaces.
   */
  @Test
  public void testCalculateDistance() {
    Space space1 = mock(Space.class);
    Space space2 = mock(Space.class);
    when(space1.getX()).thenReturn(0);
    when(space1.getY()).thenReturn(0);
    when(space2.getX()).thenReturn(3);
    when(space2.getY()).thenReturn(4);

    double distance = strategy.calculateDistance(space1, space2);
    assertEquals(5.0, distance, 0.001);
  }
}
