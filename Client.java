import java.awt.Dimension;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.IOException;
import java.util.HashSet;
import java.util.Set;
import javax.swing.JFrame;
import javax.swing.JPanel;

public class Client extends JPanel implements KeyListener {
    static private JFrame frame1 = new JFrame("Player 1");
    static private JFrame frame2 = new JFrame("Player 2");
    static private int frameW = 800;
    static private int frameH = 300;

    static private Player player1 = new Player(50, 180, 30, 60, 1);
    static private Player player2 = new Player(50, 180, 30, 60, 2);

    static private long lastFpsTime = 0;
    static private int fps = 0;

    static private boolean isPaused = false;

    private final Set<Character> pressed = new HashSet<Character>();

    public Client() {
        frame1.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame1.getContentPane().add(player1);
        frame1.addKeyListener(this);
        frame1.setFocusable(true);
        frame1.pack();
        frame1.setSize(new Dimension(frameW, frameH));
        frame1.setLocationByPlatform(true);
        frame1.setVisible(true);
        
        frame2.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame2.getContentPane().add(player2);
        frame2.addKeyListener(this);
        frame2.setFocusable(true);
        frame2.pack();
        frame2.setSize(new Dimension(frameW, frameH));
        frame2.setLocationByPlatform(true);
        frame2.setVisible(true);
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
        if(e.getKeyChar() == KeyEvent.VK_ENTER){
            if(isPaused){
                isPaused = false;
            } else {
                isPaused = true;
            }
        }
        pressed.add(e.getKeyChar());
        player1.changeAnimation(e);
        player2.changeAnimation(e);
    }

    @Override
    public synchronized void keyReleased(KeyEvent e) {
        pressed.remove(e.getKeyChar());
        if(pressed.size() == 0){
            player1.stopWalking();
            player1.stop();
            player2.stopWalking();
            player2.stop();
        }
    }

    public static void gameLoop() throws IOException{
        long lastLoopTime = System.nanoTime();
        final int TARGET_FPS = 60;
        final long OPTIMAL_TIME = 1000000000 / TARGET_FPS;
        Connection c = new Connection();

        while (true) {
            long now = System.nanoTime();
            long updateLength = now - lastLoopTime;
            lastLoopTime = now;
            double delta = updateLength / ((double) OPTIMAL_TIME);

            lastFpsTime += updateLength;
            fps++;

            if (lastFpsTime >= 1000000000) {
                // System.out.println("FPS: " + fps);
                lastFpsTime = 0;
                fps = 0;
            }

            c.sendMessage(player1.objectPosition());
            if(!isPaused){
                player1.update(delta);
                player2.update(delta);
                
                frame1.validate();
                frame1.repaint();

                frame2.validate();
                frame2.repaint();
            }

            if(c.receiveMessage()){
                player1.isFinished = true;
                player2.isFinished = true;
            }

            try {
                Thread.sleep(Math.abs((lastLoopTime - System.nanoTime() + OPTIMAL_TIME) / 1000000));
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public static void main(String[] args) throws IOException {
        new Client();
        gameLoop();
    }
}