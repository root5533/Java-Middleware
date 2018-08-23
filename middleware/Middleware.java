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
        System.out.println("Register service " + serviceName);
        Service_Directory = new HashMap<String, String[]>();
        Service_Directory.put(serviceName, new String[]{host, portNumber});
        out.println("Service : " + serviceName + " registered");
    }

    public void initialize() throws Exception {
        System.out.println("Middleware service started");
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
                        System.out.println("new request : " + findRequest);
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
        String request[] = findRequest.split(",");
        if (request[0].equalsIgnoreCase("registerService")) {
            middleware.registerService(request[1], request[2], request[3]);
        }
        if ( Service_Directory.containsKey(request[0]) ) {
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
            out.println("Server Response : " + response);
        } catch (Exception e) {
            throw e;
        } finally {
            service_socket.close();
        }
        
    }


}