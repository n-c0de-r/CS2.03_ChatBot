import java.io.InputStreamReader;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;

public class Client {
	private int port;
	private String host;
	private static BufferedReader bufferedReader;
	private PrintWriter printWriter;
	private Socket socket;

	public static void main(String[] args) throws UnknownHostException, IOException, InterruptedException {
		Client colmin = new Client("127.0.0.1", 8005);
		colmin.startClient();

		while (true) {
			System.out.println("Please enter text:");
			Scanner s = new Scanner(System.in);
			String m = s.nextLine();
			
			colmin.writeMessage(m);
			readMessage();
			Thread.sleep(5000);
		}
	}

	// Stores the IP address & port nr the client is connecting to
	public Client(String host, int port) {
		this.host = host;
		this.port = port;
	}

	// Starts the client and sets up streams to and from it
	private void startClient() throws UnknownHostException, IOException {
		connectSocket();
		setupStreams();
	}

	// Creates a socket to communicate through
	private void connectSocket() throws UnknownHostException, IOException {
		socket = new Socket(host, port);
	}

	// Create IO-Streams
	private void setupStreams() throws IOException {
		InputStreamReader input = new InputStreamReader(socket.getInputStream());
		OutputStreamWriter output = new OutputStreamWriter(socket.getOutputStream());

		bufferedReader = new BufferedReader(input);
		printWriter = new PrintWriter(output);
	}

	// new code
	private static void readMessage() throws IOException {
		String s = bufferedReader.readLine();
		System.out.println("Client got message: " + s);
	}

	// Method to write messages inside the client
	private void writeMessage(String message) {
		printWriter.print(message + "\r\n");
		printWriter.flush();
	}
}
