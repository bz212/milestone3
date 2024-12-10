package view;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import org.junit.Before;
import org.junit.Test;

/**
 * Test class for AboutScreen.
 */

public class AboutScreenTest {

  private AboutScreen aboutScreen;
  private boolean startGameTriggered;

  /**
   * Sets up the test environment before each test.
   */
  @Before
  public void setUp() {
    startGameTriggered = false;
    aboutScreen = new AboutScreen(() -> startGameTriggered = true);
  }

  /**
   * Tests the initialization of the AboutScreen.
   */
  @Test
  public void testInitialization() {
    assertNotNull("AboutScreen should be initialized", aboutScreen);
    assertEquals("Title should match", "Welcome to NBA Heist", aboutScreen.getTitle());
    assertEquals("Width should be 400", 400, aboutScreen.getWidth());
    assertEquals("Height should be 300", 300, aboutScreen.getHeight());
    assertEquals("Default close operation should be EXIT_ON_CLOSE",
        JFrame.EXIT_ON_CLOSE, aboutScreen.getDefaultCloseOperation());
  }

  /**
   * Tests the welcome label on the AboutScreen.
   */
  @Test
  public void testWelcomeLabel() {
    JLabel welcomeLabel = (JLabel) aboutScreen.getContentPane().getComponent(0);
    assertNotNull("Welcome label should not be null", welcomeLabel);
    assertEquals("Label alignment should be CENTER",
        SwingConstants.CENTER, welcomeLabel.getHorizontalAlignment());
    assertTrue("Welcome label text should contain 'Welcome to NBA Heist'",
        welcomeLabel.getText().contains("Welcome to NBA Heist"));
  }

  /**
   * Tests the credit panel on the AboutScreen.
   */
  @Test
  public void testCreditPanel() {
    JPanel creditPanel = (JPanel) aboutScreen.getContentPane().getComponent(1);
    assertNotNull("Credit panel should not be null", creditPanel);
    assertEquals("Credit panel layout should be BoxLayout",
        BoxLayout.class, creditPanel.getLayout().getClass());

    JLabel textLabel = (JLabel) creditPanel.getComponent(0);
    assertNotNull("Credit panel label should not be null", textLabel);
    assertTrue("Credit panel label text should contain 'Designed by'",
        textLabel.getText().contains("Designed by"));
  }

  /**
   * Tests the Start Game button functionality.
   */
  @Test
  public void testStartGameButton() {
    JPanel buttonPanel = (JPanel) aboutScreen.getContentPane().getComponent(2);
    JButton startGameButton = (JButton) buttonPanel.getComponent(0);
    assertNotNull("Start Game button should not be null", startGameButton);
    assertEquals("Button text should match", "Start New Game", startGameButton.getText());

    // Simulate button click
    startGameButton.doClick();
    assertTrue("Start game action should be triggered", startGameTriggered);
  }

  /**
   * Tests the Quit Game button functionality.
   */
  @Test
  public void testQuitButton() {
    JPanel buttonPanel = (JPanel) aboutScreen.getContentPane().getComponent(2);
    JButton quitButton = (JButton) buttonPanel.getComponent(1);
    assertNotNull("Quit Game button should not be null", quitButton);
    assertEquals("Button text should match", "Quit Game", quitButton.getText());

    // Simulate button click (mock or inspect behavior as needed)
    quitButton.doClick();
    // If System.exit mocking is available, validate it was called here.
  }
}
