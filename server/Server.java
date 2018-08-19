import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.Buffer;

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
            String values[] = request.split(",");
            String reply = String.valueOf( Integer.parseInt(values[1]) + Integer.parseInt(values[2]) );
            out.println(reply);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) throws Exception {
        
        int port = 9999;
        System.out.println("Server started");
        registerServer();

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

    private static void registerServer() throws Exception {
        Socket mware_socket = new Socket("localhost", 9090);
        try {
            PrintWriter output = new PrintWriter(mware_socket.getOutputStream(), true);
            BufferedReader input = new BufferedReader(new InputStreamReader(mware_socket.getInputStream()));
            System.out.println(input.readLine());
            String message = "registerService,addService,localhost,9999";
            output.println(message);
            String response = input.readLine();
            System.out.println(response);
        } finally {
            mware_socket.close();
        }
    }

}