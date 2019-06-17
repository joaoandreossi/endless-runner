import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;

class Animation {

    private int frameTime;
    private int frameTimeCounter;
    private int currentFrame;
    private int animationDirection;
    private int totalFrames;
    private boolean stopped;
    private List<Frame> frames = new ArrayList<Frame>();

    public Animation(BufferedImage[] frames, int frameTime){
        for(int i = 0; i < frames.length; i++){
            addFrame(frames[i], frameTime);
        }

        this.frameTime = frameTime;
        this.frameTimeCounter = 0;
        this.currentFrame = 0;
        this.animationDirection = 1;
        this.totalFrames = this.frames.size();
        this.stopped = true;
    }

    private void addFrame(BufferedImage frame, int duration){
        if(duration <= 0){
            System.err.println("Invalid duration: " + duration);
            throw new RuntimeException("Invalid duration: " + duration);
        }

        frames.add(new Frame(frame, duration));
        currentFrame = 0;
    }

    public void start(){
        if(!stopped){
            return;
        }
        if(frames.size() == 0){
            return;
        }

        stopped = false;
    }

    public void stop(){
        if(frames.size() == 0){
            return;
        }

        stopped = true;
    }

    public void restart(){
        if(frames.size() == 0){
            return;
        }

        stopped = false;
        currentFrame = 0;
    }

    public void reset(){
        this.stopped = true;
        this.frameTimeCounter = 0;
        this.currentFrame = 0;
    }

    public BufferedImage getSprite(){
        return frames.get(currentFrame).getFrame();
    }

    public void update(){
        if(!stopped){
            frameTimeCounter++;
            
            if(frameTimeCounter > frameTime){
                frameTimeCounter = 0;
                currentFrame += animationDirection;

                if(currentFrame > totalFrames - 1){
                    currentFrame = 0;
                }
                else if(currentFrame < 0){
                    currentFrame = totalFrames - 1;
                }
            }
        }
    }
}