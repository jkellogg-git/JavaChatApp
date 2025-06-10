import java.io.*;
import java.net.*;

/**
 * A simple chat client that connects to a chat server using TCP/IP sockets.
 * The client can send messages to the server and receive responses.
 */
public class ChatClient {
    // Socket for network communication
    private Socket socket = null;
    // Reader for console input
    private BufferedReader inputConsole = null;
    // Writer to send messages to server
    private PrintWriter out = null;
    // Reader to receive messages from server
    private BufferedReader in = null;

    /**
     * Constructor that initializes the chat client and handles the main communication loop
     * @param address The server's IP address
     * @param port The server's port number
     * @throws IOException If there's an error with the network communication
     */
    public ChatClient(String address, int port) throws IOException {
        try {
            // Establish connection to the server
            socket = new Socket(address, port);
            System.out.println("Connected to the chat server");

            // Initialize I/O streams
            inputConsole = new BufferedReader(new InputStreamReader(System.in));
            out = new PrintWriter(socket.getOutputStream(), true);
            in = new BufferedReader(new InputStreamReader(socket.getInputStream()));

            // Main communication loop
            String line = "";
            while (!line.equals("exit")) {
                // Read input from console
                line = inputConsole.readLine();
                // Send message to server
                out.println(line);
                // Print server's response
                System.out.println(in.readLine());
            }

            // Clean up resources
            socket.close();
            inputConsole.close();
            out.close();
        } catch (UnknownHostException e) {
            System.out.println("Host unknown: " + e.getMessage());
        }  catch (IOException e) {
            System.out.println("I/O Error: " + e.getMessage());
        }
    }

    /**
     * Main method to start the chat client
     * @param args Command line arguments (not used)
     * @throws IOException If there's an error initializing the client
     */
    public static void main(String[] args) throws IOException {
        ChatClient chatClient = new ChatClient("127.0.0.1", 5000);
    }
}