import javax.swing.*;
import java.awt.*;
import java.net.InetAddress;

public class StartScreen extends JFrame {
    private String selectedIP;
    private JTextField usernameField;
    
    public StartScreen() {
        super("Chat Application - Start");
        setSize(300, 200);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        
        // Create main panel with a vertical BoxLayout instead of GridLayout
        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));
        mainPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        
        // Welcome label with some styling
        JLabel welcomeLabel = new JLabel("Welcome to Chat Application");
        welcomeLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        welcomeLabel.setFont(new Font(welcomeLabel.getFont().getName(), Font.BOLD, 14));
        
        // Add some vertical spacing
        mainPanel.add(welcomeLabel);
        mainPanel.add(Box.createRigidArea(new Dimension(0, 20)));
        
        // Username panel
        JPanel usernamePanel = new JPanel(new BorderLayout(5, 0));
        usernamePanel.setMaximumSize(new Dimension(Integer.MAX_VALUE, 25)); // Control height
        usernamePanel.add(new JLabel("Username: "), BorderLayout.WEST);
        usernameField = new JTextField();
        usernamePanel.add(usernameField, BorderLayout.CENTER);
        
        // Buttons
        JButton hostButton = new JButton("Host a Chat");
        JButton joinButton = new JButton("Join a Chat");
        
        // Make buttons the same size
        Dimension buttonSize = new Dimension(Integer.MAX_VALUE, 30);
        hostButton.setMaximumSize(buttonSize);
        joinButton.setMaximumSize(buttonSize);
        
        // Center-align buttons
        hostButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        joinButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        
        hostButton.addActionListener(e -> {
            if (validateUsername()) {
                selectedIP = ChatUtils.getLocalIPAddress();
                startChat(selectedIP, usernameField.getText().trim(), ChatUtils.ServerOption.HOST);
                dispose();
            }
        });
        
        joinButton.addActionListener(e -> {
            if (validateUsername()) {
                String ipAddress = JOptionPane.showInputDialog(
                    this,
                    "Enter host IP address:",
                    "Join Chat",
                    JOptionPane.PLAIN_MESSAGE
                );
                
                if (ipAddress != null && !ipAddress.trim().isEmpty()) {
                    try {
                        InetAddress.getByName(ipAddress);
                        startChat(ipAddress, usernameField.getText().trim(), ChatUtils.ServerOption.JOIN);
                        dispose();
                    } catch (Exception ex) {
                        JOptionPane.showMessageDialog(
                            this,
                            "Invalid IP address format",
                            "Error",
                            JOptionPane.ERROR_MESSAGE
                        );
                    }
                }
            }
        });
        
        // Add components to main panel with spacing
        mainPanel.add(usernamePanel);
        mainPanel.add(Box.createRigidArea(new Dimension(0, 15)));
        mainPanel.add(hostButton);
        mainPanel.add(Box.createRigidArea(new Dimension(0, 10)));
        mainPanel.add(joinButton);
        
        add(mainPanel);
    }
    
    private boolean validateUsername() {
        String username = usernameField.getText().trim();
        if (username.isEmpty()) {
            JOptionPane.showMessageDialog(
                this,
                "Please enter a username",
                "Error",
                JOptionPane.ERROR_MESSAGE
            );
            usernameField.requestFocus();
            return false;
        }
        return true;
    }
    
    private void startChat(String ipAddress, String username, ChatUtils.ServerOption serverOption) {
        SwingUtilities.invokeLater(() -> {
            ChatClientGUI chatClient = new ChatClientGUI(ipAddress, username, serverOption);
            chatClient.setVisible(true);
        });
    }
    
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            new StartScreen().setVisible(true);
        });
    }
}