package view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

/**
 * The InfoPanel class is responsible for displaying player and game state information.
 */
public class InfoPanel extends JPanel {

  private static final long serialVersionUID = 1L;
  private final JTextArea gameStateArea;
  private final JTextArea playerStatusArea;

  /**
   * Constructs an InfoPanel with areas for game state and player status updates.
   */
  public InfoPanel() {
    setLayout(new BorderLayout());
    gameStateArea = createTextArea("Game State:");
    playerStatusArea = createTextArea("Player Status:");

    add(new JScrollPane(gameStateArea), BorderLayout.CENTER);
    add(new JScrollPane(playerStatusArea), BorderLayout.SOUTH);
  }

  /**
   * Creates a JTextArea with default settings.
   *
   * @param title The title for the JTextArea.
   * @return The configured JTextArea.
   */
  private JTextArea createTextArea(String title) {
    JTextArea textArea = new JTextArea(title + "\n");
    textArea.setEditable(false);
    textArea.setLineWrap(true);
    textArea.setWrapStyleWord(true);
    textArea.setBackground(Color.LIGHT_GRAY);
    textArea.setFont(new Font("Monospaced", Font.PLAIN, 12));
    return textArea;
  }

  /**
   * Updates the game state information.
   *
   * @param message The game state message to display.
   */
  public void updateGameState(String message) {
    String output = (message == null || message.trim().isEmpty()) 
        ? "Game State: No update provided.\n"
        : "Game State: " + message + "\n";

    gameStateArea.append(output);
    gameStateArea.setCaretPosition(gameStateArea.getDocument().getLength());
  }

  /**
   * Updates the player status information.
   *
   * @param status The player status message to display.
   */
  public void updatePlayerStatus(String status) {
    String output = (status == null || status.trim().isEmpty()) 
        ? "Player Status: No update provided.\n"
        : "Player Status: " + status + "\n";

    playerStatusArea.append(output);
    playerStatusArea.setCaretPosition(playerStatusArea.getDocument().getLength());
  }

  /**
   * Clears all the displayed information.
   * Resets the game state and player status areas.
   */
  public void clearInfo() {
    gameStateArea.setText("Game State:\n");
    playerStatusArea.setText("Player Status:\n");
  }
}
