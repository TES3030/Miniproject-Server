import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;

/**
 * Created by Tobias on 29/10/2017.
 */
public class Gamelounge {
    /*static PrintWriter out;

    Gamelounge(PrintWriter out){
        this.out = out;
    }
*/
    static ArrayList<PlayerObject> playerArray = null;
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
        //when all clients are ready, cut the server listener. no more clients should join

        //need to track whether individual players have typed start
            //ie need to track players output while in lounge and search for in.line "start"

        //this method simply checks whether all objects in the playerArray have their iAmReady bool set to true

        for(int i=0;i<playerArray.size();i++){
            if(!((playerArray.get(i)).iAmReady)){
                return false;
            }
        }//if all != false, return true
        // Temp fix, used in the Server.java to determine when to stop listening
        areClientsReady = true;
            return true;
    }

    static void clientJoins(String _ip, String _nick, PrintWriter out){



        //create playerArray if its null (on launch)
        if (playerArray==null)
            playerArray = new ArrayList<PlayerObject>();

        //new player with nick and ip created + added to list
        playerArray.add(new PlayerObject(_ip,_nick));



        // display when a new client joins
        // send text to client instead of system out
        out.println("\nA new player joined the lounge!");
        out.println("The players currently in the lounge are:");

        //prints lists of players
        for(int i=0;i<playerArray.size();i++){
            out.println((playerArray.get(i)).nickname + " ");
                    if((playerArray.get(i)).iAmReady){
                out.print("- READY");
            }
        }
        out.println();

    }
}
