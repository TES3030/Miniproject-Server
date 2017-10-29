import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

/**
 * Created by mariana on 27/10/2017.
 */
public class ServerThread extends Thread {

    Socket client;

    //word array containing different options of words that could be featured in the game
    String[] wordArray = {"peanut", "sunburn", "superman",
            "wrench", "cowboy", "shadow", "swordfish", "unicorn",
            "rainbow", "party", "mirror", "conversation", "regret",
            "friends", "explore", "evolution", "shipwreck", "bridge",
            "satallite", "cd", "rabbit", "cat", "dolphin", "sunset",
            "applesauce", "procrastinate", "unemployed", "oil", "fizz",
            "teenager", "programmer", "algae", "Medialogy", "silhouette",
            "application", "game", "hangbro", "bubble", "roundabout", "level", "bro"};

    // used to generate a random word from the wordArray
    int randomWordNumber = (int) (Math.random() * wordArray.length);

    //char array enteredLetters is the same length as the word randomly drawed from the WordArray
    char[] enteredLetters = new char[wordArray[randomWordNumber].length()];

    // number of lives that the player has
    //number of times it can guess incorrectly before loosing the game
    int numOfLives = 5;

    //game state to be sent to the client by the server, can be 0, 1 or 2
    int gameState = 0;

    boolean wordIsGuessed = false;

    //Constructor that takes in the client socket
    ServerThread(Socket socket){

        this.client = client;
    }

    public void run(){
        //pickup whats coming from the client
        try {

           String message = null;

            // formats to a text output stream instead of their byte types, e.g long int.
            PrintWriter out = new PrintWriter(client.getOutputStream(), true);

            // InputStreamReader converts bytes to character streams
            InputStreamReader isr = new InputStreamReader(client.getInputStream());

            // BufferedReader is used to read the text from a character-based input stream
            BufferedReader in = new BufferedReader(isr);

            // while there is a message from the client, print it
            while ((message = in.readLine()) != null) {
                System.out.println("message from client:" + message);
            }

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
                        // here the word guessed is true and therefore a message is sent to the client stating the word that they guessed
                        out.println("\nBro, that was correct! The word was " + wordArray[randomWordNumber]);
                        wordIsGuessed = true;
                        break;
                    case 4:
                        break;
                }
                //all inside of the dowhile happens while the word isnt guessed and the number of lives is larger than 0
                //once the number of lives hits zero the client has lost.
            } while (!wordIsGuessed && numOfLives > 0);

            // if if the word hasnt been guessed and the number of lives is bigger than 0
            //out.print("YOU LOST" + numOfLives);

            out.close(); //close PrinterWriter
            in.close(); //Close BufferedReader
            isr.close(); //close InputStreamReader

            //client.close();
            //server.close();

        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    // This function hints the user to enter a letter and places it in the correct place

    public static int enteredLetter (String word, char[] enteredLetters, BufferedReader in, PrintWriter out){

        out.print("Attempt to guess the word by entering a letter ");

        //if the printWord function returns false then all the letters have been guessed
        if (!printWord(word, enteredLetters, out)) {
            return 3;
        }

        out.print(" -> ");

        // empty position output is saved onto an int variable
        int emptyPosition = findEmptyPosition(enteredLetters);


        try {
            char userInput = in.readLine().charAt(0);
            //char userInput = input.nextLine().charAt(0);

            //if the letter is in the EnteredLetters array already
            //returns 2 because the letter guessed is correct but is being reentered by the user
            if (inEnteredLetters(userInput, enteredLetters)) {
                out.println("This letter is already in the word: " + userInput);
                return 2;

                // else if the letter guessed was correct and entered for the first time
                // the asterisk is then substituted for the correct letter in the correct position
            } else if (word.contains(String.valueOf(userInput))) {
                enteredLetters[emptyPosition] = userInput;
                return 1;

                // else the letter entered is not in the word
                //which returns a 0 and therefore the client looses a life
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

    // This function prints the word in asterisks (*) in order to hide the letters from the viewer
    // the function returns true if the asterisks are printed, else returns false
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

    // This function checks if the letter is in the enteredLetters array
    public static boolean inEnteredLetters(char letter, char[] enteredLetters) {
        return new String(enteredLetters).contains(String.valueOf(letter));
    }

    // This function looks in the enteredLetters array for the first empty slot
    public static int findEmptyPosition(char[] enteredLetters) {
        int i = 0;
        while (enteredLetters[i] != '\u0000') i++;
        return i;
    }

}
