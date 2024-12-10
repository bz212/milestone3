package view;

import controller.Features;
import java.awt.event.KeyAdapter;
import java.awt.event.MouseAdapter;
import java.util.List;
import java.util.function.Consumer;
import world.Item;
import world.Space;

/**
 * The View interface defines the methods required to display
 * the game state and interact with the user.
 */
public interface View {

  /**
   * Gets the item selected by the user.
   *
   * @return The selected item, or null if no item is selected.
   */
  Item getSelectedItem();

  /**
   * Gets the space selected by the user.
   *
   * @return The selected space, or null if no space is selected.
   */
  Space getSelectedSpace();

  /**
   * Sets the Features controller for this view.
   *
   * @param features The Features instance to connect to the view.
   */
  void setFeatures(Features features);

  /**
   * Renders the game world.
   *
   * @param spaces The list of spaces in the game world.
   */
  void renderWorld(List<Space> spaces);

  /**
   * Updates the game state information.
   *
   * @param message The message to display about the game state.
   */
  void updateGameState(String message);

  /**
   * Updates the player's status in the view.
   *
   * @param playerStatus The string representing the player's status.
   */
  void updatePlayerStatus(String playerStatus);

  /**
   * Highlights the current player in the game.
   *
   * @param playerName The name of the player to highlight.
   */
  void highlightCurrentPlayer(String playerName);

  /**
   * Displays an error message to the user.
   *
   * @param errorMessage The error message to display.
   */
  void displayError(String errorMessage);

  /**
   * Refreshes the view, ensuring all components are updated.
   */
  void refresh();

  /**
   * Prompts the user for input and returns the result.
   *
   * @param message The prompt message.
   * @return The user's input as a string.
   */
  String promptUser(String message);

  /**
   * Clears information displayed in the InfoPanel or other components.
   */
  void clearInfo();

  /**
   * Displays a prompt to the user and processes the input.
   *
   * @param message  The message to display in the prompt.
   * @param callback The function to process the input.
   */
  void showPrompt(String message, Consumer<String> callback);

  /**
   * Displays a message to the user.
   *
   * @param message The message to display.
   */
  void displayMessage(String message);

  /**
   * Gets the name of the space at the specified coordinates.
   *
   * @param x The X coordinate.
   * @param y The Y coordinate.
   * @return The name of the space, or null if no space exists.
   */
  String getSpaceAt(int x, int y);

  /**
   * Shows the welcome screen for the game.
   */
  void showWelcomeScreen();

  /**
   * Adds a key listener to the view.
   *
   * @param keyAdapter The KeyAdapter to add.
   */
  void addKeyListener(KeyAdapter keyAdapter);

  /**
   * Adds a mouse listener to the view.
   *
   * @param mouseAdapter The MouseAdapter to add.
   */
  void addMouseListener(MouseAdapter mouseAdapter);

  /**
   * The InputCallback interface defines a callback mechanism
   * for handling user input.
   */
  interface InputCallback {
    /**
     * Processes the user's input.
     *
     * @param input The input provided by the user.
     */
    void onInput(String input);
  }
}
