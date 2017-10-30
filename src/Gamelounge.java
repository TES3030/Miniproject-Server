import java.io.IOException;
import java.util.ArrayList;

/**
 * Created by Tobias on 29/10/2017.
 */
public class Gamelounge {
    static ArrayList<PlayerObject> playerArray = null;
    static void launchGameLounge(){
        if (playerArray!=null)
            playerArray = new ArrayList<PlayerObject>();
    }

    //array of player-objects,
        //each with a nickname
        //ready bool
        //ip address



    static void clientInfo() throws IOException {

        //system.out means sending this text to client
        // Introduction to the game lounge
        System.out.println("---------------------------------------------------------------");
        System.out.println("Welcome to the game lounge! Here you can see all players who have joined \n " +
                "Wait here until someone starts the game. Type \"start\" if you want the game to start :)");
        System.out.println("---------------------------------------------------------------");


        //Check start booleans for every client
        //If 100% has their start booleans = start, startgame + gameHasStarted + gameRunning

    }

    static boolean areClientsReady(){
        //check start bools for all playerobjects
        //when all clients are ready, cut the server listener. no more clients should join

        if(false)//if all = true, return true
            return true;
        else//else return false
            return false;
    }

    static void clientJoins(String _ip, String _nick){

        //client sends nickname and ip address
        //playerArray.add(PlayerObject(_ip,_nick));

        //make new player object with those 2 data
        //add new player to array
        //print array of joined players

        // display when a new client joins
        System.out.println("A new player joined the lounge!");
        System.out.println("The players currently in the lounge are:\n" + /* loop playerArray */"\n");
    }

}
