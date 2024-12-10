package view;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.util.List;
import javax.swing.JPanel;
import world.Space;

/**
 * The GamePanel class is responsible for rendering the game world
 * and its components, including spaces and players.
 */
public class GamePanel extends JPanel {

  private static final long serialVersionUID = 1L;
  private static final int TILE_SIZE = 50; // Size of each tile
  private List<Space> spaces;

  /**
   * Constructor for GamePanel.
   * Sets default configurations for the panel.
   */
  public GamePanel() {
    setBackground(Color.WHITE);
    setDoubleBuffered(true); // Improves rendering performance
  }

  /**
   * Retrieves the name of the space at the given screen coordinates.
   *
   * @param x The X-coordinate on the screen.
   * @param y The Y-coordinate on the screen.
   * @return The name of the space, or null if no space exists at the location.
   */
  public String getSpaceAt(int x, int y) {
    if (spaces == null) {
      return null;
    }

    int gridX = x / TILE_SIZE;
    int gridY = y / TILE_SIZE;

    for (Space space : spaces) {
      if (space.getX() == gridX && space.getY() == gridY) {
        return space.getName();
      }
    }

    return null;
  }

  /**
   * Updates the list of spaces to be rendered and refreshes the panel.
   *
   * @param spaces The list of spaces in the game world.
   * @throws IllegalArgumentException if spaces is null.
   */
  public void renderWorld(List<Space> spaces) {
    if (spaces == null) {
      throw new IllegalArgumentException("Spaces cannot be null.");
    }
    this.spaces = spaces;
    repaint(); // Triggers a re-render of the panel
  }

  /**
   * Paints the component by rendering all spaces and their attributes.
   *
   * @param g The Graphics object used for rendering.
   */
  @Override
  protected void paintComponent(Graphics g) {
    super.paintComponent(g);
    Graphics2D g2d = (Graphics2D) g;

    if (spaces != null && !spaces.isEmpty()) {
      for (Space space : spaces) {
        drawSpace(g2d, space);
      }
    } else {
      drawEmptyWorldMessage(g2d);
    }
  }

  /**
   * Renders a single space on the game panel.
   *
   * @param g     The Graphics2D object for rendering.
   * @param space The space to render.
   */
  private void drawSpace(Graphics2D g, Space space) {
    int x = space.getX() * TILE_SIZE;
    int y = space.getY() * TILE_SIZE;

    // Draw the space background
    g.setColor(Color.LIGHT_GRAY);
    g.fillRect(x, y, TILE_SIZE, TILE_SIZE);

    // Draw the space border
    g.setColor(Color.BLACK);
    g.drawRect(x, y, TILE_SIZE, TILE_SIZE);

    // Draw the space name
    g.setColor(Color.BLACK);
    g.drawString(space.getName(), x + 5, y + 15);
  }

  /**
   * Draws a placeholder message when there are no spaces to render.
   *
   * @param g The Graphics2D object for rendering.
   */
  private void drawEmptyWorldMessage(Graphics2D g) {
    String message = "No spaces to display.";
    int x = getWidth() / 2 - g.getFontMetrics().stringWidth(message) / 2;
    int y = getHeight() / 2;
    g.setColor(Color.RED);
    g.drawString(message, x, y);
  }
}
