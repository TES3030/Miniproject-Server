import java.io.IOException;

/**
 * Created by Tobias on 29/10/2017.
 */
public class Gamelounge {
    static void clientInfo() throws IOException {
        //system.out means sending this text to client
        // Introduction to the game lounge
        System.out.println("---------------------------------------------------------------");
        System.out.println("Welcome to the game lounge! Here you can see all players who have joined \n " +
                "Wait here until someone starts the game. Type \"start\" if you want the game to start :)");
        System.out.println("---------------------------------------------------------------");
        // display when a new client joins
        System.out.println("A new player joined the lounge!");
        System.out.println("The players currently in the lounge are:\n" + /*clientThreadName + */"\n");

        //Check start booleans for every client
        //If 100% has their start booleans = start, startgame + gameHasStarted + gameRunning

    }
}
