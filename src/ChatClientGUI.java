import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.IOException;
import java.net.InetAddress;

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
    // Add CustomTitleBar as a field
    private CustomTitleBar titleBar;
    
    /**
     * Constructor: Initializes the chat window and establishes connection to the server
     */
    public ChatClientGUI(String ipAddress, String name, ChatUtils.ServerOption serverOption) {
        super("Chat Application - " + name);
        setSize(800, 600);
        setUndecorated(true);
        
        // Create main content panel
        JPanel contentPanel = new JPanel(new BorderLayout());
        
        // Initialize and add the custom title bar
        titleBar = new CustomTitleBar(this, name);
        contentPanel.add(titleBar, BorderLayout.NORTH);
        
        // Create the gradient panel (your existing code)
        JPanel gradientPanel = new JPanel(new BorderLayout()) {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Graphics2D g2d = (Graphics2D) g;
                int width = getWidth();
                int height = getHeight();
                Color color1 = new Color(0, 102, 204);  // Blue
                Color color2 = new Color(102, 0, 204);  // Purple
                GradientPaint gp = new GradientPaint(0, 0, color1, width, height, color2);
                g2d.setPaint(gp);
                g2d.fillRect(0, 0, width, height);
            }
        };
        gradientPanel.setOpaque(false);
        
        // Add the gradient panel to the content panel
        contentPanel.add(gradientPanel, BorderLayout.CENTER);
        
        // Set the content pane to our new panel
        setContentPane(contentPanel);

        setDefaultCloseOperation(EXIT_ON_CLOSE);
        // Initialize the message display area
        messageArea = new JTextArea();
        messageArea.setEditable(false);  // Make it read-only

        // Style the message area
        messageArea.setBackground(new Color(0, 0, 0));  // Black background
        messageArea.setForeground(new Color(0, 255, 0));  // Electric green text
        messageArea.setFont(new Font("Monospaced", Font.PLAIN, 12));
        messageArea.setCaretColor(new Color(0, 255, 0));

        // Create a styled scroll pane
        JScrollPane scrollPane = new JScrollPane(messageArea);
        scrollPane.getViewport().setBackground(new Color(0, 0, 0));
        scrollPane.setBorder(BorderFactory.createEmptyBorder());

        // Provide welcome message based on ServerOption selected
        String welcomeMessageHost = "Welcome to the chat! You are hosting. Share your IP Address to other users: "
                + ipAddress + "\n";
        String welcomeMessageJoin = "Welcome to the chat! You joined server: " + ipAddress + "\n";
        switch (serverOption) {
            case HOST -> messageArea.append(ChatUtils.MessageSenderInfo.SYSTEM.messagePrefix() + welcomeMessageHost);
            case JOIN -> messageArea.append(ChatUtils.MessageSenderInfo.USER.messagePrefix() + welcomeMessageJoin);
        }

        // Initialize and configure the input text field
        textField = new JTextField();
        // Add listener to handle message sending when Enter is pressed
        textField.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String message = ChatUtils.MessageSenderInfo.USER.messagePrefix() + name + ": "
                        + textField.getText();
                client.sendMessage(message);
                textField.setText("");  // Clear the input field after sending
            }
        });

        // Style the text field
        textField.setBackground(new Color(0, 0, 0));
        textField.setForeground(new Color(0, 255, 0));
        textField.setCaretColor(new Color(0, 255, 0));
        textField.setFont(new Font("Monospaced", Font.PLAIN, 12));
        textField.setOpaque(true);

        // Add placeholder using FocusListener
        textField.setText("Type your message...");
        textField.setForeground(new Color(0, 255, 0));  // Initial color for placeholder

        textField.addFocusListener(new FocusListener() {
            @Override
            public void focusGained(FocusEvent e) {
                if (textField.getText().equals("Type your message...")) {
                    textField.setText("");
                    textField.setForeground(new Color(0, 255, 0));  // Bright green when typing
                }
            }

            @Override
            public void focusLost(FocusEvent e) {
                if (textField.getText().isEmpty()) {
                    textField.setText("Type your message...");
                    textField.setForeground(new Color(0, 255, 0));  // White for placeholder
                }
            }
        });

        // Create a line border with the electric green color
        textField.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(0, 255, 0)),  // Outer border in green
            BorderFactory.createEmptyBorder(0, 10, 0, 0)  // Inner padding (top, left, bottom, right)
        ));

        // Initialize sendMessage button
        JButton sendMessageButton = new JButton("Send");
        sendMessageButton.addActionListener(e -> {
            String message = ChatUtils.MessageSenderInfo.USER.messagePrefix() + name + ": "
                    + textField.getText();
            client.sendMessage(message);
            textField.setText("");
        });

        sendMessageButton.setBackground(new Color(0, 0, 0));
        sendMessageButton.setForeground(new Color(0, 255, 0));
        sendMessageButton.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(0, 255, 0), 1),
                BorderFactory.createEmptyBorder(5, 10, 5, 10)
        ));

        sendMessageButton.setFocusPainted(false);
        sendMessageButton.setOpaque(true);


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

        // Style the exit button
        exitButton.setBackground(new Color(0, 0, 0));
        exitButton.setForeground(new Color(0, 255, 0));
        exitButton.setFocusPainted(false);
        exitButton.setBorderPainted(false);
        exitButton.setOpaque(true);

        // Style the bottom panel
        JPanel bottomPanel = new JPanel(new BorderLayout());
        bottomPanel.setBackground(new Color(0, 0, 0));  // Set background for bottom panel
        bottomPanel.add(textField, BorderLayout.CENTER);
        bottomPanel.add(exitButton, BorderLayout.EAST);
        bottomPanel.add(sendMessageButton, BorderLayout.EAST);

        gradientPanel.add(scrollPane, BorderLayout.CENTER);
        gradientPanel.add(bottomPanel, BorderLayout.SOUTH);
        
        // Add a green border to the main window
        getRootPane().setBorder(BorderFactory.createLineBorder(new Color(0, 255, 0), 2));
        
        // The rest of your existing constructor code remains the same...

        try {
            // Initialize chat client with localhost connection
            this.client = new ChatClient(InetAddress.getByName(ipAddress), 5000, this::onMessageReceived);
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

    public JButton getExitButton() {
        return exitButton;
    }


    /**
     * Main method to start the application
     * Creates and shows the GUI on the Event Dispatch Thread
     */
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            new StartScreen().setVisible(true);
        });
    }
}