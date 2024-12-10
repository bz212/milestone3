package view;

import java.awt.BorderLayout;
import java.awt.Font;
import java.awt.event.ActionEvent;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;

/**
 * AboutScreen class displays a welcome message, developer credits,
 * and provides options to start the game or exit.
 */
public class AboutScreen extends JFrame {

  private final Runnable startGameCallback;

  /**
   * Constructor for AboutScreen.
   *
   * @param startGameCallback A callback function to start the game.
   */
  public AboutScreen(Runnable startGameCallback) {
    this.startGameCallback = startGameCallback;

    setTitle("Welcome to NBA Heist");
    setSize(600, 400); // Adjusted size for better layout
    setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    setLocationRelativeTo(null);
    setLayout(new BorderLayout());

    // Welcome message
    JLabel welcomeLabel = new JLabel(
        "<html><h1>Welcome to NBA Heist</h1></html>", 
        SwingConstants.CENTER
    );
    welcomeLabel.setFont(new Font("Arial", Font.BOLD, 24));

    // Developer credits
    JLabel textLabel = new JLabel(
        "<html><p>Designed by Bowen Z.</p></html>", 
        SwingConstants.CENTER
    );
    textLabel.setFont(new Font("Arial", Font.PLAIN, 16));

    JPanel creditPanel = new JPanel();
    creditPanel.setLayout(new javax.swing.BoxLayout(creditPanel, javax.swing.BoxLayout.Y_AXIS));
    creditPanel.add(textLabel);

    // Buttons
    JButton startGameButton = new JButton("Start New Game");
    JButton quitButton = new JButton("Quit Game");
    startGameButton.setFocusPainted(false);
    quitButton.setFocusPainted(false);

    // Button panel
    JPanel buttonPanel = new JPanel();
    buttonPanel.add(startGameButton);
    buttonPanel.add(quitButton);

    // Adding components to main frame
    add(welcomeLabel, BorderLayout.NORTH);
    add(new JScrollPane(creditPanel), BorderLayout.CENTER); // Add scrolling for credits
    add(buttonPanel, BorderLayout.SOUTH);

    // Button action listeners
    startGameButton.addActionListener(this::handleStartGame);
    quitButton.addActionListener(e -> System.exit(0));
  }

  /**
   * Handles the "Start New Game" button click.
   *
   * @param e The action event.
   */
  private void handleStartGame(ActionEvent e) {
    setVisible(false); // Hide AboutScreen
    startGameCallback.run(); // Call the callback to start the game
  }

  /**
   * Main method for standalone testing.
   *
   * @param args Command-line arguments.
   */
  public static void main(String[] args) {
    SwingUtilities.invokeLater(() -> 
        new AboutScreen(() -> System.out.println("Game starting..."))
            .setVisible(true)
    );
  }
}
