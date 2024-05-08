import java.io.InputStreamReader;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Scanner;

/**
 * A very basic Server that listens to incomming messages and repeats them back.
 * Not too fancy and might differ from your Lab exercises or implementations.
 */
public class Server {
    private int port;
    private PrintWriter printWriter;
    private ServerSocket socket;
    private Socket client;
    private Scanner scanner;

    public static void main(String[] args) throws IOException {
        // We were in breakout room nr 5, so our server and port reflect that :D
        Server room5 = new Server(8005);
        room5.startServer();
    }
    
    /**
     * Creates a Server listening to the given port.
     * @param port  The port number to listen to.
     */
    private Server(int port) {
        this.port = port;
    }

    /**
     * Starts the server, sets up streams to and from it and wait for connection
     */ 
    private void startServer() {
        socket = openServerSocket(port);
        if(socket != null)
            client = waitForLogin(socket);
            
        if(client != null)
            setupStreams(client);

        while (true) {
            String message = readMessage(scanner);
            writeMessage(message + "'" + message + "' arrived at server");
        }
    }

    /**
     * Creates a socket to communicate through
     * @param port  The port number to connect to.
     * @returns A ServerSocket on successful connection.
     */ 
    private ServerSocket openServerSocket(int port) {
        try
        {
            socket = new ServerSocket(port);
            
            if(socket != null)
                System.out.println("connected");
            
                return socket;
        }
        catch (IOException ioe)
        {
            ioe.printStackTrace();
        }
        return null;
    }
    
    /**
     * Tries to connect to a ServerSocket and returns a client Socket.
     * @param serverSocket  The ServerSocket to connect the client to.
     * @returns A Socket connected to the Server, representing the Client.
     */
    private Socket waitForLogin(ServerSocket serverSocket) {
        try
        {
            client = serverSocket.accept();
            return client;
        }
        catch (IOException ioe)
        {
            ioe.printStackTrace();
        }
        return null;
    }

    /**
     * Create IO-Streams
     * @param client    The client socket to read and write to.
     */ 
    private void setupStreams(Socket client) {
        try (InputStreamReader input = new InputStreamReader(client.getInputStream());
        OutputStreamWriter output = new OutputStreamWriter(client.getOutputStream()))
        {
            scanner = new Scanner(input);
            printWriter = new PrintWriter(output);
        } 
        catch (IOException ioe)
        {
            ioe.printStackTrace();
        }
    }

    /**
     * Method to read messages from the client
     * @param scn   Scanner class to read input from.
     * @returns The String of the input.
     */
    private String readMessage(Scanner scn) {
        return scn.nextLine();
    }

    /**
     * Method to write messages back to the client
     * @param message   The received message to print to the screen.
     */ 
    private void writeMessage(String message) {
        printWriter.print(message + "\r\n");
        printWriter.flush();
    }
}
