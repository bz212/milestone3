package controller;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.List;
import java.util.Scanner;

import command.AttemptAttackCommand;
import command.Command;
import command.LookAroundCommand;
import command.MoveCommand;
import command.PickUpItemCommand;
import world.Item;
import world.Player;
import world.Space;
import world.World;

/**
 * Controller class manages user inputs and executes commands in the game.
 */
public class Controller {
    private List<Player> players;
    private World world;
    private Scanner scanner;

    /**
     * Initializes the Controller with the list of players and the game world.
     *
     * @param players The list of players in the game.
     * @param world   The game world.
     */
    public Controller(List<Player> players, World world) {
        this.players = players;
        this.world = world;
        this.scanner = new Scanner(System.in);
    }

    /**
     * Gets the list of players in the game.
     *
     * @return The list of players.
     */
    public List<Player> getPlayers() {
        return players;
    }

    /**
     * Adds a new player to the game.
     *
     * @param player The player to add.
     */
    public void addPlayer(Player player) {
        players.add(player);
    }

    /**
     * Executes the given command.
     *
     * @param command The command to execute.
     */
    public void executeCommand(Command command) {
        if (command.isValid()) {
            command.execute();
        }
    }

    /**
     * Starts the game loop, prompting players for commands and executing them.
     */
    public void startGame() {
        boolean gameRunning = true;

        while (gameRunning) {
            for (Player player : players) {
                System.out.println(player.getName() + ", it's your turn!");

                Command command = null;
                while (command == null) {
                    command = getCommandFromInput(player);

                    if (command != null && command.isValid()) {
                        executeCommand(command);
                    } else {
                        System.out.println("Invalid command. Try again.");
                        command = null; // Prompt again for valid command
                    }
                }

                if (isGameOver()) {
                    gameRunning = false;
                    break;
                }
            }
        }
        System.out.println("Game Over. Thanks for playing!");
    }

    /**
     * Gets a command from the player's input.
     *
     * @param player The player giving the command.
     * @return The command to be executed.
     */
    private Command getCommandFromInput(Player player) {
        System.out.println("Enter your command (look around, move [space], pick up [item], attack [player]): ");
        String input = scanner.nextLine().trim().toLowerCase();

        if (input.startsWith("look around")) {
            return new LookAroundCommand(player, world);
        } else if (input.startsWith("move")) {
            String[] parts = input.split(" ", 2);
            if (parts.length == 2) {
                Space targetSpace = world.getSpaces().stream()
                        .filter(space -> space.getName().equalsIgnoreCase(parts[1]))
                        .findFirst().orElse(null);
                if (targetSpace != null) {
                    return new MoveCommand(player, targetSpace);
                }
            }
        } else if (input.startsWith("pick up")) {
            String[] parts = input.split(" ", 3);
            if (parts.length == 3) {
                // Find the item in the current space
                Space currentSpace = player.getCurrentSpace();
                Item itemToPickUp = currentSpace.getItems().stream()
                        .filter(item -> item.getName().equalsIgnoreCase(parts[2]))
                        .findFirst()
                        .orElse(null);
                if (itemToPickUp != null) {
                    return new PickUpItemCommand(player, itemToPickUp);
                }
            }
        } else if (input.startsWith("attack")) {
            String[] parts = input.split(" ", 2);
            if (parts.length == 2) {
                Player targetPlayer = player.getCurrentSpace().getPlayers().stream()
                        .filter(p -> p.getName().equalsIgnoreCase(parts[1]) && !p.equals(player))
                        .findFirst().orElse(null);
                if (targetPlayer != null) {
                    return new AttemptAttackCommand(player, targetPlayer);
                }
            }
        }

        return null;
    }

    /**
     * Checks if the game is over.
     *
     * @return True if the game is over, otherwise false.
     */
    private boolean isGameOver() {
        // Placeholder implementation to determine if the game is over.
        // For example: check if all players' health are zero or a certain condition is met.
        for (Player player : players) {
            if (player.getHealth() > 0) {
                return false; // If at least one player is still alive, the game is not over.
            }
        }
        return true;
    }

    /**
     * Loads the game world configuration from a file.
     *
     * @param fileName The file path of the world configuration file.
     */
    public void loadWorldFromFile(String fileName) {
        try (BufferedReader br = new BufferedReader(new FileReader(fileName))) {
            String line;
            while ((line = br.readLine()) != null) {
                // Process each line to initialize spaces, players, items, etc.
                // This is a placeholder. Actual parsing logic should be implemented here
                System.out.println("Loaded line: " + line);
            }
        } catch (IOException e) {
            System.err.println("Error loading world configuration: " + e.getMessage());
        }
    }
}
