import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Arrays;
import java.util.Scanner;

public class Server {

    public static void main(String[] args) {

        String[] wordArray = { "baboons", "beavers", "cats",
                "chickens", "choughs", "dolphins", "eagles", "elephants",
                "flamingoes", "giraffes", "grasshoppers", "hedgehogs", "hornets",
                "kangaroos" };

        int randomWordNumber = (int) (Math.random() * wordArray.length);

        char[] enteredLetters = new char[wordArray[randomWordNumber].length()];

        int numOfTries = 0;


        int maxNumOfTries = 12;
        

        int gameState = 0;

        switch(){
            case 0:
                break;
            case 1:
                break;
            case 2:
                break;
        }

            try {
                ServerSocket serverSocket = new ServerSocket(50000);
                Socket clientSocket = serverSocket.accept();
                PrintWriter out = new PrintWriter(clientSocket.getOutputStream(), true);
                InputStreamReader isr = new InputStreamReader(clientSocket.getInputStream());
                BufferedReader in = new BufferedReader(isr);
                String inputLine = in.readLine();
                System.out.println("I heard. " + inputLine);
                out.println("Hello Client!");
                //System.out.println(in.readLine());
                out.close();
                in.close();
                isr.close();
                clientSocket.close();
                serverSocket.close();

            } catch (IOException e) {
                e.printStackTrace();
            }

        }

        public static int enteredLetter(String word, char[] enteredLetters){


        }

        public static boolean printWord(String word, char[] enteredLetters){
            
        }
        
    }



