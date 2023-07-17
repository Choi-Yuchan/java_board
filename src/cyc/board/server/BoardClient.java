package cyc.board.server;

import java.io.IOException;
import java.net.Socket;

import cyc.board.thread.BoardHandler;

public class BoardClient {
    public static void main(String[] args) {
        String serverIP = "127.0.0.1";

        try {
            Socket socket = new Socket(serverIP, 7777);
            System.out.println("Connected to server.");

            BoardHandler boardHandler = new BoardHandler(socket);
            boardHandler.start();
            try {
				BoardService.main(null);
			} catch (Exception e) {
				e.printStackTrace();
			}
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
