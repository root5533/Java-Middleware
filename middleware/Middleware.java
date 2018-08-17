import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Collections;
import java.util.HashMap;
import java.util.*;


class Middleware {

    static Middleware middleware = new Middleware();

    Map<String, String[]> Service_Directory;

    int mware_port = 9090;
    // String mware_host = "localhost";

    ServerSocket listener;
    Socket socket;

    PrintWriter out;
    BufferedReader in;


    private Middleware() { }

    public static Middleware getMiddleware() {
        return middleware;
    }


    public static void main(String[] args) {

        try {
            middleware.initialize();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public void registerService(String serviceName, String host, String portNumber) {
        Service_Directory = new HashMap<String, String[]>();
        Service_Directory.put(serviceName, new String[]{host, portNumber});
    }

    public void initialize() throws Exception {
        listener = new ServerSocket(mware_port);
        try {
            while (true) {
                socket = listener.accept();
                try {
                    out = new PrintWriter(socket.getOutputStream(), true);
                    in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                    out.println("Connection with Middleware Success");
                    try {
                        String findRequest = in.readLine();
                        System.out.println(findRequest);
                        middleware.executeService(findRequest);
                    } catch (Exception e) {
                        throw e;
                    }
                } finally {
                    socket.close();
                }
            }
        } finally {
            listener.close();
        }
    }

    private void executeService(String findRequest) throws Exception {
        middleware.registerService("addservice", "localhost", "9999");
        String request[] = findRequest.split(",");
        System.out.println(request[1]);
        if ( Service_Directory.containsKey(request[0]) ) {
            // out.println(findRequest + " service is available");
            String value[] = Service_Directory.get(request[0]);
            middleware.connectService(value, findRequest);
        } else {
            out.println(findRequest + " service is not available");
        }
    }

    private void connectService(String service[], String request) throws Exception {
        Socket service_socket = new Socket(service[0], Integer.parseInt(service[1]));
        try {
            PrintWriter output = new PrintWriter(service_socket.getOutputStream(), true);
            BufferedReader input = new BufferedReader(new InputStreamReader(service_socket.getInputStream()));
            output.println(request);
            String response = input.readLine();
            out.println(response);
        } catch (Exception e) {
            throw e;
        } finally {
            service_socket.close();
        }
        
    }


}