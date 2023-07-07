package java_board;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class BoardServer {

	public static void main(String[] args) {

		ServerSocket serverSocket = null;

		Socket socket = null;

		try {
			// Port number
			serverSocket = new ServerSocket(8888);
			System.out.println("Server is ready.");

			socket = serverSocket.accept();
			System.out.println("Connected." + socket.getInetAddress().getHostName());

		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
