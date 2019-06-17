import java.awt.Image;
import java.awt.image.BufferedImage;

public class Button {
    Sprite buttonSprite;
    BufferedImage[] sprites;
    boolean inCooldown = false;

    Button(String file){
        if(file == "goomba_button"){
            buttonSprite = new Sprite("goomba_button");
        } else {
            buttonSprite = new Sprite("fish_button");
        }
        loadImage();
    }

    void loadImage(){
        sprites = buttonSprite.getSprites(2, 30, 30);
    }

    BufferedImage buttonImage(){
        if(inCooldown){
            return sprites[1];    
        }
        return sprites[0];
    }
}