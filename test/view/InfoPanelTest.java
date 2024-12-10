package view;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.awt.BorderLayout;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import org.junit.Before;
import org.junit.Test;

/**
 * Test class for InfoPanel.
 */
public class InfoPanelTest {

  private InfoPanel infoPanel;

  /**
   * Sets up the test environment before each test execution.
   * Initializes the InfoPanel instance.
   */
  @Before
  public void setUp() {
    infoPanel = new InfoPanel();
  }

  /**
   * Tests the initialization of InfoPanel.
   */
  @Test
  public void testInitialization() {
    assertNotNull(infoPanel);
    assertTrue(infoPanel.getLayout() instanceof BorderLayout);
  }

  /**
   * Tests updating the game state with a valid message.
   */
  @Test
  public void testUpdateGameState() {
    String testMessage = "Game has started.";
    infoPanel.updateGameState(testMessage);

    JTextArea gameStateArea =
        (JTextArea) ((JScrollPane) infoPanel.getComponent(0)).getViewport().getView();
    assertTrue(gameStateArea.getText().contains(testMessage));
  }

  /**
   * Tests updating the game state with an empty message.
   */
  @Test
  public void testUpdateGameStateWithEmptyMessage() {
    infoPanel.updateGameState("");

    JTextArea gameStateArea =
        (JTextArea) ((JScrollPane) infoPanel.getComponent(0)).getViewport().getView();
    assertTrue(gameStateArea.getText().contains("No update provided"));
  }

  /**
   * Tests updating the player status with a valid message.
   */
  @Test
  public void testUpdatePlayerStatus() {
    String testStatus = "Player health: 100.";
    infoPanel.updatePlayerStatus(testStatus);

    JTextArea playerStatusArea =
        (JTextArea) ((JScrollPane) infoPanel.getComponent(1)).getViewport().getView();
    assertTrue(playerStatusArea.getText().contains(testStatus));
  }

  /**
   * Tests updating the player status with an empty message.
   */
  @Test
  public void testUpdatePlayerStatusWithEmptyMessage() {
    infoPanel.updatePlayerStatus("");

    JTextArea playerStatusArea =
        (JTextArea) ((JScrollPane) infoPanel.getComponent(1)).getViewport().getView();
    assertTrue(playerStatusArea.getText().contains("No update provided"));
  }

  /**
   * Tests clearing information in InfoPanel.
   */
  @Test
  public void testClearInfo() {
    infoPanel.updateGameState("Game running.");
    infoPanel.updatePlayerStatus("Player health: 50.");
    infoPanel.clearInfo();

    JTextArea gameStateArea =
        (JTextArea) ((JScrollPane) infoPanel.getComponent(0)).getViewport().getView();
    JTextArea playerStatusArea =
        (JTextArea) ((JScrollPane) infoPanel.getComponent(1)).getViewport().getView();

    assertEquals("Game State:\n", gameStateArea.getText());
    assertEquals("Player Status:\n", playerStatusArea.getText());
  }
}
