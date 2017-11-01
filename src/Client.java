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
    static PrintWriter clientOut;
    static BufferedReader inFromUser;


    public boolean connected = false;

    static boolean gameRunning = true;

    static boolean gameLoungeRunning = true;

    public static void main(String[] args) throws IOException{//cheese workaround - instead of try catch
        Client clientObject = new Client();//ghetto workaround - trying to access client in reader and out of static

        Scanner sc = new Scanner(System.in);
        String input;

        System.out.println("---------------------------------------------------------------");
        System.out.println("Welcome to HangBro! Type \"connect\" if you want to join a game :)");
        System.out.println("---------------------------------------------------------------");


        while(sc.hasNext() && gameLoungeRunning){//asks if it has something - returns bool
            input = (String) sc.next();//takes it and uses it for something - String value depends on case

            switch (input){
                case"connect":
                    System.out.println("Write the ip you want to connect to");//String value ip adress
                    clientObject.connect((String) sc.next());
                    break;
                case"login":
                    System.out.println("Write your preferred nickname");//string value nickname
                    clientObject.login((String) sc.next());
                    break;
                default:
                    if (clientObject.connected){
                        clientObject.clientOut.println(input); //sends to server
                    }else{
                        System.out.println("Please connect to the server by typing: connect");
                    }
                    break;
            }

        }

        System.out.println("\nConnection was closed");
        clientOut.close();
        clientSocket.close();

        //try{

/*

            ////////////// CLIENT CONNECTED TO SERVER /////////////



            //--------------------- VV  ACTUAL GAME  VV -----------------

            do {
                try {

*/
/*
                            //if next line is commented out, the first time a letter is written, there is a reaction.
                            // however, then it will only run once!
                            // if the line is there, it does the double thing
                            //inFromUser = new BufferedReader(new InputStreamReader(System.in));
                            char i = inFromUser.readLine().charAt(0);
                            System.out.println(i);
                            clientOut.println(i);
*//*


                        //if next line is commented out, the first time a letter is written, there is a reaction.
                    // however, then it will only run once!
                    // if the line is there, it does the double thing
                    inFromUser = new BufferedReader(new InputStreamReader(System.in));
                    char i = inFromUser.readLine().charAt(0);
                    System.out.println(i);
                    clientOut.println(i);

                } catch (Exception e){
                    e.printStackTrace();
                }
            } while(gameRunning);
            //if gamerunning is false go to gamelounge
*/

        //}catch(IOException e){
            //e.printStackTrace();
        //}
    }

    public void connect(String IPAddress) throws IOException{

        clientSocket = new Socket (IPAddress, 3000); //Request permission to the IP address
        clientOut = new PrintWriter(clientSocket.getOutputStream(), true);
        clientOut.println(IPAddress);//sends to server
        connected = true;
        System.out.println("Connected to server");

        new Thread(new Reader(clientSocket.getInputStream(), this)).start();

    }


    public void login(String username) throws IOException{

        System.out.println("You typed in your nickname: " + username);

    }


}
