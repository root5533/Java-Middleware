import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

class Client {

    String service;
    BufferedReader input;
    PrintWriter output;
    Socket socket;
    int mware_port = 9090;
    String host = "localhost";

    public Client() {
        try {
            socket = new Socket("localhost", 9090);
            input = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            output = new PrintWriter(socket.getOutputStream(), true);
            String answer = input.readLine();
            System.out.println(answer);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) throws IOException {
        Client client = new Client();
        String a[] = {"1", "2"};
        client.getService("addservice", a);
    }    

    public void getService(String service, String val[]) {
        try {
            for (int i = 0; i < val.length; i++) {
                service = service + "," + val[i];
            }
            output.println(service);
            String response;
            response = input.readLine();
            System.out.println(response);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}