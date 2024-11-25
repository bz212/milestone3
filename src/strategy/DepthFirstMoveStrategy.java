package strategy;

import world.Pet;
import world.Player;
import world.Space;
import world.World;

import java.util.HashSet;
import java.util.Set;
import java.util.Stack;

/**
 * DepthFirstMoveStrategy implements a depth-first traversal strategy for moving the pet in the game.
 */
public class DepthFirstMoveStrategy implements MoveStrategy, TargetStrategy {
    private Set<Space> visited;
    private Stack<Space> stack;

    /**
     * Initializes the DepthFirstMoveStrategy with necessary data structures.
     */
    public DepthFirstMoveStrategy() {
        this.visited = new HashSet<>();
        this.stack = new Stack<>();
    }

    /**
     * Moves the pet in a depth-first manner to traverse the game world.
     *
     * @param pet   The pet to be moved.
     * @param world The game world context.
     */
    @Override
    public void movePet(Pet pet, World world) {
        Space currentSpace = pet.getCurrentSpace();

        // If the stack is empty, initialize it with the current space
        if (stack.isEmpty()) {
            stack.push(currentSpace);
        }

        // Mark the current space as visited
        visited.add(currentSpace);

        // Get the next unvisited neighbor
        Space nextSpace = getNextUnvisitedNeighbor(currentSpace);
        if (nextSpace != null) {
            // Move to the next unvisited neighbor
            pet.moveTo(nextSpace);
            stack.push(nextSpace);
            System.out.println(pet.toString() + " moved to " + nextSpace.getName() + " using depth-first traversal.");
        } else {
            // Backtrack if no unvisited neighbors are available
            stack.pop();
            if (!stack.isEmpty()) {
                Space backtrackSpace = stack.peek();
                pet.moveTo(backtrackSpace);
                System.out.println(pet.toString() + " backtracked to " + backtrackSpace.getName() + ".");
            }
        }
    }

    /**
     * Gets the next unvisited neighbor of the given space.
     *
     * @param space The current space.
     * @return The next unvisited neighbor, or null if all neighbors are visited.
     */
    private Space getNextUnvisitedNeighbor(Space space) {
        for (Space neighbor : space.getNeighbors()) {
            if (!visited.contains(neighbor)) {
                return neighbor;
            }
        }
        return null;
    }

    @Override
    public void moveTarget(Player target, World world) {
      // TODO Auto-generated method stub
      
    }

    @Override
    public void move(Player player, World world) {
      // TODO Auto-generated method stub
      
    }

    @Override
    public void move(Pet pet, World world) {
      // TODO Auto-generated method stub
      
    }
}
