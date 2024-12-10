package controller;

import command.Command;
import command.LookAroundCommand;
import command.MoveCommand;
import command.PickUpItemCommand;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.HashMap;
import java.util.Map;
import view.View;
import world.WorldModel;

/**
 * Manages interactions between the model and the view, handling user input and game logic.
 */
public class Controller implements Features {

  private static final String COMMAND_LOOK = "look";
  private static final String COMMAND_MOVE = "move";
  private static final String COMMAND_PICKUP = "pickup";

  private final WorldModel model;
  private final View view;
  private final Map<String, Command> commands;

  /**
   * Constructs a Controller with the given model and view.
   *
   * @param model The game world model.
   * @param view  The game view.
   */
  public Controller(WorldModel model, View view) {
    if (model == null || view == null) {
      throw new IllegalArgumentException("Model and View cannot be null.");
    }
    this.model = model;
    this.view = view;
    this.commands = new HashMap<>();
    initialize();
  }

  /**
   * Initializes commands and listeners for the game.
   */
  private void initialize() {
    setupCommands();
    setupListeners();
  }

  /**
   * Sets up available commands in the game.
   */
  private void setupCommands() {
    commands.put(COMMAND_LOOK, new LookAroundCommand(model, view));
    commands.put(COMMAND_MOVE, new MoveCommand(model, view));
    commands.put(COMMAND_PICKUP, new PickUpItemCommand(model, view));
  }

  /**
   * Sets up keyboard and mouse listeners for the game.
   */
  private void setupListeners() {
    view.addKeyListener(new java.awt.event.KeyAdapter() {
      @Override
      public void keyPressed(KeyEvent e) {
        handleKeyInput(e);
      }
    });

    view.addMouseListener(new MouseAdapter() {
      @Override
      public void mouseClicked(MouseEvent e) {
        handleMouseClick(e);
      }
    });
  }

  /**
   * Handles keyboard input events.
   *
   * @param e The keyboard event.
   */
  void handleKeyInput(KeyEvent e) {
    switch (e.getKeyCode()) {
      case KeyEvent.VK_L:
        executeCommand(COMMAND_LOOK);
        break;
      case KeyEvent.VK_M:
        view.showPrompt("Enter space name to move:", input -> {
          if (validateInput(input)) {
            executeCommand(COMMAND_MOVE, input);
          } else {
            view.displayMessage("Invalid input for move.");
          }
        });
        break;
      case KeyEvent.VK_P:
        executeCommand(COMMAND_PICKUP);
        break;
      default:
        view.displayMessage("Invalid key pressed.");
        break;
    }
  }

  /**
   * Handles mouse click events.
   *
   * @param e The mouse event.
   */
  void handleMouseClick(MouseEvent e) {
    String clickedSpace = view.getSpaceAt(e.getX(), e.getY());
    if (validateInput(clickedSpace)) {
      executeCommand(COMMAND_MOVE, clickedSpace);
    } else {
      view.displayMessage("Invalid space clicked.");
    }
  }

  /**
   * Executes a command with the given key.
   *
   * @param commandKey The command key.
   */
  void executeCommand(String commandKey) {
    Command command = commands.get(commandKey);
    if (command != null) {
      try {
        command.execute();
      } catch (Exception ex) {
        view.displayMessage("Error executing command: " + ex.getMessage());
      }
    } else {
      view.displayMessage("Invalid command.");
    }
  }

  /**
   * Executes a command with additional input.
   *
   * @param commandKey The command key.
   * @param input      The additional input.
   */
  private void executeCommand(String commandKey, String input) {
    Command command = commands.get(commandKey);
    if (command != null) {
      try {
        command.execute(input);
      } catch (Exception ex) {
        view.displayMessage("Error executing command: " + ex.getMessage());
      }
    } else {
      view.displayMessage("Invalid command.");
    }
  }

  /**
   * Validates user input.
   *
   * @param input The user input.
   * @return True if input is valid, false otherwise.
   */
  private boolean validateInput(String input) {
    return input != null && !input.trim().isEmpty();
  }

  @Override
  public void start() {
    System.out.println("Starting game...");
    view.showWelcomeScreen();
  }

  @Override
  public boolean isGameOver() {
    return model.isGameOver();
  }

  @Override
  public void movePlayerTo(int x, int y) {
    String spaceName = model.getSpaceNameAt(x, y);
    if (validateInput(spaceName)) {
      executeCommand(COMMAND_MOVE, spaceName);
    } else {
      view.displayMessage("Invalid move to coordinates: (" + x + ", " + y + ")");
    }
  }

  @Override
  public void movePlayerTo(String spaceName) {
    if (validateInput(spaceName)) {
      executeCommand(COMMAND_MOVE, spaceName);
    } else {
      view.displayMessage("Invalid space name.");
    }
  }

  @Override
  public void pickUpItem(String itemName) {
    if (validateInput(itemName)) {
      executeCommand(COMMAND_PICKUP, itemName);
    } else {
      view.displayMessage("Invalid item name.");
    }
  }

  @Override
  public void lookAround() {
    executeCommand(COMMAND_LOOK);
  }

  @Override
  public void attackPlayer(String targetName) {
    if (validateInput(targetName)) {
      model.attackPlayer(targetName);
      view.displayMessage("Attacked player: " + targetName);
    } else {
      view.displayMessage("Invalid player name.");
    }
  }

  @Override
  public void startNewGame(String worldConfigFile, int maxTurns) {
    if (validateInput(worldConfigFile) && maxTurns > 0) {
      System.out.println("Starting new game with config: "
          + worldConfigFile + " and max turns: " + maxTurns);
    } else {
      view.displayMessage("Invalid configuration for starting a new game.");
    }
  }

  @Override
  public void quitGame() {
    System.out.println("Game quit.");
    System.exit(0);
  }

  /**
   * Returns the map of available commands.
   *
   * @return A map of command keys to their respective commands.
   */
  public Map<String, Command> getCommands() {
    return this.commands;
  }
}
