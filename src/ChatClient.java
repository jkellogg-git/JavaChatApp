import java.io.*;
import java.net.*;
import java.util.function.Consumer;

/**
 * A client implementation for a chat application using TCP/IP sockets.
 */
public class ChatClient {
    // Socket for network communication
    private Socket socket;
    // Reader for incoming messages from the server
    private BufferedReader in;
    // Writer for sending messages to the server
    private PrintWriter out;
    // Callback function to handle received messages
    private Consumer<String> onMessageReceived;

    /**
     * Initializes a new chat client.
     * 
     * @param serverAddress    The IP address or hostname of the server
     * @param serverPort      The port number the server is listening on
     * @param onMessageReceived Callback function to handle incoming messages
     * @throws IOException    If connection to the server fails
     */
    public ChatClient(InetAddress serverAddress, int serverPort, Consumer<String> onMessageReceived) throws IOException {
        this.socket = new Socket(serverAddress, serverPort);
        this.in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        this.out = new PrintWriter(socket.getOutputStream(), true);
        this.onMessageReceived = onMessageReceived;
    }

    /**
     * Sends a message to the server.
     * 
     * @param msg The message to be sent
     */
    public void sendMessage(String msg) {
        out.println(msg);
    }

    /**
     * Starts a background thread that continuously listens for incoming messages.
     * When a message is received, it is passed to the onMessageReceived callback.
     */
    public void startClient() {
        new Thread(() -> {
            try {
                String line;
                // Keep reading messages until the connection is closed
                while ((line = in.readLine()) != null) {
                    onMessageReceived.accept(line);
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }).start();
    }
}