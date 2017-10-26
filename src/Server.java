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

        /*
        0 = if letter entered is not in the word
        1 = if letter were entered 1st time
        2 = if already guessed letter was reentered
        3 = if all letters were guessed
         */

        public static int enteredLetter(String word, char[] enteredLetters){
            System.out.print("Attempt to guess the word by entering a letter ");

            if(! printWord(word, enteredLetters)){
                return 3;
            }
            System.out.print(" -> ");
            //take input from client here // Scanner input = new Scanner(System.in);
            int emptyPosition = findEmptyPosition(enteredLetters);
            char userInput = input.nextLine.charAt(0);

            if(inEnteredLetters(userInput,enteredLetters)){
                System.out.println(userInput + " this letter is already in the word");
                return 2;
            }
            else if(word.contains(String.valueOf(userInput))){
                enteredLetters[emptyPosition] = userInput;
                return 1;
            }
            else{
                System.out.println(userInput + "this letter is not in the word");
                return 0;
            }


        }

        public static boolean printWord(String word, char[] enteredLetters){
            
        }
        
    }



