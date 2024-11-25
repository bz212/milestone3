package renderer;

import world.Space;
import world.World;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.List;

/**
 * MapRenderer is responsible for rendering the world map and saving it as an image file.
 */
public class MapRenderer {
    private static final int SPACE_SIZE = 100;
    private static final int PADDING = 20;
    private World world;

    /**
     * Constructor to initialize MapRenderer with a World object.
     *
     * @param world The game world to be rendered.
     */
    public MapRenderer(World world) {
        this.world = world;
    }

    /**
     * Renders the world map and saves it as a PNG file.
     *
     * @param outputPath The path where the rendered image will be saved.
     */
    public String renderMap(String outputPath) {
        List<Space> spaces = world.getSpaces();
        int imageWidth = (int) Math.sqrt(spaces.size()) * (SPACE_SIZE + PADDING);
        int imageHeight = (int) Math.sqrt(spaces.size()) * (SPACE_SIZE + PADDING);

        BufferedImage image = new BufferedImage(imageWidth, imageHeight, BufferedImage.TYPE_INT_ARGB);
        Graphics2D graphics = image.createGraphics();

        // Set background color
        graphics.setColor(Color.WHITE);
        graphics.fillRect(0, 0, imageWidth, imageHeight);

        // Draw spaces
        int x = PADDING;
        int y = PADDING;
        StringBuilder mapDescription = new StringBuilder("World Map:\n");
        for (Space space : spaces) {
            graphics.setColor(Color.BLUE);
            graphics.fillRect(x, y, SPACE_SIZE, SPACE_SIZE);
            graphics.setColor(Color.BLACK);
            graphics.drawRect(x, y, SPACE_SIZE, SPACE_SIZE);
            graphics.drawString(space.getName(), x + 10, y + 20);

            // Append space details to the map description
            mapDescription.append(space.getName()).append(": Players - ")
                    .append(space.getPlayers()).append(", Items - ")
                    .append(space.getItems()).append("\n");

            x += SPACE_SIZE + PADDING;
            if (x >= imageWidth) {
                x = PADDING;
                y += SPACE_SIZE + PADDING;
            }
        }

        // Save the image
        try {
            ImageIO.write(image, "PNG", new File(outputPath));
            System.out.println("World map saved to " + outputPath);
        } catch (IOException e) {
            System.err.println("Failed to save world map: " + e.getMessage());
        }

        // Return the map description as a string for verification in tests
        return mapDescription.toString();
    }

    /**
     * Renders the world map and returns it as a string representation.
     *
     * @return The string representation of the world map.
     */
    public String renderMap() {
        List<Space> spaces = world.getSpaces();
        StringBuilder mapDescription = new StringBuilder("World Map:\n");

        // Construct map description with spaces, players, and items
        for (Space space : spaces) {
            mapDescription.append(space.getName()).append(": Players - ")
                    .append(space.getPlayers()).append(", Items - ")
                    .append(space.getItems()).append("\n");
        }

        return mapDescription.toString();
    }
}
