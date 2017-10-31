import java.io.IOException;
import java.io.PrintWriter;
import java.util.*;
/**
 * Created by Tobias on 29/10/2017.
 */
public class Gamelounge {

    static ArrayList<PlayerObject> playerArray = null;

    static HashSet<PrintWriter> writers = new HashSet<PrintWriter>();
    static HashSet<String> nickNameList = new HashSet<String>();

    static boolean areClientsReady = false;


    static void clientInfo(PrintWriter out) throws IOException {

        //system.out means sending this text to client
        // Introduction to the game lounge
        System.out.println("---------------------------------------------------------------");
        System.out.println("Welcome to the game lounge! Here you can see all players who have joined \n " +
                "Wait here until everyone is ready to begin. Type \"start\" if you want the game to start :)");
        System.out.println("---------------------------------------------------------------");


        //Check start booleans for every client
        //If 100% has their start booleans = start, startgame + gameHasStarted + gameRunning

    }

    static boolean areClientsReady(){
        //this method simply checks whether someone has written "start" in the gamelounge
        //when game starts, remember to cut the server listener. no more clients should join

        // Temp fix, used in the Server.java to determine when to stop listening
        areClientsReady = true;

        return false;//testing: false starts the chat, true starts the game

    }

    static void clientJoins(String _ip, String _nick, PrintWriter out){
        //create playerArray if its null (on launch)

        //adds inputted nickName and PW to lists
        nickNameList.add(_nick);
        writers.add(out);

        // display when a new client joins
        // send text to client instead of system out
        out.println("\nA new player joined the lounge!");
        out.println("The players currently in the lounge are:");

        //prints lists of players
        for (String s : nickNameList) {
            out.println((s) + " ");
        }
        out.println();

    }
}
