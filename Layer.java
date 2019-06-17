import java.awt.Graphics;
import java.awt.Image;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

public class Layer{

    private Image layer;
    private Image layerSwap;
    private int positionX;
    private int positionY;
    private int swapPositionX = 1000;
    
    Layer(String file, int posX, int posY){
        positionX = posX;
        positionY = posY;
        setLayer(file);
    }

    protected void setLayer(String file){
        try {
            layer = ImageIO.read(new File("sheets/" + file + ".png"));
            layerSwap = layer;
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    protected void swapLayer(Graphics g, int speed){
        g.drawImage(layerImage(), positionX, positionY, null);
        g.drawImage(swapImage(), swapPositionX, positionY, null);
        positionX = positionX - speed;
        swapPositionX = swapPositionX - speed;
        if(positionX < (-1000)) positionX = swapPositionX + 1000;
        if(swapPositionX < (-1000)) swapPositionX = positionX + 1000;
    }

    protected Image layerImage(){
        return layer;
    }

    protected Image swapImage(){
        return layerSwap;
    }

}