import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;

/**
 * Created by mariana on 27/10/2017.
 */
public class ServerThread extends Thread{
    Socket socket;

    ServerThread(Socket socket){
        this.socket = socket;
    }

    public void run(){
        //pickup whats coming from the client
        try {
            String message = null;
            BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            while ((message = in.readLine()) != null){
                System.out.println("message from client:" + message);
            }
            socket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
