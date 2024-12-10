package command;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.atLeastOnce;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;
import org.junit.Before;
import org.junit.Test;
import world.Item;
import world.Player;
import world.Space;
import world.World;

/**
 * Test class for LookAroundCommand.
 */
public class LookAroundCommandTest {

  private Player mockPlayer;
  private Space mockCurrentSpace;
  private Space mockNeighborSpace;
  private Item mockItem;
  private World mockWorld;
  private LookAroundCommand lookAroundCommand;

  /**
   * Sets up the test environment before each test.
   */
  @Before
  public void setUp() {
    mockPlayer = mock(Player.class);
    mockCurrentSpace = mock(Space.class);
    mockNeighborSpace = mock(Space.class);
    mockItem = mock(Item.class);
    mockWorld = mock(World.class);

    // Mock player and space interactions
    when(mockPlayer.getName()).thenReturn("TestPlayer");
    when(mockPlayer.getCurrentSpace()).thenReturn(mockCurrentSpace);
    when(mockCurrentSpace.getName()).thenReturn("Current Space");

    // Mock items and neighbors
    when(mockItem.getName()).thenReturn("TestItem");
    List<Item> itemsInSpace = new ArrayList<>();
    itemsInSpace.add(mockItem);
    when(mockCurrentSpace.getItems()).thenReturn(itemsInSpace);

    List<Space> neighbors = new ArrayList<>();
    neighbors.add(mockNeighborSpace);
    when(mockCurrentSpace.getNeighbors()).thenReturn(neighbors);

    when(mockNeighborSpace.getName()).thenReturn("Neighbor Space");
    when(mockWorld.isVisible(mockNeighborSpace)).thenReturn(true);

    // Set up LookAroundCommand
    lookAroundCommand = new LookAroundCommand(mockPlayer, mockWorld);
  }

  /**
   * Tests a valid look around operation.
   */
  @Test
  public void testValidLookAround() {
    assertTrue(lookAroundCommand.isValid());

    lookAroundCommand.execute();

    // Verify interactions
    verify(mockPlayer, atLeastOnce()).getCurrentSpace();
    verify(mockCurrentSpace, atLeastOnce()).getItems();
    verify(mockCurrentSpace, atLeastOnce()).getNeighbors();
    verify(mockWorld).isVisible(mockNeighborSpace);
  }

  /**
   * Tests looking around with no items in the current space.
   */
  @Test
  public void testLookAroundWithNoItems() {
    when(mockCurrentSpace.getItems()).thenReturn(new ArrayList<>());

    lookAroundCommand.execute();

    // Verify interactions
    verify(mockCurrentSpace, atLeastOnce()).getItems();
  }

  /**
   * Tests looking around with an invisible neighbor space.
   */
  @Test
  public void testLookAroundWithInvisibleNeighbor() {
    when(mockWorld.isVisible(mockNeighborSpace)).thenReturn(false);

    lookAroundCommand.execute();

    // Verify interactions
    verify(mockWorld).isVisible(mockNeighborSpace);
  }

  /**
   * Tests looking around with no neighbors in the current space.
   */
  @Test
  public void testLookAroundWithNoNeighbors() {
    when(mockCurrentSpace.getNeighbors()).thenReturn(new ArrayList<>());

    lookAroundCommand.execute();

    // Verify interactions
    verify(mockCurrentSpace, atLeastOnce()).getNeighbors();
  }

  /**
   * Tests looking around when the player has no current space.
   */
  @Test(expected = IllegalStateException.class)
  public void testLookAroundWithNoCurrentSpace() {
    when(mockPlayer.getCurrentSpace()).thenReturn(null);

    lookAroundCommand.execute();
  }

  /**
   * Tests the description of the LookAroundCommand.
   */
  @Test
  public void testDescription() {
    assertEquals(
        "Look around your current space and its neighbors.",
        lookAroundCommand.getDescription()
    );
  }

  /**
   * Tests executing LookAroundCommand with invalid input.
   */
  @Test(expected = UnsupportedOperationException.class)
  public void testExecuteWithInput() {
    lookAroundCommand.execute("Invalid Input");
  }

  /**
   * Tests the constructor when the player is null.
   */
  @Test(expected = IllegalArgumentException.class)
  public void testConstructorWithNullPlayer() {
    new LookAroundCommand(null, mockWorld);
  }

  /**
   * Tests the constructor when the world is null.
   */
  @Test(expected = IllegalArgumentException.class)
  public void testConstructorWithNullWorld() {
    new LookAroundCommand(mockPlayer, null);
  }
}
