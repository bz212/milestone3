package command;

import world.Player;
import world.Item;
import world.Space;

/**
 * PickUpItemCommand allows a player to pick up an item from their current space.
 */
public class PickUpItemCommand implements Command {
    private Player player;
    private Item item;

    /**
     * Initializes the PickUpItemCommand with the player and the item to be picked up.
     *
     * @param player The player picking up the item.
     * @param item   The item to be picked up.
     */
    public PickUpItemCommand(Player player, Item item) {
        this.player = player;
        this.item = item;
    }

    /**
     * Executes the pick-up action, allowing the player to pick up the item if it's present in the current space.
     */
    @Override
    public void execute() {
        Space currentSpace = player.getCurrentSpace();
        if (currentSpace.getItems().contains(item)) {
            player.pickUpItem(item);
            currentSpace.removeItem(item);
            System.out.println(player.getName() + " picked up " + item.getName());
        } else {
            System.out.println("Item " + item.getName() + " is not available in the current space.");
        }
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
     * @return True if the player and item are valid and the item is in the current space, otherwise false.
     */
    @Override
    public boolean isValid() {
        return player != null && item != null && player.getCurrentSpace().getItems().contains(item);
    }
}
