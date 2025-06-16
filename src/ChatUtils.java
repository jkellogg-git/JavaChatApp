import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Enumeration;

public final class ChatUtils {

    public enum ServerOption {
        HOST,
        JOIN
    }

    public enum MessageSenderInfo {
        SYSTEM("[" + new SimpleDateFormat("HH:mm:ss").format(new Date()) + "] System: "),
        USER ("[" + new SimpleDateFormat("HH:mm:ss").format(new Date()) + "] ");

        private final String message;
        MessageSenderInfo(String s) {
            this.message = s;
        }

        public String messagePrefix() {return message;}
    }

    public static String getLocalIPAddress() {
        try {
            Enumeration<NetworkInterface> interfaces = NetworkInterface.getNetworkInterfaces();
            while (interfaces.hasMoreElements()) {
                NetworkInterface iface = interfaces.nextElement();
                // Skip loopback interfaces and interfaces that are down
                if (iface.isLoopback() || !iface.isUp()) {
                    continue;
                }

                Enumeration<InetAddress> addresses = iface.getInetAddresses();
                while (addresses.hasMoreElements()) {
                    InetAddress addr = addresses.nextElement();
                    // Skip IPv6 addresses and loopback addresses
                    if (addr.getHostAddress().contains(":") || addr.isLoopbackAddress()) {
                        continue;
                    }
                    return addr.getHostAddress();
                }
            }
        } catch (SocketException e) {
            System.err.println("Failed to get local IP address: " + e.getMessage());
        }
        return "0.0.0.0"; // Fallback to all interfaces if no specific IP is found
    }
}