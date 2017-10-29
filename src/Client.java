
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
    static PrintWriter clientOut;
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
            String nickname = "";//the nickname chosen

            //Wait for user input
            try {
                s = input.nextLine();
            } catch (Exception el) {}

            //if they write connect
            if(Objects.equals(s, "connect")) {
                System.out.println("Write the ip you want to connect to");// then write an IP address
                //Wait for user input
                try {
                    IPAdress = input.nextLine(); //Read the IP address

                    System.out.println("Write the nickname you your fellow bros will know you by");// then write a nickname
                    nickname = input.nextLine(); //reads the nickname

                    //connect to the IP address given.
                    //clientSocket = new Socket (IPAdress, 3000); //Request permission to the IP address

                    try{
                        clientSocket = new Socket ("localhost", 3000); //Request permission to the IP address
                    } catch (Exception e){}

                    System.out.println("Connected to server");
                    System.out.println("Bro, you are connected to the IP address: " + Inet4Address.getLocalHost().getHostAddress());
                    //^The IP address user connected to
                } catch (Exception e1) {}

            } else {//if something beside connect is written
                System.out.println("Wrong command!");//default response
            }//end of if

            //right after connection is established and clients join gamelounge
            //(server side)establish nickname for client - gamelounge

            //printWriter (send to server) is initialized in order to capture "start" to start game
            try {
                clientOut = new PrintWriter(clientSocket.getOutputStream(), true);
            } catch (Exception e){}

            try//does this need its own try??
            {
                //gameLounge.clientInfo();
            } catch (Exception el) {}

            System.out.println("\nConnection was closed, or program failed to connect");

            //MERGED CLIENT -----------

            //dunno what this is
            /*
            Socket Socket = new Socket(InetAddress.getByName("localhost"),50000);
            PrintWriter out = new PrintWriter(Socket.getOutputStream(),true);
            BufferedReader inFromServer = new BufferedReader(new InputStreamReader(Socket.getInputStream()));

*/


            try {
                //receive from server
                inFromServer = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
                inFromUser = new BufferedReader( new InputStreamReader(System.in));

            } catch (Exception e){}

            do {
                try {
                    char i = inFromUser.readLine().charAt(0);
                    System.out.println(i);
                    clientOut.println(i);
                    if (i == 'w') {
                        gameRunning = false;//this is just for testing purposes
                    }
                } catch (Exception e){}
            } while(gameRunning);
            //if gamerunning is false terminate clients


            //out.println("F");

            inFromServer.close();
            clientOut.close();
            clientSocket.close();

        }catch(IOException e){
            e.printStackTrace();
        }
    }
}