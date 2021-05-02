import java.io.InputStreamReader;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Scanner;

public class Server {
	private int port;
	private PrintWriter printWriter;
	private ServerSocket socket;
	private Socket client;
	private Scanner scanner;

	public static void main(String[] args) throws IOException {
		// We were in breakout room nr 5, so sour server and port reflect that :D
		Server room5 = new Server(8005);
		room5.startServer();
	}

	private Server(int port) {
		this.port = port;
	}
	// Starts the server, sets up streams to and from it and wait for connection
	private void startServer() throws IOException {
		socket = openServerSocket(port);
		client = waitForLogin(socket);
		System.out.println("connected");
		setupStreams(client);

		while (true) {
			String message = readMessage(scanner);
			writeMessage(message + "'" + message + "' arrived at server");
		}
	}

	// Creates a socket to communicate through
	private ServerSocket openServerSocket(int port) throws IOException {
		socket = new ServerSocket(port);
		return socket;
	}

	private Socket waitForLogin(ServerSocket serverSocket) throws IOException {
		client = serverSocket.accept();
		return client;
	}

	// Create IO-Streams
	private void setupStreams(Socket client) throws IOException {
		InputStreamReader input = new InputStreamReader(client.getInputStream());
		OutputStreamWriter output = new OutputStreamWriter(client.getOutputStream());

		scanner = new Scanner(input);
		printWriter = new PrintWriter(output);
	}

	// Method to read messages from the client
	private String readMessage(Scanner scn) throws IOException {
		return scn.nextLine();
	}

	// Method to write messages back to the client
	private void writeMessage(String message) {
		printWriter.print(message + "\r\n");
		printWriter.flush();
	}
}
