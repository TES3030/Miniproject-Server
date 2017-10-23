import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;

public class Server {

    public static void main(String[] args) {

        try {
            Socket Socket = new Socket(InetAddress.getByName("localhost"), 50000);
            PrintWriter out = new PrintWriter(Socket.getOutputStream(), true);
            BufferedReader in = new BufferedReader(new InputStreamReader(Socket.getInputStream()));
            out.println("I am a client");
            String l = in.readLine();
            System.out.println(l);
            in.close();
            out.close();
            Socket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        // Getting the IP address
        InetAddress ip;
        try {

            ip = InetAddress.getLocalHost();
            System.out.println("Current IP address : " + ip.getHostAddress());

        } catch (UnknownHostException e) {

            e.printStackTrace();
            
        }
    }
}
