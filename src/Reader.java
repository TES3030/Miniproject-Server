import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

/**
 * Created by Tobias on 31-10-2017.
 */
public class Reader implements Runnable
{

    private InputStream input;
    private Client c;

    Reader(InputStream in, Client c){
        input = in;
        this.c = c;

    }

    @Override
    public void run() {
        try{
            //Read from server
            BufferedReader reader = new BufferedReader(new InputStreamReader(input));
            String fromServer;
            while(true){
                while((fromServer = reader.readLine()) == null){
                        //Wait until server says something cool
                }
                protocolDecoder(fromServer);
            }

        }catch (IOException ex){}
    }

    public void protocolDecoder(String protoInput){
        switch(protoInput){
            case "*":
                System.out.print(protoInput);
                break;

            case " -> ":
                System.out.print(protoInput);
                break;

            case "gameRunning is false":
                c.gameRunning = false;
                break;

            case "gameRunning is true"://if server receives start
                break;

            case "gameLoungeRunning is false"://if server receives exit
                break;

            default:
                    System.out.println(protoInput);
                    break;


        }
}

}