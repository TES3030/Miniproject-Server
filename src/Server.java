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
/*
    //OUTSIDE MAIN PLS VV

    String[] wordArray = {"baboons", "beavers", "cats",
            "chickens", "choughs", "dolphins", "eagles", "elephants",
            "flamingoes", "giraffes", "grasshoppers", "hedgehogs", "hornets",
            "kangaroos"};

    int randomWordNumber = (int) (Math.random() * wordArray.length);
    char[] enteredLetters = new char[wordArray[randomWordNumber].length()];
    int numOfLives = 5;
    int gameState = 0;
    boolean wordIsGuessed = false;

    //OUTSIDE MAIN PLS ^^
*/
    public static void main(String[] args) {

        //OUTSIDE MAIN PLS VV

        String[] wordArray = {"baboons", "beavers", "cats",
                "chickens", "choughs", "dolphins", "eagles", "elephants",
                "flamingoes", "giraffes", "grasshoppers", "hedgehogs", "hornets",
                "kangaroos"};

        int randomWordNumber = (int) (Math.random() * wordArray.length);
        char[] enteredLetters = new char[wordArray[randomWordNumber].length()];
        int numOfLives = 5;
        int gameState = 0;
        boolean wordIsGuessed = false;
        boolean gameHasStarted = false;

        //OUTSIDE MAIN PLS ^^

        try {

            ServerSocket serverSocket = new ServerSocket(50000);
            Socket clientSocket = serverSocket.accept();
            System.out.println("IP address: " + Inet4Address.getLocalHost().getHostAddress());  //The IP address user connected to
            System.out.println("Bro with ip address:" + Inet4Address.getLocalHost().getHostAddress() + " has joined the game");//this has to display "Client x has joined the server" in the client.

            PrintWriter out = new PrintWriter(clientSocket.getOutputStream(), true);
            InputStreamReader isr = new InputStreamReader(clientSocket.getInputStream());
            BufferedReader in = new BufferedReader(isr);

            do {
                //launching gamelounge


                if (gameHasStarted) { //read "startgame" from client chat
                    // infinitely iterate through cycle as long as enterLetter returns true
                    // if enterLetter returns false that means user guessed all the letters
                    // in the word e. g. no asterisks were printed by printWord
                    switch (enteredLetter(wordArray[randomWordNumber], enteredLetters, in, out)) {
                        case 0:
                            //numOfLives--; //disabling to make the server not cut off while testing
                            break;
                        case 1:
                            //numOfTries++;
                            break;
                        case 2:
                            break;
                        case 3:
                            out.println("\nBro, that was correct! The word was " + wordArray[randomWordNumber]);
                            wordIsGuessed = true;
                            break;
                        case 4:
                            break;
                    }
                }
            }

                while (!wordIsGuessed && numOfLives > 0) ;

                out.print("YOU LOST" + numOfLives);
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

    static void gameLounge() throws IOException {
        // Introduction to the game lounge
        System.out.println("---------------------------------------------------------------");
        System.out.println("Welcome to the game lounge! Here you can see all players who have joined \n " +
                "Wait here until someone starts the game. Type \"start\" if you want the game to start :)");
        System.out.println("---------------------------------------------------------------");
        // display when a new client joins

        //If the user inputs the "start" command
        //This does not work yet :/
        //chatHandler();

        startGame();
    }

    static void startGame() throws IOException {//all of following descriptions are expected in the "startgame" function in client
        //send number of lives to client
        //send word in underscores

        //send to client if letter is correct or not
        //send to client indication to wether game continues or not
        //send the return message to the client


        //SEND GAMESTATE TO CLIENT!!!!!!!!!!!!!

    }

    public static int enteredLetter (String word,char[] enteredLetters, BufferedReader in, PrintWriter out){
        out.print("Attempt to guess the word by entering a letter ");

        if (!printWord(word, enteredLetters, out)) {
            return 3;
        }

        out.print(" -> ");

        //Scanner input = new Scanner(System.in);
        int emptyPosition = findEmptyPosition(enteredLetters);
        try {

            char userInput = in.readLine().charAt(0);
            //char userInput = input.nextLine().charAt(0);

            if (inEnteredLetters(userInput, enteredLetters)) {
                out.println("This letter is already in the word: " + userInput);
                return 2;
            } else if (word.contains(String.valueOf(userInput))) {
                enteredLetters[emptyPosition] = userInput;
                return 1;
            } else {
                out.println("This letter is not in the word: " + userInput);
                return 0;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        //String input = in.readLine();
        //System.out.println("I heard. " + inputLine);
        //out.println("Hello Client!");
        //System.out.println(in.readLine());

        return 4;

    }

    public static boolean printWord(String word, char[] enteredLetters, PrintWriter out) {

        boolean asteriskPrinted = false;

        for (int i = 0; i < word.length(); i++) {
            char letter = word.charAt(i);
            if (inEnteredLetters(letter, enteredLetters)) {
                out.print(letter);
            } else {
                out.print('*');
                asteriskPrinted = true;
            }
        }
        return asteriskPrinted;
    }

    public static boolean inEnteredLetters(char letter, char[] enteredLetters) {
        return new String(enteredLetters).contains(String.valueOf(letter));

    }

    public static int findEmptyPosition(char[] enteredLetters) {
        int i = 0;
        while (enteredLetters[i] != '\u0000') i++;
        return i;
    }
}