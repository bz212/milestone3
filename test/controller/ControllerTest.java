package controller;

import static org.junit.Assert.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.doAnswer;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import command.Command;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.util.function.Consumer;
import org.junit.Before;
import org.junit.Test;
import view.View;
import world.WorldModel;

/**
 * Unit tests for the Controller class, ensuring all functionalities
 * are working correctly and meet project requirements.
 */
public class ControllerTest {

  private Controller controller;
  private WorldModel mockModel;
  private View mockView;

  /**
   * Sets up the test environment before each test.
   */
  @Before
  public void setUp() {
    mockModel = mock(WorldModel.class);
    mockView = mock(View.class);
    controller = new Controller(mockModel, mockView);
  }

  /**
   * Tests handling key input for the "Look Around" action.
   */
  @Test
  public void testKeyInputLookAround() {
    KeyEvent mockKeyEvent = mock(KeyEvent.class);
    when(mockKeyEvent.getKeyCode()).thenReturn(KeyEvent.VK_L);
    when(mockModel.lookAround()).thenReturn("You see a basketball and sneakers.");
    
    controller.handleKeyInput(mockKeyEvent);

    verify(mockModel, times(1)).lookAround();
    verify(mockView, times(1)).updateGameState("You see a basketball and sneakers.");
  }

  /**
   * Tests handling key input for the "Move Player" action.
   */
  @Test
  public void testKeyInputMove() {
    KeyEvent mockKeyEvent = mock(KeyEvent.class);
    when(mockKeyEvent.getKeyCode()).thenReturn(KeyEvent.VK_M);

    doAnswer(invocation -> {
      Consumer<String> callback = invocation.getArgument(1);
      callback.accept("CourtSide");
      return null;
    }).when(mockView).showPrompt(eq("Enter space name to move:"), any());

    when(mockModel.movePlayer("CourtSide")).thenReturn(true);

    controller.handleKeyInput(mockKeyEvent);

    verify(mockModel, times(1)).movePlayer("CourtSide");
    verify(mockView, times(1)).updateGameState("Player moved to CourtSide");
  }

  /**
   * Tests handling key input for the "Pick Up Item" action.
   */
  @Test
  public void testKeyInputPickUpItem() {
    KeyEvent mockKeyEvent = mock(KeyEvent.class);
    when(mockKeyEvent.getKeyCode()).thenReturn(KeyEvent.VK_P);

    when(mockModel.pickUpItem("Jersey")).thenReturn(true);

    controller.handleKeyInput(mockKeyEvent);

    verify(mockModel, times(1)).pickUpItem(anyString());
    verify(mockView, times(1)).updateGameState(anyString());
  }

  /**
   * Tests handling mouse click for a valid space.
   */
  @Test
  public void testMouseClickValidSpace() {
    MouseEvent mockMouseEvent = mock(MouseEvent.class);
    when(mockMouseEvent.getX()).thenReturn(100);
    when(mockMouseEvent.getY()).thenReturn(200);
    when(mockView.getSpaceAt(100, 200)).thenReturn("CourtSide");

    when(mockModel.movePlayer("CourtSide")).thenReturn(true);

    controller.handleMouseClick(mockMouseEvent);

    verify(mockModel, times(1)).movePlayer("CourtSide");
  }

  /**
   * Tests successful execution of a command.
   */
  @Test
  public void testCommandExecutionSuccess() throws Exception {
    Command mockCommand = mock(Command.class);
    when(mockCommand.isValid()).thenReturn(true);
    controller.getCommands().put("look", mockCommand);

    controller.executeCommand("look");

    verify(mockCommand, times(1)).execute();
  }

  /**
   * Tests execution failure of a command and error handling.
   */
  @Test
  public void testCommandExecutionFailure() throws Exception {
    Command mockCommand = mock(Command.class);
    when(mockCommand.isValid()).thenReturn(true);
    doThrow(new RuntimeException("Execution failed")).when(mockCommand).execute();
    controller.getCommands().put("look", mockCommand);

    controller.executeCommand("look");

    verify(mockView, times(1)).displayMessage("Error executing command: Execution failed");
  }

  /**
   * Tests the game over condition.
   */
  @Test
  public void testGameOver() {
    when(mockModel.isGameOver()).thenReturn(true); // Mock game over condition
    boolean result = controller.isGameOver();
    verify(mockModel, times(1)).isGameOver();
    assertTrue(result);
  }

  /**
   * Tests starting the controller and showing the welcome screen.
   */
  @Test
  public void testStart() {
    controller.start();
    verify(mockView).showWelcomeScreen();
  }
}
