import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.Inet4Address;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Objects;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.net.UnknownHostException;
import java.util.Arrays;
import java.util.Scanner;

public class Server {

    static Gamelounge gameLounge = new Gamelounge();
    static ServerThread handler;

    public static void main(String[] args) throws Exception {

        //try {

            ServerSocket serverSocket = new ServerSocket(3000);
            System.out.println("IP address: " + Inet4Address.getLocalHost().getHostAddress());  //The IP address user should connect to


        //before gamelounge is initialized, setting up clients
        System.out.println("Listening!");
        Socket client = serverSocket.accept();//server waits for clients to establish connection
        System.out.println("A CLIENT CONNECTED");

        handler = new ServerThread(client);
        //handler.start() might have to be moved out of while-loop
        handler.start();


        /*
        //Server connection fix thing
        while (true) {

            //before gamelounge is initialized, setting up clients
            System.out.println("Listening!");
            while(!gameLounge.areClientsReady) {
                Socket client = serverSocket.accept();//server waits for clients to establish connection
                System.out.println("A CLIENT CONNECTED");
                handler = new ServerThread(client);
                // handler.start() might have to be moved out of while-loop
                handler.start();
            }

                //when all clients have typed start it should stop the listening loop and jump to game (handler.start)




            }
*/
            /*
        } catch (IOException e) {
            e.printStackTrace();
        }*/
    }
}


    /*

            Socket clientSocket = serverSocket.accept();
            System.out.println("IP address: " + Inet4Address.getLocalHost().getHostAddress());  //The IP address user connected to
            System.out.println("Bro with ip address:" + Inet4Address.getLocalHost().getHostAddress() + " has joined the game");//this has to display "Client x has joined the server" in the client.

            PrintWriter out = new PrintWriter(clientSocket.getOutputStream(), true);
            InputStreamReader isr = new InputStreamReader(clientSocket.getInputStream());
            BufferedReader in = new BufferedReader(isr);
*/