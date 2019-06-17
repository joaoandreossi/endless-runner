import java.io.IOException;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Server {
    final static int port = 4444;
    static Scanner in;
    static PrintWriter out;
    static int value;
    static List<Integer> position = new ArrayList<Integer>();

    static void checkCollision(){
        Collision col = new Collision(position);
        out.println(col.checkCollision());
    }
    public static void main(String[] args) throws IOException {

        try (ServerSocket listener = new ServerSocket(port)) {
            System.out.println("O server está rodando na porta " + port);

            while (true) {
                try (Socket socket = listener.accept()) {
                    System.out.println("Cliente conectado.");
                    in = new Scanner(socket.getInputStream());
                    out = new PrintWriter(socket.getOutputStream(), true);

                    while(in.hasNextInt()){
                        value = in.nextInt();
                        System.out.println(value);
                        if(value == -100){
                            checkCollision();
                            position = new ArrayList<Integer>();
                        } else {
                            position.add(value);
                        }
                    }
                }
            }
        }
    }
}