package view;

import controller.Features;
import java.awt.BorderLayout;
import java.awt.event.KeyAdapter;
import java.awt.event.MouseAdapter;
import java.util.List;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import world.Item;
import world.Space;
import world.WorldModel;

/**
 * GameView is the main window for the game, integrating all UI components.
 */
public class GameView extends JFrame implements View {

  private static final long serialVersionUID = 1L;
  private final InfoPanel infoPanel;
  private final GamePanel gamePanel;
  private Features features;

  /**
   * Constructs the GameView with the main components.
   *
   * @param model The game world model.
   */
  public GameView(WorldModel model) {
    setTitle("Game View");
    setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    setLayout(new BorderLayout());

    // Initialize main panels
    infoPanel = new InfoPanel();
    gamePanel = new GamePanel();

    // Add panels to the main layout
    add(infoPanel, BorderLayout.EAST);
    add(gamePanel, BorderLayout.CENTER);

    setSize(1024, 768);
    setVisible(true);
  }

  @Override
  public void showPrompt(String message, java.util.function.Consumer<String> callback) {
    String input = JOptionPane.showInputDialog(this, message);
    if (input != null && !input.trim().isEmpty()) {
      callback.accept(input.trim());
    }
  }

  @Override
  public void displayMessage(String message) {
    JOptionPane.showMessageDialog(this, message, "Information", JOptionPane.INFORMATION_MESSAGE);
  }

  @Override
  public String getSpaceAt(int x, int y) {
    // Assuming `GamePanel` has a method to map coordinates to spaces
    return gamePanel.getSpaceAt(x, y);
  }

  @Override
  public void showWelcomeScreen() {
    JOptionPane.showMessageDialog(this, "Welcome to the Game!", "Welcome", 
        JOptionPane.INFORMATION_MESSAGE);
  }

  @Override
  public void setFeatures(Features features) {
    this.features = features;
  }

  @Override
  public void renderWorld(List<Space> spaces) {
    gamePanel.renderWorld(spaces);
  }

  @Override
  public void updateGameState(String message) {
    infoPanel.updateGameState(message);
  }

  @Override
  public void updatePlayerStatus(String status) {
    infoPanel.updatePlayerStatus(status);
  }

  @Override
  public void highlightCurrentPlayer(String playerName) {
    infoPanel.updateGameState("Current turn: " + playerName);
  }

  @Override
  public void displayError(String errorMessage) {
    JOptionPane.showMessageDialog(this, errorMessage, "Error", JOptionPane.ERROR_MESSAGE);
  }

  @Override
  public void refresh() {
    revalidate();
    repaint();
  }

  @Override
  public String promptUser(String message) {
    return JOptionPane.showInputDialog(this, message);
  }

  @Override
  public void clearInfo() {
    infoPanel.clearInfo();
  }

  /**
   * Adds a KeyAdapter for key input handling.
   *
   * @param keyAdapter The KeyAdapter to add.
   */
  @Override
  public void addKeyListener(KeyAdapter keyAdapter) {
    super.addKeyListener(keyAdapter);
  }

  /**
   * Adds a MouseAdapter for mouse input handling.
   *
   * @param mouseAdapter The MouseAdapter to add.
   */
  @Override
  public void addMouseListener(MouseAdapter mouseAdapter) {
    super.addMouseListener(mouseAdapter);
  }

  /**
   * Gets the currently selected space in the game.
   *
   * @return The selected Space object, or null if none is selected.
   */
  @Override
  public Space getSelectedSpace() {
    // TODO: Implement method to return the selected space.
    return null;
  }

  /**
   * Gets the currently selected item in the game.
   *
   * @return The selected Item object, or null if none is selected.
   */
  @Override
  public Item getSelectedItem() {
    // TODO: Implement method to return the selected item.
    return null;
  }
}
