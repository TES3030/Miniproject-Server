/**
 * Created by mariana on 26/10/2017.
 */
import java.io.IOException;
import java.net.*;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

public class Client {

    public static void main(String[] args) {
        try{
            Socket Socket = new Socket(InetAddress.getByName("localhost"),50000);
            PrintWriter out = new PrintWriter(Socket.getOutputStream(),true);
            BufferedReader in= new BufferedReader(new InputStreamReader(Socket.getInputStream()));
            out.println("I am a client");
            String l = in.readLine();
            System.out.println(l);
            in.close();
            out.close();
            Socket.close();
        }catch(IOException e){
            e.printStackTrace();
        }
    }


}
