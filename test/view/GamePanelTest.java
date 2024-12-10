package view;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.awt.Color;
import java.awt.Graphics;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JPanel;
import org.junit.Before;
import org.junit.Test;
import world.Space;

/**
 * Test class for GamePanel.
 */
public class GamePanelTest {

  private GamePanel gamePanel;
  private List<Space> spaces;

  /**
   * Sets up the test environment before each test.
   */
  @Before
  public void setUp() {
    gamePanel = new GamePanel();

    // Initialize mock spaces
    spaces = new ArrayList<>();
    spaces.add(new Space("Room1", 0, 0, null));
    spaces.add(new Space("Room2", 1, 0, null));
    spaces.add(new Space("Room3", 0, 1, null));

    // Render these spaces
    gamePanel.renderWorld(spaces);
  }

  /**
   * Tests the initialization of the GamePanel.
   */
  @Test
  public void testInitialization() {
    assertNotNull("GamePanel should be initialized", gamePanel);
    assertEquals("Background color should be white", Color.WHITE, gamePanel.getBackground());
    assertTrue("GamePanel should be double buffered", gamePanel.isDoubleBuffered());
  }

  /**
   * Tests getting a space at valid coordinates.
   */
  @Test
  public void testGetSpaceAtValidCoordinates() {
    // Test space at (0,0)
    String spaceName = gamePanel.getSpaceAt(25, 25); // Inside Room1
    assertEquals("Room1", spaceName);

    // Test space at (1,0)
    spaceName = gamePanel.getSpaceAt(75, 25); // Inside Room2
    assertEquals("Room2", spaceName);

    // Test space at (0,1)
    spaceName = gamePanel.getSpaceAt(25, 75); // Inside Room3
    assertEquals("Room3", spaceName);
  }

  /**
   * Tests getting a space at invalid coordinates.
   */
  @Test
  public void testGetSpaceAtInvalidCoordinates() {
    // Test coordinates outside any space
    String spaceName = gamePanel.getSpaceAt(200, 200);
    assertNull("Space should be null for invalid coordinates", spaceName);
  }

  /**
   * Tests rendering the world with spaces.
   */
  @Test
  public void testRenderWorld() {
    assertNotNull("GamePanel should not be null", gamePanel);
    assertEquals("Number of spaces should be 3", 3, spaces.size());

    // Validate that the spaces are stored correctly
    for (Space space : spaces) {
      assertTrue("Space should be part of the rendered world", spaces.contains(space));
    }
  }

  /**
   * Tests the paintComponent method.
   */
  @Test
  public void testPaintComponent() {
    // Create a mock Graphics object
    Graphics g = new JPanel().getGraphics();
    try {
      gamePanel.paintComponent(g);
    } catch (Exception e) {
      fail("paintComponent should not throw an exception.");
    }
  }
}
