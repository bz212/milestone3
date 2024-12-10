package renderer;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.fail;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.atLeastOnce;
import static org.mockito.Mockito.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.awt.Graphics2D;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import javax.swing.JFrame;
import org.junit.Before;
import org.junit.Test;
import world.Player;
import world.Space;
import world.World;

/**
 * Test class for MapRenderer.
 */
public class MapRendererTest {

  private MapRenderer mapRenderer;
  private World mockWorld;
  private List<Space> mockSpaces;
  private List<Player> mockPlayers;
  private Player mockTarget;

  /**
   * Sets up the test environment before each test.
   */
  @Before
  public void setUp() {
    mockWorld = mock(World.class);
    mockSpaces = new ArrayList<>();
    mockPlayers = new ArrayList<>();

    // Mock spaces
    Space mockSpace1 = mock(Space.class);
    when(mockSpace1.getX()).thenReturn(1);
    when(mockSpace1.getY()).thenReturn(1);
    when(mockSpace1.getName()).thenReturn("Space 1");
    mockSpaces.add(mockSpace1);

    Space mockSpace2 = mock(Space.class);
    when(mockSpace2.getX()).thenReturn(2);
    when(mockSpace2.getY()).thenReturn(2);
    when(mockSpace2.getName()).thenReturn("Space 2");
    mockSpaces.add(mockSpace2);

    when(mockSpace1.getNeighbors()).thenReturn(Arrays.asList(mockSpace2));
    when(mockSpace2.getNeighbors()).thenReturn(Arrays.asList(mockSpace1));

    // Mock players
    Player mockPlayer = mock(Player.class);
    when(mockPlayer.getName()).thenReturn("Player 1");
    when(mockPlayer.getCurrentSpace()).thenReturn(mockSpace1);
    mockPlayers.add(mockPlayer);

    // Mock target
    mockTarget = mock(Player.class);
    when(mockTarget.getName()).thenReturn("Target");
    when(mockTarget.getCurrentSpace()).thenReturn(mockSpace2);

    // Mock world behavior
    when(mockWorld.getSpaces()).thenReturn(mockSpaces);
    when(mockWorld.getPlayers()).thenReturn(mockPlayers);
    when(mockWorld.getTarget()).thenReturn(mockTarget);

    mapRenderer = new MapRenderer(mockWorld);
  }

  /**
   * Tests the constructor of MapRenderer.
   */
  @Test
  public void testConstructor() {
    assertNotNull(mapRenderer);
    assertEquals(800, mapRenderer.getPreferredSize().width);
    assertEquals(600, mapRenderer.getPreferredSize().height);
  }

  /**
   * Tests the rendering of the world without exceptions.
   */
  @Test
  public void testRenderWorld() {
    JFrame frame = new JFrame();
    frame.add(mapRenderer);
    frame.pack();

    try {
      mapRenderer.paintComponent(frame.getGraphics());
    } catch (Exception e) {
      fail("Rendering should not throw exceptions.");
    }

    verify(mockWorld, atLeastOnce()).getSpaces();
    verify(mockWorld, atLeastOnce()).getPlayers();
    verify(mockWorld, atLeastOnce()).getTarget();
  }

  /**
   * Tests the drawing of spaces and connections.
   */
  @Test
  public void testDrawSpacesAndConnections() {
    Graphics2D mockGraphics = mock(Graphics2D.class);
    mapRenderer.paintComponent(mockGraphics);

    verify(mockGraphics, atLeastOnce()).fillRect(anyInt(), anyInt(), anyInt(), anyInt());
    verify(mockGraphics, atLeastOnce()).drawLine(anyInt(), anyInt(), anyInt(), anyInt());
  }

  /**
   * Tests the drawing of players.
   */
  @Test
  public void testDrawPlayers() {
    Graphics2D mockGraphics = mock(Graphics2D.class);
    mapRenderer.paintComponent(mockGraphics);

    verify(mockGraphics, atLeastOnce()).fillOval(anyInt(), anyInt(), anyInt(), anyInt());
    verify(mockGraphics, atLeastOnce()).drawString(eq("Player 1"), anyInt(), anyInt());
  }

  /**
   * Tests the drawing of the target.
   */
  @Test
  public void testDrawTarget() {
    Graphics2D mockGraphics = mock(Graphics2D.class);
    mapRenderer.paintComponent(mockGraphics);

    verify(mockGraphics, atLeastOnce()).fillOval(anyInt(), anyInt(), anyInt(), anyInt());
    verify(mockGraphics, atLeastOnce()).drawString(eq("Target"), anyInt(), anyInt());
  }

  /**
   * Tests handling mouse click events.
   */
  @Test
  public void testHandleMouseClick() {
    MouseEvent mockEvent = mock(MouseEvent.class);
    when(mockEvent.getX()).thenReturn(50);
    when(mockEvent.getY()).thenReturn(50);

    mapRenderer.dispatchEvent(mockEvent);

    verify(mockWorld).getSpaces();
  }

  /**
   * Tests the constructor with a null world, expecting an exception.
   */
  @Test(expected = IllegalArgumentException.class)
  public void testConstructorWithNullWorld() {
    new MapRenderer(null);
  }
}
