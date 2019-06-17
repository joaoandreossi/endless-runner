import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JPanel;

public class Player extends JPanel {

    private int posX;
    private int posY;
    private int width;
    private int height;
    private int id;

    private Sprite a = new Sprite("walking");
    private Sprite c = new Sprite("jumping");
    private BufferedImage[] walkSprites = a.getSprites(10, 14, 32);
    private BufferedImage[] jumpSprites = c.getSprites(17, 20, 31);
    private Animation walking = new Animation(walkSprites, 2);
    private Animation jumping = new Animation(jumpSprites, 2);
    private Animation animation = walking;

    private boolean isGrounded = true;
    private boolean isWalking = false;

    private double horizontalVelocity = 0;
    private double verticalVelocity = 25;
    private double gravity = -2;

    private Layer floor = new Layer("floor", 0, 240);
    private Layer sky = new Layer("sky", 0, 0);
    private Layer cloud = new Layer("cloud", 0, 50);
    private Layer mountain = new Layer("mountain", 0, 205);

    private List<Obstacle> obstacles = new ArrayList<Obstacle>();

    private int cooldown;
    private boolean isCooldownOver = true;

    private Button gButton = new Button("goomba_button");
    private Button fButton = new Button("fish_button");

    private int pontos = 0;
    private int frameTime = 0;

    public boolean isFinished = false;

    protected Player(int x, int y, int w, int h, int identifier) {
        posX = x;
        posY = y;
        width = w;
        height = h;
        id = identifier;
        animation.start();
    }

    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.drawImage(sky.layerImage(), 0, 0, null);
        if(id == 1){
            g.drawString("Pontos:   " + pontos, 25, 25);
            endgame(id, g);
        }
        if(id == 2){
            g.drawImage(gButton.buttonImage(), 650, 20, 30, 30, null);
            g.drawImage(fButton.buttonImage(), 690, 20, 30, 30, null);
            endgame(id, g);
        }
        cloud.swapLayer(g, 1);
        mountain.swapLayer(g, 2);
        floor.swapLayer(g, 3);
        drawObstacle(g);
        g.drawImage(animation.getSprite(), posX, posY, width, height, null);
        
    }

    protected void createObstacle(String name){
        Obstacle ob = new Obstacle(name);
        ob.isActive = true;
        obstacles.add(ob);
    }

    protected void drawObstacle(Graphics g){
        for(Obstacle ob: obstacles){
            g.drawImage(ob.animation.getSprite(), ob.positionX, ob.positionY, ob.sizeX, ob.sizeY, null);
            ob.startMoving(4);
        }
    }

    protected void updateObstacle(){
        if(!obstacles.isEmpty()){
            for(Obstacle ob: obstacles){
                ob.animation.update();
            }
        }
        removeObstacles();
    }

    protected void removeObstacles(){
        for(int i = 0; i < obstacles.size(); i++){
            if(obstacles.get(i).positionX < 0){
                obstacles.remove(i);
            }
        }
    }

    protected void stopObstacle(){
        if(!obstacles.isEmpty()){
            for(Obstacle ob: obstacles){
                ob.isActive = false;
            }
        }
    }

    protected void reset(){
        if(!obstacles.isEmpty()){
            for(Obstacle ob: obstacles){
                ob.positionX = -1;
            }
        }
        removeObstacles();
        pontos = 0;
        frameTime = 0;
        isFinished = false;
    }

    protected void endgame(int identifier, Graphics g){
        if(isFinished){
            stopObstacle();

            if(id == 1){
                g.drawString("Fim de jogo!", 350, 75);
                g.drawString("Aperte ENTER para continuar", 350, 95);
            } else {
                g.drawString("Fim de jogo!", 350, 75);
                g.drawString("Aperte ENTER para continuar", 350, 95);
            }
        }
    }

    protected void increasePoints(){
        if(frameTime == 60 && !isFinished){
            pontos = pontos + 10;
            frameTime = 0;
        }
        frameTime++;
    };

    protected void cooldown(){
        cooldown--;
        if(cooldown == 0){
            isCooldownOver = true;
            gButton.inCooldown = false;
            fButton.inCooldown = false;
        }
    }

    protected void move(int dir){
        isWalking = true;
        if(isGrounded){
            animation = walking;
            animation.start();
        }
        if(dir == 1){
            horizontalVelocity = 3;
            
        } else {
            horizontalVelocity = -3;
        }
    }

    protected void stopWalking(){
        isWalking = false;
    }

    protected void jump(){
        if(isGrounded){
            animation = jumping;
            animation.start();
            isGrounded = false;
        }
    }

    protected void stop(){
        if(isGrounded && !isWalking){
            animation = walking;
            animation.start();
            horizontalVelocity = 0;
        }
    }

    protected void changeAnimation(KeyEvent e){
        switch(e.getKeyCode()){
            case KeyEvent.VK_W:
                if(!isFinished){
                    jump();
                }
                break;
            case KeyEvent.VK_NUMPAD1:
                if(isCooldownOver && !isFinished){
                    isCooldownOver = false;
                    cooldown = 30;
                    gButton.inCooldown = true;
                    fButton.inCooldown = true;
                    createObstacle("goomba");
                }
                break;
            case KeyEvent.VK_NUMPAD2:
                if(isCooldownOver && !isFinished){
                    isCooldownOver = false;
                    cooldown = 30;
                    gButton.inCooldown = true;
                    fButton.inCooldown = true;
                    createObstacle("fish");
                }
                break;
            case KeyEvent.VK_ENTER:
                if(isFinished){
                    reset();
                }
                break;
            default:
                break;
        }
    }

    protected void update(double delta){
        animation.update();
        updateObstacle();
        cooldown();
        increasePoints();
        if(!isGrounded){
            posX += horizontalVelocity*delta;
            posY -= verticalVelocity*delta;
            verticalVelocity += gravity;
            if(posY > 180){
                posY = 180;
                isGrounded = true;
                verticalVelocity = 25;
                if(isWalking){
                    animation = walking;
                    animation.restart();
                }
                stop();
            }
        }
        posX += horizontalVelocity*delta;
    }

    protected List<Integer> objectPosition(){
        List<Integer> position = new ArrayList<Integer>();
        position.add(0, posX);
        position.add(1, posY);
        position.add(2, width);
        position.add(3, height);
        if(!obstacles.isEmpty()){
            position.add(4, obstacles.get(0).positionX);
            position.add(5, obstacles.get(0).positionY);
            position.add(6, obstacles.get(0).sizeX);
            position.add(7, obstacles.get(0).sizeX);
            position.add(8, -100);
        } else {
            position.add(4, -100);
        }
        return position;
    }
}