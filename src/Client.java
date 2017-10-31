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
    static PrintWriter clientOut;
    static BufferedReader inFromServer;
    static BufferedReader inFromUser;
    static String string = null;

    static boolean gameRunning = true;

    public static void main(String[] args) {

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
            } catch (Exception e) {
                e.printStackTrace();
            }


                //if they write connect

                if (Objects.equals(s, "connect")) {//this returns true no matter the string - wtf
                    System.out.println("Write the ip you want to connect to");// then write an IP address
                    //Wait for user input
                    try {
                        IPAdress = input.nextLine(); //Read the IP address

                        //disabled due to testing
                        System.out.println("Write your preferred nickname");// then write a nickname
                        nickname = input.nextLine(); //reads the nickname

                        //connect to the IP address given.
                        try {
                            clientSocket = new Socket(IPAdress, 4000); //Request permission to the IP address
                            clientOut = new PrintWriter(clientSocket.getOutputStream(), true);
                            clientOut.println(IPAdress);
                            clientOut.println(nickname);

                        } catch (Exception e) {
                            System.out.println("client DID NOT connect");
                        }
                        //System.out.println("");
                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                } else { //if something beside connect is written
                    System.out.print("Wrong command!");//will only happen if u do not enter a string...?
                }//end of if

            //right after connection is established and clients join gamelounge
            //(server side)establish nickname for client - gamelounge

            //printWriter (send to server) is initialized in order to capture "start" to start game
            try {
                clientOut = new PrintWriter(clientSocket.getOutputStream(), true);
            } catch (Exception e){
                e.printStackTrace();
            }

            try//does this need its own try??
            {
                //gameLounge.clientInfo();
            } catch (Exception e) {
                e.printStackTrace();
            }

            try {

                inFromServer = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));

            } catch (Exception e){
                e.printStackTrace();
            }
            do {
                try {
                    //receive from server
                    while(inFromServer.ready() && (string = inFromServer.readLine()) != null && gameRunning)
                    {
                        if(string.equals("*") || string.equals(" -> ") || string.length() == 1) {
                            System.out.print(string);
                        } else if (string.equals("gameRunning is false")){
                            gameRunning = false;
                        } else {
                            System.out.print("\n" + string);
                        }
                    }

                    inFromUser = new BufferedReader( new InputStreamReader(System.in));
                    char i = inFromUser.readLine().charAt(0);
                    System.out.println(i);
                    clientOut.println(i);

                    /*
                    if (i == 'w') {//this is just for testing purposes
                        gameRunning = false;
                        System.out.println("keyletter detected - terminating client");
                    }
                    */
                } catch (Exception e){
                    e.printStackTrace();
                }
           } while(gameRunning);
            //if gamerunning is false terminate clients

            System.out.println("\nConnection was closed");



            inFromServer.close();
            clientOut.close();
            clientSocket.close();

        }catch(IOException e){
            e.printStackTrace();
        }
    }
}
