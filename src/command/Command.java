package command;

/**
 * Command interface represents a player action in the game.
 */
public interface Command {

  /**
   * Executes the command.
   *
   * @throws Exception If execution fails.
   */
  void execute() throws Exception;

  /**
   * Executes the command with input (not supported).
   *
   * @param input The input string.
   * @throws UnsupportedOperationException if the method is not supported.
   */
  void execute(String input) throws UnsupportedOperationException;

  /**
   * Validates the command.
   *
   * @return True if the command is valid, otherwise false.
   */
  boolean isValid();

  /**
   * Provides a description of the command.
   *
   * @return The description of the command.
   */
  String getDescription();
}
