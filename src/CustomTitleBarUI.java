import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;

class CustomTitleBar extends JPanel {
    private JFrame frame;
    private Point initialClick;

    public CustomTitleBar(JFrame frame, String name) {
        this.frame = frame;
        setLayout(new BorderLayout());
        setBackground(Color.BLACK);
        setPreferredSize(new Dimension(frame.getWidth(), 30));

        // Title label
        JLabel title = new JLabel("Chat Application - " + name);
        title.setForeground(new Color(0, 255, 0));
        title.setBorder(BorderFactory.createEmptyBorder(0, 10, 0, 0));
        add(title, BorderLayout.WEST);

        // Button panel
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT, 0, 0));
        buttonPanel.setBackground(Color.BLACK);

        // Create window buttons
        JButton minimizeButton = createButton("Min");
        JButton maximizeButton = createButton("Max");
        JButton closeButton = createButton("Exit");

        buttonPanel.add(minimizeButton);
        buttonPanel.add(maximizeButton);
        buttonPanel.add(closeButton);
        add(buttonPanel, BorderLayout.EAST);

        // Add window dragging capability
        addMouseListener(new MouseAdapter() {
            public void mousePressed(MouseEvent e) {
                initialClick = e.getPoint();
            }
        });

        addMouseMotionListener(new MouseMotionAdapter() {
            public void mouseDragged(MouseEvent e) {
                int thisX = frame.getLocation().x;
                int thisY = frame.getLocation().y;

                int xMoved = (thisX + e.getX()) - (thisX + initialClick.x);
                int yMoved = (thisY + e.getY()) - (thisY + initialClick.y);

                int X = thisX + xMoved;
                int Y = thisY + yMoved;
                frame.setLocation(X, Y);
            }
        });

        // Add button actions
        minimizeButton.addActionListener(e -> frame.setState(Frame.ICONIFIED));
        maximizeButton.addActionListener(e -> {
            if (frame.getExtendedState() != Frame.MAXIMIZED_BOTH) {
                frame.setExtendedState(Frame.MAXIMIZED_BOTH);
            } else {
                frame.setExtendedState(Frame.NORMAL);
            }
        });
        closeButton.addActionListener(e -> {
            // Use the existing exit functionality
            if (frame instanceof ChatClientGUI) {
                ChatClientGUI chatGui = (ChatClientGUI) frame;
                chatGui.getExitButton().doClick(); // This will trigger the existing exit logic
            } else {
                System.exit(0);
            }
        });
    }

    private JButton createButton(String text) {
        JButton button = new JButton(text);
        button.setForeground(new Color(0, 255, 0));
        button.setBackground(Color.BLACK);
        button.setBorder(BorderFactory.createEmptyBorder(5, 15, 5, 15));
        button.setFocusPainted(false);
        button.setFont(new Font("Arial", Font.PLAIN, 16));
        
        // Add hover effect
        button.addMouseListener(new MouseAdapter() {
            public void mouseEntered(MouseEvent e) {
                button.setBackground(new Color(30, 30, 30));
            }
            public void mouseExited(MouseEvent e) {
                button.setBackground(Color.BLACK);
            }
        });
        
        return button;
    }
}