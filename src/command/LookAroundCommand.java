package command;

import world.Player;
import world.Space;
import world.World;

import java.util.stream.Collectors;

/**
 * LookAroundCommand allows a player to observe their surroundings, considering the pet's effect on visibility.
 */
public class LookAroundCommand implements Command {
    private Player player;
    private World world;

    /**
     * Initializes the LookAroundCommand with the player and the world context.
     *
     * @param player The player executing the command.
     * @param world  The game world to interact with.
     */
    public LookAroundCommand(Player player, World world) {
        if (player == null || world == null) {
            throw new IllegalArgumentException("Player and World cannot be null");
        }
        this.player = player;
        this.world = world;
    }

    /**
     * Executes the look-around action, providing information about the current and neighboring spaces.
     */
    @Override
    public void execute() {
        // Defensive check
        if (player == null) {
            throw new IllegalStateException("Player cannot be null when executing command.");
        }

        Space currentSpace = player.getCurrentSpace();
        if (currentSpace == null) {
            throw new IllegalStateException("Player must be in a space to look around.");
        }

        // Output information about the current space
        System.out.println("You are currently in: " + currentSpace.getName());

        // Items in the current space
        if (currentSpace.getItems().isEmpty()) {
            System.out.println("There are no items in this space.");
        } else {
            System.out.println("Items in this space: " +
                    currentSpace.getItems().stream().map(item -> item.getName()).collect(Collectors.joining(", ")));
        }

        // Players in the current space (excluding the player themselves)
        String playersInSpace = currentSpace.getPlayers().stream()
                .filter(p -> !p.equals(player))
                .map(Player::getName)
                .collect(Collectors.joining(", "));

        if (playersInSpace.isEmpty()) {
            System.out.println("There are no other players in this space.");
        } else {
            System.out.println("Other players in this space: " + playersInSpace);
        }

        // Output information about neighboring spaces
        System.out.println("From here, you can see:");
        for (Space neighbor : currentSpace.getNeighbors()) {
            if (world.isVisible(neighbor)) {
                String neighborItems = neighbor.getItems().isEmpty() ? "no items" :
                        neighbor.getItems().stream().map(item -> item.getName()).collect(Collectors.joining(", "));

                String neighborPlayers = neighbor.getPlayers().isEmpty() ? "no players" :
                        neighbor.getPlayers().stream().map(Player::getName).collect(Collectors.joining(", "));

                System.out.println(" - " + neighbor.getName() + " with " + neighborItems + " and " + neighborPlayers);
            } else {
                System.out.println(" - " + neighbor.getName() + " is not visible.");
            }
        }
    }

    /**
     * Gets a description of the command.
     *
     * @return The command description.
     */
    @Override
    public String getDescription() {
        return "Look around your current space and its neighbors.";
    }

    /**
     * Validates whether the command can be executed.
     *
     * @return True if valid, otherwise false.
     */
    @Override
    public boolean isValid() {
        return player != null && player.getCurrentSpace() != null; // Command is valid if the player is in a space.
    }
}
