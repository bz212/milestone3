package command;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.anyInt;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.junit.Before;
import org.junit.Test;
import world.Item;
import world.Player;
import world.Space;

/**
 * Test class for AttemptAttackCommand.
 */
public class AttemptAttackCommandTest {

  private Player mockAttacker;
  private Player mockTarget;
  private Item mockWeapon;
  private Space mockSpace;
  private AttemptAttackCommand attackCommand;

  /**
   * Sets up the test environment before each test.
   */
  @Before
  public void setUp() {
    mockAttacker = mock(Player.class);
    mockTarget = mock(Player.class);
    mockWeapon = mock(Item.class);
    mockSpace = mock(Space.class);

    when(mockAttacker.getName()).thenReturn("Attacker");
    when(mockTarget.getName()).thenReturn("Target");
    when(mockWeapon.getName()).thenReturn("Sword");
    when(mockWeapon.getDamage()).thenReturn(10);
    when(mockAttacker.getCurrentSpace()).thenReturn(mockSpace);
    when(mockTarget.getCurrentSpace()).thenReturn(mockSpace);
    when(mockAttacker.getHealth()).thenReturn(50);
    when(mockTarget.getHealth()).thenReturn(30);

    attackCommand = new AttemptAttackCommand(mockAttacker, mockTarget, mockWeapon);
  }

  /**
   * Tests a valid attack using a weapon.
   */
  @Test
  public void testValidAttack_WithWeapon() {
    assertTrue(attackCommand.isValid());
    attackCommand.execute();

    verify(mockTarget).reduceHealth(10);
    verify(mockAttacker.getInventory()).removeItem(mockWeapon);
  }

  /**
   * Tests a valid attack without a weapon.
   */
  @Test
  public void testValidAttack_NoWeapon() {
    attackCommand = new AttemptAttackCommand(mockAttacker, mockTarget);

    assertTrue(attackCommand.isValid());
    attackCommand.execute();

    verify(mockTarget).reduceHealth(1);
    verify(mockAttacker.getInventory(), never()).removeItem(any());
  }

  /**
   * Tests an attack that defeats the target.
   */
  @Test
  public void testAttack_TargetDefeated() {
    when(mockTarget.getHealth()).thenReturn(1);

    assertTrue(attackCommand.isValid());
    attackCommand.execute();

    verify(mockTarget).reduceHealth(1);
  }

  /**
   * Tests an invalid attack when the players are in different spaces.
   */
  @Test
  public void testInvalidAttack_DifferentSpaces() {
    when(mockTarget.getCurrentSpace()).thenReturn(mock(Space.class));

    assertFalse(attackCommand.isValid());
    attackCommand.execute();

    verify(mockTarget, never()).reduceHealth(anyInt());
  }

  /**
   * Tests an invalid attack when the attacker is dead.
   */
  @Test
  public void testInvalidAttack_AttackerDead() {
    when(mockAttacker.getHealth()).thenReturn(0);

    assertFalse(attackCommand.isValid());
    attackCommand.execute();

    verify(mockTarget, never()).reduceHealth(anyInt());
  }

  /**
   * Tests an invalid attack when the target is dead.
   */
  @Test
  public void testInvalidAttack_TargetDead() {
    when(mockTarget.getHealth()).thenReturn(0);

    assertFalse(attackCommand.isValid());
    attackCommand.execute();

    verify(mockTarget, never()).reduceHealth(anyInt());
  }

  /**
   * Tests the description of the attack command.
   */
  @Test
  public void testGetDescription() {
    assertEquals("Attempt an attack on the target.", attackCommand.getDescription());
  }

  /**
   * Tests executing the attack command with invalid input.
   */
  @Test(expected = UnsupportedOperationException.class)
  public void testExecute_WithInput() {
    attackCommand.execute("Invalid Input");
  }

  /**
   * Tests the constructor when the attacker is null.
   */
  @Test(expected = IllegalArgumentException.class)
  public void testConstructor_NullAttacker() {
    new AttemptAttackCommand(null, mockTarget, mockWeapon);
  }

  /**
   * Tests the constructor when the target is null.
   */
  @Test(expected = IllegalArgumentException.class)
  public void testConstructor_NullTarget() {
    new AttemptAttackCommand(mockAttacker, null, mockWeapon);
  }
}
