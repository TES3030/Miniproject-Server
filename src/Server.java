import java.io.IOException;
import java.net.Inet4Address;
import java.net.ServerSocket;
import java.net.Socket;


public class Server {

    public static void main(String[] args) throws Exception {

        //try {

            ServerSocket serverSocket = new ServerSocket(3000);
            System.out.println("Listening!");

            while (true) {

                //before gamelounge is initialized, setting up clients
                System.out.println("IP address: " + Inet4Address.getLocalHost().getHostAddress());  //The IP address user should connect to
                Socket client = serverSocket.accept();//server waits for clients to establish connection

                //when all clients have typed start it should stop the listening loop and jump to game (handler.start)

                System.out.println("SOMETHING CONNECTED");


                ServerThread handler = new ServerThread(client);
                handler.start();

            }

            /*
        } catch (IOException e) {
            e.printStackTrace();
        }*/
    }
}


    /*

            Socket clientSocket = serverSocket.accept();
            System.out.println("IP address: " + Inet4Address.getLocalHost().getHostAddress());  //The IP address user connected to
            System.out.println("Bro with ip address:" + Inet4Address.getLocalHost().getHostAddress() + " has joined the game");//this has to display "Client x has joined the server" in the client.

            PrintWriter out = new PrintWriter(clientSocket.getOutputStream(), true);
            InputStreamReader isr = new InputStreamReader(clientSocket.getInputStream());
            BufferedReader in = new BufferedReader(isr);
*/