import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * A GUI client application for a chat system using Swing components.
 * This class creates a window with a text area for messages and an input field for sending messages.
 */
public class ChatClientGUI extends JFrame {
    // GUI Components
    private JTextArea messageArea;    // Area to display chat messages
    private JTextField textField;      // Input field for new messages
    private ChatClient client;         // Handles network communication
    private JButton exitButton;
    /**
     * Constructor: Initializes the chat window and establishes connection to the server
     */
    public ChatClientGUI() {
        super("Chat Application");
        setSize(400, 500);
        setDefaultCloseOperation(EXIT_ON_CLOSE);

        // Initialize the message display area
        messageArea = new JTextArea();
        messageArea.setEditable(false);  // Make it read-only

        // Add scrollable message area in the center
        add(new JScrollPane(messageArea), BorderLayout.CENTER);

        // Prompt user for name
        String name = JOptionPane.showInputDialog(this, "Enter your name", "Name", JOptionPane.PLAIN_MESSAGE);
        this.setTitle("Chat Application - " + name);
        // Initialize and configure the input text field
        textField = new JTextField();
        // Add listener to handle message sending when Enter is pressed
        textField.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String message = "[" + new SimpleDateFormat("HH:mm:ss").format(new Date()) + "] " + name + ": "
                        + textField.getText();
                client.sendMessage(message);
                textField.setText("");  // Clear the input field after sending
            }
        });
        // Add the text field at the bottom of the window
        add(textField, BorderLayout.SOUTH);

        // Initialize exit button
        exitButton = new JButton("Exit");
        exitButton.addActionListener(e ->
                {
                    String depatureMessage = name + " has left the chat.";
                    client.sendMessage(depatureMessage);
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException ex) {
                        Thread.currentThread().interrupt();
                    }
                    System.exit(0);
                }
        );
        JPanel bottomPanel = new JPanel(new BorderLayout());
        bottomPanel.add(textField, BorderLayout.CENTER);
        bottomPanel.add(exitButton, BorderLayout.EAST);
        add(bottomPanel, BorderLayout.SOUTH);

        try {
            // Initialize chat client with localhost connection
            this.client = new ChatClient("127.0.0.1", 5000, this::onMessageReceived);
            client.startClient();
        } catch (IOException e) {
            e.printStackTrace();
            // Show error dialog if connection fails
            JOptionPane.showMessageDialog(this, "Error when connecting to the server",
                    "Connection Error", JOptionPane.ERROR_MESSAGE);
            System.exit(1);  // Exit application on connection failure
        }
    }

    /**
     * Callback method to handle received messages
     * Updates the message area safely using SwingUtilities.invokeLater
     * @param message The received message to display
     */
    private void onMessageReceived(String message) {
        SwingUtilities.invokeLater(() -> messageArea.append(message + "\n"));
    }

    /**
     * Main method to start the application
     * Creates and shows the GUI on the Event Dispatch Thread
     */
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            new ChatClientGUI().setVisible(true);
        });
    }
}