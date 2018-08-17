import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * Server
 */
public class Server extends Thread {

    private Socket socket;

    private Server( Socket socket ) {

        this.socket = socket;
        System.out.println("New client connection!");

    }

    @Override
    public void run() {
        try {
            PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
            BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            String request = in.readLine();
            out.println(request + " was requested by server");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) throws Exception {
        
        int port = 9999;
        System.out.println("Server started");
        // registerServer();
        System.out.println("Server registered");

        ServerSocket listener = new ServerSocket(port);

        try {
            while(true) {
                new Server(listener.accept()).start();
            }
        } catch (Exception e) {
            throw e;
        } finally {
            listener.close();
        }

    }

}