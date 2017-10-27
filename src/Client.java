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
import java.util.Scanner;

public class Client {

    public static void main(String[] args) {
        boolean gameRunning = true;
        //Scanner input = new Scanner(System.in);

        try{

            Socket Socket = new Socket(InetAddress.getByName("localhost"),50000);
            PrintWriter out = new PrintWriter(Socket.getOutputStream(),true);
            BufferedReader inFromServer = new BufferedReader(new InputStreamReader(Socket.getInputStream()));
            BufferedReader inFromUser = new BufferedReader( new InputStreamReader(System.in));

            //System.out.println(in.readLine());

            do {
                char i = inFromUser.readLine().charAt(0);
                System.out.println(i);
                out.println(i);
                if(i == 'w'){
                    gameRunning = false;
                }
            } while(gameRunning);

            //out.println("F");

            inFromServer.close();
            out.close();
            Socket.close();
        }catch(IOException e){
            e.printStackTrace();
        }
    }
}