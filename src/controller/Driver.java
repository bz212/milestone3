package controller;

import java.io.File;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.SwingUtilities;
import view.AboutScreen;
import view.GameView;
import world.World;
import world.WorldModel;


/**
 * Driver class to launch the game.
 */
public class Driver {

  private static final Logger logger = Logger.getLogger(Driver.class.getName());

  /**
   * Main entry point of the program.
   *
   * @param args Command-line arguments.
   */
  public static void main(String[] args) {
    SwingUtilities.invokeLater(() -> {
      AboutScreen aboutScreen = new AboutScreen(() -> {
        try {
          startGame();
        } catch (Exception e) {
          logger.log(Level.SEVERE, "Error starting the game", e);
          JOptionPane.showMessageDialog(
              null,
              "Failed to start the game: " + e.getMessage(),
              "Error",
              JOptionPane.ERROR_MESSAGE
          );
        }
      });
      aboutScreen.setVisible(true);
    });
  }

  /**
   * Starts the game after AboutScreen interaction.
   */
  private static void startGame() throws Exception {
    // Show file chooser to get world configuration file
    String worldConfigFile = getConfigFilePath();
    if (worldConfigFile == null) {
      throw new IllegalArgumentException("World configuration file not selected.");
    }

    // Get maximum number of turns
    int maxTurns = getMaxTurns();
    if (maxTurns <= 0) {
      throw new IllegalArgumentException("Invalid maximum turns.");
    }

    // Validate and load the configuration file
    File configFile = new File(worldConfigFile);
    if (!configFile.exists() || !configFile.isFile()) {
      throw new IllegalArgumentException(
          "World configuration file not found: " + worldConfigFile);
    }

    // Initialize model
    WorldModel model = initializeModel(worldConfigFile, maxTurns);

    // Launch the game using Swing's Event Dispatch Thread
    SwingUtilities.invokeLater(() -> {
      GameView view = new GameView(model);
      Controller controller = new Controller(model, view);
      controller.start();
    });
  }

  /**
   * Prompts the user to select a world configuration file.
   *
   * @return The selected file path or null if canceled.
   */
  private static String getConfigFilePath() {
    JFileChooser fileChooser = new JFileChooser();
    fileChooser.setDialogTitle("Select World Configuration File");
    int result = fileChooser.showOpenDialog(null);

    if (result == JFileChooser.APPROVE_OPTION) {
      File selectedFile = fileChooser.getSelectedFile();
      if (selectedFile != null && selectedFile.exists() && selectedFile.isFile()) {
        return selectedFile.getAbsolutePath();
      } else {
        JOptionPane.showMessageDialog(
            null,
            "Invalid file selected. Please select a valid configuration file.",
            "Error",
            JOptionPane.ERROR_MESSAGE
        );
      }
    }
    return null;
  }

  /**
   * Prompts the user to input the maximum number of turns.
   *
   * @return The validated number of turns.
   */
  private static int getMaxTurns() {
    while (true) {
      String input = JOptionPane.showInputDialog("Enter the maximum number of turns:");
      try {
        int maxTurns = Integer.parseInt(input);
        if (maxTurns > 0) {
          return maxTurns;
        }
      } catch (NumberFormatException ignored) {
        // Show error dialog
      }
      JOptionPane.showMessageDialog(
          null,
          "Invalid number of turns. Please enter a positive integer.",
          "Error",
          JOptionPane.ERROR_MESSAGE
      );
    }
  }

  /**
   * Initializes the game world model.
   *
   * @param worldConfigFile The configuration file path.
   * @param maxTurns        The maximum number of turns.
   * @return The initialized WorldModel instance.
   * @throws Exception If initialization fails.
   */
  private static WorldModel initializeModel(String worldConfigFile, int maxTurns) throws Exception {
    logger.info("Initializing the game model...");
    WorldModel model = new World(worldConfigFile, maxTurns);
    logger.info("Game model initialized successfully.");
    return model;
  }
}
