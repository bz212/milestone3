package command;

import org.junit.Before;
import org.junit.Test;
import world.*;

import static org.junit.Assert.*;

import java.util.ArrayList;

/**
 * Test class for the AttemptAttackCommand class.
 */
public class AttemptAttackCommandTest {
    private World world;
    private Space space;
    private Player attacker;
    private Player target;
    private Item weapon;
    private AttemptAttackCommand command;

    @Before
    public void setUp() {
        // Initialize world and space
        world = new World(new ArrayList<>(), new ArrayList<>(), null, null, null);
        space = new Space("Battlefield", world);
        world.getSpaces().add(space);

        // Initialize players and weapon
        attacker = new HumanPlayer("Attacker", 100, space);
        target = new HumanPlayer("Target", 100, space);
        weapon = new Item("Sword", 20, "A sharp blade.");
        space.addPlayer(attacker);
        space.addPlayer(target);
        attacker.pickUpItem(weapon);

        // Set visibility to false to allow attack to succeed
        attacker.setCanSee(target, false); 

        // Initialize command
        command = new AttemptAttackCommand(attacker, target, weapon);
    }

    @Test
    public void testExecuteSuccessfulAttack() {
        // Execute the attack command
        command.execute();

        // Verify the target's health is reduced by the weapon's damage
        assertEquals(80, target.getHealth());
    }

    @Test
    public void testExecuteAttackWithoutWeapon() {
        // Remove weapon from attacker
        attacker.getInventory().remove(weapon);

        // Reinitialize the command without a weapon
        command = new AttemptAttackCommand(attacker, target);

        // Execute the attack command
        command.execute();

        // Verify the target's health is reduced by the default damage (1 point)
        assertEquals(99, target.getHealth());
    }

    @Test
    public void testExecuteAttackSeenByOtherPlayer() {
        // Add another player to witness the attack
        Player witness = new HumanPlayer("Witness", 100, space);
        space.addPlayer(witness);

        // Execute the attack command
        command.execute();

        // Verify the attack was stopped (target's health remains unchanged)
        assertEquals(100, target.getHealth());
    }


    @Test
    public void testExecuteAttackWhenTargetNotInSameSpace() {
        // Move target to another space
        Space otherSpace = new Space("Safe Room", world);
        world.getSpaces().add(otherSpace);
        target.move(otherSpace);

        // Execute the attack command
        command.execute();

        // Verify the attack did not occur (target's health remains unchanged)
        assertEquals(100, target.getHealth());
    }
}
