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
    static String[] wordArray = {"peanut", "sunburn", "superman",
            "wrench", "cowboy", "shadow", "swordfish", "unicorn",
            "rainbow", "party", "mirror", "conversation", "regret",
            "friends", "explore", "evolution", "shipwreck", "bridge",
            "satallite", "cd", "rabbit", "cat", "dolphin", "sunset",
            "applesauce", "procrastinate", "unemployed", "oil", "fizz",
            "teenager", "programmer", "algae", "medialogy", "silhouette",
            "application", "game", "hangbro", "bubble", "roundabout", "level", "bro"};

    // used to generate a random word from the wordArray
    static int randomWordNumber = (int) (Math.random() * wordArray.length);

    //char array enteredLetters is the same length as the word randomly drawed from the WordArray
    static char[] enteredLetters = new char[wordArray[randomWordNumber].length()];

    // number of lives that the player has
    //number of times client can guess incorrectly before loosing the game
    final static int livesOnStart = 6;
    static int numOfLives = livesOnStart;

    static boolean wordIsGuessed = false;
    static Gamelounge gameLounge = new Gamelounge();

    private String IPAddress;
    String nickName;
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
            out.println("\nBro, you are connected to the IP address: " + Inet4Address.getLocalHost().getHostAddress());

            //start reader thread
            /*
            new Thread(new ServerReaderThread(in, this, gameLounge)).start();
            System.out.println("Started ServerReaderThread");
            */

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
                            gameLounge.clientInfo();
                            break;
                        }
                        out.println("Omg bruh, that nickname is already taken!");
                    }
                }

                // Accept messages from this client and broadcast them.
                // Ignore other clients that cannot be broadcasted to.

                // TESTING
                while (gameLounge.areClientsReady == false) {
                    input = in.readLine();
                    gameLounge.checkForWord(input, out, this.nickName);

                }

                do {

                    //------------------ ACTUAL GAME STARTS HERE ------------------------

                    //when clients proceed from gamelounge to game

                    // infinitely iterate through cycle as long as enterLetter returns true
                    // if enterLetter returns false that means user guessed all the letters
                    // in the word e.g. no asterisks were printed by printWord

                    switch (enteredLetter(wordArray[randomWordNumber], enteredLetters, in, out)) {
                        // if letter guessed by client is not in the word then number of lives decreases by 1
                        case 0:
                            numOfLives--;
                            for (PrintWriter writer : gameLounge.writers) {
                                writer.println("Number of lives left: " + numOfLives);
                            }
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
                            for (PrintWriter writer : gameLounge.writers) {
                                writer.println("\nBro, that was correct! The word was " + wordArray[randomWordNumber]);
                            }
                            wordIsGuessed = true;
                            break;
                        case 4:
                            break;
                        default:
                            break;
                    }
                    //all inside of the do while happens while the word isnt guessed and the number of lives is larger than 0
                    //once the number of lives hits zero the client has lost.

                    if(wordIsGuessed){
                        clientWon();
                    }
                    if (numOfLives == 0) {
                        clientLost();
                    }

                } while (gameLounge.areClientsReady == true);

                //still in gamelounge
            } while (gameLounge.gameLoungeRunning);//gamelounge stops here

            out.close(); //close PrinterWriter
            in.close(); //Close BufferedReader
            isr.close(); //close InputStreamReader

        } catch (Exception e) {
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


    public void clientWon()throws Exception{
        for (PrintWriter writer : gameLounge.writers) {
            writer.println("CONGRATZ Bros! You won the game!");
        }
        gameReset();
        wordIsGuessed = false;

        //reset start
    }

    public void clientLost()throws Exception{
        //letting the client know
        for (PrintWriter writer : gameLounge.writers) {
            writer.println("\nOh no bro! You lost.");
        }
        gameReset();

        //reset start

    }

    public void gameReset()throws Exception{
        // used to generate a random word from the wordArray
        randomWordNumber = (int) (Math.random() * wordArray.length);

        //char array enteredLetters is the same length as the word randomly drawed from the WordArray
        enteredLetters = new char[wordArray[randomWordNumber].length()];

        gameLounge.areClientsReady = false;//set areclientsready to false and return to lounge
        numOfLives = livesOnStart; //reset lives
        gameLounge.chatTerminated = false;//leaving game, people can send messages again
        gameLounge.clientInfo();//prints clientInfo once when game is exited

    }


    // This function hints the user to enter a letter and places it in the correct place
    public static int enteredLetter (String word, char[] enteredLetters, BufferedReader in, PrintWriter out){

        for (PrintWriter writer : gameLounge.writers) {
            writer.println(("\nBro, attempt to guess the word by entering a letter: "));
        }


        //if the printWord function returns false then all the letters have been guessed
        if (!printWord(word, enteredLetters, out)) {
            return 3;
        }
        for (PrintWriter writer : gameLounge.writers) {
            writer.println((" -> "));
        }


        // empty position output is saved onto an int variable
        int emptyPosition = findEmptyPosition(enteredLetters);


        try {
            char userInput = in.readLine().charAt(0);
            //char userInput = input.nextLine().charAt(0);

            //if the letter is in the EnteredLetters array already
            //returns 2 because the letter guessed is correct but is being reentered by the user
            if (inEnteredLetters(userInput, enteredLetters)) {

                for (PrintWriter writer : gameLounge.writers) {
                    writer.println("\nYou forget quickly ma bro, the letter " + userInput + " is already in the word.");
                }

                return 2;

                // else if the letter guessed was correct and entered for the first time
                // the asterisk is then substituted for the correct letter in the correct position
            } else if (word.contains(String.valueOf(userInput))) {

                for (PrintWriter writer : gameLounge.writers) {
                    writer.println("\nGood job bro, the letter " + userInput + " is in the word.");
                }
                enteredLetters[emptyPosition] = userInput;
                return 1;

                //else the letter entered is not in the word
                //which returns a 0 and therefore the client looses a life
            } else {
                for (PrintWriter writer : gameLounge.writers) {
                    writer.println("\n\nSorry bro, the letter " + userInput + " is not in the word.");
                }
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
                for (PrintWriter writer : gameLounge.writers) {
                    writer.println(letter);
                }

            } else {
                for (PrintWriter writer : gameLounge.writers) {
                    writer.println(("*"));
                }

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
