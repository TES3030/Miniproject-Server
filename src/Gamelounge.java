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
    private static String keywordStart = ("start");
    private static String keywordExit = ("exit");


    static void clientInfo(PrintWriter out) throws IOException {

        //system.out means sending this text to client
        // Introduction to the game lounge
        out.println("---------------------------------------------------------------");
        out.println("Welcome to the game lounge! Here you can see all bros who have joined \n " +
                "Wait here until everyone is ready to begin. Type \"start\" if you want the game to start :)");
        out.println("---------------------------------------------------------------\n");


        //Check start booleans for every client
        //If 100% has their start booleans = start, startgame + gameHasStarted + gameRunning

    }

    static void checkForStart(String input, PrintWriter out){
        //this method simply checks whether someone has written "start" in the gamelounge
        //when game starts, remember to cut the server listener. no more clients should join


        if ( input.toLowerCase().indexOf(keywordStart.toLowerCase()) != -1 ) {
            System.out.println("keyword \"start\" found!");
            areClientsReady = true;
            chatTerminated = true;

        }

        if ( input.toLowerCase().indexOf(keywordExit.toLowerCase()) != -1 ) {
            System.out.println("keyword \"exit\" found!");
            out.println("Press any key to shutdown");//debug
            out.println("gameLoungeRunning is false");// to each client reader with a string
            gameLoungeRunning = false;
            chatTerminated = true;


        } else {
            System.out.println("\"exit\" not found");
        }
    }

    static void clientJoins(String _ip, String _nick, PrintWriter out){
        //create playerArray if its null (on launch)

        //adds inputted nickName and PW to lists
        nickNameList.add(_nick);
        writers.add(out);


        // display when a new client joins
        // send text to client instead of system out
        out.println("\nA new bro joined the lounge!");
        out.println("The bros currently in the lounge are: ");

        //prints lists of players
        for (String s : nickNameList) {
            out.println((s) + " ");
        }
        out.println();

    }
}
