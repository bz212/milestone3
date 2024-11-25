package command;

import world.Player;
import world.Space;
import world.World;
import java.util.List;

/**
 * MoveCommand allows a player to move to a specific space if it's valid.
 */
public class MoveCommand implements Command {
    private Player player;
    private Space targetSpace;

    /**
     * Initializes the MoveCommand with the player and target space.
     *
     * @param player The player performing the move.
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
     * Executes the move command, allowing the player to move to the target space if it is a neighbor.
     */
    @Override
    public void execute() {
        Space currentSpace = player.getCurrentSpace();
        if (currentSpace != null && currentSpace.getNeighbors().contains(targetSpace)) {
            player.move(targetSpace);
            System.out.println(player.getName() + " moved to " + targetSpace.getName());
        } else {
            System.out.println("Move not allowed: " + targetSpace.getName() + " is not a neighboring space.");
        }
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
        return player != null && targetSpace != null && player.getCurrentSpace() != null && player.getCurrentSpace().getNeighbors().contains(targetSpace);
    }
}
