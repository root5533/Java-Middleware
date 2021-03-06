import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.Buffer;
import java.io.InputStreamReader; 
import java.io.UnsupportedEncodingException; 
import java.security.NoSuchAlgorithmException; 
import java.io.BufferedReader; 
import java.io.IOException; 

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
            String reply = "";
            if(values[0].equals("addService")){
                reply = String.valueOf(addNumbers(Integer.parseInt(values[1]),Integer.parseInt(values[2])) );
                out.println(reply);
            }
            else if(values[0].equals("encryptionService")){
                reply = SHA1Encryption(values[1]);
                out.println(reply);
            }
           
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public int addNumbers(int nm1, int nm2){
        return nm1 + nm2 ;
    }

    public String SHA1Encryption(String rawString){
        String encryptionText = " ";
        try { 
            encryptionText =  AeSimpleSHA1.SHA1(rawString);
            return encryptionText;
        } catch (NoSuchAlgorithmException e) { 
            e.printStackTrace();
        } catch (UnsupportedEncodingException e) { 
            e.printStackTrace();
        } 

        return encryptionText;
    }

    public static void main(String[] args) throws Exception {
        
        int port = 9999;
        System.out.println("Server started");
        registerServer("addService");
        registerServer("encryptionService");
       

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

    private static void registerServer(String servicename) throws Exception {
        Socket mware_socket = new Socket("localhost", 9090);
    
        try {
            PrintWriter output = new PrintWriter(mware_socket.getOutputStream(), true);
            BufferedReader input = new BufferedReader(new InputStreamReader(mware_socket.getInputStream()));
            System.out.println(input.readLine());
            String message = "registerService,"+servicename+",localhost,9999";
            output.println(message);
            String response = input.readLine();
            System.out.println(response);

           
        } finally {
            mware_socket.close();
            
        }
    }

}