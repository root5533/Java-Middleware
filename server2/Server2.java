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
import java.util.Random;

/**
 * Server
 */
public class Server2 extends Thread {

    private Socket socket;

    private Server2( Socket socket ) {

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
            if(values[0].equals("findGCDService")){
                reply = String.valueOf(findGCD(Integer.parseInt(values[1]),Integer.parseInt(values[2])) );
                out.println(reply);
            }
            else if(values[0].equals("guestLuckyNumberService")){
                reply = matchNumber(Integer.parseInt(values[1]));
                out.println(reply);
            }
           
           
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public int findGCD(int nm1, int nm2){
        int gcd = 1;

        for(int i = 1; i <= nm1 && i <= nm2; ++i)
        {
            // Checks if i is factor of both integers
            if(nm1 % i==0 && nm2 % i==0)
                gcd = i;
        }

        return gcd;
    }

    public String matchNumber(int nm){
        Random numberrandom = new Random();
        int luckyNumber = numberrandom.nextInt(10 - 1 + 1) + 1;
        String result = "You lost";

        if(nm > 10 || nm < 0){
            result = "Plese guest the number between 1 to 10";
        }
        else if(nm == luckyNumber){
            result = "Congratulations!!You won.You guest the right number";
        }
        else{
            result = "Soory!! you lost. Try again..";
        }

        return result;

    }

    public static void main(String[] args) throws Exception {
        
        int port = 9998;
        System.out.println("Server started");
        registerServer("findGCDService");
        registerServer("guestLuckyNumberService");
       

        ServerSocket listener = new ServerSocket(port);

        try {
            while(true) {
                new Server2(listener.accept()).start();
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
            String message = "registerService,"+servicename+",localhost,9998";
            output.println(message);
            String response = input.readLine();
            System.out.println(response);

           
        } finally {
            mware_socket.close();
            
        }
    }

}