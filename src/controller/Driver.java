package controller;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

import strategy.DepthFirstMoveStrategy;
import strategy.RandomMoveStrategy;
import strategy.TargetStrategy;
import world.HumanPlayer;
import world.Pet;
import world.Player;
import world.Space;
import world.World;

/**
 * Driver class for starting the game and initializing the world.
 */
public class Driver {
    public static void main(String[] args) {
        // Initialize the world
        World world = new World(new ArrayList<>(), new ArrayList<>(), null, null, null);

        // Load world configuration from file
        String fileName = "res/world-file.txt";
        try (BufferedReader br = new BufferedReader(new FileReader(fileName))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] parts = line.split(" ");
                switch (parts[0].toLowerCase()) {
                    case "space":
                        // Format: space <name>
                        String spaceName = parts[1];
                        Space space = new Space(spaceName, world);
                        world.getSpaces().add(space);
                        break;
                    case "player":
                        // Format: player <name> <health> <spaceName>
                        String playerName = parts[1];
                        int health = Integer.parseInt(parts[2]);
                        String playerSpaceName = parts[3];
                        Space playerSpace = world.getSpaces().stream()
                                .filter(s -> s.getName().equalsIgnoreCase(playerSpaceName))
                                .findFirst()
                                .orElse(null);
                        if (playerSpace != null) {
                            Player player = new HumanPlayer(playerName, health, playerSpace);
                            world.getPlayers().add(player);
                        }
                        break;
                    case "pet":
                        // Format: pet <name> <spaceName>
                        String petName = parts[1];
                        String petSpaceName = parts[2];
                        Space petSpace = world.getSpaces().stream()
                                .filter(s -> s.getName().equalsIgnoreCase(petSpaceName))
                                .findFirst()
                                .orElse(null);
                        if (petSpace != null) {
                            Pet pet = new Pet(petName, petSpace, new DepthFirstMoveStrategy());
                            pet.setWorld(world);
                            world.setPet(pet);
                        }
                        break;
                    case "neighbor":
                        // Format: neighbor <space1> <space2>
                        String space1Name = parts[1];
                        String space2Name = parts[2];
                        Space space1 = world.getSpaces().stream()
                                .filter(s -> s.getName().equalsIgnoreCase(space1Name))
                                .findFirst()
                                .orElse(null);
                        Space space2 = world.getSpaces().stream()
                                .filter(s -> s.getName().equalsIgnoreCase(space2Name))
                                .findFirst()
                                .orElse(null);
                        if (space1 != null && space2 != null) {
                            space1.addNeighbor(space2);
                        }
                        break;
                    default:
                        System.err.println("Unknown configuration line: " + line);
                }
            }
        } catch (IOException e) {
            System.err.println("Error loading world configuration: " + e.getMessage());
            return;
        }

        // Set target strategy
        TargetStrategy strategy = new RandomMoveStrategy();
        world.setStrategy(strategy);

        // Initialize controller and start the game
        Controller controller = new Controller(world.getPlayers(), world);
        controller.startGame();
    }
}
