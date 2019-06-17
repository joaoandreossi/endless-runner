import java.util.List;

public class Collision {
    private int playerX;
    private int playerY;
    private int playerW;
    private int playerH;

    private int objX;
    private int objY;
    private int objW;
    private int objH;

    Collision(List<Integer> list){
        if(list.size() > 4){
            playerX = list.get(0);
            playerY = list.get(1);
            playerW = list.get(2);
            playerH = list.get(3);

            objX = list.get(4);
            objY = list.get(5);
            objW = list.get(6);
            objH = list.get(7);
        } else {
            playerX = list.get(0);
            playerY = list.get(1);
            playerW = list.get(2);
            playerH = list.get(3);

            objX = 0;
            objY = 0;
            objW = 0;
            objH = 0;
        }

        checkCollision();
    }

    boolean checkCollision(){
        if(playerX + playerW >= objX && 
           playerX <= objX + objW && 
           playerY + playerH >= objY && 
           playerY <= objY + objH){
            return true;
        }
        return false;
    }
}