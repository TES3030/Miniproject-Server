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

    public static void main(String[] args) throws Exception {


            ServerSocket serverSocket = new ServerSocket(4000);
            System.out.println("IP address: " + Inet4Address.getLocalHost().getHostAddress());  //The IP address user should connect to


        while (true) {

            //before gamelounge is initialized, setting up clients
            System.out.println("Listening!");
            Socket client = serverSocket.accept();//server waits for clients to establish connection
            System.out.println("A CLIENT CONNECTED");

            //when all clients have typed start it should stop the listening loop and jump to game (handler.start)

            ServerThread handler = new ServerThread(client);
            handler.start();

            }

    }
}

