package Gui.SimulatorView;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;

public class SpriteSheetHelper {

    public BufferedImage[] createSpriteSheet(String spriteName, int columns, int rows, int marginX, int marginY) {
        BufferedImage[] sprite = null;

        int subImageCount = columns * rows;

        try {
            BufferedImage image = ImageIO.read(getClass().getResource(spriteName));
            sprite = new BufferedImage[subImageCount];

            int subImageWidth = image.getWidth() / columns - marginX * 2;
            int subImageHeight = image.getHeight() / rows - marginY * 2;
            if (false) {//debugging
                System.out.println("sprite sheet size: " + image.getWidth() + " x " + image.getHeight());
                System.out.println("subImage size: " + subImageWidth + " x " + subImageHeight);
                System.out.println("sprite sheet size calculated by subImage: " + (subImageWidth + (marginX * 2)) * columns + " x " + (subImageHeight + (marginY * 2)) * rows);
            }
            for (int i = 0; i < rows; i++) {
                for (int j = 0; j < columns; j++) {
                    if (i * 4 + j < subImageCount) {
//                        System.out.println("adding subImage " + (i * 4 + j + 1) + "/" + subImageCount + "(" + i + "," + j + ")");

                        sprite[i * 4 + j] = image.getSubimage((int) ((j * (subImageWidth + (marginX * 2))) + marginX), (int) ((i * (subImageHeight + (marginY * 2))) + marginY), (int) subImageWidth, (int) subImageHeight);

                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return sprite;
    }

    public BufferedImage[] createSpriteSheet(String spriteName, int columns, int marginX, int marginY) {
        return createSpriteSheet(spriteName, columns, columns, marginX, marginY);
    }

    public BufferedImage[] createSpriteSheet(String spriteName, int columns, int rows) {
        return createSpriteSheet(spriteName, columns, rows, 0, 0);
    }

    public BufferedImage[] createSpriteSheet(String spriteName, int columns) {
        return createSpriteSheet(spriteName, columns, columns, 0, 0);
    }
}
