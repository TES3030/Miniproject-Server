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
import java.net.*;
import java.util.Objects;
import java.io.*;

public class Client {

    static Socket clientSocket;
    //Socket Socket;
    static PrintWriter out;
    static BufferedReader inFromServer;
    static BufferedReader inFromUser;


    public static void main(String[] args) {
        boolean gameRunning = true;
        Scanner input = new Scanner(System.in);

        try{

            //MERGED CLIENT -------------
            System.out.println("---------------------------------------------------------------");
            System.out.println("Welcome to HangBro! Type \"connect\" if you want to join a game :)");
            System.out.println("---------------------------------------------------------------");

            String s = "";
            String IPAdress = "";//The IP address given from external source

            //Wait for user input
            try {
                s = input.nextLine();
            } catch (Exception el) {}
            // if they write connect
            if(Objects.equals(s, "connect")) {
                System.out.println("Write the ip you want to connect to");// then write an IP address
                //Wait for user input
                try {
                    IPAdress = input.nextLine(); //Read the IP address

                    //connect to the IP address given.

                    //THIS IS MAIN PROBLEM - needs fix

                    out = new PrintWriter(clientSocket.getOutputStream(),true);
                    inFromServer = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));



                } catch (Exception e1) {}

            }
            else {
                System.out.println("Wrong command!");
            }

            try
            {
                clientSocket = new Socket (IPAdress, 3000); //Request permission to the IP address
                //clientSocket = new Socket ("localhost", 3000); //Request permission to the IP address
                System.out.println("Connected to server");
                System.out.println("Bro, you are connected to the IP address: " + Inet4Address.getLocalHost().getHostAddress());
                //The IP address user connected to

                //gameLounge();
            } catch (Exception e) {}

            System.out.println("\nConnection was closed, or program failed to connect");

            //MERGED CLIENT -----------

            //dunno what this is
            /*
            Socket Socket = new Socket(InetAddress.getByName("localhost"),50000);
            PrintWriter out = new PrintWriter(Socket.getOutputStream(),true);
            BufferedReader inFromServer = new BufferedReader(new InputStreamReader(Socket.getInputStream()));
            BufferedReader inFromUser = new BufferedReader( new InputStreamReader(System.in));

            //System.out.println(in.readLine());
*/
            do {
                char i = inFromUser.readLine().charAt(0);
                System.out.println(i);
                out.println(i);
                if(i == 'w'){
                    gameRunning = false;//this is just for testing purposes
                }
            } while(gameRunning);
            //if gamerunning is false terminate clients


            //out.println("F");

            inFromServer.close();
            out.close();
            //Socket.close();

        }catch(IOException e){
            e.printStackTrace();
        }
    }
}