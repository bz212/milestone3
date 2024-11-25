package command;

import world.Player;
import world.Item;
import world.Space;

import java.util.List;

/**
 * AttemptAttackCommand allows a player to make an attempt on the target character's life.
 */
public class AttemptAttackCommand implements Command {
    private Player attacker;
    private Player target;
    private Item weapon;

    /**
     * Initializes the AttemptAttackCommand.
     *
     * @param attacker The player attempting the attack.
     * @param target   The target character.
     * @param weapon   The weapon used for the attack, can be null for default.
     */
    public AttemptAttackCommand(Player attacker, Player target, Item weapon) {
        this.attacker = attacker;
        this.target = target;
        this.weapon = weapon;
    }

    public AttemptAttackCommand(Player attacker, Player target) {
        this(attacker, target, null); // Use default weapon (null)
    }

    /**
     * Executes the attack attempt.
     */
    @Override
    public void execute() {
        // Ensure the attacker and target are in the same space
        if (attacker.getCurrentSpace() != target.getCurrentSpace()) {
            System.out.println("Attack attempt failed! " + target.getName() + " is not in the same space.");
            return;
        }

        // Check if there are other players in the same space who can see the attack
        Space currentSpace = attacker.getCurrentSpace();
        List<Player> playersInSpace = currentSpace.getPlayers();

        for (Player player : playersInSpace) {
            if (player != attacker && player != target) {
                System.out.println("Attack attempt failed! " + target.getName() + " was seen by " + player.getName() + ".");
                return;
            }
        }

        // Only execute if attacker cannot see the target (sneak attack)
        if (!attacker.canSee(target)) {
            int damage = (weapon != null) ? weapon.getDamage() : 1; // Default damage is 1 if no weapon
            target.reduceHealth(damage);

            System.out.println(attacker.getName() + " attacked " + target.getName() + " for " + damage + " damage.");

            // If a weapon is used, remove it from the attacker's inventory as evidence
            if (weapon != null) {
                attacker.getInventory().remove(weapon);
                System.out.println(weapon.getName() + " was removed from play as evidence.");
            }

            // Check if target is defeated
            if (target.getHealth() <= 0) {
                System.out.println(target.getName() + " has been defeated!");
            }
        } else {
            System.out.println("Attack attempt failed! " + target.getName() + " was seen.");
        }
    }

    /**
     * Returns the description of the command.
     *
     * @return Description string.
     */
    @Override
    public String getDescription() {
        return "Attempt an attack on the target.";
    }

    /**
     * Validates the command.
     *
     * @return True if valid, otherwise false.
     */
    @Override
    public boolean isValid() {
        return attacker != null && target != null;
    }
}
