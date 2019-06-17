import java.util.List;
import java.util.Scanner;

import java.net.InetAddress;
import java.net.Socket;
import java.io.IOException;
import java.io.PrintWriter;

public class Connection {
    Socket socket;
    Scanner in;
    PrintWriter out;

    Connection() throws IOException{
        socket = new Socket(InetAddress.getLocalHost(), 4444);
        in = new Scanner(socket.getInputStream());
        out = new PrintWriter(socket.getOutputStream(), true);
        socket.setKeepAlive(true);
    }

    void sendMessage(List<Integer> list){
        for(int i = 0; i < list.size(); i++){
            out.println(list.get(i));
        }
    }

    boolean receiveMessage(){
        if(in.hasNextBoolean()){
            return in.nextBoolean();
        }
        return false;
    }

    void printResponse() throws IOException{
        System.out.println("Server response: " + in.nextLine());
        closeConnection();
    }

    void closeConnection() throws IOException{
        in.close();
        socket.close();
    }
}