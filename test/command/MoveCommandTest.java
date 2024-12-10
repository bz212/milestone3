package command;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;
import org.junit.Before;
import org.junit.Test;
import view.View;
import world.Player;
import world.Space;
import world.WorldModel;


/**
 * Test class for MoveCommand.
 */
public class MoveCommandTest {

  private Player mockPlayer;
  private Space mockCurrentSpace;
  private Space mockTargetSpace;
  private MoveCommand moveCommand;
  private WorldModel mockModel;
  private View mockView;

  /**
   * Sets up the test environment before each test.
   */
  @Before
  public void setUp() {
    mockPlayer = mock(Player.class);
    mockCurrentSpace = mock(Space.class);
    mockTargetSpace = mock(Space.class);
    mockModel = mock(WorldModel.class);
    mockView = mock(View.class);

    // Setup mock interactions
    when(mockPlayer.getCurrentSpace()).thenReturn(mockCurrentSpace);
    List<Space> neighbors = new ArrayList<>();
    neighbors.add(mockTargetSpace);
    when(mockCurrentSpace.getNeighbors()).thenReturn(neighbors);
    when(mockTargetSpace.getName()).thenReturn("Target Space");

    // Create a MoveCommand instance
    moveCommand = new MoveCommand(mockPlayer, mockTargetSpace);
  }

  /**
   * Tests executing a valid move command.
   */
  @Test
  public void testExecute_ValidMove() {
    assertTrue(moveCommand.isValid());
    moveCommand.execute();
    verify(mockPlayer).move(mockTargetSpace);
  }

  /**
   * Tests executing an invalid move command.
   */
  @Test
  public void testExecute_InvalidMove() {
    // Modify neighbors to make the move invalid
    List<Space> emptyNeighbors = new ArrayList<>();
    when(mockCurrentSpace.getNeighbors()).thenReturn(emptyNeighbors);

    assertFalse(moveCommand.isValid());
    moveCommand.execute();
    verify(mockPlayer, never()).move(mockTargetSpace);
  }

  /**
   * Tests retrieving the description of the move command.
   */
  @Test
  public void testGetDescription() {
    assertEquals("Move to Target Space", moveCommand.getDescription());
  }

  /**
   * Tests executing the move command with invalid input.
   */
  @Test(expected = UnsupportedOperationException.class)
  public void testExecute_WithInput() {
    moveCommand.execute("Invalid Input");
  }

  /**
   * Tests the MoveCommand constructor with model and view.
   */
  @Test
  public void testConstructor_WithModelAndView() {
    when(mockModel.getCurrentPlayer()).thenReturn(mockPlayer);
    when(mockView.getSelectedSpace()).thenReturn(mockTargetSpace);

    MoveCommand command = new MoveCommand(mockModel, mockView);
    assertTrue(command.isValid());
  }

  /**
   * Tests the MoveCommand constructor when the player is null.
   */
  @Test(expected = IllegalArgumentException.class)
  public void testConstructor_NullPlayer() {
    new MoveCommand(null, mockTargetSpace);
  }

  /**
   * Tests the MoveCommand constructor when the target space is null.
   */
  @Test(expected = IllegalArgumentException.class)
  public void testConstructor_NullTargetSpace() {
    new MoveCommand(mockPlayer, null);
  }
}
