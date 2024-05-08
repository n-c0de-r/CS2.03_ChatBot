import java.io.InputStreamReader;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Scanner;

/**
 * A very basic Client that connects to a Server.
 * It takes input messages from the user and sends them to the server.
 * Not too fancy and might differ from your Lab exercises or implementations.
 */
public class Client {
    private int port;
    private String host;
    private static BufferedReader bufferedReader;
    private PrintWriter printWriter;
    private Socket socket;

    public static void main(String[] args){
        Client client = new Client("127.0.0.1", 8005);
        client.start();

        while (true) {
            System.out.println("Please enter text:");
            Scanner s = new Scanner(System.in);
            String m = s.nextLine();

            client.writeMessage(m);
            client.readMessage();
            try
            {
                // wait 5s to display the message, to see if it works
                Thread.sleep(5000);
            }
            catch (InterruptedException ie)
            {
                ie.printStackTrace();
            }
        }
    }

    /**
     * Stores the IP address & port nr the client is connecting to.
     * @param host  The String of the host's IP address.
     * @param port  The port nr to listen to on connection.
     */ 
    public Client(String host, int port) {
        this.host = host;
        this.port = port;
    }

    /**
     * Starts the client and sets up streams to and from it
     */ 
    private void start() {
        connectSocket();
        setupStreams();
    }

    /**
     * Creates a socket to communicate through
     */ 
    private void connectSocket() {
        try
        {
            socket = new Socket(host, port);
        }
        catch (UnknownHostException uhe)
        {
            uhe.printStackTrace();
        }
        catch (IOException ioe)
        {
            ioe.printStackTrace();
        }
    }

    // From here on, almost 1:1 copy-paste from Server!
    /**
     * Create IO-Streams
     */ 
    private void setupStreams() {
        try (InputStreamReader input = new InputStreamReader(socket.getInputStream());
        OutputStreamWriter output = new OutputStreamWriter(socket.getOutputStream());)
        {
            bufferedReader = new BufferedReader(input);
            printWriter = new PrintWriter(output);
        }
        catch (IOException ioe)
        {
            ioe.printStackTrace();
        }
    }

    /**
     * Read incoming messages and print them out
     */ 
    private void readMessage() {
        try
        {
            String s = bufferedReader.readLine();
            System.out.println("Client got the following: " + s);
        }
        catch (IOException ioe)
        {
            ioe.printStackTrace();
        }
    }

    /**
     * Method to write messages to the server
     * @param message   The message string to write to the connection.
     */ 
    private void writeMessage(String message) {
        printWriter.print(message + "\r\n");
        printWriter.flush();
    }
}
