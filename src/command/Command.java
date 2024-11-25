package command;

/**
 * Command interface represents a player action in the game.
 */
public interface Command {
    /**
     * Executes the command.
     */
    void execute();

    /**
     * Provides a description of the command.
     *
     * @return The description of the command.
     */
    String getDescription();

    /**
     * Validates whether the command can be executed.
     *
     * @return True if the command is valid, false otherwise.
     */
    boolean isValid();
}
