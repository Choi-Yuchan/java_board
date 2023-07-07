package java_board;

import java.io.IOException;
import java.net.Socket;

public class BoardClient {

	public static void main(String[] args) {
		String serverIP = "127.0.0.1";

		Socket socket = null;

		try {

			socket = new Socket(serverIP, 8888);

			System.out.println("Connected at server.");

		} catch (IOException e) {
			e.printStackTrace();
		}

	}

}
