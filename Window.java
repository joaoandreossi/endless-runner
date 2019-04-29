import java.awt.Dimension;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.HashSet;
import java.util.Set;

import javax.swing.JFrame;
import javax.swing.JPanel;

public class Window extends JPanel implements KeyListener {
    static private JFrame frame = new JFrame("Player");
    static private int frameW = 600;
    static private int frameH = 400;

    static private Player player = new Player(50, frameH - 100, 30, 50);
    
    static private long lastFpsTime = 0;
    static private int fps = 0;

    private final Set<Character> pressed = new HashSet<Character>();

    public Window() {
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.getContentPane().add(player);
        frame.addKeyListener(this);
        setFocusable(true);
        frame.pack();
        frame.setSize(new Dimension(frameW, frameH));
        frame.setLocationByPlatform(true);
        frame.setVisible(true);
        
    }

    @Override
    public void setFocusable(boolean focusable) {
        super.setFocusable(focusable);
    }

    @Override
    public void keyTyped(KeyEvent e) {
        
    }

    @Override
    public synchronized void keyPressed(KeyEvent e) {
        pressed.add(e.getKeyChar());
        player.changeAnimation(e);
    }

    @Override
    public synchronized void keyReleased(KeyEvent e) {
        pressed.remove(e.getKeyChar());
        if(pressed.size() == 0){
            player.stopWalking();
            player.stop();
        }
    }

    public static void gameLoop() {
        long lastLoopTime = System.nanoTime();
        final int TARGET_FPS = 60;
        final long OPTIMAL_TIME = 1000000000 / TARGET_FPS;

        while (true) {
            long now = System.nanoTime();
            long updateLength = now - lastLoopTime;
            lastLoopTime = now;
            double delta = updateLength / ((double) OPTIMAL_TIME);

            lastFpsTime += updateLength;
            fps++;

            if (lastFpsTime >= 1000000000) {
                System.out.println("FPS: " + fps);
                lastFpsTime = 0;
                fps = 0;
            }

            player.update(delta);
            frame.validate();
            frame.repaint();

            try {
                Thread.sleep(Math.abs((lastLoopTime - System.nanoTime() + OPTIMAL_TIME) / 1000000));
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public static void main(String[] args) {
        new Window();
        gameLoop();
    }
}