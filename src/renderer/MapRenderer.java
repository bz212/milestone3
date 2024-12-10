package renderer;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.List;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import world.Player;
import world.Space;
import world.World;

/**
 * The MapRenderer class is responsible for rendering the game world
 * and its components onto a graphical interface.
 */
public class MapRenderer extends JPanel {

  private static final long serialVersionUID = 1L;
  private static final int TILE_SIZE = 50;
  private static final int PLAYER_OFFSET = 15;
  private static final int PLAYER_SIZE = 20;

  private final World world;
  private final List<Player> players;

  /**
   * Constructs a new MapRenderer with the given world.
   *
   * @param world The game world to render.
   */
  public MapRenderer(World world) {
    if (world == null) {
      throw new IllegalArgumentException("World cannot be null.");
    }
    this.world = world;
    this.players = world.getPlayers();
    setPreferredSize(new Dimension(800, 600));
    addMouseListener(new MouseAdapter() {
      @Override
      public void mouseClicked(MouseEvent e) {
        handleMouseClick(e.getX(), e.getY());
      }
    });
  }

  @Override
  protected void paintComponent(Graphics g) {
    super.paintComponent(g);
    Graphics2D g2d = (Graphics2D) g;

    // Clear the panel
    g2d.setColor(Color.WHITE);
    g2d.fillRect(0, 0, getWidth(), getHeight());

    // Render spaces and their neighbors
    for (Space space : world.getSpaces()) {
      drawSpace(g2d, space);
      for (Space neighbor : space.getNeighbors()) {
        drawNeighborConnection(g2d, space, neighbor);
      }
    }

    // Render players
    for (Player player : players) {
      drawPlayer(g2d, player);
    }

    // Render target
    drawTarget(g2d, world.getTarget());
  }

  private void drawSpace(Graphics2D g, Space space) {
    int x = space.getX() * TILE_SIZE;
    int y = space.getY() * TILE_SIZE;

    g.setColor(Color.LIGHT_GRAY);
    g.fillRect(x, y, TILE_SIZE, TILE_SIZE);
    g.setColor(Color.BLACK);
    g.drawRect(x, y, TILE_SIZE, TILE_SIZE);
    g.drawString(space.getName(), x + 5, y + 25);
  }

  private void drawNeighborConnection(Graphics2D g, Space space, Space neighbor) {
    int x1 = space.getX() * TILE_SIZE + TILE_SIZE / 2;
    int y1 = space.getY() * TILE_SIZE + TILE_SIZE / 2;
    int x2 = neighbor.getX() * TILE_SIZE + TILE_SIZE / 2;
    int y2 = neighbor.getY() * TILE_SIZE + TILE_SIZE / 2;

    g.setColor(Color.GRAY);
    g.drawLine(x1, y1, x2, y2);
  }

  private void drawPlayer(Graphics2D g, Player player) {
    Space currentSpace = player.getCurrentSpace();
    if (currentSpace != null) {
      int x = currentSpace.getX() * TILE_SIZE + PLAYER_OFFSET;
      int y = currentSpace.getY() * TILE_SIZE + PLAYER_OFFSET;

      g.setColor(Color.BLUE);
      g.fillOval(x, y, PLAYER_SIZE, PLAYER_SIZE);
      g.setColor(Color.BLACK);
      g.drawString(player.getName(), x - 5, y - 5);
    }
  }

  private void drawTarget(Graphics2D g, Player target) {
    if (target == null) {
      return;
    }
    Space currentSpace = target.getCurrentSpace();
    if (currentSpace != null) {
      int x = currentSpace.getX() * TILE_SIZE + PLAYER_OFFSET;
      int y = currentSpace.getY() * TILE_SIZE + PLAYER_OFFSET;

      g.setColor(Color.RED);
      g.fillOval(x, y, PLAYER_SIZE, PLAYER_SIZE);
      g.setColor(Color.BLACK);
      g.drawString("Target", x - 5, y - 5);
    }
  }

  private void handleMouseClick(int x, int y) {
    int gridX = x / TILE_SIZE;
    int gridY = y / TILE_SIZE;
    for (Space space : world.getSpaces()) {
      if (space.getX() == gridX && space.getY() == gridY) {
        JOptionPane.showMessageDialog(
            this, "You clicked on space: " + space.getName());
        return;
      }
    }
  }
}
