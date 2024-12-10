package view;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verifyNoInteractions;
import static org.mockito.Mockito.when;

import controller.Features;
import java.awt.BorderLayout;
import java.awt.event.KeyAdapter;
import java.awt.event.MouseAdapter;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JOptionPane;
import org.junit.Before;
import org.junit.Test;
import world.Space;

/**
 * Test class for GameView.
 */
public class GameViewTest {
  
  private GameView gameView;
  private Features mockFeatures;

  /**
   * Sets up the test environment before each test method execution.
   * Initializes the mock Features and the GameView instance.
   */
  @Before
  public void setUp() {
    mockFeatures = mock(Features.class);
    gameView = new GameView(null); // Initialize with null model for testing
    gameView.setFeatures(mockFeatures);
  }

  @Test
  public void testInitialization() {
    assertNotNull(gameView);
    assertTrue(gameView.getContentPane().getLayout() instanceof BorderLayout);
  }

  @Test
  public void testRenderWorld() {
    List<Space> mockSpaces = new ArrayList<>();
    Space mockSpace = mock(Space.class);
    when(mockSpace.getName()).thenReturn("Test Space");
    mockSpaces.add(mockSpace);

    gameView.renderWorld(mockSpaces);
  }

  @Test
  public void testShowPrompt() {
    String input = "Test Input";
    JOptionPane mockPane = mock(JOptionPane.class);

    when(mockPane.showInputDialog(any(), anyString())).thenReturn(input);

    gameView.showPrompt("Enter something", result -> {
      assertEquals("Test Input", result);
    });
  }

  @Test
  public void testDisplayMessage() {
    gameView.displayMessage("This is a test message.");
  }

  @Test
  public void testDisplayError() {
    gameView.displayError("This is an error message.");
  }

  @Test
  public void testUpdateGameState() {
    String testState = "Game State Updated";
    gameView.updateGameState(testState);
  }

  @Test
  public void testUpdatePlayerStatus() {
    String testStatus = "Player Status Updated";
    gameView.updatePlayerStatus(testStatus);
  }

  @Test
  public void testAddKeyListener() {
    KeyAdapter mockKeyAdapter = mock(KeyAdapter.class);
    gameView.addKeyListener(mockKeyAdapter);
  }

  @Test
  public void testAddMouseListener() {
    MouseAdapter mockMouseAdapter = mock(MouseAdapter.class);
    gameView.addMouseListener(mockMouseAdapter);
  }

  @Test
  public void testIntegrationWithFeatures() {
    verifyNoInteractions(mockFeatures);
    gameView.setFeatures(mockFeatures);
    verifyNoInteractions(mockFeatures);
  }

  @Test
  public void testClearInfo() {
    gameView.clearInfo();
  }
}
