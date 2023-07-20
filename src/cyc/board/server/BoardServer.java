package cyc.board.server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

import cyc.board.thread.BoardHandler;

public class BoardServer {
	public static void main(String[] args) {
		ServerSocket serverSocket = null;

		try {
			// Port number
			serverSocket = new ServerSocket(7777);
			System.out.println("Server is ready.");

			while (true) {
				Socket socket = serverSocket.accept();
				System.out.println("Connected: " + socket.getInetAddress().getHostName());

				BoardHandler boardHandler = new BoardHandler(socket);
				boardHandler.start();

			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
}
