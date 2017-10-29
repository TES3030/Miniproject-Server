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

    String[] wordArray = {"baboons", "beavers", "cats",
            "chickens", "choughs", "dolphins", "eagles", "elephants",
            "flamingoes", "giraffes", "grasshoppers", "hedgehogs", "hornets",
            "kangaroos"};

    int randomWordNumber = (int) (Math.random() * wordArray.length);

    char[] enteredLetters = new char[wordArray[randomWordNumber].length()];

    int numOfLives = 5;

    int gameState = 0;

    boolean wordIsGuessed = false;

    ServerThread(Socket socket){

        this.client = client;
    }

    public void run(){
        //pickup whats coming from the client
        try {
            String message = null;

            PrintWriter out = new PrintWriter(client.getOutputStream(), true);
            InputStreamReader isr = new InputStreamReader(client.getInputStream());
            BufferedReader in = new BufferedReader(isr);

            while ((message = in.readLine()) != null) {
                System.out.println("message from client:" + message);
            }

            //client.close();

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
            //client.close();
            //server.close();

        } catch (IOException e) {
            e.printStackTrace();

        }
    }

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
}
