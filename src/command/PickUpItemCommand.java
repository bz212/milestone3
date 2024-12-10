package command;

import view.View;
import world.Item;
import world.Player;
import world.Space;
import world.WorldModel;

/**
 * PickUpItemCommand allows a player to pick up an item from their current space.
 */
public class PickUpItemCommand implements Command {

  private final Player player;
  private final Item item;

  /**
   * Initializes the PickUpItemCommand with the player and the item to be picked up.
   *
   * @param player The player picking up the item.
   * @param item   The item to be picked up.
   */
  public PickUpItemCommand(Player player, Item item) {
    if (player == null || item == null) {
      throw new IllegalArgumentException("Player and Item cannot be null.");
    }
    this.player = player;
    this.item = item;
  }

  /**
   * Alternative constructor initializing PickUpItemCommand with WorldModel and View.
   *
   * @param model The world model providing game context.
   * @param view  The view providing user input.
   */
  public PickUpItemCommand(WorldModel model, View view) {
    if (model == null || view == null) {
      throw new IllegalArgumentException("Model and View cannot be null.");
    }
    this.player = model.getCurrentPlayer();
    this.item = view.getSelectedItem();
  }

  /**
   * Executes the pick-up action, allowing the player to pick up the item
   * if it's present in the current space.
   */
  @Override
  public void execute() {
    if (!isValid()) {
      System.out.println(
          "Pick-up action is invalid. Item is not available in the current space.");
      return;
    }

    Space currentSpace = player.getCurrentSpace();
    player.pickUpItem(item);
    currentSpace.removeItem(item);

    System.out.println(player.getName() + " picked up " + item.getName());
  }

  /**
   * Executes the command with input (not applicable for PickUpItemCommand).
   *
   * @param input The input string (ignored in this implementation).
   * @throws UnsupportedOperationException This command does not support input parameters.
   */
  @Override
  public void execute(String input) throws UnsupportedOperationException {
    throw new UnsupportedOperationException(
        "PickUpItemCommand does not support input parameters.");
  }

  /**
   * Gets a description of the command.
   *
   * @return The command description.
   */
  @Override
  public String getDescription() {
    return "Pick up " + item.getName() + " from the current space.";
  }

  /**
   * Validates whether the command can be executed.
   *
   * @return True if the player and item are valid and the item is in the current space,
   *         otherwise false.
   */
  @Override
  public boolean isValid() {
    return player != null
        && item != null
        && player.getCurrentSpace() != null
        && player.getCurrentSpace().getItems().contains(item);
  }
}
