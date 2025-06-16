
# Java Network Chat Application

A real-time networked chat application implemented in Java, featuring a Swing-based graphical interface and TCP/IP socket communication. This application allows users to either host a chat server or join an existing one, enabling real-time communication between multiple users.

## Key Features

### Network Architecture
- TCP/IP socket-based client-server communication
- Multi-threaded server supporting concurrent client connections
- Automatic server IP address detection and display
- Port 5000 used for all network communications

### User Interface
- Clean Swing-based graphical interface
- Real-time message updates
- Scrollable message history
- Simple text input with Enter-to-send functionality
- Exit button for graceful disconnection

### Messaging Features
- Timestamped messages in HH:mm:ss format
- User join/leave notifications
- Custom username support
- System messages for connection status
- Message broadcasting to all connected clients

## Requirements

- Java Development Kit (JDK) 24
- Network connectivity for multi-user chat
- Available port 5000 for server operation

## Getting Started

### Starting the Server
Run the ChatServer class to start the server. The server will:
1. Start listening on port 5000
2. Display all available IP addresses
3. Accept incoming client connections
4. Handle message broadcasting between clients

### Starting the Client
Run the ChatClientGUI class. You will be presented with two options:
1. **Host**: Create a new chat server
2. **Join**: Connect to an existing server

## Usage Instructions

### Hosting a Chat Server
1. Launch the client application
2. Select "Host" option
3. Enter your username
4. Share your displayed IP address with other users

### Joining a Chat
1. Launch the client application
2. Select "Join" option
3. Enter the host's IP address
4. Choose your username

### Chat Functions
- Type your message in the text field and press Enter to send
- Messages appear in the main chat area with timestamps
- Use the Exit button to leave the chat gracefully
- System notifications appear for user join/leave events

## Technical Implementation

### Server Component
- Maintains a list of connected clients
- Handles client connections/disconnections automatically
- Broadcasts messages to all connected clients
- Provides console output for server status

### Client Component
- Manages socket connections to the server
- Handles incoming/outgoing message streams
- Provides connection status feedback
- Implements graceful disconnection handling

### GUI Component
- Thread-safe message display using SwingUtilities
- Auto-scrolling message area
- Non-editable message display
- Simple message input interface

## Troubleshooting

### Connection Issues
- Verify the server is running
- Check if port 5000 is available
- Ensure correct IP address entry
- Verify network connectivity

### Client Problems
- Check server connection status
- Verify correct IP address input
- Ensure Java runtime environment is properly installed
- Check for firewall restrictions

## Building from Source

1. Compile all Java files using the Java compiler
2. Run the server application first
3. Run the client application and choose to host or join

## License

This project is open source and available under the MIT License.

## Contributing

Contributions are welcome! Please feel free to submit issues and pull requests.

## Security Note

This implementation is designed for local network use. For internet deployment, additional security measures would be necessary.
