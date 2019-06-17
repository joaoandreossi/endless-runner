import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;

public class Sprite {

    private BufferedImage sheet;

    public Sprite(String file){
        sheet = null;
        loadSheet(file);
    }
 
    private void loadSheet(String file){
        try {
            sheet = ImageIO.read(new File("sheets/" + file + ".png"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public BufferedImage[] getSprites(int spriteNumber, int xTile, int yTile){
        BufferedImage[] sprites = new BufferedImage[spriteNumber];

        for(int i = 0; i < spriteNumber; i++){
            sprites[i] = sheet.getSubimage(i*xTile, 0*yTile, xTile, yTile);
        }

        return sprites;
    }
}