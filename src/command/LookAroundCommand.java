package command;

import java.util.stream.Collectors;

import view.View;
import world.Player;
import world.Space;
import world.World;
import world.WorldModel;

/**
 * LookAroundCommand allows a player to observe their surroundings,
 * considering the pet's effect on visibility.
 */
public class LookAroundCommand implements Command {

  private final Player player;
  private final World world;

  /**
   * Initializes the LookAroundCommand with the player and the world context.
   *
   * @param player The player executing the command.
   * @param world  The game world to interact with.
   */
  public LookAroundCommand(Player player, World world) {
    if (player == null || world == null) {
      throw new IllegalArgumentException("Player and World cannot be null.");
    }
    this.player = player;
    this.world = world;
  }

  /**
   * Alternative constructor initializing LookAroundCommand with WorldModel and View.
   *
   * @param model The world model providing game context.
   * @param view  The view component for display purposes.
   */
  public LookAroundCommand(WorldModel model, View view) {
    if (model == null || view == null) {
      throw new IllegalArgumentException("Model and View cannot be null.");
    }
    this.player = model.getCurrentPlayer();
    this.world = (World) model;
  }

  /**
   * Executes the look-around action, providing information about the current
   * and neighboring spaces.
   */
  @Override
  public void execute() {
    validateState();
    System.out.println(generateLookAroundDetails());
  }

  /**
   * Executes the command with input (not applicable for LookAroundCommand).
   *
   * @param input The input string (ignored in this implementation).
   * @throws UnsupportedOperationException This command does not require input.
   */
  @Override
  public void execute(String input) {
    throw new UnsupportedOperationException(
        "LookAroundCommand does not support input parameters.");
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
    return player != null && player.getCurrentSpace() != null && world != null;
  }

  /**
   * Validates the state before execution.
   */
  private void validateState() {
    if (player == null) {
      throw new IllegalStateException("Player cannot be null when executing command.");
    }
    if (player.getCurrentSpace() == null) {
      throw new IllegalStateException(
          "Player must be in a space to look around. Player: " + player.getName());
    }
  }

  /**
   * Generates the details of the current and neighboring spaces.
   *
   * @return A string representing the look-around details.
   */
  private String generateLookAroundDetails() {
    StringBuilder result = new StringBuilder();

    Space currentSpace = player.getCurrentSpace();
    result.append("You are currently in: ")
          .append(currentSpace.getName())
          .append("\n");

    if (currentSpace.getItems().isEmpty()) {
      result.append("There are no items in this space.\n");
    } else {
      result.append("Items in this space: ")
            .append(currentSpace.getItems().stream()
                    .map(item -> item.getName())
                    .collect(Collectors.joining(", ")))
            .append("\n");
    }

    String playersInSpace = currentSpace.getPlayers().stream()
        .filter(p -> !p.equals(player))
        .map(Player::getName)
        .collect(Collectors.joining(", "));
    result.append(playersInSpace.isEmpty()
        ? "There are no other players in this space.\n"
        : "Other players in this space: " + playersInSpace + "\n");

    result.append("From here, you can see:\n");
    for (Space neighbor : currentSpace.getNeighbors()) {
      if (world.isVisible(neighbor)) {
        String neighborItems = neighbor.getItems().isEmpty()
            ? "no items"
            : neighbor.getItems().stream()
                      .map(item -> item.getName())
                      .collect(Collectors.joining(", "));
        String neighborPlayers = neighbor.getPlayers().isEmpty()
            ? "no players"
            : neighbor.getPlayers().stream()
                      .map(Player::getName)
                      .collect(Collectors.joining(", "));
        result.append(" - ").append(neighbor.getName())
              .append(" with ").append(neighborItems)
              .append(" and ").append(neighborPlayers).append("\n");
      } else {
        result.append(" - ").append(neighbor.getName()).append(" is not visible.\n");
      }
    }
    return result.toString();
  }
}
