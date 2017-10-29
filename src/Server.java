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

    public static final int PORT = 50000;

    public static void main(String[] args) throws IOException {


        //new Server.runServer();

        // array of words that are picked randomly to be guessed



        try {

            ServerSocket serverSocket = new ServerSocket(PORT);
            System.out.println("The server is listening");
            while(true) {

                Socket clientSocket = serverSocket.accept();
                System.out.println("Connected");
                //new Thread(new Server(clientSocket)).start();
                //PrintWriter out = new PrintWriter(clientSocket.getOutputStream(), true);
                //InputStreamReader isr = new InputStreamReader(clientSocket.getInputStream());
                //BufferedReader in = new BufferedReader(isr);
                ServerThread handler = new ServerThread(clientSocket);
                handler.start();

/*
                do {
                    // infinitely iterate through cycle as long as enterLetter returns true
                    // if enterLetter returns false that means user guessed all the letters
                    // in the word e.g. no asterisks were printed by printWord

                    switch (enteredLetter(wordArray[randomWordNumber], enteredLetters, in, out)) {
                        // if letter guessed by client is not in the word then number of lives decreases by 1
                        case 0:
                            numOfLives--;
                            break;
                        //if letter guessed was correct and entered for the first time
                        case 1:
                            //numOfTries++;
                            break;
                        //if letter guessed was correct but reentered
                        case 2:
                            break;
                        //if all leters have already been guessed
                        case 3:
                            out.println("\nBro, that was correct! The word was " + wordArray[randomWordNumber]);
                            wordIsGuessed = true;
                            break;
                        case 4:
                            break;
                    }
                } while (!wordIsGuessed && numOfLives > 0);
                // if if the word hasnt been guessed and the number of lives is bigger than 0
                //out.print("YOU LOST" + numOfLives);
                out.close();
                in.close();
                isr.close();
                clientSocket.close();
                serverSocket.close();
            }
            */
            }
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
/*
    public static int enteredLetter (String word, char[] enteredLetters, BufferedReader in, PrintWriter out){
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
/*
    public void runServer() throws IOException{
        ServerSocket serverSocket = new ServerSocket(PORT);
        System.out.println("The server is listening");
        while(true){
            Socket clientSocket = serverSocket.accept();
            System.out.println("Connected");
            new ServerThread(clientSocket).start();
        }
    }
    */
}