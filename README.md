# Java Chat Application

A real-time chat application built with Java that enables multiple users to communicate through a graphical interface. The application uses a client-server architecture with TCP/IP networking and provides a responsive Swing-based GUI for users.

## Features

- Multi-user chat support
- Real-time message broadcasting
- User-friendly graphical interface
- Timestamped messages
- Custom user names
- Join/leave notifications
- Auto-scrolling message history
- Graceful disconnection handling

## Requirements

- Java JDK 24 or higher
- Network connectivity for client-server communication

## Getting Started

1. Start the server:
   ```bash
   java ChatServer
   ```
   The server will start listening on port 5000.

2. Launch the client application:
   ```bash
   java ChatClientGUI
   ```
   Multiple instances can be run for different users.

## Usage

1. When launching the client, enter your desired username when prompted
2. Type messages in the input field and press Enter to send
3. Messages will appear in the main chat area with timestamps
4. Click the Exit button to leave the chat gracefully

## Technical Details

- Server runs on port 5000
- Uses TCP/IP sockets for reliable communication
- Built with Java Swing for the GUI
- Implements multi-threading for handling multiple clients
- Automatic resource cleanup on disconnection

## Project Structure

- `ChatServer.java` - Server implementation that handles client connections
- `ChatClient.java` - Network communication logic for the client
- `ChatClientGUI.java` - Graphical user interface for the chat client

## Building from Source

1. Clone the repository
2. Compile the Java files:
   ```bash
   javac ChatServer.java ChatClient.java ChatClientGUI.java
   ```
3. Run the server and client applications as described in the Getting Started section

## Network Configuration

By default, the client connects to `localhost` (127.0.0.1). For use across a network, modify the server address in the client configuration to match your server's IP address.

## Contributing

Feel free to submit issues and enhancement requests.

## License

This project is open source and available under the [MIT License](LICENSE).
