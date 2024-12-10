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
import world.Item;
import world.Player;
import world.Space;
import world.WorldModel;

/**
 * Test class for PickUpItemCommand.
 */
public class PickUpItemCommandTest {

  private Player mockPlayer;
  private Item mockItem;
  private Space mockCurrentSpace;
  private PickUpItemCommand pickUpItemCommand;
  private WorldModel mockModel;
  private View mockView;

  /**
   * Sets up the test environment before each test.
   */
  @Before
  public void setUp() {
    mockPlayer = mock(Player.class);
    mockItem = mock(Item.class);
    mockCurrentSpace = mock(Space.class);
    mockModel = mock(WorldModel.class);
    mockView = mock(View.class);

    // Setup mock interactions
    when(mockPlayer.getCurrentSpace()).thenReturn(mockCurrentSpace);
    List<Item> mockItems = new ArrayList<>();
    mockItems.add(mockItem);
    when(mockItem.getName()).thenReturn("Magic Sword");

    // Create a PickUpItemCommand instance
    pickUpItemCommand = new PickUpItemCommand(mockPlayer, mockItem);
  }

  /**
   * Tests a valid pick-up action.
   */
  @Test
  public void testExecute_ValidPickUp() {
    assertTrue(pickUpItemCommand.isValid());
    pickUpItemCommand.execute();

    verify(mockPlayer).pickUpItem(mockItem);
    verify(mockCurrentSpace).removeItem(mockItem);
  }

  /**
   * Tests an invalid pick-up action where the item is not available.
   */
  @Test
  public void testExecute_InvalidPickUp() {
    List<Item> emptyItems = new ArrayList<>();
    when(mockCurrentSpace.getItems()).thenReturn(emptyItems);

    assertFalse(pickUpItemCommand.isValid());
    pickUpItemCommand.execute();

    verify(mockPlayer, never()).pickUpItem(mockItem);
    verify(mockCurrentSpace, never()).removeItem(mockItem);
  }

  /**
   * Tests the description of the PickUpItemCommand.
   */
  @Test
  public void testGetDescription() {
    assertEquals(
        "Pick up Magic Sword from the current space.",
        pickUpItemCommand.getDescription()
    );
  }

  /**
   * Tests executing PickUpItemCommand with invalid input.
   */
  @Test(expected = UnsupportedOperationException.class)
  public void testExecute_WithInput() {
    pickUpItemCommand.execute("Invalid Input");
  }

  /**
   * Tests the PickUpItemCommand constructor with model and view.
   */
  @Test
  public void testConstructor_WithModelAndView() {
    when(mockModel.getCurrentPlayer()).thenReturn(mockPlayer);
    when(mockView.getSelectedItem()).thenReturn(mockItem);

    PickUpItemCommand command = new PickUpItemCommand(mockModel, mockView);
    assertTrue(command.isValid());
  }

  /**
   * Tests the PickUpItemCommand constructor when the player is null.
   */
  @Test(expected = IllegalArgumentException.class)
  public void testConstructor_NullPlayer() {
    new PickUpItemCommand(null, mockItem);
  }

  /**
   * Tests the PickUpItemCommand constructor when the item is null.
   */
  @Test(expected = IllegalArgumentException.class)
  public void testConstructor_NullItem() {
    new PickUpItemCommand(mockPlayer, null);
  }
}
