import java.io.*;
import java.net.*;
import java.util.*;
import java.net.InetAddress;

/**
 * A simple multi-client chat server that accepts connections on port 5000.
 * The server maintains a list of connected clients and broadcasts messages
 * from any client to all connected clients.
 */

public class ChatServer {
    // List to keep track of connected clients
    private static List<ClientHandler> clients = new ArrayList<>();

    public static void main(String[] args) throws IOException {
        // Remove binding to specific IP and use 0.0.0.0 to listen on all interfaces
        ServerSocket serverSocket = new ServerSocket(5000);
        System.out.println("Server started on port 5000");
        System.out.println("Server IP addresses:");
        // Print all server IP addresses for convenience
        NetworkInterface.getNetworkInterfaces().asIterator().forEachRemaining(networkInterface -> {
            networkInterface.getInetAddresses().asIterator().forEachRemaining(address -> {
                if (!address.isLoopbackAddress() && !address.isLinkLocalAddress() && !address.isMulticastAddress()) {
                    System.out.println("  " + address.getHostAddress());
                }
            });
        });
        System.out.println("Waiting for clients...");

        while (true) {
            Socket clientSocket = serverSocket.accept();
            System.out.println("New client connected: " + clientSocket.getInetAddress());
            ClientHandler clientThread = new ClientHandler(clientSocket, clients);
            clients.add(clientThread);
            new Thread(clientThread).start();
        }
    }
}
/**
 * Handles communication with a single connected client.
 * Receives messages from the client and broadcasts them to all connected clients.
 */

class ClientHandler implements Runnable {
    private Socket clientSocket;
    private List<ClientHandler> clients;
    private PrintWriter out;
    private BufferedReader in;

    /**
     * Creates a new ClientHandler for a connected client.
     *
     * @param clientSocket The socket connection to the client
     * @param clients The list of all connected client handlers
     * @throws IOException If there's an error setting up the input/output streams
     */

    public ClientHandler(Socket clientSocket, List<ClientHandler> clients) throws IOException {
        this.clientSocket = clientSocket;
        this.clients = clients;
        this.out = new PrintWriter(clientSocket.getOutputStream(), true);
        this.in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
    }

    /**
     * Continuously reads messages from the client's input stream and broadcasts them to all connected clients.
     * The method runs in an infinite loop until the connection is terminated or an I/O error occurs.
     * It ensures the proper closure of resources like input/output streams and socket upon termination.
     *
     * The method performs the following:
     * 1. Reads incoming messages from the client.
     * 2. Broadcasts each message to all connected clients by writing to their output streams.
     * 3. Handles and logs any I/O exceptions that may occur during communication.
     * 4. Closes the client's input/output streams and socket connection in the `finally` block to release resources.
     */

    public void run() {
        try {
            String inputLine;
            while ((inputLine = in.readLine()) != null) {
                for (ClientHandler client : clients) {
                    client.out.println(inputLine);
                }
            }
        }  catch (IOException e) {
            System.out.println("An error occurred: " + e.getMessage());
        } finally {
            try {
                in.close();
                out.close();
                clientSocket.close();
            } catch (IOException e) {
                System.out.println("An error occurred: " + e.getMessage());
            }
        }
    }
}