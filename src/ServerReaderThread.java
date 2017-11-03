import java.io.*;

/**
 * Created by cocok on 03-11-2017.
 */
public class ServerReaderThread implements Runnable//reads from client
{
    private BufferedReader input;
    private ServerThread s;
    static Gamelounge g;

    ServerReaderThread(BufferedReader in, ServerThread s, Gamelounge g){
        input = in;
        this.s = s;
        this.g = g;

    }

    @Override
    public void run() {
        try{
            //Read from server
            //BufferedReader reader = new BufferedReader(new InputStreamReader(input));
            String fromClient;
            while(true){
                while((fromClient = input.readLine()) == null){
                    //Wait until server says something cool
                }
                protocolDecoder(fromClient);
            }

        }catch (IOException ex){}
    }

    public void protocolDecoder(String protoInput) {
        switch (protoInput) {
            case "start":
                System.out.println("keyword \"start\" found!");
                s.gameLounge.areClientsReady = true;
                s.gameLounge.chatTerminated = true;
                for (PrintWriter writer : s.gameLounge.writers) {
                    writer.println("\nBros, the game has begun!");
                }
                break;

            case "exit":
                System.out.println("keyword \"exit\" found!");
                for (PrintWriter writer : s.gameLounge.writers) {
                    writer.println("Enter any key to shutdown");//debug
                    writer.println("gameLoungeRunning is false");// to each client reader with a string
                }

                s.gameLounge.gameLoungeRunning = false;
                s.gameLounge.chatTerminated = true;
                break;

            default:
                //if neither start or exit, broadcast to all clients
                if (!s.gameLounge.chatTerminated) {
                    for (PrintWriter writer : s.gameLounge.writers) {
                        writer.println("\nThe Bro " + s.nickName + " says: " + input);
                    }
                }
                break;

        }
    }

}


