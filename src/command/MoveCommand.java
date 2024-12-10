package command;

import view.View;
import world.Player;
import world.Space;
import world.WorldModel;

/**
 * MoveCommand allows a player to move to a specific space if it's valid.
 */
public class MoveCommand implements Command {

  private final Player player;
  private final Space targetSpace;

  /**
   * Initializes the MoveCommand with the player and target space.
   *
   * @param player      The player performing the move.
   * @param targetSpace The space the player will move to.
   */
  public MoveCommand(Player player, Space targetSpace) {
    if (player == null || targetSpace == null) {
      throw new IllegalArgumentException("Player and Target Space cannot be null");
    }
    this.player = player;
    this.targetSpace = targetSpace;
  }

  /**
   * Alternative constructor initializing MoveCommand with WorldModel and View.
   *
   * @param model The world model providing game context.
   * @param view  The view component providing user input.
   */
  public MoveCommand(WorldModel model, View view) {
    if (model == null || view == null) {
      throw new IllegalArgumentException("Model and View cannot be null.");
    }
    this.player = model.getCurrentPlayer();
    this.targetSpace = view.getSelectedSpace();
  }

  /**
   * Executes the move command, allowing the player to move to the target space
   * if it is a neighboring space.
   */
  @Override
  public void execute() {
    if (!isValid()) {
      System.out.println("Move not allowed: " + targetSpace.getName()
          + " is not a neighboring space or player state is invalid.");
      return;
    }

    player.move(targetSpace);
    System.out.println(player.getName() + " moved to " + targetSpace.getName());
  }

  /**
   * Executes the command with input (not applicable for MoveCommand).
   *
   * @param input The input string (ignored in this implementation).
   * @throws UnsupportedOperationException This command does not support input parameters.
   */
  @Override
  public void execute(String input) throws UnsupportedOperationException {
    throw new UnsupportedOperationException("MoveCommand does not support input parameters.");
  }

  /**
   * Returns a description of the move command.
   *
   * @return The command description.
   */
  @Override
  public String getDescription() {
    return "Move to " + targetSpace.getName();
  }

  /**
   * Checks if the move command is valid.
   *
   * @return True if the command is valid, false otherwise.
   */
  @Override
  public boolean isValid() {
    return player != null
        && targetSpace != null
        && player.getCurrentSpace() != null
        && player.getCurrentSpace().getNeighbors().contains(targetSpace);
  }
}
