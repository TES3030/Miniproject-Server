import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Inet4Address;
import java.net.Socket;

/**
 * Created by mariana on 27/10/2017.
 */
public class ServerThread extends Thread {

    /**
     * A handler thread class.  Handlers are spawned from the listening
     * loop and are responsible for a dealing with a single client
     * and broadcasting its messages.
     */

    Socket client;

    //word array containing different options of words that could be featured in the game
    String[] wordArray = {"peanut", "sunburn", "superman",
            "wrench", "cowboy", "shadow", "swordfish", "unicorn",
            "rainbow", "party", "mirror", "conversation", "regret",
            "friends", "explore", "evolution", "shipwreck", "bridge",
            "satallite", "cd", "rabbit", "cat", "dolphin", "sunset",
            "applesauce", "procrastinate", "unemployed", "oil", "fizz",
            "teenager", "programmer", "algae", "medialogy", "silhouette",
            "application", "game", "hangbro", "bubble", "roundabout", "level", "bro"};

    // used to generate a random word from the wordArray
    int randomWordNumber = (int) (Math.random() * wordArray.length);

    //char array enteredLetters is the same length as the word randomly drawed from the WordArray
    char[] enteredLetters = new char[wordArray[randomWordNumber].length()];

    // number of lives that the player has
    //number of times it can guess incorrectly before loosing the game
    static int numOfLives = 6;

    static boolean wordIsGuessed = false;
    static Gamelounge gameLounge = new Gamelounge();

    boolean lost = false;

    private String IPAddress;
    private String nickName;
    private boolean nickWritten = false;

    public String input;

    //Constructor that takes in the client socket
    ServerThread(Socket client){
        this.client = client;
    }

    private PrintWriter out;
    private InputStreamReader isr;
    private BufferedReader in;

    public void run() {
        //pickup whats coming from the client
        try {

            // formats to a text output stream instead of their byte types, e.g long int.
            out = new PrintWriter(client.getOutputStream(), true);

            // InputStreamReader converts bytes to character streams
            isr = new InputStreamReader(client.getInputStream());

            // BufferedReader is used to read the text from a character-based input stream
            in = new BufferedReader(isr);

            IPAddress = in.readLine();

            //debug
            System.out.println("\nPlayer with\nIP: " + IPAddress + " has connected");
            out.println("\nBro, you are connected to the IP address: " + Inet4Address.getLocalHost().getHostAddress());

            do {
                //--------------------- GAMELOUNGE LAUNCHED -------------------//

                while (!nickWritten) {
                    out.println("\nWrite a nickname your fellow bros will know you by: ");// then write a nickname
                    nickName = in.readLine();
                    if (nickName == null) {
                        return;
                    }
                    synchronized (gameLounge.nickNameList) {

                        //cycle through array list nicknames
                        //if its unique run clientJoins
                        if (!(gameLounge.nickNameList.contains(nickName))) {
                            gameLounge.clientJoins(IPAddress, nickName, out);
                            System.out.println("\nBro with\nIP: " + IPAddress + "\nand nickname: " + nickName + "\nhas connected to lounge");
                            nickWritten = true;
                            break;
                        }
                    }
                }

                gameLounge.clientInfo(out);

                // Accept messages from this client and broadcast them.
                // Ignore other clients that cannot be broadcasted to.
                while (gameLounge.areClientsReady == false) {//make this to switch. basically reading while not in game
                    input = in.readLine();
                    switch (input) {
                        case "":
                            break;

                        default:
                            //checks all strings for start and exit
                            gameLounge.checkForStart(input, out);

                            //if neither start or exit, broadcast to all clients
                            if (!gameLounge.chatTerminated) {
                                for (PrintWriter writer : gameLounge.writers) {
                                    writer.println("\nThe Bro " + nickName + " says: " + input);
                                    break;

                                }
                            }
                    }
                }

                do {

                    //------------------ ACTUAL GAME STARTS HERE ------------------------

                    //when clients proceed from gamelounge to game
                    //send gameRunning = true to client

                    // infinitely iterate through cycle as long as enterLetter returns true
                    // if enterLetter returns false that means user guessed all the letters
                    // in the word e.g. no asterisks were printed by printWord

                    switch (enteredLetter(wordArray[randomWordNumber], enteredLetters, in, out)) {
                        // if letter guessed by client is not in the word then number of lives decreases by 1
                        case 0:
                            numOfLives--;
                            out.println("\n\nSorry bro, that letter is not in the word. \nNumber of lives left: " + numOfLives);

                            break;
                        //if letter guessed was correct and entered for the first time
                        case 1:
                            //numOfTries++;
                            break;
                        //if letter guessed was correct but reentered
                        case 2:
                            break;

                        //if all letters have already been guessed

                        case 3:
                            // here the word guessed is true and therefore a message is sent to the client stating the word that they guessed
                            out.println("\nBro, that was correct! The word was " + wordArray[randomWordNumber]);
                            wordIsGuessed = true;
                            break;
                        case 4:
                            break;
                        default:
                            break;
                    }
                    //all inside of the do while happens while the word isnt guessed and the number of lives is larger than 0
                    //once the number of lives hits zero the client has lost.

                } while (!wordIsGuessed && numOfLives > 0 && gameLounge.areClientsReady == true);
                // if the word hasnt been guessed and the number of lives is bigger than 0

                if (numOfLives == 0) {
                    lost = true;
                }
                if (lost) {
                    //letting the client know
                    out.println("\nOh no bro! You lost.");

                    //set areclientsready to false and return to lounge
                    gameLounge.areClientsReady = false;

                }

                //still in gamelounge
            } while (gameLounge.gameLoungeRunning);//gamelounge stops here

            out.close(); //close PrinterWriter
            in.close(); //Close BufferedReader
            isr.close(); //close InputStreamReader

        } catch (IOException e) {
            System.out.println(e);

        } finally {
            // This client is going down!  Remove its name and its printWriter
            // from the sets, and close its socket.
            if (nickName != null) {
                gameLounge.nickNameList.remove(nickName);
            }
            if (out != null) {
                gameLounge.writers.remove(out);
            }
            try {
                client.close();
            } catch (IOException e) {
            }

        }
    }

    // This function hints the user to enter a letter and places it in the correct place
    public static int enteredLetter (String word, char[] enteredLetters, BufferedReader in, PrintWriter out){

        out.println(("\nBro, attempt to guess the word by entering a letter: "));


        //if the printWord function returns false then all the letters have been guessed
        if (!printWord(word, enteredLetters, out)) {
            return 3;
        }

        out.println((" -> "));


        // empty position output is saved onto an int variable
        int emptyPosition = findEmptyPosition(enteredLetters);


        try {
            char userInput = in.readLine().charAt(0);
            //char userInput = input.nextLine().charAt(0);

            //if the letter is in the EnteredLetters array already
            //returns 2 because the letter guessed is correct but is being reentered by the user
            if (inEnteredLetters(userInput, enteredLetters)) {

                out.println("\nYou forget quickly ma bro, the letter " + userInput + " is already in the word.");

                return 2;

                // else if the letter guessed was correct and entered for the first time
                // the asterisk is then substituted for the correct letter in the correct position
            } else if (word.contains(String.valueOf(userInput))) {

                out.println("\nGood job bro, the letter " + userInput + " is in the word.");
                enteredLetters[emptyPosition] = userInput;
                return 1;

                //else the letter entered is not in the word
                //which returns a 0 and therefore the client looses a life
            } else {
                return 0;
            }

        } catch (IOException e) {
            e.printStackTrace();
        }

        return 4;

    }

    // This function prints the word in asterisks (*) in order to hide the letters from the viewer
    // the function returns true if the asterisks are printed, else returns false
    public static boolean printWord(String word, char[] enteredLetters, PrintWriter out) {

        boolean asteriskPrinted = false;

        for (int i = 0; i < word.length(); i++) {
            char letter = word.charAt(i);
            if (inEnteredLetters(letter, enteredLetters)) {
                out.println(letter);

            } else {
                out.println(("*"));

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
