import java.awt.image.BufferedImage;

public class Obstacle {
    private Sprite sheet;
    private BufferedImage[] sprites;
    public Animation animation;

    public boolean isActive = false;
    public int positionX = 810;
    public int positionY;
    public int sizeX = 25;
    public int sizeY = 25;

    Obstacle(String enemy){
        if(enemy == "fish"){
            sheet = new Sprite("fish");
            sprites = sheet.getSprites(2, 15, 16);
            animation = new Animation(sprites, 5);
            animation.start();
            positionY = 120;
        } else {
            sheet = new Sprite("goomba");
            sprites = sheet.getSprites(2, 16, 16);
            animation = new Animation(sprites, 5);
            animation.start();
            positionY = 215;
        }
    }

    void startMoving(int speed){
        if(isActive){
            positionX = positionX - speed;
        }
    }

    void stopMoving(){
        isActive = false;
    }


    int[] getPosition(){
        int[] position = new int[]{positionX, positionY, 16, 16};
        return position;
    }
}