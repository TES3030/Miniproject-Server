import java.io.IOException;
import java.io.PrintWriter;
import java.util.*;
/**
 * Created by Tobias on 29/10/2017.
 */
public class Gamelounge {

    //static ArrayList<PlayerObject> playerArray = null;

    static HashSet<PrintWriter> writers = new HashSet<PrintWriter>();
    static HashSet<String> nickNameList = new HashSet<String>();

    static boolean areClientsReady = false;//testing: false starts the chat, true starts the game
    static boolean gameLoungeRunning = true;
    static boolean chatTerminated = false;


    static void clientInfo() throws IOException {

        //system.out means sending this text to client
        // Introduction to the game lounge
        for (PrintWriter writer : writers) {

            writer.println("---------------------------------------------------------------");
            writer.println("Welcome to the game lounge! Wait here until everyone is ready to begin.\n" +
                " > Type \"start\" if you want the game to start :)\n" +
                " > Type \"exit\" if you want to disconnect");
            writer.println("---------------------------------------------------------------\n");
    }
        //Check start booleans for every client
        //If 100% has their start booleans = start, startgame + gameHasStarted + gameRunning

    }

    static void checkForWord(String input, PrintWriter out, String nickName) {
        //this method simply checks whether someone has written "start" in the gamelounge
        //when game starts, remember to cut the server listener. no more clients should join

        switch (input) {

            case "start":
                    System.out.println("keyword \"start\" found!");
                areClientsReady = true;
                chatTerminated = true;
                for (PrintWriter writer : writers) {
                    writer.println("\nBros, the game has begun!");
                }
                break;

            case "exit":
                System.out.println("keyword \"exit\" found!");
                out.println("Enter any key to shutdown");//debug
                out.println("gameLoungeRunning is false");// to each client reader with a string
                gameLoungeRunning = false;
                chatTerminated = true;
                break;

            default:
                //if neither start or exit, broadcast to all clients
                if (!chatTerminated) {
                    for (PrintWriter writer : writers) {
                        writer.println("\nThe Bro " + nickName + " says: " + input);
                    }
                }
                break;
        }
    }

    static void clientJoins(String _ip, String _nick, PrintWriter out){
        //create playerArray if its null (on launch)

        //adds inputted nickName and PW to lists
        nickNameList.add(_nick);
        writers.add(out);

        for(PrintWriter writer : writers) {
            // display when a new client joins
            // send text to client instead of system out
            writer.println("\nA new bro joined the lounge!");
            writer.println("The bros currently in the lounge are: ");
            //prints lists of players
        }
        for (String s : nickNameList) {
            out.println((s) + " ");
        }
        out.println();

    }
}
