import java.io.IOException;
import java.io.PrintWriter;
import java.util.*;
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
<<<<<<< HEAD
    static HashSet<PrintWriter> writers = new HashSet<PrintWriter>();
    static HashSet<String> nickNameList = new HashSet<String>();
=======
    static boolean areClientsReady = false;

>>>>>>> master

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

    static void checkingForStart(){

        //need to track whether individual players have typed start
        //ie need to track players output while in lounge and search for in.line "start"

    }

    static boolean areClientsReady(){
        //this method simply checks whether someone has written "start" in the gamelounge
        //when game starts, remember to cut the server listener. no more clients should join

        //outdated code
        /*
        for(int i=0;i<playerArray.size();i++){
            if(!((playerArray.get(i)).iAmReady)){
                return false;
            }
        }//if all != false, return true
<<<<<<< HEAD
        */

        return false;//testing: false starts the chat, true starts the game
=======
        // Temp fix, used in the Server.java to determine when to stop listening
        areClientsReady = true;
            return true;
>>>>>>> master
    }

    static void clientJoins(String _ip, String _nick, PrintWriter out){
        //create playerArray if its null (on launch)
        /*
        if (playerArray==null)
            playerArray = new ArrayList<PlayerObject>();

        //new player with nick and ip created + added to list
        playerArray.add(new PlayerObject(_ip,_nick));
        */

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

        /*
        for(int i=0;i<nickNameList.size();i++){
            out.println(nickNameList(i) + " ");
                    if(nickNameList(i).iAmReady){
                out.print("- READY");
            }
        }
        out.println();
        */

    }
}
