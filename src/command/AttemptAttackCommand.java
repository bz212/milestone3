package command;

import world.Item;
import world.Player;

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

  /**
   * Initializes the AttemptAttackCommand with no weapon.
   *
   * @param attacker The player attempting the attack.
   * @param target   The target character.
   */
  public AttemptAttackCommand(Player attacker, Player target) {
    this(attacker, target, null); // Use default weapon (null).
  }

  /**
   * Executes the attack attempt.
   */
  @Override
  public void execute() {
    if (!isValid()) {
      System.out.println("Invalid attack command.");
      return;
    }

    StringBuilder result = new StringBuilder();
    int damage = (weapon != null) ? weapon.getDamage() : 1; // Default damage is 1 if no weapon.
    target.reduceHealth(damage);

    result.append(attacker.getName())
          .append(" attacked ")
          .append(target.getName())
          .append(" for ")
          .append(damage)
          .append(" damage.");

    if (weapon != null) {
      attacker.getInventory().removeItem(weapon);
      result.append(" ")
            .append(weapon.getName())
            .append(" was removed from play as evidence.");
    }

    if (target.getHealth() <= 0) {
      result.append(" ").append(target.getName()).append(" has been defeated!");
    }

    System.out.println(result.toString());
  }

  /**
   * Executes the attack attempt with input (not supported).
   *
   * @param input The input string.
   * @throws UnsupportedOperationException if the method is not supported.
   */
  @Override
  public void execute(String input) throws UnsupportedOperationException {
    throw new UnsupportedOperationException("This command does not support string input.");
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
    if (attacker == null || target == null) {
      return false;
    }
    if (attacker.getHealth() <= 0 || target.getHealth() <= 0) {
      return false; // Dead players cannot attack or be attacked.
    }
    if (attacker.getCurrentSpace() != target.getCurrentSpace()) {
      return false; // Players must be in the same space.
    }
    return true;
  }
}
