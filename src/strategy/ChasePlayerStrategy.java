package strategy;

import world.AIPlayer;
import world.Player;
import world.Space;
import world.World;

import java.util.List;
import java.util.Random;

/**
 * ChasePlayerStrategy implements a strategy where the target character moves towards the nearest player.
 */
public class ChasePlayerStrategy implements TargetStrategy, AIStrategy {
    private Random random;

    public ChasePlayerStrategy(Random random) {
        this.random = random;
    }

    public ChasePlayerStrategy() {
        this.random = new Random();
    }

    @Override
    public void moveTarget(Player target, World world) {
        move(target, world);
    }

    @Override
    public void decideAction(AIPlayer player, World world) {
        move(player, world);
    }

    public void move(Player chaser, World world) {
        Space currentSpace = chaser.getCurrentSpace();
        List<Space> neighbors = currentSpace.getNeighbors();
        Player closestPlayer = findClosestPlayer(chaser, world);

        if (closestPlayer == null) {
            System.out.println("No players to chase.");
            return;
        }

        Space targetSpace = closestPlayer.getCurrentSpace();
        Space nextSpace = findBestNeighbor(neighbors, targetSpace);

        if (nextSpace == null && !neighbors.isEmpty()) {
            nextSpace = neighbors.get(random.nextInt(neighbors.size()));
        }

        if (nextSpace != null) {
            chaser.move(nextSpace);
            System.out.println(chaser.getName() + " moved towards " + closestPlayer.getName() + " and entered " + nextSpace.getName() + ".");
        } else {
            System.out.println(chaser.getName() + " has no valid moves towards the player.");
        }
    }

    private Player findClosestPlayer(Player chaser, World world) {
        List<Player> players = world.getPlayers();
        Player closestPlayer = null;
        double closestDistance = Double.MAX_VALUE;

        for (Player player : players) {
            if (!player.equals(chaser)) {
                double distance = calculateDistance(chaser.getCurrentSpace(), player.getCurrentSpace());
                if (distance < closestDistance) {
                    closestDistance = distance;
                    closestPlayer = player;
                }
            }
        }
        return closestPlayer;
    }

    private Space findBestNeighbor(List<Space> neighbors, Space targetSpace) {
        Space bestNeighbor = null;
        double closestDistance = Double.MAX_VALUE;

        for (Space neighbor : neighbors) {
            double distance = calculateDistance(neighbor, targetSpace);
            if (distance < closestDistance) {
                closestDistance = distance;
                bestNeighbor = neighbor;
            }
        }
        return bestNeighbor;
    }

    private double calculateDistance(Space space1, Space space2) {
        if (space1.equals(space2)) {
            return 0;
        }
        return 1;  
    }
}
